package com.grafo;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Dijkstra {
    public static void calculaMenorCaminho(No inicio, No destino, StringBuilder resultado, Map<No, No> predecessores) {
        Map<No, Integer> distancias = new HashMap<>();
        PriorityQueue<NoCusto> pq = new PriorityQueue<>((a, b) -> a.custo - b.custo);

        distancias.put(inicio, 0);
        pq.add(new NoCusto(inicio, 0));

        while (!pq.isEmpty()) {
            NoCusto atual = pq.poll();
            No noAtual = atual.no;

            for (Aresta aresta : noAtual.vizinhos) {
                No vizinho = aresta.no;
                int novaDistancia = distancias.get(noAtual) + aresta.custo;

                if (novaDistancia < distancias.getOrDefault(vizinho, Integer.MAX_VALUE)) {
                    distancias.put(vizinho, novaDistancia);
                    predecessores.put(vizinho, noAtual);
                    pq.add(new NoCusto(vizinho, novaDistancia));
                }
            }
        }

        // Imprimir a distância e o caminho
        int distanciaFinal = distancias.getOrDefault(destino, Integer.MAX_VALUE);
        resultado.append("Distância de ").append(inicio.conteudo).append(" para ").append(destino.conteudo).append(" é ").append(distanciaFinal);
        resultado.append(" | Caminho: ");
        if (distanciaFinal == Integer.MAX_VALUE) {
            resultado.append("Nenhum caminho");
        } else {
            imprimeCaminho(inicio, destino, predecessores, resultado);
        }
    }

    private static void imprimeCaminho(No inicio, No destino, Map<No, No> predecessores, StringBuilder resultado) {
        Stack<No> caminho = new Stack<>();
        No passo = destino;
        caminho.push(passo);
        while (predecessores.get(passo) != null) {
            passo = predecessores.get(passo);
            caminho.push(passo);
        }
        while (!caminho.isEmpty()) {
            resultado.append(caminho.pop().conteudo);
            if (!caminho.isEmpty()) {
                resultado.append(" -> ");
            }
        }
    }
}
