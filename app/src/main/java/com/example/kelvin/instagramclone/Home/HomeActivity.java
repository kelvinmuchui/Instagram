package com.example.kelvin.instagramclone.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kelvin.instagramclone.Login.LoginActivity;
import com.example.kelvin.instagramclone.R;
import com.example.kelvin.instagramclone.utils.BottomNavigationViewHelper;
import com.example.kelvin.instagramclone.utils.SectionPagerAdpter;
import com.example.kelvin.instagramclone.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private Context mcontext = HomeActivity.this;
    private static final int Activity_Num = 0;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting this activity to use activities
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: startting");
        //intializing firebaseauth instance
        //calling the bottom navigation view method
        setUpBottomNavigationView();
        //method to set the page view
        setupViewPager();

        //Init Image loader
        initImageLoader();
        setUpFirebaseAuth();
    }


    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mcontext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

    }
    //Responsible for adding the 3 tabs, Camera, Home, Message

    private  void setupViewPager(){

        //Using section pager to add fragments
        SectionPagerAdpter adpter = new SectionPagerAdpter(getSupportFragmentManager());
        adpter.addFragment(new CameraFragment());// fragment index 0
        adpter.addFragment(new HomeFragment());// fragment index 1
        adpter.addFragment(new MessagesFragment());// fragment index 2
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adpter);

        //Creating the Tab of the appllicaation
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);//Icon for index 0
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_instagram);//Icon for index 1
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_arrow);// Icon for index 2

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

    /**
     * ............................................firebase..............
     */
    /**
     * Check to see if the @param is logged in
     * @param user
     */

    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser:  checking if user is logd in");
        if(user == null){
            Intent intent = new Intent(mcontext , LoginActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Setting up firebase auth object
     */

    private void setUpFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //Check if user is loged in
                checkCurrentUser(user);
                if(user !=null){
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:  sign_in" + user.getUid());
                }else{
                    //user is signed out

                    Log.d(TAG, "onAuthStateChanged:  signed_out");
                }

            }
        };

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }
    @Override
    public void onStop(){
        super.onStop();

        if(mAuth != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}
