package com.LH.gastei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class HomeScreenActivity extends AppCompatActivity {

    Intent intentGet;
    Bundle parametros;
    String loginUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        intentGet = getIntent();
        parametros = intentGet.getExtras();

        if(parametros != null) {
            loginUsed = parametros.getString("Login");
        }
    }

    public void IncluirGasto(View view) {
        Intent intentPut = new Intent(getApplicationContext(),IncluirGastoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login",loginUsed);

        intentPut.putExtras(bundle);

        startActivity(intentPut);
        finish();
    }

    public void ConsultarGastos(View view) {
        Intent intentPut = new Intent(getApplicationContext(),ConsultarGastosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login",loginUsed);

        intentPut.putExtras(bundle);

        startActivity(intentPut);
        finish();
    }
}
