package lexico;

public enum Codigo {

    CADEIA_VAZIA(0),
    FIM_ARQUIVO(1),
    MAIS(2),
    MENOS(3),
    ASTERISCO(4),
    DIVISAO(5),
    IGUAL(6),
    MAIOR(7),
    MAIOR_IGUAL(8),
    MENOR(9),
    MENOR_IGUAL(10),
    DIFERENTE(11),
    ATRIBUICAO(12),
    DOIS_PONTOS(13),
    PONTO_VIRGULA(14),
    VIRGULA(15),
    PONTO(16),
    PARENTESES_ABERTO(17),
    PARENTESES_FECHADO(18),
    ID(19),
    INTEIRO(20),
    LIT(21),
    PROGRAM(22),
    CONST(23),
    VAR(24),
    PROCEDURE(25),
    BEGIN(26),
    END(27),
    INTEGER(28),
    OF(29),
    CALL(30),
    IF(31),
    THEN(32),
    ELSE(33),
    WHILE(34),
    DO(35),
    REPEAT(36),
    UNTIL(37),
    READLN(38),
    WRITELN(39),
    OR(40),
    AND(41),
    NOT(42),
    FOR(43),
    TO(44),
    CASE(45),
    COMENTARIO(46),

    CARACTERE_INVALIDO(9998),
    FIM_ARQUIVO_INESPERADO(9999)
    ;

    public Integer value;

    Codigo(Integer value) {
        this.value = value;
    }
}