package com.example.appgerenciadordetarefas;

import android.content.Intent;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;

public class dialogEntrar extends AppCompatDialogFragment {

    private ExampleDialogListener listener;
    private AppCompatButton btnEntrar;
    private TextView btnVoltar;
    private ToastHelper toastHelper;

    DatabaseHelper db;

    private EditText editUsuario;
    private EditText editSenha;
    private ArrayList<String> usuarioCorrespondente;

    private String nomeUsuario;
    private String senhaUsuario;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_login, null);

        builder.setView(view)
                .setTitle("Login");

        toastHelper = new ToastHelper(getContext());

        btnEntrar = view.findViewById(R.id.btnFazerLogin);
        btnVoltar = view.findViewById(R.id.btnVoltar);
        editUsuario = view.findViewById(R.id.edit_username);
        editSenha = view.findViewById(R.id.edit_password);
        usuarioCorrespondente = new ArrayList<>();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                fazer tratativa para validar o login e ir para a próxima activity (das tarefas)
                 */
                String usuario = editUsuario.getText().toString();
                String senha = editSenha.getText().toString();

                if (usuario.isEmpty() || senha.isEmpty()) {
                    toastHelper.showToast("E", "Preencha todos os dados!");
                } else {
                    db = new DatabaseHelper(getContext());
                    nomeUsuario = usuario;
                    senhaUsuario = senha;

                    preencherUsuarioCorresponde();

                    if (usuarioCorrespondente.size() <= 0) {
                        toastHelper.showToast("E", "Usuário não encontrado!");
                    } else {
                        Intent intent = new Intent(getActivity(), ActivityHome.class);
                        startActivity(intent);
                        dismiss();
                    }
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return builder.create();
    }

    public void preencherUsuarioCorresponde() {
        Cursor cursor = db.retornarLogin(this.nomeUsuario, this.senhaUsuario);
        while (cursor.moveToNext()) {
            usuarioCorrespondente.add(cursor.getString(0));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String username, String password);
    }
}