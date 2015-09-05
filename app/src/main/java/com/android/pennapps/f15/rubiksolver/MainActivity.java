package com.android.pennapps.f15.rubiksolver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private Camera mCamera = null;
    private CameraView mCameraView = null;

    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {

        CameraHandler handler = new CameraHandler();

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
                execute(data);
        }

        private void execute(byte[] data) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            YuvImage yuvImg = new YuvImage(data, ImageFormat.NV21, CameraHandler.WIDTH, CameraHandler.HEIGHT, null);
            yuvImg.compressToJpeg(new Rect(0, 0, CameraHandler.WIDTH, CameraHandler.HEIGHT), 50, out);
            byte[] imageBytes = out.toByteArray();
            Bitmap image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            RubikColor[][] imageMap = handler.readImage(image);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCamera = CameraView.getCameraInstance();
        mCamera.setDisplayOrientation(90);
        mCameraView = new CameraView(this, mCamera);
        RelativeLayout preview = (RelativeLayout) findViewById(R.id.relativeLayoutMain);
        preview.addView(mCameraView);
        //mCamera.setPreviewCallback(previewCallback);
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
