package com.example.personalnotesplus;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalnotesplus.bd.Nota;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdaptadorNota extends RecyclerView.Adapter<AdaptadorNota.ViewHolder> {

    private List<Nota> notas;
    private OnItemClickListener listener;

    public AdaptadorNota(List<Nota> notas) {
        this.notas = notas;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdaptadorNota.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
//        return new ViewHolder(view, listener);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nota nota = notas.get(position);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(nota.getDataCaducitat());
        holder.textViewTitol.setText(nota.getTitol());
        holder.textViewDataCaducitat.setText(date);
        holder.textViewContingut.setText(nota.getContingut());
    }

    public Nota getNotaPosition(int elementPosition){return this.notas.get(elementPosition);}

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitol;
        TextView textViewDataCaducitat;
        TextView textViewContingut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitol = itemView.findViewById(R.id.textViewTitolItem);
            textViewDataCaducitat = itemView.findViewById(R.id.textViewDateItem);
            textViewContingut = itemView.findViewById(R.id.textViewContingutItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, getAdapterPosition());
//                        }
//                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int elementPosition);
    }



}


