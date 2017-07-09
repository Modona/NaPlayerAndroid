package com.a1440707245.mwh.naplayerdemo.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Modona on 2016/12/23.
 */

public class SqLiteOperater extends SQLiteOpenHelper{
    private static final String name = "subscribe.db"; //数据库名称
    private static final int version = 1;
    public SqLiteOperater(Context context)
    {
            super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists subscribe(videoname varchar(20),imageurl varchar(150),videourl varchar(150))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
