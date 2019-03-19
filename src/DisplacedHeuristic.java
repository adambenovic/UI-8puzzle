public class DisplacedHeuristic implements IHeuristic {
    public int calculate(Puzzle current, Puzzle finish) {
        int result = 0;

        for(int i = 0; i < current.getHeight(); i++) {
            for(int j = 0; j < current.getWidth(); j++) {
                if(current.getMap()[i][j] == 0)
                    continue;
                if(current.getMap()[i][j] != finish.getMap()[i][j])
                    result++;
            }
        }

        return result;
    }
}
