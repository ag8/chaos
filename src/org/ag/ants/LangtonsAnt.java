package org.ag.ants;

import org.ag.ants_utils.Direction;
import org.ag.elements.Ant;

import static org.ag.ants.Main.*;

public class LangtonsAnt implements Ant {
    private int x;
    private int y;
    private Direction dir;

    public LangtonsAnt(int x, int y) {
        this.x = x;
        this.y = y;
        this.dir = new Direction(0);
    }

    public LangtonsAnt(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = new Direction(dir);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] move(boolean state) {
//        System.out.println("Pointing " + this.dir.english());

        if (state) {
            this.dir.right();
        } else {
            this.dir.left();
        }

//        System.out.println("Now pointing " + this.dir.english());

        int[] changed = new int[]{this.x, this.y};

        moveForward();

        return changed;
    }

    private void moveForward() {
        if (this.dir.getDirection() == 0) {
            this.x--;

            if (this.x < 0) {
                this.x = DIM - 1;
            }
        }
        if (this.dir.getDirection() == 1) {
            this.y--;

            if (this.y < 0) {
                this.y = DIM - 1;
            }
        }
        if (this.dir.getDirection() == 2) {
            this.x++;

            if (this.x > DIM - 1) {
                this.x = 0;
            }
        }
        if (this.dir.getDirection() == 3) {
            this.y++;

            if (this.y > DIM - 1) {
                this.y = 0;
            }
        }
    }
}
