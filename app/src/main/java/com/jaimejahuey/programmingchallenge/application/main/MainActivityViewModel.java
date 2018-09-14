package com.jaimejahuey.programmingchallenge.application.main;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaimejahuey.programmingchallenge.application.base.BaseViewModel;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends BaseViewModel {

    private ValueEventListener mProfilesListener;
    public ArrayList<ProfileInformation> profiles = new ArrayList<>();
    public MutableLiveData<Boolean> profilesFetched = new MutableLiveData<>();

    public final String [] filterOptions = {"All ", "Females", "Males"};


    public final int SORT_DEFAULT = 0, SORT_AGE_ASCENDING = 1, SORT_AGE_DESCENDING = 2, SORT_NAME_ASCENDING = 3, SORT_NAME_DESCENDING = 4;
    public final int FILTER_ALL = 0, FILTER_FEMALES = 2, FILTER_MALES = 3;

    public final int [] sortOptions = {SORT_DEFAULT, SORT_AGE_ASCENDING, SORT_AGE_DESCENDING, SORT_NAME_ASCENDING, SORT_NAME_DESCENDING};
//    public final int [] filterOptions = {FILTER_ALL, FILTER_FEMALES, FILTER_MALES};

    public int currentFilterSort = -1;

    public MainActivityViewModel() {
        super();
    }

    public void getProfiles() {

        if (mProfilesListener != null) dbReference.removeEventListener(mProfilesListener);

        mProfilesListener = dbReference.orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                profiles.clear(); //Clear the previous list.

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    ProfileInformation profileInformation  = snapshot.getValue(ProfileInformation.class);
                    if (profileInformation != null) profiles.add(profileInformation);
                }

                updateUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                profilesFetched.setValue(false);
            }
        });
    }

    public void getProfilesBySort(String sortBy) {
        Query query = dbReference.orderByChild("age");

        dbReference.removeEventListener(mProfilesListener); //Remove previous listener.

        mProfilesListener = dbReference.orderByChild(sortBy).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                profiles.clear(); //Clear the previous list.

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    ProfileInformation profileInformation  = snapshot.getValue(ProfileInformation.class);
                    if (profileInformation != null) {
                        profiles.add(profileInformation);
                    }
                }

                updateUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                profilesFetched.setValue(false);
            }

        });
    }

    public void getProfilesByFilter(String gender) {
        Query query;

        if(gender!=null) {
            query = dbReference.orderByChild("gender").equalTo(gender);
        } else {
            getProfiles();
            return;
        }

        dbReference.removeEventListener(mProfilesListener);
        mProfilesListener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profiles.clear(); //Clear the previous list.

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    ProfileInformation profileInformation  = snapshot.getValue(ProfileInformation.class);
                    if (profileInformation != null) {
                        profiles.add(profileInformation);
                    }
                }

                updateUi();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                profilesFetched.setValue(false);
            }
        });
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
