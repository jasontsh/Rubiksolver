package com.android.pennapps.f15.rubiksolver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    public static int[][][] array;
    Spinner[][] spinners;
    public static int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        spinners = new Spinner[3][3];
        spinners[0][0] = (Spinner) findViewById(R.id.spinner1);
        spinners[1][0] = (Spinner) findViewById(R.id.spinner2);
        spinners[2][0] = (Spinner) findViewById(R.id.spinner3);
        spinners[0][1] = (Spinner) findViewById(R.id.spinner4);
        spinners[1][1] = (Spinner) findViewById(R.id.spinner5);
        spinners[2][1] = (Spinner) findViewById(R.id.spinner6);
        spinners[0][2] = (Spinner) findViewById(R.id.spinner7);
        spinners[1][2] = (Spinner) findViewById(R.id.spinner8);
        spinners[2][2] = (Spinner) findViewById(R.id.spinner9);
        if(array != null){
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    spinners[i][j].setSelection(array[state][i][j]);
                }
            }
        } else {
            array = new int[6][3][3];
        }
        mySwitch();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.menu_ar){
            Intent intent = new Intent(this, ARActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void next(View v){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                array[state][i][j] = spinners[i][j].getSelectedItemPosition();
            }
        }
        state++;
        if(state >= 6){
            Intent intent = new Intent(this, RubikViewer.class);
            startActivity(intent);
            finish();
        }
        mySwitch();
    }

    public void mySwitch(){
        ((TextView) findViewById(R.id.instructions)).setText(getStateString());
        spinners[1][1].setSelection(state);
    }

    public static String getStateString(){
        switch(state){
            case 0: return "Please enter the values with white at the center, and blue on top";
            case 1:
                return "Please enter the" +
                        " values with blue at the center, and red on top";
            case 2:
                return "Please enter the" +
                        " values with red at the center, and yellow on top";
            case 3:
                return "Please enter the" +
                        " values with yellow at the center, and green on top";
            case 4:
                return"Please enter the" +
                        " values with green at the center, and orange on top";
            case 5:
                return"Please enter the" +
                        " values with orange at the center, and white on top";
        }
        return null;
    }

    public void reset(View v){
        for(Spinner[] ss: spinners){
            for (Spinner s : ss) {
                s.setSelection(0);
            }
        }
    }
}