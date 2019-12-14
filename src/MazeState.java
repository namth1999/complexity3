public class MazeState {
    private Node pawnOne;
    private Node pawnTwo;
    private String path = "";
    private MazeState previousState = null;

    public MazeState(Node pawnOne, Node pawnTwo) {
        this.pawnOne = pawnOne;
        this.pawnTwo = pawnTwo;
    }

    public Node getPawnOne() {
        return pawnOne;
    }

    public Node getPawnTwo() {
        return pawnTwo;
    }

    public String getPath() {
        return path;
    }

    public void appendPath(MazeState previousState) {
        this.previousState = previousState;

        this.path += previousState.getPath() + "[" +
                previousState.getPawnOne().getPositionNr() + "," +
                previousState.getPawnTwo().getPositionNr() + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        else if (!(other instanceof MazeState)) return false;
        return this.getPawnOne().getPositionNr() == ((MazeState) other).getPawnOne().getPositionNr() &&
                this.getPawnTwo().getPositionNr() == ((MazeState) other).getPawnTwo().getPositionNr();
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.getPawnOne().getPositionNr() + "" + this.getPawnTwo().getPositionNr());
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]"
                , pawnOne.getPositionNr(), pawnTwo.getPositionNr());
    }


}
