package com.example.kelvin.instagramclone.Search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kelvin.instagramclone.R;
import com.example.kelvin.instagramclone.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by kelvin on 2/23/18.
 */

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    private Context mcontext = SearchActivity.this;
    private static final int Activity_Num = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started");
        setUpBottomNavigationView();

    }
    /**
     *Bottom navigation setup
     *
     */
    private void setUpBottomNavigationView(){
        Log.d(TAG, "setUpBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mcontext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(Activity_Num);
        menuItem.setChecked(true);

    }
}
