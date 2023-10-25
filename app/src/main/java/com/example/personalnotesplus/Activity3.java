package com.example.personalnotesplus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.personalnotesplus.asynTask.DeleteNoteAsyncTask;
import com.example.personalnotesplus.bd.Nota;
import com.example.personalnotesplus.bd.NotaDatabase;

import java.text.SimpleDateFormat;

public class Activity3 extends AppCompatActivity {

    private Nota nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        TextView date;
        TextView contingut;
        TextView titol;

        Button volver = findViewById(R.id.buttonVolver);
        Button eliminar = findViewById(R.id.buttonEliminar2);

        titol = (TextView)  findViewById(R.id.textViewMostrarTitol);
        date =  (TextView) findViewById(R.id.textViewMostrarDate);
        contingut = (TextView) findViewById(R.id.textViewMostrarContingut);

        Intent intent = getIntent();
        nota = (Nota) intent.getSerializableExtra("nota");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if (nota != null) {
            String t = nota.getTitol();
            titol.setText(t);
            String d = (String) sdf.format(nota.getDataCaducitat());
            date.setText(d);
            String c = nota.getContingut();
            contingut.setText(c);
        }

        volver.setOnClickListener(view -> this.finish());

        eliminar.setOnClickListener(view -> {
            NotaDatabase database = NotaDatabase.getInstance(this);
            new DeleteNoteAsyncTask(database.notaDao()).execute(nota);
            this.finish();
        });
    }
}