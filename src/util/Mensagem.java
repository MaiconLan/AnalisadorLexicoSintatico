package util;

import javax.swing.*;

public class Mensagem {

    public static void mensagem(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public static String input(String mensagem){
        return JOptionPane.showInputDialog(mensagem);
    }

}
