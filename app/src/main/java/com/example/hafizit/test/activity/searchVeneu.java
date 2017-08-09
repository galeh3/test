package com.example.hafizit.test.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hafizit.test.AppController;
import com.example.hafizit.test.R;
import com.example.hafizit.test.adapter.VeneuAdapter;
import com.example.hafizit.test.adapter.searchVeneuAdapter;
import com.example.hafizit.test.com.example.hafizit.test.services.getNotif;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.hafizit.test.AppController.TAG;

/**
 * Created by Hafiz IT on 07/08/2017.
 */

public class searchVeneu extends AppCompatActivity {
    private String url = "https://api.fibobeta.id/venue/filtersearch";
    private RecyclerView recyclerVeneu;
    private EditText editSearch;
    searchVeneuAdapter mAdapter;
    List<VeneuAdapter> veneus;
    LinearLayoutManager mLayoutManager;
    int max = 0;
    boolean requested = true;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_veneu);
        recyclerVeneu = (RecyclerView) findViewById(R.id.recyclerVeneu);
        editSearch = (EditText) findViewById(R.id.editSearch);

//        VeneuAdapter data = new VeneuAdapter();
//        data.setName("a");
//        datas.add(data);
//

        veneus = new ArrayList<VeneuAdapter>();
        mAdapter = new searchVeneuAdapter(getBaseContext(), veneus);
        recyclerVeneu.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerVeneu.setLayoutManager(mLayoutManager);
        recyclerVeneu.setItemAnimator(new DefaultItemAnimator());
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editSearch.getText().toString().length() > 0){
                    veneus.clear();
                    mAdapter.notifyDataSetChanged();
                    getVeneu(0, editSearch.getText().toString());
                    max = 0;
                    requested = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        try {
            Intent msgIntent = new Intent(this, getNotif.class);
//        msgIntent.putExtra(getNotif.PARAM_IN_MSG, strInputMsg);
            this.startService(msgIntent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getVeneu(final int page, final String search){
        int pages = page * 5;
        final String urls = "?uid=4&fiboVenueName=" + search + "&spcat=FTS&fiboStartPage=" + pages + "&dtsrc=2017-09-09";
        Log.d("page", String.valueOf(pages));
        String tag_json_obj = "req" + page;
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url + urls, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray datas = response.getJSONObject(0).getJSONArray("message");
                            if(max <= datas.length()) max = datas.length();
                            else requested = false;
                            for(int i = 0; i < datas.length(); i++){
                                JSONObject data = datas.getJSONObject(i);
                                VeneuAdapter veneu = new VeneuAdapter();
                                veneu.setName(data.getString("venue_name"));
                                veneu.setAddress(data.getString("venue_address"));
                                veneu.setImage(data.getString("image"));
                                veneus.add(veneu);
                            }
                        } catch (Exception e){
                            Log.d("error1", e.toString());
                        }

                        mAdapter.notifyDataSetChanged();
                        recyclerVeneu.clearOnScrollListeners();
                        recyclerVeneu.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                if(requested) {
                                    try {
                                        int visibleItemCount = mLayoutManager.getChildCount();
                                        int totalItemCount = mLayoutManager.getItemCount();
                                        int pastVisibleItems = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                                        //                    Log.d("s", String.valueOf(pastVisibleItems));
                                        if ((pastVisibleItems + visibleItemCount >= totalItemCount) && (totalItemCount % max == 0)) {
                                            recyclerView.clearOnScrollListeners();
                                            Log.d("page2", String.valueOf(page+1));
                                            //                        getVeneu(1, search);
                                            getVeneu(page+1, search);
                                            return;
                                        }
                                    } catch (Exception e) {
                                        Log.d("error2", e.toString());
                                    }
                                }

                            }
                        });
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        if(requested) AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }
}