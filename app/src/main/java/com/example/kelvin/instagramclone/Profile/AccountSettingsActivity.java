package com.example.kelvin.instagramclone.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kelvin.instagramclone.R;

import java.util.ArrayList;

/**
 * Created by kelvin on 2/24/18.
 */

public class AccountSettingsActivity extends AppCompatActivity {
    private static final String TAG = "AccountSettingsActivity";

    private Context mcontext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = AccountSettingsActivity.this;
        setContentView(R.layout.activity_accountsettings);
        Log.d(TAG, "onCreate: started");
        setupSettingsList();
    }
    private void setupSettingsList(){
        Log.d(TAG, "setupSettingsList: Insializzing account settings list");
        ListView listView  = findViewById(R.id.lvAccountSettings);
        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile));
        options.add(getString(R.string.sign_out));

        ArrayAdapter adapter =  new ArrayAdapter(mcontext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);
    }
}
