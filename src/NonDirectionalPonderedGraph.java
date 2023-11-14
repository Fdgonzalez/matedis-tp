import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NonDirectionalPonderedGraph<T> implements Graph<T> {
    private final List<Edge<T>> edges;
    private final List<T> vertices;

    public NonDirectionalPonderedGraph() {
        this.edges = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }

    @Override
    public void addVertex(T x) {
        if (!hasVertex(x)) {
            vertices.add(x);
        }
        // Orden N (por la llamada a hasVertex)
    }

    @Override
    public void removeVertex(T x) {
        vertices.remove(x);
        edges.removeIf(edge -> edge.contains(x));
        // Orden N
    }

    @Override
    public boolean hasVertex(T v) {
        return vertices.contains(v);
        // Orden N
    }

    @Override
    public List<T> getVertices() {
        return vertices;
        // Orden 1
    }

    @Override
    public void addEdge(T v, T w, int weight) {
        Edge<T> edgeToAdd = new Edge<>(v, w, weight);
        edges.add(edgeToAdd);
        addVertex(v);
        addVertex(w);
    }  // Orden 1

    @Override
    public void removeEdge(T v, T w) {
        edges.removeIf(edge -> edge.contains(v) && edge.contains(w));
    } // Orden N

    @Override
    public boolean hasEdge(T v, T w) {
        for (Edge<T> edge : edges)
            if (edge.contains(v) && edge.contains(w)) {
                return true;
            }
        return false;
    } // Orden N

    @Override
    public int order() {
        return edges.size();
    } // Orden 1 (Arraylist guarda y devuelve el size)

    @Override
    public int alpha() {
        // Este algoritmo es eager, no es garantizado que encontramos el set mas grande de vetices independientes
        List<T> independents = new ArrayList<>();
        for (Edge<T> edge : edges) {
            boolean connected = false;
            for (T independent : independents) {
                if (edge.contains(independent))
                    connected = true;
            }
            if (!connected)
                independents.add(edge.getFirstVertex());
        }
        return independents.size();
    } // Orden N^2?

    @Override
    public List<T> getAdjacencyList(T v) {
        return edges.stream().map(edge -> edge.getOtherVertex(v)).filter(Objects::nonNull).collect(Collectors.toList());
    } // Orden N

    public Graph<T> prim() {
        Graph<T> tree = new NonDirectionalPonderedGraph<>();
        // Start with a random vertex
        T startingVertex = vertices.stream().findFirst().orElseThrow(() -> new RuntimeException("Empty graph, can't run prim"));
        tree.addVertex(startingVertex);
        int vertexes = vertices.size();
        int added = 1; // Save how many vertexes we added to stop when we add all vertexes
        List<Edge<T>> nonAddedEdges = new ArrayList<>(edges);
        while (added < vertexes) {
            int minWeight = Integer.MAX_VALUE;
            Edge<T> minEdge = null;
            List<T> verticesInTree = tree.getVertices();
            for (Edge<T> edge : nonAddedEdges) {
                // Skip this edge if it's not connected to the result tree
                if (!edge.containsAny(verticesInTree))
                    continue;
                // Get the edge with the minimum weight
                int weight = edge.getWeight();
                if (weight < minWeight){
                    minWeight = weight;
                    minEdge = edge;
                }
            }
            if (minEdge == null)
                break;
            // Add both vertexes (One is already in here, but we won't double add it)
            tree.addVertex(minEdge.getFirstVertex());
            tree.addVertex(minEdge.getSecondVertex());
            tree.addEdge(minEdge.getFirstVertex(),minEdge.getSecondVertex(),minEdge.getWeight());
            nonAddedEdges.remove(minEdge);
            added += 1;
        }
        return tree;
    } // Orden aristas * log (vertices)
}
