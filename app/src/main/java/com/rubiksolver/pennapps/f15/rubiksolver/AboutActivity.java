package com.rubiksolver.pennapps.f15.rubiksolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        stringBuilder.append("email: tangsh@sas.upenn.edu\n");
        stringBuilder.append("LinkedIn: https://www.linkedin.com/profile/view?id=AAIAABTSyYwBHQTAZJaQutpWZrn6Ul0P6MDrMrI \n\n");
        stringBuilder.append("He Chen- GUI, image recognition, refactoring\n");
        stringBuilder.append("email: chenhe95@hotmail.com \n");
        stringBuilder.append("Jiahui Jiao- proofread code, test program\n");
        stringBuilder.append("email: jiaoj@seas.upenn.edu\n");
        stringBuilder.append("Jenny Chen- marketing\n");
        stringBuilder.append("email: jennche@seas.upenn.edu");
        String string = stringBuilder.toString();
        tv.setText(string);
    }


}
