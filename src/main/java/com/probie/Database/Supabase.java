package com.probie.Database;

import java.sql.*;
import java.util.*;
import java.io.Closeable;
import java.io.Serializable;
import java.util.regex.Pattern;
import com.probie.Interface.ISupabase;
import com.probie.Tools.SerializeBase64;

public class Supabase extends SerializeBase64 implements ISupabase, Cloneable, Closeable, Serializable {

    /**
     * 快速上手Supabase的SQL语法!
     * 增 Insert: INSERT INTO table (key, key) VALUES (value, value)     INSERT INTO users (username, password) VALUES ("postgres", 123456)
     * 删 Delete: DELETE FROM table WHERE condition                      DELETE FROM users WHERE username="postgres"
     * 改 Update: UPDATE table SET key=value WHERE condition             UPDATE users SET password=123456 WHERE username="postgres"
     * 查 Select: SELECT key FROM table WHERE condition                  SELECT password FROM users WHERE username="postgres"

     * 其他语法!
     * 按key排序:      ORDER BY key    ORDER BY password
     * 限制num条数据:   LIMIT num      LIMIT 10
     * */

    /**
     * 资源实例对象
     * */
    private Connection connection;

    /**
     * 用户信息
     * */
    private String userName = "postgres";   //  postgres
    private Object password = "123456";     //  123456

    /**
     * JDBC网络请求
     * */
    private String driver = "org.postgresql.Driver";
    private String supabaseUrl = "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres?user="+getUserName()+".hgxarfmxttbwcfgcmxps&password="+getPassword();

    /**
     * 数据库参数
     * */
    private String currentDatabase = "publicdb";
    private String currentTable = "publictb";

    /**
     * 指令参数
     * */
    private Object seatMark = "?";
    private Object tableMark = "[table]";
    private Object headMark = "[head]";

    /**
     * 指令
     * */
    private String commandCheckTable = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name='"+getTableMark()+"' AND table_schema='public') AS table_exists;";
    private String commandCreateTable = "CREATE TABLE IF NOT EXISTS"+getTableMark()+" (id SERIAL PRIMARY KEY);";
    private String commandDeleteTable = "DROP TABLE IF EXISTS"+getTableMark();

    private String commandCheckHead = "SELECT EXISTS (SELECT column_name FROM information_schema.columns WHERE table_name ='"+getTableMark()+"' AND column_name = '"+getHeadMark()+"') AS column_exists;";
    private String commandCreateHead = "ALTER TABLE "+getTableMark()+" ADD COLUMN "+getHeadMark()+" VARCHAR(20)";
    private String commandDeleteHead = "ALTER TABLE "+getTableMark()+" DROP COLUMN "+getHeadMark();

    private String commandInsert = "INSERT INTO "+getTableMark()+" ("+getSeatMark()+") VALUES ("+getSeatMark()+");";

    /**
     * 类初始化
     * */
    public Supabase() {

    }

    /**
     * 连接数据库
     * */
    @Override
    public boolean connect() {
        if (!getIsConnection()) {
            Runtime.getRuntime().addShutdownHook(new Thread(this::close));
            return reConnect();
        }
        return getIsConnection();
    }

    @Override
    public boolean reConnect() {
        if (getIsConnection()) {
            commit();
        }
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException(classNotFoundException);
        }
        try {
            setConnection(DriverManager.getConnection(getSupabaseUrl()));
        } catch (SQLException sqlException) {
            try {
                setConnection(DriverManager.getConnection(getSupabaseUrl(), getUserName(), getPassword().toString()));
            } catch (SQLException sqlException1) {
                throw new RuntimeException(sqlException1);
            }
        }
        setAutoCommit(false);
        return getIsConnection();
    }

    @Override
    public boolean getIsConnection() {
        if (getConnection() != null) {
            try {
                return !getConnection().isClosed();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return false;
    }

    /**
     * 提交、回滚操作
     * */
    @Override
    public boolean commit() {
        if (connect()) {
            try {
                getConnection().commit();
                return true;
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return false;
    }

    @Override
    public boolean rollBack() {
        if (connect()) {
            try {
                getConnection().rollback();
                return true;
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return false;
    }

    @Override
    public Supabase setAutoCommit(boolean autoCommit) {
        if (connect()) {
            try {
                getConnection().setAutoCommit(autoCommit);
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return this;
    }

    @Override
    public boolean getIsAutoCommit() {
        if (connect()) {
            try {
                return getConnection().getAutoCommit();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return false;
    }

    /**
     * 获取PreparedStatement实例化对象
     * */
    @Override
    public PreparedStatement getPreparedStatement(String prepareCommand) {
        if (connect()) {
            try {
                return getConnection().prepareStatement(prepareCommand);
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return null;
    }

    @Override
    public PreparedStatement getPreparedStatement(String prepareCommand, Object... values) {
        if (connect()) {
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(prepareCommand);
                for (int i = 1; i <= values.length; i++) {
                    preparedStatement.setString(i, values[i].toString());
                }
                return preparedStatement;
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
        return null;
    }

    @Override
    public PreparedStatement getPreparedStatement(String prepareCommand, HashSet<Object> values) {
        return getPreparedStatement(prepareCommand, values.toArray());
    }

    @Override
    public PreparedStatement getPreparedStatement(String prepareCommand, ArrayList<Object> values) {
        return getPreparedStatement(prepareCommand, values.toArray());
    }

    /**
     * 运行操作指令 - execute
     * */
    @Override
    public boolean runPrepareCommand(PreparedStatement preparedStatement) {
        if (connect()) {
            if (connectTable()) {
                try {
                    if (!preparedStatement.isClosed()) {
                        boolean returnValue = preparedStatement.execute();
                        preparedStatement.close();
                        return returnValue;
                    }
                } catch (SQLException sqlException) {
                    throw new RuntimeException(sqlException);
                }
            }
        }
        return false;
    }

    @Override
    public boolean runPrepareCommand(String prepareCommand) {
        return runPrepareCommand(getPreparedStatement(prepareCommand));
    }

    @Override
    public boolean runPrepareCommand(String prepareCommand, Object... values) {
        return runPrepareCommand(getPreparedStatement(prepareCommand, values));
    }

    @Override
    public boolean runPrepareCommand(String prepareCommand, HashSet<Object> values) {
        return runPrepareCommand(getPreparedStatement(prepareCommand, values));
    }

    @Override
    public boolean runPrepareCommand(String prepareCommand, ArrayList<Object> values) {
        return runPrepareCommand(getPreparedStatement(prepareCommand, values));
    }

    /**
     * 运行操作指令 - update
     * */
    @Override
    public int runPrepareUpdate(PreparedStatement preparedStatement) {
        if (connect()) {
            if (connectTable()) {
                try {
                    if (!preparedStatement.isClosed()) {
                        int returnValue = preparedStatement.executeUpdate();
                        preparedStatement.close();
                        return returnValue;
                    }
                } catch (SQLException sqlException) {
                    throw new RuntimeException(sqlException);
                }
            }
        }
        return 0;
    }

    @Override
    public int runPrepareUpdate(String prepareUpdate) {
        return runPrepareUpdate(getPreparedStatement(prepareUpdate));
    }

    @Override
    public int runPrepareUpdate(String prepareUpdate, Object... values) {
        return runPrepareUpdate(getPreparedStatement(prepareUpdate, values));
    }

    @Override
    public int runPrepareUpdate(String prepareUpdate, HashSet<Object> values) {
        return runPrepareUpdate(getPreparedStatement(prepareUpdate, values));
    }

    @Override
    public int runPrepareUpdate(String prepareUpdate, ArrayList<Object> values) {
        return runPrepareUpdate(getPreparedStatement(prepareUpdate, values));
    }

    /**
     * 运行操作指令 - query
     * */
    @Override
    public ResultSet runPrepareQuery(PreparedStatement preparedStatement) {
        if (connect()) {
            if (connectTable()) {
                try {
                    if (!preparedStatement.isClosed()) {
                        ResultSet returnValue = preparedStatement.executeQuery();
                        preparedStatement.close();
                        return returnValue;
                    }
                } catch (SQLException sqlException) {
                    throw new RuntimeException(sqlException);
                }
            }
        }
        return null;
    }

    @Override
    public ResultSet runPrepareQuery(String prepareQuery) {
        return runPrepareQuery(getPreparedStatement(prepareQuery));
    }

    @Override
    public ResultSet runPrepareQuery(String prepareQuery, Object... values) {
        return runPrepareQuery(getPreparedStatement(prepareQuery, values));
    }

    @Override
    public ResultSet runPrepareQuery(String prepareQuery, HashSet<Object> values) {
        return runPrepareQuery(getPreparedStatement(prepareQuery, values));
    }

    @Override
    public ResultSet runPrepareQuery(String prepareQuery, ArrayList<Object> values) {
        return runPrepareQuery(getPreparedStatement(prepareQuery, values));
    }

    /**
     * 操作数据表
     * */
    @Override
    public Supabase useTable(String  table) {
        return setCurrentTable(table);
    }

    @Override
    public boolean connectTable() {
        return connectTable(getCurrentTable());
    }

    @Override
    public boolean connectTable(String table) {
        if (connect()) {
            useTable(table);
            createTable();
            return true;
        }
        return false;
    }

    @Override
    public boolean getIsTableExists() {
        return getIsTableExists(getCurrentTable());
    }

    @Override
    public boolean getIsTableExists(Object table) {
        if (getIsConnection()) {
            try (PreparedStatement prepareStatement = getConnection().prepareStatement(getCommandCheckTable().replaceFirst(Pattern.quote(getTableMark().toString()), table.toString()))) {
                ResultSet resultSet = prepareStatement.getResultSet();
                if (resultSet != null) {
                    if (resultSet.next()) {
                        boolean isTableExists = resultSet.getBoolean("table_exists");
                        resultSet.close();
                        return isTableExists;
                    }
                }
            } catch (SQLException exception) {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean getIsTableExists(Object... tables) {
        boolean isAllExists = true;
        for (Object table : tables) {
            isAllExists = isAllExists ^ getIsTableExists(tables);
        }
        return isAllExists;
    }

    @Override
    public boolean createTable() {
        return createTable(getCurrentTable());
    }

    @Override
    public boolean createTable(Object table) {
        if (connect()) {
            if (!getIsTableExists(table)) {
                try (PreparedStatement prepareStatement = getConnection().prepareStatement(getCommandCreateTable().replaceFirst(Pattern.quote(getTableMark().toString()), table.toString()))) {
                    return prepareStatement.execute();
                } catch (SQLException sqlException) {
                    throw new RuntimeException(sqlException);
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean createTable(Object... tables) {
        boolean isAllCreated = true;
        for (Object table : tables) {
            isAllCreated = isAllCreated ^ createTable(tables);
        }
        return isAllCreated;
    }

    @Override
    public boolean deleteTable() {
        return deleteTable(getCurrentTable());
    }

    @Override
    public boolean deleteTable(Object table) {
        if (connect()) {
            if (getIsTableExists()) {
                try (PreparedStatement preparedStatement = getConnection().prepareStatement(getCommandDeleteTable().replaceFirst(Pattern.quote(getTableMark().toString()), table.toString()))) {
                    return preparedStatement.execute();
                } catch (SQLException sqlException) {
                    throw new RuntimeException(sqlException);
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTable(Object... tables) {
        boolean isAllDeleted = true;
        for (Object table : tables) {
            isAllDeleted = isAllDeleted ^ deleteTable(tables);
        }
        return isAllDeleted;
    }

    @Override
    public boolean getIsHeadExists(Object head) {
        if (getIsConnection()) {
            if (getIsTableExists()) {
                try (PreparedStatement preparedStatement = getConnection().prepareStatement(getCommandCheckHead().replaceFirst(Pattern.quote(getTableMark().toString()), getCurrentTable()).replaceFirst(Pattern.quote(getHeadMark().toString()), head.toString()))) {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet != null) {
                        if (resultSet.next()) {
                            boolean isHeadExist = resultSet.getBoolean("column_exists");
                            resultSet.close();
                            return isHeadExist;
                        }
                    }
                } catch (SQLException e) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public boolean getIsHeadExists(Object... heads) {
        boolean isAllExists = true;
        for (Object head : heads) {
            isAllExists = isAllExists ^ getIsHeadExists(head);
        }
        return isAllExists;
    }

    @Override
    public boolean createHead(Object head) {
        if (connect()) {
            if (connectTable()) {
                if (!getIsHeadExists(head)) {
                    try (PreparedStatement preparedStatement = getConnection().prepareStatement(getCommandCreateHead().replaceFirst(Pattern.quote(getTableMark().toString()), getCurrentTable()).replaceFirst(Pattern.quote(getHeadMark().toString()), head.toString()))) {
                        return preparedStatement.execute();
                    } catch (SQLException sqlException) {
                        throw new RuntimeException(sqlException);
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean createHead(Object... heads) {
        boolean isAllCreated = true;
        for (Object head : heads) {
            isAllCreated = isAllCreated ^ createHead(head);
        }
        return isAllCreated;
    }

    @Override
    public boolean deleteHead(Object head) {
        if (connect()) {
            if (connectTable()) {
                if (getIsHeadExists(head)) {
                    try (PreparedStatement preparedStatement = getConnection().prepareStatement(getCommandDeleteHead().replaceFirst(Pattern.quote(getTableMark().toString()), getCurrentTable()).replaceFirst(Pattern.quote(getHeadMark().toString()), head.toString()))) {
                        return preparedStatement.execute();
                    } catch (SQLException sqlException) {
                        throw new RuntimeException(sqlException);
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteHead(Object... heads) {
        boolean isAllDeleted = true;
        for (Object head : heads) {
            isAllDeleted = isAllDeleted ^ deleteHead(head);
        }
        return isAllDeleted;
    }

    /**
     * 增删改查
     * */
    @Override
    public int insert(Object key, Object value) {
        return insert(new Object[]{key}, new Object[]{value});
    }

    @Override
    public int insert(Object[] keys, Object value) {
        Object[] values = new Object[keys.length];
        Arrays.fill(values, value);
        return insert(keys, values);
    }

    @Override
    public int insert(Object[] keys, Object[] values) {
        if (createHead(keys)) {
            String command = getCommandInsert();
            command = command.replaceFirst(Pattern.quote(getTableMark().toString()), getCurrentTable());
            command = command.replaceFirst(Pattern.quote(getSeatMark().toString()), Arrays.toString(keys).replace("[","").replace("]",""));
            command = command.replaceFirst(Pattern.quote(getSeatMark().toString()), Arrays.toString(values).replace("[","'").replace("]","'").replace(" ","'").replace(",","', "));
            return runPrepareUpdate(command);
        }
        return 0;
    }

    @Override
    public int insert(HashMap<Object, Object> map) {
        return insert(map.keySet(), map.values());
    }

    /**
     * 操作类方法
     * */
    @Override
    public Supabase toSupabase(Object object) {
        return (Supabase) object;
    }

    @Override
    public Supabase getSupabase() {
        return this;
    }

    /**
     * 类数据改查
     * */
    @Override
    public Supabase setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Supabase setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public Supabase setPassword(Object password) {
        this.password = password;
        return this;
    }

    @Override
    public Object getPassword() {
        return password;
    }

    @Override
    public Supabase setDriver(String driver) {
        this.driver = driver;
        return this;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public Supabase setSupabaseUrl(String supabaseUrl) {
        this.supabaseUrl = supabaseUrl;
        return this;
    }

    @Override
    public String getSupabaseUrl() {
        return supabaseUrl;
    }

    @Override
    public Supabase setCurrentDatabase(String currentDatabase) {
        this.currentDatabase = currentDatabase;
        return this;
    }

    @Override
    public String getCurrentDatabase() {
        return currentDatabase;
    }

    @Override
    public Supabase setCurrentTable(String table) {
        this.currentTable = currentTable;
        return this;
    }

    @Override
    public String getCurrentTable() {
        return currentTable;
    }

    @Override
    public Supabase setSeatMark(Object seatMark) {
        this.seatMark = seatMark;
        return this;
    }

    @Override
    public Object getSeatMark() {
        return seatMark;
    }

    @Override
    public Supabase setHeadMark(Object headMark) {
        this.headMark = headMark;
        return this;
    }

    @Override
    public Object getHeadMark() {
        return headMark;
    }

    @Override
    public Supabase setTableMark(Object tableMark) {
        this.tableMark = tableMark;
        return this;
    }

    @Override
    public Object getTableMark() {
        return tableMark;
    }

    @Override
    public Supabase setCommandCheckTable(String commandCheckTable) {
        this.commandCheckTable = commandCheckTable;
        return this;
    }

    @Override
    public String getCommandCheckTable() {
        return commandCheckTable;
    }

    @Override
    public Supabase setCommandCreateTable(String commandCreateTable) {
        this.commandCreateTable = commandCreateTable;
        return this;
    }

    @Override
    public String getCommandCreateTable() {
        return commandCreateTable;
    }

    @Override
    public Supabase setCommandDeleteTable(String commandDeleteTable) {
        this.commandDeleteTable = commandDeleteTable;
        return this;
    }

    @Override
    public String getCommandDeleteTable() {
        return commandDeleteTable;
    }

    @Override
    public Supabase setCommandCheckHead(String commandCheckHead) {
        this.commandCheckHead = commandCheckHead;
        return this;
    }

    @Override
    public String getCommandCheckHead() {
        return commandCheckHead;
    }

    @Override
    public Supabase setCommandCreateHead(String commandCreateHead) {
        this.commandCreateHead = commandCreateHead;
        return this;
    }

    @Override
    public String getCommandCreateHead() {
        return commandCreateHead;
    }

    @Override
    public Supabase setCommandDeleteHead(String commandDeleteHead) {
        this.commandDeleteHead = commandDeleteHead;
        return this;
    }

    @Override
    public String getCommandDeleteHead() {
        return commandDeleteHead;
    }

    @Override
    public Supabase setCommandInsert(String commandInsert) {
        this.commandInsert = commandInsert;
        return this;
    }

    @Override
    public String getCommandInsert() {
        return commandInsert;
    }

    /**
     * 克隆
     * */
    @Override
    public Supabase clone() {
        try {
            return toSupabase(super.clone());
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }

    /**
     * 资源清理
     * */
    @Override
    public void close() {
        if (getIsConnection()) {
            commit();
            try {
                getConnection().close();
            } catch (SQLException sqlException) {
                throw new RuntimeException(sqlException);
            }
        }
    }

}