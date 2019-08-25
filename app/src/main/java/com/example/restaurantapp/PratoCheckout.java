package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PratoCheckout extends AppCompatActivity implements View.OnClickListener {

    private Button BTConfirmar;
    private Boolean ValorNivelContaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_checkout);

        BTConfirmar = findViewById(R.id.BTFinalCheckout);
        BTConfirmar.setOnClickListener(this);


        Intent intent = getIntent();
        Boolean ValorNivelConta = intent.getBooleanExtra("NivelConta", false);
        ValorNivelContaUsuario = ValorNivelConta;

    }



    @Override
    public void onClick(View view){
        if (view == BTConfirmar){
            AppConfirmaCheckout();
        }

    }   //Verifica o botão pressionado

    private void AppConfirmaCheckout(){


        if (!ValorNivelContaUsuario){
            Toast.makeText(this,"Sucesso na criação do pedido",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MenuPrincipal.class);
            intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA,ValorNivelContaUsuario);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                      //Limpa o stack de activities antes de voltar para limpar um pouco a memória
            startActivity(intent);
        }else{
            Toast.makeText(this,"Sucesso na criação do pedido",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MenuPrincipalADMIN.class);
            intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA,ValorNivelContaUsuario);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                      //Limpa o stack de activities
            startActivity(intent);
        }                   //Verifica qual o nível do usuário antes de confirmar, para saber para onde voltar
    }                          //Confirma saída, mas não funciona ainda, em construção
}
