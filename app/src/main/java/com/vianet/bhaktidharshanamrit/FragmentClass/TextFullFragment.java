package com.vianet.bhaktidharshanamrit.FragmentClass;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.vianet.bhaktidharshanamrit.MainActivity.text;


public class TextFullFragment extends DialogFragment {
    ViewPager viewPager;
    FullTextPagerAdaptor fullTextPagerAdaptor;
    TextView texttitle;
    ScrollView scrollView;
    private int position;

    public TextFullFragment() {
        // Required empty public constructor
    }
/*
    // TODO: Rename and change types and number of parameters
    public static TextFullFragment newInstance(String param1, String param2) {
        TextFullFragment fragment = new TextFullFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }*/

  /*  private static String[] splitToNChar(String text, int size) {
        List<String> parts = new ArrayList<>();

        int length = text.length();
        for (int i = 0; i < length; i += size) {
            parts.add(text.substring(i, Math.min(length, i + size)));
        }
        return parts.toArray(new String[0]);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyMaterialTheme_wallpaper);

        Bundle bundle = getArguments();
        position = bundle.getInt("position");

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text_full, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.fulltextViewPager);
        ImageView back = (ImageView) view.findViewById(R.id.home_text_image);
        texttitle = (TextView) view.findViewById(R.id.title_text_frag);
        final ImageView textShare = (ImageView) view.findViewById(R.id.home_text_share);


        textShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

//                Log.d("sixty", "onClick: " + "\n" + Arrays.toString(splitToNChar(text.get(viewPager.getCurrentItem()).getDescription(), 160)));
                  intent.putExtra(Intent.EXTRA_TEXT, text.get(viewPager.getCurrentItem()).getDescription() +"\n" + getString(R.string.app_link));
//                intent.putExtra(Intent.EXTRA_TEXT, Arrays.toString(splitToNChar(text.get(viewPager.getCurrentItem()).getDescription(), 160)) + "\n" + getString(R.string.app_link));
                getContext().startActivity(Intent.createChooser(intent, "Share text"));

            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fullTextPagerAdaptor = new FullTextPagerAdaptor(getContext(), text);

        viewPager.setAdapter(fullTextPagerAdaptor);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                texttitle.setText(text.get(position).getTitle());

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


    public class FullTextPagerAdaptor extends PagerAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<Get_Set> text;
        Get_Set get_set;
        TextView textView;
        ImageView imageView;
        private ClipboardManager myClipboard;
        private ClipData myClip;
        private TextView prevPage;
        private TextView nextPage;

        public FullTextPagerAdaptor(Context context, ArrayList<Get_Set> list) {
            this.context = context;
            this.text = list;
            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {

            return text.size();

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = inflater.inflate(R.layout.full_text_view, container, false);
            get_set = text.get(position);
            imageView = (ImageView) view.findViewById(R.id.text_frag_image);
            scrollView = (ScrollView) view.findViewById(R.id.fullTextScroll);
            textView = (TextView) view.findViewById(R.id.text_frag_description);

            myClipboard = (ClipboardManager) AppControllerSingleton.getMinstance().getSystemService(CLIPBOARD_SERVICE);

            textView.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return true;

                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return true;

                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                    switch (item.getItemId()) {

                        case android.R.id.copy:

                            int min = 0;
                            int max = textView.getText().length();
                            if (textView.isFocused()) {

                                final int selStart = textView.getSelectionStart();
                                final int selEnd = textView.getSelectionEnd();

                                min = Math.max(0, Math.min(selStart, selEnd));
                                max = Math.max(0, Math.max(selStart, selEnd));

                            }
                            // Perform your definition lookup with the selected text
                            final CharSequence selectedText = textView.getText()
                                    .subSequence(min, max);
                            String text = selectedText.toString();

                            myClip = ClipData.newPlainText("text", text);
                            myClipboard.setPrimaryClip(myClip);

                            // Finish and close the ActionMode
                            mode.finish();
                            return true;

                        case android.R.id.cut:
                            // add your custom code to get cut functionality according
                            // to your requirement
                            return true;

                        case android.R.id.paste:
                            // add your custom code to get paste functionality according
                            // to your requirement
                            return true;

                        default:
                            break;
                    }
                    return false;

                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });

            nextPage = (TextView) view.findViewById(R.id.nextText);
            prevPage = (TextView) view.findViewById(R.id.previousText);


            if (position == 0 && text.size() > 1) {

                nextPage.setVisibility(View.VISIBLE);
                prevPage.setVisibility(View.GONE);

            } else if (position > 0 && position < text.size() - 1) {

                nextPage.setVisibility(View.VISIBLE);
                prevPage.setVisibility(View.VISIBLE);

            } else if (position == text.size() - 1 && text.size() > 1) {

                nextPage.setVisibility(View.GONE);
                prevPage.setVisibility(View.VISIBLE);

            }

            container.addView(view);
            textView.setText(get_set.getDescription());
            textView.setTextIsSelectable(true);
            Glide.with(context).load(getString(R.string.path) + get_set.getThumb()).into(imageView);
            return view;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }
}
