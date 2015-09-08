package com.android.pennapps.f15.rubiksolver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason on 9/4/2015.
 */
public class CubeSide extends CubePart{

    private int color1, color2;
    private int color1On, color2On;

    public CubeSide(int c1,int c2, int cc1, int cc2){
        color1 = c1;
        color2 = c2;
        color1On = cc1;
        color2On = cc2;
    }

    public int[] nextTo(){
        int[] ans = new int[2];
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
        if (centercolor == color1On){
            return color1;
        } else{
            return color2;
        }
    }

    public void change(int from, int to){
        if (color1On == from){
            color1On = to;
        } else{
            color2On = to;
        }
    }

    public int getOtherColor(int centercolor){
        if (centercolor == color1On){
            return color2;
        } else{
            return color1;
        }
    }

    public Map<Integer, Integer> getValues(){
        HashMap<Integer, Integer> ans = new HashMap<Integer,Integer>();
        ans.put(color1On, color1);
        ans.put(color2On, color2);
        return ans;
    }

    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(!(other instanceof CubeSide)){
            return false;
        }
        return ((CubeSide) other).getValues().equals(getValues());
    }

    public int hashCode(){
        return getValues().hashCode();
    }
}
