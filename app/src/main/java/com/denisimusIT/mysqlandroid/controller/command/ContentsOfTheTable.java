package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.model.DataSet;
import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

public class ContentsOfTheTable implements Command {
    private String newLine = System.lineSeparator();

    private final View view;
    private final DatabaseManager manager;
    private String separator = "•+--------------------------------------------------";
    private String beginSymbol = "•+ ";

    public ContentsOfTheTable(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
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

        DataSet[] tableColumns = manager.getTableData(tableName);
        printHeader(tableColumns);

        DataSet[] tableData = manager.getTableData(tableName);
        printTable(tableData);

    }

    @Override
    public String description() {
        return "for receiving contents of the table tableName";

    }

    @Override
    public String format() {
        return "find|tableName";
    }


    private void printHeader(DataSet[] tableColumns) {

        for (DataSet rows : tableColumns) {
            printColum(rows);
        }

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
        view.write(result);
        view.write(separator);

    }

    private void printColum(DataSet data) {
        String result = beginSymbol;
        Object[] names = data.getNames();

        for (Object name : names) {
            result += name + " + ";
        }
        view.write(separator);
        view.write(result);
        view.write(separator);

    }

}
