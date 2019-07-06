package me.yaacob.notesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import me.yaacob.notesapp.R;
import me.yaacob.notesapp.persistence.Note;

public class NoteActivity extends AppCompatActivity {

    boolean edit=false;
    int id;
    Note note=null;
    EditText title;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Bundle b=getIntent().getExtras();
        if(b!=null) {
            edit = b.containsKey("noteId");
            id = b.getInt("noteId");
            note = MainActivity.activity.noteManager.getNotes().get(id);
            if (note != null) {
                title = findViewById(R.id.title);
                content = findViewById(R.id.content);
                title.setText(note.getTitle());
                content.setText(note.getContent());
                content.setSelection(note.getContent().length());
                title.setActivated(false);
                content.setActivated(true);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addnote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.back) {
            finish();
            return true;
        }
        if(id == R.id.done){
            if(!edit) {
                Note note = new Note(((TextView) findViewById(R.id.title)).getText().toString(), ((TextView) findViewById(R.id.content)).getText().toString());
                MainActivity.activity.noteManager.newNote(note);
            }else{
                note.setTitle(title.getText().toString());
                note.setContent(content.getText().toString());
                MainActivity.activity.noteManager.update(note);
            }
            finish();

            return true;
        }

         return super.onOptionsItemSelected(item);
    }
}
