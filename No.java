import java.util.ArrayList;

class No {
    String conteudo;
    ArrayList<Aresta> vizinhos = new ArrayList<>();

    void adicionaVizinho(No novoVizinho, int custo) {
        vizinhos.add(new Aresta(novoVizinho, custo));
    }
}
