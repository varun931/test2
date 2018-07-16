package awayzoneadvertiser.com.advertiser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.gettersetter.ChatDatum;

/**
 * Created by Devraj on 1/19/2018.
 */

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatRecyclerView> {

    List<ChatDatum> list = new ArrayList<>();
    String userimage;
    Context context;

    public ChatRecyclerViewAdapter(List<ChatDatum> list,String userimage,Context context)
    {
        this.list = list;
        this.userimage = userimage;
        this.context = context;
    }

    @Override
    public ChatRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View viewONE = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatrecyclerviewone, parent, false);
                ChatRecyclerView rowONE = new ChatRecyclerView(viewONE);
                return rowONE;

            case 2:
                View viewTWO = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatrecyclerviewtwo, parent, false);
                ChatRecyclerView rowTWO = new ChatRecyclerView(viewTWO);
                return rowTWO;

        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {


        if(list.get(position).getAdminId()==1)
        {
            return 1;
        }
        else if(list.get(position).getAdminId()==0)
        {
            return 2;
        }
        else
        {
           return 2;
        }



        /*if (position % 3 == 0) {
            return 1;
        } *//*else if (position % 2 == 0) {
            return 2;
        }*//* else {
            return 2;
        }*/
    }

    @Override
    public void onBindViewHolder(ChatRecyclerView holder, int position) {

       holder.commenttext.setText(list.get(position).getMessage());
       holder.time.setText(list.get(position).getChatTime());
       holder.date.setText(list.get(position).getChatDate());

       if(list.get(position).getAdminId()==1)
       {
           holder.username.setText("ADMIN");
       }
       else
       {
          holder.username.setText("YOU");
          if(userimage.equalsIgnoreCase(""))
          {
              Glide.with(context).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(holder.profilepic);
          }
          else
          {
              Glide.with(context).load(userimage).apply(RequestOptions.circleCropTransform()).into(holder.profilepic);
          }
       }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatRecyclerView extends RecyclerView.ViewHolder {
        TextView commenttext,username,time,date;
        ImageView profilepic;

        public ChatRecyclerView(View itemView) {
            super(itemView);
            commenttext = (TextView)itemView.findViewById(R.id.commenttext);
            username = (TextView)itemView.findViewById(R.id.username);
            time = (TextView)itemView.findViewById(R.id.time);
            date = (TextView)itemView.findViewById(R.id.date);
            profilepic = (ImageView) itemView.findViewById(R.id.profilepic);


        }
    }
}
