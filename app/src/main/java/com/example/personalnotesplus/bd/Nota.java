package com.example.personalnotesplus.bd;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "nota_table")
@TypeConverters({Converter.class})
public class Nota implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "titol")
    public String titol;

    @ColumnInfo(name = "data_caducitat")
    public Date dataCaducitat;

    @ColumnInfo(name = "contingut")
    public String Contingut;

    public Nota(String titol, Date dataCaducitat, String contingut) {
        this.titol = titol;
        this.dataCaducitat = dataCaducitat;
        Contingut = contingut;
    }

    public Nota() {
    }

    public String getTitol() {
        return titol;
    }

    public Date getDataCaducitat() {
        return dataCaducitat;
    }

    public String getContingut() {
        return Contingut;
    }

    @NonNull
    @Override
    public String toString() {
        return "Nota{" +
                "titol='" + titol + '\'' +
                ", dataCaducitat=" + dataCaducitat +
                ", Contingut='" + Contingut + '\'' +
                '}';
    }
}
