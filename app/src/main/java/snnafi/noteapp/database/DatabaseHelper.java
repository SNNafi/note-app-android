package snnafi.noteapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import snnafi.noteapp.Constant;
import snnafi.noteapp.model.Note;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, "Notes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_NOTE = "CREATE TABLE " + Constant.TABLE + " ("
                + Constant.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constant.TITLE + " TEXT, "
                + Constant.CONTENT + " TEXT, "
                + Constant.PRIORiTY + " INTEGER DEFAULT 0) ";


        sqLiteDatabase.execSQL(SQL_NOTE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP = "DROP TABLE IF EXISTS " + Constant.TABLE;
        sqLiteDatabase.execSQL(DROP);
        onCreate(sqLiteDatabase);

    }


    public List<Note> getAllNotes() {
        String SQL = "SELECT * FROM " + Constant.TABLE;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL, null);
        ArrayList<Note> notes = new ArrayList<>();
        assert cursor != null;
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setPriority(cursor.getInt(3));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }


    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.TITLE, note.getTitle());
        values.put(Constant.CONTENT, note.getContent());
        values.put(Constant.PRIORiTY, note.getPriority());
        db.insert(Constant.TABLE, null, values);
        db.close();
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constant.TABLE, Constant.ID + "=?", new String[]{Integer.toString(note.getId())});
        db.close();
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.TITLE, note.getTitle());
        values.put(Constant.CONTENT, note.getContent());
        values.put(Constant.PRIORiTY, note.getPriority());
        int update = db.update(Constant.TABLE, values, Constant.ID + "=?",
                new String[]{Integer.toString(note.getId())});

        db.close();
        return update;
    }
}
