package com.vianet.bhaktidharshanamrit.ActivityClass;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener /*ConnectivityReceiver.ConnectivityReceiverListener*/ {

    //Signin button
//    private SignInButton signInButton;

    //Signing Options
//    private GoogleSignInOptions gso;

    //google api client
//    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
//    private int RC_SIGN_IN = 100;

//    private CallbackManager callbackManager;

    RelativeLayout layout;
//    TextView videoTitle, views, commentsText;  ////      ----------------------------#
//    ExpandableTextView videoDescription;
//    ImageButton  shareVideo, commentVideo;
//    public ImageView likeVideo;
//    ListView commentsList;
    RecyclerView recyclerView;
    ProgressBar playerProgress;
//    Button moreComments;

//    ProgressDialog pDialog;
//    ArrayList<HashMap<String, String>> commentItems;

//    CommentListAdapter adapter;
//    int current_page = 0;

    View dialogView;
//    ImageView facebook_Login;
//    boolean loginFlag;

    String video_id, video_Description, play_Video_File, fromLink;
    int position;
    Button retry;

//    private boolean loading = false;
    private int page = 1;
    private int totalPage = 0;
    int previousSize;
//    private Paginate paginate;

    Animation myAnim;

    SharedPreferences sharedPreferences;
    private TextView textMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setActionBar(toolbar);

//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
//        video_id = intent.getStringExtra("id");
        position = intent.getIntExtra("position",1);      ////////---------#
//        video_Description = intent.getStringExtra("videoDescription");
        play_Video_File = intent.getStringExtra("videoFile");
//        fromLink = intent.getStringExtra("fromLink");

//        Toast.makeText(this, "shared video - "+video_id, Toast.LENGTH_SHORT).show();

//        sectionAdapter = new SectionedRecyclerViewAdapter();  ///-------------------#

//        getView();
        getPlayerViews();

//        if (FirstActivity.is_Login) {
//            Toast.makeText(this, "login "+video_id+" "+FirstActivity.user_ID, Toast.LENGTH_SHORT).show();
//            sendRequest("http://dhinta.com/haryanvidhamaka/API/video_comments_API.php?action=PlayerContent&vidId=" + video_id + "&email=" + FirstActivity.user_ID + "&page=1");
//        }else {
//            Toast.makeText(this, "not login "+video_id, Toast.LENGTH_SHORT).show();
           /* Log.w("video_id", video_id);
            sendRequest(FirstActivity.commonMain+"video_comments_API.php?action=PlayerContent&vidId="
                    + video_id + "&email=null&page=1");
            Log.w("player_url", FirstActivity.commonMain+"video_comments_API.php?action=PlayerContent&vidId="
                    + video_id + "&email=null&page=1");*/
//        }

//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        if (sharedPreferences.getInt("duration", 0) != 0){
////            new RequestTaskPlayer(sharedPreferences.getInt("duration", 0), sharedPreferences.getString("playervideoid", "1")).execute();
////            Toast.makeText(this, ""+sharedPreferences.getInt("duration", 0), Toast.LENGTH_SHORT).show();
//        }

    }

    /*public void sendRequest(final String JSON_URL){
        StringRequest stringRequest = new StringRequest(JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
                Log.w("response", response);
            }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getBaseContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//                recyclerView.setVisibility(View.GONE);
//                final Button retry = new Button(PlayerActivity.this);
//                retry.setText("retry");
//                retry.setLayoutParams(new ViewGroup.LayoutParams(80, 30));
//                layout.addVie w(retry);
//                playerProgress.setVisibility(View.GONE);
//                retry.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        recyclerView.setVisibility(View.VISIBLE);
//                        playerProgress.setVisibility(View.VISIBLE);
//                        retry.setVisibility(View.GONE);
//                        sendRequest(JSON_URL);
//                    }
//                });
                playerProgress.setVisibility(View.GONE);
                progress_page.setVisibility(View.GONE);
                Log.w("suggestive_list", String.valueOf(PlayerJson.suggestiveList.size()));
                if (PlayerJson.suggestiveList.size() < 1) {
                    retry.setVisibility(View.VISIBLE);
                }
//                Toast.makeText(getBaseContext(), "NetWork Error.", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json) {
        PlayerJson pj = new PlayerJson(json);
        boolean fatched = pj.parseJSON(page);

//        if (PlayerJson.views != null) {
//            views.setText(PlayerJson.views);
//        }else {
//            views.setText("0");
//        }

        if (PlayerJson.suggestiveList.size() > 0){
            next.setEnabled(true);
        }
//        else {
//            next.setEnabled(false);
//        }

        if (page == 1) {
            playerProgress.setVisibility(View.GONE);

//            if (fromLink != null && fromLink.equalsIgnoreCase("fromLink")){
//                videoTitle.setText(PlayerJson.title);
////                videoDescription.setText(PlayerJson.description);
//            }

//            if (PlayerJson.isLiked != null) {
//                if (!PlayerJson.isLiked.equals("null")) {
//                    setLike();
////                likeVideo.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//                    liked = false;
//                }
//            }

//            if (PlayerJson.videoComments != null) {
//                for (int i = 0; i < 2; i++) {
//                    if (i < PlayerJson.videoComments.length) {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put("Name", PlayerJson.userName[i]);
//                        map.put("Comment", PlayerJson.videoComments[i]);
//                        current_page += 1;
//                        commentItems.add(map);
//                    }
//                }
//            }

//            if (current_page > 0) {
//                loadList();
//            } else {
//                commentsList.setVisibility(View.GONE);
//                commentsText.setVisibility(View.GONE);
//            }
        }

        if (fatched) {

            Log.w("list_size", String.valueOf(PlayerJson.suggestiveList.size()));

            setRecyclerView();
        }else {
            if (page == 1){
                playerProgress.setVisibility(View.GONE);
                if (PlayerJson.suggestiveList.size() < 1) {
                    retry.setVisibility(View.VISIBLE);
                }
//                Toast.makeText(getBaseContext(), "no suggestive video", Toast.LENGTH_SHORT).show();
            }else {

                setRecyclerView();
            }
        }
    }*/

//    private void setLike(){
//        likeVideo.startAnimation(myAnim);
//        switch (MainActivity.select_theme){
//            case 0:
//                likeVideo.setImageResource(R.drawable.like_thumb_blue);
//                break;
//            case 1:
//                likeVideo.setImageResource(R.drawable.like_thumb_green);
//                break;
//            case 2:
//                likeVideo.setImageResource(R.drawable.like_thumb_red);
//                break;
//            case 3:
//                likeVideo.setImageResource(R.drawable.like_thumb_purple);
//                break;
//        }
//    }

//    private class Recycler extends GridLayoutManager{
//        public Recycler(Context context, int spanCount, int orientation, boolean reverseLayout) {
//            super(context, spanCount, orientation, reverseLayout);
//        }
//        @Override
//        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//            try {
//                super.onLayoutChildren(recycler, state);
//            } catch (IndexOutOfBoundsException e) {
//
//            }
//        }
//
//    }

    /*private void setRecyclerView(){
        progress_page.setVisibility(View.GONE);
//        loading = false;
        try {
            if (page == 1) {

    //            if (previousSize != PlayerJson.suggestive_row_file.size()) {
    //                previousSize = PlayerJson.suggestive_row_file.size();
    //            }
    //            if (paginate != null) {
    //                paginate.unbind();
    //            }
                if (Integer.parseInt(PlayerJson.count) % 6 == 0){
                    totalPage = Integer.parseInt(PlayerJson.count) / 6;
                }else {
                    totalPage = (Integer.parseInt(PlayerJson.count) / 6) + 1;
                }

                sectionAdapter.addSection(new SectionsPlayer("Suggestive Videos"));

                LinearLayoutManager glm = new LinearLayoutManager(getBaseContext());
    //            GridLayoutManager glm = new GridLayoutManager(getBaseContext(), 2);
    //            GridLayoutManager glm = new Recycler(getBaseContext(), 2, GridLayoutManager.VERTICAL, false);
    //            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
    //                @Override
    //                public int getSpanSize(int position) {
    //                    switch(sectionAdapter.getSectionItemViewType(position)) {
    //                        case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
    //                            return 2;
    //                        default:
    //                            return 1;
    //                    }
    //                }
    //            });

                recyclerView.setLayoutManager(glm);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(sectionAdapter);

    //            if (page >= 2) {
    //                paginate = Paginate.with(recyclerView, this)
    //                        .addLoadingListItem(true).build();
    //            }
            }else {
                sectionAdapter.addData_player(previousSize);
    //            previousSize = PlayerJson.suggestive_row_file.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

//    @Override
//    public synchronized void onLoadMore() {
//        loading = true;
//
//        if (FirstActivity.is_Login){
//            sendRequest("http://dhinta.com/haryanvidhamaka/API/video_comments_API.php?action=PlayerContent&vidId=" + video_id + "&email=" + FirstActivity.user_ID + "&page=" + page);
//        }else {
//            sendRequest("http://dhinta.com/haryanvidhamaka/API/video_comments_API.php?action=PlayerContent&vidId=" + video_id + "&email=null&page=" + page);
//        }
//    }
//
//    @Override
//    public synchronized boolean isLoading() {
//        return loading;
//    }
//
//    @Override
//    public boolean hasLoadedAllItems() {
//        return page == totalPage;
//    }


//    private void getView(){
//
////        listHeader = ((LayoutInflater)PlayerActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.player_list_header, null, false);
//        layout = (RelativeLayout) findViewById(R.id.lllll);
//        playerProgress = (ProgressBar) findViewById(R.id.playerProgress);
//        playerProgress.setVisibility(View.VISIBLE);
//        videoTitle = (TextView) findViewById(R.id.videoTitle);
////        videoDescription = (ExpandableTextView) findViewById(R.id.videoDescrip);
////        videoDescription.setVisibility(View.GONE);
//        views = (TextView) findViewById(R.id.views);
//        commentsText = (TextView) findViewById(R.id.commentsText);
//        recyclerView = (RecyclerView) findViewById(R.id.playerCat);
////        likeVideo = (ImageView) findViewById(R.id.likeVideo);
////        likeVideo.setVisibility(View.INVISIBLE);
////        shareVideo = (ImageButton) findViewById(R.id.shareVideo);
////        commentVideo = (ImageButton) findViewById(R.id.commentsVideo);
////        commentVideo.setVisibility(View.INVISIBLE);
////
////        commentsList = (ListView) findViewById(R.id.commentList);
////
////        commentItems = new ArrayList<HashMap<String, String>>();
//
//        retry = (Button) findViewById(R.id.retry);
//
//        setViews();
//    }

    /*private void setViews(){

        videoTitle.setText(video_Title);

        videoTitle.setMaxLines(1);

        videoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if(videoTitle.getMaxLines()==1) {
                        videoTitle.setMaxLines(Integer.MAX_VALUE);
                    }
                    else {
                        videoTitle.setMaxLines(1);
//                        image_more_about.setImageResource(R.drawable.expand);
                    }
                }
            }
        });
//        videoDescription.setText(video_Description);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest(FirstActivity.commonMain+"video_comments_API.php?action=PlayerContent&vidId=" + video_id + "&email=null&page=1");
                retry.setVisibility(View.GONE);
                playerProgress.setVisibility(View.VISIBLE);
                Log.w("video_id", video_id);
            }
        });

//        commentsText.setText("Comments");

//        likeVideo.setImageResource(R.drawable.like_blank);
//        shareVideo.setBackgroundResource(R.drawable.share);
//        commentVideo.setBackgroundResource(R.drawable.comment);


//        likeVideo.setOnClickListener(this);
//        shareVideo.setOnClickListener(this);
//        commentVideo.setOnClickListener(this);

//        myAnim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.bounce);
//        double animationDuration = 1 * 1000;
//        myAnim.setDuration((long)animationDuration);

//        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.37, 10);
//
//        myAnim.setInterpolator(interpolator);

    }*/

//    private void loadList(){
//
//        LayoutInflater inflater = getLayoutInflater();
//        ViewGroup footer = (ViewGroup)inflater.inflate(R.layout.comment_list_footer, commentsList, false);
//        Button moreComments = (Button) footer.findViewById(R.id.listFooterBtn);
//        switch (MainActivity.select_theme){
//            case 0:
//                moreComments.setBackgroundResource(R.drawable.circle_btn);
//                break;
//            case 1:
//                moreComments.setBackgroundResource(R.drawable.circle_btn_green);
//                break;
//            case 2:
//                moreComments.setBackgroundResource(R.drawable.circle_btn_red);
//                break;
//            case 3:
//                moreComments.setBackgroundResource(R.drawable.circle_btn_purple);
//                break;
//        }
////        Button moreComments = new Button(this);
//        moreComments.setText("More");
////        moreComments.setBackgroundResource(R.drawable.circle_btn);
////        moreComments.setLayoutParams(new LayoutParams(100, 100));
//
////        commentsList.addHeaderView(listHeader);
//
//        moreComments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadMoreComments();
//            }
//        });
//
//        adapter = new CommentListAdapter(this, commentItems);
//        commentsList.setAdapter(adapter);
//        justifyListViewHeightBasedOnChildren(commentsList, 100);
//        commentsList.addFooterView(footer);
//    }

//    public void justifyListViewHeightBasedOnChildren (ListView listView, int a) {
//
//        commentsList.setDivider(null);
//        ListAdapter adapter = listView.getAdapter();
//
//        if (adapter == null) {
//            return;
//        }
//        ViewGroup vg = listView;
//        int totalHeight = 0;
//        for (int i = 0; i < adapter.getCount(); i++) {
//            View listItem = adapter.getView(i, null, vg);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams par = listView.getLayoutParams();
//        par.height = a + totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
//        listView.setLayoutParams(par);
//        listView.requestLayout();
//    }

//    boolean liked = true;
//    View dialogView;
//    @Override
//    public void onClick(View view) {
//
//        switch (view.getId()){
//            case R.id.likeVideo:
////                Toast.makeText(this, "Clicked on like", Toast.LENGTH_SHORT).show();
//                if (FirstActivity.is_Login) {
//                    if (liked) {
//                        setLike();
////                        likeVideo.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//                        liked = false;
//                        new RequestTask(video_id).execute();
//                    } else {
//                        likeVideo.startAnimation(myAnim);
//                        likeVideo.setImageResource(R.drawable.like_blank);
////                        likeVideo.setBackgroundColor(getResources().getColor(android.R.color.white));
//                        liked = true;
//                        new RequestTask(video_id).execute();
//                    }
//                }else {
////                    dialogView = getLayoutInflater().inflate(R.layout.activity_search, null);
////                    gmail_Login = (ImageView) dialogView.findViewById(R.id.gPlus);
////                    facebook_Login = (ImageView) dialogView.findViewById(R.id.facebook);
////                    dialogHeader = (TextView) dialogView.findViewById(R.id.headerText);
////                    dialogHeader.setText("Please LogIn First!!");
////                    showDialog("likeVideo");
//                    loginFlag = true;
//                    showLoginDialog();
//                }
//                break;
//            case R.id.shareVideo:
////                Toast.makeText(this, "Clicked on share", Toast.LENGTH_SHORT).show();
//                shareVideo.setEnabled(false);
//                int applicationNameId = getBaseContext().getApplicationInfo().labelRes;
////                final String appPackageName = getBaseContext().getPackageName();
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(Intent.EXTRA_SUBJECT, getBaseContext().getString(applicationNameId));
////                String text = "Download the latest Team Film Bhojpuri app."+"\nDownload this at:";
//                String link = "http://m.haryanvidhamaka.com/watch.php?id=" + video_id + "/" + play_Video_File;
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                i.putExtra(Intent.EXTRA_TEXT, ""+ link);
//                startActivity(Intent.createChooser(i, "Shared video link:"));
//                shareVideo.setEnabled(true);
//                break;
//            case R.id.commentsVideo:
////                Toast.makeText(this, "Clicked on comment", Toast.LENGTH_SHORT).show();
//                if (FirstActivity.is_Login) {
//
//                    showCommentDialog();
//
//                }else {
//                    loginFlag = false;
//                    showLoginDialog();
//                }
//
//                break;
//            case R.id.gPlus:
////                Toast.makeText(this, "Clicked on gmail_login", Toast.LENGTH_SHORT).show();
//                if (FirstActivity.isNetworkAvailable(getBaseContext())) {
//                    loginProgress.setVisibility(View.VISIBLE);
//
////                    signIn();
//                }else {
//                    Toast.makeText(this, "No Network.", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.facebook:
//                if (FirstActivity.isNetworkAvailable(getBaseContext())) {
//                    loginProgress.setVisibility(View.VISIBLE);
//
////                    LoginButton loginfb = new LoginButton(PlayerActivity.this);
////                    loginfb.setReadPermissions(Arrays.asList("public_profile", "email"));
////                    try {
////                        loginfb.performClick();
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//                }else {
//                    Toast.makeText(this, "No Network.", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show();
//
//        }
//    }

//    private void showLoginDialog(){
//        dialogView = getLayoutInflater().inflate(R.layout.login_dialog, null);
////        signInButton = (SignInButton) dialogView.findViewById(R.id.gPlus);
////        facebook_Login = (ImageView) dialogView.findViewById(R.id.facebook);
//        loginProgress = (ProgressBar) dialogView.findViewById(R.id.progressBar3);
//        loginProgress.setVisibility(View.GONE);
////                    dialogHeader.setText("Please LogIn First!!");
//        //gmail----------------------------------------------
//
//        //Initializing google signin option
////        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
////                .requestEmail()
////                .build();
////
////        //Initializing signinbutton
//////                signInButton = (SignInButton) findViewById(R.id.sign_in_button);
////        signInButton.setSize(SignInButton.SIZE_WIDE);
////        signInButton.setScopes(gso.getScopeArray());
////
////        //Initializing google api client
////        mGoogleApiClient = new GoogleApiClient.Builder(PlayerActivity.this)
//////                      .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
////                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
////                .build();
////        signInButton.setOnClickListener(this);
//
//        //facebook------------------------------------------
////        FacebookSdk.sdkInitialize(getApplicationContext());
////        callbackManager = CallbackManager.Factory.create();
////
////        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
////            @Override
////            public void onSuccess(LoginResult loginResult) {
////
////             /*   Profile profile = Profile.getCurrentProfile();
////                if(profile!=null) {
////                    Toast.makeText(getApplicationContext(), profile.getName(), Toast.LENGTH_LONG).show();
////                }else
////                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
////              //  loginResult.getAccessToken().getPermissions("user")
////              //  Intent home=new Intent(MainActivity.this,Home.class);
////                //startActivity(home); */
////
////                GraphRequest request = GraphRequest.newMeRequest(
////                        loginResult.getAccessToken(),
////                        new GraphRequest.GraphJSONObjectCallback() {
////                            @Override
////                            public void onCompleted(JSONObject object, GraphResponse response) {
//////                                Log.v("LoginActivity", response.toString());
////
////                                // Application code
////                                JSONObject json = response.getJSONObject();
////
//////                                Toast.makeText(getApplicationContext(),String.valueOf(json),Toast.LENGTH_LONG).show();
////                                try {
////
////                                    String d = json.getString("name");
////                                    String a = json.getString("email");
//////                                    Toast.makeText(PlayerActivity.this, d+" "+a, Toast.LENGTH_SHORT).show();
////                                    SharedPreferences sharedpreferences = PreferenceManager
////                                            .getDefaultSharedPreferences(getBaseContext());
////                                    SharedPreferences.Editor editor = sharedpreferences.edit();
////                                    editor.putBoolean("IS_LOGIN", true);
////                                    editor.putString("USER_NAME", json.getString("name"));
////                                    editor.putString("USER_ID", a);
//////
////                                    editor.apply();
////
////                                    FirstActivity.user_ID = a;
////                                    FirstActivity.is_Login = true;
////
////                                    builder.dismiss();
//////                                    Toast.makeText(PlayerActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
////
////                                    new RequestTaskLogin("facebook", d).execute();
////
////
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
////                            }
////                        });
////                Bundle parameters = new Bundle();
////                parameters.putString("fields", "id,name,email,gender,birthday");
////                request.setParameters(parameters);
////                request.executeAsync();
////
////
////            }
////
////            @Override
////            public void onCancel() {
////                loginProgress.setVisibility(View.GONE);
////                Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();
////
////            }
////
////            @Override
////            public void onError(FacebookException error) {
////                loginProgress.setVisibility(View.GONE);
////            }
////
////
////        });
////
////        facebook_Login.setOnClickListener(this);
//
//        showDialog();
//    }

//    Dialog builder;
//    private void showDialog(){
//
//        try {
//            builder = new Dialog(PlayerActivity.this);
//            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            builder.getWindow().setBackgroundDrawable(
//                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialogInterface) {
//                    //nothing;
//                }
//            });
//
//            builder.addContentView(dialogView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            if (!isFinishing()) {
//                builder.show();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

   /* private void showDialogPlayer(){

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set title
            alertDialogBuilder.setTitle(R.string.app_name);

            // set dialog message
            alertDialogBuilder
                    .setMessage("Do you wish to resume from where you stopped?").setTitle(video_Title)
                    .setCancelable(false)
                    .setIcon(R.drawable.videos)
                    .setPositiveButton("Resume", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int resumeTime = sharedPreferences.getInt("durationplay", 0);
                            player1.loadVideo(play_Video_File, resumeTime);
                            setTime(resumeTime);

                        }
                    })
                    .setNegativeButton("Start Over", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            player1.loadVideo(play_Video_File);
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            if (!isFinishing()) {
                // show it
                alertDialog.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    //This function will option signing intent
//    private void signIn() {
//
//        //Creating an intent
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//
//        //Starting intent for result
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //If signin
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            //Calling a new function to handle signin
//            handleSignInResult(result);
//        }else {
//            callbackManager.onActivityResult(requestCode,resultCode,data);
//        }
//    }


    //After the signing we are calling this function
//    private void handleSignInResult(GoogleSignInResult result) {
//        //If the login succeed
//        if (result.isSuccess()) {
//            //Getting google account
//            GoogleSignInAccount acct = result.getSignInAccount();
//
//            //Displaying name and email
////            textViewName.setText(acct.getDisplayName());
////            textViewEmail.setText(acct.getEmail());
//
//            //Initializing image loader
////            imageLoader = MySingleton.getInstance(this.getApplicationContext())
////                    .getImageLoader();
//
////            imageLoader.get(acct.getPhotoUrl().toString(),
////                    ImageLoader.getImageListener(profilePhoto,
////                            R.mipmap.ic_launcher,
////                            R.mipmap.ic_launcher));
//
//            //Loading image
////            profilePhoto.setImageUrl(acct.getPhotoUrl().toString(), imageLoader);
//
//            //storing user information
//            SharedPreferences sharedpreferences = PreferenceManager
//                    .getDefaultSharedPreferences(getBaseContext());
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putBoolean("IS_LOGIN", true);
//            editor.putString("USER_NAME", acct.getDisplayName());
//            editor.putString("USER_ID", acct.getEmail());
//            if (acct.getPhotoUrl() != null)
//                editor.putString("USER_IMAGE", acct.getPhotoUrl().toString());
//            editor.apply();
//
//            FirstActivity.user_ID = acct.getEmail();
//            FirstActivity.is_Login = true;
//
//            builder.dismiss();
//
//            new RequestTaskLogin("gmail", acct.getDisplayName()).execute();
////            if (loginFlag){
////                setLike();
//////                likeVideo.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
////                liked = false;
////            }else {
////                showCommentDialog();
////            }
//
//        } else {
//            //If login fails
//            loginProgress.setVisibility(View.GONE);
//            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
//        }
//    }

//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }

//    private void loadMoreComments(){
////        pDialog = new ProgressDialog(PlayerActivity.this);
////        pDialog.setMessage("Please wait..");
////        pDialog.setIndeterminate(true);
////        pDialog.setCancelable(false);
////        pDialog.show();
//
//        if (current_page != PlayerJson.videoComments.length) {
//        //call sendRequest
//            for (int i = 0; i < 2; i++){
//                HashMap<String, String> map = new HashMap<String, String>();
//                if (current_page < PlayerJson.videoComments.length) {
//
//                    map.put("Name", PlayerJson.userName[current_page]);
//                    map.put("Comment", PlayerJson.videoComments[current_page]);
//
//                    commentItems.add(map);
//                    current_page += 1;
//                }
//            }
//
//            int currentPosition = commentsList.getFirstVisiblePosition();
//
//            adapter = new CommentListAdapter(this, commentItems);
//            commentsList.setAdapter(adapter);
//
//            commentsList.setSelectionFromTop(currentPosition + 1, 0);
//
//            justifyListViewHeightBasedOnChildren(commentsList, 0);
//        }else {
//            Toast.makeText(this, "no more comments", Toast.LENGTH_SHORT).show();
//        }
////        pDialog.dismiss();
//    }

//    public void showCommentDialog(){
//
//        LayoutInflater li = LayoutInflater.from(this);
//        View promptsView = li.inflate(R.layout.comment_dialog, null);
//
//        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                PlayerActivity.this, R.style.AlertDiagCustom);
//
//        // set prompts.xml to alertdialog builder
//        alertDialogBuilder.setView(promptsView);
//
//        final EditText userInput = (EditText) promptsView
//                .findViewById(R.id.commentText);
//        LinearLayout dialogColor = (LinearLayout) promptsView.findViewById(R.id.commentDialogcolor);
//
//        // set dialog message
//        alertDialogBuilder
//                .setCancelable(false)
//                .setPositiveButton("OK", null)
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//
//        final AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(final DialogInterface dialogInterface) {
//                Button ok = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
////                        Toast.makeText(getBaseContext(), ""+userInput.getText().toString(), Toast.LENGTH_SHORT).show();
//                        String input = userInput.getText().toString();
//                        if (!input.matches("") && input.trim().length() > 0) {
//
//                            HashMap<String, String> map = new HashMap<String, String>();
//                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//                            map.put("Name", sharedPreferences.getString("USER_NAME", "User"));
//                            map.put("Comment", input);
//
//                            commentItems.add(map);
//
//                            int currentPosition = commentsList.getFirstVisiblePosition();
//
//                            adapter = new CommentListAdapter(PlayerActivity.this, commentItems);
//                            commentsList.setAdapter(adapter);
//
//                            commentsList.setSelectionFromTop(currentPosition + 1, 0);
//
//                            justifyListViewHeightBasedOnChildren(commentsList, 0);
//
//                            if (!commentsText.isShown()){
//                                commentsList.setVisibility(View.VISIBLE);
//                                commentsText.setVisibility(View.VISIBLE);
//                            }
//
//                            new RequestTaskComment(video_id, userInput.getText().toString()).execute();
//
//                            dialogInterface.dismiss();
//                        }else {
//                            Toast.makeText(PlayerActivity.this, "Please type your comment.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
//
//        if (!isFinishing()) {
//            alertDialog.show();
//        }
//
//        switch (MainActivity.select_theme){
//            case 0:
//                dialogColor.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//                break;
//            case 1:
//                dialogColor.setBackgroundColor(getResources().getColor(R.color.greenColorPrimaryDark));
//                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.greenColorPrimaryDark));
//                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.greenColorPrimaryDark));
//                break;
//            case 2:
//                dialogColor.setBackgroundColor(getResources().getColor(R.color.redColorPrimaryDark));
//                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.redColorPrimaryDark));
//                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.redColorPrimaryDark));
//                break;
//            case 3:
//                dialogColor.setBackgroundColor(getResources().getColor(R.color.purpleColorPrimaryDark));
//                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.purpleColorPrimaryDark));
//                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.purpleColorPrimaryDark));
//                break;
//        }
//
//    }

    /*class SectionsPlayer extends StatelessSection {

        String title;

        public SectionsPlayer(String title) {
            super(R.layout.section_more_header, R.layout.section_more_items);

            this.title = title;
        }

        @Override
        public int getContentItemsTotal() {
            try{
                return PlayerJson.suggestiveList.size();
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {

            return new ItemViewHolder(view);

        }

        @Override
        public void onBindItemViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

//            String name = list.get(position).getName();
//            String category = list.get(position).getCategory();

            try {
                itemHolder.tvItem.setText(PlayerJson.suggestiveList.get(position)
                        .getTitle().trim());
//            itemHolder.tvSubItem.setText(category);
                Glide.with(getBaseContext()).load("http://img.youtube.com/vi/"
                        + PlayerJson.suggestiveList.get(position).getVideo_id()
                        + "/mqdefault.jpg")
                        .thumbnail(0.5f)
                        .crossFade(0).placeholder(R.drawable.cdthumbnail)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(itemHolder.imgItem);
//                itemHolder.imgItem.setDefaultImageResId(R.drawable.cdthumbnail);
//                itemHolder.imgItem.setImageUrl("http://img.youtube.com/vi/" + PlayerJson.suggestive_row_file.get(position) + "/mqdefault.jpg", itemHolder.imageLoader);
                itemHolder.imgItem.setScaleType(ImageView.ScaleType.FIT_XY);
            }catch (Exception e){
                e.printStackTrace();
            }

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getBaseContext(), String.format("Clicked on position #%s of Section %s",
//                            sectionAdapter.getSectionPosition(itemHolder.getAdapterPosition()), title),
//                            Toast.LENGTH_SHORT).show();
                    try {
                        if (player1 != null) {

                            tyu = 1;
                            boolb = true;
//                if (task != null)
//                    task.cancel(true);
                            processTime.setText("00:00");
                            seekBar.setProgress(0);
                            resetTime();
//                        if ((player1.getCurrentTimeMillis()) / (1000) >= 10) {
//                            if (nextPosition == -1) {
//                                new RequestTaskPlayer((player1.getCurrentTimeMillis()) / (1000), video_id).execute();
//                            } else {
//                                new RequestTaskPlayer((player1.getCurrentTimeMillis()) / (1000), PlayerJson.suggestive_videoId.get(nextPosition)).execute();
//                            }
//                        }
//                            if (PlayerJson.suggestive_video_mode.get(position).equalsIgnoreCase("youtube")) {
                                tyu = 1;
                                processTime.setText("00:00");
                                seekBar.setProgress(0);
                                resetTime();
                                player1.loadVideo(PlayerJson.suggestiveList.get(position)
                                .getVideo_id());
                                videoTitle.setText(PlayerJson.suggestiveList.get(position).getTitle());
//                            videoDescription.setText(PlayerJson.suggestive_description.get(position));
//                            }
//                            else {
//                                task.cancel(true);
//                                Intent intent = new Intent(getBaseContext(), CustomPlayerActivitty.class);
//                                intent.putExtra("videoId", PlayerJson.suggestive_videoId.get(position));
//                                intent.putExtra("videoTitle", PlayerJson.suggestive_title.get(position));
//                                intent.putExtra("videoDescription", PlayerJson.suggestive_description.get(position));
//                                intent.putExtra("videoFile", PlayerJson.suggestive_row_file.get(position));
//                                startActivity(intent);
//                                finish();
//                            }
                            nextPosition = position;
                            video_id = PlayerJson.suggestiveList.get(position).getId();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });


        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            try {
                switch (MainActivity.select_theme) {
                    case 0:
                        headerHolder.tvTitle.setTextColor(ContextCompat.getColor(getBaseContext(),
                                R.color.colorPrimaryDark));
//                    headerHolder.btnMore.setBackgroundResource(R.drawable.selector_btn);
                        break;
                    case 1:
                        headerHolder.tvTitle.setTextColor(ContextCompat.getColor(getBaseContext(),
                                R.color.greenColorPrimaryDark));
//                    headerHolder.btnMore.setBackgroundResource(R.drawable.selector_btn_green);
                        break;
                    case 2:
                        headerHolder.tvTitle.setTextColor(ContextCompat.getColor(getBaseContext(),
                                R.color.redColorPrimaryDark));
//                    headerHolder.btnMore.setBackgroundResource(R.drawable.selector_btn_red);
                        break;
                    case 3:
                        headerHolder.tvTitle.setTextColor(ContextCompat.getColor(getBaseContext(),
                                R.color.purpleColorPrimaryDark));
//                    headerHolder.btnMore.setBackgroundResource(R.drawable.selector_btn_purple);
                        break;

                }
            }catch (Exception e){
                e.printStackTrace();
            }

            headerHolder.tvTitle.setText(title);

//            headerHolder.btnMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getBaseContext(), String.format("Clicked on more button from the header of Section %s",
//                            title),
//                            Toast.LENGTH_SHORT).show();
//
//                }
//            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
//        private final Button btnMore;

        public HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//            btnMore = (Button) view.findViewById(R.id.btnMore);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgItem;
        private final TextView tvItem;
        private final TextView tvItemTime;
        private ImageLoader imageLoader;
        private final ImageView itemPlay;

        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imageLoader = MySingleton.getInstance(getBaseContext()).getImageLoader();
            imgItem = (ImageView) view.findViewById(R.id.itemImage);
            tvItem = (TextView) view.findViewById(R.id.itemTitle);
            tvItemTime = (TextView) view.findViewById(R.id.itemTime);
            tvItemTime.setVisibility(View.GONE);
            itemPlay = (ImageView) view.findViewById(R.id.itemPlay);
        }
    }*/


//    private List<Category> getTopRatedMoviesList() {
//        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources()
//                .getStringArray(R.array.top_rated_movies)));
//
//        List<Category> movieList = new ArrayList<>();
//
//        for (String str : arrayList) {
//            String[] array = str.split("\\|");
//            movieList.add(new Category(array[0], array[1]));
//        }
//
//        return movieList;
//    }
//
//    private List<Category> getMostPopularMoviesList() {
//        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources()
//                .getStringArray(R.array.most_popular_movies)));
//
//        List<Category> movieList = new ArrayList<>();
//
//        for (String str : arrayList) {
//            String[] array = str.split("\\|");
//            movieList.add(new Category(array[0], array[1]));
//        }
//
//        return movieList;
//    }

//    class Category {
//        String name;
//        String category;
//
//        public Category(String name, String category) {
//            this.name = name;
//            this.category = category;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getCategory() {
//            return category;
//        }
//
//        public void setCategory(String category) {
//            this.category = category;
//        }
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == android.R.id.home){
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        try {
            if (task != null)
                task.cancel(true);
            if (player1 != null)
                player1.pause();
        }catch (Exception e){
            e.printStackTrace();
        }
//        finish();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        try {
        if (player1 != null) {

//            if ((player1.getCurrentTimeMillis()) / (1000) >= 10) {
////            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putInt("duration", (player1.getCurrentTimeMillis()) / (1000));
//                if (nextPosition == -1) {
//                    editor.putString("playervideoid", video_id);
//                } else {
//                    editor.putString("playervideoid", PlayerJson.suggestive_videoId.get(nextPosition));
//                }
//                editor.apply();
//            }
            player1.pause();
                /*if (player1.getCurrentTimeMillis() < player1.getDurationMillis()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("durationplay", (player1.getCurrentTimeMillis()));
                    if (nextPosition == -1) {
                        editor.putString("playervideoidplay", video_id);
                    } else {
                        editor.putString("playervideoidplay", PlayerJson.suggestiveList
                                .get(nextPosition).getId());
                    }
                    editor.apply();
                }*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    int seekTime = 0;
    @Override
    protected void onPause() {
//        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
        /*try {
            if (player1 != null) {
                if (player1.isPlaying()) {
                    seekTime = player1.getCurrentTimeMillis();
                }
            }
            unregisterReceiver(new ConnectivityReceiver());
        }catch (Exception e){
            PlayerActivity.this.finish();
        }*/
    }

    @Override
    protected void onResume() {
//        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
        super.onResume();
        /*if (isYoutubeInstall){
            youTubePlayerView.initialize(API_KEY, this);
        }*/
        /*NetworkCheck.getInstance().setConnectivityListener(this);
        try {
            if (player1 != null && seekTime != 0) {
                if (nextPosition == -1) {
                    player1.loadVideo(play_Video_File, seekTime);
                } else {
                    player1.loadVideo(PlayerJson.suggestiveList.get(nextPosition)
                            .getVideo_id(), seekTime);
                }
            }
        }catch (Exception e){
            PlayerActivity.this.finish();
        }*/
    }

//    public class RequestTask extends AsyncTask<String, String, String> {
//
//                String videoID;
////        int duration;
//        public RequestTask(String videoID){
//            this.videoID = videoID;
////            this.duration = duration;
//        }
//
//        @Override
//        protected String doInBackground(String... uri) {
////            String responseString = null;
//            try {
//                URL url = new URL("http://dhinta.com/haryanvidhamaka/API/video_like_api.php?action=VideoLike&videoId=" + videoID + "&email=" + FirstActivity.user_ID);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
//                    // Do normal input or output stream reading
////                    responseString = "Passed";
//                }
//                else {
////                    responseString = "FAILED"; // See documentation for more info on response handling
//                }
//            } catch (IOException e) {
//                //TODO Handle problems..
//            }
//            return null;//responseString;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            //Do anything with response..
////            Toast.makeText(getBaseContext(), result+" "+FirstActivity.user_ID, Toast.LENGTH_SHORT).show();
//        }
//    }

//    public class RequestTaskComment extends AsyncTask<String, String, String> {
//
//        String videoID, comment;
//        //        int duration;
//        public RequestTaskComment(String videoID, String comment){
//            this.videoID = videoID;
//            this.comment = comment;
//        }
//
//        @Override
//        protected String doInBackground(String... uri) {
////            String responseString = null;
//            try {
//                URL url = new URL("http://dhinta.com/haryanvidhamaka/API/video_comments_API.php?action=videoComment&vidId=" + videoID + "&email=" + FirstActivity.user_ID + "&comments=" + URLEncoder.encode(comment));
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
//                    // Do normal input or output stream reading
////                    responseString = "Passed";
//                }
//                else {
////                    responseString = "FAILED"; // See documentation for more info on response handling
//                }
//            } catch (IOException e) {
//                //TODO Handle problems..
//            }
//            return null;//responseString;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            //Do anything with response..
////            Toast.makeText(getBaseContext(), comment, Toast.LENGTH_SHORT).show();
//        }
//    }

//    public class RequestTaskLogin extends AsyncTask<String, String, String> {
//
//        String social, name;
//        //        int duration;
//        public RequestTaskLogin(String social, String name){
//            this.social = social;
//            this.name = name;
//        }
//
//        @Override
//        protected String doInBackground(String... uri) {
//            String responseString = null;
//            try {
//                String json = "http://dhinta.com/haryanvidhamaka/API/user_login.php?action=l0ginStatus&deviceID=1" +
//                        "&social_name=" + social + "&name=" + name + "&email=" + FirstActivity.user_ID;
//                URL url = new URL(json.replace(" ",  "%20"));
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
//                    // Do normal input or output stream reading
//                    responseString = "Passed";
//                }
//                else {
//                    responseString = "FAILED"; // See documentation for more info on response handling
//                }
//            } catch (IOException e) {
//                //TODO Handle problems..
//            }
//            return responseString;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            //Do anything with response..
////            Toast.makeText(getBaseContext(), social+" "+ name, Toast.LENGTH_SHORT).show();
//            if (result.equalsIgnoreCase("Passed")) {
//                if (loginFlag) {
//                    setLike();
////                                        likeVideo.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//                    liked = false;
//                } else {
//                    showCommentDialog();
//                }
//                Toast.makeText(PlayerActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//            }else {
//                Toast.makeText(getBaseContext(), "Failed! try again.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }



    //----------youtube Player-------------------------------------------------------------------------------------------

//    RelativeLayout player, playerControl;
    public TextView totalTime, processTime;
    int sc = 0, mm = 0, hh = 0;
    dotime task;
    public SeekBar seekBar;
    ImageView playPause, next;//, volume;
    static boolean boolb = true;
    public static int tyu;
    YouTubePlayerView youTubePlayerView;
//    NestedScrollView scrollContent;
//    int nextPosition = -1;
    Handler hhl;
//    ProgressBar progress_page;


    public static final String API_KEY = "AIzaSyAznLIu7lYME1302tBfSPfsU_HOkqJpcaw";

    int adv=0;
    private void getPlayerViews(){
        totalTime = (TextView) findViewById(R.id.play_time);
        processTime = (TextView) findViewById(R.id.current_time_running);
        seekBar = (SeekBar) findViewById(R.id.video_seekbar);
//        volume = (ImageButton) findViewById(R.id.volume);
        playPause = (ImageView) findViewById(R.id.play_video);
        next = (ImageButton) findViewById(R.id.next);
//        next.setEnabled(false);
//        player = (RelativeLayout) findViewById(R.id.player);
//        playerControl = (RelativeLayout) findViewById(R.id.relativeLayout);
        totalTime.setText("00:00");
        processTime.setText("00:00");
//        scrollContent = (NestedScrollView) findViewById(R.id.scrollContent);
//        progress_page = (ProgressBar) findViewById(R.id.progress_page);
//        textMore = (TextView) findViewById(R.id.moreData);
        youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);
        hhl = new Handler();
//        playerControlListner();

        /*scrollContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View view = (View) v.getChildAt(v.getChildCount()-1);
                int diff = (view.getBottom()-(v.getHeight() + v.getScrollY()));

                if (diff == 0) {
                    if (!progress_page.isShown()) {
                        if (totalPage >= 2 && totalPage != page) {
                            page = page + 1;
                            previousSize = PlayerJson.suggestiveList.size();
                            sendRequest(FirstActivity.commonMain+
                                    "video_comments_API.php?action=PlayerContent&vidId="
                                    + video_id + "&email=null&page=" + page);
                            progress_page.setVisibility(View.VISIBLE);
                            scrollContent.post(new Runnable() {
                                @Override
                                public void run() {
                                    scrollContent.fullScroll(NestedScrollView.FOCUS_DOWN);
                                }
                            });
                        } else {
                            if (totalPage > 1) {
                                textMore.setVisibility(View.VISIBLE);
                                scrollContent.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        scrollContent.fullScroll(NestedScrollView.FOCUS_DOWN);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });*/

        /*final AudioManager myAudioManager;
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0)
                {
                    volume.setImageResource(android.R.drawable.ic_lock_silent_mode_off);

                    myAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    // Toast.makeText(getBaseContext(),"Mute",Toast.LENGTH_SHORT).show();

                }
                else
                {

                    volume.setImageResource(android.R.drawable.ic_lock_silent_mode);
                    myAudioManager.setStreamMute(AudioManager.STREAM_MUSIC,true);
                    // Toast.makeText(getBaseContext(),"Not Mute",Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (player1 != null) {
                    if (boolb == false) {
                        player1.pause();
//                    Toast.makeText(getBaseContext(), "Pause", Toast.LENGTH_SHORT).show();
                        boolb = true;
                    } else {
//                    Toast.makeText(getBaseContext(), "play", Toast.LENGTH_SHORT).show();
                            player1.play();
                            boolb = false;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("position", String.valueOf(position));

                    try {
                        if (player1 !=null){
                            if (position< MainActivity.videos.size()-1){
                            Get_Set get_set=MainActivity.videos.get(position);
                            player1.loadVideo(get_set.getContents());
                            tyu=1;
                            boolb=true;
                            processTime.setText(R.string.total_time);
                            seekBar.setProgress(0);
                            resetTime();
                            position=position+1;
                        }else {
                                position=0;
                                player1.loadVideo(MainActivity.videos.get(position).getContents());
                            }
                            play_Video_File=MainActivity.videos.get(position).getCategory();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }


             /*   try {
                    if (player1 != null) {
                        tyu = 1;
                        boolb = true;
//                if (task != null)
//                    task.cancel(true);
                        processTime.setText(R.string.total_time);
                        seekBar.setProgress(0);
                        resetTime();
                if ((player1.getCurrentTimeMillis())/(1000) >= 10) {
                    if (nextPosition == -1) {
                        new RequestTaskPlayer((player1.getCurrentTimeMillis())/(1000), video_id).execute();
                    } else {
                        new RequestTaskPlayer((player1.getCurrentTimeMillis())/(1000), PlayerJson.suggestive_videoId.get(nextPosition)).execute();
                    }
                }

                        nextPosition = nextPosition + 1;
                        if (nextPosition < PlayerJson.suggestiveList.size()) {
                            player1.loadVideo(PlayerJson.suggestiveList.get(nextPosition)
                            .getVideo_id());
                            videoTitle.setText(PlayerJson.suggestiveList.get(nextPosition)
                            .getTitle());
//                    videoDescription.setText(PlayerJson.suggestive_description.get(nextPosition));
                        } else {
                            nextPosition = 0;
                            player1.loadVideo(PlayerJson.suggestiveList.get(nextPosition)
                            .getVideo_id());
                            videoTitle.setText(PlayerJson.suggestiveList.get(nextPosition).getTitle());
//                    videoDescription.setText(PlayerJson.suggestive_description.get(nextPosition));
                        }
                        video_id = PlayerJson.suggestiveList.get(nextPosition).getId();
                    }
                }catch (NullPointerException e){
                    PlayerActivity.this.finish();
                }catch (Exception e){
                    e.printStackTrace();
                }*/
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {
                try {
                    if (player1 != null) {
                        player1.seekToMillis(bar.getProgress());
                        hh = (player1.getCurrentTimeMillis() / (1000 * 60 * 60));
                        sc = ((player1.getCurrentTimeMillis() / (1000)) % 60);
                        mm = ((player1.getCurrentTimeMillis() / (1000 * 60)) % 60);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        /*try {
            int display_mode = getResources().getConfiguration().orientation;

            if (display_mode == Configuration.ORIENTATION_LANDSCAPE) {
//                scrollContent.setVisibility(View.GONE);
                player.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
*/
//        youTubePlayerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (playerControl.getVisibility() == View.VISIBLE){
//                    playerControl.setVisibility(View.GONE);
//                    hhl.removeCallbacks(runnable);
//                }else {
//                    playerControlListner();
//                }
//            }
//        });

    }

   /* public void playerControlListner(){
        playerControl.setVisibility(View.VISIBLE);
        hhl.postDelayed(runnable, 5000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            playerControl.setVisibility(View.GONE);
        }
    };*/
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
//            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//                volume.setImageResource(R.drawable.volume_button);
//            }
//        }
//        return true;
//    }

    /*@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                volume.setImageResource(android.R.drawable.ic_lock_silent_mode_off);
            }
        }
        return super.onKeyUp(keyCode, event);
    }*/

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
//            viewMe();
            scrollContent.setVisibility(View.GONE);
            player.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            setDimensions(player, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }else {
//            getWindow().getDecorView().setSystemUiVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            scrollContent.setVisibility(View.VISIBLE);
            player.getLayoutParams().height = (int) getResources().getDimension(R.dimen.youtubeview_height);
//            setDimensions(player, ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.youtubeview_height));
        }
    }*/

//    private void setDimensions(View view, int width, int height){
//        android.view.ViewGroup.LayoutParams params = view.getLayoutParams();
//        params.width = width;
//        params.height = height;
//        view.setLayoutParams(params);
//    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult result) {
//        Toast.makeText(getApplicationContext(),
//                "onInitializationFailure()",
//                Toast.LENGTH_LONG).show();
//        isYoutubeInstall = false;
//        myalert("You don't have youtube app.\nDownload youtube to play videos.");

    }

    static YouTubePlayer player1;

    public void viewMe() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        player1 = player;


        if (!wasRestored) {

            /*if (sharedPreferences.getString("playervideoidplay", "0").equalsIgnoreCase(video_id)){
                showDialogPlayer();
            }else if(play_Video_File != null) {*/
//                Toast.makeText(this, ""+ videoFileID, Toast.LENGTH_SHORT).show();
                player.loadVideo(play_Video_File);

//            }
//            else
//                player.cuePlaylist(P_ID);

            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {
            playPause.setImageResource(R.drawable.playplayer);
        }

        @Override
        public void onAdStarted() {
           adv = 1;
        }

        @Override
        public void onVideoStarted() {
            adv = 0;
            task = (dotime)new dotime().execute();
            int mm1=0,hh1=0,sc1=0;
            hh1=(player1.getDurationMillis() /(1000*60*60));
            sc1=((player1.getDurationMillis()/(1000))%60);
            mm1=((player1.getDurationMillis()/(1000*60))%60);
            seekBar.setMax(player1.getDurationMillis());
            if(hh1==0)
                if (mm1 < 10 && sc1 < 10) {
                    totalTime.setText("0" + mm1 + ":0" + sc1);
                }else if (mm1 > 10 && sc1 < 10){
                    totalTime.setText("" + mm1 + ":0" + sc1);
                } else if (mm1 < 10 && sc1 > 10){
                    totalTime.setText("0" + mm1 + ":" + sc1);
                }
                else
                    totalTime.setText(""+hh1+":"+mm1+":"+sc1);
        }

        @Override
        public void onVideoEnded() {
            tyu=1;
            boolb = true;
//            task.cancel(true);
            processTime.setText("00:00");
            seekBar.setProgress(0);
            resetTime();
//            if ((player1.getCurrentTimeMillis())/(1000) >= 10) {
//                if (nextPosition == -1) {
//                    new RequestTaskPlayer((player1.getCurrentTimeMillis())/(1000), video_id).execute();
//                } else {
//                    new RequestTaskPlayer((player1.getCurrentTimeMillis())/(1000), PlayerJson.suggestive_videoId.get(nextPosition)).execute();
//                }
//            }

            /*nextPosition = nextPosition + 1;
            if (nextPosition < PlayerJson.suggestiveList.size()) {
                player1.loadVideo(PlayerJson.suggestiveList.get(nextPosition).getVideo_id());
                videoTitle.setText(PlayerJson.suggestiveList.get(nextPosition).getTitle());
//                videoDescription.setText(PlayerJson.suggestive_description.get(nextPosition));
//                youTubePlayerView.initialize(API_KEY, PlayerActivity.this);
            }else {
                nextPosition = 0;
                if (nextPosition < PlayerJson.suggestiveList.size()) {
                    player1.loadVideo(PlayerJson.suggestiveList.get(nextPosition).getVideo_id());
                    videoTitle.setText(PlayerJson.suggestiveList.get(nextPosition).getTitle());
//                    videoDescription.setText(PlayerJson.suggestive_description.get(nextPosition));
//                    youTubePlayerView.initialize(API_KEY, PlayerActivity.this);
                }
            }*/


        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            tyu = 0;
            playPause.setImageResource(R.drawable.pauseplayer);
        }

        @Override
        public void onPaused() {
            tyu = 1;
            playPause.setImageResource(R.drawable.playplayer);
        }

        @Override
        public void onStopped() {
            playPause.setImageResource(R.drawable.playplayer);
            /*if (!ConnectivityReceiver.isConnected()){
                seekTime = player1.getCurrentTimeMillis();
            }*/
        }

        @Override
        public void onBuffering(boolean b) {
            if(b)
                tyu=1;
            else
                tyu=0;
        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    /*@Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        try {
            if (!isConnected) {
//            Toast.makeText(this, "not connected", Toast.LENGTH_SHORT).show();
                if (player1 != null) {
                    seekTime = player1.getCurrentTimeMillis();
                }
            } else {
//            Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
                if (player1 != null) {
                    if (nextPosition == -1) {
                        player1.loadVideo(play_Video_File, seekTime);
                    } else {
                        player1.loadVideo(PlayerJson.suggestiveList.get(nextPosition)
                                .getVideo_id(), seekTime);
                    }
                }
            }
        }catch (Exception e){

        }*/
//    }


    public static int i = 0;
    private class dotime extends AsyncTask<Void,Integer,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
//                if (player1 != null) {
                    for (i = (player1.getCurrentTimeMillis()) / (1000); i < (player1.getDurationMillis()) / (1000); i++) {
//    ty1.setText(""+i);

//                    if(task.isCancelled()) {
//                        break;
//                    }else {
                        try {
                            Thread.sleep(1000);
                            publishProgress(i);

                        } catch (Exception e) {

                        }
//                    }
                    }
//                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);

            if (tyu == 0) {
                if (sc < 60) {
                    sc++;
                } else if (mm < 60) {
                    sc = 1;
                    mm = mm + 1;
                } else {
                    sc = 1;
                    mm = 0;
                    hh++;
                }
                if(hh == 0) {
                    if (mm < 10 && sc < 10)
                        processTime.setText("0" + mm + ":0" + sc);
                    else if (mm < 10 && sc > 9)
                        processTime.setText("0" + mm + ":" + sc);
                    else if (mm > 9 && sc > 9)
                        processTime.setText("" + mm + ":" + sc);
                    else if (mm > 9 && sc < 10)
                        processTime.setText("" + mm + ":0" + sc);
                }
                else
                    processTime.setText("" + hh + ":" + mm + ":" + sc);
                try {
                    if (player1 != null) {
                        seekBar.setProgress(player1.getCurrentTimeMillis());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }

    }

    private void resetTime(){
        sc = 0;
        mm = 0;
        hh = 0;
    }

    private void setTime(int milisec){
        hh=(milisec /(1000*60*60));
        sc=((milisec/(1000))%60);
        mm=((milisec/(1000*60))%60);
    }

    @Override
    protected void onDestroy() {
        try {
            if (task != null)
                task.cancel(true);
            if (player1 != null) {
                player1.pause();
                player1.release();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
//        viewme();
    }

    /*public class RequestTaskPlayer extends AsyncTask<String, String, String> {

        int duration;
        String videoid;
        public RequestTaskPlayer(int duration, String videoid){
            this.duration = duration;
            this.videoid = videoid;
        }

        @Override
        protected String doInBackground(String... uri) {
            String responseString = null;
            try {
                String json = "http://dhinta.com/haryanvidhamaka/API/update_video_viewers.php?action=VideoPlayes&videoId="+ videoid + "&playTime="+duration;
                URL url = new URL(json.replace(" ",  "%20"));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    // Do normal input or output stream reading
                    responseString = "Passed";
                }
                else {
                    responseString = "FAILED"; // See documentation for more info on response handling
                }
            } catch (IOException e) {
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
//            Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
            try {
                if (result.equalsIgnoreCase("Passed")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("duration", 0);
                    editor.apply();
                }
            }catch (Exception e){

            }
        }
    }*/

    /*public void myalert(String message)
    {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this, R.style.AlertDiagCustom);

            // set title
            alertDialogBuilder.setTitle(R.string.app_name);

            // set dialog message
            alertDialogBuilder
                    .setMessage(message)
                    .setCancelable(true)
                    .setIcon(R.mipmap.icon100)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity
                            launchMarket();
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            if (!isFinishing()) {
                // show it
                alertDialog.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    /*boolean isYoutubeInstall = false;

    public void launchMarket() {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.youtube&hl=en");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            isYoutubeInstall = true;
            startActivity(myAppLinkToMarket);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Unable to open play store...Please try again.", Toast.LENGTH_LONG).show();
        }
    }*/
}
