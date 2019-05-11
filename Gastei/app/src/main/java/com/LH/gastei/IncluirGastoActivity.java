package com.LH.gastei;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class IncluirGastoActivity extends AppCompatActivity {

    Intent intentGet;
    Bundle parametros;
    String loginUsed;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private String dataCompra, generoCompra, formaPagamento,classificacaoGasto,descreverGasto, valorGasto;

    EditText edtValorGasto,edtDescreverGasto;
    Spinner spGeneroCompra,spFormaPagamento,spClassificacaoGasto;
    TextView txDataCompra;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_gasto);

        db = new DBHelper(this);
        txDataCompra = findViewById(R.id.txdDataCompra);
        ConfigSpinners();

        intentGet = getIntent();
        parametros = intentGet.getExtras();

        loginUsed = parametros.getString("Login");


       //Criando o click para inserir a data
        txDataCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar call = Calendar.getInstance();
                int dia = call.get(Calendar.DAY_OF_MONTH);
                int mes = call.get(Calendar.MONTH);
                int ano = call.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(IncluirGastoActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,ano,mes,dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //Salvando a data dps do ok do calendario
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;

                String data;

                if (month < 10 && dayOfMonth < 10) {
                    data = "0" + dayOfMonth + "/" + "0" + month + "/" + year;
                } else if (month < 10 && dayOfMonth > 9) {
                    data = dayOfMonth + "/" + "0" + month + "/" + year;
                } else if (month > 9 && dayOfMonth < 10) {
                    data = "0" + dayOfMonth + "/" + "0" + month + "/" + year;
                } else {
                    data = dayOfMonth + "/" + month + "/" + year;
                }
                txDataCompra.setText(data);
            }
        };


    }

    public void Voltar(View view) {
        Intent intentPut = new Intent(getApplicationContext(),HomeScreenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login",loginUsed);

        intentPut.putExtras(bundle);

        startActivity(intentPut);
        finish();
    }

    public void ConfigSpinners() {
        Spinner generoCompras = findViewById(R.id.spGeneroCompra);
        ArrayAdapter<CharSequence> aGP = ArrayAdapter.createFromResource(this,R.array.GenerosCompra,android.R.layout.simple_spinner_item);
        aGP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generoCompras.setAdapter(aGP);

        Spinner formaPagamento = findViewById(R.id.spFormaPagamento);
        ArrayAdapter<CharSequence> aFP = ArrayAdapter.createFromResource(this,R.array.FormaPagamento,android.R.layout.simple_spinner_item);
        aFP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        formaPagamento.setAdapter(aFP);

        Spinner classificacaoGasto = findViewById(R.id.spClassificacaoGasto);
        ArrayAdapter<CharSequence> aCG = ArrayAdapter.createFromResource(this,R.array.ClassificacaoGasto,android.R.layout.simple_spinner_item);
        aCG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classificacaoGasto.setAdapter(aCG);
    }

    public void AdicionarGasto(View view) {
        PegarCampos();
        if(TestarCampos())
        {
            Gasto gasto = new Gasto();
            gasto.setUsedLogin(loginUsed);
            gasto.setDataCompra(dataCompra);
            gasto.setFormaPagamento(formaPagamento);
            gasto.setDescreverGasto(descreverGasto);
            gasto.setValorGasto(valorGasto);
            gasto.setClassificacaoGasto(classificacaoGasto);
            gasto.setGeneroDespesas(generoCompra);

            long res = db.CreateGasto(gasto);
            if(res >0)
            {
                Alerta("Gasto adicionado com sucesso!");
                Intent intentPut = new Intent(getApplicationContext(),HomeScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Login",loginUsed);

                intentPut.putExtras(bundle);

                startActivity(intentPut);
                finish();
            }
            else
                Alerta("Problemas ao criar gasto");
        }

    }

    public void PegarCampos() {
        txDataCompra = findViewById(R.id.txdDataCompra);
        edtDescreverGasto = findViewById(R.id.edtDescreverGasto);
        edtValorGasto = findViewById(R.id.edtValorGasto);

        spClassificacaoGasto = findViewById(R.id.spClassificacaoGasto);
        spFormaPagamento = findViewById(R.id.spFormaPagamento);
        spGeneroCompra = findViewById(R.id.spGeneroCompra);

        dataCompra = txDataCompra.getText().toString();
        valorGasto = edtValorGasto.getText().toString();
        descreverGasto = edtDescreverGasto.getText().toString();

        classificacaoGasto = spClassificacaoGasto.getSelectedItem().toString();
        formaPagamento = spFormaPagamento.getSelectedItem().toString();
        generoCompra = spGeneroCompra.getSelectedItem().toString();
    }

    public boolean TestarCampos() {
        if(dataCompra.equals("Selecionar a data"))
        {
            Alerta("Selecione a data da Compra");
            return false;
        }
        else if(generoCompra.equals("Selecionar genero da compra"))
        {
            Alerta("Selecione o gênero da compra");
            return false;
        }
        else if(valorGasto.equals(""))
        {
            Alerta("Digite o valor gasto");
            return false;
        }
        else if(formaPagamento.equals("Selecionar forma de Pagamento"))
        {
            Alerta("Selecione a forma de pagamento");
            return false;
        }
        else if(classificacaoGasto.equals("Selecionar a classificação do gasto"))
        {
            Alerta("Selecione classificação do gasto");
            return false;
        }
        else
        {
            return true;
        }
    }

    public void Alerta(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
