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
import java.util.List;

/* o serviço deste array adapter é inflar a lista de todos os pratos salvos no servidor*/

public class ArrayAdapterRelatorioPrato extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    public ArrayAdapterRelatorioPrato (Context context, String[] values){
        super (context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_relatorio_prato, parent, false);
        final TextView textViewNome = rowView.findViewById(R.id.TV_RowLabel_Nome);
        final TextView textViewQuantidade = rowView.findViewById(R.id.TV_Relatorio_Quantidade);
        final TextView textViewQuantidadePratos = rowView.findViewById(R.id.TV_Relatorio_Quantidade_Pratos);
        final TextView textViewValorTotal = rowView.findViewById(R.id.TV_Relatorio_Valor_Total);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pedidos");
        query.whereEqualTo("pedidosPratoNome",values[position].trim());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    textViewQuantidade.setText(context.getString(R.string.TXRelatorioQuantidade,objectsList.size()));
                    int quant =0;
                    for (int i =0; i<objectsList.size();i++){
                        quant = quant + objectsList.get(i).getInt("pedidosQuantidade");
                    }
                    textViewQuantidadePratos.setText(context.getString(R.string.TXRelatorioQuantidadePratos,quant));
                    quant = 0;
                    double valor=0;
                    for (int i=0; i < objectsList.size();i++){
                        valor = valor + objectsList.get(i).getDouble("pedidosValor");
                    }
                    textViewValorTotal.setText(context.getString(R.string.TXRelatorioPrecoTotal,valor));
                    valor =0;

                }
            }
        });

            textViewNome.setText(values[position]);


        return rowView;
    }
}