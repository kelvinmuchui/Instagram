package com.example.kelvin.instagramclone.Profile;

;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kelvin.instagramclone.R;
import com.example.kelvin.instagramclone.dialog.ConfirmPasswordDialog;
import com.example.kelvin.instagramclone.models.User;
import com.example.kelvin.instagramclone.models.UserAccountSettings;
import com.example.kelvin.instagramclone.models.UserSettings;
import com.example.kelvin.instagramclone.utils.FirebaseMethods;
import com.example.kelvin.instagramclone.utils.UniversalImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kelvin on 2/25/18.
 */

public class EditProfileFragment extends Fragment implements
        ConfirmPasswordDialog.OnConfirmPasswordListener {
    @Override
    public void onConfirmPasswordListener(String password) {


        //get Auth Cridentials from the user for re-authentiction
        //email and password credentials but there are multiple
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);
        ////////////Prompt the user to re-provide their sign-in credentials
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                        Log.d(TAG, "onComplete: user re-authenticated");

                        ///////////////check to seeif the email is already present in the database

                        mAuth.fetchProvidersForEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                                if(task.isSuccessful()){
                                    try{
                                    if(task.getResult().getProviders().size() == 1){
                                        Log.d(TAG, "onComplete:  That email is already in use");
                                        Toast.makeText(getActivity(), "That email is already in use", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Log.d(TAG, "onComplete: that email is available");
                                        //....The eamil is avialable so updated
                                        mAuth.getCurrentUser().updateEmail(mEmail.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Log.d(TAG, "onComplete: user email updated");
                                                            Toast.makeText(getActivity(), "email updated", Toast.LENGTH_SHORT).show();

                                                            mfirebaseMethods.updateEmail(mEmail.getText().toString());

                                                        }
                                                    }
                                                });
                                        
                                    }

                                }catch (NullPointerException e){
                                        Log.d(TAG, "onComplete:  nullPointerexception" + e.getMessage());
                                    }
                                }

                            }
                        });
                    }else{
                            Log.d(TAG, "onComplete: re-authentication failed");
                        }
                    }
                });

        Log.d(TAG, "onConfirmPasswordListener:  got the password");
    }
    private static final String TAG = "EditProfileFragment";
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseMethods mfirebaseMethods;

    //EditProfile Fragment Widgets
    private CircleImageView mProfilePhoto;
    private EditText mDisplayname, mUsername, mWebsite, mDescription,mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;
    private String userID;

    //vars
    private UserSettings mUserSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        
            View view = inflater.inflate(R.layout.fragment_editprofile , container, false);

            mProfilePhoto = view.findViewById(R.id.profile_photo);
            mDisplayname = view.findViewById(R.id.display_name);
            mUsername = view.findViewById(R.id.username);
            mWebsite = view.findViewById(R.id.website);
            mDescription = view.findViewById(R.id.description);
            mEmail = view.findViewById(R.id.email);
            mPhoneNumber = view.findViewById(R.id.phonenumber);
            mChangeProfilePhoto = view.findViewById(R.id.changeProfilePhoto);
            mfirebaseMethods = new FirebaseMethods(getActivity());
                setUpFirebaseAuth();
            //setProfileImage();

            //Back button going back to profile activity

        ImageView backArrow = view.findViewById(R.id.backarrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick:  navigatting back to profile Edit profile");
                getActivity().finish();

            }
        });

        ImageView checkmark = view.findViewById(R.id.saveChanges);

        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:  attempting to save changes");
                 saveProfileSettings();
            }
        });

            return view;



    }

    /**
     * Retrives the data contained in the widgets and submits it to the database
     * before doing so it checks to make sure the username is unique
     */

    private  void saveProfileSettings(){

        final String  username = mUsername.getText().toString();
        final String displayName = mDisplayname.getText().toString();
        final String website = mWebsite.getText().toString();
        final  String description = mDescription.getText().toString();
        final  long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());
        final String email = mEmail.getText().toString();




                    //case1 the user did not change the username

                    if(!mUserSettings.getUser().getUsername().equals(username)){
                            checkIfUsernameExists(username);
                    }

                    ///case2 if the user made change to thier email
                    if(!mUserSettings.getUser().getEmail().equals(email)){

                        //step1 reauthenticate
                        //          *confirm the password and email

                        ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
                        dialog.show(getFragmentManager(),getString(R.string.confirm_password_dialog));
                        dialog.setTargetFragment(EditProfileFragment.this,1);


                        //step2 check if the email already is registerd
                        //              -use "fetchProvidersForEmail(string email)"
                        //step3 change email

                        //          -submit the email to the database authentication

                    }


                }





    /**
     * Check is @param username already exists in the database
     * @param username
     */

    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists: checking if " + username +"already Exists");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    //add the username
                    mfirebaseMethods.updateUsername(username);
                    Toast.makeText(getActivity(),"Saved username", Toast.LENGTH_SHORT).show();

                }
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    if(singleSnapshot.exists()){
                        Toast.makeText(getActivity(),"That username arealdy exsits", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets:  setting widgets with data retrieving from firebase  database" + userSettings.toString());
        mUserSettings = userSettings;
        User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null,"");

        mDisplayname.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mEmail.setText(userSettings.getUser().getEmail());
        mPhoneNumber.setText(String.valueOf(userSettings.getUser().getPhone_number()));


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

        userID = mAuth.getCurrentUser().getUid();

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
