package com.denisimusIT.mysqlandroid.controller.command.connektToDatabase;

import com.denisimusIT.mysqlandroid.controller.command.Command;
import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;

public class IsConnect implements Command {
    private final View view;
    private final DatabaseManager manager;

    public IsConnect(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String command) {
        view.write(String.format("You cannot use a command '%s' be not connected by means of a command yet " +
                "connect|databaseName|userName|password", command));
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String format() {
        return null;
    }
}
