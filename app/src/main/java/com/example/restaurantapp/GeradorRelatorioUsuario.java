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
import java.util.List;

/**Lista todos os usuários salvos no servidor**/

public class GeradorRelatorioUsuario extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerador_relatorio_usuario);
        AppGetListaUsuario();

    }

    protected void onListItemClick(ListView listview, View view, int position, long id){
        String item = (String) getListAdapter().getItem(position);
        Intent intent = new Intent(GeradorRelatorioUsuario.this,GeradorRelatorioCompletoUsuario.class);
        intent.putExtra("UsuarioSelecionado",item);
        startActivity(intent);
    } /**Abre o relatório completo de acordo com o item que aqui foi selecionado**/

    private void AppGetListaUsuario(){
        final ProgressDialog pd = new ProgressDialog(GeradorRelatorioUsuario.this);
        pd.setMessage(getString(R.string.TXLoading));
        pd.setCancelable(false);
        pd.show();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    String[] listaUserId = new String[objectsList.size()];
                    for (int i =0; i < objectsList.size();i++){
                        listaUserId[i] = objectsList.get(i).getObjectId();
                    }

                    ArrayAdapterRelatorioUsuario adapter = new ArrayAdapterRelatorioUsuario(GeradorRelatorioUsuario.this,listaUserId);
                    setListAdapter(adapter);
                    pd.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
            }
        });
    } /**baixa a lista com o nome de todos os usuários e infla lista**/

}
