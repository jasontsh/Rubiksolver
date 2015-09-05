package com.android.pennapps.f15.rubiksolver;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

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
    public RubiksSolver(RubiksCube c){
        state = 0;
        cube = c;
        queue = new LinkedList<Integer[]>();
        locked = new HashSet<CubePart>();
    }

    public Integer[] turn(){
        if(!queue.isEmpty()){
            return queue.remove();
        }
        Integer[] answer = new Integer[3];
        switch(state){
            case 0: state0(answer);
            case 1: state1(answer);
            case 2: state2(answer);
            case 3: state3(answer);
        }
        return answer;
    }

    public void state0(Integer[] answer){
        if(locked.size() >= 4){
            state++;
            state1(answer);
            return;
        }
        //check for updates on the white cross
        Set<Integer> left = new HashSet<Integer>();
        left.add(1);
        left.add(2);
        left.add(4);
        left.add(5);
        for(CubePart cs: locked){
            left.remove(cs.getColor(0));
        }
        for(Integer i: left){
            if(cube.getCenters()[0].getSide(i).getColor(0) == 0 &&
                    cube.getCenters()[0].getSide(i).getColor(i) == i) {
                locked.add(cube.getCenters()[0].getSide(i));
            }
        }
        if(locked.size() >= 4){
            state++;
            state1(answer);
            return;
        }

        //first go through the top layer with white on top already
        for(Integer i: left){
            if(cube.getCenters()[0].getSide(i).getColor(0) == 0){
                Set<Integer> b = new HashSet<Integer>();
                b.add(1);
                b.add(2);
                b.add(4);
                b.add(5);
                b.remove(i);
                b.remove((i+3)%6);
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
                if((i+3)%6 == othercolor) {
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
                }else{
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
                state0side(answer, 4, 2) || state0side(answer, 4, 5) || state0side(answer, 5, 4)
                || state0side(answer, 5, 1) || state0side(answer, 1, 5)) {
            return;
        }

        //bottom with bottom white
        if(state0bb(answer, 1) || state0bb(answer, 2) || state0bb(answer, 4) || state0bb(answer, 5)){
            return;
        }

        //bottom with side white
        if(state0bs(answer, 1) || state0bs(answer, 2) || state0bs(answer, 4) || state0bs(answer, 5)){
            return;
        }

        //top layer with side white
        if(state0ts(answer, 1) || state0ts(answer, 2) || state0ts(answer, 4) || state0ts(answer, 5)){
            return;
        }
    }

    public boolean state0ts(Integer[] answer, int side){
        CubeSide cs = cube.getCenters()[0].getSide(side);
        if(cs.getColor(side) == 0){
            int ad = (side % 3) % 2 + 1;
            answer[0] = side;
            answer[1] = 0;
            answer[2] = ad;
            Integer[] q01 = new Integer[3];
            q01[0] = side;
            q01[1] = 0;
            q01[2] = ad;
            queue.add(q01);
            if(cs.getColor(0) == side || cs.getColor(0) == (side + 3) % 6){
                Integer[] q0 = new Integer[3];
                q0[0] = 3;
                q0[1] = side;
                q0[2] = ad;
                queue.add(q0);
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
            }else{
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

    public boolean state0bs(Integer[] answer, int side){
        CubeSide cs = cube.getCenters()[3].getSide(side);
        if(cs.getColor(side) == 0){
            answer[0] = 0;
            if(cs.getColor(3) == side || cs.getColor(3) == (side + 3) % 6){
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
            } else{
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

    public boolean state0bb(Integer[] answer, int side){
        if(cube.getCenters()[3].getSide(side).getColor(3) == 0){
            answer[0] = 0;
            int othercolor = cube.getCenters()[3].getSide(side).getColor(side);
            if(othercolor == (side+3)%6){
                answer[0] = 3;
                answer[1] = side;
                int ad = (side % 3) % 2 + 1;
                answer[2] = ad;
                Integer[] q1 = new Integer[3];
                q1[0] = 3;
                q1[1] = side;
                q1[2] = ad;
                queue.add(q1);
            } else if(othercolor != side){
                answer[0] = 3;
                answer[1] = side;
                answer[2] = othercolor;
            }
            if(answer[0] == 0){
                answer[0] = side;
                answer[1] = 3;
                answer[2] = (side % 3) % 2 + 1;
            } else{
                Integer[] q1 = new Integer[3];
                q1[0] = side;
                q1[1] = 3;
                q1[2] = (side%3) %2 + 1;
                queue.add(q1);
            }
            Integer[] q1 = new Integer[3];
            q1[0] = side;
            q1[1] = 3;
            q1[2] = (side%3) %2 + 1;
            queue.add(q1);
            return true;
        }
        return false;
    }

    public boolean state0side(Integer[] answer, int i1, int i2){
        CubeSide br = cube.getCenters()[i1].getSide(i2);
        if(br.getColor(i2) == 0) {
            int othercolor = br.getOtherColor(1);
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
                q3[0] = (i1+3)%6;
                q3[1] = i2;
                q3[2] = 0;
                queue.add(q3);
                Integer[] q4 = new Integer[3];
                q4[0] = (i1+3)%6;
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

    public void state1(Integer[] answer){
        if(locked.size() >= 8){
            state++;
            state2(answer);
            return;
        }
        //double check stuff
        HashSet<Integer> s1 = new HashSet<Integer>();
        s1.add(1);
        s1.add(2);
        CubeCorner cc1 = cube.getCenters()[0].getCorner(s1);
        if(cc1.getColor(0) == 0 && cc1.getColor(1) == 1 && cc1.getColor(2) == 2){
            locked.add(cc1);
        }
        HashSet<Integer> s2 = new HashSet<Integer>();
        s2.add(2);
        s2.add(4);
        CubeCorner cc2 = cube.getCenters()[0].getCorner(s2);
        if(cc2.getColor(0) == 0 && cc2.getColor(2) == 2 && cc2.getColor(4) == 4){
            locked.add(cc2);
        }
        HashSet<Integer> s3 = new HashSet<Integer>();
        s3.add(4);
        s3.add(5);
        CubeCorner cc3 = cube.getCenters()[0].getCorner(s3);
        if(cc3.getColor(4) == 4 && cc3.getColor(0) == 0 && cc3.getColor(5) == 5){
            locked.add(cc3);
        }
        HashSet<Integer> s4 = new HashSet<Integer>();
        s4.add(5);
        s4.add(1);
        CubeCorner cc4 = cube.getCenters()[0].getCorner(s4);
        if(cc4.getColor(0) == 0 && cc4.getColor(5) == 5 && cc4.getColor(1) == 1){
            locked.add(cc4);
        }
        if(locked.size() >= 8){
            state++;
            state2(answer);
            return;
        }
        //done double checking, got 4 references of the corners.
        //bottom corners check sides
        CubeCorner cc5 = cube.getCenters()[3].getCorner(s1);
        if(state1bs(cc5, 1, 2) || state1bs(cc5, 2, 1)){
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }
        CubeCorner cc6 = cube.getCenters()[3].getCorner(s2);
        if(state1bs(cc6, 4, 2) || state1bs(cc6, 2, 4)){
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }
        CubeCorner cc7 = cube.getCenters()[3].getCorner(s3);
        if(state1bs(cc7, 4, 5) || state1bs(cc7, 5, 4)){
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }
        CubeCorner cc8 = cube.getCenters()[3].getCorner(s4);
        if(state1bs(cc8, 1, 5) || state1bs(cc8, 5, 1)){
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }

        //top corners
        if(state1t(cc1, 1, 2) || state1t(cc2, 4, 2) ||state1t(cc3, 4, 5) || state1t(cc4, 1, 5)){
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }

        //bottom corner facing bottom
        if(state1bb(cc5, 1, 2) || state1bb(cc6, 2, 4) || state1bb(cc7, 4, 5) || state1bb(cc8, 1, 5)){
            Integer[] q = queue.remove();
            answer[0] = q[0];
            answer[1] = q[1];
            answer[2] = q[2];
            return;
        }
    }

    public boolean state1bb(CubeCorner cc, int s1, int s2){
        if(cc.getColor(3) == 0){
            if(cc.getColor(s1) == (s2 + 3) % 6 && cc.getColor(s2) == (s1+3)%6 ){
                Integer[] q1 = new Integer[3];
                q1[0] = 3;
                q1[1] = s1;
                q1[2] = s2;
                queue.add(q1);
                return true;
            }
            if(cc.getColor(s1) == s1 || cc.getColor(s2) == s2){
                Integer[] q1 = new Integer[3];
                q1[0] = 3;
                q1[1] = cc.getColor(s1) == s1? s1 : s2;
                q1[2] = cc.getColor(s1) == s1? s2 : s1;
                queue.add(q1);
                return true;
            }
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
            q3[0] = 3;
            q3[1] = s1;
            q3[2] = s2;
            queue.add(q3);
            Integer[] q4 = new Integer[3];
            q4[0] = s1;
            q4[1] = s2;
            q4[2] = 0;
            queue.add(q4);
            return true;
        }
        return false;
    }

    public boolean state1t(CubeCorner cc, int s1, int s2){
        if(cc.getColor(0) == 0 || cc.getColor(s2) == 0){
            Integer[] q1 = new Integer[3];
            q1[0] = s2;
            q1[1] = 0;
            q1[2] = s1;
            queue.add(q1);
            Integer[] q2 = new Integer[3];
            q2[0] = 3;
            q2[1] = s2;
            q2[2] = s1;
            queue.add(q1);
            Integer[] q3 = new Integer[3];
            q3[0] = s2;
            q3[1] = s1;
            q3[2] = 0;
            queue.add(q2);
            return true;
        }else if (cc.getColor(s1) == 0){
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
            return true;
        }
        return false;
    }

    public boolean state1bs(CubeCorner cc, int s1, int s2){
        if(cc.getColor(s1) == 0){
            if(cc.getColor(s2) == (s2+3)% 6){
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
            } else if(cc.getColor(s2) != s2){
                Integer[] q1 = new Integer[3];
                q1[0] = 3;
                q1[1] = s1;
                q1[2] = s2;
                queue.add(q1);
            }
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

    public void state2(Integer[] answer){
        
    }
    public void state3(Integer[] answer){

    }
}
