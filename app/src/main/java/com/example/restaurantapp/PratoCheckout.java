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
    private Integer ValorNivelContaUsuario =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_checkout);

        BTConfirmar = findViewById(R.id.BTFinalCheckout);
        BTConfirmar.setOnClickListener(this);


        Intent intent = getIntent();
        Integer ValorNivelConta = intent.getIntExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA,0);
        if (ValorNivelConta==0){
            Toast.makeText(this,"Ocorreu um erro",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            ValorNivelContaUsuario = ValorNivelConta;
        }
    }



    @Override
    public void onClick(View view){
        if (view == BTConfirmar){
            AppConfirmaCheckout();
        }

    }   //Verifica o botão pressionado

    private void AppConfirmaCheckout(){


        if (ValorNivelContaUsuario==2){
            Toast.makeText(this,"Sucesso na criação do pedido",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MenuPrincipal.class);
            intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA,ValorNivelContaUsuario);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                      //Limpa o stack de activities antes de voltar para limpar um pouco a memória
            startActivity(intent);
        }else if (ValorNivelContaUsuario==1){
            Toast.makeText(this,"Sucesso na criação do pedido",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MenuPrincipalADMIN.class);
            intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA,ValorNivelContaUsuario);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                      //Limpa o stack de activities
            startActivity(intent);
        }                   //Verifica qual o nível do usuário antes de confirmar, para saber para onde voltar
    }                          //Confirma saída, mas não funciona ainda, em construção
}
