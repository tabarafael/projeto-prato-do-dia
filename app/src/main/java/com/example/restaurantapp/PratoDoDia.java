package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.app.ListActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class PratoDoDia extends ListActivity implements View.OnClickListener{

    private TextView TVPratoDiaHeader;
    private Boolean ValorNivelContaUsuario;
    private Integer ValorSemana = 0;
    private ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_do_dia);
        TVPratoDiaHeader = findViewById(R.id.TVPratoDiaHeader);

        Integer hoje = 1;
        AppGetPratosInBackGround(hoje);


        Intent intent = getIntent();
        Boolean ValorNivelConta = intent.getBooleanExtra("NivelConta", false);
        ValorNivelContaUsuario = ValorNivelConta;
        ValorSemana = intent.getIntExtra("ValorDia",0);

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
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this,item +"selected", Toast.LENGTH_LONG).show();
    }

    private void AppGetPratosInBackGround(Integer hoje){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pratos");
        final ArrayList<Object> listaPratos = new ArrayList<Object>();
        query.whereEqualTo("PratoDia", hoje);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                     String[] listaNomePratos = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaNomePratos[i] = objectsList.get(i).getString("PratoNome");
                    }

                    Toast.makeText(PratoDoDia.this, ""+ listaNomePratos[0]+""+listaNomePratos[1],Toast.LENGTH_LONG).show();
                    final ListView listview = (ListView) findViewById(R.id.LW_Pratos);
                    MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(PratoDoDia.this,listaNomePratos);
                    setListAdapter(adapter);

                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override

    public void onClick (View view){


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
     intent.putExtra("NivelConta",ValorNivelContaUsuario);
     startActivity(intent);
    }       //Acessa o prato selecionado na lista disponível

}
