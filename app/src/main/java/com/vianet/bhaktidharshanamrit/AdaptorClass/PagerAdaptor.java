package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.vianet.bhaktidharshanamrit.FragmentClass.GodsAudio;
import com.vianet.bhaktidharshanamrit.FragmentClass.ImageFragment;
import com.vianet.bhaktidharshanamrit.FragmentClass.TextFragment;
import com.vianet.bhaktidharshanamrit.FragmentClass.videoFragment;

/**
 * Created by editing2 on 18-Dec-17.
 */

public class PagerAdaptor extends FragmentPagerAdapter {
    int mNumOfTabs;

    public PagerAdaptor(FragmentManager fm,int numOftab) {
        super(fm);
        this.mNumOfTabs=numOftab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new videoFragment();
            case 1:
                return new GodsAudio();
            case 2:
                return new TextFragment();
            case 3:
                return new ImageFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
