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

    public static final int RESULT_CANCEL=0;
    public static final int RESULT_UPDATE=1;
    private SetupState currentState = SetupState.SHOW_SIDE_WHITE;
    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private int width, height;

    private CameraHandler handler = null;

    private Context mContext = this;
    private Activity mActivity = this;
    private Camera.PreviewCallback previewCallback = null;
    private RubikColor[][][] cube = new RubikColor[9 ][3][3];

    public SetupState getCurrentState() {
        return currentState;
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

        Button updateButton = (Button) findViewById(R.id.set_update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentState != SetupState.SOLVE) {
                    RubikColor[][] colorMap = handler.getCurrentLoadedColorMap();
                    if (colorMap == null) {
                        return;
                    }
                    Intent intent = new Intent(mContext, UpdateValues.class);
                    intent.putExtra("currentState", currentState.ordinal());

                    mActivity.startActivityForResult(intent, 1);
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (colorMap[i][j] == null) {
                                return;
                            }
                            switch(colorMap[i][j]){
                                case RED: intent.putExtra("value" + 3*i+j, 2); break;
                                case BLUE: intent.putExtra("value" + 3*i+j, 1); break;
                                case WHITE: intent.putExtra("value" + 3*i+j, 0); break;
                                case YELLOW: intent.putExtra("value" + 3*i+j, 3); break;
                                case GREEN: intent.putExtra("value" + 3*i+j, 4); break;
                                case ORANGE: intent.putExtra("value" + 3*i+j, 5); break;
                            }

                        }
                    }
                    mActivity.startActivityForResult(intent, 1);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_UPDATE){
            RubikColor rc1;
            switch(data.getIntExtra("value1", 0)){
                case 1: rc1 = RubikColor.BLUE; break;
                case 2: rc1 = RubikColor.RED; break;
                case 3: rc1 = RubikColor.YELLOW; break;
                case 4: rc1 = RubikColor.GREEN; break;
                case 5: rc1 = RubikColor.ORANGE; break;
                case 0:
                default: rc1 = RubikColor.WHITE; break;
            }
            cube[currentState.ordinal()][0][0] = rc1;
            RubikColor rc2;
            switch(data.getIntExtra("value2", 0)){
                case 1: rc2 = RubikColor.BLUE; break;
                case 2: rc2 = RubikColor.RED; break;
                case 3: rc2 = RubikColor.YELLOW; break;
                case 4: rc2 = RubikColor.GREEN; break;
                case 5: rc2 = RubikColor.ORANGE; break;
                case 0:
                default: rc2 = RubikColor.WHITE; break;
            }
            cube[currentState.ordinal()][0][1] = rc2;
            RubikColor rc3;
            switch(data.getIntExtra("value3", 0)){
                case 1: rc3 = RubikColor.BLUE; break;
                case 2: rc3 = RubikColor.RED; break;
                case 3: rc3 = RubikColor.YELLOW; break;
                case 4: rc3 = RubikColor.GREEN; break;
                case 5: rc3 = RubikColor.ORANGE; break;
                case 0:
                default: rc3 = RubikColor.WHITE; break;
            }
            cube[currentState.ordinal()][0][2] = rc3;
            RubikColor rc4;
            switch(data.getIntExtra("value4", 0)){
                case 1: rc4 = RubikColor.BLUE; break;
                case 2: rc4 = RubikColor.RED; break;
                case 3: rc4 = RubikColor.YELLOW; break;
                case 4: rc4 = RubikColor.GREEN; break;
                case 5: rc4 = RubikColor.ORANGE; break;
                case 0:
                default: rc4 = RubikColor.WHITE; break;
            }
            cube[currentState.ordinal()][1][0] = rc4;
            RubikColor rc5;
            switch(data.getIntExtra("value5", 0)){
                case 1: rc5 = RubikColor.BLUE; break;
                case 2: rc5 = RubikColor.RED; break;
                case 3: rc5 = RubikColor.YELLOW; break;
                case 4: rc5 = RubikColor.GREEN; break;
                case 5: rc5 = RubikColor.ORANGE; break;
                case 0:
                default: rc5 = RubikColor.WHITE; break;
            }
            cube[currentState.ordinal()][1][1] = rc5;
            RubikColor rc6;
            switch(data.getIntExtra("value6", 0)){
                case 1: rc6 = RubikColor.BLUE; break;
                case 2: rc6 = RubikColor.RED; break;
                case 3: rc6 = RubikColor.YELLOW; break;
                case 4: rc6 = RubikColor.GREEN; break;
                case 5: rc6 = RubikColor.ORANGE; break;
                case 0:
                default: rc6 = RubikColor.WHITE; break;
            }
            cube[currentState.ordinal()][1][2] = rc6;
            RubikColor rc7;
            switch(data.getIntExtra("value7", 0)){
                case 1: rc7 = RubikColor.BLUE; break;
                case 2: rc7 = RubikColor.RED; break;
                case 3: rc7 = RubikColor.YELLOW; break;
                case 4: rc7 = RubikColor.GREEN; break;
                case 5: rc7 = RubikColor.ORANGE; break;
                case 0:
                default: rc7 = RubikColor.WHITE; break;
            }
            cube[currentState.ordinal()][2][0] = rc7;
            RubikColor rc8;
            switch(data.getIntExtra("value8", 0)){
                case 1: rc8 = RubikColor.BLUE; break;
                case 2: rc8 = RubikColor.RED; break;
                case 3: rc8 = RubikColor.YELLOW; break;
                case 4: rc8 = RubikColor.GREEN; break;
                case 5: rc8 = RubikColor.ORANGE; break;
                case 0:
                default: rc8 = RubikColor.WHITE; break;
            }
            cube[currentState.ordinal()][2][1] = rc8;
            RubikColor rc9;
            switch(data.getIntExtra("value9", 0)){
                case 1: rc9 = RubikColor.BLUE; break;
                case 2: rc9 = RubikColor.RED; break;
                case 3: rc9 = RubikColor.YELLOW; break;
                case 4: rc9 = RubikColor.GREEN; break;
                case 5: rc9 = RubikColor.ORANGE; break;
                case 0:
                default: rc9 = RubikColor.WHITE; break;
            }
            cube[currentState.ordinal()][2][2] = rc9;

            currentState = SetupState.values()[currentState.ordinal() + 1];
        }
    }


    @Override
    protected void onPause(){
        super.onPause();
        mCamera.release();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mCamera.open();
    }
}
