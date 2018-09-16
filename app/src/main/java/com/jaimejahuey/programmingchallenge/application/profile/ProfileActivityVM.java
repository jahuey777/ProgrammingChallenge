package com.jaimejahuey.programmingchallenge.application.profile;

import com.jaimejahuey.programmingchallenge.application.base.BaseViewModel;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;

public class ProfileActivityVM extends BaseViewModel {

    public ProfileInformation profile, copyProfile;

    public void setCopy() {
        copyProfile = profile.copyProfile(profile);
    }

    public void saveChangesToFireBase() {
        dbReference.child(profile.getKey()).child("hobbies").setValue(profile.getHobbies());
    }

    public void deleteProfileFromFirebase(){
        dbReference.child(profile.getKey()).removeValue();
    }

}
