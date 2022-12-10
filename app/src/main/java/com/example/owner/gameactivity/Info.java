package com.example.owner.gameactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Info extends AppCompatActivity {
    SQLiteDatabase database;
    CustomerDatabaseHelper databaseHelper;
    String login_table = "MEMBER";
    String rank_table = "RANK";
    String databaseName = "blockblock";
    String Cid;
    String Cpass;
    String Cname;
    String Cscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try
        {
            if (database == null) {
                //  database = openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
                databaseHelper = new CustomerDatabaseHelper(getApplicationContext(), databaseName, null, 1);
                database = databaseHelper.getWritableDatabase();
                //Toast.makeText(getApplication(), "DB :" + databaseName + "이 생성되었습니다.", Toast.LENGTH_SHORT).show();
            } else if (database != null) {
                //Toast.makeText(getApplication(), "이미 디비열렸음", Toast.LENGTH_SHORT).show();
            }

        } catch (
                Exception e
        )

        {
            e.printStackTrace();
        }

        try {
            if (database != null) {
                database.execSQL("CREATE TABLE if not exists " + login_table + "(" +
                        "_id integer PRIMARY KEY autoincrement," +
                        "name text," +
                        "pass text," +
                        "passCheck text," +
                        "id text" +
                        ")");
                //Toast.makeText(getApplication(), "Table :" + login_tableName + "이 생성되었습니다.", Toast.LENGTH_SHORT).show();

                database.execSQL("CREATE TABLE if not exists " + rank_table + "(" +
                        "_id integer PRIMARY KEY autoincrement," +
                        "score integer" +
                        ")");
                //.makeText(getApplication(), "Table :" + rank_tableName + "이 생성되었습니다.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class CustomerDatabaseHelper extends SQLiteOpenHelper {
        CustomerDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onOpen(SQLiteDatabase database) {
            super.onOpen(database);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        }

    }



}