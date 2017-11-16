package com.sportspartner.util.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sportspartner.models.NightMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yujiaxiao on 11/15/17.
 */

public class NightModeDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Night.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SportPartnerDBContract.NightModeDB.TABLE_NAME + " (" +
                    SportPartnerDBContract.NightModeDB.COLUMN_USERID + " TEXT PRIMARY KEY," +
                    SportPartnerDBContract.NightModeDB.COLUMN_START_TIME + " TEXT," +
                    SportPartnerDBContract.NightModeDB.COLUMN_END_TIME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SportPartnerDBContract.NightModeDB.TABLE_NAME;

    // implement the singleton pattern
    private static NightModeDBHelper instance;

    private NightModeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static NightModeDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NightModeDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * get all records
     *
     * @return all record
     */
    public ArrayList<NightMode> getAll() throws ParseException {
        ArrayList<NightMode> allMode = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SportPartnerDBContract.NightModeDB.TABLE_NAME, null);

        if (cursor.moveToFirst())

        {
            while (!cursor.isAfterLast()) {
                String start = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.NightModeDB.COLUMN_START_TIME));
                String end = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.NightModeDB.COLUMN_END_TIME));

                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

                Date startDate = format.parse(start);
                Date endDate = format.parse(end);
                allMode.add(new NightMode(startDate, endDate));
                cursor.moveToNext();
            }
        }
        return allMode;
    }

    /**
     * Insert a new notification record.
     * @param startDate the start time of the night mode
     * @param endDate   the end time of the night mode
     */
    public void insert(String id, Date startDate, Date endDate) {
        //parse the date
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

        String start = format.format(startDate);
        String end = format.format(endDate);

        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SportPartnerDBContract.NightModeDB.COLUMN_USERID, id);
        values.put(SportPartnerDBContract.NightModeDB.COLUMN_START_TIME, start);
        values.put(SportPartnerDBContract.NightModeDB.COLUMN_END_TIME, end);

        // Insert the new row, returning the primary key value of the new row
        db.insert(SportPartnerDBContract.NightModeDB.TABLE_NAME, null, values);
    }

    /**
     * update the record by Id
     * @param id the email of the user
     * @param startDate The new start date of the night mode
     * @param endDate The new end date of the night mode
     */
    public void updateById(String id, Date startDate, Date endDate) {
        //parse the date
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

        String start = format.format(startDate);
        String end = format.format(endDate);

        SQLiteDatabase db = this.getWritableDatabase();
        //values
        ContentValues values = new ContentValues();
        values.put(SportPartnerDBContract.NightModeDB.COLUMN_START_TIME, start);
        values.put(SportPartnerDBContract.NightModeDB.COLUMN_END_TIME, end);
        //args
        String[] args = {id};
        //update
        db.update(SportPartnerDBContract.NightModeDB.TABLE_NAME, values, SportPartnerDBContract.NightModeDB.COLUMN_USERID + "=?", args);
    }


    /**
     * Clear the  records.
     */
    public void deleteAllrows() {
        // clear the table
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SportPartnerDBContract.NightModeDB.TABLE_NAME, null, null);
    }


    // implement methods from superclass
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean isEmpty() {
        ArrayList<NightMode> allMode = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SportPartnerDBContract.NightModeDB.TABLE_NAME, null);

        int size = cursor.getCount();
        return (size == 0);
    }
}
