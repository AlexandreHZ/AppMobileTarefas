package com.example.appgerenciadordetarefas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private ToastHelper toastHelper;
    private static final String DATABASE_NAME = "ToDoList.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME_USUARIO = "usuario";
    private static final String COLUMN_ID_USUARIO = "id";
    private static final String COLUMN_NOME_USUARIO = "usuario";
    private static final String COLUMN_EMAIL_USUARIO = "email";
    private static final String COLUMN_SENHA_USUARIO = "senha";

    private static final String TABLE_NAME_TAREFA = "tarefa";
    private static final String COLUMN_ID_TAREFA = "id";
    private static final String COLUMN_TITULO_TAREFA = "titulo";
    private static final String COLUMN_PRIORIDADE_TAREFA = "prioridade";
    private static final String COLUMN_DATA_TAREFA = "data";
    private static final String COLUMN_DESCRICAO_TAREFA = "descricao";
    private static final String COLUMN_USUARIO = "id_usuario";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.toastHelper = new ToastHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCriarTabelaUsuario = "create table " + TABLE_NAME_USUARIO
                + "(" + COLUMN_ID_USUARIO + " integer primary key autoincrement, "
                + COLUMN_NOME_USUARIO + " text,"
                + COLUMN_EMAIL_USUARIO + " text,"
                + COLUMN_SENHA_USUARIO + " text);";

        String queryCriarTabelaTarefas = "create table "+TABLE_NAME_TAREFA
                + "(" + COLUMN_ID_TAREFA + " integer primary key autoincrement, "
                + COLUMN_TITULO_TAREFA + " text,"
                + COLUMN_PRIORIDADE_TAREFA + " text,"
                + COLUMN_DATA_TAREFA + " text,"
                + COLUMN_DESCRICAO_TAREFA + " text,"
                + COLUMN_USUARIO + " integer);";

        db.execSQL(queryCriarTabelaUsuario);
        db.execSQL(queryCriarTabelaTarefas);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_TAREFA);
        onCreate(db);
    }

    //Referentes à tarefas

    void addTarefa(String titulo, String prioridade, String data, String descricao, Integer idUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITULO_TAREFA, titulo);
        contentValues.put(COLUMN_PRIORIDADE_TAREFA, prioridade);
        contentValues.put(COLUMN_DATA_TAREFA, data);
        contentValues.put(COLUMN_DESCRICAO_TAREFA, descricao);
        contentValues.put(COLUMN_USUARIO, idUsuario);

        long result = db.insert(TABLE_NAME_TAREFA, null, contentValues);
        if (result == -1) {
            toastHelper.showToast("F", "Ops, algo de errado aconteceu!");
        } else {
            toastHelper.showToast("S", "Tarefa adicionada com sucesso!");
        }
    }

    Cursor retornarTarefasByUsuario(String idUsuario) {
        String query = "select * from "+ TABLE_NAME_TAREFA+ " where id_usuario = "+ idUsuario;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db!= null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void deletarTarefaById(String idTarefa) {

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_TAREFA, COLUMN_ID_TAREFA + "= ?", new String[]{idTarefa});

        if (result == -1) {
            toastHelper.showToast("F", "Não foi possível excluir a tarefa!");
        } else {
            toastHelper.showToast("S", "Tarefa excluída com sucesso!");
        }
    }

    //Referentes à usuários
    Cursor retornarLogin(String usuario, String senha) {
        String query =
                "select "+
                    COLUMN_ID_USUARIO+
                " from "+
                    TABLE_NAME_USUARIO+
                " where "+
                    COLUMN_NOME_USUARIO+" = '"+usuario+
                "' and "+ COLUMN_SENHA_USUARIO+" = '"+senha+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db!= null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor retornarByUsuario(String usuario) {
        String query =
                "select "+
                        COLUMN_ID_USUARIO+
                        " from "+
                        TABLE_NAME_USUARIO+
                        " where "+
                        COLUMN_NOME_USUARIO+" = '"+usuario+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db!= null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void addUsuario (String usuario, String email, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NOME_USUARIO, usuario);
        contentValues.put(COLUMN_EMAIL_USUARIO, email);
        contentValues.put(COLUMN_SENHA_USUARIO, senha);

        long result = db.insert(TABLE_NAME_USUARIO, null, contentValues);
        if (result == -1) {
            toastHelper.showToast("F", "Ops, não foi efetuar o cadastro!");
        } else {
            toastHelper.showToast("S", "Cadastro realizado com sucesso!");
        }
    }

    /*void showToast(String tipo, String mensagem) {
        if (tipo.equals("S")) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.toast_sucesso, null);
            Toast toast = new Toast(context);
            TextView msgSucesso = view.findViewById(R.id.msgSucesso);
            msgSucesso.setText(mensagem);
            toast.setGravity(Gravity.TOP, 0, 140);
            toast.setView(view);
            toast.show();
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.toast_falha, null);
            Toast toast = new Toast(context);
            TextView msgFalha = view.findViewById(R.id.msgFalha);
            msgFalha.setText(mensagem);
            toast.setGravity(Gravity.TOP, 0, 140);
            toast.setView(view);
            toast.show();
        }
    }*/
}
