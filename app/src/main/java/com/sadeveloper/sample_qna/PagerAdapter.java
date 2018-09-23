package com.sadeveloper.sample_qna;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

public class PagerAdapter extends FragmentPagerAdapter {
    private final static int pagecount=6;
    Context context;

    public PagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new Home_activity();
            case 1:
                return new Questions();
            case 2:
                return new Notification_activity();
            case 3:
                return new Search_activity();
            case 4:
                return new activity_favourite();
            case 5:
                return new user_details_activity();
        }
        return null;
    }

    @Override
    public int getCount() {
        return pagecount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
