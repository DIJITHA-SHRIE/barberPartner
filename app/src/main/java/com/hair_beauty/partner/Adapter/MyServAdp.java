package com.hair_beauty.partner.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hair_beauty.partner.Model.Myservices_Mod;
import com.hair_beauty.partner.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyServAdp extends RecyclerView.Adapter<MyServAdp.ViewHolder> {
    ArrayList<Myservices_Mod> myservices_modArrayList;
    Activity context;

    public MyServAdp(ArrayList<Myservices_Mod> myservices_modArrayList, Activity context) {
        this.myservices_modArrayList = myservices_modArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyServAdp.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.myserv_adp_lay,viewGroup,false);
        MyServAdp.ViewHolder view_serv = new MyServAdp.ViewHolder(view);
        return view_serv;
    }

    @Override
    public void onBindViewHolder(@NonNull MyServAdp.ViewHolder holder, int i) {
        Myservices_Mod index = myservices_modArrayList.get(i);
        holder.name.setText(index.getPac_name());
        holder.category.setText(index.getCat_name());
        holder.price.setText(index.getPrice());
        holder.time.setText(index.getTimes());

    }

    @Override
    public int getItemCount() {
        return myservices_modArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.time)
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
