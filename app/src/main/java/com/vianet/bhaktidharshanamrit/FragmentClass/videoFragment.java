package com.vianet.bhaktidharshanamrit.FragmentClass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vianet.bhaktidharshanamrit.AdaptorClass.VideoFragAdaptor;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

public class videoFragment extends Fragment {
    RecyclerView recyclerView;
    VideoFragAdaptor videoFragAdaptor;
    private TextView massage;

    public videoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        recyclerView = view.findViewById(R.id.VideoFragrecyclerView);
        massage = view.findViewById(R.id.messageTextFrag);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        videoFragAdaptor = new VideoFragAdaptor(getContext(), MainActivity.videos);
        massage.setVisibility(View.GONE);
        recyclerView.setAdapter(videoFragAdaptor);

       /* if (MainActivity.videos != null && MainActivity.videos.size() > 0) {
            massage.setVisibility(View.GONE);
            final ProgressBar progressBar = new ProgressBar(getContext());

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setBackgroundResource(R.drawable.circle_image);

            for (int i = 0; i < MainActivity.videos.size(); i++) {

                final int finalI = i;
                Glide.with(getContext()).load("https://img.youtube.com/vi/" + MainActivity.videos.get(i)
                        .getContents() + "/mqdefault.jpg").asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.d("video_frag1", "onLoadFailed: " + finalI + " " + MainActivity.videos.size());
                        if (finalI == MainActivity.videos.size() - 1) {
                            progressBar.setVisibility(View.GONE);
                            videoFragAdaptor = new VideoFragAdaptor(getContext(), MainActivity.videos);
                            massage.setVisibility(View.GONE);
                            recyclerView.setAdapter(videoFragAdaptor);
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        Log.d("video_frag", "onLoadFailed: " + e.getMessage() + " " + finalI + " " + MainActivity.videos.size());
                        MainActivity.videos.remove(finalI);
                      *//*  if (finalI ==  MainActivity.videos.size()-1){
                            progressBar.setVisibility(View.GONE);
                            videoFragAdaptor = new VideoFragAdaptor(getContext(), MainActivity.videos);
                            massage.setVisibility(View.GONE);
                            recyclerView.setAdapter(videoFragAdaptor);
                        }*//*
                    }
                });
            }
        }*/

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setEnabled(false);
    }
}
