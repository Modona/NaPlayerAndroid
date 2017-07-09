package com.a1440707245.mwh.naplayerdemo;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment  {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private List<String>titles;
    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("NaPlayer");
        View view=inflater.inflate(R.layout.main_fragment, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.viewpagerTab);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        initFragments();
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return view;
    }
    private void initFragments(){
        FirstFragment mainFragment1=new FirstFragment();
        fragments.add(mainFragment1);
        SecondFragment mainFragment2=new SecondFragment();
        fragments.add(mainFragment2);


        titles.add("首页");
        titles.add("最近更新");

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

}
