package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

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

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditarEditorPratosADMIN extends AppCompatActivity implements View.OnClickListener{

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
    private String NomeImagem;
    private ParseFile parseFileImagem;
    private int diaOriginal=0;
    private ParseObject originalObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_editor_pratos_admin);

        Intent intent = getIntent();
        ValorNivelContaUsuario = intent.getBooleanExtra("NivelConta",false);
        String PratoSelecionado = intent.getStringExtra("PratoSelecionado");


        ETNome = findViewById(R.id.ET_Nome_Adicionar);
        ETIngredientes = findViewById(R.id.ET_Ingredientes_Adicionar);
        ETDescricao = findViewById(R.id.ET_Descricao_Adicionar);
        ETPreco = findViewById(R.id.ET_Preco_Adicionar);
        BTAdicionarImagem = findViewById(R.id.BT_Adicionar_Imagem);
        IVPreview = findViewById(R.id.IV_Imagem_Preview);
        BTAdicionarImagem.setOnClickListener(this);
        BTSalvar = findViewById(R.id.BT_Confirma);
        BTSalvar.setOnClickListener(this);
        CB1= findViewById(R.id.CB_1);
        CB2= findViewById(R.id.CB_2);
        CB3= findViewById(R.id.CB_3);
        CB4= findViewById(R.id.CB_4);
        CB5= findViewById(R.id.CB_5);
        CB6= findViewById(R.id.CB_6);
        CB7= findViewById(R.id.CB_7);
        ETPreco.setFilters(new InputFilter[]{new EditarEditorPratosADMIN.DecimalDigitsInputFilter(5,2)});
        AppFillGaps(PratoSelecionado);

    }

    private void AppFillGaps(String IdPratoSelecionado){
        ParseQuery<ParseObject> query = new ParseQuery<>("Pratos");
        query.whereEqualTo("objectId", IdPratoSelecionado);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    try{
                        String valorNome = object.getString("PratoNome");
                        String valorIngrediente = object.getString("PratoIngrediente");
                        String valorDescricaoo = object.getString("PratoDescricao");
                        Integer valorDia = object.getInt("PratoDia");
                        Double valorPreco = object.getDouble("PratoPreco");

                        ETNome.setText(valorNome);
                        ETIngredientes.setText(valorIngrediente);
                        ETDescricao.setText(valorDescricaoo);
                        ETPreco.setText(valorPreco.toString());

                        switch (valorDia){
                            case 1:
                                CB1.setChecked(true);
                                break;
                            case 2:
                                CB2.setChecked(true);
                                break;
                            case 3:
                                CB3.setChecked(true);
                                break;
                            case 4:
                                CB4.setChecked(true);
                                break;
                            case 5:
                                CB5.setChecked(true);
                                break;
                            case 6:
                                CB6.setChecked(true);
                                break;
                            case 7:
                                CB7.setChecked(true);
                                break;
                        }

                        ParseFile imagem = (ParseFile) object.get("PratoImagem");
                        parseFileImagem = imagem;
                        originalObject = object;
                        diaOriginal = valorDia;
                        NomeImagem = imagem.getName();
                        imagem.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e==null){
                                    Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);
                                    IVPreview.setImageBitmap(bmp);
                                }
                            }
                        });
                    }catch (Exception f){
                        Toast.makeText(EditarEditorPratosADMIN.this,f.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(EditarEditorPratosADMIN.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public class DecimalDigitsInputFilter implements InputFilter{

        Pattern pattern;
        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero){
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

    }

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
    }

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
    }

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
    }

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
    }

    private void AppSalvarDados(Integer dia){

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
        }

        if(valorNome.isEmpty()||valorIngredientes.isEmpty()||valorDescricao.isEmpty()||valorPreco==null||valorPreco<=0||!hasImage(IVPreview)){
            Toast.makeText(this,"Verifique os campos",Toast.LENGTH_LONG).show();
        }else{
            ParseObject upload;
            if (diaOriginal==dia||ContaDias()==1){
                upload = originalObject;
            }else{
                upload = ParseObject.create("Pratos");
            }
            try {
                String fileName;
                if(UriImagem!=null){
                    fileName = getFileName(UriImagem);
                }else{
                    fileName=NomeImagem;
                }
                if (UriImagem!=null){
                    Bitmap bitmap = getThumbnail(UriImagem,this);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                    byte[] image = stream.toByteArray();
                    ParseFile file = new ParseFile(fileName, image);
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(EditarEditorPratosADMIN.this,"325",Toast.LENGTH_SHORT).show();
                        }
                    });
                    upload.put("PratoImagem",file);
                }else{
                    upload.put("PratoImagem",parseFileImagem);
                }
            }catch (IOException e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(EditarEditorPratosADMIN.this,"Salvo com sucesso!",Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(EditarEditorPratosADMIN.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

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
    }

    private int ContaDias(){
        int totalDias = 0;
        if(CB1.isChecked()){
            totalDias++;
        }
        if(CB2.isChecked()){
            totalDias++;
        }
        if(CB3.isChecked()){
            totalDias++;
        }
        if(CB4.isChecked()){
            totalDias++;
        }
        if(CB5.isChecked()){
            totalDias++;
        }
        if(CB6.isChecked()){
            totalDias++;
        }
        if(CB7.isChecked()){
            totalDias++;
        }
        return totalDias;
    }
}
