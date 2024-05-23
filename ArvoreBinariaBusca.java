import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArvoreBinariaBusca {
    private Node raiz;

    public ArvoreBinariaBusca() {
        raiz = null;
    }

    public Node getRaiz() {
        return raiz;
    }

    public void inserir(String palavra) {
        raiz = inserirRec(raiz, palavra);
    }

    private Node inserirRec(Node raiz, String palavra) {
        if (raiz == null) {
            raiz = new Node(new Palavra(palavra));
            return raiz;
        }

        int comparacao = palavra.compareTo(raiz.palavra.getPalavra());
        if (comparacao < 0) {
            raiz.esquerda = inserirRec(raiz.esquerda, palavra);
        } else if (comparacao > 0) {
            raiz.direita = inserirRec(raiz.direita, palavra);
        } else {
            raiz.palavra.incrementarOcorrencias();
        }
        return raiz;
    }

    public Palavra buscar(String palavra) {
        return buscarRec(raiz, palavra);
    }

    private Palavra buscarRec(Node raiz, String palavra) {
        if (raiz == null) {
            return null;
        }

        int comparacao = palavra.compareTo(raiz.palavra.getPalavra());
        if (comparacao < 0) {
            return buscarRec(raiz.esquerda, palavra);
        } else if (comparacao > 0) {
            return buscarRec(raiz.direita, palavra);
        } else {
            return raiz.palavra;
        }
    }

    public void exibirEmOrdem() {
        exibirEmOrdemRec(raiz);
    }

    private void exibirEmOrdemRec(Node raiz) {
        if (raiz != null) {
            exibirEmOrdemRec(raiz.esquerda);
            System.out.println(raiz.palavra);
            exibirEmOrdemRec(raiz.direita);
        }
    }

    public int contarNos() {
        return contarNosRec(raiz);
    }

    private int contarNosRec(Node raiz) {
        if (raiz == null) {
            return 0;
        } else {
            return 1 + contarNosRec(raiz.esquerda) + contarNosRec(raiz.direita);
        }
    }

    public int contarOcorrencias() {
        return contarOcorrenciasRec(raiz);
    }

    private int contarOcorrenciasRec(Node raiz) {
        if (raiz == null) {
            return 0;
        } else {
            return raiz.palavra.getOcorrencias() + contarOcorrenciasRec(raiz.esquerda) + contarOcorrenciasRec(raiz.direita);
        }
    }

    public String palavraMaisLonga() {
        return palavraMaisLongaRec(raiz, "");
    }

    private String palavraMaisLongaRec(Node raiz, String maisLonga) {
        if (raiz == null) {
            return maisLonga;
        }
        if (raiz.palavra.getPalavra().length() > maisLonga.length()) {
            maisLonga = raiz.palavra.getPalavra();
        }
        maisLonga = palavraMaisLongaRec(raiz.esquerda, maisLonga);
        maisLonga = palavraMaisLongaRec(raiz.direita, maisLonga);
        return maisLonga;
    }

    public List<Palavra> coletarPalavras() {
        List<Palavra> palavras = new ArrayList<>();
        coletarPalavrasRec(raiz, palavras);
        return palavras;
    }

    private void coletarPalavrasRec(Node raiz, List<Palavra> palavras) {
        if (raiz != null) {
            coletarPalavrasRec(raiz.esquerda, palavras);
            palavras.add(raiz.palavra);
            coletarPalavrasRec(raiz.direita, palavras);
        }
    }

    public List<Palavra> palavrasMaisFrequentes() {
        List<Palavra> palavras = coletarPalavras();
        Collections.sort(palavras, Comparator.comparingInt(Palavra::getOcorrencias).reversed());
        return palavras;
    }
}
