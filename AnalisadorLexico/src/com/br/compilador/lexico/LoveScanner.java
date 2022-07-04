package AnalisadorLexico.src.com.br.compilador.lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.lang.model.element.Element;
import javax.xml.transform.Templates;

import AnalisadorLexico.src.com.br.compilador.exceptions.loveLexicalException;

public class LoveScanner {
    
	private char[] content;
	private int state;
	private int position;

	public LoveScanner(String filename) {
		try{
			String fileContent;
			fileContent = new String(Files.readAllBytes(
									Paths.get(filename)),
									StandardCharsets.UTF_8);
			System.out.println("--------DEBUGING--------");
			System.out.println(fileContent);
			System.out.println("------------------------");
			content = fileContent.toCharArray();
			position = 0;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public Token nextToken() {
		char currentChar;
		String term = "";
		if(isEOF()){
			return null;
		}
		state = 0;
		while(true){
			currentChar = nextChar();
			switch (state){
				case 0:
					if(isChar(currentChar)){
						term += currentChar;
						state = 1;
					}
					else if(isDigit((currentChar))){
						term += currentChar;
						state = 3;
					}
					else if(isSpace(currentChar)){
						state = 0;
					}
					else if(isOperator(currentChar)){
						state = 5;
					}
					else{
						throw new loveLexicalException("Unrecognized SYMBOL.");
					}
					break;
				case 1:
					if(isChar(currentChar)||
						isDigit(currentChar)){
						term += currentChar;
						state = 1;
					}
					else if(isOperator(currentChar)||
							isOperator(currentChar) ){
						state = 2;
					}
					else{
						throw new loveLexicalException("Malformed Identifier");
					}
					break;
				case 2:
					back();
					Token token = new Token();
					token.setType(Token.TK_IDENTIFIER);
					token.setText(term);
					return token;
				case 3:
					if(isDigit(currentChar)){
						term += currentChar;
						state = 3;
					}
					else if(!isChar(currentChar)){
						state = 4;
					}
					else{
						throw new loveLexicalException("Unrecognized Numeber.");
					}
					break;
				case 4:
					token = new Token();
					token.setType(Token.TK_NUMBER);
					token.setText(term);
					back();
					return token;
				case 5:
					term += currentChar;
					token = new Token();
					token.setText(term);
					token.setType(Token.TK_OPERATOR);
					return token;
			}
		}
	}
	
	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private boolean isChar(char c) {
		return c >='a' && c <= 'z' ||
				c >= 'A' && c <= 'Z';
	}

	private boolean isOperator(char c) {
		return c == '>' || c == '<' || 
		c == '=' || c == '!';
	}

	private boolean isSpace(char c) {
		return c == ' ' || c == '\t' ||
				c == '\n' || c == '\r';
	}

	private char nextChar() {
		return content[position++];
	}

	private boolean isEOF() {
		return position == content.length;
	}

	private void back() {
		position--;
	}
}
