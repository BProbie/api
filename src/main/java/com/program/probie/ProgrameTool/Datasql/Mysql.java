package com.program.probie.ProgrameTool.Datasql;

import java.sql.*;
import java.util.HashMap;

public class Mysql {

    private String driver = "com.mysql.jdbc.Driver";
    private String host = "localhost:3306";
    private String useDatabase = "mysql";
    private String user = "root";
    private String password = "";

    private boolean isConnection = false;
    private String database = "data";
    private String datatable = "map";

    private PreparedStatement preparedStatement;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public boolean connection() {
        isConnection = false;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection("jdbc:mysql://"+host+"/"+useDatabase+"?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true",user,password);
            isConnection = true;
        } catch (SQLException | ClassNotFoundException error) {
            noConnection(error);
        }
        return isConnection;
    }

    private void noConnection() {
        System.out.println("Tools.Mysql.Error>"+" "+"Can Not Found Connection");
    }
    private void noConnection(Object reason) {
        System.out.println("Tools.Mysql.Error>"+" "+"Can Not Found Connection");
        System.out.println(reason.toString());
    }

    public boolean runSafeCommand(String command) {
        if (isConnection) {
            try {
                preparedStatement = connection.prepareStatement(command);
                preparedStatement.execute();
                return true;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } else {noConnection();}
        return false;
    }

    public boolean runSafeCommand(String command,Object[] values) {
        if (isConnection) {
            try {
                preparedStatement = connection.prepareStatement(command);
                for (int i = 0; i < values.length; i++) {
                    preparedStatement.setString(i+1,String.valueOf(values[i]));
                }
                preparedStatement.execute();
                return true;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } else {noConnection();}
        return false;
    }

    public ResultSet select(String command) {
        if (isConnection) {
            try {
                preparedStatement = connection.prepareStatement(command);
                resultSet = preparedStatement.executeQuery();
                return resultSet;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } else {noConnection();}
        return null;
    }

    public void close() {
        try {
            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
       } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        System.gc();
    }

    public boolean addValue(String key,String value) {
        if (isConnection) {
            runSafeCommand("CREATE DATABASE IF NOT EXISTS `"+database+"`");
            runSafeCommand("CREATE TABLE IF NOT EXISTS `"+database+"`.`"+datatable+"` (`key` VARCHAR(100) NOT NULL UNIQUE COMMENT 'key',`value` VARCHAR(100) COMMENT 'value')ENGINE=INNODB DEFAULT CHARSET=utf8;");
            runSafeCommand("DELETE FROM `"+database+"`.`"+datatable+"` WHERE `key`='"+key+"';");
            runSafeCommand("INSERT INTO `"+database+"`.`"+datatable+"` (`key`,`value`) values ('"+key+"','"+value+"');");
            return true;
        } else {noConnection();}
        return false;
    }

    public void addValueMap(HashMap<String,String> hashMap) {
        if (isConnection) {
            for (Object map : hashMap.entrySet()) {
                String[] maps = map.toString().split("=");
                addValue(maps[0],maps[1]);
            }
        } else {noConnection();}
    }

    public String getValue(String key) {
        if (isConnection) {
            ResultSet resultSet = select("SELECT * FROM `"+database+"`.`"+datatable+"` WHERE `key`='"+key+"';");
            try {
                resultSet.next();
                return resultSet.getString("value");
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } else {noConnection();}
        return null;
    }

    public HashMap<String,String> getValueMap() {
        if (isConnection) {
            ResultSet resultSet = select("SELECT * FROM `"+database+"`.`"+datatable+"`");
            HashMap<String,String> hashMap = new HashMap<>();
            try {
                while (resultSet.next()) {
                    hashMap.put(resultSet.getString("key"),resultSet.getString("value"));
                }
                return hashMap;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        } else {noConnection();}
        return null;
    }

    public boolean deleteValue(String key) {
        if (isConnection) {
            runSafeCommand("DELETE FROM `"+database+"`.`"+datatable+"` WHERE `key`='"+key+"';");
            return true;
        } else {noConnection();}
        return false;
    }

    public void deleteValue(HashMap<String,String> hashMap) {
        if (isConnection) {
            for (Object map : hashMap.entrySet()) {
                String[] maps = map.toString().split("=");
                deleteValue(maps[0]);
            }
        } else {noConnection();}
    }

    public Mysql setDriver(String driver) {this.driver = driver; return this;}
    public String getDriver() {return driver;}

    public Mysql setHost(String host) {this.host = host; return this;}
    public String getHost() {return host;}

    public Mysql setUseDatabase(String useDatabase) {this.useDatabase = useDatabase; return this;}
    public String getUseDatabase() {return useDatabase;}

    public Mysql setUser(String user) {this.user = user; return this;}
    public String getUser() {return user;}

    public Mysql setPassword(String password) {this.password = password; return this;}
    public String getPassword() {return password;}

    public String getDatabase() {return database;}
    public Mysql setDatabase(String database) {this.database = database; return this;}

    public String getDatatable() {return datatable;}
    public Mysql setDatatable(String datatable) {this.datatable = datatable; return this;}

}