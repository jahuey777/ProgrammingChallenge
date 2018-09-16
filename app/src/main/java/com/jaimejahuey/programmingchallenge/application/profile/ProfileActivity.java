package com.jaimejahuey.programmingchallenge.application.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.jaimejahuey.programmingchallenge.R;
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

        binding.profileIncludedProfileInfo.setIsEditing(false);
        binding.setShowEditingFab(true);
        getProfile();
        setFabs();
        setEditTextWatchers();
    }

    private void setEditTextWatchers() {
        binding.profileIncludedProfileInfo.profileHobbiesEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.profile.setHobbies(s.toString());
            }
        });
    }

    private void getProfile() {
        if(getIntent().getExtras() != null) {
            viewModel.profile = (ProfileInformation) getIntent().getExtras().getSerializable(PROFILE_EXTRA);
            viewModel.setCopy();
            loadData();
        }
        else {
            Toast.makeText(this, "Error loading profile. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        Picasso.get().load(viewModel.profile.getImageUrl()).into(binding.profileProfileImage);
        binding.profileIncludedProfileInfo.profileNameEdittext.setText(viewModel.profile.getName());
        binding.profileIncludedProfileInfo.profileAgeEdittext.setText(String.valueOf(viewModel.profile.getAge()));
        binding.profileIncludedProfileInfo.profileGenderEdittext.setText(String.valueOf(viewModel.profile.getGender()));

        if(viewModel.profile.getHobbies()!=null) binding.profileIncludedProfileInfo.profileHobbiesEdittext.setText(viewModel.profile.getHobbies());
    }

    private void setFabs() {
        binding.profileEditFab.setOnClickListener(v -> {
            binding.profileIncludedProfileInfo.setIsEditing(true);
            binding.setShowEditingFab(false);
        });

        binding.profileSaveFab.setOnClickListener(v -> {
            if(!viewModel.profile.sameHobbies(viewModel.copyProfile)) {
                showConfirmationDialog();
            } else {
                binding.setShowEditingFab(true);
                binding.profileIncludedProfileInfo.setIsEditing(false);
            }
        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Save changes?");
        dialog.setNegativeButton("No", null);
        dialog.setPositiveButton("Yes", (dialog1, which) -> {
            viewModel.saveChangesToFireBase();
            binding.setShowEditingFab(true);
            binding.profileIncludedProfileInfo.setIsEditing(false);
        });

        dialog.show();
    }



}
