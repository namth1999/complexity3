import java.util.*;

/**
 * This class fully represents the puzzle graph
 */
public class MazeGraph {

    private List<Node> nodesList = new ArrayList<>();
    private Node goalNode;
    private int nrOfStatesCreated;

    /**
     * Find all possible solutions for the puzzle based on a specified start state and the goal node
     *
     * @param startState the state to start from
     * @param goalNode   the goal node to reach
     * @return a list containing solutions
     */
    public ArrayList<LinkedList<MazeState>> findSolutions(MazeState startState, Node goalNode) {
        nrOfStatesCreated = 0;
        Set<String> successPaths = new HashSet<>();
        ArrayList<LinkedList<MazeState>> solutions = new ArrayList<>();
        LinkedList<MazeState> solution;

        this.setGoalNode(goalNode);
        boolean completelySolved = false;
        while (!completelySolved) {
            Set<MazeState> visitedState = new HashSet<>();
            solution = this.dfsBacktrack(startState, visitedState, successPaths);

            // a result has been found
            // add the last state path to the forbidden paths
            // this path represents how we got to this result and will be used
            // so that we will not get this result again
            if (solution.size() > 0) {
                successPaths.add(solution.getLast().getPath());
                solutions.add(solution);
            } else {
                completelySolved = true;
            }
        }
        ;

        return solutions;
    }

    /**
     * Depth first search the graph and backtrack if a result has been found.
     *
     * @param startState   the start state to start from
     * @param visitedState the states that have already been visited
     * @param successPaths paths of successful solutions
     * @return a solution as a list of state led to goal node
     */
        public LinkedList<MazeState> dfsBacktrack(MazeState startState, Set<MazeState> visitedState, Set<String> successPaths) {
        LinkedList<MazeState> solutionStates;
        // the visitedStates prevents that we do not get cycles
        visitedState.add(startState);
        nrOfStatesCreated++;

        // if the current startState we check is the desired state and if the path that led to this startState
        // has not already been added as a solution
        if (isGoalState(startState) && !successPaths.contains(startState.getPath())) {
            solutionStates = new LinkedList<>();
            // add this startState to the solutionStates
            // now we can start the backtracking process
            // we return here and path that led to this point is built using recursion
            solutionStates.add(startState);
            return solutionStates;

        }

        // get all the possible states this first state node can go to
        // based on the current startState
        List<MazeState> movePawnOneLeadToStates = this.getLeadToStates(startState, Pawn.ONE);

        for (int i = 0; i < movePawnOneLeadToStates.size(); i++) {
            // append the startState path to this neighbourState
            // this way we know how we got to this neighbourState
            String pathToState = startState.getPath() + "[" +
                    startState.getPawnOne().getPositionNr() + "," +
                    startState.getPawnTwo().getPositionNr() + "]";

            movePawnOneLeadToStates.get(i).setPath(pathToState);

            // if we have not already visited this neighbourState
            if (!visitedState.contains(movePawnOneLeadToStates.get(i))) {
                // depth first search this neighbourState
                solutionStates = this.dfsBacktrack(movePawnOneLeadToStates.get(i), visitedState, successPaths);

                // if we have reached the goal in these solutionStates
                // meaning we came here from the if statement on line 62
                // we add this state to the solutionStates and return
                // this way the path gets built using recursion
                if (goalIsReached(solutionStates)) {
                    solutionStates.addFirst(startState);
                    return solutionStates;
                }
            }
        }

        // here we do the same but then for the second node
        List<MazeState> movePawnTwoLeadToStates = this.getLeadToStates(startState, Pawn.TWO);
            String pathToState = startState.getPath() + "[" +
                    startState.getPawnOne().getPositionNr() + "," +
                    startState.getPawnTwo().getPositionNr() + "]";

        for (int i = 0; i < movePawnTwoLeadToStates.size(); i++) {
            movePawnTwoLeadToStates.get(i).setPath(pathToState);

            if (!visitedState.contains(movePawnTwoLeadToStates.get(i))) {
                solutionStates = this.dfsBacktrack(movePawnTwoLeadToStates.get(i), visitedState, successPaths);
                if (goalIsReached(solutionStates)) {
                    solutionStates.addFirst(startState);
                    return solutionStates;
                }
            }
        }


        // we need to add this because a branch of a path might lead to a valid solution
        // else we would never go back to try this branch
        visitedState.remove(startState);
        return new LinkedList<>(); // no solution has been found return an empty list
    }

    /**
     * Get all the possible states that the current state can lead to by moving 1 step deeper based on a provided state
     *
     * @param mazeState the state to get the leadToStates for
     * @param pawn      decide to move pawn one or two
     * @return a list of possible states this state can lead to
     */
    public LinkedList<MazeState> getLeadToStates(MazeState mazeState, Pawn pawn) {
        LinkedList<MazeState> leadToStates = new LinkedList<>();

        if (pawn == Pawn.ONE) {
            // get the outgoing edges based on the current color of the second node
            List<Edge> edgesOfPawnOne = mazeState.getPawnOne().getEdgesForColor(mazeState.getPawnTwo().getColor());

            // for every edge create a new state that can be achieved
            for (int i = 0; i < edgesOfPawnOne.size(); i++) {
                // we dont allow the first and second node to have the same position
                if (edgesOfPawnOne.get(i).getDestination().getPositionNr() == mazeState.getPawnTwo().getPositionNr())
                    continue;
                leadToStates.add(new MazeState(edgesOfPawnOne.get(i).getDestination(), mazeState.getPawnTwo()));
            }
        } else {
            // here we do the same but for the second node
            List<Edge> edgesOfPawnTwo = mazeState.getPawnTwo().getEdgesForColor(mazeState.getPawnOne().getColor());

            for (int i = 0; i < edgesOfPawnTwo.size(); i++) {
                if (edgesOfPawnTwo.get(i).getDestination().getPositionNr() == mazeState.getPawnOne().getPositionNr())
                    continue;
                leadToStates.add(new MazeState(mazeState.getPawnOne(), edgesOfPawnTwo.get(i).getDestination()));
            }
        }

        return leadToStates;
    }

    /**
     * Checks if a state is the goal state.
     *
     * @param mazeState the state to check
     * @return true if the state is the goal state false otherwise
     */
    private boolean isGoalState(MazeState mazeState) {
        return mazeState.getPawnOne().getPositionNr() == this.goalNode.getPositionNr() ||
                mazeState.getPawnTwo().getPositionNr() == this.goalNode.getPositionNr();
    }

    /**
     * Checks if a list of states contains the solution.
     *
     * @param solutionStates states to check
     * @return true if contained false otherwise
     */
    private boolean goalIsReached(List<MazeState> solutionStates) {
        for (MazeState solutionState : solutionStates) {
            if (isGoalState(solutionState)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get a list of nodes currently in the puzzle.
     *
     * @return a list of nodes
     */
    public List<Node> getNodesList() {
        return this.nodesList;
    }

    /**
     * Add a node to this graph.
     *
     * @param node the node to add
     */
    public void addNode(Node node) {
        this.nodesList.add(node);
    }

    /**
     * Set the goal node of the puzzle.
     *
     * @param goalNode the goal node
     */
    public void setGoalNode(Node goalNode) {
        this.goalNode = goalNode;
    }

    /**
     * Get the number of states created.
     *
     * @return the number of states created
     */
    public int getNrOfStatesCreated() {
        return nrOfStatesCreated;
    }

    /**
     * Display the graph
     * @return string contains the visual display of the graph
     */
    @Override
    public String toString() {
        StringBuilder graphStr = new StringBuilder();
        for (Node node : nodesList) {
            if (node.getPositionNr() != 23) {
                graphStr.append(String.format("%s\n-----------------------------\n\n", node.toString()));
            } else {
                graphStr.append("23_BLUE_FINISH_NODE\n\n");
            }
        }
        return graphStr.toString();
    }
}
