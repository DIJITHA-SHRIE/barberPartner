package com.hair_beauty.partner.Adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hair_beauty.partner.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.MyViewHolder> {

    private final Activity context;
    int[] image;

    public HomePageAdapter(FragmentActivity context, int[] image) {
        this.context = context;
        this.image = image;
    }

    @Override
    public HomePageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.home_adapter, parent, false);

        HomePageAdapter.MyViewHolder holder = new HomePageAdapter.MyViewHolder(view1);
        return holder;
    }
    @Override
    public void onBindViewHolder (final HomePageAdapter.MyViewHolder holder, final int position) {

        Picasso.with(context).load(image[position]).placeholder(R.drawable.image1).error(R.drawable.image1).into(holder.image_bg);

    }
    @Override
    public int getItemCount () {
        return image.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_bg;

        public MyViewHolder(View view1) {
            super(view1);

            image_bg = (ImageView) view1.findViewById(R.id.bg);
        }
    }
}