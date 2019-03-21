import java.util.ArrayList;

public interface ISolver {
    public ArrayList<Solution> solve(ArrayList<Puzzle> starts, ArrayList<Puzzle> finishes, int timeLimit);
    public boolean isSolvable(Puzzle puzzle);
}
