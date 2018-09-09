package com.jaimejahuey.programmingchallenge.application.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jaimejahuey.programmingchallenge.R;
import com.jaimejahuey.programmingchallenge.application.main.MainActivity;
import com.jaimejahuey.programmingchallenge.application.main.MainActivityViewModel;
import com.jaimejahuey.programmingchallenge.databinding.ActivityProfileBinding;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private static final String PROFILE_EXTRA = "PROFILE_EXTRA";

    private ActivityProfileBinding binding;
    ProfileActivityVM viewModel;

    public static Intent newIntent(Context context, ProfileInformation profile){
        Intent intent = new Intent(context, ProfileActivity.class);

        intent.putExtra(PROFILE_EXTRA, profile);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        viewModel = ViewModelProviders.of(ProfileActivity.this).get(ProfileActivityVM.class);

        getProfile();
    }

    private void getProfile() {
        if(getIntent().getExtras() != null) {
            viewModel.profile = (ProfileInformation) getIntent().getExtras().getSerializable(PROFILE_EXTRA);
            loadData();
        }
        else {
            Toast.makeText(this, "Error loading profile. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        Picasso.get().load(viewModel.profile.getImageUrl()).into(binding.profileProfileImage);
        binding.profileNameTextview.setText(viewModel.profile.getName());
        binding.profileAgeTextview.setText(String.valueOf(viewModel.profile.getAge()));
        binding.profileGenderTextview.setText(String.valueOf(viewModel.profile.getGender()));

        StringBuilder hobbies = new StringBuilder();
        for(String hobby: viewModel.profile.getHobbies()) hobbies.append(hobby).append(", ");

        if(hobbies.length() > 0) binding.profileHobbiesTextview.setText(hobbies.substring(0, hobbies.length()-2));
        else binding.profileHobbiesLabel.setVisibility(View.GONE);
    }

}
