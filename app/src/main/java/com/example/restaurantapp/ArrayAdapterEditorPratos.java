package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ArrayAdapterEditorPratos extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ArrayAdapterEditorPratos (Context context, String[] values){
        super (context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override

    public View getView (final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_pedidos, parent, false);
        final TextView textViewNome = (TextView) rowView.findViewById(R.id.TV_RowLabel_pedidos);
        final TextView textViewQuantidade = (TextView) rowView.findViewById(R.id.TV_Quantidade_Pedidos);
        final TextView textViewDescricao = (TextView) rowView.findViewById(R.id.TV_Descricao_Pedidos);


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Pratos");
        query.whereEqualTo("objectId", values[position]);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    String valorNome = object.getString("PratoNome");
                    Integer valorDia = object.getInt("PratoDia");
                    Double valorPreco = object.getDouble("PratoPreco");

                    textViewNome.setText(context.getResources().getString(R.string.TXNomePrato,valorNome));
                    textViewQuantidade.setText(context.getResources().getString(R.string.TXValorDia,valorDia));
                    textViewDescricao.setText(context.getResources().getString(R.string.TXValorPreco,valorPreco));
                }

            }
        });
        return rowView;
    }
}