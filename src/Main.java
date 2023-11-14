public class Main {
    public static void main(String[] args) {
        NonDirectionalPonderedGraph<Integer> test = new NonDirectionalPonderedGraph<>();
        test.addVertex(1);
        test.addVertex(2);
        test.addVertex(3);
        test.addVertex(4);
        test.addVertex(5);
        test.addVertex(6);
        test.addVertex(7);
        test.addEdge(6, 1, 10);
        test.addEdge(1,2,28);
        test.addEdge(2,3,16);
        test.addEdge(3,4,12);
        test.addEdge(4,5,22);
        test.addEdge(5,6,25);
        test.addEdge(5,7,24);
        test.addEdge(7,4,18);
        test.addEdge(7,2,14);

        Graph<Integer> minimumSpanningTree = test.prim();
        assert minimumSpanningTree.getVertices().size() == test.getVertices().size();
        assert minimumSpanningTree.order() < test.order();
    }
}