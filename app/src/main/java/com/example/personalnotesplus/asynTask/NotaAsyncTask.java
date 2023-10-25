package com.example.personalnotesplus.asynTask;

import android.os.AsyncTask;

import com.example.personalnotesplus.bd.Nota;
import com.example.personalnotesplus.bd.NotaDao;

import java.util.List;

public class NotaAsyncTask extends AsyncTask<Void, Void, List<Nota>> {

    private NotaDao notaDao;

    public NotaAsyncTask(NotaDao notaDao) {
        this.notaDao = notaDao;
    }

    @Override
    protected List<Nota> doInBackground(Void... voids) {
        return notaDao.getAllNotes();
    }
}