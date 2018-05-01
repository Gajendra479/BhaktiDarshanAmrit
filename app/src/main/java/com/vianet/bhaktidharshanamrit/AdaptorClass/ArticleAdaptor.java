package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

/**
 * Created by editing2 on 24-Jan-18.
 */

public class ArticleAdaptor extends RecyclerView.Adapter<ArticleAdaptor.MyViewHolder> {
    Context context;
    ArrayList<Get_Set> list;

    public ArticleAdaptor(Context context, ArrayList<Get_Set> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_article_design,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Get_Set get_set=list.get(position);
        Glide.with(context).load("http://bhaktidarshan.in/manage_panel/"+get_set.getThumb()).into(holder.imageView);
        holder.textView.setText(get_set.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.articleImage);
            textView= (TextView) itemView.findViewById(R.id.articleText);
        }
    }
}
