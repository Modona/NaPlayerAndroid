package com.a1440707245.mwh.naplayerdemo.dbutil.download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.a1440707245.mwh.naplayerdemo.down.DownloadThreadInfo;

/**
 * Created by Modona on 2017/1/2.
 */

public class DownloadImpl implements  DownloadDAO {
    private SQLiteDatabase sqLiteDatabase;
    public DownloadImpl(Context context)
    {
     this.sqLiteDatabase=new DownloadSql(context).getWritableDatabase();
    };
    @Override
    public void insertThread(String downloadurl, String videoid, int index,int length) {
        sqLiteDatabase.execSQL("insert into download values(?,?,?,?,?,?)",new Object[]{downloadurl,videoid,0,0,length,index});
    }

    @Override
    public DownloadThreadInfo findThread(int index, String videoid) {
        Cursor cursor= sqLiteDatabase.rawQuery("select * from download where  videoid=? and vindex=?",new String[]{String.valueOf(index),videoid});
        DownloadThreadInfo downloadThreadInfo=null;
        if(cursor.moveToNext())
        {
           downloadThreadInfo=new DownloadThreadInfo();
            downloadThreadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            downloadThreadInfo.setVideoid(cursor.getString(cursor.getColumnIndex("videoid")));
            downloadThreadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            downloadThreadInfo.setIndex(cursor.getInt(cursor.getColumnIndex("index")));
            downloadThreadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            downloadThreadInfo.setLength(cursor.getInt(cursor.getColumnIndex("length")));
        }
        return downloadThreadInfo;
        }

    @Override
    public void updateThread(int index, String videoid, int start,int finished) {
        sqLiteDatabase.execSQL("update download set start=?,finished=? where videoid=? and vindex=?",new Object[]{start,finished,videoid,index});
    }

    @Override
    public void deleteThread(int index, String videoid) {
        sqLiteDatabase.execSQL("delete from download where vindex=? and videoid=?",new Object[]{index,videoid});
    }
    public void closeDb()
    {
     sqLiteDatabase.close();
    }
}
