package battleship;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;


public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static int[] xAxis = IntStream.range(1,11).toArray();
    public static char[] yAxis = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    public static String[][] battlefield;
    public static void main(String[] args) {
        // Write your code here
        Game game = new Game();
        game.start();
    }

    public static void getAircraft(){
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        String aircraft = scanner.nextLine();
        String[] strings = aircraft.split(" ");
        if (strings.length != 2) getAircraft();
    }

    public static void getBattleship(){
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        String battleship = scanner.nextLine();
        String[] strings = battleship.split(" ");
        if (strings.length != 2) getBattleship();

    }

    public static String[][] createField() {
        String[][] field = new String[11][11];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (i >= 1 && j >= 1) {
                    field[i][j] = "~";
                } else if (i == 0 && j >= 1) {
                    field[i][j] = String.valueOf(xAxis[j-1]);
                } else if (i >= 1 && j == 0) {
                    field[i][j] = String.valueOf(yAxis[i-1]);
                }
            }
        }
        field[0][0] = " ";
        return field;
    }

    public static void printField(String[][] field) {
        for (String[] strings : field) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }

}
