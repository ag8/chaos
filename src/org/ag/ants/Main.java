package org.ag.ants;

import org.ag.ants_utils.Direction;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
//    public static final int DENSITY = 1; // out of 100


    private static final BigInteger STEPS = BigInteger.TEN.pow(10);
    private static final BigInteger LOOP_STATE_STEP = BigInteger.TEN.multiply(BigInteger.ONE.add(BigInteger.ONE));
    private static final boolean DISPLAY = false;
    public static int DIM;
    BWGridCell[][] grid;
    List<LangtonsAnt> ants;

    List<Double> whiteFraction;
    BWGridCell[][] loopState;
    Direction loopStateDirection;
    int[] loopStatePosition;

//    static Random rand = new Random(123L);

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("Hi!");

        for (int i = 1; i < 14; i++) {
            BigInteger numSteps = new Main().run(i);
            BigDecimal percentageOfPossibleVariants = new BigDecimal(numSteps).divide(BigDecimal.ONE.add(BigDecimal.ONE).pow(i * i), 10, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.TEN.multiply(BigDecimal.TEN));
            System.out.println("\tFor a torus of size " + i + ", each loop takes " + numSteps + " steps. (" + percentageOfPossibleVariants + "% visited)");
        }
//        System.out.println("a");
//        System.out.println(new Main().run(4));
//        System.out.println("b");
//        System.out.println(new Main().run(5));
    }

    private BigInteger run(int dim) throws InterruptedException {
//        System.out.println("Run " + dim + ".");

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


        JFrame frame = new JFrame("Langton's Ants!");
        GridDisplayPane g = new GridDisplayPane(grid);
        if (DISPLAY) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            }

            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(g);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }


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
            if (i.mod(BigInteger.TEN.pow(7)).equals(BigInteger.ZERO)) {
//                System.out.println("\tCurrently on step " + i + ". (At least " + new BigDecimal(i).divide(BigDecimal.ONE.add(BigDecimal.ONE).pow(dim * dim), 120, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)) + "% complete.");
            }

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

            if (DISPLAY) {
                Thread.sleep(1000);
                frame.setTitle("Langton's Ants! Step " + i + "/" + STEPS + " (Dir = " + ants.get(0).getDirection() + ")");
                g.draw(grid);
            }

            boolean loop = checkLoop();
            if (loop) {
                return i.subtract(LOOP_STATE_STEP.subtract(BigInteger.ONE)); // Number of steps in loop
            }
        }

//        System.out.println(whiteFraction);
        return BigInteger.ZERO.subtract(BigInteger.ONE);
    }

    private boolean checkLoop() {
//        System.out.println(Arrays.deepToString(loopState));
//        System.out.println(Arrays.deepToString(grid));
        return Arrays.deepEquals(loopState, grid) && (ants.get(0).getDirection().equals(loopStateDirection)) && loopStatePosition[0] == ants.get(0).getX() && loopStatePosition[1] == ants.get(0).getY();
    }

    private void saveLoopState() {
        loopState = new BWGridCell[DIM][DIM];

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                loopState[i][j] = new BWGridCell(grid[i][j].getState());
            }
        }

        loopStateDirection = ants.get(0).getDirection();

        loopStatePosition = new int[]{ants.get(0).getX(), ants.get(0).getY()};
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
