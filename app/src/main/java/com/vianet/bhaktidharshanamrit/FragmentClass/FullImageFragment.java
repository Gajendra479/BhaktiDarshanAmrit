package com.vianet.bhaktidharshanamrit.FragmentClass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.vianet.bhaktidharshanamrit.AdaptorClass.FullImagePagerAdaptor;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FullImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FullImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullImageFragment extends DialogFragment {
    ViewPager viewPager;
    FullImagePagerAdaptor fullImagePagerAdaptor;
    private String path="http://162.144.68.182/ambey/Thumbnails/";


    private OnFragmentInteractionListener mListener;
    private int position;
    private ImageView back;
    private ImageView share;
    private Uri imageuri=null;
    private ProgressBar progressshare;

    public FullImageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FullImageFragment newInstance(String param1, String param2) {
        FullImageFragment fragment = new FullImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.AppTheme_NoActionBar;
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.MyMaterialTheme_wallpaper);

        Bundle bundle=getArguments();
        position=bundle.getInt("position");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_full_image, container, false);
        viewPager= (ViewPager) view.findViewById(R.id.fullimageViewPager);
        back=(ImageView)view.findViewById(R.id.home_image_image);
        share= (ImageView) view.findViewById(R.id.home_image_share);
        progressshare= (ProgressBar) view.findViewById(R.id.shareProgresss);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                share.setEnabled(false);
                try {


                    progressshare.setVisibility(View.VISIBLE);

                    Glide
                            .with(getContext())
                            .load(path+MainActivity.images.get(viewPager.getCurrentItem()).getThumb())
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(400,720) {
                                @Override
                                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                    super.onLoadFailed(e, errorDrawable);
                                    progressshare.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "Check your Internet Connectivity", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                    convertToFile(resource);
                                }

                            });


                }catch (Exception error){

                    share.setEnabled(true);

                    progressshare.setVisibility(View.GONE);

                }


            }
        });


        fullImagePagerAdaptor=new FullImagePagerAdaptor(getContext(), MainActivity.images);
        viewPager.setAdapter(fullImagePagerAdaptor);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(position);

        return view;
    }


        public  void convertToFile(Bitmap myBitmap){

            try {
                File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
                FileOutputStream out = new FileOutputStream(file);
                myBitmap.compress(Bitmap.CompressFormat.PNG, 60, out);
                out.close();
//                bmpUri = Uri.fromFile(file);

                share(Uri.fromFile(file));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void share (Uri bmpUri){

        progressshare.setVisibility(View.GONE);

        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
//                final File photoFile = new File(getFilesDir(), "foo.jpg");
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        getContext().startActivity(Intent.createChooser(shareIntent, "Share image using"));
//        share.setEnabled(true);

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
