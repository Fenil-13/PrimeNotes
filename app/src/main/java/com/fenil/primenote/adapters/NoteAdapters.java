package com.fenil.primenote.adapters;

import static com.fenil.primenote.utils.Constants.NOTE_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fenil.primenote.activities.EditNoteActivity;
import com.fenil.primenote.databinding.ItemNoteBinding;
import com.fenil.primenote.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapters extends RecyclerView.Adapter<NoteAdapters.MyViewHolder> {
    private Context context;
    private List<Note> mNoteList;

    public NoteAdapters(Context context, List<Note> mNoteList) {
        this.context = context;
        this.mNoteList = mNoteList;
    }



    @NonNull
    @Override
    public NoteAdapters.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding=ItemNoteBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyViewHolder(binding);

    }



    @Override
    public void onBindViewHolder(@NonNull NoteAdapters.MyViewHolder holder, int position) {
        holder.binding.tvNotesTitle.setText(mNoteList.get(position).getData());
        holder.binding.fabEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent=new Intent(context, EditNoteActivity.class);
                editIntent.putExtra(NOTE_ID_KEY,mNoteList.get(position).getId());
                context.startActivity(editIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemNoteBinding binding;
        public MyViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public Note getNoteAtPosition(int position){
        return mNoteList.get(position);
    }
}
