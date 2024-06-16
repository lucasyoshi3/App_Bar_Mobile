package com.fateczl.bar_boteco_app_android.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;

import com.fateczl.bar_boteco_app_android.model.Cerveja;
import com.fateczl.bar_boteco_app_android.model.Drink;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrinkDao implements IDrinkDao, ICRUDDao<Drink>{

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public DrinkDao (Context context){
        this.context = context;
    }

    @Override
    public DrinkDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Drink drink) throws SQLException {
        ContentValues contentValues = getContentValues(drink);
        database.insert("drink", null, contentValues);
    }

    @Override
    public int update(Drink drink) throws SQLException {
        ContentValues contentValues = getContentValues(drink);
        int ret = database.update("drink", contentValues, "id = "
                +drink.getId(), null);
        return ret;
    }


    @Override
    public void delete(Drink drink) throws SQLException {
        database.delete("drink", "id = "
                +drink.getId(), null);
    }

    @SuppressWarnings("Range")
    @Override
    public List<Drink> findAll() throws SQLException {
        List<Drink> drinks = new ArrayList<>();
        String sql = "SELECT id, nome, preco FROM drink";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Drink drink = new Drink();

            drink.setId(cursor.getInt(cursor.getColumnIndex("id")));
            drink.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            drink.setPreco(cursor.getInt(cursor.getColumnIndex("preco")));

            drinks.add(drink);
            cursor.moveToNext();
        }
        cursor.close();
        return drinks;
    }

    private ContentValues getContentValues(Drink drink) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", drink.getId());
        contentValues.put("nome", drink.getNome());
        contentValues.put("preco", drink.getPreco());

        return contentValues;
    }
}
