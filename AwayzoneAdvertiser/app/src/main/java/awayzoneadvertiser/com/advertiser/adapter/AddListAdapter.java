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
import com.suke.widget.SwitchButton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.Ad;
import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * Created by Devraj on 1/18/2018.
 */

public class AddListAdapter extends RecyclerView.Adapter<AddListAdapter.AddListRecyclerView> {
    List<Ad> list = new ArrayList<>();
    String imageurl;
    Context context;
    ProgressDialogClass progressDialogClass;
    int userid=0;
    public AddListAdapter(int userid,List<Ad> list, String imageurl, Context context)
    {
        this.list = list;
        this.imageurl = imageurl;
        this.context = context;
        this.userid = userid;
        progressDialogClass = new ProgressDialogClass(context);
    }
    @Override
    public AddListRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addlistrecyclerview,parent,false);
        return new AddListRecyclerView(view);
    }
    @Override
    public void onBindViewHolder(AddListRecyclerView holder, int position) {
        holder.startdatetext.setText("Start Date:"+list.get(position).getStartDate());
        holder.enddatetextview.setText("End Date:"+list.get(position).getEndDate());
        holder.impression.setText(list.get(position).getImpression()+"");
        holder.click.setText(list.get(position).getClicks()+"");
        Glide.with(context).load(imageurl+list.get(position).getImage()).into(holder.imageview);
        if(list.get(position).getStatus()==0)
        {
            holder.switch_button.setVisibility(View.GONE);
            holder.statustextview.setText(Constant.PENDING);
        }
        else if(list.get(position).getStatus()==2)
        {
            holder.switch_button.setVisibility(View.GONE);
            holder.statustextview.setText(Constant.DISAAPROVOED);

        }
        else if(list.get(position).getStatus()==1)
        {
            holder.statustextview.setVisibility(View.GONE);
            holder.switch_button.setVisibility(View.VISIBLE);
            if (list.get(position).getOnOff() == 1) {
                holder.switch_button.setChecked(true);
            } else {
                holder.switch_button.setChecked(false);
            }

        }
        else {

            holder.statustextview.setVisibility(View.GONE);
            if (list.get(position).getOnOff() == 1) {
                holder.switch_button.setChecked(true);
            } else {
                holder.switch_button.setChecked(false);
            }
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AddListRecyclerView extends RecyclerView.ViewHolder {

        TextView startdatetext,enddatetextview,impression,click,statustextview;
        ImageView imageview;
        SwitchButton switch_button;

        public AddListRecyclerView(View itemView) {
            super(itemView);
            startdatetext = (TextView)itemView.findViewById(R.id.startdatetext);
            enddatetextview = (TextView)itemView.findViewById(R.id.enddatetextview);
            imageview = (ImageView) itemView.findViewById(R.id.imageview);
            switch_button = (SwitchButton) itemView.findViewById(R.id.switch_button);
            impression = (TextView) itemView.findViewById(R.id.impression);
            click = (TextView) itemView.findViewById(R.id.click);
            statustextview = (TextView) itemView.findViewById(R.id.statustextview);

            switch_button.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    //TODO do your job

                    if(isChecked)
                    {
                        bookbeenhere(userid, list.get(getAdapterPosition()).getId());
                    }
                    else
                    {
                        bookbeenhere(userid, list.get(getAdapterPosition()).getId());
                    }

                }
            });
        }
    }
    public  void bookbeenhere(int userid, final int addid)
    {
        progressDialogClass.showdialog();
        //RequestInterface requestInterface= retrofitRegister();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://173.255.247.199/away_zone/advertisers/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<HalfRegisterationResponse> call = requestInterface.addlist(userid,addid);
        call.enqueue(new retrofit2.Callback<HalfRegisterationResponse>() {
            @Override
            public void onResponse(Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {
                progressDialogClass.hideDialog();
               if(response.body().getStatus().equalsIgnoreCase("1")) {
                }
            }
            @Override
            public void onFailure(Call<HalfRegisterationResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
                //  progressDialogClass.hideDialog();
                //  webservice();
            }
        });

    }
}
