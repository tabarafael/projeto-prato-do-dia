package com.example.restaurantapp;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

/*Classe para visualizar os pratos a serem editados*/

public class EditarModificarPratosADMIN extends ListActivity {

    private boolean ValorNivelContaUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_modificar_pratos_admin);

        Intent intent = getIntent();
        ValorNivelContaUsuario = intent.getBooleanExtra("NivelConta",false);
        AppGetListaPratos();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }  /*Reconstrução da activity para atualizar a lista*/
    @Override
    protected void onListItemClick(ListView listview, View view, int position, long id){
        String item = (String) getListAdapter().getItem(position);
        Intent intent = new Intent(EditarModificarPratosADMIN.this, EditarEditorPratosADMIN.class);
        intent.putExtra("NivelConta", ValorNivelContaUsuario);
        intent.putExtra("PratoSelecionado",item);
        startActivity(intent);

    } /*Verifica qual item foi selecionado da lista*/

    private void AppGetListaPratos(){
        final ProgressDialog pd = new ProgressDialog(EditarModificarPratosADMIN.this);
        pd.setMessage(getString(R.string.TXLoading));
        pd.setCancelable(false);
        pd.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pratos");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    String[] listaNomePedidos = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaNomePedidos[i] = objectsList.get(i).getObjectId();
                    }
                    ArrayAdapterEditorPratos adapter = new ArrayAdapterEditorPratos(EditarModificarPratosADMIN.this,listaNomePedidos);
                    setListAdapter(adapter);
                    pd.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            }
        });
    }   /*Carrega a lista de pratos*/
}
