package com.example.healthbuddy.healthtips;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "health.db";
    private static final String TABLE_NAME = "health_table";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_INSTRUCTIONS = "instructions";
    private static final String COLUMN_FREQUENCY = "frequency";
    private static final String COLUMN_DATE = "date";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table  " + TABLE_NAME + "(" +
                        COLUMN_ID + " integer primary key autoincrement, " +
                        COLUMN_NAME + " varchar(10) ," +
                        COLUMN_INSTRUCTIONS + " varchar(20) , " +
                        COLUMN_FREQUENCY + " varchar(15)," +
                        COLUMN_DATE + " text)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    long inserthealth(Health health) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, health.getName());
        values.put(COLUMN_INSTRUCTIONS, health.getInstructions());
        values.put(COLUMN_FREQUENCY, health.getFrequency());
        values.put(COLUMN_DATE, health.getDate());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;

    }

    List<Health> getHealth() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Health> health = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Health health1 = new Health();
                health1.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                health1.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                health1.setInstructions(cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUCTIONS)));
                health1.setFrequency(cursor.getString(cursor.getColumnIndex(COLUMN_FREQUENCY)));
                health1.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                health.add(health1);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return health;


    }

    Health getHealths(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_INSTRUCTIONS, COLUMN_FREQUENCY, COLUMN_DATE};
        String selection = COLUMN_ID + " =?";
        String[] selectionArgs = new String[]{id};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            Health health = new Health();

            health.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            health.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            health.setInstructions(cursor.getString(cursor.getColumnIndex(COLUMN_INSTRUCTIONS)));
            health.setFrequency(cursor.getString(cursor.getColumnIndex(COLUMN_FREQUENCY)));
            health.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            cursor.close();
            db.close();
            return health;

        } else {
            db.close();
            return null;
        }
    }

    int updateStudent(Health health) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,health.getName());
        values.put(COLUMN_INSTRUCTIONS, health.getInstructions());
        values.put(COLUMN_FREQUENCY, health.getFrequency());
        values.put(COLUMN_DATE, health.getDate());
        int noOfRowsUpdate = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{health.getId() + ""});
        db.close();
        return noOfRowsUpdate;
    }

    int deleteStudent(int stdId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int noOfRowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{stdId + ""});
        db.close();
        return noOfRowsDeleted;

    }
}
