import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ArvoreBinariaBusca arvore = new ArvoreBinariaBusca();
    private static List<String> linhasMusica = new ArrayList<>();
    private static int totalLinhas = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1- Carregar o texto");
            System.out.println("2- Estatísticas");
            System.out.println("3- Busca por palavra");
            System.out.println("4- Exibição do texto");
            System.out.println("5- Palavras mais frequentes");
            System.out.println("6- Encerrar");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    if (carregarTexto("musica.txt")) {
                        System.out.println("TEXTO CARREGADO");
                    } else {
                        System.out.println("FALHA AO CARREGAR O TEXTO");
                    }
                    break;
                case 2:
                    exibirEstatisticas();
                    break;
                case 3:
                    System.out.print("Digite a palavra a ser buscada: ");
                    String palavra = scanner.nextLine().toLowerCase();
                    Palavra resultado = arvore.buscar(palavra);
                    if (resultado != null) {
                        System.out.println("A palavra '" + palavra + "' apareceu " + resultado.getOcorrencias() + " vezes.");
                    } else {
                        System.out.println("Palavra não encontrada.");
                    }
                    break;
                case 4:
                    arvore.exibirEmOrdem();
                    break;
                case 5:
                    exibirPalavrasMaisFrequentes();
                    break;
                case 6:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static boolean carregarTexto(String arquivo) {
        totalLinhas = 0;
        arvore = new ArvoreBinariaBusca(); // Resetar a árvore
        linhasMusica.clear(); // Limpar a lista de linhas da música
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                totalLinhas++;
                linhasMusica.add(linha); // Armazenar a linha na lista
                String[] palavras = linha.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                for (String palavra : palavras) {
                    arvore.inserir(palavra);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void exibirEstatisticas() {
        int totalPalavras = arvore.contarOcorrencias();
        int totalPalavrasDistintas = arvore.contarNos();
        String palavraMaisLonga = arvore.palavraMaisLonga();

        System.out.println("Total de linhas no texto: " + totalLinhas);
        System.out.println("Total de palavras no texto: " + totalPalavras);
        System.out.println("Total de palavras distintas no texto: " + totalPalavrasDistintas);
        System.out.println("Palavra mais longa do texto: " + palavraMaisLonga);
    }

    private static void exibirFrequenciaLetras() {
        int[] frequencia = new int[26];
        calcularFrequenciaLetras(arvore.getRaiz(), frequencia);
        for (int i = 0; i < frequencia.length; i++) {
            System.out.println((char) (i + 'a') + ": " + frequencia[i]);
        }
    }

    private static void calcularFrequenciaLetras(Node raiz, int[] frequencia) {
        if (raiz != null) {
            for (char c : raiz.palavra.getPalavra().toCharArray()) {
                if (c >= 'a' && c <= 'z') {
                    frequencia[c - 'a']++;
                }
            }
            calcularFrequenciaLetras(raiz.esquerda, frequencia);
            calcularFrequenciaLetras(raiz.direita, frequencia);
        }
    }

    private static void gerarNovaArvore(String arquivo) {
        ArvoreBinariaBusca novaArvore = new ArvoreBinariaBusca();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] palavras = linha.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
                for (String palavra : palavras) {
                    novaArvore.inserir(palavra);
                }
            }
            System.out.println("NOVA ÁRVORE BINÁRIA DE BUSCA GERADA");
            novaArvore.exibirEmOrdem();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FALHA AO CARREGAR O TEXTO");
        }
    }

    private static void exibirPalavrasMaisFrequentes() {
        List<Palavra> palavras = arvore.palavrasMaisFrequentes();
        for (Palavra p : palavras) {
            System.out.println(p);
        }
    }
}
