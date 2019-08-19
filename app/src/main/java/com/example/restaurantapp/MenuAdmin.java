package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAdmin extends AppCompatActivity implements View.OnClickListener {


    private Button BTSwitchADMIN;
    private Button BTSwitchCLIENT;
    public static final String PARAMETRO_NIVEL_CONTA = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        BTSwitchADMIN = findViewById(R.id.BTSwitchADMIN);
        BTSwitchCLIENT = findViewById(R.id.BTSwitchCLIENT);
        BTSwitchADMIN.setOnClickListener(this);
        BTSwitchCLIENT.setOnClickListener(this);
    }    //A partir deste ponto, o usuário recebe um "nível de conta", 1 para Admin e 2 para clientes, isso afeta o acesso a certos aspectos e menus


    @Override
    public void onClick(View view) {
        if (view == BTSwitchADMIN) {
            AppDirecionaAdmin();
        }
        if (view == BTSwitchCLIENT) {
            AppDirecionaClient();
        }
    }         //Verifica o botão pressionado e redireciona de acordo;

    private void AppDirecionaAdmin () {
        finish();
        Intent intent = new Intent(this, MenuPrincipalADMIN.class);
        intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA, 1);
        startActivity(intent);
    }     //Direciona o usuário para o lado admin do app

    private void AppDirecionaClient () {
        finish();
        Intent intent = new Intent(this, MenuPrincipal.class);
        intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA, 2);
        startActivity(intent);
    }    //Direciona o usuário para o lado cliente do app

}
