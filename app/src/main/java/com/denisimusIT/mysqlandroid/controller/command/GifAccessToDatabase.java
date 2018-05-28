package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

public class GifAccessToDatabase implements Command {
    private String newLine = System.lineSeparator();
    private View view;
    private DatabaseManager manager;

    public GifAccessToDatabase(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("giveAccess|");
    }

    @Override
    public void process(String command) {
        String[] dataCommand = command.split("\\|");
        int minQuantity = 3;
        if (dataCommand.length != minQuantity) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", minQuantity, dataCommand.length) + newLine +
                    "\texample: giveAccess|databaseName|userName");
        }
        String databaseName = dataCommand[1];
        String userName = dataCommand[2];
        manager.giveAccessUserToTheDatabase("\"" + databaseName + "\"", userName);
        //TODO exption Database didon crate

        view.write("Give access " + userName + " to database : " + databaseName.toString() + " is successfully");

    }

    @Override
    public String description() {
        return "Give access user to database";
    }

    @Override
    public String format() {
        return "giveAccess|databaseName|userName";
    }
}
