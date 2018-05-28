package com.denisimusIT.mysqlandroid.controller;

import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.model.MySQLDatabaseManager;
import com.denisimusIT.mysqlandroid.view.Console;
import com.denisimusIT.mysqlandroid.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager databaseManager = new MySQLDatabaseManager();
        MainController mainController = new MainController(view,databaseManager);
        mainController.run();//TODO сделать вывод в консоль имя текуйщей базы данных


    }
}
