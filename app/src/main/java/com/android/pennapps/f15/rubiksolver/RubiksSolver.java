package com.android.pennapps.f15.rubiksolver;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import static com.android.pennapps.f15.rubiksolver.RubiksCube.key;

/**
 * Created by Jason on 9/5/2015.
 */
public class RubiksSolver {
    /**
     * 0  when doing the cross, 1 when doing corners, 2 when 2nd layer, 3 when switch up, 4 when
     * change sides, 5 when relocate corners, 6 flip corners
     */
    int state;
    Set<CubePart> locked;
    Queue<Integer[]> queue;
    RubiksCube cube;

    public RubiksSolver(RubiksCube c) {
        state = 0;
        cube = c;
        queue = new LinkedList<Integer[]>();
        locked = new HashSet<CubePart>();
    }

    public Integer[] turn() {
        if (!queue.isEmpty()) {
            return queue.remove();
        }
        Integer[] answer = new Integer[3];
        answer[0] = 0;
        answer[1] = 0;
        answer[2] = 0;
        switch (state) {
            case 0:
                state0(answer);
                break;
            case 1:
                state1(answer);
                break;
            case 2:
                state2(answer);
                break;
            case 3:
                state3(answer);
                break;
            case 4:
                state4(answer);
                break;
            case 5:
                state5(answer);
                break;
            //            case 6: state6(answer); break;
        }
        return answer;
    }

    public void state0(Integer[] answer) {
        locked = new HashSet<CubePart>();
        //check for updates on the white cross
        Set<Integer> left = new HashSet<Integer>();
        left.add(1);
        left.add(2);
        left.add(4);
        left.add(5);
        for (Integer i : left) {
            if (cube.getCenters()[0].getSide(i).getColor(0) == 0 &&
                    cube.getCenters()[0].getSide(i).getColor(i) == i) {
                locked.add(cube.getCenters()[0].getSide(i));
            }
        }
        for (CubePart cs : locked) {
            left.remove(((CubeSide) cs).getOtherColor(0));
        }
        if (locked.size() >= 4) {
            state++;
            state1(answer);
            return;
        }

        //first go through the top layer with white on top already
        for (Integer i : left) {
            if (cube.getCenters()[0].getSide(i).getColor(0) == 0) {
                Set<Integer> b = new HashSet<Integer>();
                b.add(1);
                b.add(2);
                b.add(4);
                b.add(5);
                b.remove(i);
                b.remove((i + 3) % 6);
                int side = b.iterator().next();
                answer[0] = i;
                answer[1] = 0;
                answer[2] = side;
                Integer[] q1 = new Integer[3];
                q1[0] = i;
                q1[1] = side;
                q1[2] = 3;
                queue.add(q1);
                int othercolor = cube.getCenters()[0].getSide(i).getOtherColor(0);
                if ((i + 3) % 6 == othercolor) {
                    Integer[] q2 = new Integer[3];
                    q2[0] = 3;
                    q2[1] = i;
                    q2[2] = side;
                    queue.add(q2);
                    Integer[] q3 = new Integer[3];
                    q3[0] = 3;
                    q3[1] = side;
                    q3[2] = othercolor;
                    queue.add(q3);
                    Integer[] q4 = new Integer[3];
                    q4[0] = othercolor;
                    q4[1] = 3;
                    q4[2] = side;
                    queue.add(q4);
                    Integer[] q5 = new Integer[3];
                    q5[0] = othercolor;
                    q5[1] = side;
                    q5[2] = 0;
                    queue.add(q5);
                } else {
                    Integer[] q2 = new Integer[3];
                    q2[0] = 3;
                    q2[1] = i;
                    q2[2] = othercolor;
                    queue.add(q2);
                    Integer[] q3 = new Integer[3];
                    q3[0] = othercolor;
                    q3[1] = 3;
                    q3[2] = i;
                    queue.add(q3);
                    Integer[] q4 = new Integer[3];
                    q4[0] = othercolor;
                    q4[1] = i;
                    q4[2] = 0;
                    queue.add(q4);
                }
                locked.add(cube.getCenters()[0].getSide(i));
                return;
            }
        }
        //side check:
        if (state0side(answer, 1, 2) || state0side(answer, 2, 1) || state0side(answer, 2, 4) ||
                state0side(answer, 4, 2) || state0side(answer, 4, 5) || state0side(answer, 5, 4) ||
                state0side(answer, 5, 1) || state0side(answer, 1, 5)) {
            return;
        }

        //bottom with bottom white
        if (state0bb(answer, 1) || state0bb(answer, 2) || state0bb(answer, 4) ||
                state0bb(answer, 5)) {
            return;
        }

        //bottom with side white
        if (state0bs(answer, 1) || state0bs(answer, 2) || state0bs(answer, 4) ||
                state0bs(answer, 5)) {
            return;
        }

        //top layer with side white
        if (state0ts(answer, 1) || state0ts(answer, 2) || state0ts(answer, 4) ||
                state0ts(answer, 5)) {
            return;
        }
    }

    public boolean state0ts(Integer[] answer, int side) {
        CubeSide cs = cube.getCenters()[0].getSide(side);
        if (cs.getColor(side) == 0) {
            int ad = (side % 3) % 2 + 1;
            answer[0] = side;
            answer[1] = 0;
            answer[2] = ad;
            Integer[] q01 = new Integer[3];
            q01[0] = side;
            q01[1] = 0;
            q01[2] = ad;
            queue.add(q01);
            if (cs.getColor(0) == side || cs.getColor(0) == (side + 3) % 6) {
                Integer[] q0 = new Integer[3];
                q0[0] = 3;
                q0[1] = side;
                q0[2] = ad;
                queue.add(q0);
                Integer[] q1 = new Integer[3];
                q1[0] = ad;
                q1[1] = 3;
                q1[2] = cs.getColor(0);
                queue.add(q1);
                Integer[] q2 = new Integer[3];
                q2[0] = cs.getColor(0);
                q2[1] = 3;
                q2[2] = ad;
                queue.add(q2);
            } else {
                Integer[] q0 = new Integer[3];
                q0[0] = side;
                q0[1] = 3;
                q0[2] = cs.getColor(3);
                queue.add(q0);
                Integer[] q1 = new Integer[3];
                q1[0] = cs.getColor(3);
                q1[1] = 3;
                q1[2] = side;
                queue.add(q1);
            }
            return true;
        }
        return false;
    }

    public boolean state0bs(Integer[] answer, int side) {
        CubeSide cs = cube.getCenters()[3].getSide(side);
        if (cs.getColor(side) == 0) {
            answer[0] = 0;
            if (cs.getColor(3) == side || cs.getColor(3) == (side + 3) % 6) {
                int ad = (side % 3) % 2 + 1;
                answer[0] = 3;
                answer[1] = side;
                answer[2] = ad;
                Integer[] q1 = new Integer[3];
                q1[0] = ad;
                q1[1] = 3;
                q1[2] = cs.getColor(3);
                queue.add(q1);
                Integer[] q2 = new Integer[3];
                q2[0] = cs.getColor(3);
                q2[1] = 3;
                q2[2] = ad;
                queue.add(q2);
            } else {
                answer[0] = side;
                answer[1] = 3;
                answer[2] = cs.getColor(3);
                Integer[] q1 = new Integer[3];
                q1[0] = cs.getColor(3);
                q1[1] = 3;
                q1[2] = side;
                queue.add(q1);
            }
            return true;
        }
        return false;
    }

    public boolean state0bb(Integer[] answer, int side) {
        if (cube.getCenters()[3].getSide(side).getColor(3) == 0) {
            answer[0] = 0;
            int othercolor = cube.getCenters()[3].getSide(side).getColor(side);
            if (othercolor == (side + 3) % 6) {
                answer[0] = 3;
                answer[1] = side;
                int ad = (side % 3) % 2 + 1;
                answer[2] = ad;
                Integer[] q1 = new Integer[3];
                q1[0] = 3;
                q1[1] = side;
                q1[2] = ad;
                queue.add(q1);
            } else if (othercolor != side) {
                answer[0] = 3;
                answer[1] = side;
                answer[2] = othercolor;
            }
            if (answer[0] == 0) {
                answer[0] = side;
                answer[1] = 3;
                answer[2] = (side % 3) % 2 + 1;
            } else {
                Integer[] q1 = new Integer[3];
                q1[0] = side;
                q1[1] = 3;
                q1[2] = (side % 3) % 2 + 1;
                queue.add(q1);
            }
            Integer[] q1 = new Integer[3];
            q1[0] = side;
            q1[1] = 3;
            q1[2] = (side % 3) % 2 + 1;
            queue.add(q1);
            return true;
        }
        return false;
    }

    public boolean state0side(Integer[] answer, int i1, int i2) {
        CubeSide br = cube.getCenters()[i1].getSide(i2);
        if (br.getColor(i2) == 0) {
            int othercolor = br.getColor(i1);
            if (othercolor == i1) {
                answer[0] = i1;
                answer[1] = i2;
                answer[2] = 0;
                return true;
            }
            answer[0] = i1;
            answer[1] = 0;
            answer[2] = i2;
            if (othercolor == (i1 + 3) % 6) {
                Integer[] q1 = new Integer[3];
                q1[0] = 3;
                q1[1] = i1;
                q1[2] = i2;
                queue.add(q1);
                Integer[] q2 = new Integer[3];
                q2[0] = 3;
                q2[1] = i1;
                q2[2] = i2;
                queue.add(q2);
                Integer[] q3 = new Integer[3];
                q3[0] = (i1 + 3) % 6;
                q3[1] = i2;
                q3[2] = 0;
                queue.add(q3);
                Integer[] q4 = new Integer[3];
                q4[0] = (i1 + 3) % 6;
                q4[1] = i2;
                q4[2] = 0;
                queue.add(q4);
            } else {
                Integer[] q1 = new Integer[3];
                q1[0] = 3;
                q1[1] = i1;
                q1[2] = othercolor;
                queue.add(q1);
                Integer[] q2 = new Integer[3];
                q2[0] = othercolor;
                q2[1] = i1;
                q2[2] = 0;
                queue.add(q2);
                Integer[] q3 = new Integer[3];
                q3[0] = othercolor;
                q3[1] = i1;
                q3[2] = 0;
                queue.add(q3);
            }
            if (cube.getCenters()[0].getSide(i1).getColor(i1) == i1 &&
                    cube.getCenters()[0].getSide(i1).getColor(0) == 0) {
                Integer[] redo = new Integer[3];
                redo[0] = i1;
                redo[1] = i2;
                redo[2] = 0;
                queue.add(redo);
            }
            return true;
        }
        return false;
    }

    public void state1(Integer[] answer) {
        if (locked.size() >= 8) {
            state++;
            state2(answer);
            return;
        }
        //double check stuff
        int s1 = key(1, 2);
        CubeCorner cc1 = cube.getCenters()[0].getCorner(s1);
        if (cc1.getColor(0) == 0 && cc1.getColor(1) == 1 && cc1.getColor(2) == 2) {
            locked.add(cc1);
        }
        int s2 = key(2, 4);
        CubeCorner cc2 = cube.getCenters()[0].getCorner(s2);
        if (cc2.getColor(0) == 0 && cc2.getColor(2) == 2 && cc2.getColor(4) == 4) {
            locked.add(cc2);
        }
        int s3 = key(4, 5);
        CubeCorner cc3 = cube.getCenters()[0].getCorner(s3);
        if (cc3.getColor(4) == 4 && cc3.getColor(0) == 0 && cc3.getColor(5) == 5) {
            locked.add(cc3);
        }
        int s4 = key(5, 1);
        CubeCorner cc4 = cube.getCenters()[0].getCorner(s4);
        if (cc4.getColor(0) == 0 && cc4.getColor(5) == 5 && cc4.getColor(1) == 1) {
            locked.add(cc4);
        }
        if (locked.size() >= 8) {
            state++;
            state2(answer);
            return;
        }

        //done double checking, got 4 references of the corners.

        //bottom corners check sides
        CubeCorner cc5 = cube.getCenters()[3].getCorner(s1);
        if (state1bs(cc5, 1, 2) || state1bs(cc5, 2, 1)) {
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }
        CubeCorner cc6 = cube.getCenters()[3].getCorner(s2);
        if (state1bs(cc6, 4, 2) || state1bs(cc6, 2, 4)) {
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }
        CubeCorner cc7 = cube.getCenters()[3].getCorner(s3);
        if (state1bs(cc7, 4, 5) || state1bs(cc7, 5, 4)) {
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }
        CubeCorner cc8 = cube.getCenters()[3].getCorner(s4);
        if (state1bs(cc8, 1, 5) || state1bs(cc8, 5, 1)) {
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }

        //top corners
        if (state1t(cc1, 1, 2) || state1t(cc2, 4, 2) || state1t(cc3, 4, 5) || state1t(cc4, 1, 5)) {
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }

        //bottom corner facing bottom
        if (state1bb(cc5, 1, 2) || state1bb(cc6, 2, 4) || state1bb(cc7, 4, 5) ||
                state1bb(cc8, 1, 5)) {
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }

        //randomly rotate bottom?
        answer[0] = 3;
        answer[1] = 1;
        answer[2] = 2;
    }

    public boolean state1bb(CubeCorner cc, int s1, int s2) {
        if (cc.getColor(3) == 0) {
            if (cc.getColor(s1) == s2) {
                Integer[] q1 = new Integer[3];
                q1[0] = s2;
                q1[1] = 0;
                q1[2] = s1;
                queue.add(q1);
                Integer[] q2 = new Integer[3];
                q2[0] = 3;
                q2[1] = s2;
                q2[2] = s1;
                queue.add(q2);
                Integer[] q3 = new Integer[3];
                q3[0] = 3;
                q3[1] = s2;
                q3[2] = s1;
                queue.add(q3);
                Integer[] q4 = new Integer[3];
                q4[0] = s2;
                q4[1] = s1;
                q4[2] = 0;
                queue.add(q4);
                return true;
            }
        }
        return false;
    }

    public boolean state1t(CubeCorner cc, int s1, int s2) {
        if (cc.getColor(0) == 0 && cc.getColor(s1) == s1 && cc.getColor(s2) == s2) {
            return false;
        }
        if (cc.getColor(0) == 0 || cc.getColor(s2) == 0) {
            Integer[] q1 = new Integer[3];
            q1[0] = s2;
            q1[1] = 0;
            q1[2] = s1;
            queue.add(q1);
            Integer[] q2 = new Integer[3];
            q2[0] = 3;
            q2[1] = s2;
            q2[2] = s1;
            queue.add(q2);
            Integer[] q3 = new Integer[3];
            q3[0] = s2;
            q3[1] = s1;
            q3[2] = 0;
            queue.add(q3);
            return true;
        } else if (cc.getColor(s1) == 0) {
            Integer[] q1 = new Integer[3];
            q1[0] = s1;
            q1[1] = 0;
            q1[2] = s2;
            queue.add(q1);
            Integer[] q2 = new Integer[3];
            q2[0] = 3;
            q2[1] = s1;
            q2[2] = s2;
            queue.add(q2);
            Integer[] q3 = new Integer[3];
            q3[0] = s1;
            q3[1] = s2;
            q3[2] = 0;
            queue.add(q3);
            return true;
        }
        return false;
    }

    public boolean state1bs(CubeCorner cc, int s1, int s2) {
        if (cc.getColor(s1) == 0) {
            if (cc.getColor(s2) == s2) {
                Integer[] q1 = new Integer[3];
                q1[0] = s1;
                q1[1] = 0;
                q1[2] = s2;
                queue.add(q1);
                Integer[] q2 = new Integer[3];
                q2[0] = 3;
                q2[1] = s1;
                q2[2] = s2;
                queue.add(q2);
                Integer[] q3 = new Integer[3];
                q3[0] = s1;
                q3[1] = s2;
                q3[2] = 0;
                queue.add(q3);
            } else if (cc.getColor(s2) == (s2 + 3) % 6) {
                Integer[] q1 = new Integer[3];
                q1[0] = 3;
                q1[1] = s1;
                q1[2] = s2;
                queue.add(q1);
                Integer[] q2 = new Integer[3];
                q2[0] = 3;
                q2[1] = s1;
                q2[2] = s2;
                queue.add(q2);
                Integer[] q3 = new Integer[3];
                q3[0] = (s1 + 3) % 6;
                q3[1] = 0;
                q3[2] = (s2 + 3) % 6;
                queue.add(q3);
                Integer[] q4 = new Integer[3];
                q4[0] = 3;
                q4[1] = (s1 + 3) % 6;
                q4[2] = (s2 + 3) % 6;
                queue.add(q4);
                Integer[] q5 = new Integer[3];
                q5[0] = (s1 + 3) % 6;
                q5[1] = (s2 + 3) % 6;
                q5[2] = 0;
                queue.add(q5);
            } else {
                Integer[] q1 = new Integer[3];
                q1[0] = 3;
                q1[1] = s2;
                q1[2] = cc.getColor(s2);
                queue.add(q1);
                Integer[] q2 = new Integer[3];
                q2[0] = cc.getColor(3);
                q2[1] = 0;
                q2[2] = cc.getColor(s2);
                queue.add(q2);
                Integer[] q3 = new Integer[3];
                q3[0] = 3;
                q3[1] = cc.getColor(s2);
                q3[2] = s2;
                queue.add(q3);
                Integer[] q4 = new Integer[3];
                q4[0] = cc.getColor(3);
                q4[1] = cc.getColor(s2);
                q4[2] = 0;
                queue.add(q4);
            }
            return true;
        }
        return false;

    }

    public void state2(Integer[] answer) {
        if (locked.size() >= 12) {
            state++;
            state3(answer);
            return;
        }
        CubeSide cs1 = cube.getCenters()[1].getSide(2);
        if (cs1.getColor(1) == 1 && cs1.getColor(2) == 2) {
            locked.add(cs1);
        }
        CubeSide cs2 = cube.getCenters()[2].getSide(4);
        if (cs2.getColor(2) == 2 && cs2.getColor(4) == 4) {
            locked.add(cs2);
        }
        CubeSide cs3 = cube.getCenters()[4].getSide(5);
        if (cs3.getColor(4) == 4 && cs3.getColor(5) == 5) {
            locked.add(cs3);
        }
        CubeSide cs4 = cube.getCenters()[5].getSide(1);
        if (cs4.getColor(5) == 5 && cs4.getColor(1) == 1) {
            locked.add(cs4);
        }
        if (locked.size() >= 12) {
            state++;
            state3(answer);
            return;
        }

        //go through all 4 sides on yellow and see if there are any to be moved down
        CubeSide cs5 = cube.getCenters()[3].getSide(1);
        if (cs5.getColor(3) == 4 && cs5.getColor(1) == 2) {
            state2alg(answer, 4, 2);
            return;
        } else if (cs5.getColor(3) == 4 && cs5.getColor(1) == 5) {
            state2alg(answer, 4, 5);
            return;
        }

        CubeSide cs6 = cube.getCenters()[3].getSide(2);
        if (cs6.getColor(3) == 5 && cs6.getColor(2) == 4) {
            state2alg(answer, 5, 4);
            return;
        } else if (cs6.getColor(3) == 5 && cs6.getColor(2) == 1) {
            state2alg(answer, 5, 1);
            return;
        }

        CubeSide cs7 = cube.getCenters()[3].getSide(4);
        if (cs7.getColor(3) == 1 && cs7.getColor(4) == 5) {
            state2alg(answer, 1, 5);
            return;
        } else if (cs7.getColor(3) == 1 && cs7.getColor(4) == 2) {
            state2alg(answer, 1, 2);
            return;
        }

        CubeSide cs8 = cube.getCenters()[3].getSide(5);
        if (cs8.getColor(3) == 2 && cs8.getColor(5) == 1) {
            state2alg(answer, 2, 1);
            return;
        } else if (cs8.getColor(3) == 2 && cs8.getColor(5) == 4) {
            state2alg(answer, 2, 4);
            return;
        }

        if (cs5.getColor(3) != 3 && cs5.getColor(1) != 3) {
            //turn the cs5's 3 towards the opposite of its color
            int op = (cs5.getColor(3) + 3) % 6;
            if (op == 1 || op == 4) {
                answer[0] = 3;
                answer[1] = 1;
                answer[2] = 2;
            } else {
                answer[0] = 3;
                answer[1] = 1;
                answer[2] = op;
            }
            return;
        }
        if (cs6.getColor(3) != 3 && cs6.getColor(2) != 3) {
            int op = (cs6.getColor(3) + 3) % 6;
            if (op == 2 || op == 5) {
                answer[0] = 3;
                answer[1] = 1;
                answer[2] = 2;
            } else {
                answer[0] = 3;
                answer[1] = 2;
                answer[2] = op;
            }
            return;
        }
        if (cs7.getColor(3) != 3 && cs7.getColor(4) != 3) {
            int op = (cs7.getColor(3) + 3) % 6;
            if (op == 4 || op == 1) {
                answer[0] = 3;
                answer[1] = 1;
                answer[2] = 2;
            } else {
                answer[0] = 3;
                answer[1] = 4;
                answer[2] = op;
            }
            return;
        }
        if (cs8.getColor(3) != 3 && cs8.getColor(5) != 3) {
            //turn the cs5's 3 towards the opposite of its color
            int op = (cs8.getColor(3) + 3) % 6;
            if (op == 5 || op == 2) {
                answer[0] = 3;
                answer[1] = 1;
                answer[2] = 2;
            } else {
                answer[0] = 3;
                answer[1] = 5;
                answer[2] = op;
            }
            return;
        }
        //worst case: one of the cs1-4 are or not being used:
        if (cs1.getColor(1) != 3 && cs1.getColor(2) != 3) {
            state2alg(answer, 1, 2);
            return;
        }
        if (cs2.getColor(2) != 3 && cs2.getColor(4) != 3) {
            state2alg(answer, 2, 4);
            return;
        }
        if (cs3.getColor(4) != 3 && cs1.getColor(5) != 3) {
            state2alg(answer, 4, 5);
            return;
        }
        if (cs4.getColor(5) != 3 && cs2.getColor(1) != 3) {
            state2alg(answer, 5, 1);
            return;
        }
        //wtf? turn a random way
        answer[0] = 3;
        answer[1] = 1;
        answer[2] = 2;
    }

    /**
     * @param answer
     * @param s1     the side that starts
     * @param s2     the other side
     */
    public void state2alg(Integer[] answer, int s1, int s2) {
        answer[0] = s1;
        answer[1] = s2;
        answer[2] = 3;
        Integer[] q2 = new Integer[3];
        q2[0] = 3;
        q2[1] = s2;
        q2[2] = s1;
        queue.add(q2);
        Integer[] q3 = new Integer[3];
        q3[0] = s1;
        q3[1] = 3;
        q3[2] = s2;
        queue.add(q3);
        Integer[] q4 = new Integer[3];
        q4[0] = 3;
        q4[1] = s2;
        q4[2] = s1;
        queue.add(q4);
        Integer[] q5 = new Integer[3];
        q5[0] = s2;
        q5[1] = s1;
        q5[2] = 3;
        queue.add(q5);
        Integer[] q6 = new Integer[3];
        q6[0] = 3;
        q6[1] = s1;
        q6[2] = s2;
        queue.add(q6);
        Integer[] q7 = new Integer[3];
        q7[0] = s2;
        q7[1] = 3;
        q7[2] = s1;
        queue.add(q7);
    }

    public void state3(Integer[] answer) {
        int yb = cube.getCenters()[3].getSide(1).getColor(3);
        int yr = cube.getCenters()[3].getSide(2).getColor(3);
        int yg = cube.getCenters()[3].getSide(4).getColor(3);
        int yo = cube.getCenters()[3].getSide(5).getColor(3);
        if (yb == 3 && yr == 3 && yg == 3 && yo == 3) {
            state++;
            state4(answer);
            return;
        }
        if (yb == 3 && yg == 3) {
            state3alg(answer, 2, 4);
            return;
        } else if (yr == 3 && yo == 3) {
            state3alg(answer, 4, 5);
            return;
        } else if (yb == 3 && yr == 3) {
            state3alg(answer, 4, 5);
            return;
        } else if (yr == 3 && yg == 3) {
            state3alg(answer, 5, 1);
            return;
        } else if (yg == 3 && yo == 3) {
            state3alg(answer, 1, 2);
            return;
        } else if (yo == 3 && yb == 3) {
            state3alg(answer, 2, 4);
            return;
        }
        state3alg(answer, 1, 2);
    }

    /**
     * @param answer
     * @param s1     left side
     * @param s2     right side
     */
    public void state3alg(Integer[] answer, int s1, int s2) {
        answer[0] = s1;
        answer[1] = 3;
        answer[2] = s2;
        Integer[] q1 = new Integer[3];
        q1[0] = s2;
        q1[1] = s1;
        q1[2] = 3;
        queue.add(q1);
        Integer[] q2 = new Integer[3];
        q2[0] = 3;
        q2[1] = s2;
        q2[2] = s1;
        queue.add(q2);
        Integer[] q3 = new Integer[3];
        q3[0] = s2;
        q3[1] = 3;
        q3[2] = s1;
        queue.add(q3);
        Integer[] q4 = new Integer[3];
        q4[0] = 3;
        q4[1] = s1;
        q4[2] = s2;
        queue.add(q4);
        Integer[] q5 = new Integer[3];
        q5[0] = s1;
        q5[1] = s2;
        q5[2] = 3;
        queue.add(q5);
    }

    public void state4(Integer[] answer) {
        if (locked.size() >= 16) {
            state++;
            state5(answer);
            return;
        }
        CubeSide yb = cube.getCenters()[3].getSide(1);
        CubeSide yr = cube.getCenters()[3].getSide(2);
        CubeSide yg = cube.getCenters()[3].getSide(4);
        CubeSide yo = cube.getCenters()[3].getSide(5);

        if (yb.getColor(1) == 1) {
            locked.add(yb);
        }
        if (yr.getColor(2) == 2) {
            locked.add(yr);
        }
        if (yg.getColor(4) == 4) {
            locked.add(yg);
        }
        if (yo.getColor(5) == 5) {
            locked.add(yo);
        }
        if (locked.size() >= 16) {
            state++;
            state5(answer);
            return;
        }
        //want to order b-r-g-o as ascending, and the shuffle is at a point where either the
        //2nd or 3rd number is the biggest, and the rest is ascending
        //keep add 6 to each one...
        //keep rotating until blue is at blue
        if (yb.getColor(1) != 1) {
            if (yr.getColor(2) == 1) {
                answer[0] = 3;
                answer[1] = 2;
                answer[2] = 1;
            } else {
                answer[0] = 3;
                answer[1] = 5;
                answer[2] = 1;
            }
            return;
        }
        if (yr.getColor(2) == 2 && yg.getColor(4) == 5) {
            state4alg(answer, 2, 4);
        } else if (yr.getColor(2) == 5 && yg.getColor(4) == 4) {
            state4alg(answer, 5, 1);
        } else if (yr.getColor(2) == 4 && yg.getColor(4) == 2) {
            state4alg(answer, 5, 4);
        } else if (yr.getColor(2) == 5 && yg.getColor(4) == 2) {
            state4alg(answer, 5, 1);
        } else if (yr.getColor(2) == 4 && yg.getColor(4) == 5) {
            state4alg(answer, 2, 1);
        }
    }

    public void state4alg(Integer[] answer, int turnside, int ad) {
        answer[0] = turnside;
        answer[1] = ad;
        answer[2] = 3;
        Integer[] q1 = new Integer[3];
        q1[0] = 3;
        q1[1] = turnside;
        q1[2] = ad;
        queue.add(q1);
        Integer[] q2 = new Integer[3];
        q2[0] = turnside;
        q2[1] = 3;
        q2[2] = ad;
        queue.add(q2);
        Integer[] q3 = new Integer[3];
        q3[0] = 3;
        q3[1] = turnside;
        q3[2] = ad;
        queue.add(q3);
        Integer[] q4 = new Integer[4];
        q4[0] = turnside;
        q4[1] = ad;
        q4[2] = 3;
        queue.add(q4);
        Integer[] q5 = new Integer[3];
        q5[0] = 3;
        q5[1] = turnside;
        q5[2] = ad;
        queue.add(q5);
        Integer[] q6 = new Integer[3];
        q6[0] = 3;
        q6[1] = turnside;
        q6[2] = ad;
        queue.add(q6);
        Integer[] q7 = new Integer[3];
        q7[0] = turnside;
        q7[1] = 3;
        q7[2] = ad;
        queue.add(q7);
    }

    public void state5(Integer[] answer) {

        int s1 = key(1, 2);
        CubeCorner ybr = cube.getCenters()[3].getCorner(s1);

        int s2 = key(4, 2);
        CubeCorner yrg = cube.getCenters()[3].getCorner(s2);

        int s3 = key(4, 5);
        CubeCorner ygo = cube.getCenters()[3].getCorner(s3);

        Collection<Integer> ybrs = ybr.getValues().values();
        Collection<Integer> yrgs = yrg.getValues().values();
        Collection<Integer> ygos = ygo.getValues().values();

        if (ybrs.contains(1) && ybrs.contains(2) && yrgs.contains(2) && yrgs.contains(4)) {
            state++;
            state6(answer);
            return;
        }

        if (ybrs.contains(1) && ybrs.contains(2)) {
            if (ygos.contains(2) && ygos.contains(4)) {
                state5alg(answer, 5, 1);
            } else {
                state5alg(answer, 4, 2);
            }
        } else {
            if (yrgs.contains(1) && yrgs.contains(2)) {
                state5alg(answer, 2, 4);
            } else if (ygos.contains(1) && ygos.contains(2)) {
                state5alg(answer, 1, 2);
            } else {
                state5alg(answer, 1, 5);
            }
        }

    }

    public void state5alg(Integer[] answer, int s1, int s2) {
        answer[0] = s1;
        answer[1] = s2;
        answer[2] = 3;
        Integer[] q1 = new Integer[3];
        q1[0] = 3;
        q1[1] = s2;
        q1[2] = s1;
        queue.add(q1);
        Integer[] q2 = new Integer[3];
        int op = (s1 + 3) % 6;
        q2[0] = op;
        q2[1] = s2;
        q2[2] = 3;
        queue.add(q2);
        Integer[] q3 = new Integer[3];
        q3[0] = 3;
        q3[1] = s1;
        q3[2] = s2;
        queue.add(q3);
        Integer[] q4 = new Integer[3];
        q4[0] = s1;
        q4[1] = 3;
        q4[2] = s2;
        queue.add(q4);
        Integer[] q5 = new Integer[3];
        q5[0] = 3;
        q5[1] = s2;
        q5[2] = s1;
        queue.add(q5);
        Integer[] q6 = new Integer[3];
        q6[0] = op;
        q6[1] = 3;
        q6[2] = s2;
        queue.add(q6);
        Integer[] q7 = new Integer[3];
        q7[0] = 3;
        q7[1] = s1;
        q7[2] = s2;
        queue.add(q7);
    }

    public void state6(Integer[] answer) {
        int s1 = key(1, 2);
        CubeCorner ybr = cube.getCenters()[3].getCorner(s1);

        int s2 = key(4, 2);
        CubeCorner yrg = cube.getCenters()[3].getCorner(s2);

        int s3 = key(4, 5);
        CubeCorner ygo = cube.getCenters()[3].getCorner(s3);

        int s4 = key(5, 1);
        CubeCorner yob = cube.getCenters()[3].getCorner(s4);

        if (ybr.getColor(3) == 3 && ybr.getColor(1) == 1 && ybr.getColor(2) == 2 &&
                yrg.getColor(3) == 3 && yrg.getColor(2) == 2 && yrg.getColor(4) == 4 &&
                ygo.getColor(3) == 3 && ygo.getColor(4) == 4 &&
                ygo.getColor(5) == 5 && yob.getColor(3) == 3 && yob.getColor(5) == 5 &&
                yob.getColor(1) == 1) {
            return;
        }

        if (ybr.getColor(1) == 3) {
            state6alg(1, 2);
        } else if (ybr.getColor(2) == 3) {
            state6alg(1, 2);
            state6alg(1, 2);
        }
        Integer[] r1 = new Integer[3];
        r1[0] = 3;
        r1[1] = 2;
        r1[2] = 1;
        queue.add(r1);
        if (yrg.getColor(3) != 3) {
            state6alg(1, 2);
            if (yrg.getColor(4) == 3) {
                state6alg(1, 2);
            }
        }
        Integer[] r2 = new Integer[3];
        r2[0] = 3;
        r2[1] = 2;
        r2[2] = 1;
        queue.add(r2);
        if (ygo.getColor(3) != 3) {
            state6alg(1, 2);
            if (ygo.getColor(5) == 3) {
                state6alg(1, 2);
            }
        }
        Integer[] r3 = new Integer[3];
        r3[0] = 3;
        r3[1] = 2;
        r3[2] = 1;
        queue.add(r3);
        if (yob.getColor(3) != 3) {
            state6alg(1, 2);
            if (yob.getColor(1) == 3) {
                state6alg(1, 2);
            }
        }
        Integer[] r4 = new Integer[3];
        r4[0] = 3;
        r4[1] = 2;
        r4[2] = 1;
        queue.add(r4);
        Integer[] b = queue.remove();
        answer[0] = b[0];
        answer[1] = b[1];
        answer[2] = b[2];
    }

    public void state6alg(int s1, int s2) {
        Integer[] q1 = new Integer[3];
        q1[0] = s2;
        q1[1] = 3;
        q1[2] = s1;
        queue.add(q1);
        Integer[] q2 = new Integer[3];
        q2[0] = 0;
        q2[1] = s1;
        q2[2] = s2;
        queue.add(q2);
        Integer[] q3 = new Integer[3];
        q3[0] = s2;
        q3[1] = s1;
        q3[2] = 3;
        queue.add(q3);
        Integer[] q4 = new Integer[3];
        q4[0] = 0;
        q4[1] = s2;
        q4[2] = s1;
        queue.add(q4);
        Integer[] q5 = new Integer[3];
        q5[0] = s2;
        q5[1] = 3;
        q5[2] = s1;
        queue.add(q5);
        Integer[] q6 = new Integer[3];
        q6[0] = 0;
        q6[1] = s1;
        q6[2] = s2;
        queue.add(q6);
        Integer[] q7 = new Integer[3];
        q7[0] = s2;
        q7[1] = s1;
        q7[2] = 3;
        queue.add(q7);
        Integer[] q8 = new Integer[3];
        q8[0] = 0;
        q8[1] = s2;
        q8[2] = s1;
        queue.add(q8);
    }

    public int getState() {
        return state;
    }
}
