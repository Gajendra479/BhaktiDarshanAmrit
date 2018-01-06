package com.vianet.bhaktidharshanamrit.FragmentClass;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vianet.bhaktidharshanamrit.AdaptorClass.ImageFragmentAdaptor;
import com.vianet.bhaktidharshanamrit.AdaptorClass.TextFragmentAdaptor;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TextFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextFragment extends Fragment {

    private RecyclerView recyclerView;
    TextView massage;
    private TextFragmentAdaptor textFragmentAdaptor;


    private OnFragmentInteractionListener mListener;

    public TextFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TextFragment newInstance(String param1, String param2) {
        TextFragment fragment = new TextFragment();
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_text, container, false);
//        Toast.makeText(getContext(), "on cre", Toast.LENGTH_SHORT).show();
        recyclerView= (RecyclerView) view.findViewById(R.id.text_Frag_RecycleView);
        massage= (TextView) view.findViewById(R.id.messageTextFrag);
        textFragmentAdaptor=new TextFragmentAdaptor(getContext(),MainActivity.text);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        if (MainActivity.text!=null&&MainActivity.text.size()>0){
            massage.setVisibility(View.GONE);
            recyclerView.setAdapter(textFragmentAdaptor);
        }



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),recyclerView, new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                TextFullFragment textFullFragment=new TextFullFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                textFullFragment.setArguments(bundle);
                textFullFragment.show(getFragmentManager().beginTransaction(), "dialog");
            }
        }));


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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private MainActivity.ClickListener clicklistener;
        private GestureDetector gestureDet;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerview, final MainActivity.ClickListener clickListener) {
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

    public static interface ClickListener {
        public void onClick(View view, int position);

    }
}
