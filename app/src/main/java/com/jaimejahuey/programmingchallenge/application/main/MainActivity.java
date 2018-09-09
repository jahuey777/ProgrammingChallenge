package com.jaimejahuey.programmingchallenge.application.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jaimejahuey.programmingchallenge.R;
import com.jaimejahuey.programmingchallenge.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel viewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(MainActivity.this).get(MainActivityViewModel.class);

        setObservers();
        viewModel.getProfiles();
    }

    private void setObservers () {
        viewModel.profilesFetched.observe(this, aBoolean -> {
            if(aBoolean != null) {
                if(aBoolean) {
                    setRecyclerView();
                } else {
                    binding.mainRecyclerview.setVisibility(View.GONE);
                    binding.mainErrorText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setRecyclerView() {

    }

}
