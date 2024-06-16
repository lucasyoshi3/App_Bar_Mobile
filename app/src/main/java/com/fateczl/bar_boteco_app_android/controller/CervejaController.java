package com.fateczl.bar_boteco_app_android.controller;

import com.fateczl.bar_boteco_app_android.model.Cerveja;
import com.fateczl.bar_boteco_app_android.persistence.CervejaDao;

import java.sql.SQLException;
import java.util.List;

public class CervejaController implements IPedido<Cerveja> {

    public final CervejaDao cDao;
    public CervejaController(CervejaDao cDao){
        this.cDao = cDao;
    }

    @Override
    public void inserir(Cerveja cerveja) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        cDao.insert(cerveja);
    }

    @Override
    public void modificar(Cerveja cerveja) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        cDao.update(cerveja);
    }

    @Override
    public void deletar(Cerveja cerveja) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        cDao.delete(cerveja);
    }

    @Override
    public Cerveja buscar(Cerveja cerveja) throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        cDao.findAll();
        return null;
    }

    @Override
    public List<Cerveja> listar() throws SQLException {
        if(cDao.open() == null){
            cDao.open();
        }
        return cDao.findAll();
    }
}
