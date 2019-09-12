package sintatico;

import util.Pilha;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Sintatico {

    private FileReader file = null;
    private BufferedReader reader;
    private String line;

    public Sintatico(String fileName) throws FileNotFoundException {
        try {
            file = new FileReader(fileName);
            reader = new BufferedReader(file);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            throw e;
        }
    }

    private void readLine() throws IOException {
        line = reader.readLine();
    }

    public void scanAll() throws IOException, AnalisadorSintaticoException {
        Pilha simbolos = novaPilha();
        Pilha entrada = new Pilha();

        readLine();
        String[] arrayEntrada = line.split(" ");
        for (int i = arrayEntrada.length - 1; i >= 0; i--) {
            entrada.empilhar(Integer.valueOf(arrayEntrada[i]));
        }

        do {
            int simboloTopoPilha = simbolos.exibeUltimoValor();

            while (simboloTopoPilha == Constants.EPSILON) {
                simbolos.desempilhar();
                simboloTopoPilha = simbolos.exibeUltimoValor();
            }

            if (isTerminal(simboloTopoPilha) || simbolos.pilhaVazia()) {
                if (simboloTopoPilha == entrada.exibeUltimoValor()) {
                    System.out.println("Desempilhando: " + simbolos.desempilhar() + "-" + entrada.desempilhar() + "\n");

                } else {
                    lancarErro(simboloTopoPilha, entrada.exibeUltimoValor());
                }

            } else if (isNaoTerminal(simboloTopoPilha)) {
                if (estaNaMatrizParse(simboloTopoPilha, entrada.exibeUltimoValor())) {
                    simbolos.desempilhar();
                    int idMatrizParse = obterMatrizParse(simboloTopoPilha, entrada.exibeUltimoValor());

                    int[] regrasProducao = obterRegrasProducao(idMatrizParse);

                    for (int i = regrasProducao.length - 1; i >= 0; i--) {
                        simbolos.empilhar(regrasProducao[i]);
                    }

                } else {
                    for (int i = 0; i < simbolos.tamanho(); i++) {
                        System.out.println(simbolos.pilha[i]);
                    }
                    lancarErro(simboloTopoPilha, entrada.exibeUltimoValor());
                }

            } else {
                lancarErro(simboloTopoPilha, entrada.exibeUltimoValor());

            }

        } while (!simbolos.pilhaVazia());

        System.err.println("\nSimbolos");
        for (Integer integer : simbolos.pilha) {
            if (integer != null)
                System.err.println(integer + " ");
        }

        System.err.println("\nEntrada");
        for (Integer integer : entrada.pilha) {
            if (integer != null)
                System.err.println(integer + " ");
        }
    }

    private void lancarErro(int simboloEsperado, int simboloRecebido) throws AnalisadorSintaticoException {
        throw new AnalisadorSintaticoException(ParserConstants.PARSER_ERROR[simboloEsperado] + ", era esperado " + simboloEsperado + " porém foi recebido  " + simboloRecebido);
    }

    private int[] obterRegrasProducao(int idMatrizParse) {
        return ParserConstants.PRODUCTIONS[idMatrizParse];
    }

    private boolean estaNaMatrizParse(int X, int a) {
        return obterMatrizParse(X, a) >= 0;
    }

    private int obterMatrizParse(int X, int a) {
        return Constants.PARSER_TABLE[X - ParserConstants.START_SYMBOL][a - 1];
    }

    private Pilha novaPilha() {
        Pilha pilha = new Pilha();
        pilha.empilhar(Constants.DOLLAR);
        pilha.empilhar(ParserConstants.START_SYMBOL);
        pilha.empilhar(Constants.EPSILON);
        return pilha;
    }

    private boolean isTerminal(int token) {
        return token > Constants.DOLLAR && token < Constants.FIRST_NON_TERMINAL;
    }

    private boolean isNaoTerminal(int token) {
        return token >= Constants.FIRST_NON_TERMINAL && token < Constants.FIRST_SEMANTIC_ACTION;
    }
}
