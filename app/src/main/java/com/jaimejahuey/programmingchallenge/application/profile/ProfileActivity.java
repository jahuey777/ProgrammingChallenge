package com.jaimejahuey.programmingchallenge.application.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jaimejahuey.programmingchallenge.R;
import com.jaimejahuey.programmingchallenge.databinding.ActivityProfileBinding;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private static final String PROFILE_EXTRA = "PROFILE_EXTRA";
    private static final String PROFILE_EXTRA_POSITION = "PROFILE_EXTRA_POSITION";

    private ActivityProfileBinding binding;
    ProfileActivityVM viewModel;

    public static Intent newIntent(Context context, ProfileInformation profile, int position){
        Intent intent = new Intent(context, ProfileActivity.class);

        intent.putExtra(PROFILE_EXTRA, profile);
        intent.putExtra(PROFILE_EXTRA_POSITION, position);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_profile_delete:
                showDeleteDialog();
                break;
            case android.R.id.home:
                if(!binding.getShowEditingFab() && !viewModel.profile.sameHobbies(viewModel.copyProfile)){
                    showConfirmationDialog(true);
                    return true;
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!binding.getShowEditingFab() && !viewModel.profile.sameHobbies(viewModel.copyProfile)){
            showConfirmationDialog(true);
        } else {
            finish();
        }
    }

    private void setEditTextWatchers() {
        binding.profileIncludedProfileInfo.profileHobbiesEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.profile.setHobbies(s.toString());
            }
        });
    }

    private void getProfile() {
        if(getIntent().getExtras() != null) {
            viewModel.profile = (ProfileInformation) getIntent().getExtras().getSerializable(PROFILE_EXTRA);
            viewModel.profilePosition = getIntent().getExtras().getInt(PROFILE_EXTRA_POSITION);
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
                showConfirmationDialog(false);
            } else {
                binding.setShowEditingFab(true);
                binding.profileIncludedProfileInfo.setIsEditing(false);
            }
        });
    }

    private void showConfirmationDialog(boolean finish) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Save changes?");
        dialog.setNegativeButton("No", (dialog2, which) -> {
            if(finish) ProfileActivity.this.finish();
        });
        dialog.setPositiveButton("Yes", (dialog1, which) -> {
            viewModel.saveChangesToFireBase();
            binding.setShowEditingFab(true);
            binding.profileIncludedProfileInfo.setIsEditing(false);

            if(finish) ProfileActivity.this.finish();
        });

        dialog.show();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete profile?");
        dialog.setNegativeButton("No", null);
        dialog.setPositiveButton("Yes", (dialog1, which) -> {
            viewModel.deleteProfileFromFirebase();
            ProfileActivity.this.finish();
        });

        dialog.show();
    }

}
