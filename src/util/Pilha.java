package util;

public class Pilha {

    public Integer[] pilha;
    public int posicaoPilha;

    public Pilha() {
        this.posicaoPilha = -1;
        this.pilha = new Integer[1000];
    }

    public boolean pilhaVazia() {
        return this.posicaoPilha == -1;
    }

    public int tamanho() {
        if (this.pilhaVazia())
            return 0;

        return this.posicaoPilha + 1;
    }

    public Integer exibeUltimoValor() {
        if (this.pilhaVazia())
            return null;

        return this.pilha[this.posicaoPilha];
    }


    public Object desempilhar() {
        //pop
        if (pilhaVazia()) {
            return null;
        }
        return this.pilha[this.posicaoPilha--];
    }

    public void empilhar(Integer valor) {
        if (this.posicaoPilha < this.pilha.length - 1)
            this.pilha[++posicaoPilha] = valor;
    }

}