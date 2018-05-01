package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

/**
 * Created by digisoft on 4/18/2017.
 */

public class Adapter_god_audio extends RecyclerView.Adapter<Adapter_god_audio.MyViewHolder> {

    ArrayList<Get_Set> guru_audios;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.videoFragImageid);
            title = (TextView) view.findViewById(R.id.videoFragTextid);
        }
    }


    public Adapter_god_audio(Context context, ArrayList<Get_Set> guru_audios) {
        mContext = context;
        this.guru_audios = guru_audios;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_frag_design, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Get_Set singer_audio = guru_audios.get(position);

        if(!TextUtils.isEmpty(singer_audio.getTitle())){
            holder.title.setText(singer_audio.getTitle());
        }


        if(TextUtils.isEmpty(singer_audio.getThumb()))
        {
            holder.thumbnail.setImageResource(R.drawable.headphones);
        }
        else
        {
            try {
                Glide.with(mContext).load("http://162.144.68.182/ambey/Thumbnails/" + singer_audio.getThumb())
                        .into(holder.thumbnail);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    @Override
    public int getItemCount() {
        return guru_audios.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

