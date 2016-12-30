package org.ag.ants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int DIM = 100;
    private static final int STEPS = 10000;

    BWGridCell[][] grid;
    List<LangtonsAnt> ants;

    List<Double> whiteFraction;


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
        for (int i = 0; i < DIM; i += 3) {
            for (int j = 0; j < DIM; j += 7) {
                ants.add(new LangtonsAnt(i, j));
            }
        }


        // Run it!

        for (int i = 0; i < STEPS; i++) {
//            System.out.println("Step " + i);
            for (LangtonsAnt ant : ants) {
                if (ant.getX() < 0 || ant.getY() < 0 || ant.getX() > DIM - 1 || ant.getY() > DIM - 1) {
                    continue;
                }

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
}
