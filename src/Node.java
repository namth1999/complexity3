import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Node implements Comparable<Node> {
    private int positionNr;
    private Color color;
    private HashSet<Edge> edges = new HashSet<>();

    public Node(int positionNr, Color color) {
        this.positionNr = positionNr;
        this.color = color;
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

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

    @Override
    public int compareTo(Node otherNode) {
        return this.getPositionNr() < otherNode.getPositionNr() ? -1 : 0;
    }

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

    @Override
    public String toString() {
        StringBuilder nodeStr = new StringBuilder();
        for (Edge edge : edges) {
            nodeStr.append(String.format("%s_%s ---%s---> %s_%s\n", positionNr, colorToString(color), colorToString(edge.getColor()), edge.getDestination().getPositionNr(), colorToString(edge.getDestination().getColor())));
        }
        return nodeStr.toString();
    }
}
