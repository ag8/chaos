package org.ag.ants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static final int DENSITY = 13; // out of 100


    public static final int DIM = 100;
    private static final int STEPS = 1000000;

    BWGridCell[][] grid;
    List<LangtonsAnt> ants;

    List<Double> whiteFraction;

    static Random rand = new Random(123L);

    public static void main(String[] args) throws InterruptedException {
        new Main().run();
    }

    private void run() throws InterruptedException {
        // Initialize grid and ants

        whiteFraction = new ArrayList<>();

        grid = new BWGridCell[DIM][DIM];

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                grid[i][j] = new BWGridCell();
            }
        }


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        JFrame frame = new JFrame("Langton's Ants!");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        GridDisplayPane g = new GridDisplayPane(grid);
        frame.add(g);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        ants = new ArrayList<>();
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (randInt(0, 100) < DENSITY) {
                    ants.add(new LangtonsAnt(i, j));
                }
            }
        }


        // Run it!

        for (int i = 0; i < STEPS; i++) {
//            System.out.println("Step " + i);
            for (LangtonsAnt ant : ants) {
                BWGridCell current = grid[ant.getX()][ant.getY()];
                int[] toChange = ant.move(current.getState());
                grid[toChange[0]][toChange[1]].toggle();
            }

            Thread.sleep(10);
            frame.setTitle("Langton's Ants! Step " + i + "/" + STEPS);
            g.draw(grid);

            saveStats();
        }

        System.out.println(whiteFraction);
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

    public static int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
