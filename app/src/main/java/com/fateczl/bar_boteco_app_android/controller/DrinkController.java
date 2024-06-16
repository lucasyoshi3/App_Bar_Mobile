package com.fateczl.bar_boteco_app_android.controller;

import com.fateczl.bar_boteco_app_android.model.Drink;
import com.fateczl.bar_boteco_app_android.persistence.DrinkDao;

import java.sql.SQLException;
import java.util.List;

public class DrinkController implements IPedido<Drink> {
    DrinkDao dDao;

    public DrinkController(DrinkDao dDao){
        this.dDao = dDao;
    }

    public double calcDrink(String nome) {
        if(nome.equals("caipirinha")){
            return 18.0;
        }
        if (nome.equals("cosmopolitan")) {
            return 14.0;
        }
        if(nome.equals("mojito")){
            return 15;
        }
        return 0.0;
    }

    @Override
    public void inserir(Drink drink) throws SQLException {
        if(dDao.open()==null){
            dDao.open();
        }
        dDao.insert(drink);
        dDao.close();
    }

    @Override
    public void modificar(Drink drink) throws SQLException {
        if(dDao.open()==null){
            dDao.open();
        }
        dDao.update(drink);
        dDao.close();
    }

    @Override
    public void deletar(Drink drink) throws SQLException {
        if(dDao.open()==null){
            dDao.open();
        }
        dDao.delete(drink);
        dDao.close();
    }

    @Override
    public Drink buscar(Drink drink) throws SQLException {
        return null;
    }

    @Override
    public List<Drink> listar() throws SQLException {
        if(dDao.open()==null){
            dDao.open();
        }
        return dDao.findAll();
    }
}
