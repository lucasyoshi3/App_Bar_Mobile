package com.fateczl.bar_boteco_app_android.persistence;

import com.fateczl.bar_boteco_app_android.model.Cerveja;
import com.fateczl.bar_boteco_app_android.model.Drink;

import java.sql.SQLException;
import java.util.List;

public interface IDrinkDao {

    public DrinkDao open() throws SQLException;
    public void close();

    @SuppressWarnings("Range")
    List<Drink> findAll() throws SQLException;

}
