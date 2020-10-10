package snnafi.noteapp.adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import snnafi.noteapp.Constant;
import snnafi.noteapp.R;
import snnafi.noteapp.model.Note;
import snnafi.noteapp.ui.AddEditNote;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.Noteholder> {

    private ArrayList<Note> notes;

    public NoteListAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public Noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Noteholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final Noteholder holder, int position) {

        final Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
        if (note.getPriority() == 0) {
            holder.priorityImage.setImageResource(R.drawable.arrow_yellow);
        } else {
            holder.priorityImage.setImageResource(R.drawable.arrow_red);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddEditNote.class);
                intent.putExtra(Constant.CONTENT, note);
                // intent.setFlags()
                view.getContext().startActivity(intent);

            }
        });

    }

    public void removeNote(int position) {
        notes.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class Noteholder extends RecyclerView.ViewHolder {

        private TextView title, content;
        private ImageView priorityImage;
        private Spinner spinner;

        public Noteholder(View noteView) {
            super(noteView);

            title = noteView.findViewById(R.id.title);
            content = noteView.findViewById(R.id.content);
            priorityImage = noteView.findViewById(R.id.priorityImage);

        }
    }
}
