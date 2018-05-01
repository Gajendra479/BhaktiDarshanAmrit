package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.FragmentClass.Biography_Card;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

/**
 * Created by editing2 on 17-Jan-18.
 */

public class Biography_adaptor extends RecyclerView.Adapter<Biography_adaptor.MyViewHolder> implements Biography_Card.SetWebBack{
    Context context;
    ArrayList<Get_Set> list;
    ArrayList<Get_Set> filterList;
    private FragmentManager fragmentManager;
    private SetBackWebBio setBackWebBio;

    public Biography_adaptor(Context context, ArrayList<Get_Set> list, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.list = list;
        this.filterList = list;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.biography_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Get_Set get_set = list.get(position);
        Glide.with(context).load("http://162.144.68.182/ambey/Thumbnails/" + get_set.getThumb()).placeholder(R.drawable.imgdef).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Biography_Card biography_card = new Biography_Card();
                Bundle bundle = new Bundle();
                bundle.putString("thumb", list.get(position).getBanner());
                bundle.putString("desc", list.get(position).getDescription());
                bundle.putString("name",list.get(position).getTitle());

                biography_card.setArguments(bundle);
                FragmentTransaction fm = fragmentManager.beginTransaction();
                biography_card.setInterface(Biography_adaptor.this);
                fm.replace(R.id.cont, biography_card, "Bio_card");
                fm.addToBackStack(null);
                fm.commit();

            }
        });
    }

    @Override
    public void setWebView(WebView view, ImageView imageView, FloatingActionButton floatingActionButton) {
        if (setBackWebBio != null) {
            setBackWebBio.onSetBackWebBio(view,imageView,floatingActionButton);
        }
    }

    public interface SetBackWebBio{
        void onSetBackWebBio(WebView webView, ImageView imageView, FloatingActionButton floatingActionButton);

    }

    public void setInterface1(SetBackWebBio setBackWebBio){
        this.setBackWebBio = setBackWebBio;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.bio_recy_image);
        }
    }

    public void setFilter(ArrayList<Get_Set> newlist) {
        list = new ArrayList<>();
        list.addAll(newlist);
        notifyDataSetChanged();
    }
}