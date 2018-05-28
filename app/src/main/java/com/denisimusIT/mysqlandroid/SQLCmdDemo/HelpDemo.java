package com.denisimusIT.mysqlandroid.SQLCmdDemo;

import ua.com.denisimusIT.SQLCmd.controller.Main;


public class HelpDemo {
    private static final String NEWLINE = System.lineSeparator();

    private static ConfigurableInputStream in;
    public static void main(String [] args){
        System.out.println("HelpDemo" + NEWLINE);
        in = new ConfigurableInputStream();
        System.setIn(in);

        in.add("help");
        in.add("exit");
        Main.main(new String[0]);


    }



}
