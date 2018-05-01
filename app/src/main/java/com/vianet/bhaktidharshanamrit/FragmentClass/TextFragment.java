package com.vianet.bhaktidharshanamrit.FragmentClass;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vianet.bhaktidharshanamrit.AdaptorClass.TextFragmentAdaptor;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

//import com.vianet.bhaktidharshanamrit.AdaptorClass.ImageFragmentAdaptor;


public class TextFragment extends Fragment {

    TextView massage;
    private RecyclerView recyclerView;
    private TextFragmentAdaptor textFragmentAdaptor;

    public TextFragment() {
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

        recyclerView = (RecyclerView) view.findViewById(R.id.VideoFragrecyclerView);
        massage = (TextView) view.findViewById(R.id.messageTextFrag);

        textFragmentAdaptor = new TextFragmentAdaptor(getContext(), MainActivity.text);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        if (MainActivity.text != null && MainActivity.text.size() > 0) {
            massage.setVisibility(View.GONE);
            recyclerView.setAdapter(textFragmentAdaptor);
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TextFullFragment textFullFragment = new TextFullFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                textFullFragment.setArguments(bundle);
                textFullFragment.show(getFragmentManager().beginTransaction(), "dialog");

            }
        }));

        return view;
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

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
                return true;
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
}
