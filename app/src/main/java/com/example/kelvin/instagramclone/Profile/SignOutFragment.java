package com.example.kelvin.instagramclone.Profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kelvin.instagramclone.R;

/**
 * Created by kelvin on 2/25/18.
 */

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            return view;

    }
}
