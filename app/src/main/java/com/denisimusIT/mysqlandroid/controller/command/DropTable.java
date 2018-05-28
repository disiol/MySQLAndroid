package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

public class DropTable implements Command {
    private String newLine = System.lineSeparator();

    private final View view;
    private final DatabaseManager databaseManager;

    public DropTable(View view, DatabaseManager databaseManager) {
        this.view = view;
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("dropTable|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", count(), data.length) + newLine +
                    String.format("\tTeam format %s, and you have entered: %s", format(), command));
        }
        String tableName = data[1];

        databaseManager.dropTable("\"" + tableName + "\"");
        view.write("Table  " + "\"" + tableName + "\"" + " deleted successfully");
        //TODO exception Database did not crate


    }

    @Override
    public String description() {
        return "Delete table";
    }

    @Override
    public String format() {
        return "dropTable|tableName";
    }

    private int count() {
        return format().split("\\|").length;
    }


}
