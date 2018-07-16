package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.adapter.HelpListRecyclerAdapter;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.TicketDatum;
import awayzoneadvertiser.com.advertiser.gettersetter.TicketListResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/19/2018.
 */

public class HelpListActivitiy extends AppCompatActivity implements View.OnClickListener{

    RecyclerView ticketlistrecyclerview;
    LinearLayout nextbtn;
    HelpListRecyclerAdapter ticketListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    ProgressDialogClass progressDialogClass;
    Config config;
    List<TicketDatum> list = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RelativeLayout helpbackarrowlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticketlistactivity);

        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ticketlistrecyclerview = (RecyclerView)findViewById(R.id.ticketlistrecyclerview);
        nextbtn  = (LinearLayout) findViewById(R.id.nextbtn);
        helpbackarrowlayout  = (RelativeLayout) findViewById(R.id.helpbackarrowlayout);
        linearLayoutManager = new LinearLayoutManager(this);
        ticketlistrecyclerview.setLayoutManager(linearLayoutManager);

        progressDialogClass = new ProgressDialogClass(HelpListActivitiy.this);
        config = new Config(HelpListActivitiy.this);

        ticketwebervice();
        nextbtn.setOnClickListener(this);
        helpbackarrowlayout.setOnClickListener(this);



    }

    public void ticketwebervice()
    {
       if (!isNetworkAvailable()) {
        Toast.makeText(HelpListActivitiy.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
    } else {
        progressDialogClass.showdialog();
        RequestInterface requestInterface = config.retrofitRegister();
        Call<TicketListResponse> call = requestInterface.ticketlist(sharedPreferences.getInt(Constant.USERID,0));
        call.enqueue(new Callback<TicketListResponse>() {
            @Override
            public void onResponse(Call<TicketListResponse> call, Response<TicketListResponse> response) {
                progressDialogClass.hideDialog();
                list= response.body().getTicketData();
                ticketListRecyclerAdapter = new HelpListRecyclerAdapter(list,HelpListActivitiy.this);
                ticketlistrecyclerview.setAdapter(ticketListRecyclerAdapter);
            }
            @Override
            public void onFailure(Call<TicketListResponse> call, Throwable t) {
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


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.nextbtn:
                Intent intent = new Intent(HelpListActivitiy.this,CreateNewAddActivity.class);
                startActivity(intent);
                break;
            case R.id.helpbackarrowlayout:
                onBackPressed();
                break;
        }
    }
}
