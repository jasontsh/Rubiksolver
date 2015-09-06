package com.android.pennapps.f15.rubiksolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class RubikViewer extends AppCompatActivity {

    public RubiksCube rc;
    public RubiksSolver rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubik_viewer);

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

        
    }


}
