/*
package com.vianet.bhaktidharshanamrit.AdaptorClass;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.vianet.bhaktidharshanamrit.FragmentClass.ImageFragment;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

*/
/**
 * Created by editing2 on 18-Dec-17.
 *//*

public class ImageFragmentAdaptor extends RecyclerView.Adapter<ImageFragmentAdaptor.MyViewHolder>{
    public Context context;
    Get_Set get_set=null;
    private ArrayList<Get_Set> imagelist;
    private String path="http://162.144.68.182/ambey/Thumbnails/";
    ImageFragment fragment;
    private LayoutInflater inflater;
    private DownloadManager downloadManager;

    public ImageFragmentAdaptor(Context context, ArrayList<Get_Set> list, ImageFragment fragment){
        this.context=context;
        this.fragment=fragment;
        this.imagelist=list;
        inflater=LayoutInflater.from(context);
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }
    View view = null;


    @Override
    public ImageFragmentAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    view= inflater.inflate(R.layout.image_frag_design,parent,false);
    MyViewHolder holder=new MyViewHolder(view);
    return holder;
    }

    @Override
    public void onBindViewHolder(final ImageFragmentAdaptor.MyViewHolder holder, int position) {
         get_set=imagelist.get(position);

        Glide.with(context).load(path+get_set.getThumb()).placeholder(R.drawable.bhakti_sangam_splas).into(holder.imageView);

        holder.download.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {

                    downloadImage();

                }else {
                    if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                        Toast.makeText(context, "permission required", Toast.LENGTH_SHORT).show();
                    }
                }
                fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

            }
        });



        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Please wait...", Toast.LENGTH_SHORT).show();
                try {

                    Glide
                            .with(context)
                            .load(path+get_set.getThumb())
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(400,720) {
                                @Override
                                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                    super.onLoadFailed(e, errorDrawable);
//
                                    Toast.makeText(context, "Check your Internet Connectivity", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                    convertToFile(resource);
                                }

                            });

                }catch (Exception error){

                }
            }
        });
    }
    public void downloadImage(){

        Uri download_Uri = Uri.parse(path + get_set.getThumb());
        DownloadManager.Request request = new DownloadManager.Request(download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(" Downloading " + "Sample" + ".png");
        request.setDescription("Downloading " + "Sample" + ".png");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,get_set.getThumb().substring(get_set.getThumb().lastIndexOf("/")+1));
        downloadManager.enqueue(request);

    }

    public  void convertToFile(Bitmap myBitmap){

        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            myBitmap.compress(Bitmap.CompressFormat.PNG, 60, out);
            out.close();
            share(Uri.fromFile(file));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void share (Uri bmpUri){

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        context.startActivity(Intent.createChooser(shareIntent, "Share image using"));

    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView share;
        ImageView download;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.image_frag_imageview);
            share= (ImageView) itemView.findViewById(R.id.image_frag_share);
            download= (ImageView) itemView.findViewById(R.id.downloadImage);

        }
    }
}
*/
