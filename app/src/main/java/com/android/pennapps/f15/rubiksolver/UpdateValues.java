package com.android.pennapps.f15.rubiksolver;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class UpdateValues extends AppCompatActivity {

    private Activity mActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_values);
        Intent intent = getIntent();

        final Spinner s1 = (Spinner) findViewById(R.id.spinner1);
        s1.setSelection(intent.getIntExtra("value0", 0));
        final Spinner s2 = (Spinner) findViewById(R.id.spinner2);
        s2.setSelection(intent.getIntExtra("value1", 1));
        final Spinner s3 = (Spinner) findViewById(R.id.spinner3);
        s3.setSelection(intent.getIntExtra("value2", 2));
        final Spinner s4 = (Spinner) findViewById(R.id.spinner4);
        s4.setSelection(intent.getIntExtra("value3", 3));
        final Spinner s5 = (Spinner) findViewById(R.id.spinner5);
        s5.setSelection(intent.getIntExtra("value4", 4));
        final Spinner s6 = (Spinner) findViewById(R.id.spinner6);
        s6.setSelection(intent.getIntExtra("value5", 5));
        final Spinner s7 = (Spinner) findViewById(R.id.spinner7);
        s7.setSelection(intent.getIntExtra("value6", 6));
        final Spinner s8 = (Spinner) findViewById(R.id.spinner8);
        s8.setSelection(intent.getIntExtra("value7", 7));
        final Spinner s9 = (Spinner) findViewById(R.id.spinner9);
        s9.setSelection(intent.getIntExtra("value8", 8));
        Button cancel = (Button) findViewById(R.id.button2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.onBackPressed();
            }
        });

        Button set = (Button) findViewById(R.id.button);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(MainActivity.RESULT_UPDATE, intent);
                intent.putExtra("value1", s1.getSelectedItemPosition());
                intent.putExtra("value2", s2.getSelectedItemPosition());
                intent.putExtra("value3", s3.getSelectedItemPosition());
                intent.putExtra("value4", s4.getSelectedItemPosition());
                intent.putExtra("value5", s5.getSelectedItemPosition());
                intent.putExtra("value6", s6.getSelectedItemPosition());
                intent.putExtra("value7", s7.getSelectedItemPosition());
                intent.putExtra("value8", s8.getSelectedItemPosition());
                intent.putExtra("value9", s9.getSelectedItemPosition());
                mActivity.finish();
            }
        });

    }



    public void onBackPressed(){
        Intent returnIntent = new Intent();
        setResult(MainActivity.RESULT_CANCEL, returnIntent);
        finish();
    }
}
