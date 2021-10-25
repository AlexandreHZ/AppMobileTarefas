package com.example.appgerenciadordetarefas;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ToastHelper {

    private Context context;

    public ToastHelper(@Nullable Context context) {
        this.context = context;
    }

    void showToast(String tipo, String mensagem) {
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
    }
}
