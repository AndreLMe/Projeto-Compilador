package AnalisadorLexico.src.com.br.compilador.main;

import AnalisadorLexico.src.com.br.compilador.exceptions.loveLexicalException;
import AnalisadorLexico.src.com.br.compilador.lexico.LoveScanner;
import AnalisadorLexico.src.com.br.compilador.lexico.Token;

public class MainClass {
    public static void main(String[] args) {
        LoveScanner ls = new LoveScanner("AnalisadorLexico/src/input-test.love");
        Token token = null;
        try{

        }
        catch(loveLexicalException ex){
            System.out.println(ex);
        }
        catch(Exception ex){
            System.out.println("Generic error!!!");
        }
        do{
            token = ls.nextToken();
            if(token != null){
                System.out.println(token);
            }
        }while(token != null);
    }
}
