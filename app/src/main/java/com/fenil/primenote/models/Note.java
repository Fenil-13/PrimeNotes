package com.fenil.primenote.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "date")
    private Date date;
    private String data;

    @Ignore
    public Note() {
    }

    @Ignore
    public Note(Date date, String data) {
        this.date = date;
        this.data = data;
    }

    public Note(int id, Date date, String data) {
        this.id = id;
        this.date = date;
        this.data = data;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
