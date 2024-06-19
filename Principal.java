import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Principal {
    private static Map<String, No> mapaDeNos = new HashMap<>();

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

        // Criar e mostrar a interface gráfica
        SwingUtilities.invokeLater(() -> {
            criarInterfaceGrafica();
        });
    }

    private static void criarInterfaceGrafica() {
        JFrame frame = new JFrame("Grafo Unidirecional + Dijkstra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 320);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel origemLabel = new JLabel("Nó de Origem:");
        JTextField origemField = new JTextField();
        JLabel destinoLabel = new JLabel("Nó de Destino:");
        JTextField destinoField = new JTextField();
        JButton calcularButton = new JButton("Calcular");
        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);

        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origemNome = origemField.getText();
                String destinoNome = destinoField.getText();
                No origem = mapaDeNos.get(origemNome);
                No destino = mapaDeNos.get(destinoNome);
                StringBuilder resultado = new StringBuilder();

                if (origem == null || destino == null) {
                    resultado.append("Nó de origem ou destino inválido.");
                } else {
                    Dijkstra.calculaMenorCaminho(origem, destino, resultado);
                }

                resultadoArea.setText(resultado.toString());
            }
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
}