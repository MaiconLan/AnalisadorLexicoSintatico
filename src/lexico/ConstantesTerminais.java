package lexico;

public class ConstantesTerminais extends Token {

    private String lexeme = "";

    public static final ConstantesTerminais AND = new ConstantesTerminais("&&", Codigo.AND.value, "Sinal AND");
    public static final ConstantesTerminais OR = new ConstantesTerminais("||", Codigo.OR.value, "Sinal OR");
    public static final ConstantesTerminais IGUAL = new ConstantesTerminais("=", Codigo.IGUAL.value, "Sinal de Igualdade");
    public static final ConstantesTerminais DIFERENTE = new ConstantesTerminais("!=", Codigo.DIFERENTE.value, "Sinal de Diferenciação");
    public static final ConstantesTerminais MENOR_IGUAL = new ConstantesTerminais("<=", Codigo.MENOR_IGUAL.value, "Sinal de comparação menor ou igual");
    public static final ConstantesTerminais MAIOR_IGUAL = new ConstantesTerminais(">=", Codigo.MAIOR_IGUAL.value, "Sinal de comparação maior ou igual");
    public static final ConstantesTerminais ANOTACAO_TIPO = new ConstantesTerminais(":", Codigo.DOIS_PONTOS.value, "Sinal anotação de tipo");

    public static final ConstantesTerminais ATRIBUICAO = new ConstantesTerminais(":=", Codigo.ATRIBUICAO.value, "Sinal atribuição");
    public static final ConstantesTerminais PARENTESES_ABERTO = new ConstantesTerminais("(", Codigo.PARENTESES_ABERTO.value, "Sinal parenteses aberto");
    public static final ConstantesTerminais PARENTESES_FECHADO = new ConstantesTerminais(")", Codigo.PARENTESES_FECHADO.value, "Sinal parenteses fechado");
    public static final ConstantesTerminais VIRGULA = new ConstantesTerminais(",", Codigo.VIRGULA.value, "Sinal vírgula");
    public static final ConstantesTerminais PONTO_VIRGULA = new ConstantesTerminais(";", Codigo.PONTO_VIRGULA.value, "Sinal ponto vírgula");

    public static final ConstantesTerminais ASTERISCO = new ConstantesTerminais("*", Codigo.ASTERISCO.value, "Sinal multiplicação ou comentário");
    public static final ConstantesTerminais MAIS = new ConstantesTerminais("+", Codigo.MAIS.value, "Sinal de soma ou concatenação");
    public static final ConstantesTerminais MENOS = new ConstantesTerminais("-", Codigo.MENOS.value, "Sinal de subtração");
    public static final ConstantesTerminais DIVISAO = new ConstantesTerminais("/", Codigo.DIVISAO.value , "Sinal de divisão");
    public static final ConstantesTerminais MAIOR = new ConstantesTerminais(">", Codigo.MAIOR.value, "Sinal de comparação maior");
    public static final ConstantesTerminais MENOR = new ConstantesTerminais("<", Codigo.MENOR.value, "Sinal de comparação menor");
    public static final ConstantesTerminais FIM_ARQUIVO = new ConstantesTerminais("$", Codigo.FIM_ARQUIVO.value, "Sinal fim de arquivo");


    public ConstantesTerminais(String s, int tag, String descricao) {
        super(tag);
        lexeme = s;
        this.descricao = descricao;
    }

    public ConstantesTerminais(String s, int tag) {
        super(tag);
        lexeme = s;
    }

    @Override
    public String toString() {
        return "" + lexeme;
    }

    String getLexeme() {
        return toString();
    }
}
