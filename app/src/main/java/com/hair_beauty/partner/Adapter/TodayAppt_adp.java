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

public class TodayAppt_adp extends RecyclerView.Adapter<TodayAppt_adp.ViewHolder> {
    ArrayList<Myservices_Mod> todayArray = new ArrayList<>();
    Activity context;

    public TodayAppt_adp(ArrayList<Myservices_Mod> todayArray, Activity context) {
        this.todayArray = todayArray;
        this.context = context;
    }

    @NonNull
    @Override
    public TodayAppt_adp.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.todayaapt_adp_lay,viewGroup,false);
        TodayAppt_adp.ViewHolder view_appt = new TodayAppt_adp.ViewHolder(view);
        return view_appt;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayAppt_adp.ViewHolder holder, int i) {
        holder.cus_name.setText(todayArray.get(i).getName());
        holder.user_serv.setText((todayArray.get(i).getId()).replaceAll("\\[", "").replaceAll("\\]","").
                replaceAll("\"",""));
        holder.user_time.setText(todayArray.get(i).getStart_time());

    }

    @Override
    public int getItemCount() {
        return todayArray.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cus_name)
        TextView cus_name;
        @BindView(R.id.user_serv)
        TextView user_serv;
        @BindView(R.id.user_time)
        TextView user_time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
