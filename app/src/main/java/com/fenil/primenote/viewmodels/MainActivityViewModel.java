package com.fenil.primenote.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fenil.primenote.database.NoteRepository;
import com.fenil.primenote.models.Note;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    public LiveData<List<Note>> mNoteList;
    private NoteRepository mRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository=NoteRepository.getInstance(application.getApplicationContext());
        mNoteList=mRepository.mNoteList;

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
