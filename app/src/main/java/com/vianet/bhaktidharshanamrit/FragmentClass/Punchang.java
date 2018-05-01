/*
package com.vianet.bhaktidharshanamrit.FragmentClass;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Punchang extends Fragment {
    String url="http://162.144.68.182/ambey/API/horoscope_api.php?action=Panchange4bhaktisangam";
    ImageView imageView;
    String path="http://162.144.68.182/ambey/";
    ProgressBar progressBar;
    public Punchang() {
        // Required empty public constructor
    }
*/
/*
    // TODO: Rename and change types and number of parameters
    public static Punchang newInstance(String param1, String param2) {
        Punchang fragment = new Punchang();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }*//*


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_punchang, container, false);
        imageView= (ImageView) view.findViewById(R.id.punchang_image);
        progressBar= (ProgressBar) view.findViewById(R.id.panchang_progresh);

        MakePunchangRequest();

        return view;
    }

    private void MakePunchangRequest() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("Panchang");
                    JSONObject data=jsonArray.getJSONObject(0);
//                    String panchangdate=data.getString("panchang_date");
                    String panchang_image=data.getString("panchang");
//                    String datetime=data.getString("date_time");
*/
/*
                    Log.d("Panchang image",panchang_image);
                    Log.d("Panchang date",panchangdate);
                    Log.d("date tieme",datetime);*//*


                    Glide.with(getContext()).load(path+panchang_image).into(imageView);

                }catch (JSONException e){
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

            }
        });
        AppControllerSingleton.getMinstance().addToRequestQueue(stringRequest);
    }

}
*/
