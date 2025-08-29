package com.probie.Interface;

import com.probie.Picture.Picture;

public interface IRGB {
    int[][] getFullRGB(Picture picture);
    int[][] getNormalRGB(Picture picture);
    int[][] getSimpleRGB(Picture picture);
    int[][] getRGB(Picture picture);
}