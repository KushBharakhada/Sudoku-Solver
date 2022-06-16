public class Solver {

    public static boolean checkRow(int[][] board, int value, int row) {
        for (int column = 0; column < GUI.GRID_LENGTH; column++) {
            if (board[row][column] == value) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkColumn(int[][] board, int value, int column) {
        for (int row = 0; row < GUI.GRID_LENGTH; row++) {
            if (board[row][column] == value) {
                return true;
            }
        }
        return false;
    }

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

    public static boolean checkLocalBoxRowAndColumn(int[][] board, int value, int row, int column) {
        // Check value is a possible placements by checking the row, column and local box
        boolean valid = !checkRow(board, value, row) && !checkColumn(board, value, column) &&
            !checkLocalBox(board, value, row, column);
        return valid;
    }

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

    public static int[][] solvedBoard(int[][] board) {
        solve(board);
        return board;
    }

}
