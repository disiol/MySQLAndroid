package com.denisimusIT.mysqlandroid.model;


import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class MySQLDatabaseManager implements DatabaseManager {

    private Connection connection;
    private final static String NEW_LINE = System.lineSeparator();


    @Override
    public void connectToDatabase(String databaseName, String userName, String password) {

        if (isConnected() == true) {
            connection = null;
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("!! " + e.getMessage());
            throw new RuntimeException("Please add jdbc jar to project.", e);

        }
        try {
            String hostnamePort = "sbjornal.mysql.ukraine.com.ua";
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + hostnamePort + "/" + databaseName, userName, password);
        } catch (SQLException e) {
            connection = null;

            Log.d("MyLog", "connectToDatabase: " + e.getMessage());

            throw new RuntimeException(
            String.format("Cant get connection for model:%s user:%s", databaseName, userName),e);
        }

    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }


    @Override
    public void clearATable(final String tableName) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();  //TODO собщение таблица не найдена
        }
    }

    @Override
    public void createATable(final String tableName, String columnsValues) {

        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS  " + tableName +
                    "(" + columnsValues + ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace(); //TODO собщение об ошибке
        }


    }


    @Override
    public DataSet[] getTableData(String tableName) {
        int size = getSize(tableName);
        ResultSet rs;
        ResultSetMetaData rsmd;
        DataSet[] result = new DataSet[0];
        int index;


        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery("SELECT * FROM " + tableName);

            rsmd = rs.getMetaData();
            result = new DataSet[size];
            index = 0;
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); //TODO собщение об ошибке
        }


        return result;
    }

    @Override
    public DataSet[] getTableColumn(String tableName, final String columnsName) {

        int size = getSize(tableName);
        ResultSet rs;
        ResultSetMetaData rsmd;
        DataSet[] result = new DataSet[0];
        int index;
        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery("SELECT " + columnsName + " FROM " + tableName);
            rsmd = rs.getMetaData();
            result = new DataSet[size];
            index = 0;

            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); //TODO собщение об ошибке
        }


        return result;


    }


    private int getSize(String tableName) {
        int size = 0;

        ResultSet rsCount;
        try (Statement stmt = connection.createStatement()) {
            rsCount = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
            rsCount.next();
            size = rsCount.getInt(1);
            rsCount.close();

        } catch (SQLException e) {
            e.printStackTrace(); //TODO собщение об ошибке
        }


        return size;
    }

    @Override
    public List<String> getTableNames() {
        //TODO добавить выбор нужной схемы
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' " +
                    "AND table_type='BASE TABLE'");
            List<String> tables = new LinkedList<>();
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;   //TODO собщение об ошибке
        }
    }


    @Override
    public void insertData(String tableName, DataSet input) {
        // берет значения из  DataSet
        // вставлает их в таблицу

        try (Statement stmt = connection.createStatement()) {

            String columnName = getNameFormatted(input, "%s,");
            String values = getValuesFormatted(input, "'%s',");

            String sql = "INSERT INTO " + tableName + "(" + columnName + ")" + "VALUES (" + values + ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace(); //TODO собщение об ошибке
        }


    }


    @Override
    public void updateTableData(final String tableName, int id, DataSet newValue) {
        //TODO добавить выбор схемы и колонки

        String tableNames = getNameFormatted(newValue, "%s = ?,");

        String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : newValue.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setObject(index, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); //TODO собщение об ошибке
        }
    }


    @Override
    public void dropTable(final String tableName) {
        try (Statement stmt = connection.createStatement();) {
            stmt.executeUpdate("DROP TABLE " + tableName + " ");
            System.out.println("Table " + tableName + " deleted in given database...");
        } catch (SQLException e) {
            e.printStackTrace(); //TODO собщение об ошибке
        }

    }


    private String getValuesFormatted(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    private String getNameFormatted(DataSet newValue, String format) {
        String string = "";
        for (String name : newValue.getNames()) {
            string += String.format(format, name);
        }
        string = string.substring(0, string.length() - 1);
        return string;
    }


    @Override
    public void createDatabase(final String databaseName) {

        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE DATABASE " + databaseName;
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            connection = null;
            se.printStackTrace();  //TODO собщение об ошибке
        }

    }


    @Override
    public List<String> getDatabaseNames() {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT datname FROM pg_database");
            List<String> tables = new LinkedList<>();
            while (rs.next()) {
                tables.add(rs.getString("datname"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void dropDatabase(final String databaseName) {

        try (Statement stmt = connection.createStatement()) {
            String sql = "DROP DATABASE " + databaseName;
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            connection = null;
            se.printStackTrace(); //TODO собщение об ошибке
        }

    }


    @Override
    public void disconnectOfDatabase(String databaseName) {

        try (Statement stmt = connection.createStatement()) {

            String sql = "SELECT pg_terminate_backend(pg_stat_activity.pid)" + NEW_LINE +
                    "FROM pg_stat_activity" + NEW_LINE +
                    "WHERE pg_stat_activity.datname = " + "'" + databaseName + "'" + NEW_LINE +
                    "  AND pid <> pg_backend_pid();" + NEW_LINE;
            stmt.execute(sql);
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace(); //TODO собщение об ошибке
        }


    }

    @Override
    public List<String> currentDatabase() {

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT current_database();");) {

            List<String> databaseName = new LinkedList<>();
            while (rs.next()) {
                databaseName.add(rs.getString("current_database"));
            }
            return databaseName;
        } catch (SQLException e) {
            e.printStackTrace(); //TODO собщение об ошибке
        }
        return null;
    }


    @Override
    public void giveAccessUserToTheDatabase(String databaseName, String userName) {
        try (Statement stmt = connection.createStatement()) {
            String sql = "GRANT ALL ON DATABASE " + databaseName + " TO  " + userName;
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            connection = null;
            se.printStackTrace();  //TODO собщение об ошибке
        }
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE table_schema='public' " +
                     "AND table_name='" + tableName + "'")) {

            List<String> tables = new LinkedList<>();
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }


}