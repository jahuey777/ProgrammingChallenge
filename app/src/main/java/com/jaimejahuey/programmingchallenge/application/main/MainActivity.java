package com.jaimejahuey.programmingchallenge.application.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jaimejahuey.programmingchallenge.R;
import com.jaimejahuey.programmingchallenge.databinding.ActivityMainBinding;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;

public class MainActivity extends AppCompatActivity implements MainAdapter.onSelectListener{

    MainActivityViewModel viewModel;
    ActivityMainBinding binding;
    MainAdapter adapter;

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
                    binding.mainProgressBar.setVisibility(View.GONE);
                    setRecyclerView();
                } else {
                    binding.mainRecyclerview.setVisibility(View.GONE);
                    binding.mainErrorText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.mainRecyclerview.setLayoutManager(layoutManager);

        adapter = new MainAdapter(viewModel.profiles, this);
        binding.mainRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onCardClick(int position, ProfileInformation profile) {

    }

}
