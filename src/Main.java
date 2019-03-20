import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Main class of solver solution
 *
 * @author Adam Benovic
 */
public class Main {

    public static void main(String[] args) {
        Main solver = new Main();

        solver.run(args[0], args[1], args[2], args[3], args[4]);
    }

    public void run(String startFile, String finishFile, String solutionFile, String heuristic, String timeLimit)
    {
        int limit = Integer.parseInt(timeLimit);
        IO io = new IO();
        ArrayList<Puzzle> starts = io.loadFromFile(startFile);
        ArrayList<Puzzle> finishes = io.loadFromFile(finishFile);
        ArrayList<Solution> solutions;
        GreedyBestFirstSearch solver = null;

        switch(heuristic)
        {
            case "1":
                //set heuristic to Displaced tiles
                solver = new GreedyBestFirstSearch(new DisplacedHeuristic());
                break;
            case "2":
                //set heuristic to Manhattan
                solver = new GreedyBestFirstSearch(new ManhattanHeuristic());
                break;
            default:
                System.out.println("Enter valid heuristic function, please.");
                System.out.println("Enter 1 for Displaced tiles heuristic.");
                System.out.println("Enter 2 for Manhattan heuristic.");
                System.exit(Enum.EXIT_INVALID_HEURISTIC);
        }

        solutions = solver.solve(starts, finishes, limit);
        io.writeToFile(solutions, solutionFile);
    }
}

