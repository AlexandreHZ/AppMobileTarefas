package com.example.appgerenciadordetarefas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    SQLiteDatabase db = this.getWritableDatabase();

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

    //Referentes ?? tarefas

    void addTarefa(String titulo, String prioridade, String data, String descricao, Integer idUsuario) {
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

    void editTarefa(String titulo, String prioridade, String data, String descricao, Integer idTarefa) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITULO_TAREFA, titulo);
        contentValues.put(COLUMN_PRIORIDADE_TAREFA, prioridade);
        contentValues.put(COLUMN_DATA_TAREFA, data);
        contentValues.put(COLUMN_DESCRICAO_TAREFA, descricao);

        long result = db.update(TABLE_NAME_TAREFA, contentValues, COLUMN_ID_TAREFA + "= ?", new String[]{idTarefa.toString()} );

        if (result == -1) {
            toastHelper.showToast("F", "Ops, algo de errado aconteceu!");
        } else {
            toastHelper.showToast("S", "Tarefa atualizada com sucesso!");
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

        long result = db.delete(TABLE_NAME_TAREFA, COLUMN_ID_TAREFA + "= ?", new String[]{idTarefa});

        if (result == -1) {
            toastHelper.showToast("F", "N??o foi poss??vel excluir a tarefa!");
        } else {
            toastHelper.showToast("S", "Tarefa exclu??da com sucesso!");
        }
    }

    //Referentes ?? usu??rios

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
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NOME_USUARIO, usuario);
        contentValues.put(COLUMN_EMAIL_USUARIO, email);
        contentValues.put(COLUMN_SENHA_USUARIO, senha);

        long result = db.insert(TABLE_NAME_USUARIO, null, contentValues);
        if (result == -1) {
            toastHelper.showToast("F", "Ops, n??o foi efetuar o cadastro!");
        } else {
            toastHelper.showToast("S", "Cadastro realizado com sucesso!");
        }
    }
}
