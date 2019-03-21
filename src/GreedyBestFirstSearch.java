import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class GreedyBestFirstSearch implements ISolver {
    private IHeuristic heuristic;

    public GreedyBestFirstSearch(IHeuristic heuristic) {
        this.heuristic = heuristic;
    }

    public ArrayList<Solution> solve(ArrayList<Puzzle> starts, ArrayList<Puzzle> finishes, int timeLimit) {
        ArrayList<Solution> solutions = new ArrayList<>();
        ArrayList<Puzzle> statesVisited;
        LinkedList<Puzzle> statesQueue;
        Puzzle start, finish, current;
        long startTime, finishTime, currentTime;
        long limit = timeLimit * 1000;

        for(int i = 0; i < starts.size(); i++) {
            start = starts.get(i);
            finish = finishes.get(i);
            start.setPriceToFinish(heuristic.calculate(start, finish));
            statesVisited = new ArrayList<>();
            statesQueue = new LinkedList<>();
            current = null;
            ArrayList<Puzzle> currentNextStates ;
            statesQueue.offer(start);
            startTime = System.nanoTime();
            boolean isSolution = false, visited;
            //solvable = this.isSolvable(start);
            BigInteger totalStates = factorial(BigInteger.valueOf(start.getWidth() * start.getHeight()));

            while(!statesQueue.isEmpty()) { //&& solvable
                current = statesQueue.poll();
                current.setKey();

                if(current.isEqual(finish)) {
                    isSolution = true;
                    break;
                }

                currentNextStates = current.generateNextStates(heuristic, finish);

                for(int j = 0; j < currentNextStates.size(); j++) {
                    Puzzle nextState = currentNextStates.get(j);
                    nextState.setKey();
                    visited = false;

                    for(Puzzle temp : statesVisited) {
                        if(nextState.isEqual(temp)) {
                            visited = true;
                            break;
                        }
                    }

                    for (Puzzle temp : statesQueue) {
                        if (nextState.isEqual(temp)) {
                            visited = true;
                            break;
                        }
                    }

                    if(!visited) {
                        boolean inserted = false;
                        for(int k = 0; k < statesQueue.size(); k++) {
                            if(nextState.getPriceToFinish() < statesQueue.get(k).getPriceToFinish()) {
                                statesQueue.add(k, nextState);
                                inserted = true;
                                break;
                            }
                        }

                        if (!inserted)
                            statesQueue.offer(nextState);
                    }
                }

                currentTime = (System.nanoTime() - startTime) / 1000000;

                if(currentTime > limit || totalStates.compareTo(BigInteger.valueOf(statesVisited.size())) <= 0){
                    isSolution = false;
                    break;
                }

                statesVisited.add(current);
                statesQueue.remove(current);
            }

            finishTime = System.nanoTime();
            System.out.println("States visited " + statesVisited.size());

            if(isSolution) {
                boolean isPath = false;
                Solution solution = new Solution();
                solution.getStates().add(current);

                while(!isPath) {
                    current = current.getPrevious();
                    solution.getStates().add(0, current);
                    if(start.isEqual(current))
                        isPath = true;
                }
                solution.setTimes(startTime, finishTime);
                solution.setVisited(statesVisited.size());
                System.out.print("Solution no." + (i+1) + " found.");
                System.out.println(" Duration: " + solution.getRunTime() + "ms");
                solutions.add(solution);
            }
            else {
                System.out.println("Solution for puzzle no." + (i + 1) + " does not exist");
                Solution solution = new Solution();
                solution.setTimes(startTime, finishTime);
                solution.setVisited(statesVisited.size());
                solutions.add(solution);
            }
        }


        return solutions;
    }

    public boolean isSolvable(Puzzle puzzle) {
        int count = 0, height = puzzle.getHeight(), width = puzzle.getWidth();

        if(width != height)
            return true;

        int[][] map = puzzle.getMap();
        for (int i = 0; i < height; i++) {
            for (int j = i + 1; j < width; j++) {
                if (map[j][i] != 0 && map[j][i] > map[i][j])
                    count++;
            }
        }

        return (count % 2 == 0);
    }

    public BigInteger factorial(BigInteger n) {
        if (n.compareTo(BigInteger.ZERO) == 0)
            return BigInteger.ONE;
        else {
            BigInteger x = n.subtract(BigInteger.ONE);
            x = n.multiply(factorial(x));
            return(x);
        }

    }
}
