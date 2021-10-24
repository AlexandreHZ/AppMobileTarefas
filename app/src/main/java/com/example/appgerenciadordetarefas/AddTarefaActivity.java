package com.example.appgerenciadordetarefas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;

public class AddTarefaActivity extends AppCompatActivity {

    EditText editTitulo;
    EditText editPrioridade;
    AppCompatButton btnAddData;
    EditText editDescricao;
    AppCompatButton btnSalvar;
    ImageButton btnVoltarTarefa;
    TextView labelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarefa);

        btnSalvar = findViewById(R.id.btnSalvar);
        editTitulo = findViewById(R.id.editTitulo);
        editPrioridade = findViewById(R.id.editPrioridade);
        btnAddData = findViewById(R.id.btnAddData);
        editDescricao = findViewById(R.id.editDescricao);
        btnVoltarTarefa = findViewById(R.id.btnVoltarTarefa);
        labelData = findViewById(R.id.labelData);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> datePicker;
                datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Selecione a data")
                        .build();

                datePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = editTitulo.getText().toString();
                String prioridade = editPrioridade.getText().toString();
                String data = labelData.getText().toString();
                String descricao = editDescricao.getText().toString();

            }
        });

        btnVoltarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTarefaActivity.this, ActivityHome.class);
                startActivity(intent);
            }
        });
    }
}