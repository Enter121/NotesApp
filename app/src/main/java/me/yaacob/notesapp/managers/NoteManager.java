package me.yaacob.notesapp.managers;

import android.os.AsyncTask;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import me.yaacob.notesapp.activities.MainActivity;
import me.yaacob.notesapp.persistence.Note;
import me.yaacob.notesapp.persistence.NoteDatabase;

public class NoteManager {


    String databaseName="notes-database";

    MainActivity activity;
    NoteDatabase database;
    Runnable refreshUI , refnotes;
    List<Note> notes=new ArrayList<>();


    public NoteManager(final MainActivity activity) {
        this.activity=activity;
        //Refreshing notes runnable obj
        refnotes = new Runnable() {
            @Override
            public void run() {
                notes=database.noteDao().getNotes();
                activity.runOnUiThread(refreshUI);
            }
        };
        //Refreshing ui runnable obj
        refreshUI=new Runnable() {
            @Override
            public void run() {
                activity.refresh();
            }
        };
        initDatabase();
    }
    public void initDatabase(){
        database = Room.databaseBuilder(activity.getApplicationContext(),
                NoteDatabase.class, databaseName).build();

    }


    public List<Note> getNotes(){
        return notes;
    }

    public void refreshNotes(){
        asyncTask(refnotes);
    }

    public void newNote(final Note note){
        asyncTask(new Runnable() {
            @Override
            public void run() {
                database.noteDao().insert(note);
                refnotes.run();
            }
        });
    }

    public void update(final Note note){
        asyncTask(new Runnable() {
            @Override
            public void run() {
                database.noteDao().update(note);
                refnotes.run();
            }
        });
    }

    public void delete(final Note note){
        asyncTask(new Runnable() {
            @Override
            public void run() {
                database.noteDao().delete(note);
                refnotes.run();
            }
        });

    }

    //updating note
    public void clear(){
        asyncTask(new Runnable() {
            @Override
            public void run() {
                database.noteDao().clear();
                refnotes.run();
            }
        });

    }


    public void asyncTask(Runnable r){
        AsyncTask.execute(r);
    }


}
