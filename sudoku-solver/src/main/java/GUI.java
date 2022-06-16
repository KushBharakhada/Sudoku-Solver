import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class GUI extends JFrame implements ActionListener {

    // Constants
    public static final int GRID_LENGTH = 9;
    public static final int LOCAL_BOX_LENGTH = 3;
    public static final int FRAME_SIZE = 540;
    private static final int BOX_SIZE = FRAME_SIZE / GRID_LENGTH;

    private JTextField[][] textBoxGrid;
    private JMenuItem solve;
    private JMenuItem clear;
    private int[][] inputValues;
    private int[][] outputValues;
    private int[][] localGridTopLeftCoords = {{0,0}, {0,6}, {3,3}, {6,0}, {6,6}};

    public GUI() {

        this.setTitle("Sudoku Solver");

        // Top menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu options = new JMenu("Options");
        solve = new JMenuItem("Solve");
        clear = new JMenuItem("Clear");
        options.add(solve);
        options.add(clear);
        this.setJMenuBar(menuBar);
        menuBar.add(options);
        solve.addActionListener(this);
        clear.addActionListener(this);

        // Instantiate array that will hold the grid input values
        inputValues = new int[GRID_LENGTH][GRID_LENGTH];

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(GRID_LENGTH, GRID_LENGTH));
        textBoxGrid = new JTextField[GRID_LENGTH][GRID_LENGTH];

        // Create text boxes to create the grid
        for (int row = 0; row < GRID_LENGTH; row++) {
            for (int column = 0; column < GRID_LENGTH; column++) {
                textBoxGrid[row][column] = new JTextField();
                textBoxGrid[row][column].setSize(BOX_SIZE, BOX_SIZE);
                textBoxGrid[row][column].setFont(new Font("Arial", Font.BOLD, 35));
                textBoxGrid[row][column].setHorizontalAlignment(JTextField.CENTER);
                this.add(textBoxGrid[row][column]);
            }
        }

        // Painting some 3x3 squares for visual clarity
        for (int i = 0; i < localGridTopLeftCoords.length; i++) {
            paintLocalSquares(localGridTopLeftCoords[i][0], localGridTopLeftCoords[i][1]);
        }

        this.setSize(FRAME_SIZE, FRAME_SIZE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == solve) {

            boolean isValidIntegers = true;

            // Store user input values in a 2D integer grid
            // If spaces are left empty, store 0 in place
            for (int row = 0; row < GRID_LENGTH; row++) {
                for (int column = 0; column < GRID_LENGTH; column++) {
                    if (textBoxGrid[row][column].getText().isEmpty())
                        inputValues[row][column] = 0;
                    else if (textBoxGrid[row][column].getText().length() == 1) {
                        try {
                            inputValues[row][column] = Integer.valueOf(textBoxGrid[row][column].getText());
                        }
                        catch (NumberFormatException ex) {
                            isValidIntegers = false;
                        }
                    }
                    else
                        isValidIntegers = false;
                }
            }
            if (isBoardValidInitially() && isValidIntegers)
                outputBoard();
            else
                boardReset();
        }

        if (e.getSource() == clear) {
            for (int row = 0; row < GRID_LENGTH; row++) {
                for (int column = 0; column < GRID_LENGTH; column++) {
                    textBoxGrid[row][column].setText("");
                }
            }
        }
    }

    public boolean isBoardValidInitially() {
        int[] listCheck = new int[GRID_LENGTH];

        // Check if rows contain a duplicate
        for (int i = 0; i < GRID_LENGTH; i++) {
            if (checkDuplicateInList(inputValues[i]))
                return false;
        }

        // Check if columns contain a duplicate
        for (int i = 0; i < GRID_LENGTH; i++) {
            for (int j = 0; j < GRID_LENGTH; j++)
                listCheck[j] = inputValues[j][i];
            if (checkDuplicateInList(listCheck))
                return false;
        }

        // Check local box contains a duplicate
        int counter = 0;
        for (int i = 0; i < localGridTopLeftCoords.length; i++) {
            int row = localGridTopLeftCoords[i][0];
            System.out.println(counter);
            if (counter == GRID_LENGTH)
                counter = 0;
            for (int y = row; y < LOCAL_BOX_LENGTH + row; y++) {
                int column = localGridTopLeftCoords[i][1];
                for (int x = column; x < LOCAL_BOX_LENGTH + column; x++) {
                    listCheck[counter] = inputValues[y][x];
                    counter++;
                }
            }
            if (checkDuplicateInList(listCheck))
                return false;
        }
        return true;
    }

    public void boardReset() {
        // Sets all values in the grid to 0
        for (int row = 0; row < GRID_LENGTH; row++) {
            for (int column = 0; column < GRID_LENGTH; column++) {
                textBoxGrid[row][column].setText(String.valueOf(0));
            }
        }
    }

    public boolean checkDuplicateInList(int[] list) {
        Set<Integer> duplicateTempList = new HashSet<Integer>();
        for (int i : list) {
            if (duplicateTempList.contains(i) && i != 0)
                return true;
            duplicateTempList.add(i);
        }
        return false;
    }

    public void outputBoard() {
        outputValues = Solver.solvedBoard(inputValues);
        for (int row = 0; row < GRID_LENGTH; row++) {
            for (int column = 0; column < GRID_LENGTH; column++) {
                textBoxGrid[row][column].setText(String.valueOf(outputValues[row][column]));
            }
        }
    }

    public void paintLocalSquares(int row, int column) {
        // Paint starting from top left of the 3x3 given by the coordinate
        for (int i = row; i < LOCAL_BOX_LENGTH + row; i++) {
            for (int j = column; j < LOCAL_BOX_LENGTH + column; j++)
                textBoxGrid[i][j].setBackground(Color.LIGHT_GRAY);
        }
    }
}
