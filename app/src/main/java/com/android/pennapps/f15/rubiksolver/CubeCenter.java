package com.android.pennapps.f15.rubiksolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jason on 9/4/2015.
 */
public class CubeCenter extends CubePart{
    int color;
    Map<Integer, CubeSide> sides;
    Map<Integer, CubeCorner> corners;
    public CubeCenter(int c){
        color = c;
        sides = new HashMap<Integer, CubeSide>();
        corners = new HashMap<Integer, CubeCorner>();
    }

    public int getColor(){
        return color;
    }

    public void setSide(int color, CubeSide cs){
        sides.put(color, cs);
    }

    public void setCorner(int set, CubeCorner cn){
        corners.put(set, cn);
    }

    public CubeSide getSide(int color){
        return sides.get(color);
    }

    public CubeCorner getCorner(int key){
        return corners.get(key);
    }

    public int getColor(int colorcenter){
        return color;
    }

    public void change(int from, int to){

    }

    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(!(other instanceof CubeCenter)){
            return false;
        }
        CubeCenter cc = (CubeCenter) other;
        return cc.getColor() == getColor();
    }

    public int hashCode(){
        return getColor();
    }

}
