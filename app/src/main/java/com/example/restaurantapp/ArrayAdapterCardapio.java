package com.example.restaurantapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;


/* o serviço deste array adapter é inflar a lista do cardápio do dia, receber os dados do servidor e exibir corretamente*/

public class ArrayAdapterCardapio extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ArrayAdapterCardapio(Context context, String[] values){
        super (context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout_cardapio, parent, false);
        TextView textView = rowView.findViewById(R.id.TV_RowLabel_cardapio);
        final ImageView imageview = rowView.findViewById(R.id.IV_RowImage_cardapio);

        ParseQuery<ParseObject> query = new ParseQuery<>("Pratos");
        query.whereEqualTo("PratoNome", values[position]);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    try{

                        ParseFile imagem = (ParseFile) object.get("PratoImagem");
                        imagem.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e==null){
                                    Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
                                    imageview.setImageBitmap(bmp);
                                }
                            }
                        });
                    }catch (Exception f){
                        Toast.makeText(context,f.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        textView.setText(values[position]);
        return rowView;
    }
}



