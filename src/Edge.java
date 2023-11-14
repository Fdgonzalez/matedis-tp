import java.util.List;

public class Edge<T>{
    private final T firstVertex;
    private final T secondVertex;

    private final int weight;

    public Edge(T firstVertex, T secondVertex, int weight) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.weight = weight;
    }

    public T getFirstVertex() {
        return firstVertex;
    }

    public T getSecondVertex() {
        return secondVertex;
    }

    public boolean contains(T x){
        return firstVertex == x || secondVertex == x;
    }

    public boolean containsAny(List<T> vertexes){
        return vertexes.stream().anyMatch(this::contains);
    }

    public int getWeight() {
        return weight;
    }

    public T getOtherVertex(T x){
        if (firstVertex == x)
            return secondVertex;
        else if (secondVertex == x)
            return firstVertex;
        return null;
    }
}
