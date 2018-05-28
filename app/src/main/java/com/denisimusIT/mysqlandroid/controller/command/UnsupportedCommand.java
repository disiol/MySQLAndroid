package com.denisimusIT.mysqlandroid.controller.command;

import com.denisimusIT.mysqlandroid.view.View;

public class UnsupportedCommand implements Command {
    private View view;

    public UnsupportedCommand(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write("Unsupported command:" + command);
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
