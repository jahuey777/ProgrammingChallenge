package com.jaimejahuey.programmingchallenge.application.base;

import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseViewModel extends ViewModel {

    protected DatabaseReference dbReference;

    public BaseViewModel() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("profiles");
    }

}