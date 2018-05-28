package com.denisimusIT.mysqlandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.denisimusIT.mysqlandroid.model.DataSet;
import com.denisimusIT.mysqlandroid.model.DatabaseManager;
import com.denisimusIT.mysqlandroid.model.MySQLDatabaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Runnable {
    Button btnAdd, btnRead, btnClear;
    TextView textViewContentsTable;
    EditText etName, etEmail;


    private String separator = "•+--------------------------------------------------";
    private String beginSymbol = "•+ ";

    DatabaseManager databaseManager = new MySQLDatabaseManager();
    private static final String databaseName = "sbjornal_test";
    private static final String userName = "sbjornal_test";
    private static final String password = "henpd4v3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        textViewContentsTable = (TextView) findViewById(R.id.textViewContentsTable);
       // etName = (EditText) findViewById(R.id.etName);


    }

    public void onClick(View view) {


        while (true) {
            try {
                connectToDatabase(databaseManager,databaseName,userName,password);
                printTableData(databaseManager, "company");
            } catch (Exception e) {
                textViewContentsTable.setText(e.getMessage().toString()+ " \n" + e.toString()  + " \n" + e.getCause().toString());
                Log.d("MyLog",e.getMessage().toString());
                e.printStackTrace();
            }
        }

    }


    public void connectToDatabase(DatabaseManager manager, String databaseName, String userName, String password) {


        manager.connectToDatabase(databaseName, userName, password);
        //TODO exception  connect
        textViewContentsTable.append("Opened database: " + databaseName + " successfully");

    }

    public void printTableData(DatabaseManager manager, String tableName) {

        DataSet[] tableColumns = manager.getTableData(tableName);
        printHeader(tableColumns);

        DataSet[] tableData = manager.getTableData(tableName);
        printTable(tableData);

    }

    private void printHeader(DataSet[] tableColumns) {

        for (DataSet rows : tableColumns) {
            printColum(rows);
        }

    }

    private void printTable(DataSet[] data) {

        for (DataSet rows : data) {
            printRow(rows);
        }

    }

    private void printRow(DataSet data) {
        String result = beginSymbol;
        Object[] names = data.getValues();

        for (Object name : names) {
            result += name + " + ";
        }
        textViewContentsTable.append(result);
        textViewContentsTable.append(separator);

    }

    private void printColum(DataSet data) {
        String result = beginSymbol;
        Object[] names = data.getNames();

        for (Object name : names) {
            result += name + " + ";
        }
        textViewContentsTable.append(separator);
        textViewContentsTable.append(result);
        textViewContentsTable.append(separator);

    }

    @Override
    public void run() {
        connectToDatabase(databaseManager, databaseName, userName, password);
    }
}
