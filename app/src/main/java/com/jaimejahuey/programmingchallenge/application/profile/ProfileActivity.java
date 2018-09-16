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

    private ActivityProfileBinding binding;
    ProfileActivityVM viewModel;

    public static Intent newIntent(Context context, ProfileInformation profile) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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
            super.onBackPressed();
        }
    }

    private void getProfile() {
        if(getIntent().getExtras() != null) {
            viewModel.profile = (ProfileInformation) getIntent().getExtras().getSerializable(PROFILE_EXTRA);
            viewModel.setCopy();
            loadData();
        }
        else Toast.makeText(this, "Error loading profile. Please try again.", Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        binding.profileIncludedProfileInfo.profileNameEdittext.setText(viewModel.profile.getName());
        binding.profileIncludedProfileInfo.profileAgeEdittext.setText(String.valueOf(viewModel.profile.getAge()));
        binding.profileIncludedProfileInfo.profileGenderEdittext.setText(String.valueOf(viewModel.profile.getGender()));

        if(viewModel.profile.getHobbies()!=null) binding.profileIncludedProfileInfo.profileHobbiesEdittext.setText(viewModel.profile.getHobbies());

        if (viewModel.profile.getImageUrl() != null) Picasso.get().load(viewModel.profile.getImageUrl()).into(binding.profileProfileImage);
        else binding.profileProfileImage.setImageResource(R.drawable.ic_person_placeholder);

    }

    /********************
     *                  *
     *     UI/Views     *
     *                  *
     ********************/

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

    private void setFabs() {
        if (viewModel.profile.getGender().equals("male")) {
            binding.profileSaveFab.setBackgroundTintList(getResources().getColorStateList(R.color.colorMaleBackgroud));
            binding.profileEditFab.setBackgroundTintList(getResources().getColorStateList(R.color.colorMaleBackgroud));
        }

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

    /********************
     *                  *
     *     Dialogs      *
     *                  *
     ********************/

    private void showConfirmationDialog(boolean finish) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.text_save_changes);
        dialog.setNegativeButton(R.string.text_no, (dialog2, which) -> {
            if(finish) ProfileActivity.this.finish();
        });
        dialog.setPositiveButton(R.string.text_yes, (dialog1, which) -> {
            viewModel.saveChangesToFireBase();
            binding.setShowEditingFab(true);
            binding.profileIncludedProfileInfo.setIsEditing(false);

            if(finish) ProfileActivity.this.finish();
        });

        dialog.show();
    }

    private void showDeleteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.text_delete_profile);
        dialog.setNegativeButton(R.string.text_no, null);
        dialog.setPositiveButton(R.string.text_yes, (dialog1, which) -> {
            viewModel.deleteProfileFromFirebase();
            ProfileActivity.this.finish();
        });

        dialog.show();
    }

}
