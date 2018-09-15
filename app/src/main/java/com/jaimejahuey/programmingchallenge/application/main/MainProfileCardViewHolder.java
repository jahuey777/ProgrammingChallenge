package com.jaimejahuey.programmingchallenge.application.main;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaimejahuey.programmingchallenge.R;

public class MainProfileCardViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView, ageTextView;
    ImageView profileImage;
    ConstraintLayout constraintLayout;

    public MainProfileCardViewHolder(View itemView) {
        super(itemView);

        nameTextView = itemView.findViewById(R.id.card_name);
        ageTextView = itemView.findViewById(R.id.card_age);
        profileImage = itemView.findViewById(R.id.card_image);
        constraintLayout = itemView.findViewById(R.id.card_constraint_layout);
    }

}
