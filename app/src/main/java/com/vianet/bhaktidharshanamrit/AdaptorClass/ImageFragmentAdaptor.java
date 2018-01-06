package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

/**
 * Created by editing2 on 18-Dec-17.
 */
public class ImageFragmentAdaptor extends RecyclerView.Adapter<ImageFragmentAdaptor.MyViewHolder>{
    private Context context;
    private ArrayList<Get_Set> imagelist;
    private String path="http://162.144.68.182/ambey/Thumbnails/";

    public ImageFragmentAdaptor(Context context , ArrayList<Get_Set> list){
        this.context=context;
        this.imagelist=list;

    }
    @Override
    public ImageFragmentAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.image_frag_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageFragmentAdaptor.MyViewHolder holder, int position) {
        Get_Set get_set=imagelist.get(position);

        Glide.with(context).load(path+get_set.getThumb()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.image_frag_imageview);

        }
    }
}
