package com.example.appgerenciadordetarefas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ToDoList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_USUARIO = "usuario";
    private static final String COLUMN_ID_USUARIO = "id";
    private static final String COLUMN_NOME_USUARIO = "usuario";
    private static final String COLUMN_EMAIL_USUARIO = "email";

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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCriarTabelaUsuario = "create table " + TABLE_NAME_USUARIO
                + "(" + COLUMN_ID_USUARIO + " integer primary key autoincrement, "
                + COLUMN_NOME_USUARIO + " text,"
                + COLUMN_EMAIL_USUARIO + " text);";

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
}
