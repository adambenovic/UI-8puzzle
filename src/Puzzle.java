import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;
import java.util.Arrays;

public class Puzzle {
    private int height;
    private int width;
    private int[][] map;
    private int zeroRow;
    private int zeroColumn;
    private Puzzle previous;
    private ArrayList<Puzzle> next;
    private int lastOperator;
    private int priceToFinish;
    private String key;

    public Puzzle() {

    }

    public Puzzle(Puzzle previous, int operator) {
        this.height = previous.getHeight();
        this.width = previous.getWidth();
        this.zeroRow = previous.getZeroRow();
        this.zeroColumn = previous.getZeroColumn();
        this.previous = previous;
        this.lastOperator = operator;
        this.next = null;
        this.key = null;
        this.map = new int[this.height][this.width];
        for(int i = 0; i < this.height; i++) {
            for(int j = 0; j < this.width; j++)
                this.map[i][j] = previous.getMap()[i][j];
        }
    }

    public ArrayList<Puzzle> generateNextStates(IHeuristic heuristic, Puzzle finish) {
        Puzzle up, left, right, down;

        this.next = new ArrayList<>();

        if(this.lastOperator != Enum.DOWN) {
            up = new Puzzle(this, Enum.UP);
            if (up.move(Enum.UP)) {
                up.setPriceToFinish(heuristic.calculate(up, finish));
                this.next.add(up);
            }
        }

        if(this.lastOperator != Enum.RIGHT) {
            left = new Puzzle(this, Enum.LEFT);
            if (left.move(Enum.LEFT)) {
                left.setPriceToFinish(heuristic.calculate(left, finish));
                this.next.add(left);
            }
        }

        if(this.lastOperator != Enum.LEFT) {
            right = new Puzzle(this, Enum.RIGHT);
            if (right.move(Enum.RIGHT)) {
                right.setPriceToFinish(heuristic.calculate(right, finish));
                this.next.add(right);
            }
        }

        if(this.lastOperator != Enum.UP) {
            down = new Puzzle(this, Enum.DOWN);
            if (down.move(Enum.DOWN)) {
                down.setPriceToFinish(heuristic.calculate(down, finish));
                this.next.add(down);
            }
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

    public int getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(int lastOperator) {
        this.lastOperator = lastOperator;
    }

    public int getPriceToFinish() {
        return priceToFinish;
    }

    public void setPriceToFinish(int priceToFinish) {
        this.priceToFinish = priceToFinish;
    }

    public String getKey() {
        return key;
    }

    public void setKey() {
        if(this.key == null)
            this.key = Integer.toString(Arrays.hashCode(this.map));
    }
}
