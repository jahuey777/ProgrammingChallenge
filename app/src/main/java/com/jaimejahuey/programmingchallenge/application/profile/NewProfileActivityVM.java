package com.jaimejahuey.programmingchallenge.application.profile;

import android.arch.lifecycle.ViewModel;

import com.jaimejahuey.programmingchallenge.application.base.BaseViewModel;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;

public class NewProfileActivityVM extends BaseViewModel {

    public void addNewProfileToFirebase(ProfileInformation profileInformation) {
        dbReference.setValue(profileInformation);
    }

}
