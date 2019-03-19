import java.util.ArrayList;

public interface ISolver {
    public ArrayList<Solution> solve(ArrayList<Puzzle> starts, ArrayList<Puzzle> finishes);
    public boolean isSolvable(Puzzle puzzle);
}
