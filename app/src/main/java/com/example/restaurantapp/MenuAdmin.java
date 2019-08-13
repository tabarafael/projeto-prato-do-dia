package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAdmin extends AppCompatActivity implements View.OnClickListener  {


    private Button BTSwitchADMIN;
    private Button BTSwitchCLIENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        BTSwitchADMIN = findViewById(R.id.BTSwitchADMIN);
        BTSwitchCLIENT = findViewById(R.id.BTSwitchCLIENT);
        BTSwitchADMIN.setOnClickListener(this);
        BTSwitchCLIENT.setOnClickListener(this);
    }




    @Override
    public void onClick(View view){
        if (view == BTSwitchADMIN){
            finish();
            Intent intent = new Intent(this, MenuPrincipalADMIN.class);
            startActivity(intent);

        }
        if (view == BTSwitchCLIENT){
            finish();
            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
        }

    }

}
