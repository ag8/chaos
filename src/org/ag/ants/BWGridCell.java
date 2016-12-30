package org.ag.ants;

import org.ag.elements.BWCell;

public class BWGridCell implements BWCell {
    private boolean state;

    public BWGridCell() {
        this.state = false;
    }

    public BWGridCell(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return this.state;
    }

    public boolean isFalse() {
        return !this.state;
    }

    @Override
    public void toggle() {
        state = !state;
    }

    public String toString() {
        return "" + state + "";
    }
}
