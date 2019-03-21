import java.util.ArrayList;

public class Solution {
    private long startTime;
    private long finishTime;
    private long runTime;
    private ArrayList<Puzzle> states;
    private long visited;

    public Solution() {
        this.states = new ArrayList<>();
    }

    public void setTimes(long startTime, long finishTime) {
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.runTime = (finishTime - startTime) / 1000000;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public ArrayList<Puzzle> getStates() {
        return states;
    }

    public void setStates(ArrayList<Puzzle> states) {
        this.states = states;
    }

    public long getVisited() {
        return visited;
    }

    public void setVisited(long visited) {
        this.visited = visited;
    }
}
