package com.probie.Factory;

import java.io.File;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import com.probie.Picture.Picture;
import com.probie.Database.APIData;
import com.probie.Pictures.Pictures;
import java.awt.image.BufferedImage;
import com.probie.Interface.IPictures;

public class PicturesFactory implements IPictures, Cloneable {

    private HashSet<Picture> pictures = new HashSet<>();

    public PicturesFactory() {

    }

    public PicturesFactory(Object object) {
        setPictures(object);
    }

    @Override
    public PicturesFactory setPictures(Object object) {
        if (object instanceof BufferedImage[]) {
            setPicturesBufferedImage((BufferedImage[]) object);
        } else {
            String temp = object.toString().toLowerCase();
            if (temp.startsWith("http://")||temp.startsWith("https://")||temp.startsWith("ftp://")||temp.startsWith("ssh://")) {
                setPictureUrl(object.toString());
            } else {
                setPicturesLocal(object.toString());
            }
        }
        return this;
    }

    @Override
    public PicturesFactory setPicturesLocal(String localPath) {
        String fileType = APIData.ChoseAll;
        if (localPath.contains(".")) {
            String[] temp = localPath.split("\\.");
            if (temp[temp.length-1-1].endsWith("*")) {
                fileType = temp[temp.length-1];
                localPath = localPath.replace("*","").replace("."+fileType,"");
            }
        }
        File localFile = new File(localPath);
        if (localFile.exists()) {
            if (localFile.isDirectory()) {
                File[] files = localFile.listFiles();
                if (files != null) {
                    if (fileType.equals(APIData.ChoseAll)) {
                        for (File file : files) {
                            addPicture(new Picture().setPictureLocal(file.getPath()));
                        }
                    } else {
                        int count = 0;
                        for (File file : files) {
                            if (file.getName().endsWith(fileType)) {
                                count +=1;
                                addPicture(new Picture().setPictureLocal(file.getPath()));
                            }
                        }
                        if (count == 0) {
                            APIData.throwError("Can Not Find "+fileType+" In The Path "+localPath);
                        }
                    }
                } else {
                    APIData.throwError("Can Not Get Files Maybe For Air Or Has Not Permission");
                }
            } else {
                APIData.throwError("The Path "+localPath+" Is Not Directory");
            }
        } else {
            APIData.throwError("The Path "+localPath+" Is Not Exists");
        }
        return this;
    }

    @Override
    public PicturesFactory setPictureUrl(String urlPath) {
        return new Picture().setPictureURL(urlPath).toPictures();
    }

    @Override
    public PicturesFactory setPicturesBufferedImage(BufferedImage[] bufferedImages) {
        for (BufferedImage bufferedImage : bufferedImages) {
            addPicture(new Picture().setPictureBufferedImage(bufferedImage));
        }
        return this;
    }

    @Override
    public PicturesFactory setPictures(Pictures pictures) {
        setPictures(pictures.toHashSet());
        return this;
    }

    @Override
    public PicturesFactory setPictures(Picture[] pictures) {
        setPictures(new HashSet<>(List.of(pictures)));
        return this;
    }

    @Override
    public PicturesFactory setPictures(HashSet<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    @Override
    public PicturesFactory addPicture(Picture picture) {
        this.pictures.add(picture);
        return this;
    }

    @Override
    public PicturesFactory addPictures(Pictures pictures) {
        addPictures(pictures.toHashSet());
        return this;
    }

    @Override
    public PicturesFactory addPictures(Picture[] pictures) {
        addPictures(new HashSet<>(List.of(pictures)));
        return this;
    }

    @Override
    public PicturesFactory addPictures(HashSet<Picture> pictures) {
        this.pictures.addAll(pictures);
        return this;
    }

    @Override
    public PicturesFactory removePicture(Picture picture) {
        this.pictures.remove(picture);
        return this;
    }

    @Override
    public PicturesFactory removePictures(Pictures pictures) {
        removePictures(pictures.toHashSet());
        return this;
    }

    @Override
    public PicturesFactory removePictures(Picture[] pictures) {
        removePictures(new HashSet<>(List.of(pictures)));
        return this;
    }

    @Override
    public PicturesFactory removePictures(HashSet<Picture> pictures) {
        for (Picture picture : pictures) {
            removePicture(picture);
        }
        return this;
    }

    @Override
    public Picture[] toArray() {
        return (Picture[]) this.pictures.toArray();
    }

    @Override
    public HashSet<Picture> toHashSet() {
        return this.pictures;
    }

    @Override
    public PicturesFactory cache() {
        for (Picture picture : pictures) {
            picture.cache();
        }
        return this;
    }

    @Override
    public PicturesFactory data() {
        for (Picture picture : pictures) {
            picture.data();
        }
        return this;
    }

    @Override
    public PicturesFactory study() {
        data();
        return this;
    }

    @Override
    public ArrayList<Object> guess() {
        ArrayList<Object> arrayList = new ArrayList<>();
        for (Picture picture : pictures) {
            arrayList.add(picture.guess());
        }
        return arrayList;
    }

    @Override
    public Pictures getPictures() {
        return (Pictures) this;
    }

    @Override
    public PicturesFactory clone() {
        try {
            return (PicturesFactory) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

}