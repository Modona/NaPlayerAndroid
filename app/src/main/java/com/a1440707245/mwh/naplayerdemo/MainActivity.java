package com.a1440707245.mwh.naplayerdemo;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.a1440707245.mwh.naplayerdemo.dbutil.SqLiteOperater;
import com.a1440707245.mwh.naplayerdemo.down.DownloadService;
import com.a1440707245.mwh.naplayerdemo.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Fragment> fragments=new ArrayList<Fragment>();
    private FragmentManager fm;
    private  Intent intent;

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id)
        {
            case  1:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.ic_menu_slideshow);
                builder.setTitle("Ver.课程设计");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 这里添加点击确定后的逻辑
                               dialog.dismiss();
                            }
                        });

                builder.create().show();
                break;

        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("NaPlayer");
        showDialog(1);
        SqLiteOperater sql=new SqLiteOperater(getBaseContext());
        SQLiteDatabase db=sql.getWritableDatabase();
        db.close();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fm=getSupportFragmentManager();
        initFragments();
        intent=new Intent(this, DownloadService.class);
        ServiceConnection connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    public void initFragments(){
        MainFragment mainFragment=new MainFragment();
        fragments.add(mainFragment);
        SubFragment subFragment=new SubFragment();
        fragments.add(subFragment);
        SearchFragment searchFragment=new SearchFragment();
        fragments.add(searchFragment);
        AboutFragment aboutFragment=new AboutFragment();
        fragments.add(aboutFragment);
        setFragments(0);

    }
    public void setFragments(int i){
        fm.beginTransaction().replace(R.id.frame1,fragments.get(i)).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           Toast.makeText(this,"展示用",Toast.LENGTH_SHORT).show();
;           return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mainpage) {
            // Handle the camera action
            fm.popBackStack();
            setFragments(0);
        } else if (id == R.id.mysubscribe) {
            fm.popBackStack();
            setFragments(1);
        }
        else if(id==R.id.search)
        {
            fm.popBackStack();
            setFragments(2);
        }
//        else if (id == R.id.response) {
//
//        }
        else if (id == R.id.about) {
            setFragments(3);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        if (intent!=null)
        stopService(intent);
        super.onDestroy();
    }
}
