package org.ag.ants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.ag.ants.Main.DIM;

public class GridDisplayPane extends JPanel {
    private BWGridCell[][] grid;
    private List<Rectangle> cells;

    public GridDisplayPane(BWGridCell[][] grid) {
        this.grid = grid.clone();
        cells = new ArrayList<>(DIM * DIM);
    }

    public void draw(BWGridCell[][] grid) {
        this.grid = grid.clone();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);
    }

    @Override
    public void invalidate() {
        cells.clear();
        super.invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        int cellWidth = width / DIM;
        int cellHeight = height / DIM;

        int xOffset = (width - (DIM * cellWidth)) / 2;
        int yOffset = (height - (DIM * cellHeight)) / 2;

        if (cells.isEmpty()) {
            for (int row = 0; row < DIM; row++) {
                for (int col = 0; col < DIM; col++) {
                    Rectangle cell = new Rectangle(
                            xOffset + (col * cellWidth),
                            yOffset + (row * cellHeight),
                            cellWidth,
                            cellHeight);
                    cells.add(cell);
                }
            }
        }

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                int index = i + (j * DIM);
                Rectangle cell = cells.get(index);
                g2d.setColor(grid[i][j].getState() ? Color.WHITE : Color.BLACK);
                g2d.fill(cell);
            }
        }

        for (Rectangle cell : cells) {
            g2d.draw(cell);
        }

        g2d.dispose();
    }

}
