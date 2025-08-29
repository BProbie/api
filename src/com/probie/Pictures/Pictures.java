package com.probie.Pictures;

import java.util.HashSet;
import com.probie.Picture.Picture;
import java.awt.image.BufferedImage;
import com.probie.Factory.PicturesFactory;

public class Pictures extends PicturesFactory {

    public Pictures() {

    }

    public Pictures(Object object) {
        super(object);
    }

    @Override
    public Pictures setPictures(Object object) {
        return super.setPictures(object).getPictures();
    }

    @Override
    public Pictures setPicturesLocal(String localPath) {
        return super.setPicturesLocal(localPath).getPictures();
    }

    @Override
    public Pictures setPictureUrl(String urlPath) {
        return super.setPictureUrl(urlPath).getPictures();
    }

    @Override
    public Pictures setPicturesBufferedImage(BufferedImage[] bufferedImages) {
        return super.setPicturesBufferedImage(bufferedImages).getPictures();
    }

    @Override
    public Pictures setPictures(Pictures pictures) {
        return super.setPictures(pictures).getPictures();
    }

    @Override
    public Pictures setPictures(Picture[] pictures) {
        return super.setPictures(pictures).getPictures();
    }

    @Override
    public Pictures setPictures(HashSet<Picture> pictures) {
        return super.setPictures(pictures).getPictures();
    }

    @Override
    public Pictures addPicture(Picture picture) {
        return super.addPicture(picture).getPictures();
    }

    @Override
    public Pictures addPictures(Pictures pictures) {
        return super.addPictures(pictures).getPictures();
    }

    @Override
    public Pictures addPictures(Picture[] pictures) {
        return super.addPictures(pictures).getPictures();
    }

    @Override
    public Pictures addPictures(HashSet<Picture> pictures) {
        return super.addPictures(pictures).getPictures();
    }

    @Override
    public Pictures removePicture(Picture picture) {
        return super.removePicture(picture).getPictures();
    }

    @Override
    public Pictures removePictures(Pictures pictures) {
        return super.removePictures(pictures).getPictures();
    }

    @Override
    public Pictures removePictures(Picture[] pictures) {
        return super.removePictures(pictures).getPictures();
    }

    @Override
    public Pictures removePictures(HashSet<Picture> pictures) {
        return super.removePictures(pictures).getPictures();
    }

    @Override
    public Pictures cache() {
        return super.cache().getPictures();
    }

    @Override
    public Pictures data() {
        return super.data().getPictures();
    }

    @Override
    public Pictures study() {
        return super.study().getPictures();
    }

    @Override
    public Pictures clone() {
        return super.clone().getPictures();
    }

}