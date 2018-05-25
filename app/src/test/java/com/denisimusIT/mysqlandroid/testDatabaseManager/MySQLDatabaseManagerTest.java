package com.denisimusIT.mysqlandroid.testDatabaseManager;

import com.denisimusIT.mysqlandroid.model.DataSet;
import com.denisimusIT.mysqlandroid.model.MySQLDatabaseManager;

import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class MySQLDatabaseManagerTest {

    private String tableName;
    private ByteArrayOutputStream OUT_CONTENT = new ByteArrayOutputStream();
    private final static String NEW_LINE = System.lineSeparator();


    private static final MySQLDatabaseManager MY_SQL_DATABASE_MANAGER = new MySQLDatabaseManager();
    private static final String databaseName = "sbjornal_test";
    private static final String userName = "sbjornal_test";
    private static final String password = "henpd4v3";

    private static final String TEST_TABLE_NAME = "testTable";
    private static final String TEST_DATABASE_NAME = "testDatabase2";


    @Before
    public void connectToTestDatabase() {
        MY_SQL_DATABASE_MANAGER.connectToDatabase(databaseName, userName, password); //TODO
    }

    @Test
    public void testGetTableData() {

        tableName = "company";

        // when
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";

        MY_SQL_DATABASE_MANAGER.createATable(tableName, columnsValues);
        String expected = "[testTable, company]";
        Object[] actual = MY_SQL_DATABASE_MANAGER.getTableNames().toArray();
      //  assertEquals("сreateTableCompany", expected, Arrays.toString(actual));


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        MY_SQL_DATABASE_MANAGER.insertData(tableName, input);

        // then
        DataSet[] company = MY_SQL_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("length", 1, company.length);

        DataSet user = company[0];


        assertEquals("[id, name, PASSWORD]", Arrays.toString(user.getNames()));
        assertEquals("[13, Stiven, pass]", Arrays.toString(user.getValues()));
    }

}