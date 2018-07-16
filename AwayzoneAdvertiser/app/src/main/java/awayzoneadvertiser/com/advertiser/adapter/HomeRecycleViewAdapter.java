package awayzoneadvertiser.com.advertiser.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.R;

/**
 * Created by Devraj on 1/10/2018.
 */

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewAdapter.HomeRecyclerview> {

    Context context;
    List<String> analyticlist = new ArrayList();




    public  HomeRecycleViewAdapter(Context context,List<String> analyticlist)
    {
       this.context = context;
       this.analyticlist = analyticlist;
    }

    @Override
    public HomeRecyclerview onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homerecyclerview,parent,false);
        return new HomeRecyclerview(view);
    }
    @Override
    public void onBindViewHolder(HomeRecyclerview holder, int position) {
//click
        if(position==0)
        {
            holder.relativelayout.setBackgroundResource(R.color.dblue);
            holder.textview.setText(analyticlist.get(position));
            holder.commenttextview.setText("Impressions");
         }
       if(position==1)
         {
           holder.relativelayout.setBackgroundResource(R.color.dyellow);
           holder.imageview.setImageResource(R.drawable.clickswhite);
             holder.textview.setText(analyticlist.get(position));
             holder.commenttextview.setText("Clicks");
        }
        if(position==2)
        {
            holder.relativelayout.setBackgroundResource(R.color.ddarkblue);
            holder.imageview.setImageResource(R.drawable.reviews);
            holder.textview.setText(analyticlist.get(position));
            holder.commenttextview.setText("Reviews");
        }
        if(position==3)
        {
            holder.relativelayout.setBackgroundResource(R.color.dpurple);
            holder.imageview.setImageResource(R.drawable.stories);
            holder.textview.setText(analyticlist.get(position));
            holder.commenttextview.setText("Stories");
        }
        if(position==4)
        {
            holder.relativelayout.setBackgroundResource(R.color.dlightblue);
            holder.imageview.setImageResource(R.drawable.beenhere);
            holder.textview.setText(analyticlist.get(position));
            holder.commenttextview.setText("Been Here");
        }
        if(position==5)
        {
            holder.relativelayout.setBackgroundResource(R.color.dorange);
            holder.imageview.setImageResource(R.drawable.bookmark);
            holder.textview.setText(analyticlist.get(position));
            holder.commenttextview.setText("Bookmarks");
        }
        if(position==6)
        {
            holder.relativelayout.setBackgroundResource(R.color.dorange);
            holder.imageview.setImageResource(R.drawable.likes);
            holder.textview.setText(analyticlist.get(position));
            holder.commenttextview.setText("Likes");
        }
        if(position==7)
        {
            holder.relativelayout.setBackgroundResource(R.color.dgreen);
            holder.imageview.setImageResource(R.drawable.share);
            holder.textview.setText(analyticlist.get(position));
            holder.commenttextview.setText("Shares");
        }
        if(position==8)
        {
            holder.relativelayout.setBackgroundResource(R.color.assmani);
            holder.imageview.setImageResource(R.drawable.comments);
            holder.textview.setText(analyticlist.get(position));
            holder.commenttextview.setText("Comments");
        }
}

    @Override
    public int getItemCount() {
        return 9;
    }

    public class HomeRecyclerview extends RecyclerView.ViewHolder {

        RelativeLayout relativelayout;
        ImageView imageview;
        TextView textview,commenttextview;

        public HomeRecyclerview(View itemView) {
            super(itemView);
            relativelayout = (RelativeLayout)itemView.findViewById(R.id.relativelayout);
            imageview = (ImageView) itemView.findViewById(R.id.imageview);
            textview = (TextView) itemView.findViewById(R.id.textview);
            commenttextview = (TextView) itemView.findViewById(R.id.commenttextview);

        }
    }
}
