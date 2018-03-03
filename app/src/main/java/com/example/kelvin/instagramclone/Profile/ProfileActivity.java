package com.example.kelvin.instagramclone.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.kelvin.instagramclone.R;
import com.example.kelvin.instagramclone.utils.BottomNavigationViewHelper;
import com.example.kelvin.instagramclone.utils.GridImageAdapter;
import com.example.kelvin.instagramclone.utils.UniversalImageLoader;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

/**
 * Created by kelvin on 2/23/18.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    private Context mcontext = ProfileActivity.this;
    private static final int Activity_Num = 4;
    private static  final int NUM_GRID_COLUMNS = 3;
    private ProgressBar mProgressBar;
    private ImageView profilePhoto;

//responsible for setting up user profiletoolbar
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setupToolbar();
        setUpBottomNavigationView();

        setupActivityWidgets();
        setProfileImage();
        tempGridSetup();


    }
    private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();


        setImageGrid(imgURLs);
     }

    private void setImageGrid(ArrayList<String> imgURLs ){
        GridView gridView = findViewById(R.id.gridView);
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int ImageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(ImageWidth);

        GridImageAdapter adapter = new GridImageAdapter(mcontext, R.layout.layout_grid_imageview,"",imgURLs);
        gridView.setAdapter(adapter );
    }

    private void setProfileImage(){

        String imgUrl = "goo.gl/images/oU9xVu";
        UniversalImageLoader.setImage(imgUrl, profilePhoto, mProgressBar, "https://");


    }

    private void setupActivityWidgets(){
        mProgressBar = findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);

        profilePhoto = findViewById(R.id.profile_photo);

    }

    private void backButton(){

        ImageView backarrow =(ImageView) findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to profileActivity");
                finish();
            }
        });
    }

    private void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.profileToolbar);
        setSupportActionBar(toolbar);

        ImageView  profileMenu = (ImageView) findViewById(R.id.profileMenu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: navigatting to account settings");
                    Intent intent = new Intent(mcontext, AccountSettingsActivity.class);
                    startActivity(intent);
                }
            });


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
