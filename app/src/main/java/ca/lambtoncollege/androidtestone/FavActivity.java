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
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ca.lambtoncollege.androidtestone.Database.DatabaseOperations;

public class FavActivity extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private MyAdapter2 mAdapter;
    private List<Recipe> arrayList = new ArrayList<Recipe>();
    SharedPreferences sharedPreferences;
    public static String RECIEPIEDETAILS= "rec";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecyclerview= (RecyclerView) findViewById(R.id.FReclist);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mAdapter = new MyAdapter2(FavActivity.this);
        sharedPreferences = getSharedPreferences(RECIEPIEDETAILS, Context.MODE_PRIVATE);

        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setHasFixedSize(false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        DatabaseOperations dop = new DatabaseOperations(FavActivity.this);
       arrayList = dop.readData(dop);

        mAdapter.setData((ArrayList<Recipe>) arrayList,true);


        ItemClickSupport.addTo(mRecyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(FavActivity.this,RecipieDetails.class);
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

    }

}
