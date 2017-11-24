package hu.bme.aut.skywatcher.ui;


import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import hu.bme.aut.skywatcher.R;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);


        final ViewPager vpMain = (ViewPager) findViewById(R.id.vpMain);
        vpMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this).create()));
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                InputMethodManager inputManager = (InputMethodManager) vpMain.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                // check if no view has focus:
                View v = ((Activity) vpMain.getContext()).getCurrentFocus();
                if (v == null)
                    return;

                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.vpTabMain);
        viewPagerTab.setViewPager(vpMain);

    }

}
