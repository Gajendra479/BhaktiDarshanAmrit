package com.vianet.bhaktidharshanamrit.FragmentClass;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vianet.bhaktidharshanamrit.AdaptorClass.Biography_adaptor;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Biography extends Fragment {
    RecyclerView recyclerView;
    Biography_adaptor biography_adaptor;
    ArrayList<Get_Set> bio_list;
    ProgressBar progressBar;
    TextView errortext;
    MenuItem menuItem;
    String titleName;
    ImageView refreshImage;
    android.support.v7.widget.SearchView searchView;
    String url = "http://162.144.68.182/ambey/API/bhaktisangrahbio.php?action=bioGraphy";
    // TODO: Rename parameter arguments, choose names that match

    public Biography() {
        // Required empty public constructor
        searchView = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
     /*   recyclerView = (RecyclerView) view.findViewById(R.id.bio_graphy_recycle);
        refreshImage = (ImageView) view.findViewById(R.id.biography_refreshImage);
        errortext = (TextView) view.findViewById(R.id.biographyError);
        progressBar = (ProgressBar) view.findViewById(R.id.biography_progress);
*/
        recyclerView = (RecyclerView) view.findViewById(R.id.articleRecycle);
        refreshImage = (ImageView) view.findViewById(R.id.articleRefresh);
        errortext = (TextView) view.findViewById(R.id.articleErrorText);
        progressBar = (ProgressBar) view.findViewById(R.id.articleProgress);

        if (bio_list == null) {
            Make_BioGraphy_Request();
        } else {
            biography_adaptor = new Biography_adaptor(getContext(), bio_list, getFragmentManager());
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(biography_adaptor);
            biography_adaptor.setInterface1((MainActivity) getActivity());
        }

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Make_BioGraphy_Request();
            }
        });
        return view;
    }

    private void Make_BioGraphy_Request() {
        progressBar.setVisibility(View.VISIBLE);
        refreshImage.setVisibility(View.GONE);
        errortext.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                bio_list = new ArrayList<>();

                try {

                    JSONArray jsonArray = new JSONObject(response).getJSONArray("report");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Get_Set get_set = new Get_Set();
                        JSONObject data = jsonArray.getJSONObject(i);

                        if (!data.getString("desc1").equals("")) {
                            get_set.setBanner(data.getString("image_banner"));
                           /* String decs = data.getString("desc1");
                            Log.d("web", "onResponse: "+decs);*/
                            get_set.setDescription(data.getString("desc1"));
                            get_set.setTitle(data.getString("name"));
                            titleName = data.getString("name");

                            get_set.setThumb(data.getString("thumb"));
                            bio_list.add(get_set);
                        }
                    }
                    biography_adaptor = new Biography_adaptor(getContext(), bio_list, getFragmentManager());
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    recyclerView.setAdapter(biography_adaptor);
                    biography_adaptor.setInterface1((MainActivity) getActivity());
                    if (menuItem != null) {
                        menuItem.setVisible(true);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                errortext.setVisibility(View.VISIBLE);
                refreshImage.setVisibility(View.VISIBLE);

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    errortext.setText(R.string.Error_TimeOut);

                } else if (error instanceof AuthFailureError) {
                    errortext.setText(R.string.AuthFailure);

                } else if (error instanceof ServerError) {
                    errortext.setText(R.string.Server_Error);

                } else if (error instanceof NetworkError) {
                    errortext.setText(R.string.Network);

                } else if (error instanceof ParseError) {
                    errortext.setText(R.string.Parsing_error);
                }
            }
        });
        AppControllerSingleton.getMinstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.biography_search, menu);
        menuItem = menu.findItem(R.id.bio_search);
        if (bio_list != null) {
            menuItem.setVisible(true);
        }
//        searchView.setVisibility(View.VISIBLE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menuItem.getActionView();

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconified(true);


            searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
//                    biography_adaptor.getFilter().filter(newText);
                    newText = newText.toLowerCase();
                    if (bio_list != null) {
                        ArrayList<Get_Set> listar = new ArrayList<>();
                        for (Get_Set get_set : bio_list) {
                            String name = get_set.getTitle().toLowerCase();
                            if (name.contains(newText)) {
                                listar.add(get_set);
                            }

                        }
                        biography_adaptor.setFilter(listar);


                    }
                    return true;
                }
            });
        }

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onResume() {
        super.onResume();

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Biography");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
