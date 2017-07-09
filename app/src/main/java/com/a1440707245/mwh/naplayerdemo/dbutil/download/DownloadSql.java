package com.a1440707245.mwh.naplayerdemo.dbutil.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Modona on 2017/1/2.
 */

public class DownloadSql extends SQLiteOpenHelper {
    final  static String DBNAME="download.db";
    final static int VERSION=1;
    public DownloadSql(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists download(url varchar(255),videoid varchar(255),start int(255),finished int(255),length int(255),vindex int(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
