/**
 * Solver.java
 * Implements the program to solve a sudoku board
 * @author Kush Bharakhada
 */

public class Solver {

    /**
     * Checks the row of the grid
     * @param board to be solved
     * @param value to check for in the row
     * @param row to be checked
     * @return true if the row already contains the value, otherwise false
     */
    public static boolean checkRow(int[][] board, int value, int row) {
        for (int column = 0; column < GUI.GRID_LENGTH; column++) {
            if (board[row][column] == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the column of the grid
     * @param board to be solved
     * @param value to check for in the column
     * @param column to be checked
     * @return true if the column already contains the value, otherwise false
     */
    public static boolean checkColumn(int[][] board, int value, int column) {
        for (int row = 0; row < GUI.GRID_LENGTH; row++) {
            if (board[row][column] == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks a local box
     * @param board to be solved
     * @param value to check for in the local box
     * @param row of a value in the local box
     * @param column of a value in the local box
     * @return true if the local box contains the value, otherwise false
     */
    public static boolean checkLocalBox(int[][] board, int value, int row, int column) {
        int localBoxTopLeftRow = row - (row % 3);
        int localBoxTopLeftColumn = column - (column % 3);

        for (int i = localBoxTopLeftRow; i < localBoxTopLeftRow + GUI.LOCAL_BOX_LENGTH; i++) {
            for (int j = localBoxTopLeftColumn; j < localBoxTopLeftColumn + GUI.LOCAL_BOX_LENGTH; j++) {
                if (board[i][j] == value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks the row, column and local box to see if a value has valid placement
     * @param board to be solved/checked
     * @param value to check if it has valid placement
     * @param row of a value
     * @param column of a value
     * @return true if the placement is valid, otherwise false
     */
    public static boolean checkLocalBoxRowAndColumn(int[][] board, int value, int row, int column) {
        // Check value is a possible placements by checking the row, column and local box
        boolean valid = !checkRow(board, value, row) && !checkColumn(board, value, column) &&
            !checkLocalBox(board, value, row, column);
        return valid;
    }

    /**
     * Attempts to place a value on the board, if it has valid placement, the value is placed.
     * If the value leads to a dead end e.g. all possible values were scanned and still the value
     * could not be placed, the recursion back tracks to remove the previous value and attempt the
     * next value possible.
     * @param board to be solved
     * @return true if the current board was solved
     */
    public static boolean solve(int[][] board) {
        for (int row = 0; row < GUI.GRID_LENGTH; row++) {
            for (int column = 0; column < GUI.GRID_LENGTH; column++) {
                if (board[row][column] == 0) {
                    for (int possibleValue = 1; possibleValue <= GUI.GRID_LENGTH; possibleValue++) {
                        if (checkLocalBoxRowAndColumn(board, possibleValue, row, column)) {
                            board[row][column] = possibleValue;
                            if (solve(board))
                                return true;
                            else
                                board[row][column] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Solves the board. Used by GUI class to get the board
     * @param board to solve
     * @return solved board
     */
    public static int[][] solvedBoard(int[][] board) {
        solve(board);
        return board;
    }

}
