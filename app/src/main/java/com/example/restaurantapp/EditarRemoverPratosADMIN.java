package com.example.restaurantapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class EditarRemoverPratosADMIN extends ListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_remover_pratos_admin);

        Intent intent = getIntent();
        boolean ValorNivelContaUsuario = intent.getBooleanExtra("NivelConta",false);
        AppGetListaPratos();
    }

    @Override
    protected void onListItemClick(ListView listview, View view, int position, long id){
        String item = (String) getListAdapter().getItem(position);
        ParseQuery<ParseObject> query = new ParseQuery<>("Pratos");
        query.whereEqualTo("objectId", item);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    final ParseObject  objetoInterno = object;
                    new  AlertDialog.Builder(EditarRemoverPratosADMIN.this)
                            .setTitle("Exclusão de dados")
                            .setMessage("Esta ação é permanente!")
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try{
                                        objetoInterno.delete();
                                        objetoInterno.saveInBackground();


                                        new AlertDialog.Builder(EditarRemoverPratosADMIN.this)
                                                .setTitle("Excluído com sucesso")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.cancel();
                                                        finish();
                                                        overridePendingTransition(0, 0);
                                                        startActivity(getIntent());
                                                        overridePendingTransition(0, 0);
                                                    }
                                                }).show();
                                    }catch (Exception f){
                                        Toast.makeText(EditarRemoverPratosADMIN.this,f.getMessage(),Toast.LENGTH_LONG).show();
                                    }

                                }
                            }).show();


                }else{
                    Toast.makeText(EditarRemoverPratosADMIN.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void AppGetListaPratos(){
        final ProgressDialog pd = new ProgressDialog(EditarRemoverPratosADMIN.this);
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
                    ArrayAdapterEditorPratos adapter = new ArrayAdapterEditorPratos(EditarRemoverPratosADMIN.this,listaNomePedidos);
                    setListAdapter(adapter);
                    pd.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            }
        });
    }
}
