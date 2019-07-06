package me.yaacob.notesapp.persistence;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDao {

    @Query("select * from note")
    List<Note> getNotes();
    @Insert
    void insert(Note note);

    @Query("delete from note")
    void clear();
    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

    @Query("select count(*) from note")
    int getSize();

}


