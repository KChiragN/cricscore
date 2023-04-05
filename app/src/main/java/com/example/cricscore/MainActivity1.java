package com.example.cricscore;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        public class MainActivity1 extends AppCompatActivity {
            ActivityMainBinding binding;
            String url="https://api.cricapi.com/v1/match_scorecard?apikey=47e7ec11-6e04-411c-811a-451c86522470&id=";;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                binding= ActivityMainBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());
                ActionBar actionBar=getSupportActionBar();
                actionBar.setTitle("Match Details");
                Intent intent=getIntent();
                String id=intent.getStringExtra("id");
                Log.d("MainActivity", "id received: " + id);
                url=url+id;
                ListView listView=findViewById(R.id.listView);
                ArrayList<String> namearraylist=new ArrayList<>();
                StringRequest jsonObjectRequest = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject dataJObject = jsonObject.getJSONObject("data");
                            String status = dataJObject.getString("status");
                            String venue = dataJObject.getString("venue");
                            JSONArray teamsArray = dataJObject.getJSONArray("teams");
                            String team1 = teamsArray.getString(Integer.parseInt("0"));
                            String team2 = teamsArray.getString(Integer.parseInt("1"));
                            String teams="TEAM1:"+team1+"\t\tTEAM2:"+team2;
                            namearraylist.add(teams);
                            namearraylist.add(status);
                            namearraylist.add(venue);
                            JSONArray scoreArray=dataJObject.getJSONArray("score");
                            for(int i=0;i<scoreArray.length();i++) {
                                JSONObject jsonObject1=scoreArray.getJSONObject(i);
                                String runs=jsonObject1.getString("r");
                                String overs=jsonObject1.getString("o");
                                String wickets=jsonObject1.getString("w");
                                String score="runs:\t\t"+runs+"overs\t\t"+overs+"wickets\t\t"+wickets;
                                namearraylist.add(score);
                            }
                           JSONArray scorecardArray=dataJObject.getJSONArray("scorecard");
                            namearraylist.add("BATTING SCORECARD");
                                JSONObject jsonObject2 = scorecardArray.getJSONObject(0);
                                JSONArray battingArray = jsonObject2.getJSONArray("batting");
                                JSONArray bowlingArray = jsonObject2.getJSONArray("bowling");
                                for (int i = 0; i < battingArray.length(); i++) {
                                    JSONObject jsonObject3 = battingArray.getJSONObject(i);
                                    JSONObject jsonObject4 = jsonObject3.getJSONObject("batsman");
                                    String name = jsonObject4.getString("name");
                                    String dismissal = jsonObject3.getString("dismissal-text");
                                    String runs = jsonObject3.getString("r");
                                    String balls = jsonObject3.getString("b");
                                    String fours = jsonObject3.getString("4s");
                                    String sixes = jsonObject3.getString("6s");
                                    String strikerate = jsonObject3.getString("sr");
                                    String scorecard = name + "\t" + dismissal + "\t" + runs + "\t" + balls + "\t" + fours + "\t" + sixes + "\t" + strikerate;
                                    namearraylist.add(scorecard);
                                }
                                for (int i = 0; i < bowlingArray.length(); i++) {
                                    JSONObject jsonObject3 = bowlingArray.getJSONObject(i);
                                    JSONObject jsonObject4 = jsonObject3.getJSONObject("bowler");
                                    String name = jsonObject4.getString("name");
                                    String overs = jsonObject3.getString("o");
                                    String maiden = jsonObject3.getString("m");
                                    String runs = jsonObject3.getString("r");
                                    String wickets = jsonObject3.getString("w");
                                    String economy = jsonObject3.getString("eco");
                                    String scorecard = name + "\t" + overs + "\t" + maiden + "\t" + runs + "\t" + wickets + "\t" + economy;
                                    namearraylist.add(scorecard);
                                }
                                namearraylist.add("BATTING SCORECARD");
                                jsonObject2 = scorecardArray.getJSONObject(1);
                            battingArray = jsonObject2.getJSONArray("batting");
                             bowlingArray = jsonObject2.getJSONArray("bowling");
                            for (int i = 0; i < battingArray.length(); i++) {
                                JSONObject jsonObject3 = battingArray.getJSONObject(i);
                                JSONObject jsonObject4 = jsonObject3.getJSONObject("batsman");
                                String name = jsonObject4.getString("name");
                                String dismissal = jsonObject3.getString("dismissal-text");
                                String runs = jsonObject3.getString("r");
                                String balls = jsonObject3.getString("b");
                                String fours = jsonObject3.getString("4s");
                                String sixes = jsonObject3.getString("6s");
                                String strikerate = jsonObject3.getString("sr");
                                String scorecard = name + "\t" + dismissal + "\t" + runs + "\t" + balls + "\t" + fours + "\t" + sixes + "\t" + strikerate;
                                namearraylist.add(scorecard);
                            }
                            for (int i = 0; i < bowlingArray.length(); i++) {
                                JSONObject jsonObject3 = bowlingArray.getJSONObject(i);
                                JSONObject jsonObject4 = jsonObject3.getJSONObject("bowler");
                                String name = jsonObject4.getString("name");
                                String overs = jsonObject3.getString("o");
                                String maiden = jsonObject3.getString("m");
                                String runs = jsonObject3.getString("r");
                                String wickets = jsonObject3.getString("w");
                                String economy = jsonObject3.getString("eco");
                                String scorecard = name + "\t" + overs + "\t" + maiden + "\t" + runs + "\t" + wickets + "\t" + economy;
                                namearraylist.add(scorecard);
                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity1.this,
                                    android.R.layout.simple_list_item_1, namearraylist);
                            binding.listView.setAdapter(arrayAdapter);
                            binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)  {
                                    String name="";
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONObject dataJObject = jsonObject.getJSONObject("data");
                                        String status = dataJObject.getString("status");
                                        Toast.makeText(MainActivity1.this, ""+namearraylist.get(pos),Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                /*Intent intent = new Intent(getApplicationContext(), MainActivity1.class);
                                intent.putExtra("t1", team1);
                                intent.putExtra("t2", team2);
                                startActivity(intent);*/
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
