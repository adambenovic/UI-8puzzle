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
        this.map = previous.getMap();
        this.zeroRow = previous.getZeroRow();
        this.zeroColumn = previous.getZeroColumn();
        this.previous = previous;
        this.lastOperator = operator;
        this.next = null;
    }

    public ArrayList<Puzzle> generateNextStates(IHeuristic heuristic, Puzzle finish) {
        Puzzle generated;
        this.next = new ArrayList<>();

        generated = new Puzzle(this, "up");
        if (generated.move("up")) {
            generated.setPriceToFinish(heuristic.calculate(generated, finish));
            this.next.add(generated);
        }

        generated = new Puzzle(this, "left");
        if (generated.move("left")) {
            generated.setPriceToFinish(heuristic.calculate(generated, finish));
            this.next.add(generated);
        }

        generated = new Puzzle(this, "right");
        if (generated.move("right")) {
            generated.setPriceToFinish(heuristic.calculate(generated, finish));
            this.next.add(generated);
        }

        generated = new Puzzle(this, "down");
        if (generated.move("down")) {
            generated.setPriceToFinish(heuristic.calculate(generated, finish));
            this.next.add(generated);
        }

        return this.next;
    }

    private boolean move(String direction) {
        boolean success = false;

        switch (direction) {
            case "up":
                if(zeroRow - 1 >= 0) {
                    swapMapValues(zeroRow, zeroColumn, zeroRow - 1, zeroColumn);
                    success = true;
                }
                break;
            case "left":
                if(zeroColumn - 1 >= 0) {
                    swapMapValues(zeroRow, zeroColumn, zeroRow, zeroColumn - 1);
                    success = true;
                }
                break;
            case "right":
                if(zeroColumn + 1 < this.width) {
                    swapMapValues(zeroRow, zeroColumn, zeroRow, zeroColumn + 1);
                    success = true;
                }
                break;
            case "down":
                if(zeroRow + 1 < this.height) {
                    swapMapValues(zeroRow, zeroColumn, zeroRow + 1, zeroColumn);
                    success = true;
                }
                break;
            default:
                System.out.println("Invalid direction entered.");
                System.exit(Enum.EXIT_INVALID_DIRECTION);

        }

        return success;
    }

    private void swapMapValues(int rowA, int columnA, int rowB, int columnB) {
        int tmp = map[rowA][columnA];
        map[rowA][columnA] = map[rowB][columnB];
        map[rowB][columnB] = tmp;
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
