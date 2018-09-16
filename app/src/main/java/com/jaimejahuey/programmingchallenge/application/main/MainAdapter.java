package com.jaimejahuey.programmingchallenge.application.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jaimejahuey.programmingchallenge.R;
import com.jaimejahuey.programmingchallenge.model.ProfileInformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainProfileCardViewHolder> {

    interface onSelectListener {
        void onCardClick(int position, ProfileInformation profile);
    }

    private List<ProfileInformation> profiles;
    private onSelectListener listener;

    MainAdapter(List<ProfileInformation> profiles, onSelectListener listener) {
        this.profiles = profiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainProfileCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainProfileCardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main_profiles, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainProfileCardViewHolder holder, int position) {
        ProfileInformation profile = profiles.get(position);

        if (profile != null) {
            holder.nameTextView.setText(profile.getName());
            holder.ageTextView.setText(String.valueOf(profile.getAge()));

            if (profile.getImageUrl()!=null ) Picasso.get().load(profile.getImageUrl()).into(holder.profileImage);
            else holder.profileImage.setImageResource(R.drawable.ic_person_placeholder);

            if (profile.getGender().toLowerCase().equals("male")) holder.constraintLayout.setBackgroundColor(holder.constraintLayout.getContext().getResources().getColor(R.color.colorMaleBackgroud));
            else holder.constraintLayout.setBackgroundColor(holder.constraintLayout.getContext().getResources().getColor(R.color.colorAccent));

            holder.constraintLayout.setOnClickListener(v -> listener.onCardClick(position, profile));
        }
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

}
