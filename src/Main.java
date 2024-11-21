import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        File f = new File("GrafoParaDijkstra.csv");
        Scanner sc = new Scanner(System.in);
        int i, j, size;
        String line;
        String [] lines;
        int [][] graph;
        int [] pesos;
        byte [] select;
        String [] caminos;

        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
            java.lang.System.exit(-1);
        }

        size = sc.nextInt();
        graph = new int[size][size];
        sc.nextLine();

        for (i = 0; i < size; i++) {
            line = sc.nextLine();
            lines = line.split(",");
            for (j = 0; j < size; j++) {
                graph[i][j] = Integer.valueOf(lines[j]);
                if (graph[i][j] == -1) {
                    graph[i][j] = -1;
                }
            }
        }
        sc.close();

        dijkstra(0, graph, size);
    }

    // Inicialización del algoritmo de Dijkstra.
    public static void dijkstra(int start, int[][] graph, int size) {
        int[] pesos = new int[size]; // Almacena las distancias mínimas o más cortas
        boolean[] select = new boolean[size]; // Marca si un nodo fue procesado
        String[] caminos = new String[size]; // Almacena los caminos ya recorridos

        // Inicializar los arreglos
        for (int i = 0; i < size; i++) {
            pesos[i] = Integer.MAX_VALUE; // Distancia infinita inicial
            select[i] = false; // Ningún nodo ha sido seleccionado
            caminos[i] = ""; // Camino vacío
        }

        pesos[start] = 0; // La distancia al nodo inicial es 0
        caminos[start] = Integer.toString(start); // Camino al nodo inicial

        for (int count = 0; count < size - 1; count++) {
            int u = minDistance(pesos, select, size); // Selecciona el nodo con menor distancia
            select[u] = true; // Marca el nodo como procesado

            // Actualiza los valores de los nodos vecinos
            for (int v = 0; v < size; v++) {
                if (!select[v] && graph[u][v] != Integer.MAX_VALUE && pesos[u] != Integer.MAX_VALUE
                        && pesos[u] + graph[u][v] < pesos[v]) {
                    pesos[v] = pesos[u] + graph[u][v];
                    caminos[v] = caminos[u] + " -> " + v; // Actualiza el camino
                }
            }
        }

        // Imprime los resultados
        System.out.println("Nodo\tDistancia\tCamino");
        for (int i = 0; i < size; i++) {
            System.out.println(i + "\t" + (pesos[i] == Integer.MAX_VALUE ? "Inalcanzable" : pesos[i]) + "\t\t" + caminos[i]);
        }
    }

    // Método auxiliar para encontrar el nodo con menor distancia
    public static int minDistance(int[] pesos, boolean[] select, int size) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int i = 0; i < size; i++) {
            if (!select[i] && pesos[i] <= min) {
                min = pesos[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
}