public class ManhattanHeuristic implements IHeuristic {

    public int calculate(Puzzle current, Puzzle finish) {
        int result = 0;
        for(int i = 0; i < current.getHeight(); i++) {
            for(int j = 0; j < current.getWidth(); j++) {
                int[] inFinish = findValue(current.getMap()[i][j], finish);
                result += Math.abs(inFinish[0] - i) + Math.abs(inFinish[1] - j);
            }
        }

        return result;
    }

    private int[] findValue(int target, Puzzle finish) {
        int[] result = new int[2];

        for(int i = 0; i < finish.getHeight(); i++) {
            for(int j = 0; j < finish.getWidth(); j++) {
                if(finish.getMap()[i][j] == target){
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        return result;
    }
}
