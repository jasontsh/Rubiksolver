package com.android.pennapps.f15.rubiksolver;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;

//There are only two facing ways: white and yellow
public class RubikViewer extends AppCompatActivity {

    public RubiksCube rc;
    public int side;
    public RubiksSolver rs;
    public boolean flip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubik_viewer);
        flip = false;

        rc = new RubiksCube(InputActivity.array);
        rs = new RubiksSolver(rc);

        TextView tv = (TextView)findViewById(R.id.rvtv);
        tv.setText("Face white with blue on top");

        updateGraphics(0);
    }

    public Command getCommand(Integer[] turn, int side){
        if(side == 0){
            switch(turn[0]){
                case 0: return (turn[1] < turn[2] || (turn[1] == 5 && turn[2] == 1))
                        ? Command.FRONT_CW : Command.FRONT_CCW;
                case 1: return (turn[1] > turn[2] || (turn[1] == 0 && turn[2] == 5))
                        ? Command.TOP_LEFT : Command.TOP_RIGHT;
                case 2: return (turn[1] < turn[2] || (turn[1] == 4 && turn[2] == 0))?
                        Command.RIGHT_UP : Command.RIGHT_DOWN;
                case 3: return (turn[1] > turn[2] || (turn[1] == 1 && turn[2] == 5))?
                        Command.BACK_CCW : Command.BACK_CW;
                case 4: return (turn[1] < turn[2] || (turn[1] == 5 && turn[2] == 0))?
                        Command.BOTTOM_RIGHT : Command.BOTTOM_LEFT;
                case 5: return (turn[1] > turn[2] || (turn[1] == 0 && turn[2] == 4))?
                        Command.LEFT_DOWN : Command.LEFT_UP;
            }
        }else{
            switch(turn[0]){
                case 0: return (turn[1] < turn[2] || (turn[1] == 5 && turn[2] == 1))?
                        Command.BACK_CCW : Command.BACK_CW;
                case 1: return (turn[1] > turn[2] || (turn[1] == 0 && turn[2] == 5))?
                        Command.BOTTOM_RIGHT : Command.BOTTOM_LEFT;
                case 2: return (turn[1] < turn[2] || (turn[1] == 4 && turn[2] == 0))?
                        Command.RIGHT_UP : Command.RIGHT_DOWN;
                case 3: return (turn[1] > turn[2] || (turn[1] == 5 && turn[2] == 5))?
                        Command.FRONT_CW : Command.FRONT_CCW;
                case 4: return (turn[1] < turn[2] || (turn[1] == 5 && turn[2] == 0))?
                        Command.TOP_LEFT : Command.TOP_RIGHT;
                case 5: return (turn[1] > turn[2] || (turn[1] == 0 && turn[2] == 4))?
                        Command.LEFT_DOWN : Command.LEFT_DOWN;
            }
        }
        return null;
    }

    public void updateGraphics(int side){
        View[][] grid = new View[3][3];
        grid[0][0] = findViewById(R.id.view0);
        grid[1][0] = findViewById(R.id.view1);
        grid[2][0] = findViewById(R.id.view2);
        grid[0][1] = findViewById(R.id.view3);
        grid[1][1] = findViewById(R.id.view4);
        grid[2][1] = findViewById(R.id.view5);
        grid[0][2] = findViewById(R.id.view6);
        grid[1][2] = findViewById(R.id.view7);
        grid[2][2] = findViewById(R.id.view8);
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                compileColor(side, grid);
            }
        }

    }

    public void compileColor(int side, View[][] grid){
        HashSet<Integer> s1 = new HashSet<Integer>();
        s1.add(1);
        s1.add(2);
        HashSet<Integer> s2 = new HashSet<Integer>();
        s2.add(2);
        s2.add(4);
        HashSet<Integer> s3 = new HashSet<Integer>();
        s3.add(4);
        s3.add(5);
        HashSet<Integer> s4 = new HashSet<Integer>();
        s4.add(5);
        s4.add(1);
        if(side == 0){
            CubeCenter c11 = rc.getCenters()[0];
            CubeCorner c00 = c11.getCorner(s4);
            CubeSide c10 = c11.getSide(1);
            CubeCorner c20 = c11.getCorner(s1);
            CubeSide c01 = c11.getSide(5);
            CubeSide c21 = c11.getSide(2);
            CubeCorner c02 = c11.getCorner(s3);
            CubeSide c12 = c11.getSide(4);
            CubeCorner c22 = c11.getCorner(s2);
            switchColor(c11, grid[1][1], 0);
            switchColor(c00, grid[0][0], 0);
            switchColor(c10, grid[1][0], 0);
            switchColor(c20, grid[2][0], 0);
            switchColor(c01, grid[0][1], 0);
            switchColor(c21, grid[2][1], 0);
            switchColor(c02, grid[0][2], 0);
            switchColor(c12, grid[1][2], 0);
            switchColor(c22, grid[2][2], 0);
        } else{
            CubeCenter c11 = rc.getCenters()[3];
            CubeCorner c00 = c11.getCorner(s3);
            CubeSide c10 = c11.getSide(4);
            CubeCorner c20 = c11.getCorner(s2);
            CubeSide c01 = c11.getSide(5);
            CubeSide c21 = c11.getSide(2);
            CubeCorner c02 = c11.getCorner(s4);
            CubeSide c12 = c11.getSide(1);
            CubeCorner c22 = c11.getCorner(s1);
            switchColor(c11, grid[1][1], 3);
            switchColor(c00, grid[0][0], 3);
            switchColor(c10, grid[1][0], 3);
            switchColor(c20, grid[2][0], 3);
            switchColor(c01, grid[0][1], 3);
            switchColor(c21, grid[2][1], 3);
            switchColor(c02, grid[0][2], 3);
            switchColor(c12, grid[1][2], 3);
            switchColor(c22, grid[2][2], 3);
        }
    }

    public void switchColor(CubePart cp, View v, int side){
        switch(cp.getColor(side)){
            case 0: v.setBackgroundColor(Color.WHITE); break;
            case 1: v.setBackgroundColor(Color.BLUE); break;
            case 2: v.setBackgroundColor(Color.RED); break;
            case 3: v.setBackgroundColor(Color.YELLOW); break;
            case 4: v.setBackgroundColor(Color.GREEN); break;
            case 5: v.setBackgroundColor(Color.parseColor("#FFA500")); break;
        }
    }

    public void next(View v){
        if(!flip && rs.getState()>=2){
            flip = true;
            side = 3;
            TextView tv = (TextView)findViewById(R.id.rvtv);
            tv.setText("Face yellow with green on top");
        }
        Integer[] turn = rs.turn();
        if(turn[0] == 0 && turn[1] == 0 && turn[2] == 0){
            TextView commands = (TextView) findViewById(R.id.commands);
            commands.setText("Solved");
            return;
        }
        TextView commands = (TextView) findViewById(R.id.commands);
        commands.setText(getCommand(turn, side).getText() + " (Move the " + ttos(turn[0]) + " centered piece from " +
                ttos(turn[1]) + " centered to " + ttos(turn[2]) +" centered)");

        rc.turn(turn[0], turn[1], turn[2]);
        updateGraphics(side);
    }

    public String ttos(int i){
        switch(i){
            case 1: return "Blue";
            case 2: return "Red";
            case 3: return "Yellow";
            case 4: return "Green";
            case 5: return "Orange";
            case 0:
            default: return "White";
        }
    }

    public void reset(View v){
        InputActivity.state = 0;
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);
        finish();
    }

}
