import java.util.*;

/**
 * Class representing a graph
 */
public class MazeGraph {

    private List<Node> nodesList = new ArrayList<>();
    private Node goalNode;
    private int nrOfStatesCreated;

    /**
     * Find all possible solutions for the current graph based on a specified start state and the node
     * which is the goal to achieve.
     *
     * @param startState the state to start from
     * @param goalNode   the goal node to reach
     * @return a list containing results, results is a sequence of states that led to the goal
     */
    public ArrayList<LinkedList<MazeState>> findSolutions(MazeState startState, Node goalNode) {
        nrOfStatesCreated = 0;
        Set<String> successPaths = new HashSet<>();
        ArrayList<LinkedList<MazeState>> solutions = new ArrayList<>();
        LinkedList<MazeState> solution;

        this.setGoalNode(goalNode);
        boolean completelySolved = false;
         while (!completelySolved){
            Set<MazeState> visitedState = new HashSet<>();
            solution = this.backtrackDeepFirst(startState, visitedState, successPaths);

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
        };

        return solutions;
    }

    /**
     * Depth first search the graph and backtrack if a result has been found.
     *
     * @param startState     the start state to start from
     * @param visitedState  the states that have already been visited
     * @param successPaths paths of solutions that have already been found
     * @return a list of states that led to a result or an empty list if no result has been found
     */
    public LinkedList<MazeState> backtrackDeepFirst(MazeState startState, Set<MazeState> visitedState, Set<String> successPaths) {
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

        } else {
            // get all the possible states this first state node can go to
            // based on the current startState
            List<MazeState> neighbourStatesPawnOne = this.getNeighbourStates(startState, Pawn.ONE);

            for (MazeState neighbourState : neighbourStatesPawnOne) {
                // append the startState path to this neighbourState
                // this way we know how we got to this neighbourState
                neighbourState.appendPath(startState);

                // if we have not already visited this neighbourState
                if (!visitedState.contains(neighbourState)) {
                    // depth first search this neighbourState
                    solutionStates = this.backtrackDeepFirst(neighbourState, visitedState, successPaths);

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
            List<MazeState> neighbourStatesPawnTwo = this.getNeighbourStates(startState, Pawn.TWO);

            for (MazeState neighbourState : neighbourStatesPawnTwo) {
                neighbourState.appendPath(startState);

                if (!visitedState.contains(neighbourState)) {

                    solutionStates = this.backtrackDeepFirst(neighbourState, visitedState, successPaths);
                    if (goalIsReached(solutionStates)) {
                        solutionStates.addFirst(startState);
                        return solutionStates;
                    }
                }
            }

        }
        // we need to add this because a branch of a path might lead to a valid solution
        // else we would never go back to try this branch
        visitedState.remove(startState);
        return new LinkedList<>(); // no solution has been found return an empty list
    }

    /**
     * Get all the possible neighbourState based on a provided state
     *
     * @param mazeState               the state to get the neighbours for
     * @param pawn decide for the first pawn or second pawn
     * @return a list of possible states this state can lead to
     */
    public LinkedList<MazeState> getNeighbourStates(MazeState mazeState, Pawn pawn) {
        LinkedList<MazeState> neighbourStates = new LinkedList<>();

        if (pawn == Pawn.ONE) {
            // get the outgoing edges based on the current color of the second node
            List<Edge> outgoingEdges = mazeState.getPawnOne().getEdgesForColor(mazeState.getPawnTwo().getColor());

            // for every edge create a new state that can be achieved
            for (Edge edge : outgoingEdges) {
                // we dont allow the first and second node to have the same position
                if (edge.getDestination().getPositionNr() == mazeState.getPawnTwo().getPositionNr()) continue;
                neighbourStates.add(new MazeState(edge.getDestination(), mazeState.getPawnTwo()));
            }
        } else {
            // here we do the same but for the second node
            List<Edge> outgoingEdges = mazeState.getPawnTwo().getEdgesForColor(mazeState.getPawnOne().getColor());

            for (Edge edge : outgoingEdges) {
                if (edge.getDestination().getPositionNr() == mazeState.getPawnOne().getPositionNr()) continue;
                neighbourStates.add(new MazeState(mazeState.getPawnOne(), edge.getDestination()));
            }
        }

        return neighbourStates;
    }

    /**
     * Checks if a provided state equal the desired state.
     *
     * @param mazeState the state to check
     * @return true if the state is the desired state false otherwise
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
     * Get a list of nodes currently in the graph.
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
     * Create and edge between two nodes.
     *
     * @param fromNode the node where this edge originates from
     * @param toNode   the node where this edge goes to
     * @param color    the color of the edge
     */
    public void addEdge(Node fromNode, Node toNode, Color color) {
        fromNode.addEdge(new Edge(toNode, color));
    }

    /**
     * Set the goal node of the graph.
     *
     * @param goalNode the goal node
     */
    public void setGoalNode(Node goalNode) {
        this.goalNode = goalNode;
    }

    /**
     * Get the number of states checked.
     *
     * @return the number of states checked
     */
    public int getNrOfStatesCreated() {
        return nrOfStatesCreated;
    }

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
