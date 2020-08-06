package com.kikimore.leduino;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class openDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "DeviceDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Devices";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TYPE = "type";

    public openDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " + COLUMN_TYPE + "TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor cursor(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String title, String type, String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TYPE, type);

        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
        if(result == -1){
            Toast toast = Toast.makeText(context, "Failed", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void deleteData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"id=?", new String[]{row_id});
    }
    public void addInfo(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == - 1){
            Toast toast = Toast.makeText(context, "Failed", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
