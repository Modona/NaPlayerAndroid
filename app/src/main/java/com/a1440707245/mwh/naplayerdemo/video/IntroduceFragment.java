package com.a1440707245.mwh.naplayerdemo.video;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a1440707245.mwh.naplayerdemo.NaCrawl;
import com.a1440707245.mwh.naplayerdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroduceFragment extends Fragment {

    private TextView introduceText;
    String introduce;
    String playurl;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 803:
                    introduce= (String) msg.obj;
                    introduceText.setText(introduce);
                    break;
                case 804:
                    introduceText.setText("暂无简介");
                    break;

            }
        }
    };
    public IntroduceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_introduce, container, false);
        introduceText= (TextView) view.findViewById(R.id.introduceText);
        Bundle bundle=getArguments();
        introduceText.setText("简介加载中...");
        playurl=bundle.getString("playurl");
        new NaCrawl().IntrodeceCrawl(playurl,handler);
        return view;
    }

}
