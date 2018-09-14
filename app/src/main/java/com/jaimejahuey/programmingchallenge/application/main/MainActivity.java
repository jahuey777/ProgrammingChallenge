package com.jaimejahuey.programmingchallenge.application.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jaimejahuey.programmingchallenge.R;
import com.jaimejahuey.programmingchallenge.application.profile.ProfileActivity;
import com.jaimejahuey.programmingchallenge.databinding.ActivityMainBinding;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;

public class MainActivity extends AppCompatActivity implements MainAdapter.onSelectListener{

    MainActivityViewModel viewModel;
    ActivityMainBinding binding;
    MainAdapter adapter;

    private static final String [] sortOptions = {"Default ", "Age Ascending", "Age Descending", "Name Ascending", "Name Descending"};
    private static final String [] filterOptions = {"All ", "Females", "Males"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(MainActivity.this).get(MainActivityViewModel.class);

        setObservers();
        viewModel.getProfiles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_main_filter:
                showFilterDialog();
                return true;
            case R.id.menu_main_sort:
                showSortDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
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

                binding.mainProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.mainRecyclerview.setLayoutManager(layoutManager);

        adapter = new MainAdapter(viewModel.profiles, this);
        binding.mainRecyclerview.setAdapter(adapter);
    }

    private void showFilterDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Filter");

        dialog.setItems(viewModel.filterOptions, (dialog1, which) -> {
            switch (which) {
                case 0:
                    viewModel.currentFilterSort = viewModel.FILTER_ALL;
                    viewModel.getProfilesByFilter(null);
                    break;
                case 1:
                    viewModel.currentFilterSort = viewModel.FILTER_FEMALES;
                    viewModel.getProfilesByFilter("female");
                    break;
                case 2:
                    viewModel.currentFilterSort = viewModel.FILTER_MALES;
                    viewModel.getProfilesByFilter("male");
                    break;

            }
        });

        dialog.show();
    }

    private void showSortDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sort");
        dialog.setItems(sortOptions, (dialog1, which) -> {

            switch (which) {
                case 0: //Default

                    break;
                case 1: //Age Ascending
                    Log.d("sorting by ", " age");
                    viewModel.getProfilesBySort("age");
                    break;
                case 2: //Age Descending

                    break;
                case 3: //Name Ascending

                    break;
                case 4: //Name Ascending

                    break;

            }
        });

        dialog.show();
    }

    @Override
    public void onCardClick(int position, ProfileInformation profile) {
        startActivity(ProfileActivity.newIntent(this, profile));
    }

}
