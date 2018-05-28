package com.denisimusIT.mysqlandroid.controller.command.connektToDatabase;

import com.denisimusIT.mysqlandroid.controller.command.Command;
import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;


public class ConnectToDatabase implements Command {
    private String newLine = System.lineSeparator();


    private final View view;
    private final DatabaseManager manager;

    public ConnectToDatabase(View view, DatabaseManager manager) {

        this.view = view;
        this.manager = manager;
    }


    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");

    }

    @Override
    public void process(String command) {


        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("The number of parameters partitioned by the character '|' " +
                    "is incorrect, it is expected %s, but is: %s", count(), data.length) + newLine +
                    String.format("\tTeam format %s, and you have entered: %s", format(), command));
        }
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];


        manager.connectToDatabase(databaseName, userName, password);
        //TODO exception  connect
        view.write("Opened database: " + "\"" + databaseName + "\"" + " successfully");


    }

    @Override
    public String format() {

        return "connect|databaseName|userName|password";
    }

    @Override
    public String description() {

        return "for connection to the database with which we will work";
    }


    private int count() {
        return format().split("\\|").length;
    }


}

