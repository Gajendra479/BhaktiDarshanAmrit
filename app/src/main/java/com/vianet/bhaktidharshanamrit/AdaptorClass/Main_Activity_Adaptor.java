package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

/**
 * Created by editing2 on 18-Dec-17.
 */

public class Main_Activity_Adaptor extends RecyclerView.Adapter<Main_Activity_Adaptor.MyViewHolder> {
    private Context context;
    private ArrayList<Get_Set> main_list;
    private String path="http://162.144.68.182/ambey/Thumbnails/";

    public Main_Activity_Adaptor(Context context,ArrayList<Get_Set> list){
        this.context=context;
        this.main_list=list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.main_act_card_image);
        }
    }

    @Override
    public Main_Activity_Adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_activity_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Main_Activity_Adaptor.MyViewHolder holder, int position) {
        Get_Set get_set=main_list.get(position);
        Glide.with(context).load(path+get_set.getThumb()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return main_list.size();
    }


}
