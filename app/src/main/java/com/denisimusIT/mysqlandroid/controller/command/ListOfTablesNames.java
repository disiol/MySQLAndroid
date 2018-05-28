package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

public class ListOfTablesNames implements Command {
    private static final String NEWLINE = System.lineSeparator();

    private final DatabaseManager manager;
    private View view;

    public ListOfTablesNames(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        String tables = format();
        return command.equals(tables);
    }

    @Override
    public void process(String command) {
        String tableNames = manager.getTableNames().toString();
        //TODO validations chek

        view.write("List of tablesNames: ");
        view.write(tableNames);

    }

    @Override
    public String format() {
        return "tables";
    }

    @Override
    public String description() {
        return "shows the list of tables";
    }
}
