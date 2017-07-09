package com.a1440707245.mwh.naplayerdemo;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a1440707245.mwh.naplayerdemo.dbutil.SqLiteOperater;
import com.a1440707245.mwh.naplayerdemo.video.VideoDetailFragment;

import java.util.ArrayList;
import java.util.List;

import static com.a1440707245.mwh.naplayerdemo.R.id.subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubFragment extends Fragment {
    RecyclerView recyclerView;
    List<PlayItem> mData;
    private SubscribeAdapter subscribeAdapter;
    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 900:
                    mData.clear();
                    mData.addAll(getData());
                    subscribeAdapter.notifyDataSetChanged();
                    break;

            }
        }
    };
    public SubFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subscribe_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(subscribe);
        mData=getData();
        subscribeAdapter=new SubscribeAdapter();
        recyclerView.setAdapter(subscribeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }

    class SubscribeAdapter extends RecyclerView.Adapter<SubscribeAdapter.ViewHolder> {
        private LayoutInflater mLayoutInflater;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;

            public ViewHolder(View convertView) {
                super(convertView);
                imageView = (ImageView) convertView.findViewById(R.id.subscribe_img);
                textView = (TextView) convertView.findViewById(R.id.subscribe_text);
            }
        }

        public SubFragment.SubscribeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = mLayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subscribe_item, viewGroup, false);
            SubFragment.SubscribeAdapter.ViewHolder holder = new SubFragment.SubscribeAdapter.ViewHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.imageView.setTag(mData.get(position).getImageurl());
            new ImageLoader().loadImage(mData.get(position).getImageurl(),holder.imageView);
            holder.textView.setText( mData.get(position).getPlayname());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoDetailFragment videoDetailFragment=new VideoDetailFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("img",mData.get(position).getImageurl());
                    bundle.putString("name",mData.get(position).getPlayname());
                    bundle.putString("url",mData.get(position).getPlayurl());
                    videoDetailFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.frame1,videoDetailFragment).addToBackStack("sort").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();

                }
            });
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoDetailFragment videoDetailFragment=new VideoDetailFragment();
                    videoDetailFragment.setHandler(handler);
                    Bundle bundle=new Bundle();
                    bundle.putString("img",mData.get(position).getImageurl());
                    bundle.putString("name",mData.get(position).getPlayname());
                    bundle.putString("url",mData.get(position).getPlayurl());
                    videoDetailFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.frame1,videoDetailFragment).addToBackStack("sort").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();

                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }


    }
    public List<PlayItem> getData(){
        List<PlayItem> list=new ArrayList<PlayItem>();
        SqLiteOperater sql=new SqLiteOperater(getActivity().getBaseContext());
        SQLiteDatabase db=sql.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from subscribe",null);

        while(cursor.moveToNext())
        {

            list.add( new PlayItem(cursor.getString(cursor.getColumnIndex("videourl")),
                    cursor.getString(cursor.getColumnIndex("imageurl")),
                    cursor.getString(cursor.getColumnIndex("videoname")    )));
        }
        cursor.close();
        db.close();
        return list;
    }






}

