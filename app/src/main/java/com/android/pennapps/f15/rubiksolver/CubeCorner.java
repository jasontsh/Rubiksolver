package com.android.pennapps.f15.rubiksolver;

/**
 * Created by Jason on 9/4/2015.
 */
public class CubeCorner extends CubePart{
    int color1, color2, color3;
    CubeCenter c1, c2, c3;

    public CubeCorner(int co1, int co2, int co3, CubeCenter cc1, CubeCenter cc2, CubeCenter cc3){
        color1 = co1;
        color2 = co2;
        color3 = co3;
        c1 = cc1;
        c2 = cc2;
        c3 = cc3;
    }

    public int getColor(int colorcenter){
        if(colorcenter == c1.getColor()){
            return color1;
        } else if (colorcenter == c2.getColor()){
            return color2;
        } else{
            return color3;
        }
    }
}
