package com.fateczl.bar_boteco_app_android.controller;

import java.sql.SQLException;
import java.util.List;

public interface IPedido <T>{
    public void inserir(T t) throws SQLException;
    public void modificar(T t) throws SQLException;
    public void deletar(T t) throws SQLException;
    public T buscar(T t) throws SQLException;
    public List<T> listar() throws SQLException;
}
