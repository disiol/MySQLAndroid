package com.denisimusIT.mysqlandroid.controller;

import com.denisimusIT.mysqlandroid.model.DataSet;
import com.denisimusIT.mysqlandroid.model.MySQLDatabaseManager;
import com.denisimusIT.mysqlandroid.view.Console;
import com.denisimusIT.mysqlandroid.view.View;


import java.util.List;
import java.util.zip.InflaterOutputStream;

public class ContentsOfTheTable {
    private String newLine = "\n";
    private Console console = new Console();

    private final MySQLDatabaseManager manager = new MySQLDatabaseManager();
    private String separator = "•+--------------------------------------------------";
    private String beginSymbol = "•+ ";


    public void process(String command) {
        String[] dataCommand = command.split("\\|");
        int count = 2;
        if (dataCommand.length != count) {
            throw new IllegalArgumentException(String.format("Team format find|tableName, and you have entered: %s",
                    count, dataCommand.length) + newLine +
                    String.format("Team format find|tableName, and you have entered: %s", command));
        }
        String tableName = dataCommand[1];

        //TODO exehen tabel didon crate

        List<String> tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);

        DataSet[] tableData = manager.getTableData("\"" + tableName + "\"");
        printTable(tableData);

    }



    private void printHeader(List<String> tableColumns) {
        String result = beginSymbol;
        for (String name : tableColumns) {

            result += name + " + ";

        }

        console.write(separator);
        console.write(result);
        console.write(separator);


    }

    private void printTable(DataSet[] data) {

        for (DataSet rows : data) {
            printRow(rows);
        }

    }

    private void printRow(DataSet data) {
        String result = beginSymbol;
        Object[] names = data.getValues();

        for (Object name : names) {
            result += name + " + ";
        }
        console.write(result);
        console.write(separator);

    }

}
