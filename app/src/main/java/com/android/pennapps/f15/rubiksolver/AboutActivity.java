package com.android.pennapps.f15.rubiksolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView tv = (TextView) findViewById(R.id.about_tv);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This application is built during PennApps, a hackathon hosted by");
        stringBuilder.append(" University of Pennsylvania, from September 4th to September ");
        stringBuilder.append("6th.  Subsequent edits have been made to solve some bugs and ");
        stringBuilder.append("improve user experience.  \n\n");
        stringBuilder.append("The developers are: \n");
        stringBuilder.append("Jason Tang- rubik's cube structure, solution, and GUI.\n");
        stringBuilder.append("tangsh@sas.upenn.edu\n\n");
        stringBuilder.append("He Chen- GUI, image recognition, refactoring\n");
        stringBuilder.append("****insert email here****");
        stringBuilder.append("*****mei mei, write your own contribution here");
        String string = stringBuilder.toString();
        tv.setText(string);
    }


}
