package com.example.nutrident4mom.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutrident4mom.R;
import com.example.nutrident4mom.adapter.VideoAdapter;
import com.example.nutrident4mom.model.Video;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class VideoFragment extends Fragment {
private ArrayList<Video> videoArrayList;
private VideoAdapter adapterVideo;
    RecyclerView videoRv;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_video, container, false);

        videoRv = view.findViewById(R.id.videoRv);

        loadVideoFromFirebase();
        return view;
    }

    private void loadVideoFromFirebase() {
        videoArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Videos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videoArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    Video modelVideo = ds.getValue(Video.class);
                    videoArrayList.add(modelVideo);
                }
                adapterVideo = new VideoAdapter(getActivity(),videoArrayList);
                videoRv.setAdapter(adapterVideo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}