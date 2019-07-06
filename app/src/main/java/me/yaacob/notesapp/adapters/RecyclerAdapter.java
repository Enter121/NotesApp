package me.yaacob.notesapp.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.Date;
import java.util.List;

import me.yaacob.notesapp.R;
import me.yaacob.notesapp.activities.MainActivity;
import me.yaacob.notesapp.activities.NoteActivity;
import me.yaacob.notesapp.managers.NoteManager;
import me.yaacob.notesapp.persistence.Note;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public List<Note> notes;
    NoteManager noteManager;
    MainActivity activity;

    public RecyclerAdapter(MainActivity activity,NoteManager manager) {
        this.noteManager=manager;
        this.activity=activity;
        this.notes = noteManager.getNotes();
        noteManager.refreshNotes();

    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(getEntryView(parent));
    }

    public View getEntryView(ViewGroup parent){
       return LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_listitem,parent,false);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note n =notes.get(position);
        holder.txt.setText(n.getTitle());
        holder.cont.setText(n.getContent());
        Date d=new Date(n.getDate());
        holder.dat.setText(d.toString());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    public void refresh(){
        if(notes!=null)notes.clear();
        notes=noteManager.getNotes();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt, cont, dat;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt=itemView.findViewById(R.id.tit);
            cont=itemView.findViewById(R.id.cont);
            dat=itemView.findViewById(R.id.dat);
           itemView.findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent=new Intent(activity, NoteActivity.class);
                Bundle b=new Bundle();
                b.putInt("noteId",getAdapterPosition());
                intent.putExtras(b);

                activity.startActivity(intent);
                }
            });
        }
    }
}
