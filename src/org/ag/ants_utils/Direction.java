package org.ag.ants_utils;

public class Direction {
    private int dir;
    /*

       [1]
    [0] A [2]
       [3]

     */

    public Direction() {
        this.dir = 0;
    }

    public Direction(int dir) {
        this.dir = dir;
    }

    private void fit() {
        this.dir = this.dir < 0 ? 4 + this.dir : this.dir % 4;
    }

    public void left() {
        this.dir--;
        fit();
//        System.out.println(dir);
    }

    public void right() {
        this.dir++;
        fit();
//        System.out.println(dir);
    }

    public void reverse() {
        this.dir += 2;
        fit();
    }

    public int getDirection() {
        return dir;
    }

    public String english() {
        switch (dir) {
            case 0:
                return "left";
            case 1:
                return "up";
            case 2:
                return "right";
            case 3:
                return "down";
            default:
                return "in an unknown direction";
        }
    }
}
