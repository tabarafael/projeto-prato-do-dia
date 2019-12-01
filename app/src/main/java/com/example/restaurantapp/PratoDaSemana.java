package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**Acitvity lista os dias da semana para usuários poderem verificar outros dias além do dia presente*/
public class PratoDaSemana extends AppCompatActivity implements View.OnClickListener{

    private Button BTPSDom;
    private Button BTPSSeg;
    private Button BTPSTer;
    private Button BTPSQua;
    private Button BTPSQui;
    private Button BTPSSex;
    private Button BTPSSab;
    private boolean ValorNivelContaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_da_semana);

        BTPSDom = findViewById(R.id.BT_PS_Dom);
        BTPSSeg = findViewById(R.id.BT_PS_Seg);
        BTPSTer = findViewById(R.id.BT_PS_Ter);
        BTPSQua = findViewById(R.id.BT_PS_Qua);
        BTPSQui = findViewById(R.id.BT_PS_Qui);
        BTPSSex = findViewById(R.id.BT_PS_Sex);
        BTPSSab = findViewById(R.id.BT_PS_Sab);

        BTPSDom.setOnClickListener(this);
        BTPSSeg.setOnClickListener(this);
        BTPSTer.setOnClickListener(this);
        BTPSQua.setOnClickListener(this);
        BTPSQui.setOnClickListener(this);
        BTPSSex.setOnClickListener(this);
        BTPSSab.setOnClickListener(this);

        Intent intent = getIntent();
        ValorNivelContaUsuario = intent.getBooleanExtra("NivelConta", false);

    }

    public void onClick(View view){
        if(view == BTPSDom){
            AppAbrePratoDoDia(1);
        } else if(view == BTPSSeg){
            AppAbrePratoDoDia(2);
        } else if(view == BTPSTer){
            AppAbrePratoDoDia(3);
        } else if(view == BTPSQua){
            AppAbrePratoDoDia(4);
        } else if(view == BTPSQui){
            AppAbrePratoDoDia(5);
        } else if(view == BTPSSex){
            AppAbrePratoDoDia(6);
        } else if(view == BTPSSab){
            AppAbrePratoDoDia(7);
        }
    }     //Verifica o botão pressionado

    private void AppAbrePratoDoDia (int valorDia){
        Intent intent = new Intent (this, PratoDoDia.class);
        intent.putExtra("NivelConta",ValorNivelContaUsuario);
        intent.putExtra("ValorDia",valorDia);
        intent.putExtra("Origem",getClass().getName());
        startActivity(intent);
    }  //Esta parte define qual o dia do cardápio que o usuário quer olhar, para então filtrar do servidor somente as opções relevantes.
}
