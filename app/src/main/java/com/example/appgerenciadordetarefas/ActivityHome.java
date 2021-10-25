package com.example.appgerenciadordetarefas;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityHome extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private RecyclerView recyclerView;
    private FloatingActionButton btnAddTarefa;

    private ImageButton btnSair;

    private int idUsuario = 1;

    DatabaseHelper db;
    ArrayList<String> idTarefa, tituloTarefa, prioridadeTarefa, dataTarefa;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddTarefa = findViewById(R.id.btnAddTarefa);
        btnSair = findViewById(R.id.btnSair);

        db = new DatabaseHelper(ActivityHome.this);
        idTarefa = new ArrayList<>();
        tituloTarefa = new ArrayList<>();
        prioridadeTarefa = new ArrayList<>();
        dataTarefa = new ArrayList<>();

        guardarDadosArrayValores();

        customAdapter = new CustomAdapter(ActivityHome.this, idTarefa, tituloTarefa, prioridadeTarefa, dataTarefa);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityHome.this));

        btnAddTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHome.this, AddTarefaActivity.class);

                startActivity(intent);
            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHome.this, R.style.AlertDialogTheme);

                builder.setCancelable(true);
                builder.setTitle("Sair");
                builder.setMessage("Deseja realmente sair?");
                builder.setPositiveButton("Sair",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ActivityHome.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void guardarDadosArrayValores() {
        Cursor cursor = db.retornarTarefasByUsuario(this.idUsuario);
        if (cursor.getCount() == 0) {

        } else {
            while (cursor.moveToNext()) {
                idTarefa.add(cursor.getString(0));
                tituloTarefa.add(cursor.getString(1));
                prioridadeTarefa.add(cursor.getString(2));
                dataTarefa.add(cursor.getString(3));
            }
        }
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
