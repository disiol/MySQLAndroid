package com.denisimusIT.mysqlandroid.controller.command;
// Команда выводит в консоль список всех доступных команд•Формат: Help (без параметров)
//•Формат вывода: текст, описания команд с любым форматированием

import com.denisimusIT.mysqlandroid.view.View;

public class Help implements Command {
    private View view;
    private Command[] commands;
    private String newLine = System.lineSeparator();


    public Help(View view) {
        this.view = view;
    }


    @Override
    public boolean canProcess(String command) {
        String help = format();
        return command.equals(help);
    }


    @Override
    public String description() {
        return "for an output of this list to the screen";

    }

    @Override
    public String format() {
        return "help";
    }


    @Override
    public void process(String input) {
        view.write("The existing command: " + newLine);

        for (Command command : commands) {
            if (command.format() == null) {
                continue;
            }
            view.write("\t" + command.format());
            view.write("\t\t" + command.description() + newLine);
        }


    }

    public void setCommands(Command[] commands) {
        this.commands = commands;
    }
}
