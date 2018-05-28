package com.denisimusIT.mysqlandroid.controller.command;

public class UpdateColumVaulue implements Command{
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
// TODO•Команда обновит запись, установив значение column2 = value2, длякоторой соблюдается условие column1 = value1
//•Формат: ua.com.denisimusIT.SQLCmd.controller.command.update | tableName | column1 | value1 | column2 | value2
//•где: tableName - имя таблицы
//•column1 - имя столбца записи которое проверяется
//•value1 - значение которому должен соответствовать столбец column1 для обновляемой записи
//•column2 - имя обновляемого столбца записи
//•value2 - значение обновляемого столбца записи
//•columnN - имя n-го обновляемого столбца записи
//•valueN - значение n-го обновляемого столбца записи
//•Формат вывода: табличный, как при ua.com.denisimusIT.SQLCmd.controller.command.find со старыми значениями обновленных записей.
    }
