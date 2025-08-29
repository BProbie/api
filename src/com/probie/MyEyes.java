package com.probie;

import java.awt.*;
import java.io.File;
import java.net.URL;
import com.probie.AI.Eyes;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.probie.Manager.RGB;
import com.probie.Database.Data;
import com.probie.Manager.Config;
import com.probie.Database.Cache;
import com.probie.Picture.Picture;
import com.probie.Database.APIData;
import java.awt.image.BufferedImage;
import com.probie.Manager.PictureAttributeManager;
import com.probie.Attribute.PictureMatchAttribute;

public class MyEyes {

    public static MyEyes INSTANCE = new MyEyes();

    public static Cache cache = new Cache();
    public static Data data = new Data();

    private static final Eyes eyes = new Eyes();

    private static final RGB rgb = new RGB();
    private static final Config config = new Config();
    private static final PictureAttributeManager pictureAttributeManager = new PictureAttributeManager();

    public static MyEyes getInstance() {
        return INSTANCE;
    }

    public static Picture cache(Picture picture) {
        return config.cache(picture);
    }

    public static Picture data(Picture picture) {
        return config.data(picture);
    }

    public static Picture study(Picture picture) {
        return eyes.study(picture);
    }

    public static Object guess(Picture picture) {
        return eyes.guess(picture);
    }

    public static int[][] getRGB(Picture picture) {
        return rgb.getRGB(picture);
    }

    public static int[][] getSimpleRGB(Picture picture) {
        return rgb.getSimpleRGB(picture);
    }

    public static PictureMatchAttribute getPictureMatchAttribute(Picture smallPicture, Picture bigPicture) {
        return pictureAttributeManager.getPictureMatchAttribute(smallPicture, bigPicture);
    }

    public static boolean isPictureInPicture(Picture smallPicture, Picture bigPicture) {
        return getPictureMatchAttribute(smallPicture, bigPicture).getIsInclude();
    }

    public static boolean isPictureHasPicture(Picture bigPicture, Picture smallPicture) {
        return isPictureInPicture(smallPicture, bigPicture);
    }

    public static int[] whereInPicture(Picture smallPicture, Picture bigPicture) {
        return getPictureMatchAttribute(smallPicture, bigPicture).getInXY();
    }

    public static int[] whereHasPicture(Picture bigPicture, Picture smallPicture) {
        return whereInPicture(smallPicture, bigPicture);
    }

    public static BufferedImage getBufferedImage(Object autoPath) {
        String temp = autoPath.toString().toLowerCase();
        if (temp.startsWith("http://")||temp.startsWith("https://")||temp.startsWith("ftp://")||temp.startsWith("ssh://")) {
            try {
                return ImageIO.read(new URL(autoPath.toString()).openConnection().getInputStream());
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
        try {
            return ImageIO.read(new File(autoPath.toString()));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static BufferedImage getScreen() {
        Dimension dimension = APIData.getScreenSize();
        try {
            return new Robot().createScreenCapture(new Rectangle(0, 0, dimension.width, dimension.height));
        } catch (AWTException awtException) {
            throw new RuntimeException(awtException);
        }
    }

    public static Cache getCache() {
        return cache;
    }

    public static Data getData() {
        return data;
    }

    public static Eyes getEyes() {
        return eyes;
    }

    public static RGB getRgb() {
        return rgb;
    }

    public static Config getConfig() {
        return config;
    }

    public static PictureAttributeManager getPictureAttributeManager() {
        return pictureAttributeManager;
    }

    /**
     * @param value 重要参数，控制图像扫描时的误差范围，默认为10
     * */
    public static void setRGBMistakeRange(int value) {
        APIData.RGBMistakeRange = value;
    }

    public static int getRGBMistakeRange() {
        return APIData.RGBMistakeRange;
    }

    /**
     * @param value 重要参数，控制图像扫描时的误差百分数，数值为0-100，单位为%，默认为10
     * */
    public static void setRGBMistakeRate(int value) {
        APIData.RGBMistakeRate = value;
    }

    public static int getRGBMistakeRate() {
        return APIData.RGBMistakeRate;
    }

    /**
     * @param value 重要参数，控制图像识别的误差范围，默认为10
     * */
    public static void setGuessMistakeRange(int value) {
        APIData.GuessMistakeRange = value;
    }

    public static int getGuessMistakeRange() {
        return APIData.GuessMistakeRange;
    }

    /**
     * @param value 重要参数，控制图像识别的误差百分数,数值为0-100，单位为%，默认为60
     * */
    public static void setGuessMistakeRate(int value) {
        APIData.GuessMistakeRate = value;
    }

    public static int getGuessMistakeRate() {
        return APIData.GuessMistakeRate;
    }

    /**
     * @param value 重要参数，控制存入data的像素大小，为value×value，默认为40
     * */
    public static void setSimpleSize(int value) {
        APIData.SimpleSize = value;
    }

    public static int getSimpleSize() {
        return APIData.SimpleSize;
    }

    /**
     * @param value 重要参数，控制特征像素模糊化处理大放大倍数，默认为3
     * */
    public static void setAmplifyTime(int value) {
        APIData.AmplifyTime = value;
    }

    public static int getAmplifyTime() {
        return APIData.AmplifyTime;
    }

}