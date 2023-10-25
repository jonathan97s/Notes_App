package com.example.personalnotesplus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalnotesplus.asynTask.InsertNoteAsyncTask;
import com.example.personalnotesplus.bd.Nota;
import com.example.personalnotesplus.bd.NotaDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Activity2 extends AppCompatActivity {

    private EditText editTitol ;
    private EditText editDate ;
    private EditText editContingut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        editTitol= (EditText) findViewById(R.id.editTextTitol);
        editDate= (EditText) findViewById(R.id.editTextDate);
        editContingut = (EditText) findViewById(R.id.editTextContingut);

        Button guardar = (Button) findViewById(R.id.buttonGuardar);
        Button cancelar = (Button) findViewById(R.id.buttonCancelar);
        Button calendario = findViewById(R.id.buttonCalendario);

        guardar.setOnClickListener(view -> {
            Context context = getApplicationContext();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String titol = this.editTitol.getText().toString();
            String d = this.editDate.getText().toString();
            String contingut = this.editContingut.getText().toString();
            try {
                Date date = sdf.parse(d);
                Nota note;
                note =  new Nota(titol,date,contingut);
                if (!titol.isEmpty()) {
                    if (!d.isEmpty()) {
                        if (!contingut.isEmpty()) {
                            NotaDatabase database = NotaDatabase.getInstance(this);
                            new InsertNoteAsyncTask(database.notaDao()).execute(note);
                            this.finish();
                        }else{
                            Toast.makeText(context, "Escribe un contenido porfavor", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Escribe un fecha porfavor", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Escribe un titulo porfavor", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        cancelar.setOnClickListener(view -> this.finish());

        calendario.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        String days = String.format("%d", dayOfMonth);
                        String months = String.format("%d", monthOfYear + 1);
                        String dates = days + "-" + months + "-" + year1;
                        editDate.setText(dates);
                    }, year, month, day);
            datePickerDialog.show();
        });
    }
}