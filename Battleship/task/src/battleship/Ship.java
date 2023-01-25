package battleship;

import java.util.Arrays;

public class Ship {
    char shipSymbol = 'O';
    int size;
    String name;
    char[] cells;
    boolean isPlaced = false;
    boolean isDead = false;
    int rowBegin;
    int rowEnd;
    int columnBegin;
    int columnEnd;

    public Ship(int size, String name) {
        this.size = size;
        this.name = name;
        this.cells = new char[size];
        Arrays.fill(this.cells, shipSymbol);
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public boolean setCoordinates(int rowBegin, int columnBegin, int rowEnd, int columnEnd) {
        if (rowBegin == rowEnd || columnBegin == columnEnd) {
            if (rowEnd - rowBegin != this.size - 1 && columnEnd - columnBegin != this.size - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", this.name);
                return false;
            }
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        this.rowBegin = rowBegin;
        this.rowEnd = rowEnd;
        this.columnBegin = columnBegin;
        this.columnEnd = columnEnd;
        this.isPlaced = true;

        return true;
    }

    public boolean isFinalHit(int index, char hit) {
        this.cells[index] = hit;
        for (char each : cells) {
            if (each != hit) {
                return false;
            }
        }
        this.isDead = true;
        return true;
    }

    public char[] getCells() {
        return cells;
    }

    public int getRowBegin() {
        return rowBegin;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getColumnBegin() {
        return columnBegin;
    }

    public int getColumnEnd() {
        return columnEnd;
    }

    public boolean isPlaced() {
        return isPlaced;
    }
}
