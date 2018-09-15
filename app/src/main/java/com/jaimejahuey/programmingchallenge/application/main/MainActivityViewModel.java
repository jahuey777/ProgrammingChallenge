package com.jaimejahuey.programmingchallenge.application.main;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jaimejahuey.programmingchallenge.application.base.BaseViewModel;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivityViewModel extends BaseViewModel {

    private ValueEventListener mProfilesListener;
    private ArrayList<ProfileInformation> tempProfiles = new ArrayList<>();
    public ArrayList<ProfileInformation> profiles = new ArrayList<>();
    public MutableLiveData<Boolean> profilesFetched = new MutableLiveData<>();

    public String currentFilterType = null;
    public boolean sortAscending = true;
    public String currentSort = "id"; //default

    public MainActivityViewModel() {
        super();
    }

    public void getProfiles() {
        if (mProfilesListener != null) dbReference.removeEventListener(mProfilesListener);

        mProfilesListener = dbReference.orderByChild(currentSort).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tempProfiles.clear(); //Clear the previous list.

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    ProfileInformation profileInformation  = snapshot.getValue(ProfileInformation.class);
                    if (profileInformation != null) tempProfiles.add(profileInformation);
                }

                filterResults();
                if (!sortAscending) Collections.reverse(profiles);
                updateUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                profilesFetched.setValue(false);
            }
        });
    }

    private void filterResults() {
        profiles.clear();

        if (currentFilterType != null) {
            for (ProfileInformation profileInformation: tempProfiles) {
                if (profileInformation.getGender().equals(currentFilterType)) {
                    profiles.add(profileInformation);
                }
            }
        } else {
            profiles.addAll(tempProfiles);
        }

    }

    private void updateUi() {
        if(!profiles.isEmpty()) {
            profilesFetched.setValue(true);
        }
        else {
            profilesFetched.setValue(false);
        }
    }

}
