grammar IsiLang;

@header{
	import br.com.Compiladores.isilanguage.datastructures.IsiSymbol;
	import br.com.Compiladores.isilanguage.datastructures.IsiVariable;
	import br.com.Compiladores.isilanguage.datastructures.IsiSymbolTable;
	import br.com.Compiladores.isilanguage.exceptions.IsiSemanticException;
	import br.com.Compiladores.isilanguage.ast.IsiProgram;
	import br.com.Compiladores.isilanguage.ast.AbstractCommand;
	import br.com.Compiladores.isilanguage.ast.CommandLeitura;
	import br.com.Compiladores.isilanguage.ast.CommandEscrita;
	import br.com.Compiladores.isilanguage.ast.CommandAtribuicao;
	import br.com.Compiladores.isilanguage.ast.CommandDecisao;
	import br.com.Compiladores.isilanguage.ast.CommandEnquanto;
	import java.util.ArrayList;
	import java.util.Stack;
}

@members{
	private int _tipo;
	private String _varName;
	private String _varValue;
	private IsiSymbolTable symbolTable = new IsiSymbolTable();
	private IsiSymbol symbol;
	private IsiProgram program = new IsiProgram();
	private ArrayList<AbstractCommand> curThread;
	private Stack<ArrayList<AbstractCommand>> stack = new Stack<ArrayList<AbstractCommand>>();
	private String _readID;
	private String _writeID;
	private String _exprID;
	private String _exprContent;
	private String _exprDecision;
	private ArrayList<AbstractCommand> listaTrue;
	private ArrayList<AbstractCommand> listaFalse;
	private ArrayList<String> listaNaoUsadas;
	
	public void verificaID(String id){
		if (!symbolTable.exists(id)){
			throw new IsiSemanticException("Symbol "+id+" not declared");
		}
	}

	public void exibeVariaveis()
	{
		ArrayList<IsiSymbol> ls = symbolTable.getAll();
		for(IsiSymbol v: ls)
		{
			System.out.println(v);
		}
	}
	
	public void exibeComandos(){
		for (AbstractCommand c: program.getComandos()){
			System.out.println(c);
		}
	}

	public void verificaVariaveisNaoUtilizadas()
	{
		listaNaoUsadas = new ArrayList<String>();
		for(IsiSymbol v: symbolTable.getAll())
		{
			IsiVariable isiVar = (IsiVariable)v;
			if(isiVar.getValue() == null)
			{
				listaNaoUsadas.add(isiVar.getName());
			}
		}
		if(listaNaoUsadas.size() != 0)
		{
			System.out.println("\nWARNING - As variaveis foram declaras, mas não foram utilizadas: " + String.join(", ", listaNaoUsadas));
		}
	}

	public void verificaSimboloNaTabela(String varName)
	{
		_varName = varName;
		_varValue = null;
		symbol = new IsiVariable(_varName, _tipo, _varValue);
		if(!symbolTable.exists(_varName))
		{
			symbolTable.add(symbol);
		}
		else
		{
			throw new IsiSemanticException("Symbol "+_varName+" already declared");
		}
	}
	
	public void generateCode(){
		program.generateTarget();
	}
}

prog	: 'programa' decl bloco 'fimprog;'
           {
           	 program.setVarTable(symbolTable);
           	 program.setComandos(stack.pop());

           }
		;

decl    :  (declaravar)+
        ;
        
        
declaravar :  'declare' tipo ID { verificaSimboloNaTabela(_input.LT(-1).getText());}        
                        (VIR ID { verificaSimboloNaTabela(_input.LT(-1).getText());})*
		                 SC
           ;

tipo       : 'numero' { _tipo = IsiVariable.NUMBER;  }
           | 'texto'  { _tipo = IsiVariable.TEXT;  }
           ;
        
bloco	: {
			curThread = new ArrayList<AbstractCommand>(); 
	        stack.push(curThread);  
          }
          (cmd)+
		;


cmd		:  cmdleitura
 		|  cmdescrita
 		|  cmdattrib
 		|  cmdselecao
 		|  cmdEnquanto
		;

cmdEnquanto  :  'enquanto' AP
                 ID { _exprDecision = _input.LT(-1).getText(); }
                 OPREL { _exprDecision += _input.LT(-1).getText(); }
                 (ID | NUMBER) {_exprDecision += _input.LT(-1).getText(); }
                 FP
                 ACH
                 {
                 	 curThread = new ArrayList<AbstractCommand>();
                     ArrayList<AbstractCommand> enquantoLista = new ArrayList<AbstractCommand>();
                     stack.push(curThread);
                 }
                 (cmd)+
                 FCH
                 {
                       enquantoLista = stack.pop();
                       CommandEnquanto cmd = new CommandEnquanto(_exprDecision, enquantoLista);
                       stack.peek().add(cmd);
                 };

cmdleitura	: 'leia' AP
                     ID { verificaID(_input.LT(-1).getText());
                     	  _readID = _input.LT(-1).getText();
                        }
                     FP
                     SC

              {
              	IsiVariable var = (IsiVariable)symbolTable.get(_readID);
              	CommandLeitura cmd = new CommandLeitura(_readID, var);
              	stack.peek().add(cmd);
              };

cmdescrita	: 'escreva'
                 AP
                 ID { verificaID(_input.LT(-1).getText());
	                  _writeID = _input.LT(-1).getText();
                     }
                 FP
                 SC
               {
                  IsiVariable isiVar = (IsiVariable)symbolTable.get(_writeID);
                  if (isiVar.getValue() == null) {
                      throw new IsiSemanticException("Symbol " + _writeID + " não foi inicializado.");
                  }
               	  CommandEscrita cmd = new CommandEscrita(_writeID);
               	  stack.peek().add(cmd);
               }
			;

cmdattrib	:  ID { verificaID(_input.LT(-1).getText());
                    _exprID = _input.LT(-1).getText();
                   }
               ATTR { _exprContent = ""; }
               expr
               SC
               {
				 if(_exprContent == "")
				 {
					throw new IsiSemanticException("A variável "+ _exprID + " não recebeu atribuição.");
				 }
				 else
				 {
					IsiVariable var = (IsiVariable)symbolTable.get(_exprID);
              	 	var.setValue(_exprContent);
               	 	CommandAtribuicao cmd = new CommandAtribuicao(_exprID, _exprContent);
               	 	stack.peek().add(cmd);
				 }
               }
			;


cmdselecao  :  'se' AP
                    ID    { _exprDecision = _input.LT(-1).getText(); }
                    OPREL { _exprDecision += _input.LT(-1).getText(); }
                    (ID | NUMBER) {_exprDecision += _input.LT(-1).getText(); }
                    FP
                    ACH
                    { curThread = new ArrayList<AbstractCommand>();
                      stack.push(curThread);
                    }
                    (cmd)+

                    FCH
                    {
                       listaTrue = stack.pop();
                    }
                   ('senao'
                   	 ACH
                   	 {
                   	 	curThread = new ArrayList<AbstractCommand>();
                   	 	stack.push(curThread);
                   	 }
                   	(cmd+)
                   	FCH
                   	{
                   		listaFalse = stack.pop();
                   		CommandDecisao cmd = new CommandDecisao(_exprDecision, listaTrue, listaFalse);
                   		stack.peek().add(cmd);
                   	}
                   )?
            ;

expr		:  termo (
	             OP  { _exprContent += _input.LT(-1).getText();}
	            termo
	            )*
			;

termo		: ID {
	               _exprContent += _input.LT(-1).getText();
                 }
            |
              NUMBER
              {
              	_exprContent += _input.LT(-1).getText();
              }
			|
			  TEXT
			  {
				_exprContent += _input.LT(-1).getText();
			  }
			;
	
AP	: '('
	;
	
FP	: ')'
	;
	
SC	: ';'
	;
	
OP	: '+' | '-' | '*' | '/'
	;
	
ATTR : ':='
	 ;
	 
VIR  : ','
     ;
     
ACH  : '{'
     ;
     
FCH  : '}'
     ;
	 
	 
OPREL : '>' | '<' | '>=' | '<=' | '==' | '!='
      ;
      
ID	: [a-z] ([a-z] | [A-Z] | [0-9])*
	;
	
NUMBER	: [0-9]+ ('.' [0-9]+)?
		;

TEXT	: ('"'.*?'"')
		;
		
WS	: (' ' | '\t' | '\n' | '\r') -> skip;