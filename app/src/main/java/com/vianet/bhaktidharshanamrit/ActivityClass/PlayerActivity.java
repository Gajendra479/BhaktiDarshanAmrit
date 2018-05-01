package com.vianet.bhaktidharshanamrit.ActivityClass;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;


public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {

    public static final String API_KEY = "AIzaSyAznLIu7lYME1302tBfSPfsU_HOkqJpcaw";
    public static int tyu;
    public static int i = 0;
    static boolean boolb = true;
    static YouTubePlayer player1;
    public TextView totalTime, processTime;
    public SeekBar seekBar;
    String play_Video_File;

    //----------youtube Player-------------------------------------------------------------------------------------------
    //    String fromLink;
    int position;
    int seekTime = 0;
    int sc = 0, mm = 0, hh = 0;
    dotime task;
    ImageView playPause, next;
    YouTubePlayerView youTubePlayerView;
    Handler hhl;
    int adv = 0;
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
            task = (dotime) new dotime().execute();
            int mm1 = 0, hh1 = 0, sc1 = 0;
            hh1 = (player1.getDurationMillis() / (1000 * 60 * 60));
            sc1 = ((player1.getDurationMillis() / (1000)) % 60);
            mm1 = ((player1.getDurationMillis() / (1000 * 60)) % 60);
            seekBar.setMax(player1.getDurationMillis());
            if (hh1 == 0)
                if (mm1 < 10 && sc1 < 10) {
                    totalTime.setText("0" + mm1 + ":0" + sc1);
                } else if (mm1 > 10 && sc1 < 10) {
                    totalTime.setText("" + mm1 + ":0" + sc1);
                } else if (mm1 < 10 && sc1 > 10) {
                    totalTime.setText("0" + mm1 + ":" + sc1);
                } else
                    totalTime.setText("" + hh1 + ":" + mm1 + ":" + sc1);
        }

        @Override
        public void onVideoEnded() {
            tyu = 1;
            boolb = true;
//            task.cancel(true);
            processTime.setText("00:00");
            seekBar.setProgress(0);
            resetTime();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
//    ProgressBar progress_page;
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
           /* if (!ConnectivityReceiver.isConnected()){
                seekTime = player1.getCurrentTimeMillis();
            }*/
        }

        @Override
        public void onBuffering(boolean b) {
            if (b)
                tyu = 1;
            else
                tyu = 0;
        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setStatusBarColor(Color.BLACK);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 1);      ////////---------#
        play_Video_File = intent.getStringExtra("videoFile");
        getPlayerViews();

    }

    @Override
    public void onBackPressed() {
        try {
            if (task != null)
                task.cancel(true);
            if (player1 != null)
                player1.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        try {
            if (player1 != null) {
                player1.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onStop();
    }

    private void getPlayerViews() {
        totalTime = (TextView) findViewById(R.id.play_time);
        processTime = (TextView) findViewById(R.id.current_time_running);
        seekBar = (SeekBar) findViewById(R.id.video_seekbar);
        playPause = (ImageView) findViewById(R.id.play_video);
        next = (ImageButton) findViewById(R.id.next);
        totalTime.setText("00:00");
        processTime.setText("00:00");
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);
        hhl = new Handler();

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (player1 != null) {
                        if (boolb == false) {
                            player1.pause();
                            boolb = true;
                        } else {
                            player1.play();
                            boolb = false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("position", String.valueOf(position));

                try {
                    if (player1 != null) {
                        if (position < MainActivity.videos.size() - 1) {
                            Get_Set get_set = MainActivity.videos.get(position);
                            player1.loadVideo(get_set.getContents());
                            tyu = 1;
                            boolb = true;
                            processTime.setText(R.string.total_time);
                            seekBar.setProgress(0);
                            resetTime();
                            position = position + 1;
                        } else {
                            position = 0;
                            player1.loadVideo(MainActivity.videos.get(position).getContents());
                        }
                        play_Video_File = MainActivity.videos.get(position).getCategory();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult result) {

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        player1 = player;

        if (!wasRestored) {

            player.loadVideo(play_Video_File);

            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    private void resetTime() {
        sc = 0;
        mm = 0;
        hh = 0;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }

    private class dotime extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                for (i = (player1.getCurrentTimeMillis()) / (1000); i < (player1.getDurationMillis()) / (1000); i++) {

                    try {
                        Thread.sleep(1000);
                        publishProgress(i);

                    } catch (Exception e) {

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {


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
                if (hh == 0) {
                    if (mm < 10 && sc < 10)
                        processTime.setText("0" + mm + ":0" + sc);
                    else if (mm < 10 && sc > 9)
                        processTime.setText("0" + mm + ":" + sc);
                    else if (mm > 9 && sc > 9)
                        processTime.setText("" + mm + ":" + sc);
                    else if (mm > 9 && sc < 10)
                        processTime.setText("" + mm + ":0" + sc);
                } else
                    processTime.setText("" + hh + ":" + mm + ":" + sc);
                try {
                    if (player1 != null) {
                        seekBar.setProgress(player1.getCurrentTimeMillis());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

}
