package com.a1440707245.mwh.naplayerdemo;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Modona on 2016/11/6.
 */

public class NaCrawl {
    public  void mainCrawl(final Handler handler)  {
        final List<PlayItem> list=new ArrayList<PlayItem>();

      new Thread(){
          public void run(){
              try {
                  URL url=new URL("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php");
                  HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                  conn.setConnectTimeout(2000);
                  conn.setReadTimeout(2000);
                  conn.setRequestMethod("GET");
                  int code=conn.getResponseCode();
                  if(code==200)
                  {
                      String buf;
                      StringBuffer buffer=new StringBuffer();
                      InputStream fin= conn.getInputStream();
                      BufferedReader br=new BufferedReader(new InputStreamReader(fin,"UTF-8"));
                      StringBuffer sb=new StringBuffer();
                      while((buf=br.readLine())!=null)
                      {
                          sb.append(buf);

                      }
                      String result=new String(sb);
                      sb=null;
                      result=result.replaceAll("[\t\r\n]", "");
                      result=result.substring(19842);
                      String regx="(?:(?:\\\"><ul>)|(?:</li>))<li><a href=\\\"(.*?)\\\"><div  class=\\\"shadow\\\"><img src=\\\"img/shadow.png\\\" alt=\\\"\\\" /></div><img style=\\\"width:116px;height:156px;\\\" src=\\\"(.*?)\\\" alt=\\\"\\\"><span class=\\\"programName\\\">(.*?)</span></a>";
                      Pattern p=Pattern.compile(regx);
                      Matcher m=p.matcher(result);
                      int i=1;
                      PlayItem playItem;
                      while(m.find())
                      {
                         playItem=new PlayItem(m.group(1),m.group(2),m.group(3));
                          list.add(playItem);
                      }

                      result=null;
                      Message msg=handler.obtainMessage();
                      msg.what=101;

                      msg.obj=list;
                      handler.sendMessage(msg);
                  }
                  else{

                  }
              }catch (Exception e)
              {
                  Message msg=handler.obtainMessage();
                  msg.what=104;
                  handler.sendMessage(msg);

              }


        }}.start();
    }
    public  void keyCrawl(final String url,final Handler handler){
        final List<PlayItem> list=new ArrayList<PlayItem>();
        new Thread(){
            public  void run(){
                try {
                    URL crawlurl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) crawlurl.openConnection();
                    conn.setConnectTimeout(2000);
                    conn.setRequestMethod("GET");

                    int code = conn.getResponseCode();
                    if (code == 200) {
                        String buf;
                        StringBuffer buffer = new StringBuffer();
                        InputStream fin = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
                        StringBuffer sb = new StringBuffer();
                        while ((buf = br.readLine()) != null) {
                            sb.append(buf);

                        }
                        String result = new String(sb);
                        sb = null;
                        result = result.replaceAll("[\t\r\n]", "");
                        String regx="<li ><a href=\\\"(.*?)\\\"><div  class=\\\"shadow\\\"><img src=\\\"img/shadow.png\\\" alt=\\\"\\\" /></div><img style=\\\"width:116px;height:156px;\\\" src=\\\"(.*?)\\\" alt=\\\"\\\"><span class=\\\"programName\\\">(.*?)</span></a></li>";
                        String indexRegx="<div class=\\\"movie_page_tip\\\">.*?/(.*?)[\\\\s]*?</div>";
                        Pattern p=Pattern.compile(regx);
                        Matcher m=p.matcher(result);
                        PlayItem playItem;
                        while(m.find())
                        {
                            playItem=new PlayItem(m.group(1),m.group(2),m.group(3));
                            list.add(playItem);
                        }

                        p=Pattern.compile(indexRegx);
                        m=p.matcher(result);
                        String index=new String("0");
                        while(m.find())
                        {
                            index=m.group(1).trim();
                        }
                        playItem=new PlayItem(index,index,index);
                        list.add(playItem);
                        result=null;
                        Message msg=handler.obtainMessage();
                        msg.what=701;

                        msg.obj=list;
                        handler.sendMessage(msg);

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
    public  void secondFragmentCrawl(final Handler handler)
    {
        final List<Map<String,String>> list=new ArrayList<Map<String, String>>();

        new Thread(){
            public void run(){
                try {
                    URL url=new URL("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php");
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(2000);
                    conn.setRequestMethod("GET");

                    int code=conn.getResponseCode();
                    if(code==200)
                    {
                        String buf;
                        StringBuffer buffer=new StringBuffer();
                        InputStream fin= conn.getInputStream();
                        BufferedReader br=new BufferedReader(new InputStreamReader(fin,"UTF-8"));
                        StringBuffer sb=new StringBuffer();
                        while((buf=br.readLine())!=null)
                        {
                            sb.append(buf);

                        }
                        String result=new String(sb);
                        sb=null;
                        result=result.replaceAll("[\t\r\n]", "");
                        result=result.substring(19842);
                        String regx="<a class=\\\"upVideo\\\" href=\\\"(.*?)\\\"><span style=\\\"position: relative;display: block;\\\">(.*?)</span></a>";
                        Pattern p=Pattern.compile(regx);
                        Matcher m=p.matcher(result);
                        Map<String,String> map;
                        while(m.find())
                        {
                            map=new HashMap<String, String>();
                            map.put("playurl",m.group(1));
                            map.put("videoname",m.group(2));
                            list.add(map);
                        }
                        result=null;
                        Message msg=handler.obtainMessage();
                        msg.what=201;
                        msg.obj=list;
                        handler.sendMessage(msg);
                    }
                    else{

                    }
                }catch(Exception e)
                {
                    e.printStackTrace();

                }

            }}.start();

    }
    public  void detailCrawl(final String url,final Handler handler)
    {
        new Thread(){
            public void run(){
                try {
                    String playurl=url;

                    URL url=new URL(playurl);
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int code=conn.getResponseCode();
                    if(code==200)
                    {
                        String buf;
                        StringBuffer buffer=new StringBuffer();
                        InputStream fin= conn.getInputStream();
                        BufferedReader br=new BufferedReader(new InputStreamReader(fin,"UTF-8"));
                        StringBuffer sb=new StringBuffer();
                        while((buf=br.readLine())!=null)
                        {
                            sb.append(buf);

                        }
                        String result=new String(sb);
                        sb=null;
                        String regx="play_url=(.*?)\\.mp4";
                        Pattern p=Pattern.compile(regx);
                        Matcher m=p.matcher(result);
                        Message message=handler.obtainMessage();
                        int i=0;
                        while(m.find())
                        {
                               message.obj= m.group(1);

                        }

                        message.what=401;

                        handler.sendMessage(message);
                        result=null;

                    }
                    else{

                    }
                }catch(Exception e)
                {
                    e.printStackTrace();

                }

            }}.start();
    }
    public void indexCrawl(final String url,final Handler handler)
    {
        final List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        new Thread(){

            public void run(){
                try {
                    String playurl=url;

                    URL url=new URL(playurl);
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int code=conn.getResponseCode();
                    if(code==200)
                    {
                        String buf;
                        StringBuffer buffer=new StringBuffer();
                        InputStream fin= conn.getInputStream();
                        BufferedReader br=new BufferedReader(new InputStreamReader(fin,"UTF-8"));
                        StringBuffer sb=new StringBuffer();
                        while((buf=br.readLine())!=null)
                        {
                            sb.append(buf);

                        }
                        String result=new String(sb);
                        sb=null;
                        result=result.substring(result.indexOf("<div class=\"selectNum\">"));
                        String regx="<a href=\"(.*?)\" class=\"button1 movie_page_indexa.*?\">(.*?)</a>";
                        Pattern p=Pattern.compile(regx);
                        Matcher m=p.matcher(result);
                        Message message=handler.obtainMessage();
                        int i=0;
                        Map map;
                        while(m.find())
                        {
                            map=new HashMap<String,String>();
                            map.put("index",m.group(2));
                            map.put("playurl",m.group(1));
                            list.add(map);
                        }
                        if(list.size()>0)
                        {
                        message.what=801;
                        message.obj=list;}
                        else {
                            message.what=802;
                        }
                        handler.sendMessage(message);
                        result=null;

                    }
                    else{

                    }
                }catch(Exception e)
                {
                    e.printStackTrace();

                }

            }}.start();

    }
    public void IntrodeceCrawl(final String url,final Handler handler)
    {
        new Thread(){

            public void run(){
                try {
                    String playurl=url;

                    URL url=new URL(playurl);
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int code=conn.getResponseCode();
                    if(code==200)
                    {
                        String buf;
                        StringBuffer buffer=new StringBuffer();
                        InputStream fin= conn.getInputStream();
                        BufferedReader br=new BufferedReader(new InputStreamReader(fin,"UTF-8"));
                        StringBuffer sb=new StringBuffer();
                        while((buf=br.readLine())!=null)
                        {
                            sb.append(buf);

                        }
                        String result=new String(sb);
                        sb=null;
                        String regx="<div class=\"article\">(.*?)</div>";
                        Pattern p=Pattern.compile(regx);
                        Matcher m=p.matcher(result);
                        Message message=handler.obtainMessage();
                        int i=0;
                        StringBuffer stringBuffer=new StringBuffer();
                        while(m.find())
                        {
                            String regx2="<p>(.*?)</p>";
                            Pattern p2=Pattern.compile(regx2);
                            Matcher m2=p2.matcher(m.group(1).trim());
                            while(m2.find())
                            {
                                stringBuffer.append(m2.group(1));
                            }
                        }

                        if(stringBuffer.length()>0)
                        {
                            message.what=803;
                            message.obj=new String(stringBuffer);}
                        else {
                            message.what=804;
                        }
                        handler.sendMessage(message);
                        result=null;

                    }
                    else{

                    }
                }catch(Exception e)
                {
                    e.printStackTrace();

                }

            }}.start();
    }
}
