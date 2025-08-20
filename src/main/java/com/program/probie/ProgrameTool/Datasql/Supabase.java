package com.program.probie.ProgrameTool.Datasql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Supabase {

    private String driver = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://db.mjrxntpgkjaswlplunxf.supabase.co:5432/postgres?user=postgres&password=[YOUR-PASSWORD]";

    private String user = "BProbie";
    private String password = "123456";

    private String database = "Database";
    private String table = "Table";

    private Connection connection;
    private boolean isConnection = false;

    public boolean connection() {
        connection = null;
        isConnection = false;
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            isConnection = true;
        } catch (ClassNotFoundException | SQLException error) {
            noConnection(error);
        }
        return isConnection && connection != null;
    }

    private void noConnection() {
        System.out.println("Tools.Supabase.Error>"+" "+"Can Not Found Connection");
    }
    private void noConnection(Object reason) {
        System.out.println("Tools.Supabase.Error>"+" "+"Can Not Found Connection");
        System.out.println(reason.toString());
    }

    public Supabase setDriver(Object driver) {
        this.driver = driver.toString();
        return this;
    }
    public String getDriver() {
        return driver;
    }

    public Supabase setUrl(Object url) {
        this.url = url.toString();
        return this;
    }
    public String getUrl() {
        return url;
    }

    public Supabase setUser(Object user) {
        this.user = user.toString();
        return this;
    }
    public String getUser() {
        return user;
    }

    public Supabase setPassword(Object password) {
        this.password = password.toString();
        return this;
    }
    public String getPassword() {
        return password;
    }

    public Supabase setDatabase(Object database) {
        this.database = database.toString();
        return this;
    }
    public String getDatabase() {
        return database;
    }

    public Supabase setTable(Object table) {
        this.table = table.toString();
        return this;
    }
    public String getTable() {
        return table;
    }

}