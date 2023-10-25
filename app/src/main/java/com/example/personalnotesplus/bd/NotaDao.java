package com.example.personalnotesplus.bd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotaDao    {

    @Insert
    void insert(Nota nota);

    @Update
    void update(Nota nota);

    @Delete
    void delete(Nota nota);

    @Query("SELECT * FROM nota_table")
    List<Nota> getAllNotes();
}
