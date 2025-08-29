package com.probie.Interface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import com.probie.Factory.DatabaseFactory;

public interface IDatabase {

    boolean connect();
    boolean noConnect();
    boolean noConnect(Object error);
    boolean isConnect();
    boolean close();

    boolean createFile();
    boolean removeFile();
    boolean clearFile();
    boolean hasCreateFile();

    void setValue(Object key, Object value);
    void setValue(Object key, Object value, boolean autoSave);
    Object getValue(Object key);
    Object getDefaultValue(Object key, Object defaultValue);
    void removeValue(Object key);
    void removeValue(Object key, boolean autoSave);

    void setValueMap(HashMap<Object, Object> valueMap);
    void setValueMap(HashMap<Object, Object> valueMap, boolean autoSave);
    HashMap<Object, Object> getValueMap(Object[] keys);
    HashMap<Object, Object> getValueMap(HashSet<Object> keys);
    HashMap<Object, Object> getDefaultValueMap(Object[] keys, Object defaultValue);
    HashMap<Object, Object> getDefaultValueMap(HashSet<Object> keys, Object defaultValue);

    void removeValueMap(Object[] keys);
    void removeValueMap(Object[] keys, boolean autoSave);
    void removeValueMap(HashSet<Object> keys);
    void removeValueMap(HashSet<Object> keys, boolean autoSave);

    DatabaseFactory setDatabase(DatabaseFactory databaseFactory);
    DatabaseFactory getDatabase();

    DatabaseFactory setProperties(Properties properties);
    Properties getProperties();

    DatabaseFactory setConnection(boolean isConnection);
    boolean getConnection();

    DatabaseFactory setPath(String path);
    DatabaseFactory appendPath(String appendPath);
    String getPath();
    String getDefaultPath();

    DatabaseFactory setFileName(String fileName);
    String getFileName();

    String getFilePath();

    DatabaseFactory setComment(String comment);
    String getComment();

}