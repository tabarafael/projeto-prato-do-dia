package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;


import com.parse.GetCallback;
import com.parse.LogInCallback;

import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
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

        BTentrar = findViewById(R.id.BTentrar);
        BTentrar.setOnClickListener(this);
        BTcadastro = findViewById(R.id.BTcadastro);
        BTcadastro.setOnClickListener(this);
        BTrecuperarsenha = findViewById(R.id.BTrecuperarsenha);
        BTrecuperarsenha.setOnClickListener(this);
        ETusuario = findViewById(R.id.ETusuario);
        ETsenha = findViewById(R.id.ETsenha);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }  //Set de listeners nos botões

    @Override
    public void onClick(View view){
        if (view == BTentrar){
            AppEntrar();
        }
        if (view == BTcadastro){
            AppCadastro();
        }
        if(view == BTrecuperarsenha){
            AppRecuperar();
        }
    }            //Verifica o botão selecionado

    private void AppEntrar(){
        final String usuario = ETusuario.getText().toString();
        String senha = ETsenha.getText().toString();

        if(TextUtils.isEmpty(usuario)) {
            ETusuario.setError(getString(R.string.ERUserVazio));
        }else if (TextUtils.isEmpty(senha)){
            ETsenha.setError(getString(R.string.ERSenhaVazio));
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

                                             AppIniciaMenu();



                                   }
                               }).show();
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                        if (NivelAdmin == true){
                            Intent intent = new Intent(MainActivity.this, MenuPrincipalADMIN.class);
                            intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA, NivelAdmin);
                            finish();
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(MainActivity.this, MenuPrincipal.class);
                            intent.putExtra(MenuAdmin.PARAMETRO_NIVEL_CONTA, NivelAdmin);
                            finish();
                            startActivity(intent);
                        }

                    }

                }
            });


        }else {

        }

    }          //Redireciona o usuário para o menu apropriado ao seu nível,
}
