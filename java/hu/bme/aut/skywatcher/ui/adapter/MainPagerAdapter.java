package hu.bme.aut.skywatcher.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import hu.bme.aut.skywatcher.ui.LoggerFragment;
import hu.bme.aut.skywatcher.ui.MainFragment;
import hu.bme.aut.skywatcher.ui.SearchFragment;


public class MainPagerAdapter extends FragmentPagerItemAdapter {
    public static int NUM_PAGES = 3;

    MainFragment mf;
    SearchFragment sf;
    LoggerFragment lf;

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
            case 2:
                return "Logged tags";
            default:
                return "Picture of the Day";
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        switch (position) {
            case 0:
                mf = (MainFragment) createdFragment;
                break;
            case 1:
                sf = (SearchFragment) createdFragment;
                break;
            case 2:
                lf = (LoggerFragment) createdFragment;
                break;
        }
        return createdFragment;
    }

    public LoggerFragment getLoggerFragment() {
        if (lf != null) {
            return lf;
        }
        return null;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MainFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new LoggerFragment();
            default:
                return new MainFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}