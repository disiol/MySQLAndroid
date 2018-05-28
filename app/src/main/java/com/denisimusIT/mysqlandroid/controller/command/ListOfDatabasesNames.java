package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

import java.util.List;

public class ListOfDatabasesNames implements Command {
    private final View view;
    private final DatabaseManager manager;

    public ListOfDatabasesNames(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("databases");
    }

    @Override
    public void process(String command) {
        List<String> databaseNames = manager.getDatabaseNames();
        view.write(String.format("List of databases : %s", databaseNames));
    }

    @Override
    public String description() {
        return "To display the list of databases";
    }

    @Override
    public String format() {
        return "databases";
    }



}
