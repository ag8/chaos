package org.ag.ants;

import javax.swing.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
//    public static final int DENSITY = 1; // out of 100


    public static int DIM;
    private static final BigInteger STEPS = BigInteger.TEN.pow(10);
    private static final BigInteger LOOP_STATE_STEP = BigInteger.TEN.multiply(BigInteger.ONE.add(BigInteger.ONE));

    BWGridCell[][] grid;
    List<LangtonsAnt> ants;

    List<Double> whiteFraction;
    BWGridCell[][] loopState;

//    static Random rand = new Random(123L);

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("Hi!");

        for (int i = 1; i < 100; i++) {
            System.out.println("For a torus of size " + i + ", each loop takes " + new Main().run(i) + " steps.");
        }
//        System.out.println("a");
//        System.out.println(new Main().run(4));
//        System.out.println("b");
//        System.out.println(new Main().run(5));
    }

    private BigInteger run(int dim) throws InterruptedException {
        DIM = dim;


        // Initialize grid and ants

//        System.out.println("c");

        whiteFraction = new ArrayList<>();

        grid = new BWGridCell[DIM][DIM];

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                grid[i][j] = new BWGridCell();
            }
        }

//        System.out.println("Running!");


//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//        }

//        JFrame frame = new JFrame("Langton's Ants!");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setLayout(new BorderLayout());
//        GridDisplayPane g = new GridDisplayPane(grid);
//        frame.add(g);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);


        ants = new ArrayList<>();
//        int m = DIM / 2 - 3;
//        int n = DIM / 2;
//        ants.add(new LangtonsAnt(m, m, 0));
//        ants.add(new LangtonsAnt(n, m, 1));
//        ants.add(new LangtonsAnt(n, n, 2));
//        ants.add(new LangtonsAnt(m, n, 3));
//        ants.add(new LangtonsAnt(m, m, 3));
//        ants.add(new LangtonsAnt(n, m, 2));
//        ants.add(new LangtonsAnt(n, n, 1));
//        ants.add(new LangtonsAnt(m, n, 0));
        ants.add(new LangtonsAnt(0, 0, 0)); // Doesn't matter where since it's a torus


        // Run it!

        for (BigInteger i = BigInteger.ONE; i.compareTo(STEPS) < 0; i = i.add(BigInteger.ONE)) {
//            System.out.println(i);
            if (i.equals(LOOP_STATE_STEP)) {
//                System.out.println("Saving loop state.");
                saveLoopState();
            }

//            System.out.println("Step " + i);
            for (LangtonsAnt ant : ants) {
                BWGridCell current = grid[ant.getX()][ant.getY()];
                int[] toChange = ant.move(current.getState());
                grid[toChange[0]][toChange[1]].toggle();
            }

//            Thread.sleep(100);
//            frame.setTitle("Langton's Ants! Step " + i + "/" + STEPS);
//            g.draw(grid);

            boolean loop = checkLoop();
            if (loop) {
                return i.subtract(LOOP_STATE_STEP); // Number of steps in loop
            }
        }

//        System.out.println(whiteFraction);
        return BigInteger.ZERO.subtract(BigInteger.ONE);
    }

    private boolean checkLoop() {
//        System.out.println(Arrays.deepToString(loopState));
//        System.out.println(Arrays.deepToString(grid));
        return Arrays.deepEquals(loopState, grid);
    }

    private void saveLoopState() {
        loopState = new BWGridCell[DIM][DIM];

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                loopState[i][j] = new BWGridCell(grid[i][j].getState());
            }
        }
    }

    private void saveStats() {
        whiteFraction.add(getWhiteFraction());
    }

    private Double getWhiteFraction() {
        int c = 0;

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (grid[i][j].getState()) {
                    c++;
                }
            }
        }

        return (double) c / Math.pow((double) DIM, (double) 2);
    }

//    public static int randInt(int min, int max) {
//        return rand.nextInt((max - min) + 1) + min;
//    }
}
