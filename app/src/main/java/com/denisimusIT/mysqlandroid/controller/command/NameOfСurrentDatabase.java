package com.denisimusIT.mysqlandroid.controller.command;

public class NameOfСurrentDatabase implements Command {
    @Override
    public boolean canProcess(String command) {
        return false;
    }

    @Override
    public void process(String command) {

    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String format() {
        return null;
    }
    //TODO
    //current_database
    //  ------------------
    //   [postgres]

}
