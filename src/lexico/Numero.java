package lexico;

public class Numero extends Token {
    
    public final int value;
    
    public Numero(int value) {
        super(Codigo.INTEGER.value, "Número");
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
}
