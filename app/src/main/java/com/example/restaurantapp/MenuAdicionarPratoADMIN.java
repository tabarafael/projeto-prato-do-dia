package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Classe utilizada para criar novos pratos no servidor*/

public class MenuAdicionarPratoADMIN extends AppCompatActivity implements View.OnClickListener{
    private EditText ETNome;
    private EditText ETIngredientes;
    private EditText ETDescricao;
    private EditText ETPreco;
    private Button BTAdicionarImagem;
    private Button BTSalvar;
    private CheckBox CB1, CB2, CB3, CB4, CB5, CB6, CB7;
    private boolean ValorNivelContaUsuario;
    private ImageView IVPreview;
    private static final Integer SELECT_PHOTO =1;
    private Uri UriImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_adicionar_prato_admin);


        Intent intent = getIntent();
        ValorNivelContaUsuario  = intent.getBooleanExtra("NivelConta",false);


        ETNome = findViewById(R.id.ET_Nome_Adicionar);
        ETIngredientes = findViewById(R.id.ET_Ingredientes_Adicionar);
        ETDescricao = findViewById(R.id.ET_Descricao_Adicionar);
        ETPreco = findViewById(R.id.ET_Preco_Adicionar);
        BTAdicionarImagem = findViewById(R.id.BT_Adicionar_Imagem);
        IVPreview = findViewById(R.id.IV_Imagem_Preview);
        BTAdicionarImagem.setOnClickListener(this);
        BTSalvar = findViewById(R.id.BT_Salvar);
        BTSalvar.setOnClickListener(this);
        CB1= findViewById(R.id.CB_1);
        CB2= findViewById(R.id.CB_2);
        CB3= findViewById(R.id.CB_3);
        CB4= findViewById(R.id.CB_4);
        CB5= findViewById(R.id.CB_5);
        CB6= findViewById(R.id.CB_6);
        CB7= findViewById(R.id.CB_7);

        ETPreco.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5,2)});


    }

    public class DecimalDigitsInputFilter implements InputFilter{

        Pattern pattern;
        private DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero){
            pattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
        }
        @Override
        public CharSequence filter (CharSequence source, int start, int end, Spanned dest, int dssttart, int dend){
            Matcher matcher = pattern.matcher(dest);
            if(!matcher.matches())
                return "";
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == BTAdicionarImagem){
            AppAdicionarImagem();
        }else if (view == BTSalvar){
            if(AnyCheckBoxChecked()){
                if (CB1.isChecked()){
                    AppSalvarDados(1);
                }
                if (CB2.isChecked()){
                    AppSalvarDados(2);
                }
                if (CB3.isChecked()){
                    AppSalvarDados(3);
                }
                if (CB4.isChecked()){
                    AppSalvarDados(4);
                }
                if (CB5.isChecked()){
                    AppSalvarDados(5);
                }
                if (CB6.isChecked()){
                    AppSalvarDados(6);
                }
                if (CB7.isChecked()){
                    AppSalvarDados(7);
                }
            }else{
                Toast.makeText(this,"Marque pelo menos um dia",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void AppAdicionarImagem(){
        Intent intent = new Intent ();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Selecione uma Imagem"), SELECT_PHOTO);

    }  /* Cria intent para devolver uri do endereço de imagem no dispositivo*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            UriImagem = uri;
                try {
                    Bitmap bitmap = getThumbnail(uri,this);
                    IVPreview.setImageBitmap(bitmap);
                }catch (IOException e ){
                    Toast.makeText(this,"IO throw",Toast.LENGTH_LONG).show();
                }
        }
    }  /*Com o uri result do AppAdicionarImagem, carrrega a imagem e carrega no ImageView*/

    public static Bitmap getThumbnail(Uri uri, Context context) throws IOException{
        InputStream input = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth==-1)||(onlyBoundsOptions.outHeight ==-1)){
            return null;
        }
        int originalSize = (onlyBoundsOptions.outHeight>onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }  /*Transforma a imagem do dispositivo em bitmap*/

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    private boolean hasImage(ImageView view){
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable!=null);

        if (hasImage && (drawable instanceof BitmapDrawable)){
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }
        return hasImage;
    } /*Verifica se já foi carregada alguma imagem no ImageView*/

    private boolean AnyCheckBoxChecked(){
        boolean anyChecked = false;
        if(CB1.isChecked()){
            anyChecked = true;
        }
        if(CB2.isChecked()){
            anyChecked = true;
        }
        if(CB3.isChecked()){
            anyChecked = true;
        }
        if(CB4.isChecked()){
            anyChecked = true;
        }
        if(CB5.isChecked()){
            anyChecked = true;
        }
        if(CB6.isChecked()){
            anyChecked = true;
        }
        if(CB7.isChecked()){
            anyChecked = true;
        }
        return anyChecked;
    } /*Verifica se ao menos uma checkbox foi selecionada*/

    private void AppSalvarDados(Integer dia){
        final ProgressDialog pd = new ProgressDialog(MenuAdicionarPratoADMIN.this);
        pd.setMessage(getString(R.string.TXLoading));
        pd.setCancelable(false);
        pd.show();
        String valorNome=null;
        String valorIngredientes=null;
        String valorDescricao=null;
        Double valorPreco=null;
        try{
             valorNome = ETNome.getText().toString();
             valorIngredientes = ETIngredientes.getText().toString();
             valorDescricao = ETDescricao.getText().toString();
             valorPreco = Double.parseDouble(ETPreco.getText().toString());
        }catch (Exception e ){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }

        if(valorNome.isEmpty()||valorIngredientes.isEmpty()||valorDescricao.isEmpty()||valorPreco==null||valorPreco<=0||!hasImage(IVPreview)){
            Toast.makeText(this,"Verifique os campos",Toast.LENGTH_LONG).show();
            pd.dismiss();
        }else{
            ParseObject upload = ParseObject.create("Pratos");
            try {
                Bitmap bitmap = getThumbnail(UriImagem,this);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                byte[] image = stream.toByteArray();
                ParseFile file = new ParseFile(getFileName(UriImagem), image);
                file.saveInBackground();
                upload.put("PratoImagem",file);
            }catch (IOException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
            upload.put("PratoNome",valorNome);
            upload.put("PratoIngrediente",valorIngredientes);
            upload.put("PratoDescricao",valorDescricao);
            upload.put("PratoPreco",valorPreco);
            upload.put("PratoDia",dia);
            upload.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){
                        Toast.makeText(MenuAdicionarPratoADMIN.this,"Salvo com sucesso!",Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                    }else{
                        Toast.makeText(MenuAdicionarPratoADMIN.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });
        }
    }  /* Salva os dados no servidor*/

    public String getFileName(Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try{
                if (cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if (result==null){
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut !=-1){
                result = result.substring(cut + 1);
            }
        }
        return result;
    } /*Retorna o nome do arquivo de imagem para nomeá-lo corretamente antes de salvar*/
}
