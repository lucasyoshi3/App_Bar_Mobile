package com.fateczl.bar_boteco_app_android;

import static android.app.ProgressDialog.show;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fateczl.bar_boteco_app_android.controller.CervejaController;
import com.fateczl.bar_boteco_app_android.controller.DrinkController;
import com.fateczl.bar_boteco_app_android.controller.RefrigeranteController;
import com.fateczl.bar_boteco_app_android.model.Cerveja;
import com.fateczl.bar_boteco_app_android.model.Drink;
import com.fateczl.bar_boteco_app_android.model.Refrigerante;
import com.fateczl.bar_boteco_app_android.persistence.CervejaDao;
import com.fateczl.bar_boteco_app_android.persistence.DrinkDao;
import com.fateczl.bar_boteco_app_android.persistence.RefrigeranteDao;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    View view;
    EditText etCodigo;
    EditText etProduto;
    Button btnInserir;
    Button btnExcluir, btnModificar;
    TextView tvListar;
    String[] suggestions = {"Heineken", "Amstel", "Budweiser", "Coca-cola", "Guarana", "Soda", "Caipirinha", "Cosmopolitan", "Mojito"};
    String filtro;
    CervejaController cCont = new CervejaController(new CervejaDao(MainActivity.this));
    RefrigeranteController rCont =new RefrigeranteController(new RefrigeranteDao(MainActivity.this));
    DrinkController dCont = new DrinkController(new DrinkDao(MainActivity.this));
    static int cId=100,dId=200,rId=300;
    RadioButton rbGrande;
    RadioButton rbMedio;
    RadioButton rbPequeno;
    StringBuffer buffer = new StringBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Acessando as views diretamente da Activity
        etProduto = findViewById(R.id.etProduto);
        etCodigo = findViewById(R.id.etCodigo);
        btnInserir = findViewById(R.id.btnInserir);
        btnExcluir = findViewById(R.id.btnExluir);
        btnModificar =findViewById(R.id.btnModificar);
        tvListar = findViewById(R.id.tvListar);

        rbGrande = findViewById(R.id.rbGrande);
        rbGrande.setChecked(true);
        rbMedio = findViewById(R.id.rbMedio);
        rbPequeno = findViewById(R.id.rbPequeno);


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.etProduto);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, suggestions);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setThreshold(1);

        Bundle bundle = getIntent().getExtras();
        StringBuffer buffer = new StringBuffer();
        btnInserir.setOnClickListener(op -> acaoInserir());
        btnExcluir.setOnClickListener(op -> acaoExcluir());
        btnModificar.setOnClickListener(op -> acaoModificar());


    }


    private void acaoInserir() {
        String nomeProduto = etProduto.getText().toString();

        // Verifique se nomeProduto é null
        if (etProduto.getText().toString().isEmpty()) {
            Toast.makeText(MainActivity.this, "Insira um produto", Toast.LENGTH_LONG).show();
            return;
        }
//        "Heineken", "Amstel", "Budweiser", "Coca-cola", "Guarana", "Soda", "Caipirinha", "Cosmopolitan", "Mojito"
        // Verificações dos tipos de produtos
        if (verificarTipo(nomeProduto).equals("cerveja")) {
            try {
                cCont.inserir(montaCerveja(nomeProduto));
            } catch (SQLException e) {
                Toast.makeText(MainActivity.this, "Erro ao inserir cerveja", Toast.LENGTH_LONG).show();
            }
        } else if (verificarTipo(nomeProduto).equals("refri")) {
            try {
                rCont.inserir(montaRefri(nomeProduto));
            } catch (SQLException e) {
                Toast.makeText(MainActivity.this, "Erro ao inserir refrigerante", Toast.LENGTH_LONG).show();
            }
        } else if (verificarTipo(nomeProduto).equals("drink")) {
            try {
                dCont.inserir(montaDrink(nomeProduto));
            } catch (SQLException e) {
                Toast.makeText(MainActivity.this, "Erro ao inserir drink", Toast.LENGTH_LONG).show();
            }
        }
        acaoListar();

    }


    private void acaoListar() {
        String tipoProduto = verificarTipo(etProduto.getText().toString());

        // Limpar o buffer antes de começar a lista
        buffer.setLength(0);

        try {
            switch (tipoProduto) {
                case "cerveja":
                    List<Cerveja> cervejas = cCont.listar();
                    for (Cerveja c : cervejas) {
                        buffer.append(c.toString()).append("\n");
                    }
                    break;
                case "refri":
                    List<Refrigerante> refrigerantes = rCont.listar();
                    for (Refrigerante r : refrigerantes) {
                        buffer.append(r.toString()).append("\n");
                    }
                    break;
                case "drink":
                    List<Drink> drinks = dCont.listar();
                    for (Drink d : drinks) {
                        buffer.append(d.toString()).append("\n");
                    }
                    break;
                default:
                    Toast.makeText(MainActivity.this, "Tipo de produto não reconhecido", Toast.LENGTH_SHORT).show();
                    break;
            }

            // Configurar o texto da tvListar apenas uma vez no final
            tvListar.setText(buffer.toString());

        } catch (SQLException e) {
            Toast.makeText(MainActivity.this, "Erro ao listar produtos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void acaoExcluir() {
        String nomeProduto = etProduto.getText().toString();
        verificarTipo(nomeProduto);
        int id = Integer.parseInt(etCodigo.getText().toString());
        if(verificarTipo(nomeProduto).equals("cerveja")){
            Cerveja cerveja = new Cerveja();
            cerveja.setId(id);
            montaCerveja(nomeProduto);
            try {
                cCont.deletar(cerveja);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(verificarTipo(nomeProduto).equals("refri")){
            Refrigerante refri = new Refrigerante();
            refri.setId(id);
            try {
                rCont.deletar(refri);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(verificarTipo(nomeProduto).equals("drink")){
            Drink drink = new Drink();
            drink.setId(id);
            try {
                dCont.deletar(drink);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        acaoListar();
    }

    private void acaoModificar(){
        String nomeProduto = etProduto.getText().toString();
        int id = Integer.parseInt(etCodigo.getText().toString());
        if(verificarTipo(nomeProduto).equals("cerveja")){
            Cerveja cerveja = montaCerveja(nomeProduto);
            montaCerveja(nomeProduto);
            try {
                cCont.modificar(cerveja);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(verificarTipo(nomeProduto).equals("refri")){
            Refrigerante refri = montaRefri(nomeProduto);
            try {
                rCont.modificar(refri);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(verificarTipo(nomeProduto).equals("drink")){
            Drink drink = montaDrink(nomeProduto);
            try {
                dCont.modificar(drink);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        acaoListar();
    }

    private String verificarTipo(String produto) {
//        {"Heineken", "Amstel", "Budweiser", "Coca-cola", "Guarana", "Soda", "Caipirinha", "Cosmopolitan", "Mojito"}
        if (produto.equals("Heineken") && produto.equals("Amstel") && produto.equals("Budweiser")) {
            return "cerveja";
        }
        else if (produto.equals("Coca-cola") && produto.equals("Guarana") && produto.equals("Soda")) {
            return "refri";
        } else{
            return "drink";
        }
    }

    private Cerveja montaCerveja(String nome) {
        Cerveja cerveja = new Cerveja();
        if(etCodigo.getText().toString().isEmpty()){
            cerveja.setId(cId+1);
            cId++;
        }else{
            cerveja.setId(Integer.parseInt(etCodigo.getText().toString()));
        }
        cerveja.setNome(etProduto.getText().toString());
        if (rbGrande.isChecked()) {
            if (nome.equals("Heineken")) {
                cerveja.setPreco(12);
            } else if (nome.equals("Amstel")) {
                cerveja.setPreco(10);
            } else if (nome.equals("Budweiser")) {
                cerveja.setPreco(13);
            }
        }
        if (rbMedio.isChecked()) {
            if (nome.equals("Heineken")) {
                cerveja.setPreco(10);
            } else if (nome.equals("Amstel")) {
                cerveja.setPreco(8);
            } else if (nome.equals("Budweiser")) {
                cerveja.setPreco(10);
            }
        }
        if (rbPequeno.isChecked()) {
            if (nome.equals("Heineken")) {
                cerveja.setPreco(7);
            } else if (nome.equals("Amstel")) {
                cerveja.setPreco(5);
            } else if (nome.equals("Budweiser")) {
                cerveja.setPreco(8);
            }
        }
        return cerveja;
    }

    private Refrigerante montaRefri(String nome){
        Refrigerante refri = new Refrigerante();
        if(etCodigo.getText().toString().isEmpty()){
            refri.setId(rId+1);
            rId++;
        }else{
            refri.setId(Integer.parseInt(etCodigo.getText().toString()));
        }
        System.out.println(nome);
        refri.setNome(nome);
        if (rbGrande.isChecked()) {
            if (etProduto.getText().toString().equalsIgnoreCase("Coca-cola")){
                refri.setPreco(8);
            }
            if (etProduto.getText().toString().equalsIgnoreCase("Soda")){
                refri.setPreco(6);
            }
            if (etProduto.getText().toString().equalsIgnoreCase("Guarana")){
                refri.setPreco(7);
            }
        }

        if (rbMedio.isChecked()) {
            if (etProduto.getText().toString().equalsIgnoreCase("Coca-cola")){
                refri.setPreco(6);
            }
            if (etProduto.getText().toString().equalsIgnoreCase("Soda")){
                refri.setPreco(4);
            }
            if (etProduto.getText().toString().equalsIgnoreCase("Guarana")){
                refri.setPreco(5);
            }
        }

        if (rbPequeno.isChecked()) {
            if (etProduto.getText().toString().equalsIgnoreCase("Coca-cola")) refri.setPreco(4);
            if (etProduto.getText().toString().equalsIgnoreCase("Soda")) refri.setPreco(3);
            if (etProduto.getText().toString().equalsIgnoreCase("Guarana")) refri.setPreco(4);
        }
        return refri;
    }

    private Drink montaDrink(String nome){
        Drink drink = new Drink();
        if(etCodigo.getText().toString().isEmpty()){
            drink.setId(dId+1);
            dId++;
        }else{
            drink.setId(Integer.parseInt(etCodigo.getText().toString()));
        }
        drink.setNome(nome);
        if(nome.equals("Caipirinha")){
            drink.setPreco(18);
        }
        if(nome.equals("Metropolitan")){
            drink.setPreco(14);
        }
        if(nome.equals("Mojito")){
            drink.setPreco(12);
        }

        return drink;
    }



}