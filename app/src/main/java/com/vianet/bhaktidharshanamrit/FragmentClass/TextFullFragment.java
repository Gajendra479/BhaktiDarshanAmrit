package com.vianet.bhaktidharshanamrit.FragmentClass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.MainActivity;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;

import static com.vianet.bhaktidharshanamrit.MainActivity.text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TextFullFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TextFullFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextFullFragment extends DialogFragment {
    ViewPager viewPager;
    FullTextPagerAdaptor fullTextPagerAdaptor;
    TextView texttitle;


    private OnFragmentInteractionListener mListener;
    private int position;
//    private TextView nextPage;
//    private TextView prevPage;
//    private int selectedPosition;

    public TextFullFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TextFullFragment newInstance(String param1, String param2) {
        TextFullFragment fragment = new TextFullFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.MyMaterialTheme_wallpaper);


        Bundle bundle=getArguments();
        position=bundle.getInt("position");

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_text_full, container, false);
        viewPager= (ViewPager) view.findViewById(R.id.fulltextViewPager);
        ImageView back= (ImageView) view.findViewById(R.id.home_text_image);
        texttitle= (TextView) view.findViewById(R.id.title_text_frag);
        final ImageView textShare= (ImageView) view.findViewById(R.id.home_text_share);


        textShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textShare.setEnabled(false);
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, text.get(viewPager.getCurrentItem()).getDescription());
                getContext().startActivity(Intent.createChooser(intent,"Share text"));

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fullTextPagerAdaptor=new FullTextPagerAdaptor(getContext(), text);
//        texttitle.setText();
        viewPager.setAdapter(fullTextPagerAdaptor);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                texttitle.setText(text.get(position).getTitle());
//                selectedPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
//                selectedPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(position);
        return view;
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

    public class FullTextPagerAdaptor extends PagerAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<Get_Set> text;
        private String path="http://162.144.68.182/ambey/Thumbnails/";
        Get_Set get_set;
        TextView textView;
        ImageView imageView;
        private TextView prevPage;
        private TextView nextPage;

        public FullTextPagerAdaptor(Context context , ArrayList<Get_Set> list){
            this.context=context;
            this.text=list;
            inflater=LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return text.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=inflater.inflate(R.layout.full_text_view,container,false);
            get_set=text.get(position);
            imageView= (ImageView) view.findViewById(R.id.text_frag_image);
            textView= (TextView) view.findViewById(R.id.text_frag_description);
            nextPage = (TextView) view.findViewById(R.id.nextText);
            prevPage= (TextView) view.findViewById(R.id.previousText);


                if (position == 0 && text.size() > 1) {

                    nextPage.setVisibility(View.VISIBLE);

                    prevPage.setVisibility(View.GONE);

                } else if (position > 0 && position < text.size()-1) {

                    nextPage.setVisibility(View.VISIBLE);
                    prevPage.setVisibility(View.VISIBLE);

                } else if (position == text.size()-1 && text.size() > 1) {

                    nextPage.setVisibility(View.GONE);

                    prevPage.setVisibility(View.VISIBLE);

                }



            container.addView(view);
//        texttitle.setText(get_set.getTitle());
            textView.setText(get_set.getDescription());
            Glide.with(context).load(path+get_set.getThumb()).into(imageView);
            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }


}
