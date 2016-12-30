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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BWGridCell that = (BWGridCell) o;

        return state == that.state;

    }

    @Override
    public int hashCode() {
        return (state ? 1 : 0);
    }

    public String toString() {
        return "" + state + "";
    }
}
