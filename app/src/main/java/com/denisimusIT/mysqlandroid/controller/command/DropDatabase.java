package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

public class DropDatabase implements Command {
    private String newLine = System.lineSeparator();


    private View view;
    private DatabaseManager manager;


    public DropDatabase(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("dropDatabase|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", count(), data.length) + newLine +
                    String.format("\tTeam format %s, and you have entered: %s", format(),command));
        }
        String databaseName = data[1];

        manager.dropDatabase("\"" + databaseName + "\"");
        view.write("Database  " + databaseName + " deleted successfully");
        //TODO exception Database did not crate


    }

    @Override
    public String description() {
        return "Delete database";
    }

    @Override
    public String format() {
        return "dropDatabase|DatabaseName";
    }

    private int count() {
        return format().split("\\|").length;
    }
}
