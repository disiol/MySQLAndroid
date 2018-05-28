package com.denisimusIT.mysqlandroid.testDatabaseManager;

import com.denisimusIT.mysqlandroid.model.DataSet;
import com.denisimusIT.mysqlandroid.model.MySQLDatabaseManager;

import org.junit.Assert;
import org.junit.Test;

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
    private static final String TEST_DATABASE_NAME = "sbjornal_test";


//    @BeforeClass
//    public static void setup() {
//
//        connectToDB();
//        MY_SQL_DATABASE_MANAGER.createDatabase(TEST_DATABASE_NAME);
//        connectToDB();
//        MY_SQL_DATABASE_MANAGER.giveAccessUserToTheDatabase(TEST_DATABASE_NAME , userName);
//        connectToTestDatabase(TEST_DATABASE_NAME, userName, password);
//        String columnsValues = "id int  NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";
//        MY_SQL_DATABASE_MANAGER.createATable(TEST_TABLE_NAME, columnsValues);
//    }


    private static void connectToDB() {
        MY_SQL_DATABASE_MANAGER.connectToDatabase(databaseName, userName, password);
    }

//    @Before
//    public void connectToTestDatabaseBefore() {
//        MY_SQL_DATABASE_MANAGER.connectToDatabase(TEST_DATABASE_NAME, userName, password);
//    }
//
//    public static void connectToTestDatabase(String testDatabaseName, String userName, String password) {
//        MY_SQL_DATABASE_MANAGER.connectToDatabase(testDatabaseName, userName, password); //TODO
//    }


    @Test
    public void TestGetTableColumns() {

        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";
        String tableNameTestColumns = "testtable2";
        MY_SQL_DATABASE_MANAGER.createATable(tableNameTestColumns, columnsValues);

        String actual = MY_SQL_DATABASE_MANAGER.getTableColumns(tableNameTestColumns).toString();
        assertEquals("GetTableColumns", "[id, name, password]", actual);
    }

    @Test
    public void testGetTableData() {
       connectToDB();
        tableName = "company";

        // when
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,password  TEXT  NOT NULL";
//
        MY_SQL_DATABASE_MANAGER.createATable(tableName, columnsValues);
        String expected = "[testTable, company]";
        //TODO разобратса
//        Object[] actual = MY_SQL_DATABASE_MANAGER.getTableNames().toArray();
//        assertEquals("сreateTableCompany", expected, Arrays.toString(actual));


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        MY_SQL_DATABASE_MANAGER.insertData(tableName, input);

        // then
        DataSet[] company = MY_SQL_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("length", 1, company.length);

        DataSet user = company[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Stiven, pass]", Arrays.toString(user.getValues()));
    }

    @Test
    public void testUpdateTableData() {

        // given
        MY_SQL_DATABASE_MANAGER.clearATable(TEST_TABLE_NAME);


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        MY_SQL_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input);

        // when
        DataSet newValue = new DataSet();
        newValue.put("password", "pass2");
        newValue.put("name", "Pup");
        MY_SQL_DATABASE_MANAGER.updateTableData(TEST_TABLE_NAME, 13, newValue);

        // then
        DataSet[] users = MY_SQL_DATABASE_MANAGER.getTableData(TEST_TABLE_NAME);
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Pup, pass2]", Arrays.toString(user.getValues()));

    }


    @Test
    public void clearATableTest() {
        //given
        tableName = "company3";
        List<String> listOfTablesBefore = MY_SQL_DATABASE_MANAGER.getTableNames();
        listOfTablesBefore.add(tableName);
        Collections.sort(listOfTablesBefore);


        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL,PASSWORD  TEXT  NOT NULL";

        MY_SQL_DATABASE_MANAGER.createATable(tableName, columnsValues);
        String expected = listOfTablesBefore.toString();
        List<String> actual = MY_SQL_DATABASE_MANAGER.getTableNames();
        Collections.sort(actual);

        assertEquals("clearTableCompany", expected, actual.toString());


        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        MY_SQL_DATABASE_MANAGER.insertData(tableName, input);

        // when
        DataSet[] company = MY_SQL_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("lengthBeforeClear", 1, company.length);

        DataSet user = company[0];
        assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, Stiven, pass]", Arrays.toString(user.getValues()));

        // then
        MY_SQL_DATABASE_MANAGER.clearATable(tableName);
        List<String> companyColumns = MY_SQL_DATABASE_MANAGER.getTableColumns(tableName);

        assertEquals("testClearATableNames", "[id, name, password]", companyColumns.toString());

        company = MY_SQL_DATABASE_MANAGER.getTableData(tableName);
        assertEquals("lengthAfterClear", 0, company.length);



    }


    @Test
    public void connectExceptions() throws Exception {
        //not the correct password

        // given
        String actualException = null;

        String database = "sqlCmd";
        String user = "postgres";
        String password = "";
        try {
            MY_SQL_DATABASE_MANAGER.connectToDatabase(database,
                    user, password);
        } catch (Exception e) {
            actualException = e.getCause().toString();
        }


        // when
        Boolean actual = MY_SQL_DATABASE_MANAGER.isConnected();

        assertFalse("connectToDatabase to data base", actual);

        // then

        String expected;
        expected = String.format("org.postgresql.util.PSQLException: FATAL: password authentication failed " +
                "for user \"%s\"", user);
        Assert.assertEquals("not the correct password", expected, actualException);

        //not the correct user


        //given

        database = "sqlCmd";
        user = "a";
        password = "1111";
        try {
            MY_SQL_DATABASE_MANAGER.connectToDatabase(database,
                    user, password);
        } catch (Exception e) {
            actualException = e.getCause().toString();
        }

        // then

        expected = String.format("org.postgresql.util.PSQLException: FATAL: password authentication failed " +
                "for user \"%s\"", user);
        Assert.assertEquals("not the correct password", expected, actualException);


        database = "sq";
        user = "postgres";
        password = "1111";
        try {
            MY_SQL_DATABASE_MANAGER.connectToDatabase(database,
                    user, password);
        } catch (Exception e) {
            actualException = e.getCause().toString();
        }

        // then

        expected = String.format("org.postgresql.util.PSQLException: FATAL: database \"%s\" does not exist", database);
        Assert.assertEquals("not the correct database", expected, actualException);


    }


    @Test
    public void getTableColumnTestWanRow() {


        // given
        MY_SQL_DATABASE_MANAGER.clearATable(TEST_TABLE_NAME);
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        MY_SQL_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input);

        // then
        //принимает данные для выдачи
        String dataGetId = "id";
        DataSet[] users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);


        DataSet user = users[0];
        assertEquals("[id]", Arrays.toString(user.getNames()));
        assertEquals("[13]", Arrays.toString(user.getValues()));


        dataGetId = "name";
        users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[name]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven]", Arrays.toString(user.getValues()));


        dataGetId = "password";
        users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[password]", Arrays.toString(user.getNames()));
        assertEquals("[pass]", Arrays.toString(user.getValues()));

        dataGetId = "id,password";
        users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[id, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, pass]", Arrays.toString(user.getValues()));

        dataGetId = "name,id,password";
        users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[name, id, password]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, 13, pass]", Arrays.toString(user.getValues()));


    }


    @Test
    public void GetTableColumnTestTooRow() {

        MY_SQL_DATABASE_MANAGER.clearATable(TEST_TABLE_NAME);
        // given
        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        MY_SQL_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input);

        DataSet input2 = new DataSet();
        input2.put("id", 14);
        input2.put("name", "Stiven2");
        input2.put("password", "pass2");
        MY_SQL_DATABASE_MANAGER.insertData(TEST_TABLE_NAME, input2);

        // then
        //принимает данные для выдачи
        String dataGetId = "id";
        DataSet[] users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);


        DataSet user = users[0];
        assertEquals("[id]", Arrays.toString(user.getNames()));
        assertEquals("[13]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[id]", Arrays.toString(user.getNames()));
        assertEquals("[14]", Arrays.toString(user.getValues()));


        dataGetId = "name";
        users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[name]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[name]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven2]", Arrays.toString(user.getValues()));


        dataGetId = "password";
        users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[password]", Arrays.toString(user.getNames()));
        assertEquals("[pass]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[password]", Arrays.toString(user.getNames()));
        assertEquals("[pass2]", Arrays.toString(user.getValues()));

        dataGetId = "id,password";
        users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);
        user = users[0];
        assertEquals("[id, password]", Arrays.toString(user.getNames()));
        assertEquals("[13, pass]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[id, password]", Arrays.toString(user.getNames()));
        assertEquals("[14, pass2]", Arrays.toString(user.getValues()));

        dataGetId = "name,id,password";
        users = MY_SQL_DATABASE_MANAGER.getTableColumn(TEST_TABLE_NAME, dataGetId);

        user = users[0];
        assertEquals("[name, id, password]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven, 13, pass]", Arrays.toString(user.getValues()));

        user = users[1];
        assertEquals("[name, id, password]", Arrays.toString(user.getNames()));
        assertEquals("[Stiven2, 14, pass2]", Arrays.toString(user.getValues()));


    }

    @Test
    public void createTableCompany() {

        // given
        String columnsValues = "id INT PRIMARY KEY NOT NULL, name TEXT NOT NULL, PASSWORD  TEXT  NOT NULL";

        //wen
        String tableName = "company2";
        MY_SQL_DATABASE_MANAGER.createATable(tableName, columnsValues);

        //then
        String expected_1 = "[id, name, password]";
        String actual_1 = MY_SQL_DATABASE_MANAGER.getTableColumns(tableName).toString();
        assertEquals("getTableColumns", expected_1, actual_1);




    }

//TODO
//    @Test
//    public void createDatabaseTest() {
//        System.setOut(new PrintStream(OUT_CONTENT));
//
//        //before
//        String dataBaseName = "testdatabase2";
//        List<String> dataBaseNames = MY_SQL_DATABASE_MANAGER.getDatabaseNames();
//        dataBaseNames.add(dataBaseName);
//        Collections.sort(dataBaseNames);
//        Object[] expected = dataBaseNames.toArray();
//
//
//        //then
//        MY_SQL_DATABASE_MANAGER.createDatabase(dataBaseName);
//        connectToTestDatabase(TEST_DATABASE_NAME, userName, password);
//        List<String> actualDatabaseNames = MY_SQL_DATABASE_MANAGER.getDatabaseNames();
//        Collections.sort(actualDatabaseNames);
//        Object[] actualDatabaseNamesSorted = actualDatabaseNames.toArray();
//        assertEquals("getDatabaseNames", Arrays.toString(expected), Arrays.toString(actualDatabaseNamesSorted));
//
//
//        //after
//        connectToTestDatabase(TEST_DATABASE_NAME, userName, password);
//        MY_SQL_DATABASE_MANAGER.dropDatabase(dataBaseName);
//
//
//    }


    @Test
    public void currentDatabaseTest() {

        String expected = "[" + TEST_DATABASE_NAME + "]";
        String actual = MY_SQL_DATABASE_MANAGER.currentDatabase().toString();

        assertEquals("currentDatabase", expected, actual);
    }


    @Test
    public void disconnectOfDatabaseTest() {
        System.setOut(new PrintStream(OUT_CONTENT));
        MY_SQL_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);

        boolean connected = MY_SQL_DATABASE_MANAGER.isConnected();
        assertFalse("disconnect", connected);


    }


    @Test
    public void dropTableCompany() {
        String tableName = "company2";
        //get tables names
        List<String> expectedTables = MY_SQL_DATABASE_MANAGER.getTableNames();
        MY_SQL_DATABASE_MANAGER.createATable(tableName, "");
        expectedTables.add(tableName);
        Collections.sort(expectedTables);

        List<String> actual = MY_SQL_DATABASE_MANAGER.getTableNames();
        Collections.sort(actual);

        assertEquals("сreateTableCompany", expectedTables.toString(), actual.toString());

        System.setOut(new PrintStream(OUT_CONTENT));


        //тест удаления таблицы
        MY_SQL_DATABASE_MANAGER.dropTable(tableName);
        String expected_1 = "Table " + tableName + " deleted in given database..." + NEW_LINE;
        String actual_1 = OUT_CONTENT.toString();

        assertEquals("dropTableCompanyMessage", expected_1, actual_1);
        expectedTables.remove(tableName);

        String expected_2 = expectedTables.toString();
        List<String> actual_2 = MY_SQL_DATABASE_MANAGER.getTableNames();
        Collections.sort(actual_2);
        assertEquals("dropTableCompany", expected_2, actual_2.toString());


    }


    @Test
    public void ShowDatabaseTest() {
        String databaseName1 = "test3";
        connectToDB();
        List<String> databaseNames = MY_SQL_DATABASE_MANAGER.getDatabaseNames();
        databaseNames.add(databaseName1);
        Collections.sort(databaseNames);
        connectToDB();
        MY_SQL_DATABASE_MANAGER.createDatabase(databaseName1);


        connectToDB();
        String expected = databaseNames.toString();
        List<String> actualDatabaseNames = MY_SQL_DATABASE_MANAGER.getDatabaseNames();
        Collections.sort(actualDatabaseNames);


        assertEquals("getDatabaseNames", expected, actualDatabaseNames.toString());

        connectToDB();
        MY_SQL_DATABASE_MANAGER.dropDatabase(databaseName1);

    }

//    @AfterClass
//    public static void dropDatabase() {
//        connectToDB();
//        MY_SQL_DATABASE_MANAGER.disconnectOfDatabase(TEST_DATABASE_NAME);
//        //DropDatabase
//        connectToDB();
//        //   MY_SQL_DATABASE_MANAGER.giveAccessUserToTheDatabase("\"" + TEST_DATABASE_NAME + "\"", userName);
//        connectToDB();
//        MY_SQL_DATABASE_MANAGER.dropDatabase(TEST_DATABASE_NAME);
//
//
//    }

}