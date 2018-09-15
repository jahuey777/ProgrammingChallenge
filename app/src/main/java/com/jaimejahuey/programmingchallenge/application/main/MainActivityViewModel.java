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
import java.util.Collections;

public class MainActivityViewModel extends BaseViewModel {

    private ValueEventListener mProfilesListener;
    public ArrayList<ProfileInformation> tempProfiles = new ArrayList<>();
    public ArrayList<ProfileInformation> profiles = new ArrayList<>();
    public MutableLiveData<Boolean> profilesFetched = new MutableLiveData<>();

    public boolean filterSelected;
    public String currentFilter;
    public String currentFilterType = null;
    public boolean sortAscending = true;
    public String currentSort = "id"; //default

    public MainActivityViewModel() {
        super();
    }

//    public void getProfiles() {
//        if (mProfilesListener != null) dbReference.removeEventListener(mProfilesListener);
//
//        mProfilesListener = dbReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                tempProfiles.clear(); //Clear the previous list.
//
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    ProfileInformation profileInformation  = snapshot.getValue(ProfileInformation.class);
//                    if (profileInformation != null) tempProfiles.add(profileInformation);
//                }
//
//                updateUi();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                profilesFetched.setValue(false);
//                Log.d("Error", ": " + databaseError);
//            }
//        });
//    }

    private void filterResults() {
        profiles.clear();

        if (currentFilterType != null) {
            for (ProfileInformation profileInformation: tempProfiles) {
                if (profileInformation.getGender().equals(currentFilterType)){
                    profiles.add(profileInformation);
                }
            }
        } else {
            profiles.addAll(tempProfiles);
        }
    }

    public void getProfiles2() {
        if (mProfilesListener != null) dbReference.removeEventListener(mProfilesListener);

        mProfilesListener = dbReference.orderByChild(currentSort).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tempProfiles.clear(); //Clear the previous list.

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    ProfileInformation profileInformation  = snapshot.getValue(ProfileInformation.class);
                    if (profileInformation != null) tempProfiles.add(profileInformation);
                }

                Log.d("Profiles temp" , " size : " + tempProfiles.size());

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


//    public void getProfilesBySort(String sortBy, boolean sortAscending) {
//        dbReference.removeEventListener(mProfilesListener); //Remove previous listener.
//
//        Query query = dbReference.orderByChild(sortBy);
//        mProfilesListener = query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                tempProfiles.clear(); //Clear the previous list.
//
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    ProfileInformation profileInformation  = snapshot.getValue(ProfileInformation.class);
//                    if (profileInformation != null) {
//                        tempProfiles.add(profileInformation);
//                    }
//                }
//
//                if (!sortAscending) Collections.reverse(tempProfiles);
//                updateUi();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                profilesFetched.setValue(false);
//            }
//
//        });
//    }

//    public void getProfilesByFilter(String gender) {
//        Query query;
//
//        if(gender!=null) {
//            query = dbReference.orderByChild("gender").equalTo(gender);
//        } else {
//            getProfiles();
//            return;
//        }
//
//        dbReference.removeEventListener(mProfilesListener);
//        mProfilesListener = query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                tempProfiles.clear(); //Clear the previous list.
//
//                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    ProfileInformation profileInformation  = snapshot.getValue(ProfileInformation.class);
//                    if (profileInformation != null) {
//                        tempProfiles.add(profileInformation);
//                    }
//                }
//
//                updateUi();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                profilesFetched.setValue(false);
//            }
//        });
//    }

    private void updateUi() {
        if(!profiles.isEmpty()) {
            profilesFetched.setValue(true);
        }
        else {
            profilesFetched.setValue(false);
        }
    }

}
