package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.ActivityClass.PlayerActivity;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

/**
 * Created by editing2 on 27-Dec-17.
 */

public class VideoFragAdaptor extends RecyclerView.Adapter<VideoFragAdaptor.MyViewHolder> {

    Context context;
    ArrayList<Get_Set> videolist;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public VideoFragAdaptor(Context context, ArrayList<Get_Set> list) {
        this.context = context;
        this.videolist = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_frag_design, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Get_Set get_set = videolist.get(position);
        holder.title.setText(get_set.getTitle());

        Glide.with(context).load("https://img.youtube.com/vi/" + get_set.getContents() + "/mqdefault.jpg").into(holder.thumb);

        holder.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null) {
                    if (networkInfo.isConnected()) {

                        Intent intent = new Intent(context, PlayerActivity.class);
                        intent.putExtra("videoFile", MainActivity.videos.get(position).getContents());
                        intent.putExtra("position", position);
                        context.startActivity(intent);
                    } else
                        Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return videolist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumb;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.videoFragTextid);
            thumb = itemView.findViewById(R.id.videoFragImageid);
        }
    }
}
