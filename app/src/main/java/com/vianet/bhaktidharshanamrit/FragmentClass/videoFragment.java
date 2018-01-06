package com.vianet.bhaktidharshanamrit.FragmentClass;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vianet.bhaktidharshanamrit.ActivityClass.PlayerActivity;
import com.vianet.bhaktidharshanamrit.ActivityClass.VideoPlayer;
import com.vianet.bhaktidharshanamrit.AdaptorClass.VideoFragAdaptor;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link videoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link videoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class videoFragment extends Fragment {
    RecyclerView recyclerView;
    VideoFragAdaptor videoFragAdaptor;


    private OnFragmentInteractionListener mListener;
    private TextView massage;


    public videoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static videoFragment newInstance(String param1, String param2) {
        videoFragment fragment = new videoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Toast.makeText(getContext(), "on cre", Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_video, container, false);

        recyclerView= (RecyclerView) view.findViewById(R.id.VideoFragrecyclerView);
        massage= (TextView) view.findViewById(R.id.messageTextFrag);
        videoFragAdaptor=new VideoFragAdaptor(getContext(),MainActivity.videos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        if (MainActivity.videos!=null && MainActivity.videos.size()>0){
            massage.setVisibility(View.GONE);
            recyclerView.setAdapter(videoFragAdaptor);
        }
        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                recyclerView.setEnabled(false);


                    Log.d("yyy","yyyy");

//
//                    ConnectivityManager connectivityManager= (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//                    NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
//

//                    if (networkInfo!=null){
//
//                        if (networkInfo.isConnected()){
                    try {
                        Intent intent =new Intent(getContext(), PlayerActivity.class);
                        intent.putExtra("videoFile", MainActivity.videos.get(position).getContents());
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                        }else {
//                            Toast.makeText(getContext(), "Check Your Internet Connectivity", Toast.LENGTH_SHORT).show();
//                        }
//                    }else {
//                        Toast.makeText(getContext(), "Check Your Internet Connectivity", Toast.LENGTH_SHORT).show();
//                    }
                }


        }));*/


        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private ClickListener clicklistener;
        private GestureDetector gestureDet;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerview, final ClickListener clickListener) {
            this.clicklistener = clickListener;
//            Log.d("gajendra ", "constructor");
            gestureDet = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());

            if (child != null && clicklistener != null && gestureDet.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildPosition(child));

            }
//            Log.d("gajendra ", "onInterceptTouchEvent" + e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//            Log.d("gajendra ", "onTouchEvent" + e);

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

    }

    @Override
    public void onResume() {
        super.onResume();

        recyclerView.setEnabled(false);
    }
}
