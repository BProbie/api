package com.probie.Factory;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import com.probie.Database.APIData;
import com.probie.Interface.IDatabase;
import java.nio.charset.StandardCharsets;

public class DatabaseFactory implements IDatabase, Cloneable {

    private static DatabaseFactory databaseFactory = new DatabaseFactory();
    private Properties properties = new Properties();

    private boolean isConnection = false;

    private String path = getDefaultPath();
    private String fileName = APIData.MainName+APIData.MainTail;
    private String comment = "The Database Is A Cache Of MyEyes";

    public DatabaseFactory() {

    }

    @Override
    public boolean connect() {
        setConnection(false);
        if (createFile()) {
            try {
                properties.load(new InputStreamReader(new FileInputStream(getFilePath()), StandardCharsets.UTF_8));
                setConnection(true);
                return true;
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        } else {
            return noConnect("Can Not Create The File"+"("+"\""+getFilePath()+"\""+")");
        }
    }

    @Override
    @Deprecated
    public boolean noConnect() {
        return noConnect("Can Not Find The Connection");
    }

    @Override
    @Deprecated
    public boolean noConnect(Object error) {
        APIData.printError(error);
        return false;
    }

    @Override
    public boolean isConnect() {
        return hasCreateFile() && isConnection;
    }

    @Override
    public boolean close() {
        try {
            properties.load((InputStreamReader) null);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        return setConnection(true).getConnection();
    }

    @Override
    public boolean createFile() {
        File file = new File(getFilePath());
        if (!file.getParentFile().exists()) {
            if (file.getParentFile().mkdirs()) {
                if (!file.exists()) {
                    try {
                        return file.createNewFile();
                    } catch (IOException ioException) {
                        throw new RuntimeException(ioException);
                    }
                } else {
                    return true;
                }
            }
        } else {
            if (!file.exists()) {
                try {
                    return file.createNewFile();
                } catch (IOException ioException) {
                    throw new RuntimeException(ioException);
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    @Deprecated
    public boolean removeFile() {
        File file = new File(getFilePath());
        if (file.exists()) {
            properties.clear();
            return file.delete() && !file.exists();
        } else {
            return true;
        }
    }

    @Override
    @Deprecated
    public boolean clearFile() {
        return removeFile() && createFile();
    }

    public boolean hasCreateFile() {
        return new File(getFilePath()).exists();
    }

    public void save() {
        try {
            properties.store(new FileWriter(getFilePath()), comment);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    @Override
    public void setValue(Object key, Object value) {
        setValue(key, value, true);
    }

    @Override
    public void setValue(Object key, Object value, boolean autoSave) {
        if (isConnect()) {
            properties.setProperty(key.toString(), value.toString());
            if (autoSave) {
                save();
            }
        } else {
            noConnect();
        }
    }

    @Override
    public Object getValue(Object key) {
        if (isConnection) {
            return properties.getProperty(key.toString());
        } else {
            noConnect();
        }
        return null;
    }

    @Override
    public Object getDefaultValue(Object key, Object defaultValue) {
        if (isConnection) {
            if (defaultValue == null) {
                Object value = properties.getOrDefault(key, "null");
                if (value.equals("null")) {
                    return null;
                }
                return value;
            }
            return properties.getOrDefault(key, defaultValue);
        } else {
            noConnect();
        }
        return null;
    }

    @Override
    public void removeValue(Object key) {
        removeValue(key, true);
    }

    @Override
    public void removeValue(Object key, boolean autoSave) {
        if (isConnect()) {
            properties.remove(key);
            if (autoSave) {
                save();
            }
        } else {
            noConnect();
        }
    }

    @Override
    public void setValueMap(HashMap<Object, Object> valueMap) {
        setValueMap(valueMap, true);
    }

    @Override
    public void setValueMap(HashMap<Object, Object> valueMap, boolean autoSave) {
        if (isConnect()) {
            for (Object object : valueMap.entrySet()) {
                Object[] objects = object.toString().split("=");
                setValue(objects[0], objects[1], autoSave);
            }
        } else {
            noConnect();
        }
    }

    @Override
    public HashMap<Object, Object> getValueMap(Object[] keys) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        for (Object key : keys) {
            hashMap.put(key, getValue(key));
        }
        return hashMap;
    }

    @Override
    public HashMap<Object, Object> getValueMap(HashSet<Object> keys) {
        return getValueMap(keys.toArray());
    }

    @Override
    public HashMap<Object, Object> getDefaultValueMap(Object[] keys, Object defaultValue) {
        HashMap<Object, Object> hashMap = new HashMap<>();
        for (Object key : keys) {
            hashMap.put(key, getDefaultValue(key, defaultValue));
        }
        return hashMap;
    }

    @Override
    public HashMap<Object, Object> getDefaultValueMap(HashSet<Object> keys, Object defaultValue) {
        return getDefaultValueMap(keys.toArray(), defaultValue);
    }

    public void removeValueMap(Object[] keys) {
        removeValueMap(keys, true);
    }

    public void removeValueMap(Object[] keys, boolean autoSave) {
        for (Object key : keys) {
            removeValue(key, autoSave);
        }
    }

    @Override
    public void removeValueMap(HashSet<Object> keys) {
        removeValueMap(keys, true);
    }

    @Override
    public void removeValueMap(HashSet<Object> keys, boolean autoSave) {
        removeValueMap(keys.toArray(), autoSave);
    }

    @Override
    @Deprecated
    public DatabaseFactory setDatabase(DatabaseFactory databaseFactory) {
        DatabaseFactory.databaseFactory = databaseFactory;
        return this;
    }

    @Override
    public DatabaseFactory getDatabase() {
        return databaseFactory;
    }

    @Override
    @Deprecated
    public DatabaseFactory setProperties(Properties properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    @Deprecated
    public DatabaseFactory setConnection(boolean isConnection) {
        this.isConnection = isConnection;
        return this;
    }

    @Override
    public boolean getConnection() {
        return isConnection;
    }

    @Override
    public DatabaseFactory setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public DatabaseFactory appendPath(String appendPath) {
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
        setPath(getPath()+appendPath);
        return this;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getDefaultPath() {
        String defaultPath = APIData.HerePath;
        if (defaultPath.contains("\\")) {
            if (!defaultPath.endsWith("\\")) {
                defaultPath += "\\";
            }
        }
        else if (defaultPath.contains("/")) {
            if (!defaultPath.endsWith("/")) {
                defaultPath += "/";
            }
        }
        defaultPath += APIData.MainName;
        return defaultPath;
    }

    @Override
    public DatabaseFactory setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getFilePath() {
        if (!path.endsWith("\\") && !path.endsWith("/")) {
            if (path.contains("\\")) {
                return path+"\\"+fileName;
            }
            else if (path.contains("/")) {
                return path+"/"+fileName;
            }
        }
        return path+fileName;
    }

    @Override
    public DatabaseFactory setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public DatabaseFactory clone() {
        try {
            return (DatabaseFactory) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

}