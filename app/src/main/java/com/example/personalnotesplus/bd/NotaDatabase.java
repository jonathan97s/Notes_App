package com.example.personalnotesplus.bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Nota.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class NotaDatabase extends RoomDatabase {

private static NotaDatabase instance;

public abstract NotaDao notaDao();

public static synchronized NotaDatabase getInstance(Context context) {
        if (instance == null) {
        instance = Room.databaseBuilder(context.getApplicationContext(),
        NotaDatabase.class, "nota_database")
        .fallbackToDestructiveMigration()
        .build();
        }
        return instance;
        }
}
