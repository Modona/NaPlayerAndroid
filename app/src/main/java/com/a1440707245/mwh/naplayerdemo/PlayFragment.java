package com.a1440707245.mwh.naplayerdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFragment extends Fragment {


    public PlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.videodetail_layout,container,false);
        VideoView videoView= ((VideoView) view.findViewById(R.id.playvideo));
        videoView.setMediaController(new MediaController(getActivity()));
        return view;
    }

}
