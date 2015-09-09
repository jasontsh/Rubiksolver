package com.rubiksolver.pennapps.f15.rubiksolver;

/**
 * Created by Jason on 9/4/2015.
 */
public class RubiksCube {

    private CubeCenter[] centers;

    static int key(int a, int b) {
        if (b > a) {
            int temp = a;
            a = b;
            b = temp;
        }
        return (a + b) * (a + b + 1) / 2 + b;
    }


    public RubiksCube(int[][][] array) {
        CubeCenter w = new CubeCenter(0);
        CubeCenter b = new CubeCenter(1);
        CubeCenter r = new CubeCenter(2);
        CubeCenter y = new CubeCenter(3);
        CubeCenter g = new CubeCenter(4);
        CubeCenter o = new CubeCenter(5);
        CubeCorner wbo = new CubeCorner(array[0][0][0], array[1][2][2], array[5][0][0], 0, 1, 5);
        CubeSide wb = new CubeSide(array[0][1][0], array[1][2][1], 0, 1);
        CubeCorner wbr = new CubeCorner(array[0][2][0], array[1][2][0], array[2][0][2], 0, 1, 2);
        CubeSide wo = new CubeSide(array[0][0][1], array[5][1][0], 0, 5);
        CubeSide wr = new CubeSide(array[0][2][1], array[2][1][2], 0, 2);
        CubeCorner wgo = new CubeCorner(array[0][0][2], array[4][2][0], array[5][2][0], 0, 4, 5);
        CubeSide wg = new CubeSide(array[0][1][2], array[4][2][1], 0, 4);
        CubeCorner wrg = new CubeCorner(array[0][2][2], array[2][2][2], array[4][2][2], 0, 2, 4);
        CubeSide br = new CubeSide(array[1][1][0], array[2][0][1], 1, 2);
        CubeSide rg = new CubeSide(array[2][2][1], array[4][1][2], 2, 4);
        CubeSide go = new CubeSide(array[4][1][0], array[5][2][1], 4, 5);
        CubeSide bo = new CubeSide(array[1][1][2], array[5][0][1], 1, 5);
        CubeCorner ygo = new CubeCorner(array[3][0][0], array[4][0][0], array[5][2][2], 3, 4, 5);
        CubeSide yg = new CubeSide(array[3][1][0], array[4][0][1], 3, 4);
        CubeCorner ryg = new CubeCorner(array[2][2][0], array[3][2][0], array[4][0][2], 2, 3, 4);
        CubeSide yo = new CubeSide(array[3][0][1], array[5][1][2], 3, 5);
        CubeSide ry = new CubeSide(array[2][1][0], array[3][2][1], 2, 3);
        CubeCorner byo = new CubeCorner(array[1][0][2], array[3][0][2], array[5][0][2], 1, 3, 5);
        CubeSide by = new CubeSide(array[1][0][1], array[3][1][2], 1, 3);
        CubeCorner bry = new CubeCorner(array[1][0][0], array[2][0][0], array[3][2][2], 1, 2, 3);

        w.setSide(1, wb);
        w.setSide(2, wr);
        w.setSide(4, wg);
        w.setSide(5, wo);
        w.setCorner(key(1, 5), wbo);
        w.setCorner(key(1, 2), wbr);
        w.setCorner(key(4,5), wgo);
        w.setCorner(key(2,4), wrg);

        b.setSide(0, wb);
        b.setSide(2, br);
        b.setSide(3, by);
        b.setSide(5, bo);
        b.setCorner(key(0,2), wbr);
        b.setCorner(key(0,5), wbo);
        b.setCorner(key(3,2), bry);
        b.setCorner(key(3,5), byo);

        r.setSide(0, wr);
        r.setSide(1, br);
        r.setSide(3, ry);
        r.setSide(4, rg);
        r.setCorner(key(0,1), wbr);
        r.setCorner(key(1,3), bry);
        r.setCorner(key(4,0), wrg);
        r.setCorner(key(4,3), ryg);

        y.setSide(1, by);
        y.setSide(2, ry);
        y.setSide(4, yg);
        y.setSide(5, yo);
        y.setCorner(key(1,2), bry);
        y.setCorner(key(1,5), byo);
        y.setCorner(key(2,4), ryg);
        y.setCorner(key(4,5), ygo);

        g.setSide(0, wg);
        g.setSide(2, rg);
        g.setSide(3, yg);
        g.setSide(5, go);
        g.setCorner(key(0,2), wrg);
        g.setCorner(key(2,3), ryg);
        g.setCorner(key(3,5), ygo);
        g.setCorner(key(5,0), wgo);

        o.setSide(0, wo);
        o.setSide(1, bo);
        o.setSide(3, yo);
        o.setSide(4, go);
        o.setCorner(key(0,1), wbo);
        o.setCorner(key(1,3), byo);
        o.setCorner(key(3,4), ygo);
        o.setCorner(key(0,4), wgo);
        centers = new CubeCenter[6];
        centers[0] = w;
        centers[1] = b;
        centers[2] = r;
        centers[3] = y;
        centers[4] = g;
        centers[5] = o;
    }

    public CubeCenter[] getCenters() {
        return centers;
    }

    /**
     * This function updates the values of the rubiks cube according to the turn.
     * The from and to colors must be next to each other.
     *
     * @param color the color of the center of which is turning
     * @param from  one of the side colors, where the turn starts.
     * @param to    one of the side colors, where the turn ends.
     */
    public void turn(int color, int from, int to) {
        CubeCenter c = centers[color];
        int to2 = (from + 3) % 6;
        int to3 = (to + 3) % 6;
        //update the individual first...
        int s1 = key(from, to3);
        int s2 = key(from, to);
        CubeCorner cc1 = c.getCorner(s1);
        CubeSide cs1 = c.getSide(from);
        CubeCorner cc2 = c.getCorner(s2);
        cc1.change(from, to);
        cc1.change(to3, from);
        cs1.change(from, to);
        cc2.change(to, to2);
        cc2.change(from, to);

        CubeSide cs2 = c.getSide(to);
        int s3 = key(to, to2);
        CubeCorner cc3 = c.getCorner(s3);
        cs2.change(to, to2);
        cc3.change(to2, to3);
        cc3.change(to, to2);

        CubeSide cs3 = c.getSide(to2);
        int s4 = key(to2, to3);
        CubeCorner cc4 = c.getCorner(s4);
        CubeSide cs4 = c.getSide(to3);
        cs3.change(to2, to3);
        cc4.change(to3, from);
        cc4.change(to2, to3);
        cs4.change(to3, from);

        c.setCorner(s1, cc4);
        c.setCorner(s2, cc1);
        c.setCorner(s3, cc2);
        c.setCorner(s4, cc3);
        c.setSide(from, cs4);
        c.setSide(to, cs1);
        c.setSide(to2, cs2);
        c.setSide(to3, cs3);

        //change the from:
        CubeCenter cf = centers[from];
        int cf1 = key(to3, color);
        cf.setCorner(cf1, cc4);
        cf.setSide(color, cs4);

        int cf2 = key(to, color);
        cf.setCorner(cf2, cc1);

        CubeCenter ct1 = centers[to];
        int ct11 = key(from, color);
        ct1.setCorner(ct11, cc1);
        ct1.setSide(color, cs1);
        int ct12 = key(to2, color);
        ct1.setCorner(ct12, cc2);

        CubeCenter ct2 = centers[to2];
        int ct21 = key(to, color);
        ct2.setCorner(ct21, cc2);
        ct2.setSide(color, cs2);
        int ct22 = key(to3, color);
        ct2.setCorner(ct22, cc3);

        CubeCenter ct3 = centers[to3];
        int ct31 = key(to2, color);
        ct3.setCorner(ct31, cc3);
        ct3.setSide(color, cs3);
        int ct32 = key(from, color);
        ct3.setCorner(ct32, cc4);
    }
}

