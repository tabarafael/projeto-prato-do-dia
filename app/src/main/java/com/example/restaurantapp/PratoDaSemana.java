package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PratoDaSemana extends AppCompatActivity implements View.OnClickListener{



    private Button BTPSDom;
    private Button BTPSSeg;
    private Button BTPSTer;
    private Button BTPSQua;
    private Button BTPSQui;
    private Button BTPSSex;
    private Button BTPSSab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_da_semana);

        BTPSDom = findViewById(R.id.BTPSDom);
        BTPSSeg = findViewById(R.id.BTPSSeg);
        BTPSTer = findViewById(R.id.BTPSTer);
        BTPSQua = findViewById(R.id.BTPSQua);
        BTPSQui = findViewById(R.id.BTPSQui);
        BTPSSex = findViewById(R.id.BTPSSex);
        BTPSSab = findViewById(R.id.BTPSSab);

        BTPSDom.setOnClickListener(this);
        BTPSSeg.setOnClickListener(this);
        BTPSTer.setOnClickListener(this);
        BTPSQua.setOnClickListener(this);
        BTPSQui.setOnClickListener(this);
        BTPSSex.setOnClickListener(this);
        BTPSSab.setOnClickListener(this);
    }

    public void onClick(View view){
        if(view == BTPSDom){
            AppPSDom();
        }
        if(view == BTPSSeg){
            AppPSSeg();
        }
        if(view == BTPSTer){
            AppPSTer();
        }
        if(view == BTPSQua){
            AppPSQua();
        }
        if(view == BTPSQui){
            AppPSQui();
        }
        if(view == BTPSSex){
            AppPSSex();
        }
        if(view == BTPSSab){
            AppPSSab();
        }
    }

    private void AppPSDom(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra(PratoDoDia.PARAMETRO_DIA_SEMANA,1);
        startActivity(intent);
    }
    private void AppPSSeg(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra(PratoDoDia.PARAMETRO_DIA_SEMANA,2);
        startActivity(intent);
    }
    private void AppPSTer(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra(PratoDoDia.PARAMETRO_DIA_SEMANA,3);
        startActivity(intent);
    }
    private void AppPSQua(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra(PratoDoDia.PARAMETRO_DIA_SEMANA,4);
        startActivity(intent);
    }
    private void AppPSQui(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra(PratoDoDia.PARAMETRO_DIA_SEMANA,5);
        startActivity(intent);
    }
    private void AppPSSex(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra(PratoDoDia.PARAMETRO_DIA_SEMANA,6);
        startActivity(intent);
    }
    private void AppPSSab(){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra(PratoDoDia.PARAMETRO_DIA_SEMANA,7);
        startActivity(intent);
    }

}
