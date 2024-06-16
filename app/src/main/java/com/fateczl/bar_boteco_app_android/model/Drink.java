package com.fateczl.bar_boteco_app_android.model;

import com.fateczl.bar_boteco_app_android.controller.DrinkController;

public class Drink extends Produto {

    @Override
    public String toString() {
        return "id: "+getId()+", "+ nome +", preco "+ preco;
    }
}
