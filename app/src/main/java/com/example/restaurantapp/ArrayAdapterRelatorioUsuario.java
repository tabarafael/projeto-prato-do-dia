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

/* o serviço deste array adapter é inflar a lista com todos os dados dos usuários*/

public class ArrayAdapterRelatorioUsuario extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    public ArrayAdapterRelatorioUsuario (Context context, String[] values){
        super (context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override

    public View getView (final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_relatorio_usuario, parent, false);

        final TextView textViewUser =  rowView.findViewById(R.id.TV_RowLabel_Nome);
        final TextView textViewAdmin = rowView.findViewById(R.id.TV_Relatorio_Usuario_Admin);
        final TextView textViewNome = rowView.findViewById(R.id.TV_Relatorio_Usuario_Nome);
        final TextView textViewData = rowView.findViewById(R.id.TV_Relatorio_Usuario_Criacao);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId",values[position].trim());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectsList, ParseException e) {
                if (e==null){
                    textViewUser.setText(objectsList.get(0).getString("username"));
                    if (objectsList.get(0).getBoolean("NivelAdmin")){
                        textViewAdmin.setText(context.getString(R.string.TXAdmin));
                    }else{
                        textViewAdmin.setText(context.getString(R.string.TXClient));
                    }
                    textViewNome.setText(objectsList.get(0).getString("Name"));
                    Date date = objectsList.get(0).getCreatedAt();
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String dateString = df.format(date);
                    textViewData.setText(dateString);
                }
            }
        });

        return rowView;
    }
}
