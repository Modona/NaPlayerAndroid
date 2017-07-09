package com.a1440707245.mwh.naplayerdemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;

public class PlayVideoActivity extends AppCompatActivity {
    MyVideoView videoView;
    TextView textview;
    TextView videoLoading;
    Button fullscreenBtn;
    private boolean isFullScreen;
    private int videoHeight;
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 401:
                    String videourl=Uri.decode((String)msg.obj).replace("%3A",":").replace("%2F","/");
                    videoView.setVideoURI(Uri.parse(videourl+".mp4"));
                    videoView.setMediaController(new MediaController(PlayVideoActivity.this));
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            textview.setText("视频加载成功");
                            videoLoading.setText("");
                            videoView.start();

                        }
                    });
                    textview= ((TextView) findViewById(R.id.videoText));

                    videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            textview.setText("视频加载失败");
                            return false;
                        }
                    });
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.videodetail_layout);
        isFullScreen=false;
        Intent intent=getIntent();
        String playurl=intent.getStringExtra("playurl");

        videoView= ((MyVideoView) findViewById(R.id.playvideo));
        videoLoading= (TextView) findViewById(R.id.videoLoding);
        ViewTreeObserver vto = videoView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                videoView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                videoHeight=videoView.getHeight();
            }
        });
        fullscreenBtn= (Button) findViewById(R.id.fullscreen);
        new NaCrawl().detailCrawl(playurl+"&nns_tag=31",handler);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        videoView.requestLayout();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
