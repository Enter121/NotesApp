package me.yaacob.notesapp.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import me.yaacob.notesapp.managers.NoteManager;
import me.yaacob.notesapp.R;
import me.yaacob.notesapp.adapters.RecyclerAdapter;
import me.yaacob.notesapp.managers.SwipeManager;

public class MainActivity extends AppCompatActivity {

    static MainActivity activity;
    TextView info;
    RecyclerView rec;
    RecyclerAdapter adapter;
    FloatingActionButton fab;


    NoteManager noteManager;
    SwipeManager swipeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity=this;


        //initManagers
        noteManager=new NoteManager(getInstance());
        swipeManager=new SwipeManager();


        //getAllViews
        rec=findViewById(R.id.recycler_view);
        adapter=new RecyclerAdapter(this,noteManager);
        info=findViewById(R.id.info);
        fab = findViewById(R.id.fab);


        //setupViews
        rec.setAdapter(adapter);
        rec.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getInstance(), NoteActivity.class);
                startActivity(intent);
            }
        });


        //init
        refreshInfo(info);
        swipeManager.initSwipe(this);



    }
    public void refreshInfo(TextView view){
        view.setText((String)((adapter.notes.size()==0)?getResources().getString(R.string.no_notes):""));
    }


    public void refresh(){
        adapter.refresh();
        refreshInfo(info);
    }

    public NoteManager getNoteManager() {
        return noteManager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear) {


            Dialog.OnClickListener clickListener=new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i){
                        case DialogInterface.BUTTON_POSITIVE:
                            noteManager.clear();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder b =new AlertDialog.Builder(activity);
            b.setMessage(R.string.rusure).setPositiveButton(R.string.yes,clickListener).setNegativeButton(R.string.no,clickListener);
            b.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public RecyclerAdapter getAdapter() {
        return adapter;
    }

    public MainActivity getInstance(){
        return this;
    }
    public RecyclerView getRecyclerView() {
        return rec;
    }

}
