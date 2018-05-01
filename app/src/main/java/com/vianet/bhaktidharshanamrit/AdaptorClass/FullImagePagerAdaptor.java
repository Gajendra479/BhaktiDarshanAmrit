/*
package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

*/
/**
 * Created by editing2 on 18-Dec-17.
 *//*



public class FullImagePagerAdaptor extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Get_Set> image;
    private String path="http://162.144.68.182/ambey/Thumbnails/";

    public FullImagePagerAdaptor(Context context, ArrayList<Get_Set> list){
        this.context=context;
        this.image=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView=inflater.inflate(R.layout.full_image_view,container,false);
        Get_Set get_set=image.get(position);
        ImageView imageViewFull= (ImageView) itemView.findViewById(R.id.fullImageViewImage);
        container.addView(itemView);
        Glide.with(context).load(path+get_set.getThumb()).into(imageViewFull);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
*/
