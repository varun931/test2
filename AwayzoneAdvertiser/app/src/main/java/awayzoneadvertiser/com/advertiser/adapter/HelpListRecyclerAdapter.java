package awayzoneadvertiser.com.advertiser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.ChatActivity;
import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.gettersetter.TicketDatum;
import awayzoneadvertiser.com.advertiser.util.Constant;

/**
 * Created by Devraj on 1/19/2018.
 */

public class HelpListRecyclerAdapter extends RecyclerView.Adapter<HelpListRecyclerAdapter.TicketRecyclerView>
{
    List<TicketDatum> list = new ArrayList();
    Context context;

    public HelpListRecyclerAdapter(List<TicketDatum> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public TicketRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticketlistrecyclerview,parent,false);
        return new TicketRecyclerView(view);
    }
    @Override
    public void onBindViewHolder(TicketRecyclerView holder, int position) {

        holder.tickettitle.setText(list.get(position).getTitle()+"("+list.get(position).getPriority()+")");
        holder.decriptiontext.setText(list.get(position).getDescription());
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getTime());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TicketRecyclerView extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date,time,tickettitle,decriptiontext;
        LinearLayout mainlayout;
        public TicketRecyclerView(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            tickettitle = (TextView)itemView.findViewById(R.id.tickettitle);
            mainlayout = (LinearLayout) itemView.findViewById(R.id.mainlayout);
            decriptiontext = (TextView)itemView.findViewById(R.id.decriptiontext);
            mainlayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.mainlayout:
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra(Constant.CHATADDID,list.get(getAdapterPosition()).getAdvertiserId());
                    intent.putExtra(Constant.TICKETID,list.get(getAdapterPosition()).getTicketId());
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
