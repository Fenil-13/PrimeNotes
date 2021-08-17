package com.fenil.primenote.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.fenil.primenote.R;
import com.fenil.primenote.adapters.NoteAdapters;
import com.fenil.primenote.databinding.ActivityMainBinding;
import com.fenil.primenote.models.Note;
import com.fenil.primenote.utils.SampleDateProvider;
import com.fenil.primenote.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private List<Note> mNoteList=new ArrayList<>();
    private MainActivityViewModel mViewModel;
    NoteAdapters mNoteAdapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        initViewModel();
        initRecycleView();

        binding.fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newNoteIntent=new Intent(MainActivity.this,EditNoteActivity.class);
                startActivity(newNoteIntent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.etSearchNote.getText().toString().isEmpty()){
                    String query=binding.etSearchNote.getText().toString();
                    if(!query.isEmpty()){
                        mNoteList=mViewModel.getFilterList(query);
                        Log.d("fenil", "onQueryTextSubmit: String "+query);
                        Log.d("fenil", "onQueryTextSubmit: "+mNoteList.size());
                    }else{
                        mNoteList=mViewModel.mNoteList.getValue();
                    }
                }else{
                    mNoteList=mViewModel.mNoteList.getValue();
                }
                mNoteAdapters = new NoteAdapters(MainActivity.this,mNoteList);
                binding.rvNotes.setAdapter(mNoteAdapters);
            }
        });

        binding.etSearchNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!binding.etSearchNote.getText().toString().isEmpty()){
                    String query=binding.etSearchNote.getText().toString();
                    if(!query.isEmpty()){
                        mNoteList=mViewModel.getFilterList(query);
                        Log.d("fenil", "onQueryTextSubmit: String "+query);
                        Log.d("fenil", "onQueryTextSubmit: "+mNoteList.size());
                    }else{
                        mNoteList=mViewModel.mNoteList.getValue();
                    }
                }else{
                    mNoteList=mViewModel.mNoteList.getValue();
                }
                mNoteAdapters = new NoteAdapters(MainActivity.this,mNoteList);
                binding.rvNotes.setAdapter(mNoteAdapters);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void initViewModel() {
        Observer<List<Note>> noteObserver=
                new Observer<List<Note>>() {
                    @Override
                    public void onChanged(List<Note> notes) {
                        mNoteList.clear();
                        mNoteList.addAll(notes);

                        if(mNoteAdapters==null){
                            mNoteAdapters = new NoteAdapters(MainActivity.this,mNoteList);
                            binding.rvNotes.setAdapter(mNoteAdapters);
                        }else{
                            mNoteAdapters.notifyDataSetChanged();
                        }
                    }
                };

      mViewModel=new ViewModelProvider(MainActivity.this).get(MainActivityViewModel.class);

      mViewModel.mNoteList.observe(MainActivity.this,noteObserver);

    }


    private void initRecycleView() {
        binding.rvNotes.hasFixedSize();
        LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
        binding.rvNotes.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(binding.rvNotes.getContext(),layoutManager.getOrientation());
        binding.rvNotes.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteNote(mNoteAdapters.getNoteAtPosition(viewHolder.getAdapterPosition()));
            }
        });

        itemTouchHelper.attachToRecyclerView(binding.rvNotes);

    }

    private void deleteNote(Note noteAtPosition) {
        mViewModel.deleteNote(noteAtPosition);
        Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.add_sample_date:
                addSampleData();
                return true;
            case R.id.delete_all_data:
                deleteAllData();
                return true;
        }
        
        
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData() {
        mViewModel.deleteAllNotes();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }

}