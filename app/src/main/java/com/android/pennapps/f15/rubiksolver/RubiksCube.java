package com.android.pennapps.f15.rubiksolver;

import java.util.HashSet;

/**
 * Created by Jason on 9/4/2015.
 */
public class RubiksCube {
    int colorFace;
    int colorUp;
    CubeCenter[] centers;

    public RubiksCube(int[][][] array){
        CubeCenter w = new CubeCenter(0);
        CubeCenter b = new CubeCenter(1);
        CubeCenter r = new CubeCenter(2);
        CubeCenter y = new CubeCenter(3);
        CubeCenter g = new CubeCenter(4);
        CubeCenter o = new CubeCenter(5);
        CubeCorner wbo = new CubeCorner(array[0][0][0], array[1][2][2], array[5][0][0], w, b, o);
        CubeSide wb = new CubeSide(array[0][1][0], array[1][2][1], w, b);
        CubeCorner wbr = new CubeCorner(array[0][2][0], array[1][2][0], array[2][0][2], w, b, r);
        CubeSide wo = new CubeSide(array[0][0][1], array[5][1][0], w, o);
        CubeSide wr = new CubeSide(array[0][2][1], array[2][1][2], w, r);
        CubeCorner wgo = new CubeCorner(array[0][0][2], array[4][2][0], array[5][2][0], w, g, o);
        CubeSide wg = new CubeSide(array[0][1][2], array[4][2][1], w, g);
        CubeCorner wrg = new CubeCorner(array[0][2][2], array[2][2][2], array[4][2][2], w, r, g);
        CubeSide br = new CubeSide(array[1][1][0], array[2][0][1], b, r);
        CubeSide rg = new CubeSide(array[2][2][1], array[4][1][2], r, g);
        CubeSide go = new CubeSide(array[4][1][2], array[5][2][1], g, o);
        CubeSide bo = new CubeSide(array[1][1][2], array[5][0][1],  b, o);
        CubeCorner ygo = new CubeCorner(array[3][0][0], array[4][0][0], array[5][2][2], y, g, o);
        CubeSide yg = new CubeSide(array[3][1][0], array[4][0][1], y, g);
        CubeCorner ryg = new CubeCorner(array[2][2][0], array[3][2][0], array[4][0][2], r, y, g);
        CubeSide yo = new CubeSide(array[3][0][1], array[5][1][2], y, o);
        CubeSide ry = new CubeSide(array[2][1][0], array[3][2][1], r, y);
        CubeCorner byo = new CubeCorner(array[1][0][2], array[3][0][2], array[5][0][2], b, y, o);
        CubeSide by = new CubeSide(array[1][0][1], array[3][1][2], b, y);
        CubeCorner bry = new CubeCorner(array[1][0][0], array[2][0][0], array[3][2][2], b, r, y);

        w.setSide(1, wb);
        w.setSide(2, wr);
        w.setSide(4, wg);
        w.setSide(5, wo);
        HashSet<Integer> w1 = new HashSet<Integer>();
        w1.add(1);
        w1.add(5);
        w.setCorner(w1, wbo);
        HashSet<Integer> w2 = new HashSet<Integer>();
        w2.add(1);
        w2.add(2);
        w.setCorner(w2, wbr);
        HashSet<Integer> w3 = new HashSet<Integer>();
        w3.add(4);
        w3.add(5);
        w.setCorner(w3, wgo);
        HashSet<Integer> w4 = new HashSet<Integer>();
        w4.add(2);
        w4.add(4);
        w.setCorner(w4, wrg);

        b.setSide(0, wb);
        b.setSide(2, br);
        b.setSide(4, by);
        b.setSide(5, bo);
        HashSet<Integer> b1 = new HashSet<Integer>();
        b1.add(0);
        b1.add(2);
        b.setCorner(b1, wbr);
        HashSet<Integer> b2 = new HashSet<Integer>();
        b2.add(0);
        b2.add(5);
        b.setCorner(b2, wbo);
        HashSet<Integer> b3 = new HashSet<Integer>();
        b3.add(3);
        b3.add(2);
        b.setCorner(b3, bry);
        HashSet<Integer> b4 = new HashSet<Integer>();
        b4.add(3);
        b4.add(5);
        b.setCorner(b4, byo);

        r.setSide(0, wr);
        r.setSide(1, br);
        r.setSide(3, ry);
        r.setSide(4, rg);
        HashSet<Integer> r1 = new HashSet<Integer>();
        r1.add(0);
        r1.add(1);
        r.setCorner(r1, wbr);
        HashSet<Integer> r2 = new HashSet<Integer>();
        r2.add(1);
        r2.add(3);
        r.setCorner(r2, bry);
        HashSet<Integer> r3 = new HashSet<Integer>();
        r3.add(4);
        r3.add(0);
        r.setCorner(r3, wrg);
        HashSet<Integer> r4 = new HashSet<Integer>();
        r4.add(4);
        r4.add(3);
        r.setCorner(r4, ryg);

        y.setSide(1, by);
        y.setSide(2, ry);
        y.setSide(4, yg);
        y.setSide(5, yo);
        HashSet<Integer> y1 = new HashSet<Integer>();
        y1.add(1);
        y1.add(2);
        y.setCorner(y1, bry);
        HashSet<Integer> y2 = new HashSet<Integer>();
        y2.add(1);
        y2.add(5);
        y.setCorner(y2, byo);
        HashSet<Integer> y3 = new HashSet<Integer>();
        y3.add(2);
        y3.add(4);
        y.setCorner(y3, ryg);
        HashSet<Integer> y4 = new HashSet<Integer>();
        y4.add(4);
        y4.add(5);
        y.setCorner(y4, ygo);

        g.setSide(0, wg);
        g.setSide(2, rg);
        g.setSide(3, yg);
        g.setSide(5, go);
        HashSet<Integer> g1 = new HashSet<Integer>();
        g1.add(0);
        g1.add(2);
        g.setCorner(g1, wrg);
        HashSet<Integer> g2 = new HashSet<Integer>();
        g2.add(2);
        g2.add(3);
        g.setCorner(g2, ryg);
        HashSet<Integer> g3 = new HashSet<Integer>();
        g3.add(3);
        g3.add(5);
        g.setCorner(g3, ygo);
        HashSet<Integer> g4 = new HashSet<Integer>();
        g4.add(5);
        g4.add(0);
        g.setCorner(g4, wgo);

        o.setSide(0, wo);
        o.setSide(1, bo);
        o.setSide(3, yo);
        o.setSide(4, go);
        HashSet<Integer> o1 = new HashSet<Integer>();
        o1.add(0);
        o1.add(1);
        o.setCorner(o1, wbo);
        HashSet<Integer> o2 = new HashSet<Integer>();
        o2.add(1);
        o2.add(3);
        o.setCorner(o2, byo);
        HashSet<Integer> o3 = new HashSet<Integer>();
        o3.add(3);
        o3.add(4);
        o.setCorner(o3, ygo);
        HashSet<Integer> o4 = new HashSet<Integer>();
        o4.add(0);
        o4.add(4);
        o.setCorner(o4, wgo);

        centers[0] = w;
        centers[1] = b;
        centers[2] = r;
        centers[3] = y;
        centers[4] = g;
        centers[5] = o;
    }

    public CubeCenter[] getCenters(){
        return centers;
    }
}
