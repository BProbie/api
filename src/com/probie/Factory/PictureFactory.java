package com.probie.Factory;

import java.util.*;
import java.io.File;
import java.net.URL;
import com.probie.MyEyes;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import com.probie.Database.Data;
import com.probie.Type.PathType;
import com.probie.Database.Cache;
import com.probie.Picture.Picture;
import java.security.MessageDigest;
import com.probie.Database.APIData;
import com.probie.Pictures.Pictures;
import java.awt.image.BufferedImage;
import com.probie.Interface.IPicture;
import java.security.NoSuchAlgorithmException;

public class PictureFactory implements IPicture, Cloneable {

    private final Data data = new Data();
    private final Cache cache = new Cache();

    private boolean hadData = false;
    private boolean hadCache = false;

    public int[][] rgb;
    public int[][] rgbSimple;
    private HashSet<Object> values;

    private String UID;
    private String picturePath;
    private String pictureName;
    private String pictureSimpleName;
    private BufferedImage bufferedImage;
    private PathType pathType = PathType.Unknow;

    public PictureFactory() {

    }

    public PictureFactory(Object object) {
        setPicture(object);
    }

    @Override
    public PictureFactory setPicture(Object object) {
        if (object instanceof BufferedImage) {
            setPictureBufferedImage((BufferedImage) object);
        } else {
            String temp = object.toString().toLowerCase();
            if (temp.startsWith("http://")||temp.startsWith("https://")||temp.startsWith("ftp://")||temp.startsWith("ssh://")) {
                setPictureURL(object.toString());
            } else {
                setPictureLocal(object.toString());
            }
        }
        return this;
    }

    @Override
    public PictureFactory setPictureLocal(String localPath) {
        File file = new File(localPath);
        if (file.exists()) {
            if (file.isFile()) {
                // 设置路径
                setPicturePath(localPath);

                // 设置名称
                String name = file.getName();
                if (name.contains(".")) {
                    name = name.split("\\.")[0];
                }
                setPictureName(name);
                setPictureSimpleName(name.split(APIData.SimpleMark)[0].trim());

                // 设置数据
                values = new HashSet<>(Collections.singleton(getPictureSimpleName()));

                // 设置图片
                try {
                    setBufferedImage(ImageIO.read(new File(localPath)));
                } catch (IOException ioException) {
                    throw new RuntimeException(ioException);
                }

                // 设置类别
                setPathType(PathType.Local);

                // 设置UID
                setUID(getUIDFromCalculate());

                // 设置数据库
                data.setFileName(getPictureSimpleName()+APIData.DataTail);
                cache.appendPath(getPathType().getTypeName()).setFileName(getUID().toCharArray()[0]+APIData.CacheTail);

                // 重置参数
                this.hadData = false;
                this.hadCache = false;
                this.rgb = null;
                this.rgbSimple = null;
            } else {
                APIData.throwError("The Path "+localPath+" Is Not A File");
            }
        } else {
            APIData.throwError("The File "+localPath+" Is Not Exists");
        }
        return this;
    }

    @Override
    public PictureFactory setPictureURL(String urlPath) {
        // 默认类别
        setPathType(PathType.Url);

        // 设置路径
        setPicturePath(urlPath);

        // 设置名称
        String name = urlPath.split("/")[urlPath.split("/").length-1];
        if (name.contains(".")) name = name.split("\\.")[0];
        setPictureName(name);
        setPictureSimpleName(name.split(APIData.SimpleMark)[0].trim());

        // 设置数据
        values = new HashSet<>(Collections.singleton(getPictureSimpleName()));

        // 设置图片
        try {
            setBufferedImage(ImageIO.read(new URL(urlPath).openConnection().getInputStream()));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        // 设置类别
        String temp = urlPath.toLowerCase();
        if (temp.startsWith("http://")) {
            setPathType(PathType.Url_Http);
        }
        else if (temp.startsWith("https://")) {
            setPathType(PathType.Url_Https);
        }
        else if (temp.startsWith("ftp://")) {
            setPathType(PathType.Url_Ftp);
        }
        else if (temp.startsWith("ssh://")) {
            setPathType(PathType.Url_Ssh);
        }

        // 设置UID
        setUID(getUIDFromCalculate());

        // 设置数据库
        data.setFileName(getPictureSimpleName()+APIData.DataTail);
        cache.appendPath(getPathType().getTypeName()).appendPath(getPathType().getUrlType()).setFileName(getUID().toCharArray()[0] +APIData.CacheTail);

        // 重置参数
        this.hadData = false;
        this.hadCache = false;
        this.rgb = null;
        this.rgbSimple = null;
        return this;
    }

    @Override
    public PictureFactory setPictureBufferedImage(BufferedImage bufferedImage) {
        // 设置图片
        setBufferedImage(bufferedImage);

        // 设置类型
        setPathType(PathType.BufferedImage);

        // 设置UID
        setUID(getUIDFromCalculate());

        // 设置数据库
        data.setFileName(getPictureSimpleName()+APIData.DataTail);
        cache.appendPath(getPathType().getTypeName()).setFileName(getUID().toCharArray()[0]+APIData.CacheTail);

        // 重置参数
        this.picturePath = null;
        this.pictureName = null;
        this.pictureSimpleName = null;
        this.values = null;
        this.hadData = false;
        this.hadCache = false;
        this.rgb = null;
        this.rgbSimple = null;
        return this;
    }

    @Override
    public PictureFactory setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        return this;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    @Override
    @Deprecated
    public PictureFactory setUID(Object UID) {
        this.UID = UID.toString();
        return this;
    }

    @Override
    public String getUID() {
        return this.UID;
    }

    @Override
    @Deprecated
    public String getUIDFromCalculate() {
        if (getPathType() == PathType.Local) {
            StringBuilder ID = new StringBuilder();
            try (FileInputStream fileInputStream = new FileInputStream(getPicturePath())) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buffer)) != -1) messageDigest.update(buffer, 0, length);
                byte[] digestBytes = messageDigest.digest();
                for (byte b : digestBytes) {
                    ID.append(String.format("%02x", b & 0xff));
                }
            } catch (NoSuchAlgorithmException | IOException error) {
                throw new RuntimeException(error);
            }
            return ID.toString();
        }
        else if (getIsUrl()) {
            return getPicturePath();
        }
        else if (getPathType() == PathType.BufferedImage) {
            return bufferedImage.toString();
        }
        return null;
    }

    @Override
    @Deprecated
    public PictureFactory setPictureName(String name) {
        this.pictureName = name;
        return this;
    }

    @Override
    public String getPictureName() {
        return this.pictureName;
    }

    @Override
    @Deprecated
    public PictureFactory setPictureSimpleName(String simpleName) {
        this.pictureSimpleName = simpleName;
        return this;
    }

    @Override
    public String getPictureSimpleName() {
        return this.pictureSimpleName;
    }

    @Override
    @Deprecated
    public PictureFactory setRGB(int[][] rgb) {
        this.rgb = rgb;
        return this;
    }

    @Override
    @Deprecated
    public PictureFactory setSimpleRGB(int[][] simpleRGB) {
        this.rgbSimple = simpleRGB;
        return this;
    }

    @Override
    public int[][] getRGB() {
        if (this.rgb == null) {
            this.rgb = MyEyes.getRGB(this.getPicture());
        }
        return this.rgb;
    }

    @Override
    public int[][] getSimpleRGB() {
        if (this.rgbSimple == null) {
            this.rgbSimple = MyEyes.getSimpleRGB(this.getPicture());
        }
        return this.rgbSimple;
    }

    @Override
    @Deprecated
    public PictureFactory setPicturePath(String path) {
        this.picturePath = path;
        return this;
    }

    @Override
    public String getPicturePath() {
        return this.picturePath;
    }

    @Override
    @Deprecated
    public PictureFactory setPathType(PathType pathType) {
        this.pathType = pathType;
        return this;
    }

    @Override
    @Deprecated
    public PictureFactory setPathType(int index) {
        this.pathType = PathType.getPathType(index);
        return this;
    }

    @Override
    public PathType getPathType() {
        return this.pathType;
    }

    @Override
    public int getPathTypeCode() {
        return getPathType().getTypeCode();
    }

    @Override
    public String getPathTypeName() {
        return getPathType().getTypeName();
    }

    @Override
    public boolean isUrl() {
        return getIsUrl();
    }

    @Override
    public boolean getIsUrl() {
        return getPathType().getIsUrl();
    }

    @Override
    public String getUrlType() {
        return getPathType().getUrlType();
    }

    @Override
    public PictureFactory setValue(Object value) {
        values = new HashSet<>(List.of(value));
        return this;
    }

    @Override
    public PictureFactory setValues(Object[] values) {
        setValues(new HashSet<>(List.of(values)));
        return this;
    }

    @Override
    public PictureFactory setValues(HashSet<Object> values) {
        this.values = values;
        return this;
    }

    @Override
    public PictureFactory addValue(Object value) {
        this.values.add(value);
        return this;
    }

    @Override
    public PictureFactory addValues(Object[] values) {
        addValues(new HashSet<>(List.of(values)));
        return this;
    }

    @Override
    public PictureFactory addValues(HashSet<Object> values) {
        this.values.addAll(values);
        return this;
    }

    @Override
    public PictureFactory removeValue(Object value) {
        this.values.remove(value);
        return this;
    }

    @Override
    public PictureFactory removeValues(Object[] values) {
        removeValues(new HashSet<>(List.of(values)));
        return this;
    }

    @Override
    public PictureFactory removeValues(HashSet<Object> values) {
        for (Object value : values) {
            removeValue(value);
        }
        return this;
    }

    @Override
    public HashSet<Object> getValues() {
        return this.values;
    }

    @Override
    public PictureFactory setHadCache(boolean hadCache) {
        this.hadCache = hadCache;
        return this;
    }

    @Override
    public boolean isCache() {
        return cacheHadSave();
    }


    @Override
    public boolean cacheHadSave() {
        return this.hadCache;
    }

    @Override
    public PictureFactory cache() {
        return cacheAdd();
    }

    @Override
    public PictureFactory cacheAdd() {
        MyEyes.cache(this.getPicture());
        return this;
    }

    @Override
    public PictureFactory cacheRemove() {
        cache.removeValue(getUID());
        return this;
    }

    @Override
    public PictureFactory cacheSetPath(String path) {
        cache.setPath(path);
        return this;
    }

    @Override
    public PictureFactory cacheSetFileName(String fileName) {
        cache.setFileName(fileName);
        return this;
    }

    @Override
    public String cacheGetPath() {
        return getCache().getPath();
    }

    @Override
    public String cacheGetFileName() {
        return getCache().getFileName();
    }

    @Override
    public String cacheGetFilePath() {
        return getCache().getFilePath();
    }

    @Override
    public Cache getCache() {
        return this.cache;
    }

    @Override
    public PictureFactory setHadData(boolean hadData) {
        this.hadData = hadData;
        return this;
    }

    @Override
    public boolean isData() {
        return dataHadSave();
    }

    @Override
    public boolean dataHadSave() {
        return this.hadData;
    }

    @Override
    public PictureFactory study() {
        return data();
    }

    @Override
    public PictureFactory data() {
        return dataAdd();
    }

    @Override
    public PictureFactory dataAdd() {
        MyEyes.study(this.getPicture());
        return this;
    }

    @Override
    public PictureFactory dataRemove() {
        for (Object value : getValues()) {
            data.setFileName(value+APIData.DataTail);
            if (data.connect()) {
                Object temp = data.getDefaultValue(value, null);
                if (temp != null) {
                    temp = temp.toString().replace(Arrays.deepToString(getSimpleRGB()),"");
                    data.setValue(value, temp);
                }
            }
        }
        return this;
    }

    @Override
    @Deprecated
    public PictureFactory dataSetPath(String path) {
        data.setPath(path);
        return this;
    }

    @Override
    @Deprecated
    public PictureFactory dataSetFileName(String fileName) {
        data.setFileName(fileName);
        return this;
    }

    @Override
    public String dataGetPath() {
        return getData().getPath();
    }

    @Override
    public String dataGetFileName() {
        return getData().getFileName();
    }

    @Override
    public String dataGetFilePath() {
        return getData().getFilePath();
    }

    @Override
    public Data getData() {
        return this.data;
    }

    @Override
    public boolean isInPicture(Picture picture) {
        return MyEyes.isPictureInPicture(this.getPicture(), picture);
    }

    @Override
    public boolean isHasPicture(Picture picture) {
        return MyEyes.isPictureHasPicture(this.getPicture(), picture);
    }

    @Override
    public int[] getInPictureWhere(Picture picture) {
        return MyEyes.whereInPicture(this.getPicture(), picture);
    }

    @Override
    public int[] getWhereHasPicture(Picture picture) {
        return MyEyes.whereHasPicture(this.getPicture(), picture);
    }

    @Override
    public ArrayList<Boolean> isInPictures(Pictures pictures) {
        ArrayList<Boolean> arrayList = new ArrayList<>();
        for (Picture picture : pictures.toHashSet()) {
            arrayList.add(isInPicture(picture));
        }
        return arrayList;
    }

    @Override
    public ArrayList<Boolean> isHasPictures(Pictures pictures) {
        ArrayList<Boolean> arrayList = new ArrayList<>();
        for (Picture picture : pictures.toHashSet()) {
            arrayList.add(isHasPicture(picture));
        }
        return arrayList;
    }

    @Override
    public ArrayList<int[]> getInPicturesWhere(Pictures pictures) {
        ArrayList<int[]> arrayList = new ArrayList<>();
        for (Picture picture : pictures.toHashSet()) {
            arrayList.add(getInPictureWhere(picture));
        }
        return arrayList;
    }

    @Override
    public ArrayList<int[]> getWhereHasPictures(Pictures pictures) {
        ArrayList<int[]> arrayList = new ArrayList<>();
        for (Picture picture : pictures.toHashSet()) {
            arrayList.add(getWhereHasPicture(picture));
        }
        return arrayList;
    }

    @Override
    public Pictures toPictures() {
        return new Pictures().addPicture(this.getPicture());
    }

    @Override
    public Object guess() {
        return getGuess();
    }

    @Override
    public Object getGuess() {
        return MyEyes.guess(this.getPicture());
    }

    @Override
    public Picture getPicture() {
        return (Picture) this;
    }

    @Override
    public PictureFactory clone() {
        try {
            return (PictureFactory) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

}