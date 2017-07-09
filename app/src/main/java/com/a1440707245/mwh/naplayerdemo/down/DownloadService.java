package com.a1440707245.mwh.naplayerdemo.down;


import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.a1440707245.mwh.naplayerdemo.NaCrawl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Modona on 2017/1/2.
 */

public class DownloadService extends Service {
    public final  static String ACTION_DOWN="ACTION_DOWN";
    public final  static String ACTION_STOP="ACTION_STOP";
    public final  static String ACTION_DEL="ACTION_DEL";

    private DownloadThreadInfo downloadThreadInfo;
    private  DownloadVideoTask downloadTask;
    private String videourl;
    private   HttpURLConnection conn;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 001:
                    Map<String,Object> map=(HashMap<String,Object>)msg.obj;
                    downloadThreadInfo.setLength((Integer) map.get("length"));
                    downloadThreadInfo.setIndex((Integer) map.get("index"));
                    downloadThreadInfo.setVideoid((String) map.get("videoid"));
                    Log.i("result",downloadThreadInfo.toString());
                    if(downloadTask==null) {
                        downloadTask = new DownloadVideoTask(getApplicationContext());
                    }
                    downloadTask.addDownloadList(downloadThreadInfo,videourl);
                    if(!downloadTask.isDownloading())
                    downloadTask.downloadBegin();
                    break;
                case 004:
                    Toast.makeText(getApplicationContext(),"Connect Server Error",Toast.LENGTH_SHORT).show();
                    break;
                case 401:
                    videourl= Uri.decode((String)msg.obj).replace("%3A",":").replace("%2F","/")+".mp4";
                    initDownload(downloadThreadInfo.getUrl(),videourl);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch(intent.getAction())
        {
            case ACTION_DOWN:
                Log.i("result","download");
                downloadThreadInfo= (DownloadThreadInfo) intent.getSerializableExtra("downloadInfo");
                new NaCrawl().detailCrawl(downloadThreadInfo.getUrl()+"&nns_tag=31",handler);
                break;
            case ACTION_STOP:
                downloadTask.downloadPause();
                break;
            case ACTION_DEL:
                break;

        }
        return super.onStartCommand(intent, flags, startId);
    }
    private void initDownload(final String url,final String downloadUrl) {
        new Thread(){
            public void run(){
                try {
                    int index=0;
                    String videoid;
                    Pattern pat= Pattern.compile("nns_video_index=(\\d*)");
                    Matcher mat=pat.matcher(url);
                    if(mat.find())
                        index= Integer.parseInt(mat.group(1));
                    pat=Pattern.compile("nns_video_id=([\\d\\w]*)");
                    mat=pat.matcher(url);
                    if(mat.find())
                        videoid=mat.group(1);

                    else videoid=null;
                    Log.i("result",videoid);
                    URL url=new URL(downloadUrl);
                    Log.i("result",downloadUrl);
                    conn= (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    if(conn.getResponseCode()==HttpURLConnection.HTTP_OK)
                    {
                        String length=conn.getHeaderField("Content-Length");
                        Message msg=handler.obtainMessage();
                        msg.what=001;
                        Map<String,Object> map=new HashMap<String,Object>();
                        map.put("length", Integer.valueOf(length));
                        map.put("index",index);
                        map.put("videoid",videoid);
                        msg.obj=map;
                        handler.sendMessage(msg);

                    }
                    else{
                        Message errormsg=handler.obtainMessage();
                        errormsg.what=004;
                        handler.sendMessage(errormsg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    conn.disconnect();
                }
            }
        }.start();
    }
}
