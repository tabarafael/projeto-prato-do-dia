package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MenuCadastro extends AppCompatActivity implements View.OnClickListener{

    private Button BTCancela;
    private Button BTConfirma;
    private EditText ETNewUsuario;
    private EditText ETNome;
    private EditText ETNewEmail;
    private EditText ETConfEmail;
    private EditText ETNewSenha;
    private EditText ETConfSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cadastro);

        BTCancela = findViewById(R.id.BT_Cancela);
        BTCancela.setOnClickListener(this);
        BTConfirma = findViewById(R.id.BT_Confirma);
        BTConfirma.setOnClickListener(this);
        ETConfEmail = findViewById(R.id.ET_Conf_Email);
        ETConfSenha = findViewById(R.id.ET_Conf_Senha);
        ETNewSenha = findViewById(R.id.ET_New_Senha);
        ETNewUsuario = findViewById(R.id.ET_New_Usuario);
        ETNome = findViewById(R.id.ET_Nome);
        ETNewEmail = findViewById(R.id.ET_New_Email);
    }  //Listeners dos botões

    @Override
    public void onClick(View view){
        if (view == BTCancela){
            AppCancela();
        }
        if (view == BTConfirma){
            AppConfirma();
        }

    }           //Olha qual botão foi pressionado
    @Override
    public void onBackPressed() {
        AppCancela();
    }       //Desvia o botão "back" para o AppCancela()

    private void AppCancela(){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.TXCancelar))
                .setCancelable(false)
                .setPositiveButton((getString(R.string.TXSim)), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MenuCadastro.super.onBackPressed();
                    }
                })
                .setNegativeButton((getString(R.string.TXNao)), null)
                .show();
    }     //aviso para garantir que o usuário não saia acidentalemnte

    private void AppConfirma(){

        String NewUsuario = ETNewUsuario.getText().toString();
        String Nome = ETNome.getText().toString();
        String NewEmail = ETNewEmail.getText().toString();                 //Recebe os dados inseridos no App
        String ConfEmail = ETConfEmail.getText().toString();
        String NewSenha = ETNewSenha.getText().toString();
        String ConfSenha = ETConfSenha.getText().toString();

        if(TextUtils.isEmpty(NewUsuario)) {
            ETNewUsuario.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(Nome)) {
            ETNome.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(NewEmail)) {
            ETNewEmail.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(ConfEmail)) {                      //Verifica se estão vazios e cria um erro caso sim.
            ETConfEmail.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(NewSenha) || (NewSenha.length()<8)) {
            new AlertDialog.Builder(this).setMessage(getString(R.string.ERSenhaFraca)).show();
        } else if(TextUtils.isEmpty(ConfSenha)) {
            ETConfSenha.setError(getString(R.string.ERVazio));
        } else {
            if(!NewEmail.equals(ConfEmail)){
                new AlertDialog.Builder(this).setMessage(getString(R.string.EREmailDif)).show();   //Verifica se os dois E-mails são iguais
            }else if (!NewSenha.equals(ConfSenha)){
                new AlertDialog.Builder(this).setMessage(getString(R.string.ERSenhaDif)).show();    //Verifica se as duas senhas são iguais
            }else {
                final ProgressDialog pd = new ProgressDialog(MenuCadastro.this);
                pd.setMessage(getString(R.string.TXLoading));
                pd.setCancelable(false);
                pd.show();
                ParseUser user = new ParseUser();
                user.setUsername(NewUsuario);
                user.setEmail(NewEmail);
                user.setPassword(NewSenha);
                user.put("Name",Nome);
                user.put("NivelAdmin",false);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            pd.dismiss();
                            new AlertDialog.Builder(MenuCadastro.this)
                                    .setMessage(getString(R.string.TXCadastroSuc))
                                    .setCancelable(false)
                                    .setPositiveButton((getString(R.string.TXOK)), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                            ParseUser.logOut();
                                        }
                                    })
                                    .show();
                        } else {
                            pd.dismiss();
                            ParseUser.logOut();
                            Toast.makeText(MenuCadastro.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        }

    }     //Verifica os espaços e valida o usuário, ainda não funciona
}
