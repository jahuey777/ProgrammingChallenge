package com.jaimejahuey.programmingchallenge.application.main;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jaimejahuey.programmingchallenge.application.base.BaseViewModel;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;

import java.util.ArrayList;

public class MainActivityViewModel extends BaseViewModel {

    private ValueEventListener mProfilesListener;
    public ArrayList<ProfileInformation> profiles;
    public MutableLiveData<Boolean> profilesFetched = new MutableLiveData<>();

    public MainActivityViewModel() {
        super();
    }

    public void getProfiles() {
        mProfilesListener = dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    ProfileInformation profileInformation  = snapshot.getValue(ProfileInformation.class);
                    if (profileInformation!=null) profiles.add(profileInformation);
                }

                if(profiles.isEmpty()) profilesFetched.setValue(false);
                else profilesFetched.setValue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LOG", "onCancelled: Failed to read message");
                profilesFetched.setValue(false);
            }
        });

        dbReference.addValueEventListener(mProfilesListener);
    }

}
