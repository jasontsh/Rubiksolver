package com.rubiksolver.pennapps.f15.rubiksolver;

/**
 * Created by He on 9/5/2015.
 */
public enum Command {
    TOP_RIGHT("Rotate top row to right"), TOP_LEFT("Rotate top row to left"),
    BOTTOM_RIGHT("Rotate bottom row to right"), BOTTOM_LEFT("Rotate bottom row to left"),
    LEFT_DOWN("Rotate left column down"), LEFT_UP("Rotate left column up"),
    RIGHT_DOWN("Rotate right column down"), RIGHT_UP("Rotate right column up"),
    BACK_CW("Rotate back clockwise"), BACK_CCW("Rotate back counter-clockwise"),
    FRONT_CW("Rotate front clockwise"), FRONT_CCW("Rotate front counter-clockwise");

    private String commandText = null;

    private Command(String s) {
        commandText = s;
    }

    public String getText() {
        return commandText;
    }
}
