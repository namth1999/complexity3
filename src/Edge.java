public class Edge implements Comparable<Edge> {
    private Node destination;
    private Color color;

    public Edge(Node destination, Color color) {
        this.destination = destination;
        this.color = color;
    }

    public Node getDestination() {
        return destination;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int compareTo(Edge otherEdge) {
        return this.destination.getPositionNr() < otherEdge.getDestination().getPositionNr() ? -1 : 0;
    }
}

