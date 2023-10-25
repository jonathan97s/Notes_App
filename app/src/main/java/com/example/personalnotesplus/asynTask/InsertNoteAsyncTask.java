package com.example.personalnotesplus.asynTask;

import android.os.AsyncTask;

import com.example.personalnotesplus.bd.Nota;
import com.example.personalnotesplus.bd.NotaDao;

public class InsertNoteAsyncTask extends AsyncTask<Nota, Void, Void> {
    private NotaDao notaDao;

    public InsertNoteAsyncTask(NotaDao notaDao) {
        this.notaDao = notaDao;
    }

    @Override
    protected Void doInBackground(Nota... notes) {
        notaDao.insert(notes[0]);
        return null;
    }
}

