package com.example.kolibreath.onit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kolibree on 2016/11/13.
 */
public class NotesDB extends SQLiteOpenHelper {

    public static final  String TABLE_NAME = "notes";
    public static final  String CONTENT = "content";
    public static final  String ID = "_id";
    public static final  String TIME = "time";
    public static final String ENDTTIME = "endtime";
    public static final String AVATARID = "avatarid";


    public NotesDB(Context context){
        super(context,"notes",null,1);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CONTENT +" TEXT NOT NULL,"
                + ENDTTIME + " TEXT NOT NULL,"
                + AVATARID + "INTEGER, "
                + TIME + " TEXT NOT NULL)");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVerison){

    }
}
