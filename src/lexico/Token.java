package lexico;

public class Token {

    public final int tag;
    public String descricao;

    public Token(int tag) {
        this.tag = tag;
    }

    public Token(int tag, String descricao){
        this.tag = tag;
        this.descricao = descricao;
    }
    
    public String toString(){
        return "" + tag;
    }
}
