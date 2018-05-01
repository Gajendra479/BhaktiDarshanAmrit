package com.vianet.bhaktidharshanamrit.ActivityClass;


import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Build;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;

import android.support.design.widget.TabLayout;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vianet.bhaktidharshanamrit.AdaptorClass.PagerAdaptor;

import com.vianet.bhaktidharshanamrit.R;
public class ContentActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter pagerAdapter;
    TabLayout tabLayout;
    ActionBar actionBar;
    Toolbar toolbar;
    private Communication_frag listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        actionBar = getSupportActionBar();

        Intent i=getIntent();
        if (i.getStringExtra("Search")!=null){
            tabLayout.setVisibility(View.GONE);
            setSupportActionBar(toolbar);
            actionBar=getSupportActionBar();
            actionBar.setTitle(" ");
            actionBar.setDisplayHomeAsUpEnabled(true);

        }else{
            fromMainRecycleView();
        }
    }


    private void fromMainRecycleView() {

        Intent intent = getIntent();
        if (intent.getExtras().getString("category")!=null){
            setSupportActionBar(toolbar);
        }
        actionBar = getSupportActionBar();
        actionBar.setTitle(intent.getExtras().getString("category"));
        actionBar.setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.container);

        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Audios"));
        tabLayout.addTab(tabLayout.newTab().setText("Texts"));
        tabLayout.addTab(tabLayout.newTab().setText("Images"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pagerAdapter = new PagerAdaptor(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0 || tab.getPosition() == 2) {
                    listener.Frag_Position(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    public interface Communication_frag {

        void Frag_Position(int position);

    }

    public void setListener(Communication_frag communicationFrag) {
        this.listener = communicationFrag;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        final int home = android.R.id.home;

        //noinspection SimplifiableIfStatement
        if (id == home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
