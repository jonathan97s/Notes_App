package com.example.personalnotesplus.asynTask;

import android.os.AsyncTask;

import com.example.personalnotesplus.bd.Nota;
import com.example.personalnotesplus.bd.NotaDao;

public class DeleteNoteAsyncTask extends AsyncTask<Nota, Void, Void> {

    private NotaDao notaDao;

    public DeleteNoteAsyncTask(NotaDao notaDao) {
        this.notaDao = notaDao;
    }

    @Override
    protected Void doInBackground(Nota... notes) {
        notaDao.delete(notes[0]);
        return null;
    }
}
