package com.idat.cine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.idat.cine.ViewPagerAdapter;
import com.idat.cine.fragment.BillboardFragment;
import com.idat.cine.fragment.ReelFragment;
import com.idat.cine.fragment.TheaterFragment;
import com.idat.cine.models.Pelicula;
import com.idat.cine.R;
import com.idat.cine.screen.MainScreen;


public class DashboardActivity extends AppCompatActivity {
    public static Pelicula pelicula;

    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    BillboardFragment billboardFragment = new BillboardFragment();
    TheaterFragment theaterFragment = new TheaterFragment();
    ReelFragment reelFragment = new ReelFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(billboardFragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:

                Intent i = new Intent( DashboardActivity.this, MainScreen.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void myClickMethod(View v){
        billboardFragment.myClickMethod(v);
    }


    private final BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.nav_billboard:
                loadFragment(billboardFragment);
                return true;
            case R.id.nav_reel:
                loadFragment(theaterFragment);
                return true;
            case R.id.nav_theater:
                loadFragment(reelFragment);
                return true;

        }

        return false;
    };


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}
