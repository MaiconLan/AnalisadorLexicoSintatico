package compilador;

import lexico.Lexico;
import sintatico.Sintatico;
import util.Util;

import java.io.IOException;

public class Compilador {

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Lexico lex = new Lexico("file2.txt");
        lex.scanAll();

        Sintatico sintatico = new Sintatico(Util.RESULTADO_COMPILADOR_LEXICO);
        sintatico.scanAll();
    }

}