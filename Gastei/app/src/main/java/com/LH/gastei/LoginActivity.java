package com.LH.gastei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LoginActivity extends AppCompatActivity {

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);
    }

    public void FazerLogin(View view) {
        EditText editTextLogin = (EditText) findViewById(R.id.editLogin);
        EditText editTextSenha = (EditText) findViewById(R.id.editSenha);
        String login = editTextLogin.getText().toString();
        String senha = editTextSenha.getText().toString();

        if(login.equals("") || senha.equals("")) {
            Alert("Preencha todos os campos para fazer login!");
        }
        else {
            User user = new User();
            user.setLogin(login);
            user.setSenha(senha);

            if(db.ValidarLogin(user)) {
                Intent intent = new Intent(getApplicationContext(),HomeScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Login",login);

                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
            else Alert("Usuário ou senha incorretos");
        }
    }

    public void FazerRegistro(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        finish();
    }

    private void Alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
