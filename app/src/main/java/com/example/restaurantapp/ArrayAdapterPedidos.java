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

/* o serviço deste array adapter é inflar a lista de pedidos, receber os dados do servidor e exibir corretamente*/

public class ArrayAdapterPedidos extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ArrayAdapterPedidos (Context context, String[] values){
        super (context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_pedidos, parent, false);
        final TextView textViewNome =  rowView.findViewById(R.id.TV_RowLabel_pedidos);
        final TextView textViewQuantidade = rowView.findViewById(R.id.TV_Quantidade_Pedidos);
        final TextView textViewDescricao = rowView.findViewById(R.id.TV_Descricao_Pedidos);


        ParseQuery<ParseObject> query = new ParseQuery<>("Pedidos");
        query.whereEqualTo("objectId", values[position]);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                        String valorNome = object.getString("pedidosPratoNome");
                        Integer valorQuantidade = object.getInt("pedidosQuantidade");
                        String valorDescricao = object.getString("pedidoObservacao");

                        textViewNome.setText(context.getResources().getString(R.string.TXNomePrato,valorNome));
                        textViewQuantidade.setText(context.getResources().getString(R.string.TXQuantidade,valorQuantidade));
                        textViewDescricao.setText(context.getResources().getString(R.string.TXDescricao,valorDescricao));


                    }

            }
        });
        return rowView;
    }
}
