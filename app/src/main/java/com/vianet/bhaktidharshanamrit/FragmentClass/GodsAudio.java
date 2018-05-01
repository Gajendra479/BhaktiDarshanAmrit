package com.vianet.bhaktidharshanamrit.FragmentClass;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vianet.bhaktidharshanamrit.ActivityClass.ContentActivity;
import com.vianet.bhaktidharshanamrit.AdaptorClass.Adapter_god_audio;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GodsAudio extends Fragment implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, SeekBar.OnSeekBarChangeListener,
        ContentActivity.Communication_frag {

    public Boolean mCancel = false;
    RecyclerView recycler_view_singers_home;
    RecyclerView.LayoutManager mLayoutManager;
    MediaPlayer musicplayer;
    ProgressBar pb;
    ImageView img_backward, img_play, img_pause, img_forward;
    TextView txt_current_time, txt_music_duration, txt_song;
    SeekBar seek_bar;
    Runnable runnable;
    LinearLayout lin_player;
    TextView massage;
    Handler handler = new Handler();
    int r_position;
    String mp3;
    Handler handler_current = new Handler();
    String path = "http://162.144.68.182/ambey/Thumbnails/";
    private boolean aboolien = true;
    private boolean scroll_down;

    public GodsAudio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_singers_audio, container, false);

        massage = (TextView) view.findViewById(R.id.messageTextFrag);


        pb = (ProgressBar) view.findViewById(R.id.pb);

        lin_player = (LinearLayout) view.findViewById(R.id.lin_player);

        img_backward = (ImageView) view.findViewById(R.id.img_backward);
        img_forward = (ImageView) view.findViewById(R.id.img_forward);
        img_play = (ImageView) view.findViewById(R.id.img_play);
        img_pause = (ImageView) view.findViewById(R.id.img_pause);
        seek_bar = (SeekBar) view.findViewById(R.id.seek_bar);
        seek_bar.setMax(99);

        txt_current_time = (TextView) view.findViewById(R.id.txt_current_time);
        txt_music_duration = (TextView) view.findViewById(R.id.txt_music_duration);
        txt_song = (TextView) view.findViewById(R.id.txt_song);
        recycler_view_singers_home = (RecyclerView) view.findViewById(R.id.recycler_view_singers_home);
        Adapter_god_audio adapter_god_audio = new Adapter_god_audio(getActivity(), MainActivity.audio);

        recycler_view_singers_home.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler_view_singers_home.setLayoutManager(mLayoutManager);
//        recycler_view_singers_home.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recycler_view_singers_home.setItemAnimator(new DefaultItemAnimator());

        if (MainActivity.audio != null && MainActivity.audio.size() > 0) {
            massage.setVisibility(View.GONE);
            recycler_view_singers_home.setVisibility(View.VISIBLE);
            recycler_view_singers_home.setAdapter(adapter_god_audio);
        } else {
            massage.setVisibility(View.VISIBLE);
            recycler_view_singers_home.setVisibility(View.GONE);
        }

        recycler_view_singers_home.addOnItemTouchListener(new Adapter_god_audio.RecyclerTouchListener(getActivity(), recycler_view_singers_home, new Adapter_god_audio.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Get_Set god_audio = MainActivity.audio.get(position);

                r_position = position;
//                Log.w("r_position", String.valueOf(r_position));
                mp3 = path + god_audio.getContents();

                ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null) {
                    if (networkInfo.isConnected()) {

                        if (musicplayer != null) {
                            musicplayer.stop();
                            musicplayer.reset();
                            musicplayer.release();
                            musicplayer = new MediaPlayer();
                            musicplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                            musicplayer.setOnPreparedListener(GodsAudio.this);   // prepare listner for playing audio

                            musicplayer.setOnErrorListener(GodsAudio.this);
                            seek_bar.setOnSeekBarChangeListener(GodsAudio.this);
                            try {
                                musicplayer.setDataSource(mp3);
                                mCancel = false;
                                lin_player.setVisibility(View.VISIBLE);
                                img_pause.setVisibility(View.GONE);
                                img_play.setVisibility(View.GONE);
                                pb.setVisibility(View.VISIBLE);
                                txt_song.setText(god_audio.getTitle());
                                musicplayer.prepareAsync();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
//                    Log.w("hii", "second");
                            musicplayer = new MediaPlayer();
                            musicplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                            musicplayer.setOnPreparedListener(GodsAudio.this);   // prepare listner for playing audio

                            musicplayer.setOnErrorListener(GodsAudio.this);
                            seek_bar.setOnSeekBarChangeListener(GodsAudio.this);
                            try {
                                musicplayer.setDataSource(mp3);

                                img_pause.setVisibility(View.GONE);
                                img_play.setVisibility(View.GONE);
                                txt_song.setText(god_audio.getTitle());

                                pb.setVisibility(View.VISIBLE);
                                musicplayer.prepareAsync();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Check Your Internt Connectivity", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Check Your Internt Connectivity", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        img_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (r_position > 0) {     // checking cuurent aiudio positio, if it is greater then zero then afetr getting prevoius position play previous song


                    if (musicplayer != null) {
                        musicplayer.stop();
                        musicplayer.reset();
                        try {
                            Get_Set god_audio = MainActivity.audio.get(r_position - 1);
                            musicplayer.setDataSource(path + god_audio.getContents());
                            txt_song.setText(god_audio.getTitle());
//                            Log.w("backward", god_audio.getAudio_file() +" "+(r_position-1));
                            r_position = r_position - 1;
                            musicplayer.prepareAsync();
                        } catch (Exception e) {

                        }
                    }
                } else {   // else if current song is first song or (zero position) then jhump to the last position song by getting last position of array
                    Get_Set god_audio = MainActivity.audio.get(MainActivity.audio.size() - 1);
//                    Log.w("last_song", String.valueOf( MainActivity.audio.size()-1));
                    try {
                        musicplayer.stop();
                        musicplayer.reset();
                        r_position = MainActivity.audio.size() - 1;
                        musicplayer.setDataSource(path + god_audio.getContents());
                        txt_song.setText(god_audio.getTitle());
//                        Log.w("again_first",god_audio.getContents());
                        musicplayer.prepareAsync();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        img_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"hii",Toast.LENGTH_LONG).show();
                pb.setVisibility(View.VISIBLE);
                img_play.setVisibility(View.GONE);
                img_pause.setVisibility(View.GONE);

//                Log.w("r_position", String.valueOf(r_position));
                if (r_position < MainActivity.audio.size() - 1) {
//                    Log.w("r_position_if", String.valueOf(r_position));
                    if (musicplayer != null) {

                        musicplayer.stop();
                        musicplayer.reset();
                        try {
                            Get_Set god_audio = MainActivity.audio.get(r_position + 1);
                            musicplayer.setDataSource(path + god_audio.getContents());
                            txt_song.setText(god_audio.getTitle());
//                            Log.w("forward",god_audio +" "+(r_position+1));
                            r_position = r_position + 1;
                            musicplayer.prepareAsync();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Get_Set guru_audio = MainActivity.audio.get(0);
                    try {
                        musicplayer.stop();
                        musicplayer.reset();
                        r_position = 0;
                        musicplayer.setDataSource(path + guru_audio.getContents());
                        txt_song.setText(guru_audio.getTitle());
//                        Log.w("again_first",guru_audio.getContents());
                        musicplayer.prepareAsync();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        img_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicplayer != null && musicplayer.isPlaying()) {
                    musicplayer.pause();
                    img_pause.setVisibility(View.GONE);
                    img_play.setVisibility(View.VISIBLE);
                }
            }
        });


        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicplayer != null && !musicplayer.isPlaying()) {
                    musicplayer.start();
                    img_pause.setVisibility(View.VISIBLE);
                    img_play.setVisibility(View.GONE);

                    displayDuration();
                    displayCurrrentDuration();
                    primarySeekBarProgressUpdater();
                }

            }
        });

        ((ContentActivity) getActivity()).setListener(this);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void primarySeekBarProgressUpdater() {
//        Log.w("media_length", String.valueOf(musicplayer.getDuration()));
        if (musicplayer != null) {
            try {
                seek_bar.setProgress((int) (((float) musicplayer.getCurrentPosition() / musicplayer.getDuration()) * 100));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // This math construction give a percentage of "was playing"/"song length"
        if (musicplayer != null) {
            runnable = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }


    @Override
    public void onPrepared(MediaPlayer mp) {

        if (!mCancel) {
//            Toast.makeText(getContext(), "C", Toast.LENGTH_SHORT).show();

//            Log.w("onPrepared", String.valueOf(r_position));
            musicplayer.start();

            musicplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

//                    Log.w("onCompletion", "hello_onCompletion");
                    if (musicplayer != null) {

                        musicplayer.reset();

                        if (r_position < MainActivity.audio.size() - 1) {

                            Get_Set guru_audio = MainActivity.audio.get(r_position + 1);

                            try {
                                musicplayer.setDataSource(path + guru_audio.getContents());
                                txt_song.setText(guru_audio.getTitle());
                                musicplayer.prepareAsync();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else { // start first song again
                            Get_Set guru_audio = MainActivity.audio.get(0);
                            try {
                                musicplayer.setDataSource(path + guru_audio.getContents());
                                txt_song.setText(guru_audio.getTitle());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            musicplayer.prepareAsync();
                        }
                    }
                }
            });

            try {
                displayDuration();
                displayCurrrentDuration();
                primarySeekBarProgressUpdater();


            } catch (Exception e) {
                e.printStackTrace();
            }
            lin_player.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
            img_pause.setVisibility(View.VISIBLE);
            img_play.setVisibility(View.GONE);
        } else {
//            Toast.makeText(getContext(), "D", Toast.LENGTH_SHORT).show();
            musicplayer.reset();
            mCancel = false;
        }
    }

    private void displayCurrrentDuration() {

        if (musicplayer != null) {
            try {
                String currentTime = formatTime(musicplayer.getCurrentPosition());
                txt_current_time.setText(currentTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (musicplayer != null) {        // threrad for updating current  time continiously after 1 sec
            Runnable current_time = new Runnable() {
                public void run() {
                    displayCurrrentDuration();
                }
            };
            handler_current.postDelayed(current_time, 1000);
        }

    }

    private void displayDuration() {
        String formattedTime = formatTime(musicplayer.getDuration());
        txt_music_duration.setText(formattedTime);       // display the total duration of audio file
    }

    private String formatTime(int millis) {     // method for converting milliseconds in minute:second formate
        int seconds = millis / 1000;
        int minutes = seconds / 60;
//        int hours = minutes / 60;  hours == 0 ? "--:" : hours + ":")

        return String.format("%02d:%02d", minutes % 60, seconds % 60);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        int totalDuration = musicplayer.getDuration();
        int currentPosition = progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        musicplayer.seekTo(currentPosition);

        // update timer progress again
        primarySeekBarProgressUpdater();
    }


    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }


    @Override
    public void onPause() {
        super.onPause();

        if (musicplayer != null) {
            musicplayer.pause();

            musicplayer.reset();
            lin_player.setVisibility(View.GONE);

        }
        mCancel = true;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }


    @Override
    public void onDetach() {

//        edit.putBoolean("my_first_time_god_audio", true).apply();  //  downloading again for another guru
        super.onDetach();


        if (musicplayer != null) {
//            Log.w("check1", "onDetach");
//    handler.removeCallbacksAndMessages(runnable);
            musicplayer.stop();
//            Log.w("check2", "onDetach");
            musicplayer.reset();
//            Log.w("check3", "onDetach");
            musicplayer.release();
//            Log.w("check4", "onDetach");
            musicplayer = null;
//            Log.w("check4", "onDetach");

        }
    }

    @Override
    public void Frag_Position(int position) {
        if (musicplayer != null && musicplayer.isPlaying()) {
//            Toast.makeText(getContext(), "A", Toast.LENGTH_SHORT).show();
            musicplayer.stop();
            musicplayer.reset();
            lin_player.setVisibility(View.GONE);
        } else {
            mCancel = true;
//            Toast.makeText(getContext(), "B", Toast.LENGTH_SHORT).show();
            lin_player.setVisibility(View.GONE);
        }
    }

}