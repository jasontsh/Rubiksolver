package com.android.pennapps.f15.rubiksolver;

/**
 * Created by He on 9/5/2015.
 */
public enum SetupState {
    SHOW_SIDE_WHITE("Show side where center slot is white"), SHOW_SIDE_LEFT("Turn cube left (1/4)"), SHOW_SIDE_LEFT_2("Turn cube left (2/4)"), SHOW_SIDE_LEFT_3("Turn cube left again (3/4)"), SHOW_SIDE_LEFT_4("Turn the cube left again (4/4)"), SHOW_BOTTOM_SIDE("Turn cube to bottom (1/4)"), SHOW_BOTTOM_SIDE_2("Turn cube to bottom (2/4)"), SHOW_BOTTOM_SIDE_3("Turn cube to bottom 3/4"), SHOW_BOTTOM_SIDE_4("Turn cube to bottom 4/4"), SOLVE("Solving...");

    private String text = null;

    private SetupState(String s) {
        text = s;
    }

    public String getText() {
        return text;
    }
}
