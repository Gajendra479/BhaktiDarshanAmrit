package com.vianet.bhaktidharshanamrit;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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
import com.vianet.bhaktidharshanamrit.ActivityClass.ContentActivity;
import com.vianet.bhaktidharshanamrit.AdaptorClass.Biography_adaptor;
import com.vianet.bhaktidharshanamrit.AdaptorClass.Main_Activity_Adaptor;
import com.vianet.bhaktidharshanamrit.FragmentClass.Article;
import com.vianet.bhaktidharshanamrit.FragmentClass.Biography;
import com.vianet.bhaktidharshanamrit.FragmentClass.Calendar;
import com.vianet.bhaktidharshanamrit.FragmentClass.Horoscope;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Biography_adaptor.SetBackWebBio, Article.SetWebBackArticle {
    public static ArrayList<Get_Set> videos;
    public static ArrayList<Get_Set> audio;
    public static ArrayList<Get_Set> text;
    public static ArrayList<Get_Set> images;
    public String url2;
    public WebView webView;
    Boolean aBoolean = true;
    Toolbar toolbar;
    ImageView imageView;
    FloatingActionButton floatingActionButton;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    private String url = "http://162.144.68.182/ambey/API/menu_webservice.php?action=AllMenuCategory";
    private RecyclerView recyclerView;
    private Main_Activity_Adaptor mainAdaptor;
    private ArrayList<Get_Set> main_activity_list;
    private TextView errorText;
    private ImageView refreshImage;
    private String cattegory;
    private ProgressBar progressbar;

   /* private static String[] splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("   Bhakti Sangam");
//        getSupportActionBar().setTitle(" ");

        recyclerView = findViewById(R.id.recycleView);
        progressbar = findViewById(R.id.main_progress_bar);
        errorText = findViewById(R.id.error_msg_main);
        refreshImage = findViewById(R.id.refresh_image_main_id);

        bottomNavigationView = findViewById(R.id.bottam_navi_view);
        bottomNavigationView.setItemIconTintList(null);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottomNavigationView.setItemIconTintList(null);
//                recyclerView.setVisibility(View.GONE);

                Fragment fragment;

                switch (item.getItemId()) {

                    case R.id.action_Home:
                        recyclerView.setVisibility(View.VISIBLE);
                        toolbar.setTitle("Bhakti Sangam");
                        if (main_activity_list != null) {
                            chechVisibilityOfFragment();
                        } else {
                            chechVisibilityOfFragment();
                            MakeMainRequest();
                        }
                        return true;


                    case R.id.action_Punchang:
                        chechVisibilityOfFragment();
                        recyclerView.setVisibility(View.GONE);
                        toolbar.setTitle("   Article");
                        progressbar.setVisibility(View.GONE);
                        errorText.setVisibility(View.GONE);
                        refreshImage.setVisibility(View.GONE);
                        fragment = new Article();
                        loadFragment(fragment, "article");
                        ((Article) fragment).setInterfaceArticle(MainActivity.this);
                        return true;


                    case R.id.action_Horoscope:
                        chechVisibilityOfFragment();
                        recyclerView.setVisibility(View.GONE);
                        errorText.setVisibility(View.GONE);
                        progressbar.setVisibility(View.GONE);
                        toolbar.setTitle("  Horoscope");
                        refreshImage.setVisibility(View.GONE);
                        fragment = new Horoscope();
                        loadFragment(fragment, "Horoscope");
                        return true;


                    case R.id.action_Calendar:
                        chechVisibilityOfFragment();
                        recyclerView.setVisibility(View.GONE);
                        toolbar.setTitle("  Calendar");
                        progressbar.setVisibility(View.GONE);
                        errorText.setVisibility(View.GONE);
                        refreshImage.setVisibility(View.GONE);
                        fragment = new Calendar();
                        loadFragment(fragment, "Calendar");
                        return true;


                    case R.id.action_Biography:
                        chechVisibilityOfFragment();
                        recyclerView.setVisibility(View.GONE);
                        errorText.setVisibility(View.GONE);
                        toolbar.setTitle("  Biography");
                        progressbar.setVisibility(View.GONE);
                        refreshImage.setVisibility(View.GONE);
                        fragment = new Biography();
                        loadFragment(fragment, "Biography");
                        return true;


                }
                return true;
            }
        });


        if (main_activity_list == null) {
            MakeMainRequest();
        } else {
            mainAdaptor = new Main_Activity_Adaptor(getApplicationContext(), main_activity_list);
            recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
            recyclerView.setAdapter(mainAdaptor);
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getBaseContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (aBoolean) {
                    url2 = "http://162.144.68.182/ambey/API/menu_webservice.php?action=MenuList&category="
                            + main_activity_list.get(position).getCategory().replace(" ", "%20") + "&page=1";

                    videos.clear();
                    audio.clear();
                    text.clear();
                    images.clear();
                    images.trimToSize();
                    text.trimToSize();
                    audio.trimToSize();
                    videos.trimToSize();

                    connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null) {
                        if (networkInfo.isConnected()) {
                            MakeContentStringRequest(url2);

                            intent = new Intent(MainActivity.this, ContentActivity.class);
                            intent.putExtra("category", main_activity_list.get(position).getCategory());
                            aBoolean = false;

                        } else
                            Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MainActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share_toolbar) {
            shareApllication();
        } else if (id == R.id.send_toolbar) {
            rateUs();
        } else if (id == android.R.id.home) {
            chechVisibilityOfFragment();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onSetBackWebBio(WebView view, ImageView imageView, FloatingActionButton floatingActionButton) {
        this.webView = view;
        this.imageView = imageView;
        this.floatingActionButton = floatingActionButton;
    }

    @Override
    public void onsetWebBackViewArticle(WebView webView, ImageView imageView) {
        this.webView = webView;
        this.imageView = imageView;
//
    }

    @Override
    public void onBackPressed() {

        int selectedItem = bottomNavigationView.getSelectedItemId();

        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            if (!webView.canGoBack()) {
                imageView.setVisibility(View.VISIBLE);
                if (floatingActionButton != null) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                }

            }

        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            super.onBackPressed();
        } else if (R.id.action_Home == selectedItem) {
            super.onBackPressed();

        } else {
            chechVisibilityOfFragment();
            bottomNavigationView.setSelectedItemId(R.id.action_Home);
            toolbar.setTitle("  Bhakti Sangam");
        }
    }


    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.cont, fragment, tag);
        fragmentTransaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        aBoolean = true;
    }

    private void chechVisibilityOfFragment() {

        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }

        Fragment article = getSupportFragmentManager().findFragmentByTag("article");
        Fragment cal_id = getSupportFragmentManager().findFragmentByTag("Horoscope");
        Fragment horo_id = getSupportFragmentManager().findFragmentByTag("Calendar");
        Fragment bio_id = getSupportFragmentManager().findFragmentByTag("Biography");
        Fragment bio_card = getSupportFragmentManager().findFragmentByTag("Bio_card");
        Fragment articleDesc = getSupportFragmentManager().findFragmentByTag("ArticleDesc");

        if (bio_id != null && bio_id.isVisible()) {

            getSupportFragmentManager().beginTransaction().remove(bio_id).commit();

        } else if (cal_id != null && cal_id.isVisible()) {

            getSupportFragmentManager().beginTransaction().remove(cal_id).commit();

        } else if (horo_id != null && horo_id.isVisible()) {

            getSupportFragmentManager().beginTransaction().remove(horo_id).commit();

        } else if (article != null && article.isVisible()) {

            getSupportFragmentManager().beginTransaction().remove(article).commit();

        } else if (bio_card != null && bio_card.isVisible()) {

            getSupportFragmentManager().beginTransaction().remove(bio_card).commit();

        } else if (articleDesc != null && articleDesc.isVisible()) {

            getSupportFragmentManager().beginTransaction().remove(articleDesc).commit();
        }
    }

    private void MakeMainRequest() {
        progressbar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null && response.length() >= 0) {
                    try {
                        videos = new ArrayList<>();
                        audio = new ArrayList<>();
                        text = new ArrayList<>();
                        images = new ArrayList<>();

                        main_activity_list = new ArrayList<>();
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonarray = object.getJSONArray("AllMenuCategory");

                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject data = jsonarray.getJSONObject(i);
                            Get_Set get_set = new Get_Set();
                            get_set.setThumb(data.getString("thumb"));
                            cattegory = data.getString("category");
                            get_set.setCategory(cattegory);
                            main_activity_list.add(get_set);
                        }
                        mainAdaptor = new Main_Activity_Adaptor(getApplicationContext(), main_activity_list);
                        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
                        recyclerView.setAdapter(mainAdaptor);

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }
                } else {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText(R.string.data_not_found);
                }
                progressbar.setVisibility(View.GONE);
                refreshImage.setVisibility(View.GONE);
                errorText.setVisibility(View.GONE);

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
        });
        AppControllerSingleton.getMinstance().addToRequestQueue(stringRequest);
    }

    private void MakeContentStringRequest(String url3) {

        progressbar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

//                    Log.w("url_re", response);
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("AllMenu");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = jsonArray.getJSONObject(i);
                        final Get_Set get_set = new Get_Set();

             /*           Log.w("title",data.getString("title"));
                        Log.w("mode",data.getString("mode"));*/

                        if (data.getString("mode").equals("video")) {
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

                        } else if (data.getString("mode").equals("audio")) {
                          /*  Log.w("title",data.getString("title"));
                            Log.w("thumb",data.getString("thumb"));
                            Log.w("mode",data.getString("mode"));*/
                            get_set.setTitle(data.getString("title"));
                            get_set.setContents(data.getString("contents"));
                            get_set.setThumb(data.getString("thumb"));
                            get_set.setId(data.getString("id"));
                            audio.add(get_set);

                        } else if (data.getString("mode").equals("text")) {
                            /*Log.w("title",data.getString("thumb"));
                            Log.w("mode",data.getString("mode"));*/
                            get_set.setThumb(data.getString("thumb"));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                get_set.setDescription(String.valueOf(Html.fromHtml(data.getString("description"), Html.FROM_HTML_MODE_COMPACT)));
                            } else {
                                get_set.setDescription(String.valueOf(Html.fromHtml(data.getString("description"))));
                            }

                            get_set.setTitle(data.getString("title"));
                            get_set.setContents(data.getString("contents"));
                            get_set.setId(data.getString("id"));
                            text.add(get_set);

                        } else if (data.getString("mode").equals("image")) {
                            if (!data.getString("thumb").equals("")) {
                                get_set.setThumb(data.getString("thumb"));
                                get_set.setId(data.getString("id"));
                                images.add(get_set);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    aBoolean = true;
                }

                startActivity(intent);
               /* for (int i=0;i<images.size();i++){
                    Log.w("imagess",images.get(i).getThumb());
                }*/
                progressbar.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
                aBoolean = true;
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
//                    Uri.parse("http://play.google.com/store/apps/details?id=" + getBaseContext().getPackageName())));
                    Uri.parse(getString(R.string.app_link))));
        }
    }

    private void shareApllication() {
        Intent i = new Intent(Intent.ACTION_SEND);

//        String s="12345678903333333333456465467546754654675456456474564564564564564";
//
//        Log.d("st", "shareApllication: "+ Arrays.toString(splitToNChar(s, 10)));

      /*  String lintEnc = null;

        try {
            lintEnc = encryptMsg(getString(R.string.app_link));
            Log.d("link", "shareApllication: "+lintEnc);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_share_content) + "\n" + getString(R.string.app_link));
        startActivity(Intent.createChooser(i, "Share link:"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_content, menu);
        return super.onCreateOptionsMenu(menu);


    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

   /* public static SecretKeySpec generateKey(String link)
            throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = link.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key = digest.digest();
        return new SecretKeySpec(key, "AES");
    }

    public static String encryptMsg(String link)
            throws Exception
    {
   *//* Encrypt the message. *//*

        SecretKeySpec keySpec = generateKey(link);

        Cipher c = Cipher.getInstance("AES");
        c.init(c.ENCRYPT_MODE,keySpec);
        byte[] bytes = c.doFinal(link.getBytes());
        String encvalue = Base64.encodeToString(bytes,Base64.DEFAULT);
        return encvalue;
    }

    public static String decryptMsg(String dec)
            throws Exception
    {
    *//* Decrypt the message, given derived encContentValues and initialization vector. *//*

    SecretKeySpec key = generateKey(dec);
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(cipher.DECRYPT_MODE,key);
    byte[] decodeValue = Base64.decode(dec,Base64.DEFAULT);
    byte[] bytes = cipher.doFinal(decodeValue);
    String decriptedValue = new String(bytes);
    return decriptedValue;

    }*/

    public static class BottomNavigationViewHelper {

        @SuppressLint("RestrictedApi")
        public static void removeShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    item.setShiftingMode(false);
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BottomNav", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BottomNav", "Unable to change value of shift mode", e);
            }
        }
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
