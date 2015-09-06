package com.android.pennapps.f15.rubiksolver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        super();
    }

    private SetupState currentState = SetupState.SHOW_SIDE_WHITE;
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private int width, height;

    private CameraHandler handler = null;

    private Camera.PreviewCallback previewCallback = null;

    public SetupState getCurrentState() {
        return currentState;
    }

    @Override
    protected  void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        mCamera = CameraView.getCameraInstance();
        mCamera.setDisplayOrientation(90);
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        handler = new CameraHandler(previewSize.width, previewSize.height);

        Button setButton = (Button) findViewById(R.id.set_side_button);
        /**
         * Ordinal 4, 8 are repeats of ordinal 0
         * Ordinal 6 is a repeat of ordinal 2
         */
        final RubikColor[][][] cube = new RubikColor[9 ][3][3];
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentState != SetupState.SOLVE) {
                    RubikColor[][] colorMap = handler.getCurrentLoadedColorMap();
                    if(colorMap == null) {
                        return;
                    }
                    for(int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if(colorMap[i][j] == null) {
                                return;
                            }
                        }
                    }

                    cube[currentState.ordinal()] = colorMap;

                    currentState = SetupState.values()[currentState.ordinal() + 1];

                    // colorMap = the 2d array with the 9 colors
                    // store current cube info
                }
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
