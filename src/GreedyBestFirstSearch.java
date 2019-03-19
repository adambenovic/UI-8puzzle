import java.util.ArrayList;
import java.util.LinkedList;

public class GreedyBestFirstSearch implements ISolver {
    private IHeuristic heuristic;

    public GreedyBestFirstSearch(IHeuristic heuristic) {
        this.heuristic = heuristic;
    }

    public ArrayList<Solution> solve(ArrayList<Puzzle> starts, ArrayList<Puzzle> finishes) {
        ArrayList<Solution> solutions = new ArrayList<>();
        ArrayList<Puzzle> statesVisited;
        LinkedList<Puzzle> statesQueue;
        Puzzle start, finish, current;
        long startTime, finishTime;

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
            boolean isSolution = false, visited, solvable = this.isSolvable(start);

            while(!statesQueue.isEmpty() && solvable) {
                current = statesQueue.poll();

                if(current.isEqual(finish)) {
                    isSolution = true;
                    break;
                }

                currentNextStates = current.generateNextStates(heuristic, finish);

                for(int j = 0; j < currentNextStates.size(); j++) {
                    Puzzle nextState = currentNextStates.get(j);
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

                statesVisited.add(current);
                statesQueue.remove(current);
            }

            finishTime = System.nanoTime();

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
                System.out.print("Solution no." + (i+1) + " found.");
                System.out.println(" Duration: " + solution.getRunTime() + "ms");
                solutions.add(solution);
            }
            else {
                System.out.println("Solution for puzzle no." + (i + 1) + " does not exist");
            }
        }


        return solutions;
    }

    public boolean isSolvable(Puzzle puzzle) {
        int count = 0, height = puzzle.getHeight(), width = puzzle.getWidth();

        if(width != height)
            return true;

        int[][] map = puzzle.getMap();
        for (int i = 0; i < height; i++)
            for (int j = i + 1; j < width; j++)
                if (map[j][i] != 0 && map[j][i] > map[i][j])
                    count++;

        return (count % 2 == 0);
    }
}
