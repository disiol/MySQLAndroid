package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.model.DataSet;
import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

public class InsertALineIntoTheTable implements Command {
    private String newLine = System.lineSeparator();
    private View view;
    private DatabaseManager manager;

    public InsertALineIntoTheTable(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Shall be even the number of parameters in a format " +
                    "'insert |tableName| column1 |value1| of column2 |value2|... |columnN| valueN'," +
                    "and you have entered: '%s'", command));
        }

        String tableName = data[1];

        DataSet dataSet = new DataSet();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];

            dataSet.put(columnName, value);
        }
        manager.insertData("\"" + tableName + "\"", dataSet);
        //TODO exption table didon crate

        view.write(String.format("The record %s was successfully created in the table: %s", dataSet, tableName));
    }

    @Override
    public String description() {
        return "Command for an insertion of one line in the given table " + newLine +
                "  \t• where: tableName - a table name" + newLine +
                "  \t• column1 - a name of the first column of record" + newLine +
                "  \t• value1 - value of the first column of record" + newLine +
                "  \t• column2 - a name of the second column of record" + newLine +
                "  \t• value2 - value of the second column of record" + newLine +
                "  \t• columnN - the record column name n-go" + newLine +
                "  \t• valueN - n-go value of a column of record";
    }

    @Override
    public String format() {
        return "insert|tableName|column1|value1|column2|value2| ... |columnN | valueN";
    }

}
