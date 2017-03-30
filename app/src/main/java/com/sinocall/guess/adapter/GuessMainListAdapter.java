package com.sinocall.guess.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.sinocall.guess.fragment.GuessMainFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */

public class GuessMainListAdapter extends FragmentStatePagerAdapter {

    private List<GuessMainFragment> listFragments;

    public void setListFragments(List<GuessMainFragment> listFragments) {
        this.listFragments = listFragments;
        notifyDataSetChanged();
    }

    public List<GuessMainFragment> getListFragments() {
        return listFragments;
    }

    public GuessMainListAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }


    @Override
    public int getCount() {
        return listFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
