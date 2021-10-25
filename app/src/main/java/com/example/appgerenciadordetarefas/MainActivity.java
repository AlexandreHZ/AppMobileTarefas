package com.example.appgerenciadordetarefas;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity implements dialogEntrar.ExampleDialogListener, dialogCadastrar.ExampleDialogListener {

    private AppCompatButton btnEntrar;
    private AppCompatButton btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntrar = (AppCompatButton) findViewById(R.id.btnEntrar);
        btnCadastrar = (AppCompatButton) findViewById(R.id.btnCadastrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogLogin();
            }
        });
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCadastro();
            }
        });
    }

    public void openDialogLogin() {
        dialogEntrar dialogEntrar = new dialogEntrar();
        dialogEntrar.show(getSupportFragmentManager(), "example dialog");
    }

    public void openDialogCadastro() {
        dialogCadastrar dialogCadastrar = new dialogCadastrar();
        dialogCadastrar.show(getSupportFragmentManager(), "teste cadastro");
    }

    @Override
    public void applyTexts(String username, String password) {
    }
}