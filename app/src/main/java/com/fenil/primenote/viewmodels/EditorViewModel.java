package com.fenil.primenote.viewmodels;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.fenil.primenote.database.NoteRepository;
import com.fenil.primenote.models.Note;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<Note> mLiveNote=new MutableLiveData<>();

    private NoteRepository mRepository ;

    private Executor mExecutor=Executors.newSingleThreadExecutor();

    public boolean newNodeInserted=false;
    public EditorViewModel(@NonNull Application application) {
        super(application);

        mRepository=NoteRepository.getInstance(application.getApplicationContext());
    }

    public void loadNote(int noteId) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Note note=mRepository.loadNote(noteId);
                mLiveNote.postValue(note); //background thread so you have to use post value
            }
        });
    }

    public void saveAndExit(String updateData) {
        //insert new note
        Note oldNote;
        if(newNodeInserted){
            if(TextUtils.isEmpty(updateData)){
                return;
            }
            oldNote=new Note(new Date(),updateData);

        }else{
            oldNote=mLiveNote.getValue();
        }
        oldNote.setDate(new Date());
        oldNote.setData(updateData);
        mRepository.insertOrUpdateNote(oldNote);

    }

    public void deleteNote() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.deleteNote(mLiveNote.getValue());
            }
        });
    }
}
