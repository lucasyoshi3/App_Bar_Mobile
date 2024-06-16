package com.fateczl.bar_boteco_app_android.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDao extends SQLiteOpenHelper {
    private static final String DATABASE = "BAR.DB";
    private static final int DATABASEVER = 1;

    private static final String CREATE_TABLE_CERVEJA=
            "CREATE TABLE cerveja (" +
                    "    id INT NOT NULL PRIMARY KEY," +
                    "    nome VARCHAR(100) NOT NULL," +
                    "    preco INTEGER NOT NULL"+
                    ");";

    private static final String CREATE_TABLE_DRINK=
            "CREATE TABLE drink (" +
                    "    id INT NOT NULL PRIMARY KEY," +
                    "    nome VARCHAR(100) NOT NULL," +
                    "    preco INTEGER NOT NULL"+
                    ");";

    private static final String CREATE_TABLE_REFRIGERANTE=
            "CREATE TABLE refrigerante (" +
                    "    id INT NOT NULL PRIMARY KEY," +
                    "    nome VARCHAR(100) NOT NULL," +
                    "    preco INTEGER NOT NULL"+
                    ");";

    public GenericDao(Context context){
        super(context, DATABASE, null, DATABASEVER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CERVEJA);
        sqLiteDatabase.execSQL(CREATE_TABLE_REFRIGERANTE);
        sqLiteDatabase.execSQL(CREATE_TABLE_DRINK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cerveja");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS refrigerante");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS drink");
            onCreate(sqLiteDatabase);
        }
    }
}
