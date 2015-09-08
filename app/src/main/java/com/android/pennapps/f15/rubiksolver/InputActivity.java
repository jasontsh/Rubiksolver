package com.android.pennapps.f15.rubiksolver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class InputActivity extends AppCompatActivity {

    AdView mAdView;
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
            for(int i = 0; i < 6; i++){
                for(int j = 0; j < 3; j++){
                    for(int k = 0; k < 3; k++){
                        array[i][j][k] = i;
                    }
                }
            }
        }
        mySwitch();

        mAdView = (AdView) findViewById(R.id.input_ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
        }else {
            mySwitch();
        }
    }

    public void mySwitch(){
        ((TextView) findViewById(R.id.instructions)).setText(getStateString());
        if(array != null){
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    spinners[i][j].setSelection(array[state][i][j]);
                }
            }
        }
    }

    public static String getStateString(){
        switch(state){
            case 0: return "Please enter the values of the white face orientated such " +
                    "that the blue face is on the top side of the cube";
            case 1:
                return "Please enter the values of the blue face orientated such " +
                        "that the red face is on the top side of the cube";
            case 2:
                return "Please enter the values of the red face orientated such " +
                        "that the yellow face is on the top side of the cube";
            case 3:
                return "Please enter the values of the yellow face orientated such " +
                        "that the green face is on the top side of the cube";
            case 4:
                return "Please enter the values of the green face orientated such " +
                        "that the orange face is on the top side of the cube";
            case 5:
                return "Please enter the values of the orange face orientated such " +
                        "that the white face is on the top side of the cube";
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

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
