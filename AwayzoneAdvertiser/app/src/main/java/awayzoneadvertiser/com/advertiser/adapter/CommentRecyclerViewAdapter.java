package awayzoneadvertiser.com.advertiser.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.gettersetter.CommentList;

/**
 * Created by Devraj on 12/18/2017.
 */

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentRecyclerView> {

    Context context;
    List<CommentList> list = new ArrayList<>();

    String imageurl;
    int userId;
    String TAG="CommentRecycler";

    public CommentRecyclerViewAdapter(int userId, String imageurl, List<CommentList> list, Context context)
    {
        this.userId = userId;
        this.context = context;
        this.list = list;

        this.imageurl= imageurl;
    }
    @Override
    public CommentRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentrecyclerview,parent,false);
       return new CommentRecyclerView(view);
    }
    @Override
    public void onBindViewHolder(CommentRecyclerView holder, int position) {

//        holder.username.setText(list.get(position).getFirst_name());
//        holder.commenttext.setText(list.get(position).getComment());
//        holder.date.setText(list.get(position).getDate());
//        holder.time.setText(list.get(position).getTime());
//        if(list.get(position).getProfile_image().equalsIgnoreCase(""))
//        {
//            Glide.with(context).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(holder.profilepic);
//        }
//        else {
//            Glide.with(context).load(imageurl + list.get(position).getProfile_image()).apply(RequestOptions.circleCropTransform()).into(holder.profilepic);
//        }
//
//        if(userId==list.get(position).getUser_id())
//        {
//            holder.optionicon.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            holder.optionicon.setVisibility(View.INVISIBLE);
//        }

    }
    @Override
    public int getItemCount() {

      //  return list.size();
        return 10;
    }

    public class CommentRecyclerView extends RecyclerView.ViewHolder  {
        ImageView profilepic;
        TextView optionicon,username,commenttext,date,time;
        public CommentRecyclerView(View itemView) {
            super(itemView);
            profilepic = (ImageView)itemView.findViewById(R.id.profilepic);
            username = (TextView) itemView.findViewById(R.id.username);
            commenttext = (TextView) itemView.findViewById(R.id.commenttext);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);

        }

    }
}
