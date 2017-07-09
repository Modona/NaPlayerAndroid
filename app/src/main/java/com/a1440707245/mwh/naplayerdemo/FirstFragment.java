package com.a1440707245.mwh.naplayerdemo;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a1440707245.mwh.naplayerdemo.sort.SortFragment;
import com.a1440707245.mwh.naplayerdemo.video.VideoDetailFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    private List<List<PlayItem>> myData;
    private SortFragment sortFragment;
    private List<Map<String, Object>> mainData;
    private List<MyRecycleViewAdapter> myRecycleViewAdapters;
    private List<LinearLayoutManager> linearLayoutManagers;
    Handler handler;
    FragmentManager fragmentManager;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myData = new ArrayList<List<PlayItem>>();
        View view = inflater.inflate(R.layout.firs_fragment, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        myRecycleViewAdapters = new ArrayList<MyRecycleViewAdapter>();
        linearLayoutManagers = new ArrayList<LinearLayoutManager>();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mainPage_recView);
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 101:
                        List<PlayItem> mydata = new ArrayList<PlayItem>();
                        ArrayList<PlayItem> list = (ArrayList<PlayItem>) msg.obj;
                        for (int i = 0; i < list.size(); i++) {

                            if (i < 6) {
                                mydata.add(list.get(i));
                                if (i == 5) {
                                    myData.add(mydata);
                                    mydata = new ArrayList<PlayItem>();
                                }
                            } else if (i < 18) {

                                mydata.add(list.get(i));
                                if (i == 17) {
                                    myData.add(mydata);
                                    mydata = new ArrayList<PlayItem>();
                                }
                            } else if (i < 30) {

                                mydata.add(list.get(i));
                                if (i == 29) {
                                    myData.add(mydata);
                                    mydata = new ArrayList<PlayItem>();
                                }

                            } else if (i < 36) {

                                mydata.add(list.get(i));
                                if (i == 35) {
                                    myData.add(mydata);
                                    mydata = new ArrayList<PlayItem>();
                                }
                            } else if (i < 42) {

                                mydata.add(list.get(i));
                                if (i == 41) {
                                    myData.add(mydata);
                                    mydata = new ArrayList<PlayItem>();
                                }

                            } else if (i < 54) {

                                mydata.add(list.get(i));
                                if (i == 53) {
                                    myData.add(mydata);
                                    mydata = new ArrayList<PlayItem>();
                                }
                            } else if (i < 66) {
                                mydata.add(list.get(i));
                                if (i == 65) {
                                    myData.add(mydata);

                                }
                            }

                        }

                        mainData = getMainData();
                        final MainPageAdapter mainPageAdapter = new MainPageAdapter();
                        recyclerView.setAdapter(mainPageAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        break;
                    case 104:
                        Toast.makeText(getContext(),"无法连接到NA服务器",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable(){

                            public void run() {

                                android.os.Process.killProcess(android.os.Process.myPid());

                            }

                        }, 3000);
                        break;
                }
            }
        };
        new NaCrawl().mainCrawl(handler);
        return view;
    }

    class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
        private LayoutInflater mLayoutInflater;
        private List<PlayItem> myData;

        public MyRecycleViewAdapter(List<PlayItem> myData) {
            this.myData = myData;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imgview;
            public TextView videoname;

            public ViewHolder(View convertView) {
                super(convertView);
                imgview = (ImageView) convertView.findViewById(R.id.itemimage);
                videoname = (TextView) convertView.findViewById(R.id.itemname);
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = mLayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mainitemlist, viewGroup, false);
            ViewHolder holder = new ViewHolder(v);

            return holder;
        }

        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.imgview.setTag(myData.get(position).getImageurl());
            holder.imgview.setBackgroundResource(R.mipmap.ic_launcher);
            holder.imgview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoDetailFragment videoDetailFragment=new VideoDetailFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("img",myData.get(position).getImageurl());
                    bundle.putString("name",myData.get(position).getPlayname());
                    bundle.putString("url",myData.get(position).getPlayurl());
                    videoDetailFragment.setArguments(bundle);
                  getParentFragment().getFragmentManager().beginTransaction().add(R.id.frame1,videoDetailFragment).addToBackStack("sort").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();

                }
            });
            new ImageLoader().loadImage(myData.get(position).getImageurl(), holder.imgview);
            holder.videoname.setText((String) myData.get(position).getPlayname());
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);
            holder.imgview.setImageBitmap(null);
        }

        @Override
        public int getItemCount() {
            return myData.size();
        }

    }

    public Handler getHandler() {
        return handler;

    }

    class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ViewHolder> {
        private LayoutInflater mLayoutInflater;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public TextView textView2;
            public RecyclerView recyclerView;

            public ViewHolder(View convertView) {
                super(convertView);
                textView = (TextView) convertView.findViewById(R.id.specifyText);
                textView2 = (TextView) convertView.findViewById(R.id.textView2);
                recyclerView = (RecyclerView) convertView.findViewById(R.id.view);
            }
        }

        public MainPageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = mLayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mainpageitem, viewGroup, false);
            MainPageAdapter.ViewHolder holder = new MainPageAdapter.ViewHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.textView.setText((String) mainData.get(position).get("text"));
            holder.textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sortFragment= (SortFragment) getFragment(position);
                    fragmentManager.beginTransaction().hide(FirstFragment.this).add(R.id.frame1, sortFragment).addToBackStack("main").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                }
            });
            if (myRecycleViewAdapters.size() < (position + 1)) {
                myRecycleViewAdapters.add(new MyRecycleViewAdapter(myData.get(position)));
                holder.recyclerView.setAdapter(myRecycleViewAdapters.get(myRecycleViewAdapters.size() - 1));
            } else holder.recyclerView.setAdapter(myRecycleViewAdapters.get(position));
            if (linearLayoutManagers.size() < (position + 1)) {
                linearLayoutManagers.add(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                holder.recyclerView.setLayoutManager(linearLayoutManagers.get(linearLayoutManagers.size() - 1));
            } else holder.recyclerView.setLayoutManager(linearLayoutManagers.get(position));

        }


        @Override
        public int getItemCount() {
            return myData.size();
        }

        public SortFragment getFragment(int position) {
            SortFragment sortFragment = new SortFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("for", position + 1);
            sortFragment.setArguments(bundle);
            return sortFragment;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden)
        {
            fragmentManager.beginTransaction().hide(getParentFragment()).commit();
        }
        else {
            fragmentManager.beginTransaction().show(getParentFragment()).commit();
            if (sortFragment != null) {
                sortFragment = null;
            }
        }
            ;
    }

    public List<Map<String, Object>> getMainData() {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("array", myData.get(0));
            map.put("text", "最新电影");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("array", myData.get(1));
            map.put("text", "最新电视剧");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("array", myData.get(2));
            map.put("text", "最新综艺");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("array", myData.get(3));
            map.put("text", "最新动漫");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("array", myData.get(4));
            map.put("text", "最新体育");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("array", myData.get(5));
            map.put("text", "最新校园视频");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("array", myData.get(6));
            map.put("text", "最新日韩");
            list.add(map);
            return list;
        }

}

