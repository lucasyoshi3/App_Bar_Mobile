package com.fateczl.bar_boteco_app_android.persistence;

import java.sql.SQLException;
import java.util.List;

import com.fateczl.bar_boteco_app_android.model.Cerveja;

public interface ICervejaDao {
    public CervejaDao open() throws SQLException;
    public void close();

    @SuppressWarnings("Range")
    List<Cerveja> findAll() throws SQLException;

}
