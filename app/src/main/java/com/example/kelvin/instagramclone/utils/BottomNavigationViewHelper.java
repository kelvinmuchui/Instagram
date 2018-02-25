package com.example.kelvin.instagramclone.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.kelvin.instagramclone.Home.HomeActivity;
import com.example.kelvin.instagramclone.Likes.LikesActivity;
import com.example.kelvin.instagramclone.Profile.ProfileActivity;
import com.example.kelvin.instagramclone.R;
import com.example.kelvin.instagramclone.Search.SearchActivity;
import com.example.kelvin.instagramclone.Share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by kelvin on 2/23/18.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHelper: Setting upp navigation view";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);

    }
    public static void enableNavigation(final Context context, BottomNavigationViewEx view){

        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.ic_house:

                        Intent intent1 = new Intent(context, HomeActivity.class);//Activity_NUm = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_search:

                        Intent intent2 = new Intent(context, SearchActivity.class);//Activity_NUm = 1
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_circle:
                        Intent intent3 = new Intent(context, ShareActivity.class);//Activity_NUm = 2
                        context.startActivity(intent3);

                        break;

                    case R.id.ic_alert:
                        Intent intent4  = new Intent(context, LikesActivity.class);//Activity_NUm = 3
                        context.startActivity(intent4);
                        break;

                    case R.id.ic_android:
                        Intent intent5 = new Intent(context, ProfileActivity.class);//Activity_NUm = 4
                        context.startActivity(intent5);
                        break;

                }
                return false;
            }
        });
    }
}
