package com.example.kelvin.instagramclone.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kelvin.instagramclone.R;

/**
 * Created by kelvin on 2/23/18.
 */

public class MessagesFragment extends Fragment {
    private static final String TAG = "MessagesFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //inflating the application with message fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        return view;
    }
}
