package ca.lambtoncollege.androidtestone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.lambtoncollege.androidtestone.Database.DatabaseOperations;

public class RecipieDetails extends AppCompatActivity {

    int recipieId = 0;
SharedPreferences sharedPreferences;

    TextView title,publisher,f2f,source_url,social_rank,publisher_url;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      sharedPreferences = getSharedPreferences(RecipieHome.RECIEPIEDETAILS, Context.MODE_PRIVATE);

        title  = (TextView) findViewById(R.id.Rtitle);
        publisher = (TextView) findViewById(R.id.Rpublisher);
        f2f = (TextView) findViewById(R.id.Rf2f_url);
        source_url = (TextView) findViewById(R.id.Rsource_url);
        social_rank = (TextView) findViewById(R.id.Rsocial_rank);
        publisher_url= (TextView) findViewById(R.id.Rpublisher_url);
        imageView = (ImageView) findViewById(R.id.recImage);


        title.setText(sharedPreferences.getString("title",""));
        publisher.setText(sharedPreferences.getString("publisher",""));
        f2f.setText(sharedPreferences.getString("f2f_url",""));
        source_url.setText(sharedPreferences.getString("source_url",""));
        social_rank.setText(sharedPreferences.getInt("social_rank",0)+"");
        publisher_url.setText(sharedPreferences.getString("publisher_url",""));
        Picasso.with(RecipieDetails.this).load(sharedPreferences.getString("image_url","")).into(imageView);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseOperations dop = new DatabaseOperations(RecipieDetails.this);
                dop.putInformation(dop,sharedPreferences.getString("publisher",""),sharedPreferences.getString("f2f_url",""),sharedPreferences.getString("title",""),sharedPreferences.getString("source_url",""),sharedPreferences.getString("recipe_id",""),sharedPreferences.getString("publisher_url",""),sharedPreferences.getString("image_url",""));

            }
        });
    }



}
