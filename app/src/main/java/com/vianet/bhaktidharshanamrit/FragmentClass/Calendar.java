package com.vianet.bhaktidharshanamrit.FragmentClass;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.vianet.bhaktidharshanamrit.Helper.AppControllerSingleton;
import com.vianet.bhaktidharshanamrit.Helper.Get_Set;
import com.vianet.bhaktidharshanamrit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Calendar extends Fragment {

    CalendarView calendar;
    ArrayList<Get_Set> calList;
    TextView tithi;
    TextView sunrise;
    TextView sunset;
    TextView moonrise;
    TextView moonset;
    TextView nakshatra;
    TextView yoga;
    TextView shaka_samwat;
    TextView vikram;
    ScrollView scrollView;
    TextView error;
    ImageView refreshImage;
    TextView festival;
    int i;
    String url = "http://162.144.68.182/ambey/API/calendar_webservice.php?action=AllCalendar";
    private ProgressBar progress;
    // TODO: Rename parameter arguments, choose names that match

    public Calendar() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        tithi = view.findViewById(R.id.tithiDesc);
        sunrise = view.findViewById(R.id.sunrisedesc);
        sunset = view.findViewById(R.id.sunsetdesc);
        moonrise = view.findViewById(R.id.moonrisedesc);
        moonset = view.findViewById(R.id.moonsetdesc);
        nakshatra = view.findViewById(R.id.nakshatradesc);
        yoga = view.findViewById(R.id.yogadesc);
        shaka_samwat = view.findViewById(R.id.shakadesc);
        vikram = view.findViewById(R.id.vikramdesc);
        festival = view.findViewById(R.id.festivaldesc);
        progress = view.findViewById(R.id.calendarProgress);
        calendar = view.findViewById(R.id.calendar);
        scrollView = view.findViewById(R.id.calendarScroll);
        refreshImage = view.findViewById(R.id.calendarRefresh);
        error = view.findViewById(R.id.calendarErrorText);

        scrollView.setVisibility(View.GONE);
        calList = new ArrayList<>();

        makecalendarRequest();

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makecalendarRequest();
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                int month1;
                month1 = month + 1;
                String day1;

                String mont;
                if (month < 10) {
                    mont = 0 + String.valueOf(month1);
                } else {
                    mont = String.valueOf(month1);
                }

                if (dayOfMonth < 10) {
                    day1 = 0 + String.valueOf(dayOfMonth);
                } else {
                    day1 = String.valueOf(dayOfMonth);
                }

                for (i = 0; i < calList.size(); i++) {
                    if (calList.get(i).getScheduledate().equals(year + "-" + mont + "-" + day1)) {
                        dataSetWhenDataAwailable();
                        break;

                    } else {
                        dataSetWhenNoDataAwailable();
                    }
                }
            }
        });
        return view;
    }


    private void makecalendarRequest() {
        progress.setVisibility(View.VISIBLE);
        refreshImage.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("AllCalendar");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Get_Set get_set = new Get_Set();
                        JSONObject data = jsonArray.getJSONObject(i);
                        get_set.setTithi(data.getString("tithi"));
                        get_set.setFestival(data.getString("Festival_title"));
                        get_set.setMoonrise(data.getString("Moonrise"));
                        get_set.setMoonset(data.getString("Moonset"));
                        get_set.setNakshatra(data.getString("Nakshatra"));
                        get_set.setShakasamwat(data.getString("Shaka_Samvat"));
                        get_set.setSunRise(data.getString("Sunrise"));
                        get_set.setSunset(data.getString("Sunset"));
                        get_set.setVikramsamvat(data.getString("Vikram_Samvat"));
                        get_set.setYoga(data.getString("Yoga"));
                        get_set.setScheduledate(data.getString("schedule_date"));
                        calList.add(get_set);

                    }
                    progress.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                    for (i = 0; i < calList.size(); i++) {

                        if (calList.get(i).getScheduledate().equals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))) {
                            dataSetWhenDataAwailable();
                            break;

                        } else {
                            dataSetWhenNoDataAwailable();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error1) {

                progress.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                refreshImage.setVisibility(View.VISIBLE);

                if (error1 instanceof TimeoutError || error1 instanceof NoConnectionError) {
                    error.setText(R.string.Error_TimeOut);

                } else if (error1 instanceof AuthFailureError) {
                    error.setText(R.string.AuthFailure);

                } else if (error1 instanceof ServerError) {
                    error.setText(R.string.Server_Error);

                } else if (error1 instanceof NetworkError) {
                    error.setText(R.string.Network);

                } else if (error1 instanceof ParseError) {
                    error.setText(R.string.Parsing_error);
                }
            }
        });
        AppControllerSingleton.getMinstance().addToRequestQueue(stringRequest);
    }

    public void dataSetWhenNoDataAwailable() {
        tithi.setText(R.string.data);
        sunrise.setText(R.string.data);
        sunset.setText(R.string.data);
        moonrise.setText(R.string.data);
        moonset.setText(R.string.data);
        nakshatra.setText(R.string.data);
        yoga.setText(R.string.data);
        shaka_samwat.setText(R.string.data);
        vikram.setText(R.string.data);
        festival.setText(R.string.data);

    }

    private void dataSetWhenDataAwailable() {

        tithi.setText(calList.get(i).getTithi());
        sunrise.setText(calList.get(i).getSunRise());
        sunset.setText(calList.get(i).getSunset());
        moonrise.setText(calList.get(i).getMoonrise());
        moonset.setText(calList.get(i).getMoonset());
        nakshatra.setText(calList.get(i).getNakshatra());
        yoga.setText(calList.get(i).getYoga());
        shaka_samwat.setText(calList.get(i).getShakasamwat());
        vikram.setText(calList.get(i).getVikramsamvat());
        festival.setText(calList.get(i).getFestival());

    }
}
