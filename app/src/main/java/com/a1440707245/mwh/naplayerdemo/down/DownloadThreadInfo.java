package com.a1440707245.mwh.naplayerdemo.down;

import java.io.Serializable;

/**
 * Created by Modona on 2017/1/2.
 */

public class DownloadThreadInfo implements Serializable {
    private int start;
    private int length;
    private String videoid;
    private int index;
    private String url;
    private int finished;

    public DownloadThreadInfo() {
    }

    public DownloadThreadInfo(String url) {
        this.url = url;
    }

    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public String getUrl() {
        return url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return  "start"+start+
                "length"+length+
        "videoid"+ videoid+
        "index"+index+
        "url"+ url+
        "finished"+finished;
    }
}
