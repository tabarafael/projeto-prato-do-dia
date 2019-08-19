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

    private TextView TVPratoDiaHeader;
    private ImageButton BTPrato1;
    private Integer ValorNivelContaUsuario = 0;
    private Integer ValorSemana = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_do_dia);

        TVPratoDiaHeader = findViewById(R.id.TVPratoDiaHeader);
        BTPrato1 = findViewById(R.id.BTPrato1);
        BTPrato1.setOnClickListener(this);


        Intent intent = getIntent();
        ValorNivelContaUsuario = intent.getIntExtra("NivelConta",0);
        ValorSemana = intent.getIntExtra("ValorDia",0);
        if (ValorNivelContaUsuario == 0){
            Toast.makeText(this,"Ocorreu um erro, Nivel Usuario",Toast.LENGTH_SHORT).show();
            finish();
        }else if(ValorSemana == 0){
            Toast.makeText(this,"Ocorreu um erro, Valor Semana",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            AppDiaSemana(ValorSemana);
        }
    }

    @Override

    public void onClick (View view){
        if (view==BTPrato1){
            AppAcessoPrato();
        }

    }      //Verifica o botão pressionado, atualmente só um funciona, pois é somente temporário

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
        }     //Verifica "de onde o usuário vem", 1-7 é o mennu semanal, 8 é "dia atual". Atualmente apenas modifica o header, mas deve filtrar os pratos disponíveis nofuturo
    }

    private void AppAcessoPrato(){
     Intent intent = new Intent (this, PratoDescricao.class);
     intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA,ValorNivelContaUsuario);
     startActivity(intent);
    }       //Acessa o prato selecionado na lista disponível

}
