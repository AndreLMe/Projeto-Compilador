package AnalisadorLexico.src.com.br.compilador.lexico;

public class Token {
    public static final int TK_IDENTIFIER = 0;
    public static final int TK_NUMBER = 1;
    public static final int TK_OPERATOR = 2;
    public static final int TK_PONTCTUATION = 3;
    public static final int TK_ASSIGN = 4;

    private String text;
    private int type;

    public Token(int type, String text) {
        super();
        this.text = text;
        this.type = type;
    }

    public Token() {
        super();
    }
    
    public String getText() {
        return text;
    }

    public int getType() {
        return type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Token [type= " + type +
                ", text= " + text + "]";
    }
}
