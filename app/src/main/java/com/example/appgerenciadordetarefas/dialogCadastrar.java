package com.example.appgerenciadordetarefas;

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

public class dialogCadastrar extends AppCompatDialogFragment {

    private ExampleDialogListener listener;
    private ToastHelper toastHelper;
    private AppCompatButton btnCadastrar;
    private TextView btnVoltar;

    private EditText editEmail;
    private EditText editUsuario;
    private EditText editSenha;

    private ArrayList<String> usuariosComMesmoNome;
    private String nomeUsuario;

    DatabaseHelper db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_registrar, null);
        toastHelper = new ToastHelper(getContext());

        builder.setView(view)
                .setTitle("Cadastrar");

        btnCadastrar = view.findViewById(R.id.btnCadastrar);
        btnVoltar = view.findViewById(R.id.btnVoltar);
        editEmail = view.findViewById(R.id.edit_email);
        editUsuario = view.findViewById(R.id.edit_username);
        editSenha = view.findViewById(R.id.edit_password);

        usuariosComMesmoNome = new ArrayList<>();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = editUsuario.getText().toString();
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();

                if (usuario.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    toastHelper.showToast("E", "Preencha todos os dados!");
                } else {
                    db = new DatabaseHelper(getContext());
                    nomeUsuario = usuario;
                    preencherUsuariosConsultaNoArray();

                    if (usuariosComMesmoNome.size() > 0) {
                        toastHelper.showToast("E", "Este usuário já existe!");
                    } else {
                        db.addUsuario(usuario, email, senha);
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

    public void preencherUsuariosConsultaNoArray() {
        Cursor cursor = db.retornarByUsuario(this.nomeUsuario);
        usuariosComMesmoNome.clear();
        while (cursor.moveToNext()) {
            usuariosComMesmoNome.add(cursor.getString(0));
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