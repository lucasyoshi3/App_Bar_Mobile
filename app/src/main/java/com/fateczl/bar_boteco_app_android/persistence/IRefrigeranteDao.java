package com.fateczl.bar_boteco_app_android.persistence;

import com.fateczl.bar_boteco_app_android.model.Cerveja;
import com.fateczl.bar_boteco_app_android.model.Refrigerante;

import java.sql.SQLException;
import java.util.List;

public interface IRefrigeranteDao {
    public RefrigeranteDao open() throws SQLException;
    public void close();

    @SuppressWarnings("Range")
    List<Refrigerante> findAll() throws SQLException;
}
