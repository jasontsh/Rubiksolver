package com.android.pennapps.f15.rubiksolver;

/**
 * Created by Jason on 9/4/2015.
 */
public abstract class CubePart {
    public static int W = 0;
    public static int B = 1;
    public static int R = 2;
    public static int Y = 3;
    public static int G = 4;
    public static int O = 5;

    public abstract int getColor(int centercolor);

    public abstract void change(int from, int to);
}
