package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

/**
 * Created by editing2 on 20-Dec-17.
 */
public class TextFragmentAdaptor extends RecyclerView.Adapter<TextFragmentAdaptor.MyViewHolder> {
    private Context context;
    private ArrayList<Get_Set> textList;

    public TextFragmentAdaptor(Context context, ArrayList<Get_Set> list){
        this.context=context;
        this.textList=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.text_frag_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Get_Set get_set=textList.get(position);
        holder.textView.setText(get_set.getTitle());

    }

    @Override
    public int getItemCount() {
        return textList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.text_frag_textview);
        }
    }
}
