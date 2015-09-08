package com.android.pennapps.f15.rubiksolver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;

public class ARActivity extends AppCompatActivity {
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private CameraHandler handler = null;

    private Camera.PreviewCallback previewCallback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        mCamera = CameraView.getCameraInstance();
        mCamera.setDisplayOrientation(90);
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        handler = new CameraHandler(previewSize.width, previewSize.height);
        previewCallback = new Camera.PreviewCallback() {

            @Override
            public void onPreviewFrame(byte[] data, Camera camera)  {
                execute(data);
            }

            private void execute(byte[] data) {
                Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                YuvImage yuvImg = new YuvImage(data, ImageFormat.NV21, previewSize.width, previewSize.height, null);
                yuvImg.compressToJpeg(new Rect(0, 0, previewSize.width, previewSize.height), 50, out);
                byte[] imageBytes = out.toByteArray();
                Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                if(image == null) {
                    System.out.println("NULL IMAGE PREVIEW");
                } else {
                    RubikColor[][] imageMap = handler.readImage(image);
                }
            }
        };
        mCameraView = new CameraView(this, this, mCamera, handler);
        RelativeLayout preview = (RelativeLayout) findViewById(R.id.relativeLayoutMain);
        preview.addView(mCameraView);
        mCamera.setPreviewCallback(previewCallback);

    }

    private boolean released = false;

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        released = true;
        mCamera.release();
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(released)
            mCamera.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ar, menu);
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
        if(id == R.id.menu_next){
            next();
        }
        if(id == R.id.menu_update){
            update();
        }
        return super.onOptionsItemSelected(item);
    }

    public void next(){
        RubikColor[][] colorMap = handler.getCurrentLoadedColorMap();
        if(colorMap == null) {
            return;
        }
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(colorMap[i][j] == null) {
                    return;
                }
                switch(colorMap[i][j]){
                    case RED: InputActivity.array[InputActivity.state][i][j] = 2; break;
                    case WHITE: InputActivity.array[InputActivity.state][i][j] = 0; break;
                    case BLUE: InputActivity.array[InputActivity.state][i][j] = 1; break;
                    case YELLOW: InputActivity.array[InputActivity.state][i][j] = 3; break;
                    case GREEN: InputActivity.array[InputActivity.state][i][j] = 4; break;
                    case ORANGE: InputActivity.array[InputActivity.state][i][j] = 5; break;
                }
            }
        }
        InputActivity.state++;
        if(InputActivity.state >= 6){

            Intent intent = new Intent(this, RubikViewer.class);
            startActivity(intent);
            finish();
        }
    }
    public void update(){
        RubikColor[][] colorMap = handler.getCurrentLoadedColorMap();
        if(colorMap == null) {
            return;
        }
        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(colorMap[i][j] == null){
                    InputActivity.array[InputActivity.state][i][j] = 0;
                }else {
                    switch (colorMap[i][j]) {
                        case RED:
                            InputActivity.array[InputActivity.state][i][j] = 2; break;
                        case WHITE:
                            InputActivity.array[InputActivity.state][i][j] = 0; break;
                        case BLUE:
                            InputActivity.array[InputActivity.state][i][j] = 1; break;
                        case YELLOW:
                            InputActivity.array[InputActivity.state][i][j] = 3; break;
                        case GREEN:
                            InputActivity.array[InputActivity.state][i][j] = 4; break;
                        case ORANGE:
                            InputActivity.array[InputActivity.state][i][j] = 5; break;
                    }
                }
            }
        }
        Intent intent = new Intent(this, InputActivity.class);
        startActivity(intent);
        finish();
    }
}
