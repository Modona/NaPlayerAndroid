package com.a1440707245.mwh.naplayerdemo.video;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.a1440707245.mwh.naplayerdemo.FragmentViewPagerAdapter;
import com.a1440707245.mwh.naplayerdemo.ImageLoader;
import com.a1440707245.mwh.naplayerdemo.PlayVideoActivity;
import com.a1440707245.mwh.naplayerdemo.R;
import com.a1440707245.mwh.naplayerdemo.dbutil.SqLiteOperater;
import com.a1440707245.mwh.naplayerdemo.down.DownloadService;
import com.a1440707245.mwh.naplayerdemo.down.DownloadThreadInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDetailFragment extends Fragment implements View.OnTouchListener {
    private ImageView imageView;
    private TextView videoname;
    private Button videoSubscribe;
    private Button videoPlay;
    private Button download;
    private List<Map<String,String>> dlList;
    private boolean isSubscribed=false;
    private TabLayout tabLayout;
    private PopupWindow popupWindow;
    private ViewPager viewPager;
    MyAdapter myAdapter;
    private List<Fragment> fragments;
    private List<String> titles;
    private LinearLayout background;
    private  Handler handler;
    private  Handler Dlhandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1000:
                    dlList= (ArrayList<Map<String, String>>) msg.obj;
                    Log.i("result",dlList.toString());
                    if(myAdapter!=null)
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    String playUrl="";
    public VideoDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.video_detail,container,false);
        imageView= (ImageView) view.findViewById(R.id.videoImg);
        videoname= (TextView) view.findViewById(R.id.videoName);
        videoPlay= (Button) view.findViewById(R.id.videoPlay);
        download= (Button) view.findViewById(R.id.download);
        viewPager= (ViewPager) view.findViewById(R.id.videoDetaiViewPager);
        tabLayout= (TabLayout) view.findViewById(R.id.videoTab);
        background= (LinearLayout) view.findViewById(R.id.background);
        videoSubscribe= (Button) view.findViewById(R.id.videoSubscribe);
        dlList=new ArrayList<Map<String,String>>();
        view.setOnTouchListener(this);
        Bundle bundle=getArguments();
        final String imgUrl=bundle.getString("img");
        final String name=bundle.getString("name");
        playUrl=bundle.getString("url");
        imageView.setTag(imgUrl);
        new ImageLoader().loadImage(imgUrl,imageView);
        videoname.setText(name);
        //查询是否已经订阅
        SqLiteOperater sql=new SqLiteOperater(getActivity().getBaseContext());
        SQLiteDatabase db=sql.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from subscribe where imageurl=?",new String[]{imgUrl});
        if(cursor.moveToNext()) {
            videoSubscribe.setText("取消订阅");
            isSubscribed = true;
            db.close();
        }
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });
        videoSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSubscribed) {
                    SqLiteOperater sql = new SqLiteOperater(getActivity().getBaseContext());
                    SQLiteDatabase db = sql.getWritableDatabase();
                    db.execSQL("insert into subscribe values(?,?,?)", new Object[]{name, imgUrl, playUrl});
                    db.close();
                    videoSubscribe.setText("取消订阅");
                    isSubscribed=true;
                    if(handler!=null)
                    {
                        Message message=handler.obtainMessage();
                        message.what=900;
                        handler.sendMessage(message);
                    }
                }
                else{
                    SqLiteOperater sql = new SqLiteOperater(getActivity().getBaseContext());
                    SQLiteDatabase db = sql.getWritableDatabase();
                    db.execSQL("delete from subscribe where imageurl=?", new Object[]{imgUrl});
                    db.close();
                    videoSubscribe.setText("订阅");
                    isSubscribed=false;
                    if(handler!=null)
                    {
                        Message message=handler.obtainMessage();
                        message.what=900;
                        handler.sendMessage(message);
                    }
                }
            }
        });
        videoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                intent.putExtra("playurl",playUrl);
                startActivity(intent);
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupDownload(v);
            }
        });
        fragments=new ArrayList<Fragment>();
        titles=new ArrayList<String>();
        initFragments();
        FragmentViewPagerAdapter fragmentViewPagerAdapter=new FragmentViewPagerAdapter(getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(fragmentViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP)
        getFragmentManager().popBackStack();
        return true;
    }
    public void setHandler(Handler handler){
        this.handler=handler;
    }
    private void initFragments(){
        IntroduceFragment introduceFragment=new IntroduceFragment();
        Bundle bundle=new Bundle();
        bundle.putString("playurl",playUrl);
        introduceFragment.setArguments(bundle);
        fragments.add(introduceFragment);
        IndexFragment indexFragment=new IndexFragment();
        indexFragment.setHandler(Dlhandler);
        Bundle bundle2=new Bundle();
        bundle2.putString("playurl",playUrl);
        indexFragment.setArguments(bundle2);
        fragments.add(indexFragment);


        titles.add("简介");
        titles.add("分集列表");

    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setCustomView(getTabView(0));
        tabLayout.getTabAt(1).setCustomView(getTabView(1));
    }


    public View getTabView(int position) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(titles.get(position));
        return view;
    }
    public void showPopupDownload(View view){
        View contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.popdownload_layout, null);
        RecyclerView downloadRecycleview= (RecyclerView) contentView.findViewById(R.id.downloadRecycleview);
        myAdapter=new MyAdapter();
        downloadRecycleview.setAdapter(myAdapter);
        downloadRecycleview.setLayoutManager(new GridLayoutManager(this.getContext(),4));
        popupWindow=new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.showAsDropDown(view);
    }
    class MyAdapter extends RecyclerView.Adapter<VideoDetailFragment.MyAdapter.ViewHolder>{
        private  LayoutInflater layoutInflater;
        @Override
        public VideoDetailFragment.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.download_item,parent,false);
            MyAdapter.ViewHolder holder=new MyAdapter.ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final VideoDetailFragment.MyAdapter.ViewHolder holder, final int position) {
            holder.button.setText(dlList.get(position).get("index"));
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(), DownloadService.class);
                    DownloadThreadInfo downloadThreadInfo=new DownloadThreadInfo(dlList.get(position).get("playurl"));
                    intent.setAction(DownloadService.ACTION_DOWN);
                    intent.putExtra("downloadInfo",downloadThreadInfo);
                    getActivity().startService(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return dlList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            public Button button;

            public ViewHolder(View itemView) {
                super(itemView);
                button= (Button) itemView.findViewById(R.id.dlItem);

            }
        }

    }

    @Override
    public void onDestroy() {
        if(popupWindow!=null)
            popupWindow.dismiss();
        super.onDestroy();
    }
}
