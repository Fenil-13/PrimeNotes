package com.fenil.primenote.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fenil.primenote.database.NoteRepository;
import com.fenil.primenote.models.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivityViewModel extends AndroidViewModel {

    public LiveData<List<Note>> mNoteList;
    private NoteRepository mRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository=NoteRepository.getInstance(application.getApplicationContext());
        mNoteList=mRepository.mNoteList;

    }

    public List<Note> getFilterList(String queryString){
        List<Note> noteList=new ArrayList<>();
        for (Note note: Objects.requireNonNull(mNoteList.getValue()))
            if(note.getData().toLowerCase(Locale.ROOT).contains(queryString.toLowerCase(Locale.ROOT))){
                noteList.add(note);
            }

        return noteList;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }

    public void deleteNote(Note noteAtPosition) {
        mRepository.deleteNote(noteAtPosition);
    }
}
