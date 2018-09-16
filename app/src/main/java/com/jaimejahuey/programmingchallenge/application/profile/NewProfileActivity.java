package com.jaimejahuey.programmingchallenge.application.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jaimejahuey.programmingchallenge.R;
import com.jaimejahuey.programmingchallenge.databinding.ActivityNewProfileBinding;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;

public class NewProfileActivity extends AppCompatActivity {

    ActivityNewProfileBinding binding;
    NewProfileActivityVM viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_profile);
        viewModel = ViewModelProviders.of(NewProfileActivity.this).get(NewProfileActivityVM.class);

        setSubmitButton();
    }

    private void setSubmitButton() {
        binding.newProfileSubmitButton.setOnClickListener(v -> {
            if(fieldsAreValid()) {
                ProfileInformation profile = new ProfileInformation();
                profile.setName(binding.newProfileNameEdittext.getText().toString());
                profile.setAge(Integer.parseInt(binding.newProfileAgeEdittext.getText().toString()));
                profile.setGender(binding.newProfileGenderEdittext.getText().toString());
                profile.setHobbies(binding.newProfileHobbiesEdittext.getText().toString());
                profile.setImageUrl(null);

                viewModel.addNewProfileToFirebase(profile);
                NewProfileActivity.this.finish();
            } else Toast.makeText(NewProfileActivity.this, "Please make sure fields are not empty.", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean fieldsAreValid() {
        return !binding.newProfileNameEdittext.getText().toString().isEmpty()
                && !binding.newProfileAgeEdittext.getText().toString().isEmpty()
                && !binding.newProfileGenderEdittext.getText().toString().isEmpty()
                && !binding.newProfileHobbiesEdittext.getText().toString().isEmpty();
    }

}
