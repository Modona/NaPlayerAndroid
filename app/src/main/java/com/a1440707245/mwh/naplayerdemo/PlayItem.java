package com.a1440707245.mwh.naplayerdemo;

/**
 * Created by Modona on 2016/12/3.
 */

public class PlayItem {
    private String playurl;
    private String imageurl;
    private String playname;
    public PlayItem(String pu,String iu,String pn)
    {
        playurl=pu;
        imageurl=iu;
        playname=pn;
    }

    public String getPlayurl() {
        return playurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getPlayname() {
        return playname;
    }
}
