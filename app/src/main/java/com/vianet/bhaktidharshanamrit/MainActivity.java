package com.vianet.bhaktidharshanamrit;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.vianet.bhaktidharshanamrit.ActivityClass.ContentActivity;
import com.vianet.bhaktidharshanamrit.AdaptorClass.Main_Activity_Adaptor;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private String url ="http://162.144.68.182/ambey/API/menu_webservice.php?action=AllMenuCategory";
    public String url2;

    private RecyclerView recyclerView;
    private Main_Activity_Adaptor mainAdaptor;
    private ArrayList<Get_Set> main_activity_list;
    private ProgressBar progressbar;
    private TextView errorText;
    private ImageView refreshImage;
    Boolean aBoolean=true;

    public static ArrayList<Get_Set> videos;
    public static ArrayList<Get_Set> audio;
    public static ArrayList<Get_Set> text;
    public static ArrayList<Get_Set> images;
    private FloatingActionButton addStart , share,rate;
    Intent intent;
    private String cattegory;
    private boolean showbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(R.string.app_name);

        recyclerView= (RecyclerView) findViewById(R.id.recycleView);
        progressbar= (ProgressBar) findViewById(R.id.main_progress_bar);
        errorText= (TextView) findViewById(R.id.error_msg_main);
        refreshImage= (ImageView) findViewById(R.id.refresh_image_main_id);
        addStart= (FloatingActionButton) findViewById(R.id.floatAdd);
        share= (FloatingActionButton) findViewById(R.id.shareFloat);
        rate= (FloatingActionButton) findViewById(R.id.rateFloat);

        main_activity_list=new ArrayList<>();
        videos=new ArrayList<>();
        audio=new ArrayList<>();
        text=new ArrayList<>();
        images=new ArrayList<>();

        MakeMainRequest();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getBaseContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (aBoolean){
                    aBoolean = false;
                    String cat=main_activity_list.get(position).getCategory();
                    cat=cat.replace(" ","%20");
                    url2="http://162.144.68.182/ambey/API/menu_webservice.php?action=MenuList&category="+cat+"&page=1";

                    videos.clear();
                    audio.clear();
                    text.clear();
                    images.clear();
                    images.trimToSize();
                    text.trimToSize();
                    audio.trimToSize();
                    videos.trimToSize();

                    ConnectivityManager connectivityManager= (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

                    if (networkInfo!=null){
                        if (networkInfo.isConnected()){
                            MakeContentStringRequest(url2);

                            intent=new Intent(MainActivity.this,ContentActivity.class);
                            intent.putExtra("category",main_activity_list.get(position).getCategory());
                            aBoolean=false;

                        }else Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        }));

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeMainRequest();
                refreshImage.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);
            }
        });


        addStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showbutton){

                    share.setVisibility(View.VISIBLE);
                    rate.setVisibility(View.VISIBLE);
                    addStart.setImageResource(R.drawable.ic_clear_black_24dp);
                    showbutton=true;
                }else {
                    share.setVisibility(View.GONE);
                    rate.setVisibility(View.GONE);
                    addStart.setImageResource(R.drawable.ic_add_black_24dp);
                    showbutton=false;
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.setVisibility(View.GONE);
                rate.setVisibility(View.GONE);
                addStart.setImageResource(R.drawable.ic_add_black_24dp);
                showbutton=false;
                shareApllication();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.setVisibility(View.GONE);
                rate.setVisibility(View.GONE);
                addStart.setImageResource(R.drawable.ic_add_black_24dp);
                showbutton=false;
                rateUs();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        aBoolean=true;
    }

    private void MakeMainRequest() {
        progressbar.setVisibility(View.VISIBLE);

        StringRequest stringRequest =new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() >= 0) {
                    try {

                        JSONObject object = new JSONObject(response);
                        JSONArray jsonarray = object.getJSONArray("AllMenuCategory");

                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject data = jsonarray.getJSONObject(i);
                            Get_Set get_set = new Get_Set();
                            get_set.setThumb(data.getString("thumb"));
                            cattegory=data.getString("category");
                            get_set.setCategory(cattegory);
                            main_activity_list.add(get_set);
                        }
                        mainAdaptor = new Main_Activity_Adaptor(getApplicationContext(), main_activity_list);
                        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
                        recyclerView.setAdapter(mainAdaptor);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText(R.string.data_not_found);
                }
                progressbar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.setVisibility(View.GONE);
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
        }) ;
        AppControllerSingleton.getMinstance().addToRequestQueue(stringRequest);
    }

    private void MakeContentStringRequest(String url3) {

        progressbar.setVisibility(View.VISIBLE);
//        Log.w("url", url3);

        StringRequest stringRequest =new StringRequest(url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    Log.w("url_re", response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("AllMenu");


                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject data=jsonArray.getJSONObject(i);
                        Get_Set get_set=new Get_Set();

             /*           Log.w("title",data.getString("title"));
                        Log.w("mode",data.getString("mode"));*/

                        if (data.getString("mode").equals("video")){
                            get_set.setTitle(data.getString("title"));
                          /*  Log.w("title",data.getString("title"));
                            Log.w("thumb",data.getString("thumb"));
                            Log.w("mode",data.getString("mode"));*/
                            get_set.setDescription(data.getString("description"));
                            get_set.setContents(data.getString("contents"));
                            get_set.setThumb(data.getString("thumb"));
                            get_set.setTitle(data.getString("title"));
                            get_set.setId(data.getString("id"));
                            videos.add(get_set);

                        }else if (data.getString("mode").equals("audio")){
                          /*  Log.w("title",data.getString("title"));
                            Log.w("thumb",data.getString("thumb"));
                            Log.w("mode",data.getString("mode"));*/
                            get_set.setTitle(data.getString("title"));
                            get_set.setContents(data.getString("contents"));
                            get_set.setThumb(data.getString("thumb"));
                            get_set.setId(data.getString("id"));
                            audio.add(get_set);

                        }else if (data.getString("mode").equals("text")){
                            /*Log.w("title",data.getString("thumb"));
                            Log.w("mode",data.getString("mode"));*/
                            get_set.setThumb(data.getString("thumb"));
                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                                get_set.setDescription(String.valueOf(Html.fromHtml(data.getString("description"),Html.FROM_HTML_MODE_COMPACT)));
                            }else {
                                get_set.setDescription(String.valueOf(Html.fromHtml(data.getString("description"))));
                            }

                            get_set.setTitle(data.getString("title"));
                            get_set.setContents(data.getString("contents"));
                            get_set.setId(data.getString("id"));
                            text.add(get_set);

                        }else if (data.getString("mode").equals("image")){
                           /* Log.w("title",data.getString("title"));
                            Log.w("mode",data.getString("mode"));*/
                            get_set.setThumb(data.getString("thumb"));
                            get_set.setId(data.getString("id"));
                            images.add(get_set);
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    aBoolean=true;
                }

                startActivity(intent);
               /* for (int i=0;i<images.size();i++){
                    Log.w("imagess",images.get(i).getThumb());
                }*/
                progressbar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.setVisibility(View.GONE);
                aBoolean=true;
            }
        });
        AppControllerSingleton.getMinstance().addToRequestQueue(stringRequest);
    }

    private void rateUs() {
        Uri uri = Uri.parse("market://details?id=" + getBaseContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        );

        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException a) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName())));
        }
    }

    private void shareApllication() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        String text = "Dear Devotees, In this app you will find All Aarti Audio , Video and Text of all bhagwaan ji( Hindu God) in hindi font. You can share any content to your friend on a single click." + getString(R.string.app_name) + " app." + "\n\nDownload this at:";
        String link = "http://play.google.com/store/apps/details?id=" +getBaseContext().getPackageName();
        i.putExtra(Intent.EXTRA_TEXT, text + " " + link);
        startActivity(Intent.createChooser(i, "Share link:"));
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
//                return true;
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

    public interface ClickListener {
         void onClick(View view, int position);
    }
}
