import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * This class representing a Node.
 */
public class Node implements Comparable<Node> {
    private int positionNr;
    private Color color;
    private HashSet<Edge> edges = new HashSet<>();

    /**
     * Constructor for creating an edge.
     *
     * @param positionNr the positionNr for this node
     * @param color      the color of this node
     */
    public Node(int positionNr, Color color) {
        this.positionNr = positionNr;
        this.color = color;
    }

    /**
     * Add an edge to this node.
     *
     * @param edge edge to be added
     */
    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    /**
     * Getter
     */
    public List<Edge> getEdgesForColor(Color color) {
        List<Edge> sortedEdges = new ArrayList<>();

        for (Edge edge : edges) {
            if (edge.getColor() == color) {
                sortedEdges.add(edge);
            }
        }

        Collections.sort(sortedEdges);
        return sortedEdges;
    }

    public int getPositionNr() {
        return positionNr;
    }


    public Color getColor() {
        return color;
    }

    /**
     * Comparing 2 nodes is based on if one positionNr comes before the other.
     *
     * @param otherNode the node to compare to
     * @return -1 if this position is smaller otherwise 1
     */
    @Override
    public int compareTo(Node otherNode) {
        return this.getPositionNr() < otherNode.getPositionNr() ? -1 : 1;
    }

    /**
     * Convert a color to string
     * @param color color to be converted
     * @return
     */
    public String colorToString(Color color) {
        String c = "";
        switch (color) {
            case BLUE:
                c = "BLUE";
                break;
            case BLACK:
                c = "BLACK";
                break;
            case GREEN:
                c = "GREEN";
                break;
            case ORANGE:
                c = "ORANGE";
                break;
            case PURPLE:
                c = "PURPLE";
                break;
            default:
                throw new IllegalArgumentException("Unknown color " + color);
        }
        return c;
    }

    /**
     * Convert information of a node to string
     * @return string contains node information
     */
    @Override
    public String toString() {
        StringBuilder nodeStr = new StringBuilder();
        for (Edge edge : edges) {
            nodeStr.append(String.format("%s_%s ---%s---> %s_%s\n", positionNr, colorToString(color), colorToString(edge.getColor()), edge.getDestination().getPositionNr(), colorToString(edge.getDestination().getColor())));
        }
        return nodeStr.toString();
    }
}
