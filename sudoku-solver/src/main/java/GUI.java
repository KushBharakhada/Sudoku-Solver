import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    // Constants
    private static final int GRID_LENGTH = 9;
    private static final int LOCAL_BOX_LENGTH = 3;
    private static final int FRAME_SIZE = 540;
    private static final int BOX_SIZE = FRAME_SIZE / GRID_LENGTH;

    private JTextField[][] textBoxGrid;
    private JMenuItem solve;
    private int[][] inputValues;

    public GUI() {

        // Top menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu options = new JMenu("Options");
        solve = new JMenuItem("Solve");
        options.add(solve);
        this.setJMenuBar(menuBar);
        menuBar.add(options);
        solve.addActionListener(this);

        // Instantiate array that will hold the grid input values
        inputValues = new int[9][9];

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(GRID_LENGTH, GRID_LENGTH));
        textBoxGrid = new JTextField[GRID_LENGTH][GRID_LENGTH];

        // Create text boxes to create the grid
        for (int row = 0; row < GRID_LENGTH; row++) {
            for (int column = 0; column < GRID_LENGTH; column++) {
                textBoxGrid[row][column] = new JTextField();
                textBoxGrid[row][column].setSize(BOX_SIZE, BOX_SIZE);
                textBoxGrid[row][column].setFont(new Font("Consoles", Font.PLAIN, 35));
                this.add(textBoxGrid[row][column]);
            }
        }

        // Painting some 3x3 squares for visual clarity
        int[][] paintLocalGrids = {{0,0}, {0,6}, {3,3}, {6,0}, {6,6}};
        for (int i = 0; i < paintLocalGrids.length; i++) {
            paintLocalSquares(paintLocalGrids[i][0], paintLocalGrids[i][1]);
        }

        this.setSize(FRAME_SIZE, FRAME_SIZE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == solve) {
            for (int row = 0; row < GRID_LENGTH; row++) {
                for (int column = 0; column < GRID_LENGTH; column++) {
                    System.out.println(textBoxGrid[row][column].getText());
                    if (textBoxGrid[row][column].getText().isEmpty()) {
                        System.out.println("wow");
                    }
                }
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
