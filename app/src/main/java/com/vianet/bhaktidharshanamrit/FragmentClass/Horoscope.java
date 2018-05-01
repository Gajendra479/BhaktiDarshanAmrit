package com.vianet.bhaktidharshanamrit.FragmentClass;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.vianet.bhaktidharshanamrit.AdaptorClass.Expandable_Horoscope_Adaptor;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Horoscope extends Fragment {
    ExpandableListView expandableListView;
    Expandable_Horoscope_Adaptor expandable_horoscope_adaptor;
    List<String> listDataHeader;
    ImageView refreshImage;
    ProgressBar progressBar;
    HashMap<String, List<String>> listDataChild;

    private TextView errorText;

    // TODO: Rename parameter arguments, choose names that match
    public Horoscope() {
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
        View view = inflater.inflate(R.layout.fragment_horoscope, container, false);

        expandableListView = (ExpandableListView) view.findViewById(R.id.horoscope_expandable_list);
        progressBar = (ProgressBar) view.findViewById(R.id.horoscope_progress);
        errorText= (TextView) view.findViewById(R.id.horoscopeError);
        refreshImage= (ImageView) view.findViewById(R.id.horoscope_RefreshImage);

        makeHoroscopeRequest();

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeHoroscopeRequest();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(getContext(), ""+listDataHeader.get(groupPosition)+ " : " +listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getContext(), ""+listDataHeader.get(groupPosition   ) + " : " +listDataChild.get(groupPosition), Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getContext(), ""+listDataHeader.get(groupPosition   ) + " : " +listDataChild.get(groupPosition), Toast.LENGTH_SHORT).show();


            }
        });

        return view;
    }

    private void makeHoroscopeRequest() {
        progressBar.setVisibility(View.VISIBLE);
        refreshImage.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest("http://162.144.68.182/ambey/API/horoscope_api.php?action=HoroscopeMenu", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    listDataHeader = new ArrayList<String>();
                    listDataChild = new HashMap<String, List<String>>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("HoroscopeNames");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        List<String> list = new ArrayList<>();
                        list.add(data.getString("horoscope_text"));

                        listDataHeader.add(data.getString("banner_img"));
                        listDataChild.put(listDataHeader.get(i), list);

                    }
                    expandable_horoscope_adaptor = new Expandable_Horoscope_Adaptor(getContext(), listDataHeader, listDataChild);
                    expandableListView.setAdapter(expandable_horoscope_adaptor);

                } catch (JSONException error) {
                    error.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                errorText.setVisibility(View.VISIBLE);
                refreshImage.setVisibility(View.VISIBLE);

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    errorText.setText(R.string.Error_TimeOut);

                } else if (error instanceof AuthFailureError) {
                    errorText.setText(R.string.AuthFailure);

                } else if (error instanceof ServerError) {
                    errorText.setText(R.string.Server_Error);

                } else if (error instanceof NetworkError) {
                    errorText.setText(R.string.Network);

                } else if (error instanceof ParseError) {
                    errorText.setText(R.string.Parsing_error);
                }

            }
        });
        AppControllerSingleton.getMinstance().addToRequestQueue(stringRequest);
    }

}
