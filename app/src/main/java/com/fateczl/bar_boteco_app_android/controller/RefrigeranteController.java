package com.fateczl.bar_boteco_app_android.controller;

import com.fateczl.bar_boteco_app_android.model.Refrigerante;
import com.fateczl.bar_boteco_app_android.persistence.RefrigeranteDao;

import java.sql.SQLException;
import java.util.List;

public class RefrigeranteController implements IPedido<Refrigerante> {
    private RefrigeranteDao rDao;

    public RefrigeranteController(RefrigeranteDao rDao){
        this.rDao=rDao;
    }


    @Override
    public void inserir(Refrigerante refrigerante) throws SQLException {
        if(rDao.open()==null){
            rDao.open();
        }
        rDao.insert(refrigerante);
        rDao.close();
    }

    @Override
    public void modificar(Refrigerante refrigerante) throws SQLException {
        if(rDao.open()==null){
            rDao.open();
        }
        rDao.update(refrigerante);
        rDao.close();
    }

    @Override
    public void deletar(Refrigerante refrigerante) throws SQLException {
        if(rDao.open()==null){
            rDao.open();
        }
        rDao.delete(refrigerante);
        rDao.close();
    }

    @Override
    public Refrigerante buscar(Refrigerante refrigerante) throws SQLException {
        return null;
    }

    @Override
    public List<Refrigerante> listar() throws SQLException {
        if(rDao.open()==null){
            rDao.open();
        }
        return rDao.findAll();
    }
}
