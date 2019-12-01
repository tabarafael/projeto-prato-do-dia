package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/* o serviço deste array adapter é inflar uma lista com todos os dados do servidor de acordo com o que foi selecionado como filtro*/

public class ArrayAdapterRelatorioCompleto extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    public ArrayAdapterRelatorioCompleto (Context context, String[] values){
        super (context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_relatorio_completo, parent, false);
        final TextView textViewNome = rowView.findViewById(R.id.TV_RowLabel_Nome);
        final TextView dado1 = rowView.findViewById(R.id.TV_Dado1);
        final TextView dado2 = rowView.findViewById(R.id.TV_Dado2);
        final TextView dado3 = rowView.findViewById(R.id.TV_Dado3);
        final TextView dado4 = rowView.findViewById(R.id.TV_Dado4);
        final TextView dado5 = rowView.findViewById(R.id.TV_Dado5);
        final TextView dado6 = rowView.findViewById(R.id.TV_Dado6);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pedidos");
        query.whereEqualTo("objectId",values[position]);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    textViewNome.setText(context.getString(R.string.TXRelatorioNomePrato,objectsList.get(0).getString("pedidosPratoNome")));
                    Date date = objectsList.get(0).getCreatedAt();
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String dateString = df.format(date);
                    dado1.setText(context.getString(R.string.TXRelatorioPratoData,dateString));
                    dado2.setText(context.getString(R.string.TXRelatorioSituacao,objectsList.get(0).getString("pedidosSituacao")));
                    dado3.setText(context.getString(R.string.TXRelatorioPratoDono,objectsList.get(0).getString("pedidosUserNome")));
                    dado4.setText(context.getString(R.string.TXRelatorioPratoPreco,objectsList.get(0).getDouble("pedidosValor")));
                    dado5.setText(context.getString(R.string.TXRelatorioPratoquantidade,objectsList.get(0).getInt("pedidosQuantidade")));
                    dado6.setText(context.getString(R.string.TXRelatorioPratoObservacao,objectsList.get(0).getString("pedidoObservacao")));

                }
            }
        });
        return rowView;
    }
}
