package com.example.restaurantapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.Calendar;
import java.util.List;


public class PratoDoDia extends ListActivity {

    private TextView TVPratoDiaHeader;
    private Boolean ValorNivelContaUsuario;
    private int ValorSemana = 0;
    private String origem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_do_dia);
        TVPratoDiaHeader = findViewById(R.id.TV_Prato_Dia_Header);



        Intent intent = getIntent();
        origem = intent.getStringExtra("Origem");
        ValorNivelContaUsuario = intent.getBooleanExtra("NivelConta", false);
        ValorSemana = intent.getIntExtra("ValorDia",0);
        if (ValorSemana==8){
            Calendar calendar = Calendar.getInstance();
            ValorSemana = calendar.get(Calendar.DAY_OF_WEEK);
            }
        AppGetPratosInBackGround(ValorSemana);


        if(ValorSemana == 0){
            Toast.makeText(this,"Ocorreu um erro, Valor Semana",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            AppDiaSemana(ValorSemana);
        }
    }

    @Override
    protected void onListItemClick(ListView listview, View view, int position, long id){
        if (origem.equals("com.example.restaurantapp.PratoDaSemana")){
            new AlertDialog.Builder(this).setMessage(getString(R.string.ERPratoSemana)).show();
        }else{
            String item = (String) getListAdapter().getItem(position);
            Intent intent = new Intent(PratoDoDia.this, PratoDescricao.class);
            intent.putExtra("NivelConta", ValorNivelContaUsuario);
            intent.putExtra("PratoSelecionado",item);
            startActivity(intent);
        }

    }

    private void AppGetPratosInBackGround(int hoje){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pratos");
        query.whereEqualTo("PratoDia", hoje);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                     String[] listaNomePratos = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaNomePratos[i] = objectsList.get(i).getString("PratoNome");
                    }
                    ArrayAdapterCardapio adapter = new ArrayAdapterCardapio(PratoDoDia.this,listaNomePratos);
                    setListAdapter(adapter);

                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void AppDiaSemana(int ValorSemana){

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


}
