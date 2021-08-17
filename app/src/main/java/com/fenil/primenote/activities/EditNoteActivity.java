package com.fenil.primenote.activities;

import android.os.Bundle;

import com.fenil.primenote.databinding.ActivityEditNoteBinding;
import com.fenil.primenote.models.Note;
import com.fenil.primenote.utils.Constants;
import com.fenil.primenote.viewmodels.EditorViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fenil.primenote.R;

import java.util.Objects;

public class EditNoteActivity extends AppCompatActivity {

    private ActivityEditNoteBinding binding;

    private EditorViewModel mEditorViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);

        initViewModel();

    }

    private void initViewModel() {
        Observer<Note> noteObserver=new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                if(note.getData()!=null){
                    binding.scrollingContent.etNoteData.setText(note.getData());
                }
            }
        };

        mEditorViewModel= new ViewModelProvider(this).get(EditorViewModel.class);
        mEditorViewModel.mLiveNote.observe(EditNoteActivity.this,noteObserver);

        if(getIntent().hasExtra(Constants.NOTE_ID_KEY)){
            mEditorViewModel.newNodeInserted=false;
            binding.toolbarLayout.setTitle(getString(R.string.edit_note));
            int noteId=getIntent().getIntExtra(Constants.NOTE_ID_KEY,0);
            mEditorViewModel.loadNote(noteId);
        }else{
            mEditorViewModel.newNodeInserted=true;
            binding.toolbarLayout.setTitle(getString(R.string.new_note));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(!mEditorViewModel.newNodeInserted){
            getMenuInflater().inflate(R.menu.menu_edit_note,menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            saveAndExit();
            super.onBackPressed();
            return true;
        }else if(item.getItemId()==R.id.delete_note){
            deleteNote();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        mEditorViewModel.deleteNote();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAndExit();
    }

    private void saveAndExit() {
        mEditorViewModel.saveAndExit(binding.scrollingContent.etNoteData.getText().toString().trim());
    }
}