package com.a1440707245.mwh.naplayerdemo;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a1440707245.mwh.naplayerdemo.video.VideoDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommonFragment extends Fragment {
    private RecyclerView recyclerView;
    private String url;
    private int index;
    private int isLoading=0;
    private int nowIndex;
    private TextView loading;
    private int isScroll;
    private List<PlayItem> mData;
    private  CommonAdapter commonAdapter;
    private FragmentManager fragmentManager;
    private  List<ImageLoader> imageLoaders;
    private NaCrawl naCrawl;
    private GridLayoutManager gridLayoutManager;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case  701:
                    if(mData.size()>0) {

                        mData.addAll((ArrayList<PlayItem>) msg.obj);
                        PlayItem playItem= mData.get(mData.size()-1);
                        index=Integer.parseInt(playItem.getImageurl());
                        mData.remove(mData.size()-1);
                        nowIndex++;
                    }
                    else {
                        mData = (ArrayList<PlayItem>) msg.obj;
                        PlayItem playItem= mData.get(mData.size()-1);
                        index=Integer.parseInt(playItem.getImageurl());
                        mData.remove(mData.size()-1);
                    }
                    loading.setText("");
                    isLoading=0;
                    commonAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    public CommonFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.common_fragment_layout,container,false);
        mData=new ArrayList<PlayItem>();
        index=0;
        nowIndex=0;
        recyclerView= (RecyclerView) view.findViewById(R.id.commonRecycle);
        loading= (TextView) view.findViewById(R.id.loadingMore);
        fragmentManager=getActivity().getSupportFragmentManager();
        url=getArguments().getString("crawlUrl");
        imageLoaders=new ArrayList<ImageLoader>();
        commonAdapter=new CommonAdapter();
        recyclerView.setAdapter(commonAdapter);
        recyclerView.setOnScrollListener(new MyScrollListener());
        gridLayoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        naCrawl=new NaCrawl();
        naCrawl.keyCrawl(url,handler);
        return view;
    }



    class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder>
    {
        private LayoutInflater layoutInflater;
        private int position;
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=layoutInflater.from(parent.getContext()).inflate(R.layout.common_itemdetail,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
                this.position=position;
                holder.textView.setText(mData.get(position).getPlayname());
                holder.imageView.setBackgroundResource(R.mipmap.ic_launcher);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentManager=getActivity().getSupportFragmentManager();
                        VideoDetailFragment videoDetailFragment=new VideoDetailFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("img",mData.get(position).getImageurl());
                        bundle.putString("name",mData.get(position).getPlayname());
                        bundle.putString("url",mData.get(position).getPlayurl());
                        videoDetailFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().add(R.id.frame1,videoDetailFragment).addToBackStack("sort").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    }
                });
                 holder.imageView.setTag(mData.get(position).getImageurl());
                if(imageLoaders.size()<(position+1)) {
                    imageLoaders.add(new ImageLoader());
                  imageLoaders.get(position).loadImage(mData.get(position).getImageurl(), holder.imageView);
                }
                else   imageLoaders.get(position).loadImage(mData.get(position).getImageurl(), holder.imageView);
                }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);


        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);
            BitmapDrawable bitmapDrawable= (BitmapDrawable) holder.imageView.getDrawable();
            if(bitmapDrawable!=null)
            {
                Bitmap bitmap=bitmapDrawable.getBitmap();
                if (bitmap!=null&&!bitmap.isRecycled())
                {
                    holder.imageView.setImageBitmap(null);
                    bitmap.recycle();
                }
            }
        }

        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            public ImageView imageView;
            public TextView textView;
            public ViewHolder(View itemView) {
                super(itemView);
                imageView= (ImageView) itemView.findViewById(R.id.plentyItemimg);
                textView= (TextView) itemView.findViewById(R.id.plentyItemtext);

            }

        }


    }

    @Override
    public void onDestroyView() {
        recyclerView.swapAdapter(null,true);
        mData.clear();
        imageLoaders.clear();
        super.onDestroyView();


    }

    @Override
    public void onDestroy() {
        if(recyclerView!=null)
        recyclerView.swapAdapter(null,true);
        if (mData!=null)
        mData.clear();
        if (imageLoaders!=null)
        imageLoaders.clear();
        getActivity().setTitle("NaPlayer");
        super.onDestroy();


    }
    class MyScrollListener extends RecyclerView.OnScrollListener
    {
        public MyScrollListener() {
            super();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            switch (newState)
            {
                case RecyclerView.SCROLL_STATE_IDLE:
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    if(isLoading==0&&gridLayoutManager.findLastVisibleItemPosition()==(mData.size()-1)&&nowIndex<index)
                    {
                        isLoading=1;
                        loading.setText("加载中...");
                        naCrawl.keyCrawl(url+"&nns_page_num="+(nowIndex+1),handler);

                    }
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

    }

}
