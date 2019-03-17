import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class GreedyBestFirstSearch {
    private IHeuristic heuristic;

    public GreedyBestFirstSearch(IHeuristic heuristic) {
        this.heuristic = heuristic;
    }

    public IHeuristic getHeuristic() {
        return heuristic;
    }

    public ArrayList<ArrayList<Puzzle>> solve(ArrayList<Puzzle> starts, ArrayList<Puzzle> finishes) {
        ArrayList<ArrayList<Puzzle>> solutions = new ArrayList<>();
        ArrayList<Puzzle> statesVisited;
        LinkedList<Puzzle> statesQueue;
        Puzzle start, finish, current;
        long startTime, finishTime;

        for(int i = 0; i < starts.size(); i++) {
            start = starts.get(i);
            finish = finishes.get(i);
            start.setPriceToFinish(heuristic.calculate(start, finish));
            System.out.println(start.getPriceToFinish());
            statesVisited = new ArrayList<>();
            statesQueue = new LinkedList<>();
            current = null;
            ArrayList<Puzzle> currentNextStates = new ArrayList<>();
            boolean isSolution = false, visited = false;
            statesQueue.offer(start);
            startTime = System.nanoTime();

            while(!statesQueue.isEmpty()) {
                System.out.println("how many times do we get here");
                current = statesQueue.poll();

                if(Arrays.equals(current.getMap(), finish.getMap())) {
                    System.out.println("is it solutio yet?");
                    isSolution = true;
                    break;
                }

                currentNextStates = current.generateNextStates(heuristic, finish);

                for(int j = 0; j < currentNextStates.size(); j++) {
                    Puzzle nextState = currentNextStates.get(j);
                    visited = false;

                    for(Puzzle temp : statesQueue) {
                        if(Arrays.equals(nextState.getMap(), temp.getMap())) {
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
            startTime = (finishTime - startTime) / 1000000;

            if(isSolution) {
                boolean isPath = false;
                ArrayList<Puzzle> solution = new ArrayList<>();
                solution.add(current);

                while(!isPath) {
                    System.out.println("we are here");
                    current = current.getPrevious();
                    solution.add(0, current);
                    if(Arrays.equals(start.getMap(), current.getMap()))
                        isPath = true;
                }
                System.out.println("Solution no." + i + " found.");
                System.out.println("Duration: " + startTime + "ms");
                solutions.add(solution);
            }
            else {
                System.out.println("Solution for puzzle no." + i + " was not found");
            }
        }


        return solutions;
    }

    public void setHeuristic(IHeuristic heuristic) {
        this.heuristic = heuristic;
    }
}
