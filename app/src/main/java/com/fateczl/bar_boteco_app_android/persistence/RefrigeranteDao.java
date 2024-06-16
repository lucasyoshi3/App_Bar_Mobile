package com.fateczl.bar_boteco_app_android.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fateczl.bar_boteco_app_android.model.Refrigerante;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RefrigeranteDao implements IRefrigeranteDao, ICRUDDao<Refrigerante> {
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase database;
    
    public RefrigeranteDao(Context context){
        this.context = context;
    }

    @Override
    public RefrigeranteDao open() throws SQLException {
        gDao = new GenericDao(context);
        database = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }
    @Override
    public void insert(Refrigerante refrigerante) throws SQLException {
        ContentValues contentValues = getContentValues(refrigerante);
        database.insert("refrigerante", null, contentValues);
    }

    @Override
    public int update(Refrigerante refrigerante) throws SQLException {
        ContentValues contentValues = getContentValues(refrigerante);
        int ret = database.update("refrigerante", contentValues, "id = "
                +refrigerante.getId(), null);
        return ret;
    }

    @Override
    public void delete(Refrigerante refrigerante) throws SQLException {
        database.delete("refrigerante", "id = "
                +refrigerante.getId(), null);
    }

    @SuppressWarnings("Range")
    @Override
    public List<Refrigerante> findAll() throws SQLException {
        List<Refrigerante> cervejas = new ArrayList<>();
        String sql = "SELECT id, nome, preco FROM refrigerante";
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        while(!cursor.isAfterLast()){
            Refrigerante refrigerante = new Refrigerante();

            refrigerante.setId(cursor.getInt(cursor.getColumnIndex("id")));
            refrigerante.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            refrigerante.setPreco(cursor.getInt(cursor.getColumnIndex("preco")));

            cervejas.add(refrigerante);
            cursor.moveToNext();
        }
        cursor.close();
        return cervejas;
    }

    private ContentValues getContentValues(Refrigerante refrigerante) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", refrigerante.getId());
        contentValues.put("nome", refrigerante.getNome());
        contentValues.put("preco", refrigerante.getPreco());

        return contentValues;
    }
}
