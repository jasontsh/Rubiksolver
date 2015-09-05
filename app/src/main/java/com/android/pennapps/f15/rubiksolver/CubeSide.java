package com.android.pennapps.f15.rubiksolver;

/**
 * Created by Jason on 9/4/2015.
 */
public class CubeSide extends CubePart{
    int color1, color2;
    CubeCenter color1On, color2On;
    public CubeSide(int c1,int c2, CubeCenter cc1, CubeCenter cc2){
        color1 = c1;
        color2 = c2;
        color1On = cc1;
        color2On = cc2;
    }

    public CubeCenter[] nextTo(){
        CubeCenter[] ans = new CubeCenter[2];
        ans[0] = color1On;
        ans[1] = color2On;
        return ans;
    }

    public int getColor1(){
        return color1;
    }

    public int getColor2(){
        return color2;
    }

    public int getColor(int centercolor){
        if (centercolor == color1On.getColor()){
            return color1;
        } else{
            return color2;
        }
    }
}
