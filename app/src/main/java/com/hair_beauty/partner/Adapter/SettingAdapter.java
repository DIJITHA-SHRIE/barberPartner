package com.hair_beauty.partner.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hair_beauty.partner.AboutUsActivity;
import com.hair_beauty.partner.AddressSetActivity;
import com.hair_beauty.partner.R;;import static com.hair_beauty.partner.utility.ServerLinks.aboutUs;
import static com.hair_beauty.partner.utility.ServerLinks.cancellation;
import static com.hair_beauty.partner.utility.ServerLinks.faq;
import static com.hair_beauty.partner.utility.ServerLinks.privacy;
import static com.hair_beauty.partner.utility.ServerLinks.support;
import static com.hair_beauty.partner.utility.ServerLinks.terms;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.MyViewHolder> {

    Activity context;
    String[] list;
    int[] icons;


    public SettingAdapter(Activity context, String[] list, int[] icons) {
        this.context = context;
        this.list = list;
        this.icons = icons;
    }

    @Override
    public SettingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.setting_adapter, parent, false);

        SettingAdapter.MyViewHolder holder = new SettingAdapter.MyViewHolder(view1);
        return holder;
    }
    @Override
    public void onBindViewHolder (final SettingAdapter.MyViewHolder holder, final int position) {

        holder.tv.setText(list[position]);
        holder.iv.setImageResource(icons[position]);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list[position].equals("My Account")) {
                    //context.startActivity(new Intent(context,AboutUsActivity.class));

                }else if (list[position].equals("About Us")) {

//                    Uri webpage = Uri.parse("http://www.door2dream.com/demo/barberapp/m_about_us.php");
//                    Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
//                    context.startActivity(myIntent);

                    context.startActivity(new Intent(context,AboutUsActivity.class)
                    .putExtra("link",aboutUs)
                    .putExtra("title","About Us"));

                } else if (list[position].equals("Support")) {
                    /*Uri webpage = Uri.parse("http://www.door2dream.com/demo/barberapp/m_support.php");
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                    context.startActivity(myIntent);*/

                    context.startActivity(new Intent(context,AboutUsActivity.class)
                            .putExtra("link",support)
                            .putExtra("title","Support"));
                } else if (list[position].equals("Career")) {
                  //  context.startActivity(new Intent(context,AboutUsActivity.class));
                } else if (list[position].equals("Terms")) {
//                    Uri webpage = Uri.parse("http://www.door2dream.com/demo/barberapp/m_terms_condition.php");
//                    Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
//                    context.startActivity(myIntent);

                    context.startActivity(new Intent(context,AboutUsActivity.class)
                            .putExtra("link",terms)
                            .putExtra("title","Terms"));

                } else if (list[position].equals("Privacy")) {
//                    Uri webpage = Uri.parse("http://www.door2dream.com/demo/barberapp/m_privacy_policy.php");
//                    Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                   // context.startActivity(myIntent);

                    context.startActivity(new Intent(context,AboutUsActivity.class)
                            .putExtra("link",privacy)
                            .putExtra("title","Privacy"));

                } else if (list[position].equals("Cancelation Policy")) {

//                    Uri webpage = Uri.parse("http://www.door2dream.com/demo/barberapp/m_cancelation_policy.php");
//                    Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
//                    context.startActivity(myIntent);

                    context.startActivity(new Intent(context,AboutUsActivity.class)
                            .putExtra("link",cancellation)
                            .putExtra("title","Cancelation Policy"));
                } else if (list[position].equals("FAQ")) {

                    Uri webpage = Uri.parse(faq);
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
                    context.startActivity(myIntent);

                }
                 else if (list[position].equals("Set Locations")) {
                    context.startActivity(new Intent(context,AddressSetActivity.class));
               }

            }
        });


    }

    @Override
    public int getItemCount () {
        return list.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;
        LinearLayout layout;


        public MyViewHolder(View view1) {
            super(view1);

            tv = (TextView) view1.findViewById(R.id.tv);
            iv = (ImageView) view1.findViewById(R.id.iv);
            layout = (LinearLayout) view1.findViewById(R.id.layout);
        }
    }
}

