package awayzoneadvertiser.com.advertiser.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.PaypalDemoActivity;
import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.gettersetter.Plan;
import awayzoneadvertiser.com.advertiser.util.Constant;

/**
 * Created by Devraj on 1/18/2018.
 */

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionRecyclerView> {

    List<Plan> list = new ArrayList<>();
    Context context;

    int usernofaddbuy;
    int size;

    public SubscriptionAdapter(List<Plan> list, Context context,int size,int usernofaddbuy )
    {
        this.list = list;
        this.context = context;
        this.size = size;
        this.usernofaddbuy = usernofaddbuy;
    }
    @Override
    public SubscriptionRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscriptionrecyclerview,parent,false);
        return new SubscriptionRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(SubscriptionRecyclerView holder, int position) {

        holder.planname.setText(list.get(position).getPlanName());
        holder.planamount.setText("$"+list.get(position).getAmount());
        holder.decritpiontext.setText(list.get(position).getDescription());
        holder.noofads.setText("You can create "+list.get(position).getNoOfAds()+" add of this plan");

        if(list.get(position).getIncrementvalue()==1)
        {
            holder.addimagelayotout.setBackgroundResource(R.drawable.redbigarrow);
        }
        else  if(list.get(position).getIncrementvalue()==2)
        {
            holder.addimagelayotout.setBackgroundResource(R.drawable.greenbigarrow);
        }
        else  if(list.get(position).getIncrementvalue()==3)
        {
            holder.addimagelayotout.setBackgroundResource(R.drawable.bluebigarrow);
        }

      if(list.get(position).getStartDate().equalsIgnoreCase(""))
      {
          holder.subscriptionlayout.setVisibility(View.GONE);
      }
      else
      {
          holder.subscriptionlayout.setVisibility(View.VISIBLE);
          holder.subscriptiondate.setText("Current Plan start date "+list.get(position).getStartDate());

      }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubscriptionRecyclerView extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout addimagelayotout,subscriptionlayout;
        TextView planname,planamount,decritpiontext,noofads,subscriptiondate;
        LinearLayout mainlayout;
        public SubscriptionRecyclerView(View itemView) {
            super(itemView);
            addimagelayotout = (RelativeLayout)itemView.findViewById(R.id.addimagelayotout);
            subscriptionlayout = (RelativeLayout)itemView.findViewById(R.id.subscriptionlayout);
            planname = (TextView) itemView.findViewById(R.id.planname);
            planamount = (TextView) itemView.findViewById(R.id.planamount);
            mainlayout = (LinearLayout) itemView.findViewById(R.id.mainlayout);
            decritpiontext = (TextView) itemView.findViewById(R.id.decritpiontext);
            subscriptiondate = (TextView) itemView.findViewById(R.id.subscriptiondate);
            noofads = (TextView) itemView.findViewById(R.id.noofads);
            mainlayout.setOnClickListener(this);
            subscriptionlayout.setOnClickListener(this);

            //SubscriptionList subscriptionList
        }

        @Override
        public void onClick(View view) {

            switch (view.getId())
            {
               /* case R.id.mainlayout:
                    Intent intent = new Intent(context, PaypalDemoActivity.class);
                    intent.putExtra(Constant.PLANDID,list.get(getAdapterPosition()).getId());
                    intent.putExtra(Constant.AMOUNT,list.get(getAdapterPosition()).getAmount());
                    intent.putExtra(Constant.NOOFADS,list.get(getAdapterPosition()).getNoOfAds());
                    context.startActivity(intent);
                    break;*/
                case R.id.mainlayout:

 if(list.get(getAdapterPosition()).getUserdata().equalsIgnoreCase("YES")) {

     if (list.get(getAdapterPosition()).getAmount() == 0) {
         AlertDialog.Builder builder = new AlertDialog.Builder(context);
         builder.setMessage("You are not Eligible to upgrade this plan")
                 .setTitle("Subscription Plan");
         builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
             }
         });
         AlertDialog dialog = builder.create();
         dialog.show();
     }
     else {
         Intent intent = new Intent(context, PaypalDemoActivity.class);
         intent.putExtra(Constant.PLANDID, list.get(getAdapterPosition()).getId());
         intent.putExtra(Constant.AMOUNT, list.get(getAdapterPosition()).getAmount());
         intent.putExtra(Constant.NOOFADS, list.get(getAdapterPosition()).getNoOfAds());
         context.startActivity(intent);
     }
 }
 else {
                      if (usernofaddbuy == 0) {
                          Intent intent = new Intent(context, PaypalDemoActivity.class);
                          intent.putExtra(Constant.PLANDID, list.get(getAdapterPosition()).getId());
                          intent.putExtra(Constant.AMOUNT, list.get(getAdapterPosition()).getAmount());
                          intent.putExtra(Constant.NOOFADS, list.get(getAdapterPosition()).getNoOfAds());
                          context.startActivity(intent);
                      } else {
                          if (list.get(getAdapterPosition()).getNoOfAds() <= usernofaddbuy) {
                              AlertDialog.Builder builder = new AlertDialog.Builder(context);
                              builder.setMessage("Are you want to sure  Downgrade the plan")
                                      .setTitle("Subscription Plan");
                              builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int id) {
                                      Intent intent = new Intent(context, PaypalDemoActivity.class);
                                      intent.putExtra(Constant.PLANDID, list.get(getAdapterPosition()).getId());
                                      intent.putExtra(Constant.AMOUNT, list.get(getAdapterPosition()).getAmount());
                                      intent.putExtra(Constant.NOOFADS, list.get(getAdapterPosition()).getNoOfAds());
                                      context.startActivity(intent);
                                  }
                              });
                              AlertDialog dialog = builder.create();
                              dialog.show();
                          } else if (list.get(getAdapterPosition()).getNoOfAds() >= usernofaddbuy) {
                              AlertDialog.Builder builder = new AlertDialog.Builder(context);
                              builder.setMessage("Are you want to sure Upgrade the plan")
                                      .setTitle("Subscription Plan");
                              builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int id) {
                                      Intent intent = new Intent(context, PaypalDemoActivity.class);
                                      intent.putExtra(Constant.PLANDID, list.get(getAdapterPosition()).getId());
                                      intent.putExtra(Constant.AMOUNT, list.get(getAdapterPosition()).getAmount());
                                      intent.putExtra(Constant.NOOFADS, list.get(getAdapterPosition()).getNoOfAds());
                                      context.startActivity(intent);
                                  }
                              });
                              AlertDialog dialog = builder.create();
                              dialog.show();
                          } else {
                              Intent intent = new Intent(context, PaypalDemoActivity.class);
                              intent.putExtra(Constant.PLANDID, list.get(getAdapterPosition()).getId());
                              intent.putExtra(Constant.AMOUNT, list.get(getAdapterPosition()).getAmount());
                              intent.putExtra(Constant.NOOFADS, list.get(getAdapterPosition()).getNoOfAds());
                              context.startActivity(intent);
                          }
                      }
                  }

                    break;
            }
        }
    }
}
