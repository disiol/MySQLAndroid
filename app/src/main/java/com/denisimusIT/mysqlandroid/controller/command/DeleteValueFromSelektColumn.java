package com.denisimusIT.mysqlandroid.controller.command;

public class DeleteValueFromSelektColumn implements Command {
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
// TODO •Команда удаляет одну или несколько записей для которых
//    соблюдается условие column = value
//•Формат: ua.com.denisimusIT.SQLCmd.controller.command.delete | tableName | column | value
//•где: tableName - имя таблицы
//•Column - имя столбца записи которое проверяется
//•value - значение которому должен соответствовать столбец
//    column1 для удаляемой записи
//•Формат вывода: табличный, как при ua.com.denisimusIT.SQLCmd.controller.command.find со старыми значениями
//    удаляемых записей.
    }
