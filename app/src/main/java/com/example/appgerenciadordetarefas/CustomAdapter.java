package com.example.appgerenciadordetarefas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList idTarefa, tituloTarefa, prioridadeTarefa, dataTarefa;
    RecyclerView recyclerView;
    DatabaseHelper db;

    CustomAdapter(Context context,
                  ArrayList idTarefa,
                  ArrayList tituloTarefa,
                  ArrayList prioridadeTarefa,
                  ArrayList dataTarefa) {

        db = new DatabaseHelper(context.getApplicationContext());
        this.context = context;
        this.idTarefa = idTarefa;
        this.tituloTarefa = tituloTarefa;
        this.prioridadeTarefa = prioridadeTarefa;
        this.dataTarefa = dataTarefa;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tarefa_registro, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tituloTarefaTxt.setText(String.valueOf(tituloTarefa.get(position)));
        holder.dataTarefaTxt.setText(String.valueOf(dataTarefa.get(position)));

        holder.idTarefaTxt.setText(String.valueOf(idTarefa.get(position)));
        holder.idTarefaTxt.setVisibility(View.INVISIBLE);

        holder.deletarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deletarTarefaById(holder.idTarefaTxt.getText().toString());

                idTarefa.remove(position);
                tituloTarefa.remove(position);
                prioridadeTarefa.remove(position);
                dataTarefa.remove(position);

                recyclerView.removeViewAt(position);
                CustomAdapter.this.notifyItemRemoved(position);
                recyclerView.setAdapter(CustomAdapter.this);
                recyclerView.invalidate();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tituloTarefa.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idTarefaTxt, tituloTarefaTxt, prioridadeTarefaTxt, dataTarefaTxt;
        ImageButton deletarTarefa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTarefaTxt = itemView.findViewById(R.id.tituloTarefa);
            dataTarefaTxt = itemView.findViewById(R.id.dataTarefa);
            idTarefaTxt = itemView.findViewById(R.id.idTarefa);
            deletarTarefa = itemView.findViewById(R.id.deletarTarefa);
        }
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
