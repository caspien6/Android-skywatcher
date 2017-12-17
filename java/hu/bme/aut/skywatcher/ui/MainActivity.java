package hu.bme.aut.skywatcher.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.Random;

import hu.bme.aut.skywatcher.R;
import hu.bme.aut.skywatcher.model.SearchedPictures;
import hu.bme.aut.skywatcher.ui.adapter.MainPagerAdapter;


public class MainActivity extends AppCompatActivity implements SearchFragment.OnSearchButtonClickedListener{

    ViewPager vpMain;
    MainPagerAdapter mainPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);


        vpMain = (ViewPager) findViewById(R.id.vpMain);
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this).create());
        vpMain.setAdapter(mainPagerAdapter);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(vpMain.getWindowToken(), 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.vpTabMain);
        viewPagerTab.setViewPager(vpMain);

    }

    @Override
    public void onSearchConditionSelected(SearchedPictures item) {
        LoggerFragment lf =  mainPagerAdapter.getLoggerFragment();
        if (lf != null) {
            int[] androidColors = getResources().getIntArray(R.array.androidcolors);
            item.setRandomColorIndex(new Random().nextInt(androidColors.length));
            lf.adapter.addItem(item);
        }
        else{
            Toast.makeText(this,
                    R.string.fragment_not_founded,Toast.LENGTH_SHORT).show();
        }
    }
}
