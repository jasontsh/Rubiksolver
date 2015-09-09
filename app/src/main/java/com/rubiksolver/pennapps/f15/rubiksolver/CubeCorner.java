package com.rubiksolver.pennapps.f15.rubiksolver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason on 9/4/2015.
 */
public class CubeCorner extends CubePart{

    private int color1, color2, color3;
    private int c1, c2, c3;

    public CubeCorner(int co1, int co2, int co3, int cc1, int cc2, int cc3){
        color1 = co1;
        color2 = co2;
        color3 = co3;
        c1 = cc1;
        c2 = cc2;
        c3 = cc3;
    }

    public void change(int from, int to){
        if(c1 == from){
            c1 = to;
        } else if (c2 == from){
            c2 = to;
        } else{
            c3 = to;
        }
    }

    public int getColor(int colorcenter){
        if(colorcenter == c1){
            return color1;
        } else if (colorcenter == c2){
            return color2;
        } else{
            return color3;
        }
    }

    public Map<Integer, Integer> getValues(){
        HashMap<Integer, Integer> ans = new HashMap<Integer,Integer>();
        ans.put(c1, color1);
        ans.put(c2, color2);
        ans.put(c3, color3);
        return ans;
    }

    public boolean equals(Object other){
        if(other == null || !(other instanceof CubeCorner)){
            return false;
        }
        CubeCorner cc = (CubeCorner) other;
        return cc.getValues().equals(getValues());
    }

    public int hashCode(){
        return getValues().hashCode();
    }
}
