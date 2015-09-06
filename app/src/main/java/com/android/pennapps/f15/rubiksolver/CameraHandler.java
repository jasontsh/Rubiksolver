package com.android.pennapps.f15.rubiksolver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

/**
 * Created by He on 9/5/2015.
 */
public class CameraHandler {

    public CameraHandler(int width, int height) {
        this.width = width;
        this.height = height;
        System.out.println(width + " w/h " + height);
        cornerXY = new int[]{(int) (width * 0.125),
                ((int) ((width * 0.125) + (0.75 * Math.min(width, height)))),
                (int) (height * 0.125),
                ((int) ((height * 0.125) + (0.75 * Math.min(width, height))))};
        slotWidth = (cornerXY[1] - cornerXY[0]) / 3;
        slotCenter = slotWidth / 2;
        xOffset = cornerXY[0];
        yOffset = cornerXY[2];
    }

    private RubikColor[][] globalColorMap = null;
    private int width = 0, height = 0;
    private int slotWidth = 0;
    private int slotCenter = 0;
    private int xOffset = 0;
    private int yOffset = 0;
    /**
     * The defined square on the android screen where the user will put the rubik cube in the square
     * and the algorithm will look for the rubik cube in this square
     */
    private int[] cornerXY = null;
    private static final int CUBE_SIZE = 3;
    private static final int SCAN_SIZE = 60;
    private int z = 0;
    private Semaphore sem = new Semaphore(1);

    public RubikColor[][] getCurrentLoadedColorMap() {
        return globalColorMap;
    }

    public void drawTargetbox(Canvas cv) {
        if (cv == null) {
            return;
        }
        Paint paint = new Paint();
        paint.setTextSize(26);
        paint.setColor(0xFFFF0000);
        paint.setStyle(Paint.Style.STROKE);
        Rect rect = new Rect(cornerXY[0], cornerXY[2], cornerXY[1], cornerXY[3] + z);
        cv.drawRect(rect, paint);
        try {
            sem.acquire();
        } catch (InterruptedException e) {

        }
        if (globalColorMap != null) {
            synchronized (globalColorMap) {
                for (int i = 0; i < globalColorMap.length; i++) {
                    for (int j = 0; j < globalColorMap[0].length; j++) {
                        if (globalColorMap[i][j] != null) {
                            int projectedX = xOffset + slotCenter + i * slotWidth - 11;
                            int projectedY = yOffset + slotCenter + j * slotWidth - 11;
                            paint.setColor(globalColorMap[i][j].getHexColor());
                            cv.drawText(globalColorMap[i][j].toString().charAt(0) +
                                    "", projectedX, projectedY, paint);
                        }
                    }
                }
            }
        }
        sem.release();
    }

    private RubikColor findColorMatch(int red, int green, int blue) {
        RubikColor color = null;
        double minDifference = Double.MAX_VALUE;
        for (RubikColor c : RubikColor.values()) {
            double sum = Math.pow(red - c.getColor()[0], 2) + Math.pow(green - c.getColor()[1], 2) +
                    Math.pow(blue - c.getColor()[2], 2);
            sum = Math.sqrt(sum);
            if (sum < minDifference) {
                color = c;
                minDifference = sum;
            }
        }
        return color;
    }

    /**
     * Reads the bitmap image img and outputs the color of the rubik cube
     *
     * @param img
     * @return
     */
    public RubikColor[][] readImage(Bitmap img) {
        RubikColor[][] colorMap = new RubikColor[CUBE_SIZE][CUBE_SIZE];
        for (int i = 0; i < CUBE_SIZE; ++i) {
            for (int j = 0; j < CUBE_SIZE; ++j) {
                int projectedX = xOffset + slotCenter + i * slotWidth;
                int projectedY = yOffset + slotCenter + j * slotWidth;
                int[] colorCounts = {0, 0, 0, 0, 0, 0};
                for (int x = projectedX - SCAN_SIZE; x < projectedX + SCAN_SIZE; ++x) {
                    for (int y = projectedY - SCAN_SIZE; y < projectedY + SCAN_SIZE; ++y) {
                        int colorHex = img.getPixel(x, y);

                        int red = (colorHex >> 0x10) & 0xff;
                        int green = (colorHex >> 0x8) & 0xff;
                        int blue = (colorHex) & 0xff;
                        RubikColor mostLikelyColor = findColorMatch(red, green, blue);
                        if (mostLikelyColor.equals(RubikColor.BLACK)) {
                            continue;
                        }
                        ++colorCounts[mostLikelyColor.ordinal()];
                    }
                }
                RubikColor match = null;
                int highestMatch = 0;
                for (int k = 0; k < colorCounts.length; ++k) {
                    if (colorCounts[k] > highestMatch) {
                        match = RubikColor.values()[k];
                        highestMatch = colorCounts[k];
                    }
                }
                colorMap[i][j] = match;
            }
        }
        try {
            sem.acquire();
        } catch (InterruptedException e) {

        }
        globalColorMap = colorMap;

        sem.release();
        return colorMap;
    }
}
