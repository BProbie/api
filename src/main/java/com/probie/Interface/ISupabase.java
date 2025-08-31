package com.probie.Interface;

import java.util.HashMap;
import java.util.HashSet;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import com.probie.Database.Supabase;

public interface ISupabase {

    boolean connect();
    boolean reConnect();
    boolean getIsConnection();

    boolean commit();
    boolean rollBack();
    Supabase setAutoCommit(boolean autoCommit);
    boolean getIsAutoCommit();

    PreparedStatement getPreparedStatement(String prepareCommand);
    PreparedStatement getPreparedStatement(String prepareCommand, Object... value1);
    PreparedStatement getPreparedStatement(String prepareCommand, HashSet<Object> values);
    PreparedStatement getPreparedStatement(String prepareCommand, ArrayList<Object> values);

    boolean runPrepareCommand(PreparedStatement preparedStatement);
    boolean runPrepareCommand(String prepareCommand);
    boolean runPrepareCommand(String prepareCommand, Object... values);
    boolean runPrepareCommand(String prepareCommand, HashSet<Object> values);
    boolean runPrepareCommand(String prepareCommand, ArrayList<Object> values);

    int runPrepareUpdate(PreparedStatement preparedStatement);
    int runPrepareUpdate(String prepareUpdate);
    int runPrepareUpdate(String prepareUpdate, Object... values);
    int runPrepareUpdate(String prepareUpdate, HashSet<Object> values);
    int runPrepareUpdate(String prepareUpdate, ArrayList<Object> values);

    ResultSet runPrepareQuery(PreparedStatement preparedStatement);
    ResultSet runPrepareQuery(String prepareQuery);
    ResultSet runPrepareQuery(String prepareQuery, Object... values);
    ResultSet runPrepareQuery(String prepareQuery, HashSet<Object> values);
    ResultSet runPrepareQuery(String prepareQuery, ArrayList<Object> values);

    Supabase useTable(String  table);
    boolean connectTable();
    boolean connectTable(String  table);
    boolean getIsTableExists();
    boolean getIsTableExists(Object table);
    boolean getIsTableExists(Object... tables);
    boolean createTable();
    boolean createTable(Object table);
    boolean createTable(Object... tables);
    boolean deleteTable();
    boolean deleteTable(Object table);
    boolean deleteTable(Object... tables);

    boolean getIsHeadExists(Object head);
    boolean getIsHeadExists(Object... heads);
    boolean createHead(Object head);
    boolean createHead(Object... heads);
    boolean deleteHead(Object head);
    boolean deleteHead(Object... heads);

    int insert(Object key, Object value);
    int insert(Object[] keys, Object value);
    int insert(Object[] keys, Object[] values);
    int insert(HashMap<Object, Object> map);

    Supabase toSupabase(Object object);
    Supabase getSupabase();

    Supabase setConnection(Connection connection);
    Connection getConnection();

    Supabase setUserName(String userName);
    String getUserName();
    Supabase setPassword(Object password);
    Object getPassword();

    Supabase setDriver(String driver);
    String getDriver();
    Supabase setSupabaseUrl(String supabaseUrl);
    String getSupabaseUrl();

    Supabase setCurrentDatabase(String currentDatabase);
    String getCurrentDatabase();
    Supabase setCurrentTable(String currentTable);
    String getCurrentTable();

    Supabase setSeatMark(Object seatMark);
    Object getSeatMark();

    Supabase setHeadMark(Object headMark);
    Object getHeadMark();

    Supabase setTableMark(Object tableMark);
    Object getTableMark();

    Supabase setCommandCheckTable(String commandCheckTable);
    String getCommandCheckTable();

    Supabase setCommandCreateTable(String commandCreateTable);
    String getCommandCreateTable();

    Supabase setCommandDeleteTable(String commandDeleteTable);
    String getCommandDeleteTable();

    Supabase setCommandCheckHead(String commandCheckHead);
    String getCommandCheckHead();

    Supabase setCommandCreateHead(String commandCreateHead);
    String getCommandCreateHead();

    Supabase setCommandDeleteHead(String commandDeleteHead);
    String getCommandDeleteHead();

    Supabase setCommandInsert(String commandInsert);
    String getCommandInsert();

}