package lexico;

import util.Util;

import java.io.*;
import java.util.Hashtable;


public class Lexico {
    public static int line = 1;

    private static String PALAVRA_RESERVADA = "Palavra Reservada";
    private char ch = ' ';
    private FileReader file = null;

    private Hashtable words = new Hashtable();

    private void reserve(ConstantesTerminais w) {
        words.put(w.getLexeme(), w);
    }

    public Lexico(String fileName) throws FileNotFoundException {

        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            throw e;
        }

        // Palavras reservadas
        reserve(new ConstantesTerminais("PROGRAM", Codigo.PROGRAM.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("VAR", Codigo.VAR.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("PROCEDURE", Codigo.PROCEDURE.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("END", Codigo.END.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("AND", Codigo.AND.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("OR", Codigo.OR.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("BEGIN", Codigo.BEGIN.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("INTEGER", Codigo.INTEGER.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("INTEIRO", Codigo.INTEIRO.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("LIT", Codigo.LIT.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("IF", Codigo.IF.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("THEN", Codigo.THEN.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("ELSE", Codigo.ELSE.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("DO", Codigo.DO.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("CALL", Codigo.CALL.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("WHILE", Codigo.WHILE.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("READLN", Codigo.READLN.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("WRITELN", Codigo.WRITELN.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("CASE", Codigo.CASE.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("CONST", Codigo.CONST.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("FOR", Codigo.FOR.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("NOT", Codigo.NOT.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("OF", Codigo.OF.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("REPEAT", Codigo.REPEAT.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("TO", Codigo.TO.value, PALAVRA_RESERVADA));
        reserve(new ConstantesTerminais("UNTIL", Codigo.UNTIL.value, PALAVRA_RESERVADA));
    }

    private void readch() throws IOException {
        ch = (char) file.read();
    }

    private boolean readch(char c) throws IOException {
        readch();
        if (ch != c) return false;
        ch = ' ';
        return true;
    }

    public void scanAll() throws IOException {
        StringBuilder texto = new StringBuilder();
        System.out.println("|---Código---|---------Token---------|------------Descrição------------|");

        do {
            Token tok = scan();

            System.out.println("Código: " + tok.tag + "\t\t\tToken:" + tok.toString() + (tok.descricao != null ? "\t\t\t\tDescrição: " + tok.descricao : ""));
            if(tok.tag == Codigo.CARACTERE_INVALIDO.value)
                System.exit(1);

            texto.append(tok.tag + " ");

            if (tok.tag == Codigo.FIM_ARQUIVO.value || tok.tag == Codigo.CARACTERE_INVALIDO.value) break;

        } while (true);

        FileWriter fr = new FileWriter(new File(Util.RESULTADO_COMPILADOR_LEXICO));
        fr.write(texto.toString());
        fr.close();
    }

    private Token erroUnexEOF(String tipo) {
        return new ConstantesTerminais("Final de arquivo inesperado (" + tipo + ") - Linha: " + line, Codigo.FIM_ARQUIVO_INESPERADO.value);
    }

    private Token erroLexico() {
        return new ConstantesTerminais("Caracter inválido - Linha: " + line, Codigo.CARACTERE_INVALIDO.value);
    }

    /*
     * Justificativa: Realizada modificações para lançar erro ao criar variaveis com numeros na frente de letra
     * Exemplo: VAR 1111x, 1x;
     *
     * Justificativa: Corrigida identificação de sinais, como <> (erro), << (erro), <= (aceito), => (erro)
     *
     * Justificativa: Na criação dos tokens, é gerada a descrição (Palavra reservada, Identificador, Sinal de Atribuição....)
     */
    public Token scan() throws IOException {

        for (; ; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
            else if (ch == '\n') line++;
            else break;
        }
        if (!Character.isLetter(ch) && !Character.isDigit(ch)) {
            switch (ch) {
                // Operadores
                case '|':
                    if (readch('|')) return ConstantesTerminais.OR;
                    else return erroLexico();
                case ':':
                    if (readch('='))
                        return ConstantesTerminais.ATRIBUICAO;
                    else
                        return ConstantesTerminais.ANOTACAO_TIPO;
                case '!':
                    if (readch('='))
                        return ConstantesTerminais.DIFERENTE;
                    else
                        return erroLexico();
                case '&':
                    if (readch('&'))
                        return ConstantesTerminais.AND;
                    else
                        return erroLexico();
                case '=':
                    readch();
                    if (!Character.isLetter(ch) && !Character.isDigit(ch))
                        return erroLexico();
                    else
                        return ConstantesTerminais.IGUAL;
                case '<':
                    readch();
                    if (ch == '=') {
                        readch();
                        return ConstantesTerminais.MENOR_IGUAL;
                    } else if (ch == ' ')
                        return ConstantesTerminais.MENOR;
                    else
                        return erroLexico();
                case '>':
                    readch();
                    if (ch == '=') {
                        readch();
                        return ConstantesTerminais.MAIOR_IGUAL;
                    } else if (ch == ' ')
                        return ConstantesTerminais.MAIOR;
                    else
                        return erroLexico();
                case '(':
                    readch();
                    return ConstantesTerminais.PARENTESES_ABERTO;
                case ')':
                    readch();
                    return ConstantesTerminais.PARENTESES_FECHADO;
                case ',':
                    readch();
                    return ConstantesTerminais.VIRGULA;
                case '$':
                    readch();
                    return ConstantesTerminais.FIM_ARQUIVO;
                case ';':
                    readch();
                    return ConstantesTerminais.PONTO_VIRGULA;
                case '*':
                    readch();
                    int done = 0;
                    while (done == 0) {
                        readch();
                        if (ch != '*') {
                            return ConstantesTerminais.ASTERISCO;
                        }
                        if (ch == '*') {
                            done = 1;
                        }
                        if (ch == Codigo.FIM_ARQUIVO.value) {
                            return erroUnexEOF("Comentário");
                        }
                    }
                    readch();
                    return new ConstantesTerminais("Comentário", Codigo.COMENTARIO.value);
                case '+':
                    readch();
                    return ConstantesTerminais.MAIS;
                case '-':
                    readch();
                    return ConstantesTerminais.MENOS;
                case '‘': {
                    String str = "";
                    str = str + ch;
                    readch();
                    while (ch != '‘') {
                        str = str + ch;
                        readch();
                        if (ch == Codigo.FIM_ARQUIVO.value) {
                            return erroUnexEOF("Aspas");
                        }
                        if (ch == '\n') {
                            return erroLexico();
                        }

                    }
                    str = str + ch;
                    readch();
                    return new ConstantesTerminais(str, Codigo.LIT.value);
                }
                case '/': {
                    readch();
                    if (ch == '/') {
                        readch();
                        while (ch != '\n')
                            readch();
                        return new ConstantesTerminais("Comentário", Codigo.COMENTARIO.value);
                    } else return ConstantesTerminais.DIVISAO;
                }
                default:
                    return erroLexico();
            }
        }

        // Números
        if (Character.isDigit(ch)) {
            int value = 0;
            do {
                value = 10 * value + Character.digit(ch, 10);
                readch();
            } while (Character.isDigit(ch));
            if (Character.isLetter(ch))
                return erroLexico();
            return new Numero(value);
        }

        // Identificadores
        if (Character.isLetter(ch)) {
            StringBuffer sb = new StringBuffer();
            do {
                sb.append(ch);
                readch();
            } while (Character.isLetterOrDigit(ch));

            String s = sb.toString();
            ConstantesTerminais w = (ConstantesTerminais) words.get(s.toUpperCase());
            if (w != null) return w; // ja existe na hashtable ou é uma palabra reservada
            w = new ConstantesTerminais(s, Codigo.ID.value, "Identificador");
            words.put(s, w);
            return w;
        }

        // Caracteres não identificados
        if (ch != Codigo.FIM_ARQUIVO.value) {
            readch();
            return erroLexico();
        }

        Token t = new Token(ch);
        ch = ' ';
        return t;
    }
}
