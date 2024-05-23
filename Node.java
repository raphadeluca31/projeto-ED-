public class Node {
    Palavra palavra;
    Node esquerda, direita;

    public Node(Palavra palavra) {
        this.palavra = palavra;
        esquerda = direita = null;
    }
}
