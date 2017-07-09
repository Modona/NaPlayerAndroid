package com.a1440707245.mwh.naplayerdemo.video;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.a1440707245.mwh.naplayerdemo.NaCrawl;
import com.a1440707245.mwh.naplayerdemo.PlayVideoActivity;
import com.a1440707245.mwh.naplayerdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView indexText;
    private List<Map<String,String>> mData;
    private  String playurl;
    private MyAdapter myAdapter;
    private Handler dlHandler;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 801:
                mData=(ArrayList<Map<String,String>>)msg.obj;
                Message dlmsg=dlHandler.obtainMessage();
                dlmsg.what=1000;
                dlmsg.obj=mData;
                dlHandler.sendMessage(dlmsg);
                myAdapter.notifyDataSetChanged();
                break;
                case 802:
                Map<String,String>  map=new HashMap<String, String>();
                map.put("index","1集");
                map.put("playurl",playurl);
                mData.add(map);
                Message dlmsg2=dlHandler.obtainMessage();
                dlmsg2.what=1000;
                dlmsg2.obj=mData;
                dlHandler.sendMessage(dlmsg2);
                myAdapter.notifyDataSetChanged();
                break;
            }
        }
    };
    public IndexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_index, container, false);
        Bundle bundle=getArguments();
        playurl=bundle.getString("playurl");
        mData=new ArrayList<Map<String,String>>();
        recyclerView= (RecyclerView) view.findViewById(R.id.videoIndex);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),4));
        myAdapter=new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        new NaCrawl().indexCrawl(playurl,handler);
        return view;
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private  LayoutInflater layoutInflater;
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.index_part,parent,false);
           ViewHolder holder=new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
                holder.button.setText(mData.get(position).get("index"));
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                        intent.putExtra("playurl",playurl);
                        startActivity(intent);
                    }
                });

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            public Button button;

            public ViewHolder(View itemView) {
                super(itemView);
                button= (Button) itemView.findViewById(R.id.indexText);

            }
        }

    }

    public void setHandler(Handler handler) {
        this.dlHandler = handler;
    }
}
