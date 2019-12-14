import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        MazeGraph pawnPuzzleMaze = new MazeGraph();
        initMaze(pawnPuzzleMaze);
        displayMaze(pawnPuzzleMaze);
        solvePuzzle(pawnPuzzleMaze);
    }

    public static void initMaze(MazeGraph graph){
        Node one = new Node(1, Color.PURPLE);
        Node two = new Node(2, Color.BLACK);
        Node three = new Node(3, Color.GREEN);
        Node four = new Node(4, Color.GREEN);
        Node five = new Node(5, Color.GREEN);
        Node six = new Node(6, Color.ORANGE);
        Node seven = new Node(7, Color.ORANGE);
        Node eight = new Node(8, Color.PURPLE);
        Node nine = new Node(9, Color.PURPLE);
        Node ten = new Node(10, Color.BLACK);
        Node eleven = new Node(11, Color.ORANGE);
        Node twelve = new Node(12, Color.PURPLE);
        Node thirtheen = new Node(13, Color.ORANGE);
        Node fourteen = new Node(14, Color.GREEN);
        Node fifteen = new Node(15, Color.ORANGE);
        Node sixteen = new Node(16, Color.GREEN);
        Node seventeen = new Node(17, Color.GREEN);
        Node eighteen = new Node(18, Color.BLACK);
        Node nineteen = new Node(19, Color.ORANGE);
        Node twenty = new Node(20, Color.GREEN);
        Node twentyone = new Node(21, Color.BLACK);
        Node twentytwo = new Node(22, Color.BLACK);
        Node twentythree = new Node(23, Color.BLUE);

        one.addEdge(new Edge(four,Color.PURPLE));
        one.addEdge(new Edge(five,Color.BLACK));

        two.addEdge(new Edge(six,Color.GREEN));
        two.addEdge(new Edge(twelve,Color.PURPLE));

        three.addEdge(new Edge(one,Color.ORANGE));
        three.addEdge(new Edge(four,Color.ORANGE));

        four.addEdge(new Edge(thirtheen,Color.BLACK));

        five.addEdge(new Edge(nine,Color.ORANGE));

        six.addEdge(new Edge(nine,Color.GREEN));
        six.addEdge(new Edge(ten,Color.PURPLE));

        seven.addEdge(new Edge(two,Color.GREEN));

        eight.addEdge(new Edge(three,Color.PURPLE));

        nine.addEdge(new Edge(four,Color.GREEN));
        nine.addEdge(new Edge(fourteen,Color.BLACK));

        ten.addEdge(new Edge(fifteen,Color.GREEN));

        eleven.addEdge(new Edge(twelve,Color.GREEN));
        eleven.addEdge(new Edge(ten,Color.PURPLE));

        twelve.addEdge(new Edge(seven,Color.GREEN));

        thirtheen.addEdge(new Edge(eighteen,Color.GREEN));
        thirtheen.addEdge(new Edge(eight,Color.GREEN));

        fourteen.addEdge(new Edge(twenty,Color.ORANGE));
        fourteen.addEdge(new Edge(twentythree,Color.GREEN));

        fifteen.addEdge(new Edge(twentytwo,Color.GREEN));
        fifteen.addEdge(new Edge(twentythree,Color.PURPLE));

        sixteen.addEdge(new Edge(fifteen,Color.GREEN));

        seventeen.addEdge(new Edge(eleven,Color.BLACK));
        seventeen.addEdge(new Edge(twelve,Color.PURPLE));
        seventeen.addEdge(new Edge(sixteen,Color.BLACK));

        eighteen.addEdge(new Edge(nine,Color.ORANGE));
        eighteen.addEdge(new Edge(twenty,Color.ORANGE));

        nineteen.addEdge(new Edge(eighteen,Color.GREEN));

        twenty.addEdge(new Edge(twentyone, Color.ORANGE));

        twentyone.addEdge(new Edge(twentytwo, Color.ORANGE));
        twentyone.addEdge(new Edge(twentythree, Color.BLACK));

        twentytwo.addEdge(new Edge(seventeen, Color.ORANGE));

        graph.addNode(one);
        graph.addNode(two);
        graph.addNode(three);
        graph.addNode(four);
        graph.addNode(five);
        graph.addNode(six);
        graph.addNode(seven);
        graph.addNode(eight);
        graph.addNode(nine);
        graph.addNode(ten);
        graph.addNode(eleven);
        graph.addNode(twelve);
        graph.addNode(thirtheen);
        graph.addNode(fourteen);
        graph.addNode(fifteen);
        graph.addNode(sixteen);
        graph.addNode(seventeen);
        graph.addNode(eighteen);
        graph.addNode(nineteen);
        graph.addNode(twenty);
        graph.addNode(twentyone);
        graph.addNode(twentytwo);
        graph.addNode(twentythree);
    }

    public static void displayMaze(MazeGraph graph){
        System.out.println("PAWN PUZZLE MAZE\n");
        System.out.println(graph.toString());
    }

    public static void solvePuzzle(MazeGraph graph){
        ArrayList<LinkedList<MazeState>> results =
                graph.findSolutions(new MazeState(graph.getNodesList().get(0), graph.getNodesList().get(1)), graph.getNodesList().get(22));

        System.out.println( "NUMBER OF CREATED STATE: " + graph.getNrOfStatesCreated());
        for (int i=0; i<results.size();i++) {
            System.out.println("SOLUTION " + (i+1) + ":");
            System.out.println(results.get(i).getLast().getPath() + results.get(i).getLast().toString());
        }
    }
}
