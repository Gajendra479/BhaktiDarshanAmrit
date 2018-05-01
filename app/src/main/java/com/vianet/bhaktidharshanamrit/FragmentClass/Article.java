package com.vianet.bhaktidharshanamrit.FragmentClass;


import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
import com.vianet.bhaktidharshanamrit.AdaptorClass.ArticleAdaptor;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Article extends Fragment implements ArticleDescription.SetWebBackArticleDesc {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView errorText;
    ImageView refreshImage;
    ArrayList<Get_Set> articlList;
    ArticleAdaptor articleAdaptor;
    GridLayoutManager layoutManager;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    private String url;
    private String urldescription;
    //    private ArrayList<Get_Set> singlearticleList;
    private String description;
    private String image;
    private SetWebBackArticle setWebBackArticle;
    private int total_num_page;
    private int item_per_page = 6;
    private String count;
    private int currentPage = 1;
    private boolean check = true;
    // TODO: Rename parameter arguments, choose names that match

    public Article() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.articleRecycle);
        progressBar = (ProgressBar) view.findViewById(R.id.articleProgress);
        refreshImage = (ImageView) view.findViewById(R.id.articleRefresh);
        errorText = (TextView) view.findViewById(R.id.articleErrorText);
        layoutManager = new GridLayoutManager(getContext(), 2);

        if (articlList != null) {
            articleAdaptor = new ArticleAdaptor(getContext(), articlList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(articleAdaptor);
        } else
            makeArticleRequest(currentPage);

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeArticleRequest(currentPage);
            }
        });
        check=true;

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (check) {

                    urldescription = "https://bhaktidarshan.in/API/webservice?action=SingleArtical&id=" + articlList.get(position).getId();

                    connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null) {
                        if (networkInfo.isConnected()) {
                            check = false;
                            makeArticleDescriptionRequest();


                        } else
                            Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        }));



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);

                int c = Integer.parseInt(count);
                if (c % item_per_page == 0) {
                    total_num_page = c / item_per_page;
                } else {
                    total_num_page = c / item_per_page + 1;
                }

                int firstVisibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition();

                if (!progressBar.isShown()) {
                    if (currentPage < total_num_page) {
                        if (firstVisibleItemCount == articlList.size() - 1) {
                            currentPage++;
                            makeArticleRequest(currentPage);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }


    @Override
    public void onsetWebBackViewArticleDesc(WebView webView, ImageView imageView) {
        if (setWebBackArticle != null) {
            setWebBackArticle.onsetWebBackViewArticle(webView, imageView);
        }
    }

    public void setInterfaceArticle(SetWebBackArticle setWebBackArticle) {
        this.setWebBackArticle = setWebBackArticle;
    }

    private void makeArticleDescriptionRequest() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(urldescription, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONObject(response).getJSONArray("SingleArtical");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        description = data.getString("desc");
                        image = data.getString("img");
                    }

                        ArticleDescription articleDescription = new ArticleDescription();
                        Bundle bundle = new Bundle();
                        bundle.putString("desc1", description);
                        bundle.putString("img", image);
                        articleDescription.setArguments(bundle);

                        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                        if (fragmentManager!=null) {
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            articleDescription.setInterfaceArticleDesc(Article.this);
                            ft.replace(R.id.cont, articleDescription, "ArticleDesc");
                            ft.addToBackStack(null);
                            ft.commit();
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

//                if (articlList==null) {

//                    errorText.setVisibility(View.VISIBLE);
//                    refreshImage.setVisibility(View.VISIBLE);

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        check=true;
//                        errorText.setText(R.string.Error_TimeOut);

                    } else if (error instanceof AuthFailureError) {
                        check=true;
//                        errorText.setText(R.string.AuthFailure);

                    } else if (error instanceof ServerError) {
                        check=true;
//                        errorText.setText(R.string.Server_Error);

                    } else if (error instanceof NetworkError) {
                        check=true;
//                        errorText.setText(R.string.Network);

                    } else if (error instanceof ParseError) {
                        check=true;
//                        errorText.setText(R.string.Parsing_error);
                    }
//                }else {
//                    Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        AppControllerSingleton.getMinstance().addToRequestQueue(stringRequest);
    }

    private void makeArticleRequest(final int currentPage) {

        url = "https://bhaktidarshan.in/API/webservice?action=ArticalList&page=" + currentPage;

        progressBar.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
        refreshImage.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
//                Log.d("response", response);
                try {
                    if (currentPage == 1) {
                        articlList = new ArrayList<>();
                    }
                    JSONObject jsonObject = new JSONObject(response);
                    count = jsonObject.getString("Count");
                    JSONArray jsonArray = jsonObject.getJSONArray("ArticalList");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        Get_Set get_set = new Get_Set();
                        JSONObject data = jsonArray.getJSONObject(i);
                        get_set.setTitle(data.getString("title"));
                        get_set.setThumb(data.getString("avatar"));
                        get_set.setId(data.getString("id"));
                        articlList.add(get_set);

                    }

                    if (currentPage == 1) {
                        articleAdaptor = new ArticleAdaptor(getContext(), articlList);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(articleAdaptor);
                    } else {
                        articleAdaptor.notifyDataSetChanged();
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
                if (articlList==null) {

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
                }else {
                    Toast.makeText(getContext(), "check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppControllerSingleton.getMinstance().addToRequestQueue(stringRequest);
    }

    public interface SetWebBackArticle {
        public void onsetWebBackViewArticle(WebView webView, ImageView imageView);
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private ClickListener clicklistener;
        private GestureDetector gestureDet;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerview, final ClickListener clickListener) {
            this.clicklistener = clickListener;
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
