package com.example.kelvin.instagramclone.Profile;

;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kelvin.instagramclone.R;
import com.example.kelvin.instagramclone.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by kelvin on 2/25/18.
 */

public class EditProfileFragment extends Fragment {
    private ImageView mProfilePhoto;
    private static final String TAG = "EditProfileFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        
            View view = inflater.inflate(R.layout.fragment_editprofile , container, false);

            mProfilePhoto = view.findViewById(R.id.profile_photo);



            setProfileImage();

            //Back button going back to profile activity

        ImageView backArrow = view.findViewById(R.id.backarrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick:  navigatting back to profile Edit profile");
                getActivity().finish();

            }
        });

            return view;



    }



    private  void setProfileImage(){

        String imgURl = "https://www.google.com/search?q=android+image&tbm=isch&source=iu&ictx=1&fir=esjkVqXhYMBavM%253A%252CJ9EMTFlcW3IosM%252C_&usg=__eiM2EDAh6mSoq5WY1BXUUlIYwR8%3D&sa=X&ved=0ahUKEwip9oednMTZAhXMEVAKHVz2CsYQ9QEILDAB&biw=1301&bih=654#imgrc=esjkVqXhYMBavM:";
        UniversalImageLoader.setImage(imgURl,mProfilePhoto,null, "");
    }
}
