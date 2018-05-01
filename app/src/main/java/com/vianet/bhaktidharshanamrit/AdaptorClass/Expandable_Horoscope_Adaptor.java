package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by editing2 on 13-Jan-18.
 */

public class Expandable_Horoscope_Adaptor extends BaseExpandableListAdapter {
    Context context;
    List<String> listDataHeader;

    HashMap<String,List<String>> listDataChild;

    public Expandable_Horoscope_Adaptor(Context context, List<String> listDataHeader, HashMap<String, List<String>> listDataChild){
        this.context=context;
        this.listDataHeader=listDataHeader;
        this.listDataChild=listDataChild;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
//        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).size();
//        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
//        return 1;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
//        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String header_title= (String) getGroup(groupPosition);


        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.main_activity_design,null);
        }

        ImageView imageView= (ImageView) convertView.findViewById(R.id.main_act_card_image);
        Glide.with(context).load("http://162.144.68.182/ambey/"+header_title).placeholder(R.drawable.imgdef)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

//         final String childTitle= (String) getChild(groupPosition, childPosition);

        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.horoscope_child,null);
        }
        TextView childText = (TextView) convertView.findViewById(R.id.horoscope_text);

        childText.setText(Html.fromHtml((String) getChild(groupPosition, childPosition)));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
