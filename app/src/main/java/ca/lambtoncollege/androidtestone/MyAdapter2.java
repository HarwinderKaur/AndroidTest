package ca.lambtoncollege.androidtestone;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ca.lambtoncollege.androidtestone.Database.DatabaseOperations;

/**
 * Created by ramandeepsingh on 2017-08-04.
 */

class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyviewHolder>  {
    private ArrayList<Recipe> infoList = new ArrayList<>();
    private LayoutInflater mInflater;
    Context context;
    Recipe currentItem;
    int pos=0;

    public MyAdapter2(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    public void setData(ArrayList<Recipe> list,Boolean b) {
        this.infoList = list;
        //update the adapter to reflect the new set of movies
        notifyDataSetChanged();
    }


    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.myrow2, parent, false);
        MyviewHolder viewHolder = new MyviewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, final int position) {
        currentItem = infoList.get(position);

         holder.recName.setText(currentItem.getTitle());
         holder.publisherName.setText(currentItem.getPublisher());
        holder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("id",currentItem.getRecipeId()+"l");
                infoList.remove(position);
                notifyDataSetChanged();
            }
        });
        Picasso.with(context).load(currentItem.getImageUrl()).into(holder.icon);


       Log.d("count",""+currentItem.getF2fUrl());



    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }
    public String getCount() {
        String count= Integer.toString(infoList.size());
        return count;
    }

    public void setData(List<Recipe> arrayList) {

    }

    static class MyviewHolder extends RecyclerView.ViewHolder {
        TextView recName;
        TextView publisherName;
        TextView to_loc;
        TextView pick;
        ImageView icon;
        View colbar;
        FloatingActionButton fab;


        public MyviewHolder(View itemView) {
            super(itemView);
            recName = (TextView) itemView.findViewById(R.id.recName);
            publisherName = (TextView) itemView.findViewById(R.id.publisherName);
            icon = (ImageView) itemView.findViewById(R.id.imageView);
            fab = itemView.findViewById(R.id.delete);
//            pick = (TextView) itemView.findViewById(R.id.pick);
//            icon= (ImageView) itemView.findViewById(R.id.icon);
//            colbar=  itemView.findViewById(R.id.colbar);

        }
    }
}