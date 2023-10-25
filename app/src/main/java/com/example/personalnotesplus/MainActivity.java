package com.example.personalnotesplus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.personalnotesplus.asynTask.NotaAsyncTask;
import com.example.personalnotesplus.bd.Nota;
import com.example.personalnotesplus.bd.NotaDao;
import com.example.personalnotesplus.bd.NotaDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements AdaptadorNota.OnItemClickListener {

    private Nota nota;
    private List<Nota> notes = new ArrayList<>();
    private AdaptadorNota adaptadorNota;
    private RecyclerView recyclerView;
    boolean estatButton = true;
    int opcionItem = 0;
    private FloatingActionButton buttonViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = (Button) this.findViewById(R.id.buttonAdd);
        buttonViewList = this.findViewById(R.id.floatingActionButtonViewList);
        recyclerView = findViewById(R.id.recyclerView);

        loadData();
        switch (opcionItem) {
            case 1:
                Intent intent = new Intent(this, Activity2.class);
                startActivityForResult(intent, 1);
                opcionItem = 1;
                saveData();
                break;
            case 2:
                notes = extrarnotes();
                notes = getExpiredDates(notes);
                adaptadorNota = new AdaptadorNota(notes);
                recyclerView.setAdapter(adaptadorNota);
                configButoonView(estatButton);
                opcionItem = 2;
                saveData();
                break;
            case 3:
                notes = extrarnotes();
                notes = getValidDates(notes);
                adaptadorNota = new AdaptadorNota(notes);
                recyclerView.setAdapter(adaptadorNota);
                configButoonView(estatButton);
                opcionItem = 3;
                saveData();
                break;
            case 4:
                configRecicleView();
                opcionItem = 4;
                saveData();
                break;
            case 5:
                pressConfigButoonView();
                opcionItem = 5;
                saveData();
                break;
            default:
                configRecicleView();
        }

        add.setOnClickListener(view -> {
            Intent intent = new Intent(this, Activity2.class);
            startActivityForResult(intent, 1);
        });

        buttonViewList.setOnClickListener(view -> {
            pressConfigButoonView();
        });
    }

    public void pressConfigButoonView(){
        if (estatButton == true){
            estatButton = false;
        }else{
            estatButton = true;
        }
        configButoonView(estatButton);
        saveData();
    }

    public void configButoonView(boolean estatButton){
        if (notes.size() > 0) {
            if (estatButton == true) {
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                buttonViewList.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_grid_view_24));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                buttonViewList.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_view_headline_24));
            }
        }

    }

    public void configRecicleView() {
        notes = extrarnotes();
        adaptadorNota = new AdaptadorNota(notes);
        adaptadorNota.setOnItemClickListener(this);
        recyclerView.setAdapter(adaptadorNota);
        configButoonView(estatButton);
    }

    public List<Nota> extrarnotes() {
        List<Nota> notes = null;
        NotaDatabase database = NotaDatabase.getInstance(this);
        NotaDao notaDao = database.notaDao();
        NotaAsyncTask task = new NotaAsyncTask(notaDao);
        task.execute();
        try {
            notes = task.get();

            // Ordenar las notas por fecha más cercana
            if (notes != null) {
                Collections.sort(notes, new Comparator<Nota>() {
                    @Override
                    public int compare(Nota nota1, Nota nota2) {
                        // Asumiendo que tienes un método getDate() en tu clase Nota
                        return nota1.getDataCaducitat().compareTo(nota2.getDataCaducitat());
                    }
                });
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return notes;
    }


    public List<Nota> getExpiredDates(List<Nota> notes) {
        List<Nota> expiredDates = new ArrayList<>();
        Date currentDate = new Date();
        for (Nota nota : notes) {
            if (nota.getDataCaducitat().before(currentDate)) {
                expiredDates.add(nota);
                System.out.println("true");
            }
        }
        return expiredDates;
    }

    public List<Nota> getValidDates(List<Nota> notes) {
        List<Nota> validDates = new ArrayList<>();
        Date currentDate = new Date();
        for (Nota nota : notes) {
            if (nota.getDataCaducitat().after(currentDate)) {
                validDates.add(nota);
            }
        }
        return validDates;
    }

    public void saveData() {
        SharedPreferences preferences = getSharedPreferences("misPreferencias", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("estatButton", estatButton);
        editor.putInt("opcionItem", opcionItem);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences preferences = getSharedPreferences("misPreferencias", MODE_PRIVATE);
        estatButton = preferences.getBoolean("estatButton", true);
        opcionItem = preferences.getInt("opcionItem", 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            configRecicleView();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onItemClick(View view, int elementPosition) {
        nota = this.adaptadorNota.getNotaPosition(elementPosition);
        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra("nota", nota);
        startActivityForResult(intent, 1);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemNuevaNota:
                Intent intent = new Intent(this, Activity2.class);
                startActivityForResult(intent, 1);
                opcionItem = 1;
                saveData();
                break;
            case R.id.itemNotesCaducades:
                notes = extrarnotes();
                notes = getExpiredDates(notes);
                adaptadorNota.setNotas(notes);
                recyclerView.setAdapter(adaptadorNota);
                configButoonView(estatButton);
                opcionItem = 2;
                saveData();
                break;
            case R.id.itemNotesNoCaducades:
                notes = extrarnotes();
                notes = getValidDates(notes);
                adaptadorNota.setNotas(notes);
                recyclerView.setAdapter(adaptadorNota);
                configButoonView(estatButton);
                opcionItem = 3;
                saveData();
                break;
            case R.id.itemAllNotes:
                configRecicleView();
                opcionItem = 4;
                saveData();
                break;
            case R.id.itemLlistaoTaula:
                pressConfigButoonView();
                opcionItem = 5;
                saveData();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}