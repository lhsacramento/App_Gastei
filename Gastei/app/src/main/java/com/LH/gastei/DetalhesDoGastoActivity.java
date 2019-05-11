package com.LH.gastei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DetalhesDoGastoActivity extends AppCompatActivity {

    Intent intentGet;
    Bundle parametros;

    String dataCompra,generoGasto,classificacaoGasto,valorGasto,formaPagamento,descricaoCompra,loginUsed;
    TextView _dataCompra,_generoGasto,_classificacaoGasto,_valorGasto,_formaPagamento,_descricaoCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_do_gasto);

        intentGet = getIntent();
        parametros = intentGet.getExtras();

        _dataCompra = findViewById(R.id.txUserDataCompra);
        _generoGasto = findViewById(R.id.txUserGeneroGasto);
        _classificacaoGasto = findViewById(R.id.txUserClassificacaoGasto);
        _valorGasto = findViewById(R.id.txUserValorGasto);
        _formaPagamento = findViewById(R.id.txUserFormaPagamento);
        _descricaoCompra = findViewById(R.id.txUserDescricaoGasto);

        if(parametros != null)
        {
            loginUsed = parametros.getString("Login");
            dataCompra = parametros.getString("DataCompra");
            generoGasto = parametros.getString("GeneroGasto");
            classificacaoGasto = parametros.getString("ClassificacaoGasto");
            valorGasto = parametros.getString("ValorGasto");
            formaPagamento = parametros.getString("FormaPagamento");
            descricaoCompra = parametros.getString("DescricaoGasto");
        }

        MostrarDetalhesDoGasto();
    }

    public void MostrarDetalhesDoGasto()
    {
        _dataCompra.setText(dataCompra);
        _classificacaoGasto.setText(classificacaoGasto);
        _descricaoCompra.setText(descricaoCompra);
        _generoGasto.setText(generoGasto);
        _valorGasto.setText("R$" + valorGasto);
        _formaPagamento.setText(formaPagamento);
    }

    public void Voltar(View view)
    {
        Intent intentPut = new Intent(getApplicationContext(),ConsultarGastosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login",loginUsed);
        intentPut.putExtras(bundle);

        startActivity(intentPut);
        finish();
    }
}
