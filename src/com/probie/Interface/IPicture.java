package com.probie.Interface;

import java.util.HashSet;
import java.util.ArrayList;
import com.probie.Database.Data;
import com.probie.Type.PathType;
import com.probie.Database.Cache;
import com.probie.Picture.Picture;
import com.probie.Pictures.Pictures;
import java.awt.image.BufferedImage;
import com.probie.Factory.PictureFactory;

public interface IPicture {

    /**
     * @param object 路径，可以是本地路径，也可以是网址，还可用是BufferedImage实例对象
     * */
    PictureFactory setPicture(Object object);
    PictureFactory setPictureLocal(String localPath);
    PictureFactory setPictureURL(String urlPath);
    PictureFactory setPictureBufferedImage(BufferedImage bufferedImage);
    PictureFactory setBufferedImage(BufferedImage bufferedImage);
    BufferedImage getBufferedImage();

    PictureFactory setUID(Object UID);
    String getUID();
    String getUIDFromCalculate();
    PictureFactory setPictureName(String name);
    String getPictureName();
    PictureFactory setPictureSimpleName(String simpleName);
    String getPictureSimpleName();

    PictureFactory setRGB(int[][] rgb);
    PictureFactory setSimpleRGB(int[][] simpleRGB);
    int[][] getRGB();
    int[][] getSimpleRGB();

    PictureFactory setPicturePath(String path);
    String getPicturePath();
    PictureFactory setPathType(PathType pathType);
    PictureFactory setPathType(int index);
    PathType getPathType();
    int getPathTypeCode();
    String getPathTypeName();
    boolean isUrl();
    boolean getIsUrl();
    String getUrlType();

    /**
     * @param value 设置图片存储的数据值，如写"猫"图片将被标记为猫进行储存
     * */
    PictureFactory setValue(Object value);
    PictureFactory setValues(Object[] values);
    PictureFactory setValues(HashSet<Object> values);

    /**
     * @param value 添加图片存储的数据值，如写"狗"图片将被增加为狗进行储存
     * */
    PictureFactory addValue(Object value);
    PictureFactory addValues(Object[] values);
    PictureFactory addValues(HashSet<Object> values);

    /**
     * @param value 删除图片存储的数据值，如写"猪"图片将被删除猪的标签
     * */
    PictureFactory removeValue(Object value);
    PictureFactory removeValues(Object[] values);
    PictureFactory removeValues(HashSet<Object> values);
    HashSet<Object> getValues();

    PictureFactory setHadCache(boolean hadCache);
    boolean isCache();
    boolean cacheHadSave();
    PictureFactory cache();
    PictureFactory cacheAdd();
    PictureFactory cacheRemove();
    PictureFactory cacheSetPath(String path);
    PictureFactory cacheSetFileName(String fileName);
    String cacheGetPath();
    String cacheGetFileName();
    String cacheGetFilePath();
    Cache getCache();

    PictureFactory setHadData(boolean hadData);
    boolean isData();
    boolean dataHadSave();
    /**
     * @deprecated 学习图片的数据，即将信息载入数据库
     * */
    PictureFactory study();
    PictureFactory data();
    PictureFactory dataAdd();
    PictureFactory dataRemove();
    PictureFactory dataSetPath(String path);
    PictureFactory dataSetFileName(String fileName);
    String dataGetPath();
    String dataGetFileName();
    String dataGetFilePath();
    Data getData();

    /**
     * @deprecated 查找图片是否在某图片内
     * @param picture 大图片
     * */
    boolean isInPicture(Picture picture);
    /**
     * @deprecated 查找图片是否包含某图片
     * @param picture 小图片
     * */
    boolean isHasPicture(Picture picture);

    /**
     * @deprecated 查找图片在某图片的坐标，返回的坐标通常偏向左上角
     * @param picture 大图片
     * */
    int[] getInPictureWhere(Picture picture);
    /**
     * @deprecated 查找被包含的图片的坐标，返回的坐标通常偏向左上角
     * @param picture 小图片
     * */
    int[] getWhereHasPicture(Picture picture);

    ArrayList<Boolean> isInPictures(Pictures pictures);
    ArrayList<Boolean> isHasPictures(Pictures pictures);

    ArrayList<int[]> getInPicturesWhere(Pictures pictures);
    ArrayList<int[]> getWhereHasPictures(Pictures pictures);

    Pictures toPictures();

    /**
     * @deprecated 猜测图片的数据，需要先进行study()
     * */
    Object guess();
    Object getGuess();

    Picture getPicture();

}