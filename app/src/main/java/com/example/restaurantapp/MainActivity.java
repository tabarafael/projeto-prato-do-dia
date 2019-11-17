package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;


import com.parse.GetCallback;
import com.parse.LogInCallback;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button BTentrar;
    private Button BTcadastro;
    private Button BTrecuperarsenha;
    private EditText ETusuario;
    private EditText ETsenha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BTentrar = findViewById(R.id.BT_Entrar);
        BTentrar.setOnClickListener(this);
        BTcadastro = findViewById(R.id.BT_Cadastro);
        BTcadastro.setOnClickListener(this);
        BTrecuperarsenha = findViewById(R.id.BT_Recuperar_Senha);
        BTrecuperarsenha.setOnClickListener(this);
        ETusuario = findViewById(R.id.ET_Usuario);
        ETsenha = findViewById(R.id.ET_Senha);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        ETsenha.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if(i == EditorInfo.IME_ACTION_DONE){
                    if(!AppVerificaVazio()){
                        AppEntrar();
                        handled = true;
                    }
                }
                return handled;
            }
        });
    }  //Set de listeners nos botões

    @Override
    public void onClick(View view){
        if (view == BTentrar){
            AppEntrar();
        }else if (view == BTcadastro){
            AppCadastro();
        }else if(view == BTrecuperarsenha){
            AppRecuperar();
        }
    }            //Verifica o botão selecionado

    private void AppEntrar(){
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage(getString(R.string.TXLoading));
        pd.setCancelable(false);
        pd.show();
        final String usuario = ETusuario.getText().toString();
        String senha = ETsenha.getText().toString();

        if(TextUtils.isEmpty(usuario)) {
            ETusuario.setError(getString(R.string.ERUserVazio));
            pd.dismiss();
        }else if (TextUtils.isEmpty(senha)){
            ETsenha.setError(getString(R.string.ERSenhaVazio));
            pd.dismiss();
        }else{
            ParseUser.logInInBackground(usuario,senha, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null) {
                       new  AlertDialog.Builder(MainActivity.this)
                               .setTitle("Login")
                               .setMessage("Login com sucesso")
                               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {
                                            pd.dismiss();
                                             AppIniciaMenu();
                                   }
                               }).show();
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        pd.dismiss();
                    }
                }
            });

        }
    }

    private void AppCadastro(){
        Intent intent = new Intent(this, MenuCadastro.class);
        startActivity(intent);
    }          //Redireciona para criar um cadastro, **Em construção

    private void AppRecuperar(){
        Intent intent = new Intent(this, MenuSenha.class);
        startActivity(intent);
    }          //Redireciona para recuperar senha  **Em construção

    private void AppIniciaMenu(){

        ParseUser curentuser = ParseUser.getCurrentUser();
        String userID = curentuser.getObjectId();
        if (userID != null){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
            query.whereEqualTo("objectId", userID);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e==null){
                        Boolean NivelAdmin = object.getBoolean("NivelAdmin");
                        if (NivelAdmin){
                            Intent intent = new Intent(MainActivity.this, MenuPrincipalADMIN.class);
                            intent.putExtra("NivelConta", NivelAdmin);
                            finish();
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                            intent.putExtra("NivelConta", NivelAdmin);
                            finish();
                            startActivity(intent);
                        }

                    }
                }
            });
        }
    }          //Redireciona o usuário para o menu apropriado ao seu nível,

    private boolean AppVerificaVazio(){
        boolean status = false;
        String usuario = ETusuario.getText().toString();

        if(TextUtils.isEmpty(usuario)){
            status = true;

            return status;
        }
        return status;
    }
}
