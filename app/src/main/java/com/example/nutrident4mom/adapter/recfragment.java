package com.example.nutrident4mom.adapter;

import android.content.Intent;
import android.os.Bundle;

import com.example.nutrident4mom.main.AddContentActivity;
import com.example.nutrident4mom.main.AddVideoActivity;
import com.example.nutrident4mom.model.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.nutrident4mom.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class recfragment extends Fragment {
    private SearchView searchView;
    private List<Model> modelList;
    FloatingActionButton floatingActionButton;
    FloatingActionButton videoBtn;
    FloatingActionButton textbtn;
    FirebaseUser firebaseUser;
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    Boolean clicked = false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    myadapter adapter;
    public recfragment() {

    }

    public static recfragment newInstance(String param1, String param2) {
        recfragment fragment = new recfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_recfragment, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        rotateOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_open_anim);

        rotateClose = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom_anim);


        recview=(RecyclerView)view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        searchView = (SearchView)view.findViewById(R.id.searchview);
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.child("isAdmin").getValue(Boolean.class) == true){
                        floatingActionButton.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        textbtn = view.findViewById(R.id.textBtn);
        videoBtn = view.findViewById(R.id.videoBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), AddContentActivity.class));

                onAddButtonClicked();
            }
        });

        textbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddContentActivity.class));
            }
        });

        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddVideoActivity.class));
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                filterList(newText.toLowerCase(Locale.ROOT));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText.toLowerCase(Locale.ROOT));
                return false;
            }
        });

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students").orderByChild("nameLower"), Model.class)
                        .build();

        adapter=new myadapter(options);
        recview.setAdapter(adapter);





        return view;
    }

    private void filterList(String newText) {


        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("students").orderByChild("nameLower").startAt(newText).endAt(newText+"\uf8ff"), Model.class)
                        .build();
        adapter=new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);
    }

    private void onAddButtonClicked(){
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setVisibility(Boolean clicked){
        if(!clicked){
            textbtn.setVisibility(View.VISIBLE);
            videoBtn.setVisibility(View.VISIBLE);
        }
        else{
            textbtn.setVisibility(View.INVISIBLE);
            videoBtn.setVisibility(View.INVISIBLE);
        }
    }
    private void setAnimation(Boolean clicked){
        if(!clicked){
            textbtn.startAnimation(fromBottom);
            videoBtn.startAnimation(fromBottom);
            floatingActionButton.startAnimation(rotateOpen);
        }
        else{
            textbtn.startAnimation(toBottom);
            videoBtn.startAnimation(toBottom);
            floatingActionButton.startAnimation(rotateClose);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
