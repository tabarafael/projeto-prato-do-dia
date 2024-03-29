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


/* o serviço deste array adapter é inflar a lista de edição de pratos, listando todos para serem modificados , receber os dados do servidor e exibir corretamente*/

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
        final TextView textViewNome = rowView.findViewById(R.id.TV_RowLabel_pedidos);
        final TextView textViewQuantidade = rowView.findViewById(R.id.TV_Quantidade_Pedidos);
        final TextView textViewDescricao = rowView.findViewById(R.id.TV_Descricao_Pedidos);
        ParseQuery<ParseObject> query = new ParseQuery<>("Pratos");
        query.whereEqualTo("objectId", values[position]);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    String valorNome = object.getString("PratoNome");
                    int valorDia = object.getInt("PratoDia");
                    Double valorPreco = object.getDouble("PratoPreco");
                    switch (valorDia){
                        case 1:
                            textViewQuantidade.setText(context.getResources().getString(R.string.TXValorDia,context.getResources().getString(R.string.TXDom)));
                            break;
                        case 2:
                            textViewQuantidade.setText(context.getResources().getString(R.string.TXValorDia,context.getResources().getString(R.string.TXSeg)));
                            break;
                        case 3:
                            textViewQuantidade.setText(context.getResources().getString(R.string.TXValorDia,context.getResources().getString(R.string.TXTer)));
                            break;
                        case 4:
                            textViewQuantidade.setText(context.getResources().getString(R.string.TXValorDia,context.getResources().getString(R.string.TXQua)));
                            break;
                        case 5:
                            textViewQuantidade.setText(context.getResources().getString(R.string.TXValorDia,context.getResources().getString(R.string.TXQui)));
                            break;
                        case 6:
                            textViewQuantidade.setText(context.getResources().getString(R.string.TXValorDia,context.getResources().getString(R.string.TXSex)));
                            break;
                        case 7:
                            textViewQuantidade.setText(context.getResources().getString(R.string.TXValorDia,context.getResources().getString(R.string.TXSab)));
                            break;
                    }
                    textViewNome.setText(context.getResources().getString(R.string.TXNomePrato,valorNome));
                    textViewDescricao.setText(context.getResources().getString(R.string.TXValorPreco,valorPreco));
                }

            }
        });
        return rowView;
    }
}