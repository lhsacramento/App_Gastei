package com.LH.gastei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ConsultarGastosActivity extends AppCompatActivity {

    DBHelper db;

    List<Gasto> gastos,gastosFiltrados;
    String loginUsed;

    ListView meusGastos;
    ArrayList<String> informacoes;
    ArrayAdapter<String> adapter;

    Intent intenGet;
    Bundle parametros;

    Spinner spFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_gastos);
        db = new DBHelper(this);
        gastos = new ArrayList<>();
        gastosFiltrados = new ArrayList<>();
        ConfigSpinner();

        intenGet = getIntent();
        parametros = intenGet.getExtras();

        meusGastos = findViewById(R.id.listGastos);

        spFiltro = findViewById(R.id.spFiltro);
        if(parametros != null)
        {
            loginUsed = parametros.getString("Login");
        }

        gastos = db.SelecionarGastos(loginUsed);
        FiltrarDias(7,false);
        //Inverter();
        MostrarGastos(gastosFiltrados);


        meusGastos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),DetalhesDoGastoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Login",loginUsed);
                bundle.putString("DataCompra",gastosFiltrados.get(position).getDataCompra());
                bundle.putString("GeneroGasto",gastosFiltrados.get(position).getGeneroDespesas());
                bundle.putString("ClassificacaoGasto",gastosFiltrados.get(position).getClassificacaoGasto());
                bundle.putString("ValorGasto",gastosFiltrados.get(position).getValorGasto());
                bundle.putString("FormaPagamento",gastosFiltrados.get(position).getFormaPagamento());
                bundle.putString("DescricaoGasto",gastosFiltrados.get(position).getDescreverGasto());

                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });
    }

    private void OrganizarDados (List<Gasto> _gastos)
    {
        String date1InNewFormat;
        String date2InNewFormat;
        String[] cutDate1 = new String[3];
        String[] cutDate2 = new String[3];

        boolean taNaOrdem = false;
        int i = 0;
        int lititVezes = ((_gastos.size() -1)*(_gastos.size()-1));
        int quantidadeDeVezes = 0;
        while(!taNaOrdem)
        {
            //Se o contador for menor que o tamanho do array
            if(i<_gastos.size()-1)
            {
                //Pegando a data e transformando ela em yyyyMMdd
                cutDate1 = _gastos.get(i).getDataCompra().split("/");
                date1InNewFormat = cutDate1[2] + cutDate1[1] + cutDate1[0];
                cutDate2 = _gastos.get(i + 1).getDataCompra().split("/");
                date2InNewFormat = cutDate2[2] + cutDate2[1] + cutDate2[0];

                long date1 = Long.parseLong(date1InNewFormat);
                long date2 = Long.parseLong(date2InNewFormat);

                if(date1 < date2)
                {
                    /*Gasto g = new Gasto();
                    g = _gastos.get(i+1);
                    _gastos.set(i+1,_gastos.get(i));
                    _gastos.set(i,g);*/

                    Gasto w = new Gasto();
                    w = gastos.get(i);
                    _gastos.set(i,_gastos.get(i+1));
                    _gastos.set(i+1,w);
                }
            }
            if(i>=_gastos.size())
            {
                i=0;
                quantidadeDeVezes++;
                if(quantidadeDeVezes >= lititVezes)
                {
                    taNaOrdem = true;
                }
            }
            else
            {
                i++;
            }

        }

    }

    public void MostrarGastos(List<Gasto> _gastos)
    {
        try {
            OrganizarDados(_gastos);

            informacoes = new ArrayList<String>();

            //A primeira é pra zerar, pois informações é zero.

            for (int i = 0; i < _gastos.size(); i++) {
                informacoes.add(_gastos.get(i).getDataCompra() + " - " +
                        "R$ " + _gastos.get(i).getValorGasto() + " - " +
                        _gastos.get(i).getGeneroDespesas() + " - " +
                        _gastos.get(i).getFormaPagamento());
            }

            //A segunda é para mostrar
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, informacoes);
            meusGastos.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Alerta("Não há gastos ainda!");
        }
    }

    public void Voltar(View view)
    {
        Intent intentPut = new Intent(getApplicationContext(),HomeScreenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Login",loginUsed);
        intentPut.putExtras(bundle);

        startActivity(intentPut);
        finish();
    }

    public void ConfigSpinner()
    {
        Spinner filtroGastos = findViewById(R.id.spFiltro);
        ArrayAdapter<CharSequence> aFG = ArrayAdapter.createFromResource(this,R.array.FiltroGasto,android.R.layout.simple_spinner_item);
        aFG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtroGastos.setAdapter(aFG);
    }

    public void FiltrarDias(int dias,boolean all) {
        if (!all)
        {
            gastosFiltrados.clear();
            long filtroDias = DataDoFiltro(dias);

            String gastosDate;
            String[] gastosDateSplited = new String[3];

            for (int i = 0; i < gastos.size(); i++) {
                gastosDate = gastos.get(i).getDataCompra();
                gastosDateSplited = gastosDate.split("/");
                gastosDate = gastosDateSplited[2] + gastosDateSplited[1] + gastosDateSplited[0];

                if (Long.parseLong(gastosDate) >= filtroDias) {
                    gastosFiltrados.add(gastos.get(i));
                }
            }
        }
        else
        {
            gastosFiltrados = gastos;
        }
    }

    public long DataDoFiltro(int dias)
    {
        String diaFiltro;
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.DATE,gc.get(Calendar.DATE) - dias);

        if(gc.get(Calendar.MONTH) < 10 && gc.get(Calendar.DAY_OF_MONTH) < 10)
        {
            diaFiltro = "" + gc.get(Calendar.YEAR) + "0" +(gc.get(Calendar.MONTH)+1) + "0" + gc.get(Calendar.DAY_OF_MONTH);
        }
        else if(gc.get(Calendar.DAY_OF_MONTH) < 10 && gc.get(Calendar.MONTH)>9)
        {
            diaFiltro = "" + gc.get(Calendar.YEAR) + (gc.get(Calendar.MONTH)+1) + "0" + gc.get(Calendar.DAY_OF_MONTH);
        }
        else if(gc.get(Calendar.DAY_OF_MONTH) > 9 && gc.get(Calendar.MONTH)<10)
        {
            diaFiltro = "" + gc.get(Calendar.YEAR) + "0" +(gc.get(Calendar.MONTH)+1) + gc.get(Calendar.DAY_OF_MONTH);
        }
        else
        {
            diaFiltro = "" + gc.get(Calendar.YEAR) + (gc.get(Calendar.MONTH) + 1) + gc.get(Calendar.DAY_OF_MONTH);
        }

        Log.d("console","dia do filtro: " + diaFiltro);
        return Long.parseLong(diaFiltro);
    }

    public void Refiltrar(View view)
    {
        switch (spFiltro.getSelectedItem().toString())
        {
            case "7 dias":
                FiltrarDias(7,false);
                MostrarGastos(gastosFiltrados);
                break;
            case "15 dias":
                FiltrarDias(15,false);
                MostrarGastos(gastosFiltrados);
                break;
            case "30 dias":
                FiltrarDias(30,false);
                MostrarGastos(gastosFiltrados);
                break;
            case "360 dias":
                FiltrarDias(360,false);
                MostrarGastos(gastosFiltrados);
                break;
            case "Todo o tempo":
                FiltrarDias(0,true);
                MostrarGastos(gastosFiltrados);
                break;
        }
    }

    public void Inverter()
    {
        //InverterArrayList
        ArrayList<Gasto> inverter = new ArrayList<>();
        for(int i = gastosFiltrados.size() -1;i>=0;i--)
        {
            inverter.add(gastosFiltrados.get(i));
        }
        gastosFiltrados = inverter;
    }

    public void Alerta(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

}
