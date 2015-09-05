package com.android.pennapps.f15.rubiksolver;

import android.graphics.Bitmap;

import java.util.Arrays;

/**
 * Created by He on 9/5/2015.
 */
public class CameraHandler {
    /**
     * Dimensions of phone screen
     */
    public static final int HEIGHT = 0;
    public static final int WIDTH = 0;
    /**
     * The defined square on the android screen where the user will put the rubik cube in the square
     * and the algorithm will look for the rubik cube in this square
     */
    private int[] cornerXY = {(int) (WIDTH * 0.125), (int) (WIDTH * 0.875), (int) (HEIGHT * 0.875),
            (int) ((HEIGHT * 0.125) + (0.75 * Math.min(WIDTH, HEIGHT)))};
    private static final int CUBE_SIZE = 3;
    private static final int SCAN_SIZE = 60;

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
     * @param img
     * @return
     */
    public RubikColor[][] readImage(Bitmap img) {
        int slotWidth = (cornerXY[1] - cornerXY[0]) / 3;
        int slotCenter = slotWidth / 2;
        int xOffset = cornerXY[0];
        int yOffset = cornerXY[2];
        RubikColor[][] colorMap = new RubikColor[CUBE_SIZE][CUBE_SIZE];
        for (int i = 0; i < CUBE_SIZE; ++i) {
            for (int j = 0; j < CUBE_SIZE; ++j) {
                int projectedX = xOffset + slotCenter + i * slotWidth;
                int projectedY = yOffset + slotCenter + j * slotWidth;
                /**
                 * colorCounts[0] -> red colorCounts[1] -> green colorCounts[2]
                 * -> blue colorCounts[3] -> orange colorCounts[4] -> yellow
                 * colorCounts[5] -> white
                 */
                int[] colorCounts = {0, 0, 0, 0, 0, 0};
                for (int x = projectedX - SCAN_SIZE; x < projectedX + SCAN_SIZE; ++x) {
                    for (int y = projectedY - SCAN_SIZE; y < projectedY + SCAN_SIZE; ++y) {

                        int colorHex = img.getPixel(y, x);
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
                System.out.println(
                        i + " " + j + " " + Arrays.toString(colorCounts) + " " + projectedX + " " +
                                projectedY);
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
        System.out.println(Arrays.toString(colorMap[0]));
        System.out.println(Arrays.toString(colorMap[1]));
        System.out.println(Arrays.toString(colorMap[2]));
        return colorMap;
    }
}
