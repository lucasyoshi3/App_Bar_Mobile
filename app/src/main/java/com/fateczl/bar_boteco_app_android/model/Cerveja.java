package com.fateczl.bar_boteco_app_android.model;

import com.fateczl.bar_boteco_app_android.controller.CervejaController;

public class Cerveja extends Produto{
    public Cerveja(){
        super();
    }
    @Override
    public String toString() {
        return "id: "+getId()+", "+nome+", preco "+ preco;
    }
}
