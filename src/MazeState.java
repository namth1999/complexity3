/**
 * Class representing a state in the puzzle. A state contains of 2 node where two pawn placed.
 */
public class MazeState {
    private Node pawnOne;
    private Node pawnTwo;
    private String path = "";

    /**
     * Constructor for a maze state.
     *
     * @param pawnOne pawn one node belonging to this state
     * @param pawnTwo pawn two node belonging to this state
     */
    public MazeState(Node pawnOne, Node pawnTwo) {
        this.pawnOne = pawnOne;
        this.pawnTwo = pawnTwo;
    }

    /**
     * Getter
     * @return
     */
    public Node getPawnOne() {
        return pawnOne;
    }

    public Node getPawnTwo() {
        return pawnTwo;
    }

    public String getPath() {
        return path;
    }

    /**
     * Append the path to get to this state base on the previous state
     * @param path
     */
    public void setPath(String path) {

        this.path = path;
    }

    /**
     * Check if two states is equal or not
     * @param other the other state
     * @return true if pawnOne and pawnTwo position is the same otherwise false
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        else if (!(other instanceof MazeState)) return false;
        return this.getPawnOne().getPositionNr() == ((MazeState) other).getPawnOne().getPositionNr() &&
                this.getPawnTwo().getPositionNr() == ((MazeState) other).getPawnTwo().getPositionNr();
    }

    /**
     * Get the hashCode for this state. Hashcode is based of the identifiers of the two pawn nodes.
     * Because we override equals, the hashCode need to be overridden in order for the hashSet to function correctly
     * @return the hashCode as int
     */
    @Override
    public int hashCode() {
        return Integer.parseInt(this.getPawnOne().getPositionNr() + "" + this.getPawnTwo().getPositionNr());
    }

    /**
     * Convert information of a maze state to string
     * @return string contains maze state information
     */
    @Override
    public String toString() {
        return String.format("[%s,%s]"
                , pawnOne.getPositionNr(), pawnTwo.getPositionNr());
    }


}
