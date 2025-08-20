package com.program.probie.ProgrameTool.Datasql;

import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;

public class Properties {

    private String path = System.getProperty("user.dir");
    private String fileName = "config.properties";
    private String comment = "This properties config file to store data";
    private java.util.Properties properties = new java.util.Properties();
    private boolean isConnection = false;

    public boolean connection() {
        isConnection = false;
        makeProperties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(getFilePath()),StandardCharsets.UTF_8));
            isConnection = true;
        } catch (IOException ioException) {
            noConnection(ioException);
        }
        return isConnection;
    }

    private void noConnection() {
        System.out.println("Tools.Properties.Error>"+" "+"Can Not Found Connection");
    }
    private void noConnection(Object reason) {
        System.out.println("Tools.Properties.Error>"+" "+"Can Not Found Connection");
        System.out.println(reason.toString());
    }

    public boolean save() {
        if (isConnection) {
            try {
                properties.store(new FileWriter(getFilePath()),comment);
                return true;
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else {noConnection();}
        return false;
    }

    public boolean addValue(String key,String value) {
        if (isConnection) {
            properties.setProperty(key,value);
            save();
            return true;
        } else {noConnection();}
        return false;
    }

    public boolean addValueMap(HashMap<String,String> hashMap) {
        if (isConnection) {
            for (Object value : hashMap.entrySet()) {
                String[] values = value.toString().split("=");
                addValue(values[0],values[1]);
            }
            return true;
        } else {noConnection();}
        return false;
    }

    public String getValue(String key) {
        if (isConnection) {
            return properties.getProperty(key);
        } else {noConnection();}
        return null;
    }

    public HashMap<String,String> getValueMap() {
        if (isConnection) {
            ArrayList<String> arrayList = new ArrayList<>();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),StandardCharsets.UTF_8));
                String reader;
                while ((reader = bufferedReader.readLine())!=null) arrayList.add(reader);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            HashMap<String,String> hashMap = new HashMap<>();
            for (Object reader : arrayList.toArray()) {
                if (!reader.toString().contains("#")) {
                    hashMap.put(reader.toString().split("=")[0],reader.toString().split("=")[1]);
                }
            }
            return hashMap;
        } else {noConnection();}
        return null;
    }

    public boolean deleteValue(Object key) {
        if (isConnection) {
            properties.remove(key);
            save();
            return true;
        } else {noConnection();}
        return false;
    }

    public boolean deleteValueMap(HashMap<Object,Object> hashMap) {
        if (isConnection) {
            for (Object value : hashMap.entrySet()) {
                Object[] values = value.toString().split("=");
                deleteValue(values[0]);
            }
            return true;
        } else {noConnection();}
        return false;
    }

    public boolean makeProperties() {
       if (!new File(path).exists()) new File(path).mkdirs();
       if (!new File(getFilePath()).exists()) {
           try {
               new File(getFilePath()).createNewFile();
               return true;
           } catch (IOException ioException) {
               ioException.printStackTrace();
           }
       }
       return false;
    }

    public boolean deleteProperties() {
        if (new File(getFilePath()).exists()) {
            new File(getFilePath()).delete();
            return true;
        }
        return false;
    }

    public boolean clearProperties() {
        if (new File(getFilePath()).exists()) {
            deleteProperties();
            makeProperties();
            return connection();
        }
        return false;
    }

    public Properties setPath(String path) {this.path = path; return this;}
    public String getPath() {return path;}

    public Properties setFileName(String fileName) {this.fileName = fileName; return this;}
    public String getFileName() {return fileName;}

    public Properties setComment(String comment) {this.comment = comment; return this;}
    public String getComment() {return comment;}

    public Properties setProperties(java.util.Properties properties) {this.properties = properties; return this;}
    public java.util.Properties getProperties() {return properties;}

    public String getFilePath() {
        if (path.endsWith("\\")) {
            return path+fileName;
        }
        return path+"\\"+fileName;
    }

}