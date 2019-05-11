package com.LH.gastei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ResumoGastosActivity extends AppCompatActivity {

    Intent intentGet;
    Bundle parametros;

    String loginUsed, filtro;

    ArrayList<String> generoGastos,classificacaoGastos,formaPagamento;

    TextView txResumoFormaPagamento,txResumoGenero,txResumoClassificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_gastos);

        generoGastos = new ArrayList<>();
        classificacaoGastos = new ArrayList<>();
        formaPagamento = new ArrayList<>();

        txResumoClassificacao = findViewById(R.id.txResumoClassificacao);
        txResumoGenero = findViewById(R.id.txResumoGenero);
        txResumoFormaPagamento = findViewById(R.id.txResumoFormaPagamento);

        intentGet = getIntent();
        parametros = intentGet.getExtras();

        if(parametros != null)
        {
            loginUsed = parametros.getString("Login");
            filtro = parametros.getString("Filtro");
            generoGastos = parametros.getStringArrayList("ResumoGeneroGastos");
            classificacaoGastos = parametros.getStringArrayList("ResumoClassificacaoGastos");
            formaPagamento = parametros.getStringArrayList("ResumoFormaPagamento");
        }

        TextView name = findViewById(R.id.txFiltro);
        name.setText("(" + filtro + ")");

        CalcularGeneroGasto();
        CalcularClassificacao();
        CalcularFormaPagamento();
    }

    public void CalcularGeneroGasto(){
        double despesasFixas = 0,combustivel = 0,passagem = 0,lanches = 0,lazer = 0, total = 0;
        double p_despesasFixas = 0,p_combustivel = 0,p_passagem = 0,p_lanches = 0,p_lazer = 0;
        DecimalFormat df = new DecimalFormat("#0.00");

        for(int i = 0;i < generoGastos.size();i++)
        {
            String[] s = new String[2];
            s = generoGastos.get(i).split("/");
            switch (s[1])
            {
                case "Despesas Fixas":
                    despesasFixas += Long.parseLong(s[0]);
                    break;

                case "Combustivel":
                    combustivel += Long.parseLong(s[0]);
                    break;

                case "Passagem":
                    passagem += Long.parseLong(s[0]);
                    break;

                case "Lazer":
                    lazer += Long.parseLong(s[0]);
                    break;

                case "Lanches":
                    lanches += Long.parseLong(s[0]);
                    break;
            }
        }

        total = despesasFixas + combustivel + passagem + lanches + lazer;
        p_despesasFixas = (despesasFixas * 100) / total;
        p_combustivel = (combustivel * 100) / total;
        p_passagem = (passagem * 100) / total;
        p_lanches = (lanches * 100) / total;
        p_lazer = (lazer * 100) / total;

        txResumoGenero.setText("Despesas Fixas - " + df.format(p_despesasFixas) + "% - " + "R$" + despesasFixas + "\n"+
                               "Combustível - " + df.format(p_combustivel) + "% - " + "R$" + combustivel + "\n"+
                               "Passagem - " + df.format(p_passagem) + "% - " + "R$" + passagem + "\n"+
                               "Lanches - " + df.format(p_lanches) + "% - " + "R$" + lanches + "\n" +
                               "Lazer - " + df.format(p_lazer) + "% - " + "R$" + lazer);
    }

    public void CalcularClassificacao(){
        double importante = 0, poucoImportante = 0, desnecessario = 0, total = 0;
        double p_importante = 0, p_poucoImportante = 0, p_desnecessario = 0;
        DecimalFormat df = new DecimalFormat("#0.00");

        for (int i = 0; i < classificacaoGastos.size(); i++) {
            String[] s = new String[2];
            s = classificacaoGastos.get(i).split("/");
            switch (s[1]) {
                case "Importante":
                    importante += Long.parseLong(s[0]);
                    break;

                case "Pouco importante":
                    poucoImportante += Long.parseLong(s[0]);
                    break;

                case "Desnecessario":
                    desnecessario += Long.parseLong(s[0]);
                    break;
            }
        }

        total = importante + poucoImportante + desnecessario;
        p_importante = (importante * 100) / total;
        p_poucoImportante = (poucoImportante * 100) / total;
        p_desnecessario = (desnecessario * 100) / total;

        txResumoClassificacao.setText("Importante - " + df.format(p_importante) + "% - " + "R$" + importante + "\n"+
                "Pouco Importante - " + df.format(p_poucoImportante) + "% - " + "R$" + poucoImportante + "\n"+
                "Desnecessário - " + df.format(p_desnecessario) + "% - " + "R$" + desnecessario);
    }

    public void CalcularFormaPagamento(){

        double dinheiro = 0,cartaoDebito = 0,boleto = 0,cartaoCredito = 0,debitoAutomatico = 0, total = 0;
        double p_dinheiro = 0,p_cartaoDebito = 0,p_boleto = 0,p_cartaoCredito = 0,p_debitoAutomatico = 0;
        DecimalFormat df = new DecimalFormat("#0.00");

        for(int i = 0;i < formaPagamento.size();i++)
        {
            String[] s = new String[2];
            s = formaPagamento.get(i).split("/");
            switch (s[1])
            {
                case "Dinheiro":
                    dinheiro += Long.parseLong(s[0]);
                    break;

                case "Cartao Debito":
                    cartaoDebito += Long.parseLong(s[0]);
                    break;

                case "Boleto":
                    boleto += Long.parseLong(s[0]);
                    break;

                case "Cartao de Credito":
                    cartaoCredito += Long.parseLong(s[0]);
                    break;

                case "Débito Automatico":
                    debitoAutomatico += Long.parseLong(s[0]);
                    break;
            }
        }

        total = dinheiro + cartaoDebito + cartaoCredito + boleto + debitoAutomatico;
        p_dinheiro = (dinheiro * 100) / total;
        p_cartaoCredito = (cartaoCredito * 100) / total;
        p_cartaoDebito = (cartaoDebito * 100) / total;
        p_boleto = (boleto * 100) / total;
        p_debitoAutomatico = (debitoAutomatico * 100) / total;

        txResumoFormaPagamento.setText("Dinheiro - " + df.format(p_dinheiro) + "% - " + "R$" + dinheiro + "\n"+
                "Cartão de Crédito - " + df.format(p_cartaoCredito) + "% - " + "R$" + cartaoCredito + "\n"+
                "Cartão de Débito - " + df.format(p_cartaoDebito) + "% - " + "R$" + cartaoDebito + "\n"+
                "Boleto - " + df.format(p_boleto) + "% - " + "R$" + boleto + "\n" +
                "Débito Automático - " + df.format(p_debitoAutomatico) + "% - " + "R$" + debitoAutomatico);
    }

    public void Voltar(View view){
        Intent intentPut = new Intent(getApplicationContext(),ConsultarGastosActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login",loginUsed);
        bundle.putString("Filtro",filtro);
        intentPut.putExtras(bundle);

        startActivity(intentPut);
        finish();
    }

    public void Menu(View view){
        Intent intentPut = new Intent(getApplicationContext(),HomeScreenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login",loginUsed);
        intentPut.putExtras(bundle);

        startActivity(intentPut);
        finish();
    }
}
