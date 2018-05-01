//package com.vianet.bhaktidharshanamrit.ActivityClass;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.graphics.Typeface;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Bundle;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Html;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerView;
//import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
//import com.vianet.bhaktidharshanamrit.MainActivity;
//import com.vianet.bhaktidharshanamrit.R;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.net.URLEncoder;
//import java.util.ArrayList;
//
//
//public class VideoPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
//
//    ImageView play_video, pause_video;
//    SeekBar video_seekbar;
//    TextView play_time, current_time_running;
//    String formattedTime;
//
//
//
//    int selection = -1;
//
//     int pause_time, total_time, current_time;
//    int RECOVERY = 1221;
//    String VIDEO_ID = "";
//
//    private YouTubePlayerView youtube_player;
//    private YouTubePlayer mPlayer;
//     String API_KEY = "AIzaSyB4pA2i3Qj8VbrzJ9x7OgvjNN5ZrRPNGg8";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_player);
//
//        try {
//
//
//
//            Intent intent = getIntent();
//            VIDEO_ID = intent.getExtras().getString("id");
//            selection = intent.getExtras().getInt("position");
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));
//            }
//
//            play_video = (ImageView) findViewById(R.id.play_video);
////            pause_video = (ImageView) findViewById(R.id.pause_video);
//            video_seekbar = (SeekBar) findViewById(R.id.video_seekbar);
//            play_time = (TextView) findViewById(R.id.play_time);
//            current_time_running = (TextView) findViewById(R.id.current_time_running);
//            youtube_player = (YouTubePlayerView) findViewById(R.id.youtube_player);
//
//            video_seekbar.setMax(99);
//
////        Log.w("video_url", VIDEO_ID);
//
//            video_seekbar.setOnSeekBarChangeListener(mVideoSeekBarChangeListener);
//
//
//            play_video.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != mPlayer && !mPlayer.isPlaying()) {
//                        play_video.setVisibility(View.GONE);
//                        pause_video.setVisibility(View.VISIBLE);
//                        mPlayer.play();
//                    }
//
//                }
//            });
//
//            pause_video.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != mPlayer && mPlayer.isPlaying()) {
//                        play_video.setVisibility(View.VISIBLE);
//                        pause_video.setVisibility(View.GONE);
//                        mPlayer.pause();
//                    }
//                }
//            });
//
//            youtube_player.initialize(API_KEY, this);
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean complete) {
//        try {
//        mPlayer = youTubePlayer;
//        if (!complete) {
//
//                youTubePlayer.loadVideo(VIDEO_ID);
//
//        }
//
//        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
//        youTubePlayer.setPlayerStateChangeListener(mPlayerStateChangeListener);
//        youTubePlayer.setPlaybackEventListener(mPlaybackEventListener);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
////        Toast.makeText(this, "yyyyyyyyyyyyyy", Toast.LENGTH_SHORT).show();
//    }
//
//
//    YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
//        @Override
//        public void onPlaying() {
////            displaytotalDuration();
////            updateseekbar();
//            try {
//                play_video.setVisibility(View.GONE);
//                pause_video.setVisibility(View.VISIBLE);
////                Log.w("onPlaying", "hii");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onPaused() {
//
//            try {
////                Log.w("onpause", "hii");
//                play_video.setVisibility(View.VISIBLE);
//                pause_video.setVisibility(View.GONE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onStopped() {
////            Log.w("onStopped", "hii");
//        }
//
//        @Override
//        public void onBuffering(boolean b) {
////            Toast.makeText(getApplicationContext(),"onBuffering",Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onSeekTo(int i) {
//
//        }
//    };
//
//    private void displaytotalDuration() {
//
//        if (null == mPlayer) return;
////        current_time = mPlayer.getCurrentTimeMillis();
//        formattedTime = formatTime(mPlayer.getDurationMillis());
////        Log.w("formattedTime", formattedTime);
//        play_time.setText(formattedTime);
////        Log.w("CurrentTime", String.valueOf(mPlayer.getCurrentTimeMillis()));
//    }
//
//
//    YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
//        @Override
//        public void onLoading() {
////            Toast.makeText(getApplicationContext(),"onLoading",Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onLoaded(String s) {
////            Toast.makeText(getApplicationContext(),"onLoaded",Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onAdStarted() {
////            Toast.makeText(getApplicationContext(),"onAdStarted",Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onVideoStarted() {
//
//
//            try {
////                Log.w("maxvalu", String.valueOf(mPlayer.getDurationMillis()));
//                total_time = mPlayer.getDurationMillis();
////                Log.w("total_time", String.valueOf(total_time));
////            displayCurrentTime_running();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onVideoEnded() {
//
//
//            try {
//                play_video.setVisibility(View.VISIBLE);
//                pause_video.setVisibility(View.GONE);
//
//                pause_time = mPlayer.getCurrentTimeMillis();
//                if (selection == -1) {
//                    Get_Set guru_video = MainActivity.videos.get(0);
//                    mPlayer.loadVideo(guru_video.getContents());
//                }
//                if (selection < MainActivity.videos.size() - 1) {
//                    Get_Set guru_video = MainActivity.videos.get(selection + 1);
//                    mPlayer.loadVideo(guru_video.getContents());
//                } else {
//                    Get_Set guru_video = MainActivity.videos.get(0);
//                    mPlayer.loadVideo(guru_video.getContents());
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onError(YouTubePlayer.ErrorReason errorReason) {
//
//        }
//    };
//
//    private void displayCurrentTime_running() {
//
//        if (mPlayer != null) {
//            try {
//                String currentTime = formatTime(mPlayer.getCurrentTimeMillis());
//                current_time_running.setText(currentTime);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
////        if (mPlayer != null) {        // threrad for updating current  time continiously after 1 sec
////            Runnable current_time = new Runnable() {
////                public void run() {
////                    //displayCurrentTime_running();
////                }
////            };
////            handler_current_time.postDelayed(current_time, 1000);
////        }
//    }
//
//    private void updateseekbar() {
//        if (mPlayer != null) {
////            Log.w("seekbar_update", "hii" + mPlayer.getCurrentTimeMillis());
//            video_seekbar.setProgress((int) (((float) mPlayer.getCurrentTimeMillis() / mPlayer.getDurationMillis()) * 100));
//        }
//
////        if (mPlayer != null && mPlayer.isPlaying()) {
////            runnable = new Runnable() {
////                public void run() {
////                    updateseekbar();
////                }
////            };
////            handler.postDelayed(runnable, 1000);
////        }
//    }
//
//    SeekBar.OnSeekBarChangeListener mVideoSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//
//            try {
//                if (mPlayer != null) {
//                    int totalDuration = mPlayer.getDurationMillis();
//                    int currentPosition = progressToTimer(seekBar.getProgress(), totalDuration);
//                    mPlayer.seekToMillis(currentPosition);
////                    updateseekbar();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    };
//
//    private void displayCurrentTime() {
//        if (null == mPlayer) return;
//        current_time = mPlayer.getCurrentTimeMillis();
//        formattedTime = formatTime(mPlayer.getDurationMillis() - mPlayer.getCurrentTimeMillis());
////        Log.w("formattedTime", formattedTime);
//        play_time.setText(formattedTime);
////        Log.w("CurrentTime", String.valueOf(mPlayer.getCurrentTimeMillis()));
//    }
//
//    private String formatTime(int millis) {
//        try {
//            int seconds = millis / 1000;
//            int minutes = seconds / 60;
//            int hours = minutes / 60;
//            return (hours == 0 ? "" : hours + ":") + String.format("%02d:%02d", minutes % 60, seconds % 60);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "00";
//    }
//
////    private Runnable runnable = new Runnable() {
////        @Override
////        public void run() {
////            displayCurrentTime();
////            mHandler.postDelayed(this, 100);
////        }
////    };
//
//    @Override
//    public void onBackPressed() {
//
//        try {
//            if(mPlayer.isPlaying())
//            {
//                mPlayer.pause();
//                mPlayer.release();
//                mPlayer = null;
//                finish();
//            }
//                    else
//            {
//                mPlayer.release();
//                mPlayer = null;
//                finish();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        try {
//            if (requestCode == RECOVERY) {
//                getYouTubePlayerProvider().initialize(API_KEY, this);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
//        return youtube_player;
//    }
//
//    @Override
//    protected void onPause() {
//        try {
//
//            if (mPlayer != null) {
//                pause_time = mPlayer.getCurrentTimeMillis();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//
//        super.onResume();
//        try {
//            if (null != mPlayer) {
//                play_video.setVisibility(View.GONE);
//                pause_video.setVisibility(View.VISIBLE);
//                mPlayer.loadVideo(VIDEO_ID, pause_time);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//   /* private void playagain(boolean isConnected) {
//
//        try {
//
//            if (!isConnected) {
//                pause_time = mPlayer.getCurrentTimeMillis();
//            } else {
//                play_video.setVisibility(View.GONE);
//                pause_video.setVisibility(View.VISIBLE);
//                mPlayer.loadVideo(VIDEO_ID, pause_time);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }*/
//
//    public int progressToTimer(int progress, int totalDuration) {
//        int currentDuration = 0;
//        try {
//            currentDuration = 0;
//            totalDuration = (int) (totalDuration / 1000);
//            currentDuration = (int) ((((double) progress) / 100) * totalDuration);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // return current duration in milliseconds
//        return currentDuration * 1000;
//    }
//
////    @Override
////    public void onDestroy() {
////        super.onDestroy();
////        try {
////            if (mPlayer != null) {
////                mPlayer.release();
////                mPlayer = null;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////
////    }
///*
//
//    private boolean isLastItemDisplaying(RecyclerView recyclerView1) {
//        if (recyclerView1.getAdapter().getItemCount() != 0) {
//            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView1.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView1.getAdapter().getItemCount() - 1)
//                return true;
//        }
//        return false;
//    }*/
//
//
//   /* @Override
//    protected void onStop() {
//        try {
//
//            if (pause_time > 10000) {
//
////                videoDurationpost();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.onStop();
//    }*/
//}
