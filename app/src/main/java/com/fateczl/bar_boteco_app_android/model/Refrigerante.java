package com.fateczl.bar_boteco_app_android.model;

import com.fateczl.bar_boteco_app_android.controller.RefrigeranteController;

public class Refrigerante extends Produto{
    public Refrigerante(){
        super();
    }

    @Override
    public String toString() {
        return "id: "+getId()+", " + getNome() +", preco "+ getPreco();
    }


}
