package snnafi.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import snnafi.noteapp.adapter.NoteListAdapter;
import snnafi.noteapp.database.DatabaseHelper;
import snnafi.noteapp.databinding.ActivityMainBinding;
import snnafi.noteapp.model.Note;
import snnafi.noteapp.ui.AddEditNote;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHelper db;
    private ArrayList<Note> noteArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DatabaseHelper(this);
        noteArrayList = new ArrayList<>();
        noteArrayList.addAll(db.getAllNotes());
        binding.notes.setHasFixedSize(true);
        binding.notes.setLayoutManager(new LinearLayoutManager(this));
        final NoteListAdapter noteListAdapter = new NoteListAdapter(noteArrayList);
        binding.notes.setAdapter(noteListAdapter);
        noteListAdapter.notifyDataSetChanged();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Note note = noteArrayList.get(viewHolder.getAdapterPosition());
                noteListAdapter.removeNote(viewHolder.getAdapterPosition());
                db.deleteNote(note);
            }
        }).attachToRecyclerView(binding.notes);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_note) {
            startActivity(new Intent(getApplicationContext(), AddEditNote.class));
        }


        return super.onOptionsItemSelected(item);
    }
}