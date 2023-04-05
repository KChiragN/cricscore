package com.example.cricscore;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cricscore.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        ActivityMainBinding binding;
        String url;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding= ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            ActionBar actionBar=getSupportActionBar();
            actionBar.setTitle("Upcoming Matches");
            ListView listView=findViewById(R.id.listView);
            url = "https://api.cricapi.com/v1/cricScore?apikey=47e7ec11-6e04-411c-811a-451c86522470";
            ArrayList<String> namearraylist=new ArrayList<>();
            StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray array = new JSONObject(response).getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String team1 = jsonObject.getString("t1");
                            String team2 = jsonObject.getString("t2");
                            String t1s=jsonObject.getString("t1s");
                            String t2s=jsonObject.getString("t2s");
                            String datetime = jsonObject.getString("dateTimeGMT");
                            int result=datetime.compareTo("2023-03-27 'T' 00:00:00");
                            String status = jsonObject.getString("status");
                            String team =team1+"vs"+team2+"\n\n"+t1s+"\t\t"+t2s+"\n\n"+status+"\n\n"+datetime;
                            String team3 =team1+"vs"+team2+"\n\n"+status+"\n\n"+datetime+"\n";
                            if(status.equals("Match not started"))
                               namearraylist.add(team3);
                            else
                               namearraylist.add(team);
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this,
                                android.R.layout.simple_list_item_1, namearraylist);
                        binding.listView.setAdapter(arrayAdapter);
                        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                                String t1s="";
                                String t2s="";
                                String id="";
                                String team1="";
                                String team2="";
                                try {
                                    JSONArray array = new JSONObject(response).getJSONArray("data");
                                    for (int i = 0; i < array.length(); i++) {
                                        if (i == pos) {
                                            JSONObject jsonObject = array.getJSONObject(i);
                                            id = jsonObject.getString("id");
                                            team1 = jsonObject.getString("t1");
                                            team2 = jsonObject.getString("t2");
                                            t1s=jsonObject.getString("t1s");
                                            t2s=jsonObject.getString("t2s");
                                            String datetime = jsonObject.getString("dateTimeGMT");
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
                                            String status = jsonObject.getString("status");
                                            String team = team1 + " vs " + team2;
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(getApplicationContext(), MainActivity1.class);
                                intent.putExtra("t1", team1);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            RequestQueue requestQueue= Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }
    }