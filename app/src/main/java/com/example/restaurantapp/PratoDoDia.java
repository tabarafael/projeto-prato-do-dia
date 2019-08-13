package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PratoDoDia extends AppCompatActivity implements View.OnClickListener{

    TextView TVPratoDiaHeader;
    ImageButton BTPrato1;

    public static final String PARAMETRO_DIA_SEMANA = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_do_dia);

        TVPratoDiaHeader = findViewById(R.id.TVPratoDiaHeader);
        BTPrato1 = findViewById(R.id.BTPrato1);
        BTPrato1.setOnClickListener(this);


        Intent intent = getIntent();
        Integer ValorSemana = intent.getIntExtra(PARAMETRO_DIA_SEMANA,0);

        if (ValorSemana == 0){
            Toast.makeText(this,"Ocorreu um erro",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            AppDiaSemana(ValorSemana);
        }
    }

    @Override

    public void onClick (View view){
        if (view==BTPrato1){
            AppAcessoPrato();
        }

    }
    private void AppDiaSemana(Integer ValorSemana){

        switch (ValorSemana){
            case 1:
                TVPratoDiaHeader.setText(getString(R.string.TXDom));
                break;
            case 2:
                TVPratoDiaHeader.setText(getString(R.string.TXSeg));
                break;
            case 3:
                TVPratoDiaHeader.setText(getString(R.string.TXTer));
                break;
            case 4:
                TVPratoDiaHeader.setText(getString(R.string.TXQua));
                break;
            case 5:
                TVPratoDiaHeader.setText(getString(R.string.TXQui));
                break;
            case 6:
                TVPratoDiaHeader.setText(getString(R.string.TXSex));
                break;
            case 7:
                TVPratoDiaHeader.setText(getString(R.string.TXSab));
                break;
            case 8:
                TVPratoDiaHeader.setText(getString(R.string.TXDia));
                break;
            default:
                Toast.makeText(this,"default",Toast.LENGTH_SHORT).show();
        }

    }

    private void AppAcessoPrato(){
     Intent intent = new Intent (this, PratoDescricao.class);
     startActivity(intent);
    }

}
