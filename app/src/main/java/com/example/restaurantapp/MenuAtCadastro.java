package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.List;

/**Menu para a atualização de cadastros já criados, baixa os daddos salvos e se modificados, atualiza no servidor*/

public class MenuAtCadastro extends AppCompatActivity implements View.OnClickListener{

    private Button BTCancela;
    private Button BTConfirma;
    private Button BTSenha;
    private EditText ETNewUsuario;
    private EditText ETNome;
    private EditText ETNewEmail;
    private EditText ETConfEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_at_cadastro);

        BTCancela = findViewById(R.id.BT_Cancela);
        BTCancela.setOnClickListener(this);
        BTConfirma = findViewById(R.id.BT_Confirma);
        BTConfirma.setOnClickListener(this);
        BTSenha = findViewById(R.id.BT_Modificar_Senha);
        BTSenha.setOnClickListener(this);
        ETConfEmail = findViewById(R.id.ET_Conf_Email);
        ETNewUsuario = findViewById(R.id.ET_New_Usuario);
        ETNome = findViewById(R.id.ET_Nome);
        ETNewEmail = findViewById(R.id.ET_New_Email);

        GetDadosUsuario();

    }  //Listeners dos botões

    @Override
    public void onClick(View view){
        if (view == BTCancela){
            AppCancela();
        }else if (view == BTConfirma){
            AppConfirma();
        }else if (view == BTSenha){
            AppModSenha();
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
                        MenuAtCadastro.super.onBackPressed();
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

        if(TextUtils.isEmpty(NewUsuario)) {
            ETNewUsuario.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(Nome)) {
            ETNome.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(NewEmail)) {
            ETNewEmail.setError(getString(R.string.ERVazio));
        } else if(TextUtils.isEmpty(ConfEmail)) {                      //Verifica se estão vazios e cria um erro caso sim.
            ETConfEmail.setError(getString(R.string.ERVazio));
        } else {
            if(!NewEmail.equals(ConfEmail)){
                new AlertDialog.Builder(this).setMessage(getString(R.string.EREmailDif)).show();   //Verifica se os dois E-mails são iguais
            }else {
                final ProgressDialog pd = new ProgressDialog(MenuAtCadastro.this);
                pd.setMessage(getString(R.string.TXLoading));
                pd.setCancelable(false);
                pd.show();
                ParseUser user = new ParseUser();
                user.setUsername(NewUsuario);
                user.setEmail(NewEmail);
                user.put("Name",Nome);
                user.setObjectId(ParseUser.getCurrentUser().getObjectId());
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            pd.dismiss();
                        }else{
                            Toast.makeText(MenuAtCadastro.this, e.getMessage(),Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }
                    }
                });


            }

        }

    }     //Verifica os espaços e valida o usuário

    private void GetDadosUsuario(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ETNome.setText(objects.get(0).getString("username"));
                ETNewUsuario.setText(objects.get(0).getString("username"));
                ETConfEmail.setText(objects.get(0).getString("email"));
                ETNewEmail.setText(objects.get(0).getString("email"));
            }
        });

    }  //Baixa os dados do servidor e insere nos edittext

    private void AppModSenha(){
        Intent intent = new Intent(MenuAtCadastro.this,MenuSenha.class);
        startActivity(intent);
    } //Redireciona para a activity de recuperar senha
}
