package com.a1440707245.mwh.naplayerdemo.sort;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a1440707245.mwh.naplayerdemo.CommonFragment;
import com.a1440707245.mwh.naplayerdemo.FragmentViewPagerAdapter;
import com.a1440707245.mwh.naplayerdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private List<String>titles;
    private int fragmentFor;
    private int tabCount;
    private List<String>crawlUrl;

    public SortFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentFor=getArguments().getInt("for");
        View view=inflater.inflate(R.layout.common_layout,container,false);
         tabLayout= (TabLayout) view.findViewById(R.id.commonTab);
         viewPager = (ViewPager) view.findViewById(R.id.commonViewpager);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        initFragments();
        FragmentViewPagerAdapter fragmentViewPagerAdapter=new FragmentViewPagerAdapter(getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(fragmentViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.setCurrentItem(0);
        return view;
    }
    private void initFragments(){

        crawlUrl=new ArrayList<String>();
        switch (fragmentFor)
        {   case 1:
            tabCount=6;
            crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_page_name=newest&nns_media_asset_id=movies&nns_category_id=1000");
            crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=movies&nns_parent_category_id=&nns_category_id=1000023&nns_page_name=movie");
            crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=movies&nns_parent_category_id=1000&nns_category_id=1000022&nns_page_name=movie");
            crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=movies&nns_parent_category_id=1000&nns_category_id=1000021&nns_page_name=movie");
            crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=movies&nns_parent_category_id=1000&nns_category_id=1000024&nns_page_name=movie");
            crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=movies&nns_parent_category_id=1000&nns_category_id=1000025&nns_page_name=movie");
            //电影
            CommonFragment mainFragment1=new CommonFragment();
            Bundle bundle1=new Bundle();
            bundle1.putString("crawlUrl",crawlUrl.get(0));
            mainFragment1.setArguments(bundle1);
            fragments.add(mainFragment1);
            //内地电影
            CommonFragment mainFragment2=new CommonFragment();
            Bundle bundle2=new Bundle();
            bundle2.putString("crawlUrl",crawlUrl.get(1));
            mainFragment2.setArguments(bundle2);
            fragments.add(mainFragment2);
            //港台电影
            CommonFragment mainFragment3=new CommonFragment();
            Bundle bundle3=new Bundle();
            bundle3.putString("crawlUrl",crawlUrl.get(2));
            mainFragment3.setArguments(bundle3);
            fragments.add(mainFragment3);
            //欧美电影
            CommonFragment mainFragment4=new CommonFragment();
            Bundle bundle4=new Bundle();
            bundle4.putString("crawlUrl",crawlUrl.get(3));
            mainFragment4.setArguments(bundle4);
            fragments.add(mainFragment4);
            //其他电影
            CommonFragment mainFragment5=new CommonFragment();
            Bundle bundle5=new Bundle();
            bundle5.putString("crawlUrl",crawlUrl.get(4));
            mainFragment5.setArguments(bundle5);
            fragments.add(mainFragment5);
            //NA映画
            CommonFragment mainFragment6=new CommonFragment();
            Bundle bundle6=new Bundle();
            bundle6.putString("crawlUrl",crawlUrl.get(5));
            mainFragment6.setArguments(bundle6);
            fragments.add(mainFragment6);

            getActivity().setTitle("电影");
            titles.add("电影");
            titles.add("内地电影");
            titles.add("港台电影");
            titles.add("欧美电影");
            titles.add("其他电影");
            titles.add("NA映画");
            break;
            case 2:
                tabCount=4;
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_page_name=newest&nns_media_asset_id=TVseries&nns_category_id=1000");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=TVseries&nns_parent_category_id=&nns_category_id=1000023&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=TVseries&nns_parent_category_id=1000&nns_category_id=1000022&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=TVseries&nns_parent_category_id=1000&nns_category_id=1000021&nns_page_name=movie");
                //电视剧
                CommonFragment mainFragment21=new CommonFragment();
                Bundle bundle21=new Bundle();
                bundle21.putString("crawlUrl",crawlUrl.get(0));
                mainFragment21.setArguments(bundle21);
                fragments.add(mainFragment21);
                //内地电视剧
                CommonFragment mainFragment22=new CommonFragment();
                Bundle bundle22=new Bundle();
                bundle22.putString("crawlUrl",crawlUrl.get(1));
                mainFragment22.setArguments(bundle22);
                fragments.add(mainFragment22);
                //港台电视剧
                CommonFragment mainFragment23=new CommonFragment();
                Bundle bundle23=new Bundle();
                bundle23.putString("crawlUrl",crawlUrl.get(2));
                mainFragment23.setArguments(bundle23);
                fragments.add(mainFragment23);
                //欧美电视剧
                CommonFragment mainFragment24=new CommonFragment();
                Bundle bundle24=new Bundle();
                bundle24.putString("crawlUrl",crawlUrl.get(3));
                mainFragment24.setArguments(bundle24);
                fragments.add(mainFragment24);

                getActivity().setTitle("电视剧");
                titles.add("电视剧");
                titles.add("内地电视剧");
                titles.add("港台电视剧");
                titles.add("欧美电视剧");
                break;
            case 3:
                tabCount=4;
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_page_name=newest&nns_media_asset_id=variety&nns_category_id=1000");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_media_asset_id=variety&nns_parent_category_id=&nns_category_id=1000015&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=variety&nns_parent_category_id=1000&nns_category_id=1000016&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=variety&nns_parent_category_id=1000&nns_category_id=1000017&nns_page_name=movie");
                //综艺
                CommonFragment mainFragment31=new CommonFragment();
                Bundle bundle31=new Bundle();
                bundle31.putString("crawlUrl",crawlUrl.get(0));
                mainFragment31.setArguments(bundle31);
                fragments.add(mainFragment31);
                //内地综艺
                CommonFragment mainFragment32=new CommonFragment();
                Bundle bundle32=new Bundle();
                bundle32.putString("crawlUrl",crawlUrl.get(1));
                mainFragment32.setArguments(bundle32);
                fragments.add(mainFragment32);
                //港台综艺
                CommonFragment mainFragment33=new CommonFragment();
                Bundle bundle33=new Bundle();
                bundle33.putString("crawlUrl",crawlUrl.get(2));
                mainFragment33.setArguments(bundle33);
                fragments.add(mainFragment33);
                //欧美综艺
                CommonFragment mainFragment34=new CommonFragment();
                Bundle bundle34=new Bundle();
                bundle34.putString("crawlUrl",crawlUrl.get(3));
                mainFragment34.setArguments(bundle34);
                fragments.add(mainFragment34);

                getActivity().setTitle("综艺");
                titles.add("综艺");
                titles.add("内地综艺");
                titles.add("港台综艺");
                titles.add("欧美综艺");
                break;
            case 4:
                tabCount=3;
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_page_name=newest&nns_media_asset_id=animation&nns_category_id=1000");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_media_asset_id=animation&nns_parent_category_id=&nns_category_id=1000001&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=animation&nns_parent_category_id=1000&nns_category_id=1000002&nns_page_name=movie");
                //动漫
                CommonFragment mainFragment41=new CommonFragment();
                Bundle bundle41=new Bundle();
                bundle41.putString("crawlUrl",crawlUrl.get(0));
                mainFragment41.setArguments(bundle41);
                fragments.add(mainFragment41);
                //国产动漫
                CommonFragment mainFragment42=new CommonFragment();
                Bundle bundle42=new Bundle();
                bundle42.putString("crawlUrl",crawlUrl.get(1));
                mainFragment42.setArguments(bundle42);
                fragments.add(mainFragment42);
                //欧美动漫
                CommonFragment mainFragment43=new CommonFragment();
                Bundle bundle43=new Bundle();
                bundle43.putString("crawlUrl",crawlUrl.get(2));
                mainFragment43.setArguments(bundle43);
                fragments.add(mainFragment43);

                getActivity().setTitle("动漫");
                titles.add("动漫");
                titles.add("国产动漫");
                titles.add("欧美动漫");
                break;
            case 5:
                tabCount=4;
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_page_name=newest&nns_media_asset_id=softmovies&nns_category_id=1000");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=softmovies&nns_parent_category_id=&nns_category_id=1000002&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=softmovies&nns_parent_category_id=1000&nns_category_id=1000001&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=softmovies&nns_parent_category_id=1000&nns_category_id=1000003&nns_page_name=movie");
                //体育
                CommonFragment mainFragment51=new CommonFragment();
                Bundle bundle51=new Bundle();
                bundle51.putString("crawlUrl",crawlUrl.get(0));
                mainFragment51.setArguments(bundle51);
                fragments.add(mainFragment51);
                //足球
                CommonFragment mainFragment52=new CommonFragment();
                Bundle bundle52=new Bundle();
                bundle52.putString("crawlUrl",crawlUrl.get(1));
                mainFragment52.setArguments(bundle52);
                fragments.add(mainFragment52);
                //篮球
                CommonFragment mainFragment53=new CommonFragment();
                Bundle bundle53=new Bundle();
                bundle53.putString("crawlUrl",crawlUrl.get(2));
                mainFragment53.setArguments(bundle53);
                fragments.add(mainFragment53);
                //综合
                CommonFragment mainFragment54=new CommonFragment();
                Bundle bundle54=new Bundle();
                bundle54.putString("crawlUrl",crawlUrl.get(3));
                mainFragment54.setArguments(bundle54);
                fragments.add(mainFragment54);

                getActivity().setTitle("体育");
                titles.add("体育");
                titles.add("足球");
                titles.add("篮球");
                titles.add("综合");
                break;
            case 6:
                tabCount=8;
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_page_name=newest&nns_media_asset_id=schoolmv&nns_category_id=1000");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=schoolmv&nns_parent_category_id=&nns_category_id=1000001&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=schoolmv&nns_parent_category_id=1000&nns_category_id=1000020&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=schoolmv&nns_parent_category_id=1000&nns_category_id=1000002&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=schoolmv&nns_parent_category_id=1000&nns_category_id=1000004&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=schoolmv&nns_parent_category_id=1000&nns_category_id=1000008&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=schoolmv&nns_parent_category_id=1000&nns_category_id=1000022&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=schoolmv&nns_parent_category_id=1000&nns_category_id=1000021&nns_page_name=movie");
                //校园视频
                CommonFragment mainFragment61=new CommonFragment();
                Bundle bundle61=new Bundle();
                bundle61.putString("crawlUrl",crawlUrl.get(0));
                mainFragment61.setArguments(bundle61);
                fragments.add(mainFragment61);
                //校园宣传
                CommonFragment mainFragment62=new CommonFragment();
                Bundle bundle62=new Bundle();
                bundle62.putString("crawlUrl",crawlUrl.get(1));
                mainFragment62.setArguments(bundle62);
                fragments.add(mainFragment62);
                //就业启航
                CommonFragment mainFragment63=new CommonFragment();
                Bundle bundle63=new Bundle();
                bundle63.putString("crawlUrl",crawlUrl.get(2));
                mainFragment63.setArguments(bundle63);
                fragments.add(mainFragment63);
                //名校课程
                CommonFragment mainFragment64=new CommonFragment();
                Bundle bundle64=new Bundle();
                bundle64.putString("crawlUrl",crawlUrl.get(3));
                mainFragment64.setArguments(bundle64);
                fragments.add(mainFragment64);
                //科技展会
                CommonFragment mainFragment65=new CommonFragment();
                Bundle bundle65=new Bundle();
                bundle65.putString("crawlUrl",crawlUrl.get(4));
                mainFragment65.setArguments(bundle65);
                fragments.add(mainFragment65);
                //演讲访谈
                CommonFragment mainFragment66=new CommonFragment();
                Bundle bundle66=new Bundle();
                bundle66.putString("crawlUrl",crawlUrl.get(5));
                mainFragment66.setArguments(bundle66);
                fragments.add(mainFragment66);
                //经典展播
                CommonFragment mainFragment67=new CommonFragment();
                Bundle bundle67=new Bundle();
                bundle67.putString("crawlUrl",crawlUrl.get(6));
                mainFragment67.setArguments(bundle67);
                fragments.add(mainFragment67);
                //纪录片
                CommonFragment mainFragment68=new CommonFragment();
                Bundle bundle68=new Bundle();
                bundle68.putString("crawlUrl",crawlUrl.get(7));
                mainFragment68.setArguments(bundle68);
                fragments.add(mainFragment68);

                getActivity().setTitle("校园视频");
                titles.add("校园视频");
                titles.add("校园宣传");
                titles.add("就业启航");
                titles.add("名校课程");
                titles.add("科技展会");
                titles.add("演讲访谈");
                titles.add("经典展播");
                titles.add("纪录片");
                break;
            case 7:
                tabCount=8;
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_page_name=newest&nns_media_asset_id=KoreaJapan&nns_category_id=1000");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=KoreaJapan&nns_parent_category_id=&nns_category_id=1000100&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=KoreaJapan&nns_parent_category_id=1000&nns_category_id=1000101&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=KoreaJapan&nns_parent_category_id=1000&nns_category_id=1000105&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=KoreaJapan&nns_parent_category_id=1000&nns_category_id=1000102&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=KoreaJapan&nns_parent_category_id=1000&nns_category_id=1000103&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=KoreaJapan&nns_parent_category_id=1000&nns_category_id=1000104&nns_page_name=movie");
                crawlUrl.add("http://navod.scse.com.cn/nn_cms/data/template/100000/200003/index_v3_001.php?nns_template_type=100000&nns_template_id=200003&nns_tag=31&nns_media_asset_id=KoreaJapan&nns_parent_category_id=1000&nns_category_id=1000106&nns_page_name=movie");

                CommonFragment mainFragment71=new CommonFragment();
                Bundle bundle71=new Bundle();
                bundle71.putString("crawlUrl",crawlUrl.get(0));
                mainFragment71.setArguments(bundle71);
                fragments.add(mainFragment71);

                CommonFragment mainFragment72=new CommonFragment();
                Bundle bundle72=new Bundle();
                bundle72.putString("crawlUrl",crawlUrl.get(1));
                mainFragment72.setArguments(bundle72);
                fragments.add(mainFragment72);

                CommonFragment mainFragment73=new CommonFragment();
                Bundle bundle73=new Bundle();
                bundle73.putString("crawlUrl",crawlUrl.get(2));
                mainFragment73.setArguments(bundle73);
                fragments.add(mainFragment73);

                CommonFragment mainFragment74=new CommonFragment();
                Bundle bundle74=new Bundle();
                bundle74.putString("crawlUrl",crawlUrl.get(3));
                mainFragment74.setArguments(bundle74);
                fragments.add(mainFragment74);

                CommonFragment mainFragment75=new CommonFragment();
                Bundle bundle75=new Bundle();
                bundle75.putString("crawlUrl",crawlUrl.get(4));
                mainFragment75.setArguments(bundle75);
                fragments.add(mainFragment75);

                CommonFragment mainFragment76=new CommonFragment();
                Bundle bundle76=new Bundle();
                bundle76.putString("crawlUrl",crawlUrl.get(5));
                mainFragment76.setArguments(bundle76);
                fragments.add(mainFragment76);

                CommonFragment mainFragment77=new CommonFragment();
                Bundle bundle77=new Bundle();
                bundle77.putString("crawlUrl",crawlUrl.get(6));
                mainFragment77.setArguments(bundle77);
                fragments.add(mainFragment77);

                CommonFragment mainFragment78=new CommonFragment();
                Bundle bundle78=new Bundle();
                bundle78.putString("crawlUrl",crawlUrl.get(7));
                mainFragment78.setArguments(bundle78);
                fragments.add(mainFragment78);

                getActivity().setTitle("日韩");
                titles.add("日韩");
                titles.add("日本动漫");
                titles.add("日本电视剧");
                titles.add("日本电影");
                titles.add("日本综艺");
                titles.add("韩国电视剧");
                titles.add("韩国综艺");
                titles.add("韩国电影");
                break;
        }

    }
    private void setupTabIcons() {
        for (int i=0;i<tabCount;i++)
        {
        tabLayout.getTabAt(i).setCustomView(getTabView(i));

        }
    }


    public View getTabView(int position) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(titles.get(position));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(fragments!=null)
        fragments.clear();
        System.gc();
    }
}
