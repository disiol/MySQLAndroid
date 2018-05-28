package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

public class DisconnectOfDatabase implements Command {
    private View view;
    private DatabaseManager manager;
    private String newLine = System.lineSeparator();


    public DisconnectOfDatabase(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("disconnect|");
    }

    @Override
    public void process(String command) {
        String[] dataCommand = command.split("\\|");
        int minQuantity = 2;
        if (dataCommand.length != minQuantity) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected  %s, but is: %s", minQuantity, dataCommand.length) + newLine +
                   String.format("\tTeam format %s, and you have entered: %s" , format(), command) );
        }
        String databaseName = dataCommand[1];
        manager.disconnectOfDatabase(databaseName);
        view.write(String.format("Disconnect of database %s is successfully" , databaseName));

        //TODO exception Database dit not crate

    }

    @Override
    public String description() {
        return "Disconnect of database";
    }

    @Override
    public String format() {
        return "disconnect|databaseName";
    }
}
