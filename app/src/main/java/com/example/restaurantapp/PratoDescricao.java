package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PratoDescricao extends AppCompatActivity implements View.OnClickListener {


    private Button BTCheckout;
    private Boolean valorNivelContaUsuario;
    private String valorPratoSelecionado;
    private Double valorPratoPreco;
    private ImageView IV_imagem_descricao;
    private TextView headerDescricao;
    private TextView textoDescricao;
    private TextView ingredienteDescricao;
    private TextView precoDescricao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_descricao);

        IV_imagem_descricao = findViewById(R.id.IV_imagem_descricao);
        headerDescricao = findViewById(R.id.TV_Header_Descricao);
        textoDescricao = findViewById(R.id.TV_Texto_Descricao);
        precoDescricao = findViewById(R.id.TV_Texto_Preco);
        ingredienteDescricao = findViewById(R.id.TV_Ingrediente_Descricao);
        BTCheckout = findViewById(R.id.BT_Prato_Checkout);
        BTCheckout.setOnClickListener(this);

        Intent intent = getIntent();
        Boolean valorNivelConta = intent.getBooleanExtra("NivelConta", false);
        String pratoSelecionado = intent.getStringExtra("PratoSelecionado");

        valorPratoSelecionado = pratoSelecionado;
        valorNivelContaUsuario = valorNivelConta;

        ParseQuery<ParseObject> query = new ParseQuery<>("Pratos");
        query.whereEqualTo("PratoNome", pratoSelecionado);
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
                                IV_imagem_descricao.setImageBitmap(bmp);
                            }
                        }
                    });
                    String conteudoHeaderDescricao = (String) object.get("PratoNome");
                    String conteudoTextoDescricao = (String) object.get("PratoDescricao");
                    String conteudoIngredienteDescricao = (String) object.get("PratoIngrediente");
                    double conteudoPrecoDescricao = object.getDouble("PratoPreco");
                    valorPratoPreco = conteudoPrecoDescricao;
                    headerDescricao.setText(conteudoHeaderDescricao);
                    textoDescricao.setText(getString(R.string.TXDescricao,conteudoTextoDescricao));
                    ingredienteDescricao.setText(getString(R.string.TXIngredientes,conteudoIngredienteDescricao));
                    precoDescricao.setText(getString(R.string.TXPreco,conteudoPrecoDescricao));
                }else{
                    Toast.makeText(PratoDescricao.this,"Erro de servidor", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onClick (View view){
        if (view == BTCheckout){
         AppCheckout();
        }
    }    //verifica o botão pressioando

    private void AppCheckout(){
        Intent intent = new Intent(this, PratoCheckout.class);
        intent.putExtra("NivelConta",valorNivelContaUsuario);
        intent.putExtra("PratoSelecionado", valorPratoSelecionado);
        intent.putExtra("PratoPreco", valorPratoPreco);
        startActivity(intent);
    }         //Direciona o usuário para o checkout final
}
