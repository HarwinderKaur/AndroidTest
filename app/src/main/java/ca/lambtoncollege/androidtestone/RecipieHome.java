package ca.lambtoncollege.androidtestone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipieHome extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private MyAdapter mAdapter;
    private List<Recipe> arrayList = new ArrayList<Recipe>();
    SharedPreferences sharedPreferences;
    public static String RECIEPIEDETAILS= "rec";
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        makeJsonObjReq("");
        mRecyclerview= (RecyclerView) findViewById(R.id.Reclist);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mAdapter = new MyAdapter(RecipieHome.this);
        sharedPreferences = getSharedPreferences(RECIEPIEDETAILS, Context.MODE_PRIVATE);

        search = (EditText) findViewById(R.id.searchEditText);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setHasFixedSize(false);
        mRecyclerview.setLayoutManager(linearLayoutManager);



        ItemClickSupport.addTo(mRecyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(RecipieHome.this,RecipieDetails.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("publisher", arrayList.get(position).getPublisher());
                editor.putString("f2f_url", arrayList.get(position).getF2fUrl());
                editor.putString("title", arrayList.get(position).getTitle());
                editor.putString("source_url", arrayList.get(position).getSourceUrl());
                editor.putString("recipe_id", arrayList.get(position).getRecipeId());
                editor.putString("image_url", arrayList.get(position).getImageUrl());
                editor.putInt("social_rank", arrayList.get(position).getSocialRank());
                editor.putString("publisher_url", arrayList.get(position).getPublisherUrl());
                editor.commit();
                startActivity(i);

            }
        });

        search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    makeJsonObjReq(search.getText().toString());
                    search.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.fav:
                Intent i = new Intent(RecipieHome.this,FavActivity.class);
                startActivity(i);
                return true;
            case R.id.search:
                search.setVisibility(View.VISIBLE);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void makeJsonObjReq(String q) {



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://food2fork.com/api/search?key=afd5c73be1a8439def05e7dbf7ac0d88&q="+q, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());


                        try {
                            JSONArray recipes = response.getJSONArray("recipes");
                            for (int i = 0 ; i< recipes.length();i++){

                               JSONObject obj = recipes.getJSONObject(i);
                                String publisher = obj.getString("publisher");
                                String f2f_url = obj.getString("f2f_url");
                                String title = obj.getString("title");
                                String source_url = obj.getString("source_url");
                                String recipe_id = obj.getString("recipe_id");
                                String image_url = obj.getString("image_url");
                                int social_rank = obj.getInt("social_rank");
                                String publisher_url = obj.getString("publisher_url");

                               Recipe model = new Recipe();
                                model.setPublisher(publisher);
                                model.setF2fUrl(f2f_url);
                                model.setTitle(title);
                                model.setSourceUrl(source_url);
                                model.setRecipeId(recipe_id);
                                model.setImageUrl(image_url);
                                model.setSocialRank(social_rank);
                                model.setPublisherUrl(publisher_url);
                                arrayList.add(model);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mAdapter.setData((ArrayList<Recipe>) arrayList, true);



                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("error", "Error: " + error.getMessage());


                Log.d("error", error.toString());
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put( "charset", "utf-8");
                return headers;
            }



        };



        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
