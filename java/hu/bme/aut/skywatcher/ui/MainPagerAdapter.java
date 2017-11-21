package hu.bme.aut.skywatcher.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import hu.bme.aut.skywatcher.R;


public class MainPagerAdapter extends FragmentPagerItemAdapter {
    private static final int NUM_PAGES = 2;

    public MainPagerAdapter(final FragmentManager fm, final FragmentPagerItems pages) {
        super(fm, pages);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Picture of the Day";
            case 1:
                return "Search for picture";
            default:
                return "Picture of the Day";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new SearchFragment();
            default:
                return new MainFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}