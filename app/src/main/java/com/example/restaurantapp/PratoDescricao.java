package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PratoDescricao extends AppCompatActivity implements View.OnClickListener {


    Button BTCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_descricao);


        BTCheckout = findViewById(R.id.BTPratoCheckout);
        BTCheckout.setOnClickListener(this);
    }


    @Override
    public void onClick (View view){
        if (view == BTCheckout){
         AppCheckout();
        }
    }

    private void AppCheckout(){
        Intent intent = new Intent(this, PratoCheckout.class);
        startActivity(intent);
    }
}
