package com.example.appgerenciadordetarefas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityHome extends AppCompatActivity {

    private BottomAppBar bottomAppBar;
    private RecyclerView recyclerView;
    private FloatingActionButton btnAddTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        btnAddTarefa = findViewById(R.id.btnAddTarefa);

        btnAddTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHome.this, AddTarefaActivity.class);

                startActivity(intent);
            }
        });

    }

}
