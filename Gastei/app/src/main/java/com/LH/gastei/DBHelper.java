package com.LH.gastei;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "DB_USERS";
    public static final int DB_VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tableGastos = "CREATE TABLE IF NOT EXISTS TABLE_GASTOS(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Login TEXT, " +
                "DataCompra TEXT, " +
                "GeneroCompra TEXT, " +
                "ValorGasto FLOAT, " +
                "FormaPagamento TEXT, " +
                "ClassificacaoGasto TEXT, " +
                "DescreverGasto TEXT)";
        db.execSQL(tableGastos);

        String tableUsers = "CREATE TABLE IF NOT EXISTS TABLE_USERS(Login TEXT PRIMARY KEY, Password TEXT)";
        db.execSQL(tableUsers);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS TABLE_USERS");
        db.execSQL("DROP TABLE IF EXISTS TABLE_GASTOS");
        onCreate(db);
    }


    public long CreateUsers(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("Login",user.getLogin());
        v.put("Password",user.getSenha());
        long res = db.insert("TABLE_USERS",null,v);
        return res;
    }

    public boolean ValidarLogin(User user) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Login, Password FROM TABLE_USERS WHERE Login=? AND Password=?",
                               new String[] {user.getLogin(),user.getSenha()});
        if(c.getCount()>0)
        {
            return true;
        }
        else
            return false;
    }

    public long CreateGasto(Gasto gasto) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("Login",gasto.getUsedLogin());
        v.put("DataCompra",gasto.getDataCompra());
        v.put("GeneroCompra",gasto.getGeneroDespesas());
        v.put("ValorGasto",gasto.getValorGasto());
        v.put("FormaPagamento",gasto.getFormaPagamento());
        v.put("ClassificacaoGasto",gasto.getClassificacaoGasto());
        v.put("DescreverGasto",gasto.getDescreverGasto());

        long res = db.insert("TABLE_GASTOS", null, v);
        return res;
    }

    public List<Gasto> SelecionarGastos(String _login) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT DataCompra, GeneroCompra, ValorGasto, FormaPagamento, ClassificacaoGasto, DescreverGasto FROM TABLE_GASTOS WHERE Login=?",new String[]{_login});
        List<Gasto> _gastos = new ArrayList<>();

        if(c.moveToFirst())
        {
            do
            {
                Gasto g = new Gasto();
                g.setUsedLogin(_login);
                g.setDataCompra(c.getString(0));
                g.setGeneroDespesas(c.getString(1));
                g.setValorGasto(c.getString(2));
                g.setFormaPagamento(c.getString(3));
                g.setClassificacaoGasto(c.getString(4));
                g.setDescreverGasto(c.getString(5));


                _gastos.add(g);
            }
            while(c.moveToNext());
            return _gastos;
        }
        else {
            return null;
        }
    }
}
