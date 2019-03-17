import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Main class of solver solution
 *
 * @author Adam Benovic
 */
public class Main {

    public static void main(String[] args) {
        Main solver = new Main();

        solver.run(args[0], args[1], args[2]);
    }

    public void run(String startFile, String finishFile, String heuristic)
    {
        IO io = new IO();
        ArrayList<Puzzle> starts = io.loadFromFile(startFile);
        ArrayList<Puzzle> finishes = io.loadFromFile(finishFile);
        ArrayList<ArrayList<Puzzle>> solutions = new ArrayList<>();
        GreedyBestFirstSearch solver = null;
        switch(heuristic)
        {
            case "1":
                //call the displaced tiles heuristic
                solver = new GreedyBestFirstSearch(new DisplacedHeuristic());
                break;
            case "2":
                //call the manhattan heuristic
                solver = new GreedyBestFirstSearch(new ManhattanHeuristic());
                break;
            default:
                System.out.println("Enter valid heuristic function, please.");
                System.out.println("Enter 1 for Displaced tiles heuristic.");
                System.out.println("Enter 2 for Manhattan heuristic.");
                System.exit(Enum.EXIT_INVALID_HEURISTIC);
        }

        solutions = solver.solve(starts, finishes);
        io.writeToFile(solutions);
    }
}

