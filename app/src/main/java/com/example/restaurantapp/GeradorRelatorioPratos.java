package com.example.restaurantapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**Lista todos os pratos salvos no servidor**/
public class GeradorRelatorioPratos extends ListActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerador_relatorio_pratos);
        AppGetListaPratos();
    }

    protected void onListItemClick(ListView listview, View view, int position, long id){
        String item = (String) getListAdapter().getItem(position);
        Intent intent = new Intent(GeradorRelatorioPratos.this,GeradorRelatorioCompletoPratos.class);
        intent.putExtra("pratoSelecionado",item);
        startActivity(intent);

    } /**Abre o relatório completo de acordo com o item que aqui foi selecionado**/

    private void AppGetListaPratos(){
        final ProgressDialog pd = new ProgressDialog(GeradorRelatorioPratos.this);
        pd.setMessage(getString(R.string.TXLoading));
        pd.setCancelable(false);
        pd.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pratos");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    String[] listaNomePratos = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaNomePratos[i] = objectsList.get(i).getString("PratoNome");
                    }
                    Set<String> temp = new LinkedHashSet<String>(Arrays.asList(listaNomePratos));
                    String[] result = temp.toArray(new String[temp.size()]);

                    ArrayAdapterRelatorioPrato adapter = new ArrayAdapterRelatorioPrato(GeradorRelatorioPratos.this,result);
                    setListAdapter(adapter);
                    pd.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            }
        });
    }  /**baixa a lista com o nome de todos os pratos e infla lista**/
}
