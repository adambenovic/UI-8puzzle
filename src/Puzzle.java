import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;

public class Puzzle {
    private int height;
    private int width;
    private int[][] map;
    private int zeroRow;
    private int zeroColumn;
    private Puzzle previous;
    private ArrayList<Puzzle> next;
    private String lastOperator;
    private int depth;
    private int priceSoFar;
    private int priceToFinish;

    public Puzzle() {

    }

    public Puzzle(Puzzle previous, String operator) {
        this.height = previous.getHeight();
        this.width = previous.getWidth();
        this.zeroRow = previous.getZeroRow();
        this.zeroColumn = previous.getZeroColumn();
        this.previous = previous;
        this.lastOperator = operator;
        this.next = null;
        this.map = new int[this.height][this.width];
        for(int i = 0; i < this.height; i++) {
            for(int j = 0; j < this.width; j++)
                this.map[i][j] = previous.getMap()[i][j];
        }
    }

    public ArrayList<Puzzle> generateNextStates(IHeuristic heuristic, Puzzle finish) {
        Puzzle up, left, right, down;

        this.next = new ArrayList<>();

        up = new Puzzle(this, "up");
        if (up.move(Enum.UP)) {
            up.setPriceToFinish(heuristic.calculate(up, finish));
            this.next.add(up);
        }

        left = new Puzzle(this, "left");
        if (left.move(Enum.LEFT)) {
            left.setPriceToFinish(heuristic.calculate(left, finish));
            this.next.add(left);
        }

        right = new Puzzle(this, "right");
        if (right.move(Enum.RIGHT)) {
            right.setPriceToFinish(heuristic.calculate(right, finish));
            this.next.add(right);
        }

        down = new Puzzle(this, "down");
        if (down.move(Enum.DOWN)) {
            down.setPriceToFinish(heuristic.calculate(down, finish));
            this.next.add(down);
        }

        return this.next;
    }

    private boolean move(int direction) {
        boolean success = false;

        switch (direction) {
            case Enum.UP:
                if(zeroRow - 1 >= 0) {
                    swapMapValues(zeroRow, zeroColumn, zeroRow - 1, zeroColumn);
                    zeroRow = zeroRow - 1;
                    success = true;
                }
                break;
            case Enum.LEFT:
                if(zeroColumn - 1 >= 0) {
                    swapMapValues(zeroRow, zeroColumn, zeroRow, zeroColumn - 1);
                    zeroColumn = zeroColumn - 1;
                    success = true;
                }
                break;
            case Enum.RIGHT:
                if(zeroColumn + 1 < width) {
                    swapMapValues(zeroRow, zeroColumn, zeroRow, zeroColumn + 1);
                    zeroColumn = zeroColumn + 1;
                    success = true;
                }
                break;
            case Enum.DOWN:
                if(zeroRow + 1 < height) {
                    swapMapValues(zeroRow, zeroColumn, zeroRow + 1, zeroColumn);
                    zeroRow = zeroRow + 1;
                    success = true;
                }
                break;
            default:
                System.out.println("Invalid direction entered.");
                System.exit(Enum.EXIT_INVALID_DIRECTION);

        }

        return success;
    }

    public boolean isEqual(Puzzle b) {
        boolean equals = true;
        for (int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(map[i][j] != b.getMap()[i][j])
                    equals = false;
            }
        }

        return equals;
    }

    private void swapMapValues(int rowA, int columnA, int rowB, int columnB) {
        int tmp = map[rowA][columnA];
        map[rowA][columnA] = map[rowB][columnB];
        map[rowB][columnB] = tmp;
    }

    public void printMap() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int getZeroRow() {
        return zeroRow;
    }

    public void setZeroRow(int zeroRow) {
        this.zeroRow = zeroRow;
    }

    public int getZeroColumn() {
        return zeroColumn;
    }

    public void setZeroColumn(int zeroColumn) {
        this.zeroColumn = zeroColumn;
    }

    public Puzzle getPrevious() {
        return previous;
    }

    public void setPrevious(Puzzle previous) {
        this.previous = previous;
    }

    public ArrayList<Puzzle> getNext() {
        return next;
    }

    public void setNext(ArrayList<Puzzle> next) {
        this.next = next;
    }

    public String getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(String lastOperator) {
        this.lastOperator = lastOperator;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getPriceSoFar() {
        return priceSoFar;
    }

    public void setPriceSoFar(int priceSoFar) {
        this.priceSoFar = priceSoFar;
    }

    public int getPriceToFinish() {
        return priceToFinish;
    }

    public void setPriceToFinish(int priceToFinish) {
        this.priceToFinish = priceToFinish;
    }
}
