package com.denisimusIT.mysqlandroid.controller;

import com.denisimusIT.mysqlandroid.controller.command.ClearTable;
import com.denisimusIT.mysqlandroid.controller.command.Command;
import com.denisimusIT.mysqlandroid.controller.command.ContentsOfTheTable;
import com.denisimusIT.mysqlandroid.controller.command.CreateDatabase;
import com.denisimusIT.mysqlandroid.controller.command.CreateNewTable;
import com.denisimusIT.mysqlandroid.controller.command.DisconnectOfDatabase;
import com.denisimusIT.mysqlandroid.controller.command.DropDatabase;
import com.denisimusIT.mysqlandroid.controller.command.DropTable;
import com.denisimusIT.mysqlandroid.controller.command.GifAccessToDatabase;
import com.denisimusIT.mysqlandroid.controller.command.Help;
import com.denisimusIT.mysqlandroid.controller.command.InsertALineIntoTheTable;
import com.denisimusIT.mysqlandroid.controller.command.ListOfDatabasesNames;
import com.denisimusIT.mysqlandroid.controller.command.ListOfTablesNames;
import com.denisimusIT.mysqlandroid.controller.command.UnsupportedCommand;
import com.denisimusIT.mysqlandroid.controller.command.connektToDatabase.ConnectToDatabase;
import com.denisimusIT.mysqlandroid.controller.command.connektToDatabase.IsConnect;
import com.denisimusIT.mysqlandroid.controller.command.exit.Exeption.ExitException;
import com.denisimusIT.mysqlandroid.controller.command.exit.Exit;
import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.view.View;




public class MainController {
    private static final String NEWLINE = System.lineSeparator();
    private final Command[] commands;
    private View view;
    private String connectFormat;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        Help help = new Help(view);
        connectFormat = new ConnectToDatabase(view, manager).format();

        this.commands = new Command[]{
                new Exit(view),
                help,
                new ConnectToDatabase(view, manager),
                new CreateDatabase(view, manager),
                new GifAccessToDatabase(view, manager),
                new ListOfDatabasesNames(view,manager),
                new DropDatabase(view, manager),
                new IsConnect(view, manager),
                new ListOfTablesNames(view, manager),
                new ContentsOfTheTable(view, manager),
                new CreateNewTable(view, manager),
                new InsertALineIntoTheTable(view, manager),
                new ClearTable(view, manager),
                new DropTable(view, manager),
                new DisconnectOfDatabase(view, manager),
                new UnsupportedCommand(view)
        };
        help.setCommands(commands);
    }

    public void run() {
        try {
            doWork();
        } catch (ExitException e) {
            // do nothing
        }

    }

    private void doWork() {
        view.write("Welcome to SQLCmd! =)");
        view.write("For connect to database to database , enter please a database name, user name and the password in a format: " +
                connectFormat + NEWLINE + "or help command for a help call");


        while (true) {
            String input = view.read();
            if (input == null) {
                new Exit(view).process(input);//nul if close application
            }
            try {
                for (Command command : commands) {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                }
            } catch (Exception e) {
                if (e instanceof ExitException) {
                    throw e;
                }
                //e.printStackTrace();
                printError(e);
                break;
            }
            view.write("enter please command or help command for a help call");
        }


    }


    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + cause.getMessage();
        }
        view.write("Failure! for the reason: " + message);
        view.write("Repeat attempt.");
    }

}
