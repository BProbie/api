package com.probie.Interface;

import java.util.HashSet;
import java.util.ArrayList;
import com.probie.Picture.Picture;
import com.probie.Pictures.Pictures;
import java.awt.image.BufferedImage;
import com.probie.Factory.PicturesFactory;

public interface IPictures {

    /**
     * @param object 路径，可以是本地目录，也可以是BufferedImage[]实例对象。支持使用如"D:\图片"、"D:\图片\"、"D:\图片\*"、"D:\图片\*.png"等写法
     * */
    PicturesFactory setPictures(Object object);
    PicturesFactory setPicturesLocal(String localPath);
    PicturesFactory setPictureUrl(String urlPath);
    PicturesFactory setPicturesBufferedImage(BufferedImage[] bufferedImages);

    PicturesFactory setPictures(Pictures pictures);
    PicturesFactory setPictures(Picture[] pictures);
    PicturesFactory setPictures(HashSet<Picture> pictures);

    PicturesFactory addPicture(Picture picture);
    PicturesFactory addPictures(Pictures pictures);
    PicturesFactory addPictures(Picture[] pictures);
    PicturesFactory addPictures(HashSet<Picture> pictures);

    PicturesFactory removePicture(Picture picture);
    PicturesFactory removePictures(Pictures pictures);
    PicturesFactory removePictures(Picture[] pictures);
    PicturesFactory removePictures(HashSet<Picture> pictures);

    Picture[] toArray();
    HashSet<Picture> toHashSet();

    PicturesFactory cache();
    PicturesFactory data();
    PicturesFactory study();

    ArrayList<Object> guess();

    Pictures getPictures();

}