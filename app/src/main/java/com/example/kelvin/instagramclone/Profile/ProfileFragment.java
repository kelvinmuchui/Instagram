package com.example.kelvin.instagramclone.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kelvin.instagramclone.Login.LoginActivity;
import com.example.kelvin.instagramclone.R;
import com.example.kelvin.instagramclone.models.User;
import com.example.kelvin.instagramclone.models.UserAccountSettings;
import com.example.kelvin.instagramclone.models.UserSettings;
import com.example.kelvin.instagramclone.utils.BottomNavigationViewHelper;
import com.example.kelvin.instagramclone.utils.FirebaseMethods;
import com.example.kelvin.instagramclone.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kelvin on 3/4/18.
 */

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private static final int Activity_Num = 4;


    private TextView mPosts, mFollower, mFollowering, mDisplayName, mUsername,mwebsite, mDescription;
    private ProgressBar mProgressBar;
    private GridView gridView;
    private CircleImageView mProfilePhoto;

    private Toolbar toolbar;

    private ImageView profileMenu;
    private BottomNavigationViewEx  bottomNavigationViewEx;
    private Context mcontext;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseMethods mfirebaseMethods;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mDisplayName = view.findViewById(R.id.display_name);
        mUsername = view.findViewById(R.id.username);
        mFollower = view.findViewById(R.id.tvFollowers);
        mFollowering  = view.findViewById(R.id.tvFollowing);
        mPosts = view.findViewById(R.id.tvPosts);
        mwebsite = view.findViewById(R.id.website);
        mDescription = view.findViewById(R.id.description);
        mProfilePhoto = view.findViewById(R.id.profile_photo);
        mProgressBar = view.findViewById(R.id.profileprogressbar);
        toolbar = view.findViewById(R.id.profileToolbar);
        profileMenu = view.findViewById(R.id.profileMenu);

        gridView = view.findViewById(R.id.gridView);
        bottomNavigationViewEx = view.findViewById(R.id.bottomNavViewBar);
        mcontext = getActivity();
        mfirebaseMethods = new FirebaseMethods(getActivity());

        Log.d(TAG, "onCreateView:  started");

        setUpBottomNavigationView();
        setupToolbar();
        setUpFirebaseAuth();

        TextView editProfile = view.findViewById(R.id.textEditProfile);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to" + mcontext.getString(R.string.edit_profile_fragment));
                Intent intent = new Intent(getActivity(), AccountSettingsActivity.class);
                intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_activity));

                startActivity(intent);

            }
        });

        return view;
    }


    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets:  setting widgets with data retrieving from firebase  database" + userSettings.toString());
        User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null,"");

        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mwebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mPosts.setText(String.valueOf(settings.getPosts()));
        mFollowering.setText(String.valueOf(settings.getFollowing()));
        mFollower.setText(String.valueOf(settings.getFollowers()));
        mProgressBar.setVisibility(View.GONE);



    }
        private void setupToolbar(){


            ((ProfileActivity)getActivity()).setSupportActionBar(toolbar);
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
     * Setting up firebase auth object
     */

    private void setUpFirebaseAuth(){

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if(user !=null){
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:  sign_in" + user.getUid());
                }else{
                    //user is signed out

                    Log.d(TAG, "onAuthStateChanged:  signed_out");
                }

            }
        };

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Retrieve user infromation from database
                   setProfileWidgets( mfirebaseMethods.getUserSettings(dataSnapshot));

                //Retrive Images for the user in Quotion

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthListener);

    }
    @Override
    public void onStop(){
        super.onStop();

        if(mAuth != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}
