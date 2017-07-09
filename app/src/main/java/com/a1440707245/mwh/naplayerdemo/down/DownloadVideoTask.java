package com.a1440707245.mwh.naplayerdemo.down;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.a1440707245.mwh.naplayerdemo.dbutil.download.DownloadImpl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Modona on 2017/1/1.
 */

public class DownloadVideoTask {
    private DownloadThreadInfo downloadThreadInfo;
    private List<DownloadThreadInfo> downloadList;
    private List<String> downloadUrl;
    private  static String storagePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Nadownload";
    private boolean isPause=true;
    private DownloadImpl downloadDao=null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0001:
                    downloadBegin();
                    break;
            }
        }
    };
    public DownloadVideoTask(Context context){
        downloadDao=new DownloadImpl(context);
        downloadList=new ArrayList<DownloadThreadInfo>();
        downloadUrl=new ArrayList<String>();
        File file=new File(storagePath);
        if(!file.isDirectory()) {

            file.mkdir();
        }
    }
    public void addDownloadList(DownloadThreadInfo downloadThreadInfo,String url)
    {
        downloadList.add(downloadThreadInfo);
        downloadUrl.add(url);
    }
    public boolean isDownloading(){
        return !isPause;
    }
    public void downloadBegin(){
        downloadThreadInfo=downloadList.get(0);
        if(downloadDao.findThread(downloadThreadInfo.getIndex(),downloadThreadInfo.getVideoid())==null)
            downloadDao.insertThread(downloadThreadInfo.getUrl(),downloadThreadInfo.getVideoid(),
                    downloadThreadInfo.getIndex(),downloadThreadInfo.getLength());
        new DownloadThread(downloadThreadInfo,downloadUrl.get(0)).start();
    }
    public void downloadPause(){
        isPause=true;
    }
    public void downloadDel(){
        downloadPause();
    }
    class DownloadThread extends Thread{
        private String url;
        private int start;
        private  String localpath;
        private  String videoid;
        private int index;
        private int length;
        private  int finished;
        HttpURLConnection httpURLConnection;
        BufferedInputStream bin;
        RandomAccessFile raf;
        public DownloadThread(DownloadThreadInfo downloadThreadInfo,String videourl)
        {
            this.url=videourl;
            this.videoid=downloadThreadInfo.getVideoid();
            this.start=downloadThreadInfo.getStart();
            this.length=downloadThreadInfo.getLength();
            this.index=downloadThreadInfo.getIndex();
            this.finished=downloadThreadInfo.getFinished();
            this.localpath=storagePath+"/"+downloadThreadInfo.getVideoid()+"&"+downloadThreadInfo.getIndex()+".tmp";
        }
        @Override
        public void run() {
            try {
                isPause=false;
                byte []buf=new byte[1024 * 4];
                Log.i("result",url);
                URL downloadurl=new URL(url);
                httpURLConnection= (HttpURLConnection) downloadurl.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Range","bytes="+start+"-");
                BufferedInputStream inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
                raf = new RandomAccessFile(localpath, "rwd");
                raf.setLength(length);
                raf.seek(start);
                int readLength;
                while ((readLength=inputStream.read(buf))!= -1) {
                        raf.write(buf,0,readLength);
                        start+=readLength;
                        finished+=readLength;
                        downloadDao.updateThread(index,videoid,start,finished);
                    if(isPause) {
                        return;
                    }
                    }
                File file=new File(localpath);
                file.renameTo(new File(localpath.replaceAll("tmp","mp4")));
                downloadList.remove(0);
                downloadUrl.remove(0);
                downloadDao.deleteThread(index,videoid);
                if(downloadList.size()>0)
                {
                    Message msg=handler.obtainMessage();
                    msg.what=0001;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();

                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
