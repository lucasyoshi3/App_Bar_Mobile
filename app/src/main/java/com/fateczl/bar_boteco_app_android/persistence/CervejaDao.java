package com.fateczl.bar_boteco_app_android.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fateczl.bar_boteco_app_android.model.Cerveja;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CervejaDao implements ICervejaDao, ICRUDDao<Cerveja>{

    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;

    public CervejaDao(Context context){
        this.context = context;
    }

    @Override
    public CervejaDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Cerveja cerveja) throws SQLException {
        ContentValues contentValues = getContentValues(cerveja);
        database.insert("cerveja", null, contentValues);
    }

    @Override
    public int update(Cerveja cerveja) throws SQLException {
        ContentValues contentValues = getContentValues(cerveja);
        int ret = database.update("cerveja", contentValues, "id = "
                +cerveja.getId(), null);
        return ret;
    }

    @Override
    public void delete(Cerveja cerveja) throws SQLException {
        database.delete("cerveja", "id = "
                +cerveja.getId(), null);
    }

    @SuppressWarnings("Range")
    @Override
    public List<Cerveja> findAll() throws SQLException {
        List<Cerveja> cervejas = new ArrayList<>();
        String sql = "SELECT id, nome, preco FROM cerveja";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Cerveja cerveja = new Cerveja();

            cerveja.setId(cursor.getInt(cursor.getColumnIndex("id")));
            cerveja.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            cerveja.setPreco(cursor.getInt(cursor.getColumnIndex("preco")));

            cervejas.add(cerveja);
            cursor.moveToNext();
        }
        cursor.close();
        return cervejas;
    }

    private ContentValues getContentValues(Cerveja cerveja) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", cerveja.getId());
        contentValues.put("nome", cerveja.getNome());
        contentValues.put("preco", cerveja.getPreco());

        return contentValues;
    }
}
