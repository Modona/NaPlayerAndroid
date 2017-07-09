package com.a1440707245.mwh.naplayerdemo;

/**
 * Created by Modona on 2016/11/23.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.R.attr.type;

;

/**
 * Created by Modona on 2016/11/16.
 */

public class ImageLoader {
    private ImageView imageView;
    private String tagUrl;
    private int Type;
    public Bitmap bitmap;
    private Context context;
    private final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 201:
                    if ((imageView.getTag()).equals(tagUrl)) {
                        imageView.setImageBitmap((Bitmap)msg.obj);
                        }


                    break;

            }
        }

    };

    public ImageLoader() {

    }


    public void loadImage(final String ImageUrl, ImageView imageView) {
        tagUrl = ImageUrl;
        Type = type;
        this.context=context;
        this.imageView = imageView;


            new Thread() {
                private HttpURLConnection conn;
                private Bitmap bitmap;
                public void run() {

                    try {
                        URL url = new URL(ImageUrl);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(2000);
                        int code = conn.getResponseCode();
                        InputStream is;
                        if (code == 200) {
                            is = conn.getInputStream();
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.RGB_565;
                            bitmap = BitmapFactory.decodeStream(is, null, options);
                            conn.disconnect();
                            is.close();
                            Message msg = handler.obtainMessage();
                            msg.what = 201;
                            msg.obj=bitmap;
                            handler.sendMessage(msg);

                        } else {


                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {

                    }

                }

            }.start();
        }






    }

