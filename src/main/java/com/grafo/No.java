package com.grafo;

import java.util.ArrayList;

public class No {
    public String conteudo;
    public ArrayList<Aresta> vizinhos = new ArrayList<>();

    public void adicionaVizinho(No novoVizinho, int custo) {
        vizinhos.add(new Aresta(novoVizinho, custo));
    }

    @Override
    public String toString() {
        return conteudo;
    }
}
