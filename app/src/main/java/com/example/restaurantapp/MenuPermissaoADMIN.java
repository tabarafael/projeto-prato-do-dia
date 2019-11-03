package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MenuPermissaoADMIN extends AppCompatActivity implements View.OnClickListener{

    Button BTConfirma;
    EditText ETUsuario;
    EditText ETSenha;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_permissao_admin);

        spinner = findViewById(R.id.SpinnerAdmin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SANivelADMIN, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        BTConfirma = findViewById(R.id.BT_Confirma);
        BTConfirma.setOnClickListener(this);
        ETUsuario = findViewById(R.id.ET_Usuario_Modificar);
        ETSenha = findViewById(R.id.ET_Usuario_Senha_Modificar);
    }

    @Override
    public void onClick(View view) {
        if (view == BTConfirma){
            AppConfirma();
        }
    }

    private void AppConfirma(){
        final String usuario = ETUsuario.getText().toString().trim();
        final String senha = ETSenha.getText().toString().trim();
        if (TextUtils.isEmpty(usuario)){
            ETUsuario.setError(getString(R.string.ERUserVazio));
        }else if (TextUtils.isEmpty(senha)){
            ETSenha.setError(getString(R.string.ERUserVazio));
        }else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
            query.whereEqualTo("username",usuario);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    final ParseObject object = objects.get(0);
                    if (e == null){
                        if (spinner.getSelectedItem().toString().equals("Administrador")){
                            ParseUser.logInInBackground(usuario,senha, new LogInCallback() {
                                @Override
                                public void done(ParseUser parseUser, ParseException e) {
                                    if (parseUser != null) {
                                        object.put("NivelAdmin",true);
                                        object.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e==null){
                                                    new  AlertDialog.Builder(MenuPermissaoADMIN.this)
                                                            .setMessage("Permissão modificada com sucesso.")
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    AppLimpar();
                                                                }
                                                            }).show();
                                                }
                                            }
                                        });
                                        ParseUser.logOut();
                                    } else {
                                        Toast.makeText(MenuPermissaoADMIN.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        ParseUser.logOut();
                                    }
                                }
                            });
                        }else if (spinner.getSelectedItem().toString().equals("Cliente")) {
                            ParseUser.logInInBackground(usuario,senha, new LogInCallback() {
                                @Override
                                public void done(ParseUser parseUser, ParseException e) {
                                    if (parseUser != null) {
                                        object.put("NivelAdmin",false);
                                        object.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if (e==null){
                                                    new  AlertDialog.Builder(MenuPermissaoADMIN.this)
                                                            .setMessage("Permissão modificada com sucesso.")
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    AppLimpar();
                                                                }
                                                            }).show();
                                                }
                                            }
                                        });
                                        ParseUser.logOut();
                                    } else {
                                        Toast.makeText(MenuPermissaoADMIN.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        ParseUser.logOut();
                                    }
                                }
                            });
                        }
                    }else {
                        Toast.makeText(MenuPermissaoADMIN.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void AppLimpar(){
        ETSenha.setText("");
        ETUsuario.setText("");
        spinner.setSelection(0);
    }

}
