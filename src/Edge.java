/**
 * This class representing an edge between two nodes.
 */
public class Edge implements Comparable<Edge> {
    private Node destination;
    private Color color;

    /**
     * Constructor for creating an edge.
     *
     * @param destination the Node to where this edge goes
     * @param color  the color of the edge
     */
    public Edge(Node destination, Color color) {
        this.destination = destination;
        this.color = color;
    }

    /**
     * Getter
     */
    public Node getDestination() {
        return destination;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Compare 2 edges. Use to sort the collection of edges
     * @param otherEdge
     * @return -1 if position number of destination is smaller than other node, else 1
     */
    @Override
    public int compareTo(Edge otherEdge) {
        return this.destination.getPositionNr() < otherEdge.getDestination().getPositionNr() ? -1 : 1;
    }
}

