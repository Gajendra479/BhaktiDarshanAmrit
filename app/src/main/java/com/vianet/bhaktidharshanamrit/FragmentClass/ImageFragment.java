package com.vianet.bhaktidharshanamrit.FragmentClass;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class ImageFragment extends Fragment {
    public static final int DOWNLOAD = 1;
    Get_Set get_set = null;
    private RecyclerView recyclerView;
    private ImageFragmentAdaptor imageFragmentAdaptor;
    private DownloadManager downloadManager;
    private TextView massage;

    public ImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.VideoFragrecyclerView);
        massage = (TextView) view.findViewById(R.id.messageTextFrag);


        imageFragmentAdaptor = new ImageFragmentAdaptor(getContext(), MainActivity.images, ImageFragment.this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        if (MainActivity.images != null && MainActivity.images.size() > 0) {
            massage.setVisibility(View.GONE);
            recyclerView.setAdapter(imageFragmentAdaptor);
        }

        return view;

    }

    public void downloadImage() {


    /*    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };*/

        try {
            Toast.makeText(getContext(), "Downloading..", Toast.LENGTH_SHORT).show();
            Uri download_Uri = Uri.parse(getString(R.string.path) + get_set.getThumb());
            DownloadManager.Request request = new DownloadManager.Request(download_Uri);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setAllowedOverRoaming(false);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(get_set.getThumb());
            request.setVisibleInDownloadsUi(true);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, get_set.getThumb().substring(get_set.getThumb().lastIndexOf("/") + 1));
            downloadManager.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadImage();
        } else {
            Toast.makeText(getContext(), "Download incomplete.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            downloadImage();
        }

    }

    public class ImageFragmentAdaptor extends RecyclerView.Adapter<ImageFragmentAdaptor.MyViewHolder> {
        public Context context;
        ImageFragment fragment;
        View view = null;
        private ArrayList<Get_Set> imagelist;
        private LayoutInflater inflater;

        public ImageFragmentAdaptor(Context context, ArrayList<Get_Set> list, ImageFragment fragment) {
            this.context = context;
            this.fragment = fragment;
            this.imagelist = list;
            inflater = LayoutInflater.from(context);
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        }

        @Override
        public ImageFragmentAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            view = inflater.inflate(R.layout.image_frag_design, parent, false);
            ImageFragmentAdaptor.MyViewHolder holder = new ImageFragmentAdaptor.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ImageFragmentAdaptor.MyViewHolder holder, int position) {
            get_set = imagelist.get(position);

            Glide.with(context).load(getString(R.string.path) + get_set.getThumb()).placeholder(R.drawable.bhakti_sangam_splas).into(holder.imageView);

            holder.download.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onClick(View v) {

                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null) {
                        if (networkInfo.isConnected()) {


                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("permission", Context.MODE_PRIVATE);

                            if (ContextCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {

                                // Permission is not granted
                                // Should we show an explanation?
                                if (fragment.shouldShowRequestPermissionRationale(
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                    fragment.requestPermissions(
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            1);
                                    // Show an explanation to the user *asynchronously* -- don't block
                                    // this thread waiting for the user's response! After the user
                                    // sees the explanation, try again to request the permission.
                                } else if (sharedPreferences.getBoolean("grant", false)) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Permission");
                                    builder.setMessage("Grant storage permission to download image.");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent callAppInfoSettingIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            callAppInfoSettingIntent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                                            startActivityForResult(callAppInfoSettingIntent, DOWNLOAD);
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    builder.show();
                                } else {

                                    // No explanation needed; request the permission
                                    fragment.requestPermissions(
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            1);


                                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                    // app-defined int constant. The callback method gets the
                                    // result of the request.
                                }

                                //sshred value change
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("grant", true).apply();
                            } else {
                                // Permission has already been granted
                                downloadImage();

                            }
                        } else
                            Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });


            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Please wait...", Toast.LENGTH_SHORT).show();
                    try {

                        Glide
                                .with(context)
                                .load(getString(R.string.path) + get_set.getThumb())
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>(400, 720) {
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

                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
            });
        }

        public void convertToFile(Bitmap myBitmap) {

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

        public void share(Uri bmpUri) {

            final Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/jpg");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_link));
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
                imageView = (ImageView) itemView.findViewById(R.id.image_frag_imageview);
                share = (ImageView) itemView.findViewById(R.id.image_frag_share);
                download = (ImageView) itemView.findViewById(R.id.downloadImage);

            }
        }
    }
}
