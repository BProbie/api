package com.probie.Factory;

import com.probie.Interface.IRGB;
import com.probie.Picture.Picture;
import com.probie.Database.APIData;
import java.awt.image.BufferedImage;

public class RGBFactory implements IRGB {

    @Override
    public int[][] getFullRGB(Picture picture) {
        if (picture.getUID() != null) {
            int[][] rgb = picture.rgb;
            if (rgb == null) {
                if (picture.getCache().connect()) {
                    rgb = APIData.turnIntList(picture.getCache().getDefaultValue(picture.getUID(), null));
                }
                if (rgb == null) {
                    BufferedImage bufferedImage = picture.getBufferedImage();
                    rgb = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
                    for (int x = 0; x < bufferedImage.getWidth(); x++) {
                        for (int y = 0; y < bufferedImage.getHeight(); y++) {
                            rgb[x][y] = bufferedImage.getRGB(x, y);
                        }
                    }
                }
            }
            return rgb;
        } else {
            APIData.throwError("The Picture Had Not Define");
        }
        return null;
    }

    @Override
    public int[][] getNormalRGB(Picture picture) {
        if (picture.getUID() != null) {
            // TODO

        } else {
            APIData.throwError("The Picture Had Not Define");
        }
        return new int[0][];
    }

    @Override
    public int[][] getSimpleRGB(Picture picture) {
        if (picture.getUID() != null) {
            int[][] temp = picture.rgbSimple;
            if (temp == null) {
                int[][] rgb = picture.getRGB();
                int rgbWeight = rgb.length;
                int rgbHeight = rgb[0].length;

                int[][] amplifyRGB = new int[rgbWeight*APIData.AmplifyTime][rgbHeight*APIData.AmplifyTime];
                int amplifyRGBWeight = amplifyRGB.length;
                int amplifyRGBHeight = amplifyRGB[0].length;

                int amplifyRGBX = (int) Math.floor((double) (amplifyRGBWeight-rgbWeight)/2);
                int amplifyRGBY = (int) Math.floor((double) (amplifyRGBHeight-rgbHeight)/2);
                for (int rgbY = 0; rgbY < rgbHeight; rgbY++) {
                    for (int rgbX = 0; rgbX < rgbWeight; rgbX++) {
                        amplifyRGB[amplifyRGBX][amplifyRGBY] = rgb[rgbX][rgbY];
                        amplifyRGBX++;
                    }
                    amplifyRGBY++;
                    amplifyRGBX -= rgbWeight;
                }

                int[][] simpleRGB = new int[APIData.SimpleSize][APIData.SimpleSize];
                int simpleRGBX = 0;
                int simpleRGBY = 0;
                int boxWeight = (int) Math.floor((double) amplifyRGBWeight/APIData.SimpleSize);
                int boxHeight = (int) Math.floor((double) amplifyRGBHeight/APIData.SimpleSize);
                for (int y = 0; y < amplifyRGBHeight; y+=boxHeight) {
                    for (int x = 0; x < amplifyRGBWeight; x+=boxWeight) {
                        int average = 0;
                        for (int i = 0; i < boxHeight; i++) {
                            for (int j = 0; j < boxWeight; j++) {
                                try {
                                    average = (average+amplifyRGB[x][y])/2;
                                } catch (ArrayIndexOutOfBoundsException ignored) {}
                            }
                        }
                        try {
                            simpleRGB[simpleRGBX][simpleRGBY] = average;
                        } catch (ArrayIndexOutOfBoundsException ignored) {}
                        simpleRGBX++;
                    }
                    simpleRGBY++;
                    simpleRGBX = 0;
                }
                return simpleRGB;
            }
            return temp;
        } else {
            APIData.throwError("The Picture Had Not Define");
        }
        return new int[0][];
    }

    @Override
    public int[][] getRGB(Picture picture) {
        return getFullRGB(picture);
    }

}