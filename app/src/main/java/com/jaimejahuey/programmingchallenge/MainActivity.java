package com.jaimejahuey.programmingchallenge;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("profiles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Log.d("Data", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w("Error", "Error getting documents.", task.getException());
                }
            }
        });

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("profiles");
//
//        if(reference == null) Toast.makeText(this, "not null ", Toast.LENGTH_SHORT).show();
//        else Toast.makeText(this, "profiles" + reference.child("profiles"), Toast.LENGTH_SHORT).show();
//
//        Log.d("URL", ": " + reference.child(""));
//
//
//        mQuery = reference.child("profiles");
//
//        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ProfileInformation>()
//                .setQuery(mQuery, ProfileInformation.class)
//                .build();
//
//        FirebaseRecyclerAdapter<ProfileInformation, RecyclerView.ViewHolder> adapter  = new FirebaseRecyclerAdapter<ProfileInformation, RecyclerView.ViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull ProfileInformation model) {
//
//                Log.d("Age", ": " + model.getAge());
//            }
//
//            @NonNull
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//        };
//
////        Log.d("Age", ": " + adapter.getItem(0).getAge());
//
//
//
//        ArrayList<ProfileInformation> profileList = new ArrayList<>();
//
//        reference.child("profiles").setValue(profileList);
//
//        if(profileList.size() == 0) Toast.makeText(this, " 0 ", Toast.LENGTH_SHORT).show();

    }
}
