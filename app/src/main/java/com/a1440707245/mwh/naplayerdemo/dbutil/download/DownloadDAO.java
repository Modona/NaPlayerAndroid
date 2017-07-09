package com.a1440707245.mwh.naplayerdemo.dbutil.download;

import com.a1440707245.mwh.naplayerdemo.down.DownloadThreadInfo;

/**
 * Created by Modona on 2017/1/2.
 */
interface DownloadDAO
{
    void insertThread(String downloadurl,String videoid,int index,int length);
    DownloadThreadInfo findThread(int index, String videoid);
    void updateThread(int index,String videoid,int start,int finished);
    void deleteThread(int index,String videoid);
}
