package com.probie.Interface;

import com.probie.Database.Properties;

public interface IProperties {

    boolean connect();
    boolean reConnect();

    boolean commit();
    boolean rollBack();

    Properties setValue(Object key, Object value);
    Properties setValue(Object[] keys, Object value);
    Properties setValue(Object[] keys, Object[] values);

    Properties addValue(Object key, Object value);
    Properties addValue(Object[] keys, Object value);
    Properties addValue(Object[] keys, Object[] values);

    Object getValue(Object key);
    Object[] getValue(Object[] keys);

    Object getDefaultValue(Object key, Object defaultValue);
    Object[] getDefaultValue(Object[] keys, Object defaultValue);
    Object[] getDefaultValue(Object[] keys, Object[] defaultValues);

    Properties removeKey(Object key);
    Properties removeKey(Object[] keys);

    Properties removeValue(Object key, Object value);
    Properties removeValue(Object[] keys, Object value);
    Properties removeValue(Object[] keys, Object[] values);

    boolean createFile();
    boolean deleteFile();
    boolean clearFile();

    Properties toProperties();
    Properties toProperties(Object object);

    Properties setProperties(java.util.Properties properties);
    java.util.Properties getProperties();

    Properties setTempProperties(java.util.Properties tempProperties);
    java.util.Properties getTempProperties();

    Properties clearProperties();
    Properties clearTempProperties();

    Properties setIsConnection(boolean isConnection);
    boolean getIsConnection();

    Properties setIsAutoCommit(boolean isAutoCommit);
    boolean getIsAutoCommit();

    Properties setPath(String path);
    String getPath();

    Properties setFileName(String fileName);
    String getFileName();

    Properties setComment(String comment);
    String getComment();

    Properties setFilePath(String filePath);
    String getFilePath();

    Properties setSplitMark(String splitMark);
    String getSplitMark();

}