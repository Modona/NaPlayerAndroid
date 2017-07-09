package com.a1440707245.mwh.naplayerdemo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    private List<Map<String,Object>> mData;
    private RecyclerView recyclerView;
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 201:
                    mData= (ArrayList<Map<String, Object>>) msg.obj;
                    recyclerView.setAdapter(new SecondFragmentAdapter());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    break;

            }
        }
    };

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.second_fragment,container,false);
        mData=new ArrayList<Map<String,Object>>();
        recyclerView= (RecyclerView) view.findViewById(R.id.second_fragment);
        new NaCrawl().secondFragmentCrawl(handler);
              return view;
    }
    class SecondFragmentAdapter extends RecyclerView.Adapter<SecondFragmentAdapter.ViewHolder>
    {
        LayoutInflater layoutInflater;
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=layoutInflater.from(parent.getContext()).inflate(R.layout.second_item,parent,false);
            SecondFragmentAdapter.ViewHolder holder=new SecondFragmentAdapter.ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.textView.setText((String)mData.get(position).get("videoname"));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),PlayVideoActivity.class);
                    intent.putExtra("playurl",(String) mData.get(position).get("playurl"));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textView;
            public ViewHolder(View itemView) {
                super(itemView);
                textView= (TextView) itemView.findViewById(R.id.second_item_text);
            }
        }

    }

}
