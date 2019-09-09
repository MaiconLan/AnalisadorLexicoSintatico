package sintatico;

import util.Mensagem;
import util.Pilha;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Sintatico {

    private FileReader file = null;
    private BufferedReader reader;
    private String line;
    private int linhaErro = 1;

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

    public void scanAll() throws IOException {
        Pilha simbolos = novaPilha();
        Pilha entrada = new Pilha();

        do {
            readLine();
            String[] teste = line.split(" ");
            for (int i = teste.length - 1; i >= 0; i--) {
                entrada.empilhar(Integer.valueOf(teste[i]));
            }

            do {
                int simboloTopoPilha = simbolos.exibeUltimoValor();

                while (simboloTopoPilha == Constants.EPSILON) {
                    simbolos.desempilhar();
                    simboloTopoPilha = simbolos.exibeUltimoValor();
                }

                if (isTerminal(simboloTopoPilha) || simbolos.pilhaVazia()) {
                    if (simboloTopoPilha == entrada.exibeUltimoValor()) {
                        System.out.println("Desempilhando: \n" + simbolos.desempilhar() + "\n" + entrada.desempilhar() + "\n\n");

                    } else {
                        Mensagem.mensagem("Ocorreu um erro na linha: " + linhaErro);
                        System.exit(0);
                    }

                } else if (isNaoTerminal(simboloTopoPilha)) {
                    if (estaNaMatrizParse(simboloTopoPilha, entrada.exibeUltimoValor())) {
                        simbolos.desempilhar();
                        int idMatrizParse = obterMatrizParse(simboloTopoPilha, entrada.exibeUltimoValor());

                        int[] regrasProducao = obterRegrasProducao(idMatrizParse);

                        for (int i = regrasProducao.length - 1; i >= 0; i--) {
                            simbolos.empilhar(regrasProducao[i]);
                        }
                        //simbolos.desempilhar();

                    } else {
                        Mensagem.mensagem("Erro novamente na linha: " + linhaErro);
                        System.exit(0);
                    }

                } else {
                    Mensagem.mensagem("Não implementado: " + linhaErro);
                    System.exit(0);
                }

            } while (!simbolos.pilhaVazia());
            linhaErro++;
        } while (line != null);
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
        return token < Constants.FIRST_NON_TERMINAL && token > Constants.DOLLAR;
    }

    private boolean isNaoTerminal(int token) {
        return token >= Constants.FIRST_NON_TERMINAL && token < Constants.FIRST_SEMANTIC_ACTION;
    }
}
