package com.android.pennapps.f15.rubiksolver;

/**
 * Created by He on 9/5/2015.
 */
public enum RubikColor {
    RED(new int[]{215, 30, 30}), GREEN(new int[]{0, 142, 40}), BLUE(new int[]{0, 91,
            170}), ORANGE(new int[]{243, 138, 36}), YELLOW(new int[]{224, 201,
            7}), WHITE(new int[]{233, 233, 233}), BLACK(new int[]{0, 0, 0});

    private int[] color = null;

    private RubikColor(int[] color) {
        this.color = color;
    }

    public int[] getColor() {
        return color;
    }
}
