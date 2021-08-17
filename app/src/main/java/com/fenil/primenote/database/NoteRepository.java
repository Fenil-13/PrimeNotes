package com.fenil.primenote.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fenil.primenote.models.Note;
import com.fenil.primenote.utils.SampleDateProvider;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteRepository {


    public LiveData<List<Note>> mNoteList;
    private static NoteRepository ourInstance;
    private Executor mExecutor= Executors.newSingleThreadExecutor();
    private NoteDatabase mDatabase;

    public static NoteRepository getInstance(Context context) {
        return ourInstance=new NoteRepository(context);
    }
    private NoteRepository(Context context){
        mDatabase=NoteDatabase.getInstance(context);
        mNoteList=  getAllNotes();
    }

    public void addSampleData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().insertAllNote(SampleDateProvider.getSampleData());
            }
        });
    }

    private  LiveData<List<Note>> getAllNotes(){
        return mDatabase.notesDao().getAllNotes();
    }

    public void deleteAllNotes() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                int result = mDatabase.notesDao().deleteAllNotes();
                Log.d("Fenil", "run: notes deleted  "+result);
            }
        });

    }

    public Note loadNote(int noteId) {
        //already called from background thread dw
        return mDatabase.notesDao().getNoteById(noteId);
    }

    public void insertOrUpdateNote(Note updateNote) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().insertNote(updateNote);
            }
        });
    }

    public void deleteNote(Note deletedNote) {
        mDatabase.notesDao().deleteNote(deletedNote);
    }
}
