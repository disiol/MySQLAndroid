package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

public class CreateDatabase implements Command {

    private String newLine = System.lineSeparator();


    private View view;
    private DatabaseManager manager;

    public CreateDatabase(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createDatabase" +"|");
    }

    @Override
    public void process(String command) {

        String[] data = command.split("\\|");
        if (data.length != count()) {
            throw new IllegalArgumentException(String.format("Team format %s, and you have entered: %s",
                                                    format().toString(), command.toString()));
        }
        String databaseName = data[1];


        manager.createDatabase("\"" + databaseName + "\"" );
        //TODO exehen database didtn crate
        view.write("The database: " + databaseName  + " successfully");


    }

    @Override
    public String description() {
        return "created database";
    }

    @Override
    public String format() {
        return "createDatabase|DatabaseName";
    }

    private int count() {
        return format().split("\\|").length;
    }

}
