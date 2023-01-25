package battleship;

import java.util.Scanner;

public class Battlefield {
    int size = 10;
    int aircraftSize = 5;
    int battleshipSize = 4;
    int submarineSize = 3;
    int cruiserSize = 3;
    int destroyerSize = 2;
    char empty = '~';
    char hit = 'X';
    char missed = 'M';
    char shipSign = 'O';
    char[][] field = new char[size][size];
    Ship[] ships;
    Scanner scanner = new Scanner(System.in);

    public Battlefield() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = empty;
            }
        }
    }

    public void initField() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(this.toString());
        ships = new Ship[5];
        ships[0] = new Ship(aircraftSize, "Aircraft Carrier");
        ships[1] = new Ship(battleshipSize, "Battleship");
        ships[2] = new Ship(submarineSize, "Submarine");
        ships[3] = new Ship(cruiserSize, "Cruiser");
        ships[4] = new Ship(destroyerSize, "Destroyer");

        for (Ship ship : ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getSize());
            while (true) {
                String[] coordinates = scanner.nextLine().split(" ");
                int rowBegin = coordinates[0].charAt(0) - 65;
                int columnBegin = Integer.parseInt(coordinates[0].substring(1));
                int rowEnd = coordinates[1].charAt(0) - 65;
                int columnEnd = Integer.parseInt(coordinates[1].substring(1));
                if (rowBegin > rowEnd) {
                    int tmp = rowEnd;
                    rowEnd = rowBegin;
                    rowBegin = tmp;
                }
                if (columnBegin > columnEnd) {
                    int tmp = columnEnd;
                    columnEnd = columnBegin;
                    columnBegin = tmp;
                }
                if (ship.setCoordinates(rowBegin, columnBegin, rowEnd, columnEnd)) {
                    if (putShipOnField(rowBegin, columnBegin, rowEnd, columnEnd, ship)) {
                        System.out.println(this.toString());
                        break;
                    }
                }
            }
        }
    }

    public boolean putShipOnField(int rowBegin, int columnBegin, int rowEnd, int columnEnd, Ship shipToPlace) {
        for (Ship ship : ships) {
            if (ship != shipToPlace && ship.isPlaced()) {
                for (int i = rowBegin - 1; i <= rowEnd + 1; i++) {
                    for (int j = columnBegin - 1; j <= columnBegin + 1; j++) {
                        if ((i == ship.getRowBegin() && j == ship.getColumnBegin()) ||
                                (i == ship.getRowEnd() && j == ship.getColumnEnd())) {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return false;
                        }
                    }
                }
            }
        }

        if (rowBegin == rowEnd) {
            for (int i = columnBegin; i <= columnEnd; i++) {
                this.field[rowBegin][i - 1] = shipToPlace.getCells()[i - columnBegin];
            }
        } else {
            for (int i = rowBegin; i <= rowEnd; i++) {
                this.field[i][columnBegin - 1] = shipToPlace.getCells()[i - rowBegin];
            }
        }
        return true;
    }

    public boolean makeShot() {
        String shotCell = scanner.nextLine();
        int shotRow = shotCell.charAt(0) - 65;
        int shotColumn = Integer.parseInt(shotCell.substring(1));
        if (shotRow < 0 || shotRow > 9 || shotColumn < 1 || shotColumn > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        } else {

            boolean isHitOnShip = false;
            boolean isSankAShip = false;
            boolean isEndGame = true;

            for (Ship ship : ships) {
                if (shotRow == ship.rowBegin && shotRow == ship.rowEnd) {
                    if (shotColumn >= ship.columnBegin && shotColumn <= ship.columnEnd) {
                        isHitOnShip = true;
                        isSankAShip = ship.isFinalHit(shotColumn - ship.columnBegin, hit);
                        break;
                    }
                } else if (shotColumn == ship.columnBegin && shotColumn == ship.columnEnd) {
                    if ((shotRow >= ship.rowBegin && shotRow <= ship.rowEnd)) {
                        isHitOnShip = true;
                        isSankAShip = ship.isFinalHit(shotRow - ship.rowBegin, hit);
                        break;
                    }
                }
            }

            if (isHitOnShip && !isSankAShip) {
                System.out.println("You hit a ship!");
            } else if (isSankAShip) {
                for (Ship ship : ships) {
                    if (!ship.isDead) {
                        System.out.println("You sank a ship! Specify a new target:");
                        isEndGame = false;
                        break;
                    }
                }
                if (isEndGame) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    return true;
                }

            } else {
                this.field[shotRow][shotColumn - 1] = missed;
                printBattlefield(true);
                System.out.println("You missed!");
            }
        }
        return false;
    }

    public void printBattlefield(boolean fogOfWar) {
        for (Ship ship : ships) {
            if (ship.rowBegin == ship.rowEnd) {
                for (int i = ship.columnBegin; i <= ship.columnEnd; i++) {
                    // If the fog of war - substitute only hit symbols in the field
                    // otherwise we take all the ship's symbols
                    if (fogOfWar) {
                        this.field[ship.rowBegin][i - 1] = ship.getCells()[i - ship.columnBegin] == shipSign ? empty : ship.getCells()[i - ship.columnBegin];
                    } else {
                        this.field[ship.rowBegin][i - 1] = ship.getCells()[i - ship.columnBegin];
                    }
                }
            } else {
                for (int i = ship.rowBegin; i <= ship.rowEnd; i++) {
                    if (fogOfWar) {
                        this.field[i][ship.columnBegin - 1] = ship.getCells()[i - ship.rowBegin] == shipSign ? empty : ship.getCells()[i - ship.rowBegin];
                    } else {
                        this.field[i][ship.columnBegin - 1] = ship.getCells()[i - ship.rowBegin];
                    }
                }
            }
        }
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < size; i++) {
            result.append(Character.toChars(65 + i));
            for (int j = 0; j < size; j++) {
                result.append(" ").append(field[i][j]);
            }
            result.append("\n");
        }
        return String.valueOf(result);
    }
}
