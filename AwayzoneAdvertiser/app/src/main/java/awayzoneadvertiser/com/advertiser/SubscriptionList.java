package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import java.util.ArrayList;
import java.util.List;
import awayzoneadvertiser.com.advertiser.adapter.SubscriptionAdapter;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.Plan;
import awayzoneadvertiser.com.advertiser.gettersetter.SubscriptionListResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/18/2018.
 */

public class SubscriptionList extends AppCompatActivity {

    RecyclerView subcriptionrecyclerview;
    SubscriptionAdapter subscriptionAdapter;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout profilebackarrowlayout;

    ProgressDialogClass progressDialogClass;
    Config config;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<Plan> planlist = new ArrayList<>();
    List<Plan> subplanlist = new ArrayList<>();
    List<Plan> addplanlist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscriptionlistactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialogClass = new ProgressDialogClass(SubscriptionList.this);
        config = new Config(SubscriptionList.this);
        subcriptionrecyclerview = (RecyclerView)findViewById(R.id.subcriptionrecyclerview);
        profilebackarrowlayout = (RelativeLayout) findViewById(R.id.profilebackarrowlayout);
        linearLayoutManager = new LinearLayoutManager(this);
        subcriptionrecyclerview.setLayoutManager(linearLayoutManager);
        webervice();
        profilebackarrowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }






    //SubscriptionListResponse



    public void webervice() {
        if (!isNetworkAvailable()) {
            Toast.makeText(SubscriptionList.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        } else {
            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();
            Call<SubscriptionListResponse> call = requestInterface.planlist(sharedPreferences.getInt(Constant.USERID,0));
            call.enqueue(new Callback<SubscriptionListResponse>() {
                @Override
                public void onResponse(Call<SubscriptionListResponse> call, Response<SubscriptionListResponse> response) {
                    progressDialogClass.hideDialog();
                    int incrementvalue=1;
                    int usernofaddbuy=0;
             for(int k=0; k<response.body().getUserPlan().size();k++)
                {
                    addplanlist.add(new Plan(response.body().getUserPlan().get(k).getId(),response.body().getUserPlan().get(k).getPlanName(),response.body().getUserPlan().get(k).getAmount(),response.body().getUserPlan().get(k).getDescription(),response.body().getUserPlan().get(k).getNoOfAds(),incrementvalue,response.body().getUserPlan().get(k).getStartDate(),"YES"));
                    incrementvalue++;
                    usernofaddbuy=response.body().getUserPlan().get(k).getNoOfAds();
                }
            for(int i=0;i<response.body().getPlan().size();i++)
              {
                planlist.add(new Plan(response.body().getPlan().get(i).getId(),response.body().getPlan().get(i).getPlanName(),response.body().getPlan().get(i).getAmount(),response.body().getPlan().get(i).getDescription(),response.body().getPlan().get(i).getNoOfAds(),incrementvalue,response.body().getPlan().get(i).getStartDate(),"NO"));
                  if(incrementvalue==3)
                   {
                       incrementvalue=0;
                   }
                  incrementvalue++;
             }

                    if(addplanlist.size()>0)
                    {

                        for(int m=0;m<planlist.size();m++)
                        {
                   if(planlist.get(m).getAmount()==0)
                    {
                        planlist.remove(m);
                    }
                        //  subplanlist.add(planlist.get(m));
                        //   planlist.add(addplanlist.get(0));
                    }
                        planlist.add(0,addplanlist.get(0));

                    }


                int size=response.body().getUserPlan().size();

                subscriptionAdapter = new SubscriptionAdapter(planlist,SubscriptionList.this,size,usernofaddbuy);
                subcriptionrecyclerview.setAdapter(subscriptionAdapter);
             }
                @Override
                public void onFailure(Call<SubscriptionListResponse> call, Throwable t) {
                    progressDialogClass.showdialog();
                }
            });
        }
    }



    // Private class isNetworkAvailable
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
