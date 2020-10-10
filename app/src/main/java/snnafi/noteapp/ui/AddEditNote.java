package snnafi.noteapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import snnafi.noteapp.Constant;
import snnafi.noteapp.adapter.PriorityPickerAdapter;
import snnafi.noteapp.database.DatabaseHelper;
import snnafi.noteapp.databinding.NoteEditBinding;
import snnafi.noteapp.model.Note;

public class AddEditNote extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private NoteEditBinding binding;
    private Note note;
    private boolean isNote = false;

    private DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NoteEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        note = new Note();
        db = new DatabaseHelper(this);
        binding.spinner.setOnItemSelectedListener(this);
        PriorityPickerAdapter adapter = new PriorityPickerAdapter(Constant.PRIORITIES);
        binding.spinner.setAdapter(adapter);

        if (getIntent().hasExtra(Constant.CONTENT)) {
            isNote = true;
            Bundle data = getIntent().getExtras();
            note = data.getParcelable(Constant.CONTENT);
            Log.d("NOTE", note.toString());
            binding.tt.setText("Edit Note");
            binding.title.setText(note.getTitle());
            binding.content.setText(note.getContent());
            binding.spinner.setSelection(note.getPriority());

        }

        binding.finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNote) {
                    if (checkInputs()) {
                        note.setTitle(binding.title.getText().toString().trim());
                        note.setContent(binding.content.getText().toString().trim());
                        db.updateNote(note);
                        Log.d("NOTE", note.toString());
                    }

                } else {
                    if (checkInputs()) {
                        note.setTitle(binding.title.getText().toString().trim());
                        note.setContent(binding.content.getText().toString().trim());
                        db.addNote(note);
                        Log.d("NOTE", note.toString());
                    }

                }

                Intent i = getBaseContext().getPackageManager().
                        getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(i);
                finish();
            }

        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                note.setPriority(i);
                break;
            case 1:
                note.setPriority(i);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean checkInputs() {
        if (binding.title.getText().toString().isEmpty()) {
            binding.title.setError("Please add title");
        } else if (binding.content.getText().toString().isEmpty()) {
            binding.content.setError("Please add content");
        }
        return !binding.title.getText().toString().isEmpty() && !binding.title.getText().toString().isEmpty();
    }
}
