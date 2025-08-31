package com.probie.Database;

import java.io.*;
import java.util.ArrayList;
import com.probie.Interface.IProperties;
import com.probie.Tools.SerializeBase64;
import java.nio.charset.StandardCharsets;

public class Properties extends SerializeBase64 implements IProperties, Closeable, Cloneable, Serializable {

    /**
     * 原生Properties
     * */
    private java.util.Properties properties = new java.util.Properties();
    private java.util.Properties tempProperties = new java.util.Properties();

    /**
     * 功能参数
     * */
    private boolean isConnection = false;
    private boolean isAutoCommit = false;

    /**
     * 信息参数
     * */
    private String path = System.getProperty("user.dir");
    private String fileName = "properties.properties";
    private String comment = "The Database Of Properties";

    /**
     * 规范化参数
     * */
    private String splitMark = ", ";

    /**
     * 连接到本地数据库
     * */
    @Override
    public boolean connect() {
        if (!getIsConnection()) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::close));
            return reConnect();
        }
        return true;
    }

    @Override
    public boolean reConnect() {
        if (getIsConnection()) {
            commit();
        }
        setIsConnection(false);
        if (createFile()) {
            try {
                getProperties().load(new InputStreamReader(new FileInputStream(getFilePath()), StandardCharsets.UTF_8));
                setIsConnection(true);
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
        return getIsConnection();
    }

    /**
     * 提交 回滚操作
     * */
    @Override
    public boolean commit() {
        try {
            if (connect()) {
                getProperties().store(new FileWriter(getFilePath()), getComment());
                setTempProperties(getProperties());
                return true;
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        return false;
    }

    @Override
    public boolean rollBack() {
        return setProperties(getTempProperties()).clearFile() && commit();
    }

    /**
     * 数据库操作 - setValue
     * */
    @Override
    public Properties setValue(Object key, Object value) {
        if (connect()) {
            getProperties().put(key, value);
            if (isAutoCommit) {
                commit();
            }
        }
        return this;
    }

    @Override
    public Properties setValue(Object[] keys, Object value) {
        for (int i = 0; i < keys.length; i++) {
            setValue(keys[i], value);
        }
        return this;
    }

    @Override
    public Properties setValue(Object[] keys, Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            setValue(keys[i], values[i]);
        }
        return this;
    }

    /**
     * 数据库操作 - addValue
     * */
    @Override
    public Properties addValue(Object key, Object value) {
        if (connect()) {
            Object fullValue = getDefaultValue(key, "");
            if (fullValue.equals("")) {
                return setValue(key, value);
            } else {
                return setValue(key, fullValue+getSplitMark()+value);
            }
        }
        return this;
    }

    @Override
    public Properties addValue(Object[] keys, Object value) {
        for (int i = 0; i < keys.length; i++) {
            addValue(keys[i], value);
        }
        return this;
    }

    @Override
    public Properties addValue(Object[] keys, Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            addValue(keys[i], values[i]);
        }
        return this;
    }

    /**
     * 数据库操作 - getValue
     * */
    @Override
    public Object getValue(Object key) {
        if (new File(getFilePath()).exists()) {
            if (connect()) {
                return getProperties().get(key);
            }
        }
        return null;
    }

    @Override
    public Object[] getValue(Object[] keys) {
        ArrayList<Object> values = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            values.add(getValue(keys));
        }
        return values.toArray();
    }

    /**
     * 数据库操作 - getDefaultValue
     * */
    @Override
    public Object getDefaultValue(Object key, Object defaultValue) {
        if (new File(getFilePath()).exists()) {
            if (connect()) {
                return getProperties().getOrDefault(key, defaultValue);
            }
        }
        return defaultValue;
    }

    @Override
    public Object[] getDefaultValue(Object[] keys, Object defaultValue) {
        ArrayList<Object> values = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            values.add(getDefaultValue(keys[i], defaultValue));
        }
        return values.toArray();
    }

    @Override
    public Object[] getDefaultValue(Object[] keys, Object[] defaultValues) {
        ArrayList<Object> values = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            values.add(getDefaultValue(keys[i], defaultValues[i]));
        }
        return values.toArray();
    }

    /**
     * 数据库操作 - removeKey
     * */
    @Override
    public Properties removeKey(Object key) {
        if (new File(getFilePath()).exists()) {
            if (connect()) {
                getProperties().remove(key);
                if (isAutoCommit) {
                    commit();
                }
            }
        }
        return this;
    }

    @Override
    public Properties removeKey(Object[] keys) {
        for (int i = 0; i < keys.length; i++) {
            removeKey(keys[i]);
        }
        return this;
    }

    /**
     * 数据库操作 - removeValue
     * */
    @Override
    public Properties removeValue(Object key, Object value) {
        if (new File(getFilePath()).exists()) {
            if (connect()) {
                Object fullValue = getDefaultValue(key, null);
                if (fullValue != null) {
                    return setValue(key, fullValue.toString().replace(getSplitMark()+value.toString(),"").replace(value.toString(),""));
                }
            }
        }
        return this;
    }

    @Override
    public Properties removeValue(Object[] keys, Object value) {
        for (int i = 0; i < keys.length; i++) {
            removeValue(keys[i], value);
        }
        return this;
    }

    @Override
    public Properties removeValue(Object[] keys, Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            removeValue(keys[i], values[i]);
        }
        return this;
    }

    /**
     * 数据库文件操作
     * */
    @Override
    public boolean createFile() {
        File file = new File(getFilePath());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
        return file.exists();
    }

    @Override
    public boolean deleteFile() {
        File file = new File(getFilePath());
        if (file.exists()) {
            file.delete();
        }
        return !file.exists();
    }

    @Override
    public boolean clearFile() {
        if (new File(getFilePath()).exists()) {
            return deleteFile() && createFile();
        }
        return true;
    }

    /**
     * 实例对象类型变换
     * */
    @Override
    public Properties toProperties() {
        return toProperties(this);
    }

    @Override
    public Properties toProperties(Object object) {
        return (Properties) object;
    }

    /**
     * 类参数的获取和设置
     * */
    @Override
    public Properties setProperties(java.util.Properties properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public java.util.Properties getProperties() {
        return properties;
    }

    @Override
    public Properties setTempProperties(java.util.Properties tempProperties) {
        this.tempProperties = tempProperties;
        return this;
    }

    @Override
    public java.util.Properties getTempProperties() {
        return tempProperties;
    }

    @Override
    public Properties clearProperties() {
        getProperties().clear();
        return this;
    }

    @Override
    public Properties clearTempProperties() {
        return null;
    }

    @Override
    public Properties setIsConnection(boolean isConnection) {
        this.isConnection = isConnection;
        return this;
    }

    @Override
    public boolean getIsConnection() {
        return isConnection  && new File(getFilePath()).exists();
    }

    @Override
    public Properties setIsAutoCommit(boolean isAutoCommit) {
        this.isAutoCommit = isAutoCommit;
        return this;
    }

    @Override
    public boolean getIsAutoCommit() {
        return isAutoCommit;
    }

    @Override
    public Properties setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Properties setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public Properties setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Properties setFilePath(String filePath) {
        File file = new File(filePath);
        setPath(file.getParent()).setFileName(file.getName());
        return this;
    }

    @Override
    public String getFilePath() {
        if (getPath().contains("\\")) {
            if (!getPath().endsWith("\\")) {
                setPath(getPath()+"\\");
            }
        }
        else if (getPath().contains("/")) {
            if (!getPath().endsWith("/")) {
                setPath(getPath()+"/");
            }
        }
        return getPath()+getFileName();
    }

    @Override
    public Properties setSplitMark(String  splitMark) {
        this.splitMark = splitMark;
        return this;
    }

    @Override
    public String getSplitMark() {
        return splitMark;
    }

    /**
     * 回收资源，同步数据
     * */
    @Override
    public void close() {
        commit();
    }

    /**
     * 克隆
     * */
    @Override
    public Properties clone() {
        try {
            return (Properties) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

}