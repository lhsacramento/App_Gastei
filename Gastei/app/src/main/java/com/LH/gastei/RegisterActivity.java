package com.LH.gastei;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText edtLogin,edtSenha,edtConfirmarSenha;
    String login,senha,confirmarSenha;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        View overlay = findViewById(R.id.ActivityRegister);
        overlay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        db = new DBHelper(this);
    }

    public void SaveUser(View view) {
        PegarCampos();
        if(TestarCampos())
        {
            User user = new User();
            user.setLogin(login);
            user.setSenha(senha);
            long res = db.CreateUsers(user);
            if(res > 0) {
                Alerta("Usuario Registrado com sucesso");
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
            else
                Alerta("Erro ao criar usuario");
        }
    }

    public void Alerta(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    public void PegarCampos() {
        edtLogin = (EditText)findViewById(R.id.editTextLoginRegister);
        edtSenha = (EditText)findViewById(R.id.editTextSenhaRegister);
        edtConfirmarSenha = (EditText)findViewById(R.id.editTextRepetirSenhaRegister);

        login = edtLogin.getText().toString();
        senha = edtSenha.getText().toString();
        confirmarSenha = edtConfirmarSenha.getText().toString();
    }

    public boolean TestarCampos() {
        String test = login.replace(" ","");

        if(login.equals("") || senha.equals("") || confirmarSenha.equals(""))
        {
            Alerta("Preencha todos os campos!");
            return false;
        }
        else if(!senha.equals(confirmarSenha))
        {
            Alerta("As senhas não conferem");
            return false;
        }
        else if(login.length() < 5)
        {
            Alerta("O login deve ter no minimo 5 caracteres!");
            return false;
        }
        else if(senha.length() < 6)
        {
            Alerta("A senha deve ter no minimo 6 caracteres!");
            return false;
        }
        else if(!login.matches("[a-zA-Z0-9_].*"))
        {
            Alerta("O login nao pode conter caracteres especiais");
            return false;
        }
        else if(!login.equals(test))
        {
            Alerta("O login nao pode conter espaços");
            return false;
        }
        else
        {
            return true;
        }
    }

    public void Voltar(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}
