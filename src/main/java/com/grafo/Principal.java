package com.grafo;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.ext.JGraphXAdapter;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Principal {
    private static Map<String, No> mapaDeNos = new HashMap<>();
    private static Graph<No, DefaultWeightedEdge> grafo;

    public static void main(String[] args) {
        // Criar os nós do grafo
        No Palmas = new No();
        Palmas.conteudo = "Palmas";

        No Porto = new No();
        Porto.conteudo = "Porto Nacional";

        No Gurupi = new No();
        Gurupi.conteudo = "Gurupi";

        No Brejinho = new No();
        Brejinho.conteudo = "Brejinho";

        No Paraiso = new No();
        Paraiso.conteudo = "Paraíso";

        // Adicionar as arestas
        Palmas.adicionaVizinho(Porto, 8);
        Palmas.adicionaVizinho(Paraiso, 10);

        Porto.adicionaVizinho(Gurupi, 5);
        Porto.adicionaVizinho(Brejinho, 4);

        Gurupi.adicionaVizinho(Paraiso, 20);
        Gurupi.adicionaVizinho(Brejinho, 11);

        Brejinho.adicionaVizinho(Porto, 4);
        Brejinho.adicionaVizinho(Gurupi, 11);

        // Adicionar nós ao mapa
        mapaDeNos.put(Palmas.conteudo, Palmas);
        mapaDeNos.put(Porto.conteudo, Porto);
        mapaDeNos.put(Gurupi.conteudo, Gurupi);
        mapaDeNos.put(Brejinho.conteudo, Brejinho);
        mapaDeNos.put(Paraiso.conteudo, Paraiso);

        // Criar o grafo
        grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // Adicionar vértices e arestas ao grafo
        grafo.addVertex(Palmas);
        grafo.addVertex(Porto);
        grafo.addVertex(Gurupi);
        grafo.addVertex(Brejinho);
        grafo.addVertex(Paraiso);

        addEdgeToGraph(grafo, Palmas, Porto, 8);
        addEdgeToGraph(grafo, Palmas, Paraiso, 10);
        addEdgeToGraph(grafo, Porto, Gurupi, 5);
        addEdgeToGraph(grafo, Porto, Brejinho, 4);
        addEdgeToGraph(grafo, Gurupi, Paraiso, 20);
        addEdgeToGraph(grafo, Gurupi, Brejinho, 11);
        addEdgeToGraph(grafo, Brejinho, Porto, 4);
        addEdgeToGraph(grafo, Brejinho, Gurupi, 11);

        // Criar e mostrar a interface gráfica
        SwingUtilities.invokeLater(() -> {
            criarInterfaceGrafica();
        });
    }

    private static void addEdgeToGraph(Graph<No, DefaultWeightedEdge> grafo, No source, No target, double weight) {
        DefaultWeightedEdge edge = grafo.addEdge(source, target);
        if (edge != null) {
            grafo.setEdgeWeight(edge, weight);
        }
    }

    private static void criarInterfaceGrafica() {
        JFrame frame = new JFrame("Algoritmo de Dijkstra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel origemLabel = new JLabel("Nó de Origem:");
        JTextField origemField = new JTextField();
        JLabel destinoLabel = new JLabel("Nó de Destino:");
        JTextField destinoField = new JTextField();
        JButton calcularButton = new JButton("Calcular");
        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);

        calcularButton.addActionListener(e -> {
            String origemNome = origemField.getText();
            String destinoNome = destinoField.getText();
            No origem = mapaDeNos.get(origemNome);
            No destino = mapaDeNos.get(destinoNome);
            StringBuilder resultado = new StringBuilder();

            if (origem == null || destino == null) {
                resultado.append("Nó de origem ou destino inválido.");
            } else {
                Map<No, No> predecessores = new HashMap<>();
                Dijkstra.calculaMenorCaminho(origem, destino, resultado, predecessores);
                exibirGrafo(grafo, origem, destino, predecessores);
            }

            resultadoArea.setText(resultado.toString());
        });

        panel.add(origemLabel);
        panel.add(origemField);
        panel.add(destinoLabel);
        panel.add(destinoField);
        panel.add(calcularButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void exibirGrafo(Graph<No, DefaultWeightedEdge> grafo, No origem, No destino, Map<No, No> predecessores) {
        JFrame frame = new JFrame("Visualização do Grafo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JGraphXAdapter<No, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<>(grafo);
        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        frame.add(graphComponent);

        // Ajustar o estilo dos vértices e arestas
        graphAdapter.setCellStyles(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE, graphAdapter.getChildVertices(graphAdapter.getDefaultParent()));
        graphAdapter.setCellStyles(mxConstants.STYLE_FONTSIZE, "16", graphAdapter.getChildVertices(graphAdapter.getDefaultParent()));
        graphAdapter.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "3", graphAdapter.getChildEdges(graphAdapter.getDefaultParent()));
        graphAdapter.setCellStyles(mxConstants.STYLE_FONTCOLOR, "brown", graphAdapter.getChildVertices(graphAdapter.getDefaultParent()));
        graphAdapter.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#d3d3d3", graphAdapter.getChildVertices(graphAdapter.getDefaultParent()));

        // Ajustar tamanho dos vértices
        Object[] vertices = graphAdapter.getChildVertices(graphAdapter.getDefaultParent());
        for (Object vertex : vertices) {
            mxGraph graph = graphComponent.getGraph();
            mxCell cell = (mxCell) vertex;
            graph.getModel().setGeometry(cell, new mxGeometry(cell.getGeometry().getX(), cell.getGeometry().getY(), 200, 100));
        }

        // Define layout
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        frame.setVisible(true);

        // Destacar o caminho encontrado
        GraphPath<No, DefaultWeightedEdge> path = DijkstraShortestPath.findPathBetween(grafo, origem, destino);
        if (path != null) {
            for (DefaultWeightedEdge edge : path.getEdgeList()) {
                graphAdapter.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", new Object[]{graphAdapter.getEdgeToCellMap().get(edge)});
                graphAdapter.setCellStyles(mxConstants.STYLE_STROKEWIDTH, "3", new Object[]{graphAdapter.getEdgeToCellMap().get(edge)});
            }
        }
    }
}
