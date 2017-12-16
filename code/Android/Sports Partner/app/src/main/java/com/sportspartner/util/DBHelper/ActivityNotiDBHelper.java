package com.sportspartner.util.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sportspartner.models.ActivityNoti;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by xuanzhang on 12/13/17.
 */

public class ActivityNotiDBHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Activitynoti.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SportPartnerDBContract.ActivityNotiDB.TABLE_NAME + " (" +
                    SportPartnerDBContract.ActivityNotiDB.COLUMN_ACTIVITYID + " TEXT PRIMARY KEY," +
                    SportPartnerDBContract.ActivityNotiDB.COLUMN_START_TIME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SportPartnerDBContract.ActivityNotiDB.TABLE_NAME;

    // implement the singleton pattern
    private static ActivityNotiDBHelper instance;

    private ActivityNotiDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ActivityNotiDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ActivityNotiDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public void insert(String activityId, String startTime){
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SportPartnerDBContract.ActivityNotiDB.COLUMN_ACTIVITYID, activityId);
        values.put(SportPartnerDBContract.ActivityNotiDB.COLUMN_START_TIME, startTime);

        // Insert the new row, returning the primary key value of the new row
        db.insert(SportPartnerDBContract.ActivityNotiDB.TABLE_NAME, null, values);
    }

    public void deleteById(String uuid){
        String[] arg = new String[1];
        arg[0] = uuid;

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SportPartnerDBContract.ActivityNotiDB.TABLE_NAME, SportPartnerDBContract.ActivityNotiDB.COLUMN_ACTIVITYID + "=? ", arg);
    }

    public ActivityNoti getLatestActivity() throws ParseException {
        ActivityNoti activityNoti = new ActivityNoti();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ SportPartnerDBContract.ActivityNotiDB.TABLE_NAME + " ORDER BY " + SportPartnerDBContract.ActivityNotiDB.COLUMN_START_TIME + " ASC LIMIT 1", null);

        if(cursor.moveToFirst()) {
            if (!cursor.isAfterLast()) {
                String uuid = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.ActivityNotiDB.COLUMN_ACTIVITYID));
                String time = cursor.getString(cursor.getColumnIndex(SportPartnerDBContract.ActivityNotiDB.COLUMN_START_TIME));

                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

                Date date = format.parse(time);
                activityNoti.setActivityId(uuid);
                activityNoti.setStartTime(date);
            }
        }
        return activityNoti;
    }

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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SportPartnerDBContract.ActivityNotiDB.TABLE_NAME, null);
        int size = cursor.getCount();
        return (size == 0);
    }
}
