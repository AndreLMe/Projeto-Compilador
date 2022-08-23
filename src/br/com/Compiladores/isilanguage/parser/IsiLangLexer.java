// Generated from IsiLang.g4 by ANTLR 4.10.1
package br.com.Compiladores.isilanguage.parser;

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

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class IsiLangLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, AP=11, FP=12, SC=13, OP=14, ATTR=15, VIR=16, ACH=17, FCH=18, 
		OPREL=19, ID=20, NUMBER=21, WS=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "AP", "FP", "SC", "OP", "ATTR", "VIR", "ACH", "FCH", "OPREL", 
			"ID", "NUMBER", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'programa'", "'fimprog;'", "'declare'", "'numero'", "'texto'", 
			"'enquanto'", "'leia'", "'escreva'", "'se'", "'senao'", "'('", "')'", 
			"';'", null, "'='", "','", "'{'", "'}'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "AP", 
			"FP", "SC", "OP", "ATTR", "VIR", "ACH", "FCH", "OPREL", "ID", "NUMBER", 
			"WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


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


	public IsiLangLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "IsiLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0016\u00a6\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0002\u0015\u0007\u0015\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0003\u0012\u008d\b\u0012\u0001\u0013\u0001\u0013\u0005"+
		"\u0013\u0091\b\u0013\n\u0013\f\u0013\u0094\t\u0013\u0001\u0014\u0004\u0014"+
		"\u0097\b\u0014\u000b\u0014\f\u0014\u0098\u0001\u0014\u0001\u0014\u0004"+
		"\u0014\u009d\b\u0014\u000b\u0014\f\u0014\u009e\u0003\u0014\u00a1\b\u0014"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0000\u0000\u0016\u0001"+
		"\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007"+
		"\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d"+
		"\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016\u0001\u0000"+
		"\u0006\u0003\u0000*+--//\u0002\u0000<<>>\u0001\u0000az\u0003\u000009A"+
		"Zaz\u0001\u000009\u0003\u0000\t\n\r\r  \u00ad\u0000\u0001\u0001\u0000"+
		"\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000"+
		"\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000"+
		"\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000"+
		"\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000"+
		"\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000"+
		"\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000"+
		"\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000"+
		"\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000"+
		"#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001"+
		"\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000"+
		"\u0000\u0001-\u0001\u0000\u0000\u0000\u00036\u0001\u0000\u0000\u0000\u0005"+
		"?\u0001\u0000\u0000\u0000\u0007G\u0001\u0000\u0000\u0000\tN\u0001\u0000"+
		"\u0000\u0000\u000bT\u0001\u0000\u0000\u0000\r]\u0001\u0000\u0000\u0000"+
		"\u000fb\u0001\u0000\u0000\u0000\u0011j\u0001\u0000\u0000\u0000\u0013m"+
		"\u0001\u0000\u0000\u0000\u0015s\u0001\u0000\u0000\u0000\u0017u\u0001\u0000"+
		"\u0000\u0000\u0019w\u0001\u0000\u0000\u0000\u001by\u0001\u0000\u0000\u0000"+
		"\u001d{\u0001\u0000\u0000\u0000\u001f}\u0001\u0000\u0000\u0000!\u007f"+
		"\u0001\u0000\u0000\u0000#\u0081\u0001\u0000\u0000\u0000%\u008c\u0001\u0000"+
		"\u0000\u0000\'\u008e\u0001\u0000\u0000\u0000)\u0096\u0001\u0000\u0000"+
		"\u0000+\u00a2\u0001\u0000\u0000\u0000-.\u0005p\u0000\u0000./\u0005r\u0000"+
		"\u0000/0\u0005o\u0000\u000001\u0005g\u0000\u000012\u0005r\u0000\u0000"+
		"23\u0005a\u0000\u000034\u0005m\u0000\u000045\u0005a\u0000\u00005\u0002"+
		"\u0001\u0000\u0000\u000067\u0005f\u0000\u000078\u0005i\u0000\u000089\u0005"+
		"m\u0000\u00009:\u0005p\u0000\u0000:;\u0005r\u0000\u0000;<\u0005o\u0000"+
		"\u0000<=\u0005g\u0000\u0000=>\u0005;\u0000\u0000>\u0004\u0001\u0000\u0000"+
		"\u0000?@\u0005d\u0000\u0000@A\u0005e\u0000\u0000AB\u0005c\u0000\u0000"+
		"BC\u0005l\u0000\u0000CD\u0005a\u0000\u0000DE\u0005r\u0000\u0000EF\u0005"+
		"e\u0000\u0000F\u0006\u0001\u0000\u0000\u0000GH\u0005n\u0000\u0000HI\u0005"+
		"u\u0000\u0000IJ\u0005m\u0000\u0000JK\u0005e\u0000\u0000KL\u0005r\u0000"+
		"\u0000LM\u0005o\u0000\u0000M\b\u0001\u0000\u0000\u0000NO\u0005t\u0000"+
		"\u0000OP\u0005e\u0000\u0000PQ\u0005x\u0000\u0000QR\u0005t\u0000\u0000"+
		"RS\u0005o\u0000\u0000S\n\u0001\u0000\u0000\u0000TU\u0005e\u0000\u0000"+
		"UV\u0005n\u0000\u0000VW\u0005q\u0000\u0000WX\u0005u\u0000\u0000XY\u0005"+
		"a\u0000\u0000YZ\u0005n\u0000\u0000Z[\u0005t\u0000\u0000[\\\u0005o\u0000"+
		"\u0000\\\f\u0001\u0000\u0000\u0000]^\u0005l\u0000\u0000^_\u0005e\u0000"+
		"\u0000_`\u0005i\u0000\u0000`a\u0005a\u0000\u0000a\u000e\u0001\u0000\u0000"+
		"\u0000bc\u0005e\u0000\u0000cd\u0005s\u0000\u0000de\u0005c\u0000\u0000"+
		"ef\u0005r\u0000\u0000fg\u0005e\u0000\u0000gh\u0005v\u0000\u0000hi\u0005"+
		"a\u0000\u0000i\u0010\u0001\u0000\u0000\u0000jk\u0005s\u0000\u0000kl\u0005"+
		"e\u0000\u0000l\u0012\u0001\u0000\u0000\u0000mn\u0005s\u0000\u0000no\u0005"+
		"e\u0000\u0000op\u0005n\u0000\u0000pq\u0005a\u0000\u0000qr\u0005o\u0000"+
		"\u0000r\u0014\u0001\u0000\u0000\u0000st\u0005(\u0000\u0000t\u0016\u0001"+
		"\u0000\u0000\u0000uv\u0005)\u0000\u0000v\u0018\u0001\u0000\u0000\u0000"+
		"wx\u0005;\u0000\u0000x\u001a\u0001\u0000\u0000\u0000yz\u0007\u0000\u0000"+
		"\u0000z\u001c\u0001\u0000\u0000\u0000{|\u0005=\u0000\u0000|\u001e\u0001"+
		"\u0000\u0000\u0000}~\u0005,\u0000\u0000~ \u0001\u0000\u0000\u0000\u007f"+
		"\u0080\u0005{\u0000\u0000\u0080\"\u0001\u0000\u0000\u0000\u0081\u0082"+
		"\u0005}\u0000\u0000\u0082$\u0001\u0000\u0000\u0000\u0083\u008d\u0007\u0001"+
		"\u0000\u0000\u0084\u0085\u0005>\u0000\u0000\u0085\u008d\u0005=\u0000\u0000"+
		"\u0086\u0087\u0005<\u0000\u0000\u0087\u008d\u0005=\u0000\u0000\u0088\u0089"+
		"\u0005=\u0000\u0000\u0089\u008d\u0005=\u0000\u0000\u008a\u008b\u0005!"+
		"\u0000\u0000\u008b\u008d\u0005=\u0000\u0000\u008c\u0083\u0001\u0000\u0000"+
		"\u0000\u008c\u0084\u0001\u0000\u0000\u0000\u008c\u0086\u0001\u0000\u0000"+
		"\u0000\u008c\u0088\u0001\u0000\u0000\u0000\u008c\u008a\u0001\u0000\u0000"+
		"\u0000\u008d&\u0001\u0000\u0000\u0000\u008e\u0092\u0007\u0002\u0000\u0000"+
		"\u008f\u0091\u0007\u0003\u0000\u0000\u0090\u008f\u0001\u0000\u0000\u0000"+
		"\u0091\u0094\u0001\u0000\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000"+
		"\u0092\u0093\u0001\u0000\u0000\u0000\u0093(\u0001\u0000\u0000\u0000\u0094"+
		"\u0092\u0001\u0000\u0000\u0000\u0095\u0097\u0007\u0004\u0000\u0000\u0096"+
		"\u0095\u0001\u0000\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000\u0098"+
		"\u0096\u0001\u0000\u0000\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099"+
		"\u00a0\u0001\u0000\u0000\u0000\u009a\u009c\u0005.\u0000\u0000\u009b\u009d"+
		"\u0007\u0004\u0000\u0000\u009c\u009b\u0001\u0000\u0000\u0000\u009d\u009e"+
		"\u0001\u0000\u0000\u0000\u009e\u009c\u0001\u0000\u0000\u0000\u009e\u009f"+
		"\u0001\u0000\u0000\u0000\u009f\u00a1\u0001\u0000\u0000\u0000\u00a0\u009a"+
		"\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000\u0000\u0000\u00a1*\u0001"+
		"\u0000\u0000\u0000\u00a2\u00a3\u0007\u0005\u0000\u0000\u00a3\u00a4\u0001"+
		"\u0000\u0000\u0000\u00a4\u00a5\u0006\u0015\u0000\u0000\u00a5,\u0001\u0000"+
		"\u0000\u0000\u0007\u0000\u008c\u0090\u0092\u0098\u009e\u00a0\u0001\u0006"+
		"\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}