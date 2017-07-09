package com.a1440707245.mwh.naplayerdemo.search;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a1440707245.mwh.naplayerdemo.ImageLoader;
import com.a1440707245.mwh.naplayerdemo.NaCrawl;
import com.a1440707245.mwh.naplayerdemo.PlayItem;
import com.a1440707245.mwh.naplayerdemo.R;
import com.a1440707245.mwh.naplayerdemo.video.VideoDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button btnSearch;
    private EditText editText;
    private NaCrawl naCrawl;
    private TextView searchLoading;
    private StringBuffer url;
    private boolean isLoading=false;
    private List<PlayItem> mData;
    private GridLayoutManager gridLayoutManager;
    private int index;
    private int nowIndex;
    private SearchAdapter searchAdapter;
    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 701:
                    if(mData.size()>0) {
                        mData.addAll((ArrayList<PlayItem>) msg.obj);
                        PlayItem playItem= mData.get(mData.size()-1);
                        index=Integer.parseInt(playItem.getImageurl());
                        mData.remove(mData.size()-1);
                        nowIndex++;
                        searchLoading.setText("");
                        isLoading=false;
                    }
                    else {
                        mData = (ArrayList<PlayItem>) msg.obj;
                        PlayItem playItem= mData.get(mData.size()-1);
                        index=Integer.parseInt(playItem.getImageurl());
                        mData.remove(mData.size()-1);if(mData.size()==0)
                        {
                            Toast.makeText(getContext(),"无搜索结果",Toast.LENGTH_SHORT).show();

                        }

                    }

                    searchAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.serch_layout,container,false);
        mData=new ArrayList<PlayItem>();
        recyclerView= (RecyclerView) view.findViewById(R.id.search_recycle);
        btnSearch= (Button) view.findViewById(R.id.btnSearch);
        editText= (EditText) view.findViewById(R.id.Editsearch);
        searchLoading= (TextView) view.findViewById(R.id.searchLoading);
        index=0;
        naCrawl=new NaCrawl();
        url=new StringBuffer();
        nowIndex=0;
        searchAdapter=new SearchAdapter();
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setOnScrollListener(new MyScrollListener());
        gridLayoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId)
                {
                    case EditorInfo.IME_ACTION_SEARCH:
                        if(mData!=null)
                        mData.clear();
                            url.replace(0,url.length(),"http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_page_name=search&nns_search="+ Uri.encode(editText.getText().toString())+"&nns_category_id=1000&nns_media_asset_id=movies|TVseries|variety|animation|softmovies|schoolmv|KoreaJapan&nns_search_type=2&nns_search_method=2&nns_include_category=1");
                        naCrawl.keyCrawl(new String(url),handler);

                        break;

                    default:
                        return false;
                }
                return false;
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.clear();
                url.replace(0,url.length(),"http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_page_name=search&nns_search="+ Uri.encode(editText.getText().toString())+"&nns_category_id=1000&nns_media_asset_id=movies|TVseries|variety|animation|softmovies|schoolmv|KoreaJapan&nns_search_type=2&nns_search_method=2&nns_include_category=1");
                naCrawl.keyCrawl(new String(url),handler);
            }
        });
        return view;
    }
    class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>
    {
        LayoutInflater layoutInflater;
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=layoutInflater.from(parent.getContext()).inflate(R.layout.common_itemdetail,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.textView.setText(mData.get(position).getPlayname());
            holder.imageView.setBackgroundResource(R.mipmap.ic_launcher);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
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
            holder.imageView.setTag(mData.get(position).getImageurl());
            new ImageLoader().loadImage(mData.get(position).getImageurl(),holder.imageView);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);
            holder.imageView.setImageBitmap(null);
        }

        public class ViewHolder extends RecyclerView.ViewHolder
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
    public void onDestroy() {
        super.onDestroy();
        recyclerView.swapAdapter(null,true);
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
                    Log.i("result",String.valueOf(gridLayoutManager.findLastVisibleItemPosition()));
                    if(!isLoading&&gridLayoutManager.findLastVisibleItemPosition()==(mData.size()-1)&&nowIndex<index)
                    {
                        searchLoading.setText("加载中");
                        naCrawl.keyCrawl(new String(url)+"&nns_page_num="+(nowIndex+1),handler);
                        isLoading=true;

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
