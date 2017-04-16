package com.crane.mockapp.core;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crane2002 on 2/24/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> views = new ArrayList<>();

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        int idx = views.indexOf(object);
        return idx == -1 ? POSITION_NONE : idx;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addView(View view, int index) {
        if (index < 0)
            index = views.size();
        views.add(index, view);
        notifyDataSetChanged();
    }

    public void removeView(View view) {
        if (views.remove(view))
            notifyDataSetChanged();
    }

    public View getViewAt(int position) {
        if (0 <= position && position < views.size())
            return views.get(position);
        return null;
    }

    public int indexOfView(View view) {
        return views.indexOf(view);
    }

}
