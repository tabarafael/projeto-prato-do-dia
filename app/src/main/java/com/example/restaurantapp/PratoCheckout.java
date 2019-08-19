package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PratoCheckout extends AppCompatActivity implements View.OnClickListener {

    Button BTConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_checkout);

        BTConfirmar = findViewById(R.id.BTFinalCheckout);
        BTConfirmar.setOnClickListener(this);
    }



    @Override
    public void onClick(View view){
        if (view == BTConfirmar){
            AppConfirmaCheckout();
        }

    }

    private void AppConfirmaCheckout(){
        Toast.makeText(this, "Pedido agendado",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MenuPrincipal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                      //Limpa o stack de activities
        startActivity(intent);

    }
}
