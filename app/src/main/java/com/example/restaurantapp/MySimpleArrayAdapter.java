package com.example.restaurantapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MySimpleArrayAdapter(Context context, String[] values){
        super (context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override

    public View getView (final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.TV_RowLabel);
        final ImageView imageview = (ImageView)  rowView.findViewById(R.id.IV_RowImage);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Pratos");
        query.whereEqualTo("PratoNome", values[position]);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
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


                }

            }
        });



        textView.setText(values[position]);
        return rowView;
    }
}


