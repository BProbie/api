package com.probie.Picture;

import java.util.HashSet;
import com.probie.Type.PathType;
import java.awt.image.BufferedImage;
import com.probie.Factory.PictureFactory;

public class Picture extends PictureFactory {

    public Picture() {

    }

    public Picture(Object object) {
        super(object);
    }

    @Override
    public Picture setPicture(Object object) {
        return super.setPicture(object).getPicture();
    }

    @Override
    public Picture setPictureLocal(String localPath) {
        return super.setPictureLocal(localPath).getPicture();
    }

    @Override
    public Picture setPictureURL(String urlPath) {
        return super.setPictureURL(urlPath).getPicture();
    }

    @Override
    public Picture setPictureBufferedImage(BufferedImage bufferedImage) {
        return super.setPictureBufferedImage(bufferedImage).getPicture();
    }

    @Override
    public Picture setBufferedImage(BufferedImage bufferedImage) {
        return super.setBufferedImage(bufferedImage).getPicture();
    }

    @Override
    @Deprecated
    public Picture setUID(Object UID) {
        return super.setUID(UID).getPicture();
    }

    @Override
    @Deprecated
    public Picture setPictureName(String name) {
        return super.setPictureName(name).getPicture();
    }

    @Override
    @Deprecated
    public Picture setPictureSimpleName(String simpleName) {
        return super.setPictureSimpleName(simpleName).getPicture();
    }

    @Override
    @Deprecated
    public Picture setRGB(int[][] rgb) {
        return super.setRGB(rgb).getPicture();
    }

    @Override
    @Deprecated
    public Picture setSimpleRGB(int[][] simpleRGB) {
        return super.setSimpleRGB(simpleRGB).getPicture();
    }

    @Override
    @Deprecated
    public Picture setPicturePath(String path) {
        return super.setPicturePath(path).getPicture();
    }

    @Override
    @Deprecated
    public Picture setPathType(PathType pathType) {
        return super.setPathType(pathType).getPicture();
    }

    @Override
    @Deprecated
    public Picture setPathType(int index) {
        return super.setPathType(index).getPicture();
    }

    @Override
    public Picture setValue(Object value) {
        return super.setValue(value).getPicture();
    }

    @Override
    public Picture setValues(Object[] values) {
        return super.setValues(values).getPicture();
    }

    @Override
    public Picture setValues(HashSet<Object> values) {
        return super.setValues(values).getPicture();
    }

    @Override
    public Picture addValue(Object value) {
        return super.addValue(value).getPicture();
    }

    @Override
    public Picture addValues(Object[] values) {
        return super.addValues(values).getPicture();
    }

    @Override
    public Picture addValues(HashSet<Object> values) {
        return super.addValues(values).getPicture();
    }

    @Override
    public Picture removeValue(Object value) {
        return super.removeValue(value).getPicture();
    }

    @Override
    public Picture removeValues(Object[] values) {
        return super.removeValues(values).getPicture();
    }

    @Override
    public Picture removeValues(HashSet<Object> values) {
        return super.removeValues(values).getPicture();
    }

    @Override
    public Picture setHadCache(boolean hadCache) {
        return super.setHadCache(hadCache).getPicture();
    }

    @Override
    public Picture cache() {
        return super.cache().getPicture();
    }

    @Override
    public Picture cacheAdd() {
        return super.cacheAdd().getPicture();
    }

    @Override
    public Picture cacheRemove() {
        return super.cacheRemove().getPicture();
    }

    @Override
    public Picture cacheSetPath(String path) {
        return super.cacheSetPath(path).getPicture();
    }

    @Override
    public Picture cacheSetFileName(String fileName) {
        return super.cacheSetFileName(fileName).getPicture();
    }

    @Override
    public Picture setHadData(boolean hadData) {
        return super.setHadData(hadData).getPicture();
    }

    @Override
    public Picture study() {
        return super.study().getPicture();
    }

    @Override
    public Picture data() {
        return super.data().getPicture();
    }

    @Override
    public Picture dataAdd() {
        return super.dataAdd().getPicture();
    }

    @Override
    public Picture dataRemove() {
        return super.dataRemove().getPicture();
    }

    @Override
    @Deprecated
    public Picture dataSetPath(String path) {
        return super.dataSetPath(path).getPicture();
    }

    @Override
    @Deprecated
    public Picture dataSetFileName(String fileName) {
        return super.dataSetFileName(fileName).getPicture();
    }

    @Override
    public Picture clone() {
        return super.clone().getPicture();
    }

}