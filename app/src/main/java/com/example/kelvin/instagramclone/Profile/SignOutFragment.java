package com.example.kelvin.instagramclone.Profile;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kelvin.instagramclone.Login.LoginActivity;
import com.example.kelvin.instagramclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by kelvin on 2/25/18.
 */

public class SignOutFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "SignOutFragment";
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private ProgressBar mProgressBar;
    private TextView tvSignout ,tvSigningOut;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_signout, container, false);
            tvSignout = view.findViewById(R.id.tvConfirmSignout);
            mProgressBar = view.findViewById(R.id.progressBar);
            tvSigningOut = view.findViewById(R.id.tvSigningOut);
        Button btnConfirmSignout = view.findViewById(R.id.btnConfirmSignout);

        mProgressBar.setVisibility(View.GONE);
        tvSigningOut.setVisibility(View.GONE);
        setUpFirebaseAuth();

        btnConfirmSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to sign out");

                mProgressBar.setVisibility(View.VISIBLE);
                tvSigningOut.setVisibility(View.VISIBLE);
                mAuth.signOut();
                getActivity().finish();
            }
        });

            return view;

    }
    /**
     * ............................................firebase..............
     */

    /**
     * Setting up firebase auth object
     */

    private void setUpFirebaseAuth(){
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

                    Log.d(TAG, "onAuthStateChanged: navigatting back to log in screen");

                    Log.d(TAG, "onAuthStateChanged:  signed_out");

                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        };

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
