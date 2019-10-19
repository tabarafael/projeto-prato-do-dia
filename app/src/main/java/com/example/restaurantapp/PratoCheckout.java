package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class PratoCheckout extends AppCompatActivity implements View.OnClickListener {

    private Button BTConfirmar;
    private boolean valorNivelContaUsuario;
    private String valorPratoSelecionado;
    private Double valorPrecoTotal;
    private double valorQuantidade;
    private ImageView IVImagemCheckout;
    private TextView TVtotalCheckout;
    private EditText ETQuantidadeCheckout;
    private final String valorSituacao = "Em espera";
    private String valorObservacao;
    private EditText ETCheckoutObservacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prato_checkout);

        IVImagemCheckout = findViewById(R.id.IV_Imagem_Checkout);
        TVtotalCheckout= findViewById(R.id.TV_Checkout_Valor_Resultado);
        ETQuantidadeCheckout = findViewById(R.id.ET_Quantidade_pedido);
        BTConfirmar = findViewById(R.id.BT_Final_Checkout);
        BTConfirmar.setOnClickListener(this);
        ETCheckoutObservacao = findViewById(R.id.ET_Checkout_Observacao);

        Intent intent = getIntent();
        Boolean nivelConta = intent.getBooleanExtra("NivelConta", false);
        String pratoSelecionado = intent.getStringExtra("PratoSelecionado");
        final Double valorPrecoPrato = intent.getDoubleExtra("PratoPreco", -1);
        valorNivelContaUsuario = nivelConta;
        valorPratoSelecionado = pratoSelecionado;


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
                                IVImagemCheckout.setImageBitmap(bmp);
                            }
                        }
                    });


                }else{
                    Toast.makeText(PratoCheckout.this,"Erro de servidor", Toast.LENGTH_LONG).show();
                }
            }
        });

        ETQuantidadeCheckout.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if(i == EditorInfo.IME_ACTION_NEXT){
                    if (TextUtils.isEmpty(ETQuantidadeCheckout.getText())) {
                        Toast.makeText(PratoCheckout.this,getText(R.string.ERVazio),Toast.LENGTH_SHORT).show();
                        TVtotalCheckout.setText("");
                    }else{
                        Double quantidade = Double.parseDouble(ETQuantidadeCheckout.getText().toString());
                        if(quantidade!=null||quantidade!=0){
                            valorPrecoTotal = (valorPrecoPrato * quantidade);
                            TVtotalCheckout.setText(getString(R.string.TXPreco2,valorPrecoTotal));
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(ETQuantidadeCheckout.getWindowToken(),0);
                            handled = true;
                        }
                    }
                }
                return handled;
            }
        });

        ETQuantidadeCheckout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!ETQuantidadeCheckout.hasFocus()){
                    if (TextUtils.isEmpty(ETQuantidadeCheckout.getText())) {
                        Toast.makeText(PratoCheckout.this,getText(R.string.ERVazio),Toast.LENGTH_SHORT).show();
                        TVtotalCheckout.setText("");
                    }else{
                        Double quantidade = Double.parseDouble(ETQuantidadeCheckout.getText().toString());
                        if(quantidade!=null||quantidade!=0){
                            valorPrecoTotal = (valorPrecoPrato * quantidade);
                            TVtotalCheckout.setText(getString(R.string.TXPreco2,valorPrecoTotal));
                            }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view){
        if (view == BTConfirmar){
            AppConfirmaCheckout();
        }

    }   //Verifica o botão pressionado

    private void AppConfirmaCheckout(){

        if (valorPrecoTotal==null||valorPrecoTotal==0){
            Toast.makeText(PratoCheckout.this,"Insira uma quantidade",Toast.LENGTH_SHORT).show();
        }else{
            valorQuantidade = Double.parseDouble(ETQuantidadeCheckout.getText().toString());
            valorObservacao = ETCheckoutObservacao.getText().toString();
            ParseObject upload = ParseObject.create("Pedidos");
            upload.put("pedidosPratoNome",valorPratoSelecionado);
            upload.put("pedidosUserId", ParseUser.getCurrentUser().getObjectId());
            upload.put("pedidosUserNome", ParseUser.getCurrentUser().getUsername());
            upload.put("pedidosValor",valorPrecoTotal);
            upload.put("pedidosSituacao",valorSituacao);
            upload.put("pedidoObservacao", valorObservacao);
            upload.put("pedidosQuantidade",valorQuantidade);
            upload.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){

                        AppVoltarInicio();
                    }else{
                        Toast.makeText(PratoCheckout.this,e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void AppVoltarInicio(){
        if (!valorNivelContaUsuario){
            Toast.makeText(this,"Sucesso na criação do pedido",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MenuPrincipal.class);
            intent.putExtra("NivelConta",valorNivelContaUsuario);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                      //Limpa o stack de activities antes de voltar para limpar um pouco a memória
            startActivity(intent);
        }else {
            Toast.makeText(this, "Sucesso na criação do pedido", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MenuPrincipalADMIN.class);
            intent.putExtra("NivelConta", valorNivelContaUsuario);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                      //Limpa o stack de activities
            startActivity(intent);
        }
    }
}
