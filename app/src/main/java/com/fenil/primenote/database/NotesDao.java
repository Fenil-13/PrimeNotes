package com.fenil.primenote.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fenil.primenote.models.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllNote(List<Note> notes);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM notes WHERE id= :id")
    Note getNoteById(int id);

    @Query("SELECT * FROM notes ORDER BY date DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("DELETE FROM notes")
    int deleteAllNotes();

    @Query("SELECT COUNT(*) FROM notes")
    int getNoteCount();

}
