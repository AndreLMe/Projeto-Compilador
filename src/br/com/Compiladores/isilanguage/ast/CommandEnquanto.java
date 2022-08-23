package br.com.Compiladores.isilanguage.ast;
import java.util.ArrayList;
public class CommandEnquanto extends AbstractCommand {

    private String condition;
    private ArrayList<AbstractCommand> enquantoCommandList;
    
    public CommandEnquanto(String condition, ArrayList<AbstractCommand> enquatoList) {
        this.condition = condition;
        this.enquantoCommandList = enquatoList;
    }
    
    @Override
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nwhile(").append(condition).append("){\n");
        for(AbstractCommand v: this.enquantoCommandList)
        {
            builder.append(v.generateJavaCode());
        }
        builder.append("\n}");
        return builder.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(AbstractCommand v: enquantoCommandList)
        {
            builder.append(v);
        }
        return "Comando enquanto lido com sucesso.\n [enquanto ("+condition+" ){\n"+builder+"\n";
    }
}
