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

public class TodayIncome_adp extends RecyclerView.Adapter<TodayIncome_adp.ViewHolder> {
    ArrayList<Myservices_Mod> incomeArray = new ArrayList<>();
    Activity context;

    public TodayIncome_adp(ArrayList<Myservices_Mod> incomeArray, Activity context) {
        this.incomeArray = incomeArray;
        this.context = context;
    }

    @NonNull
    @Override
    public TodayIncome_adp.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.todayincome_adp_lay,viewGroup,false);
        TodayIncome_adp.ViewHolder view_income = new TodayIncome_adp.ViewHolder(view);
        return view_income;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayIncome_adp.ViewHolder holder, int i) {



    }

    @Override
    public int getItemCount() {
        return incomeArray.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cus_in_name)
        TextView cus_in_name;
        @BindView(R.id.serv_in)
        TextView serv_in;
        @BindView(R.id.price_in)
        TextView price_in;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
