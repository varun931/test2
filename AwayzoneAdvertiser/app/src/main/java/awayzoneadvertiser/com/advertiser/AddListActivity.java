package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.triusinfotech.awayzoneadvertiser.util.Config;

import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.adapter.AddListAdapter;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.Ad;
import awayzoneadvertiser.com.advertiser.gettersetter.AddListResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/18/2018.
 */

public class AddListActivity extends AppCompatActivity implements View.OnClickListener{

    AddListAdapter addListAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView addlistrecyclerview;
    List<Ad> addlist = new ArrayList<>();
    ProgressDialogClass progressDialogClass;
    Config config;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView createadd;
    RelativeLayout adlistbackarrowlayout,noadfound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addlistactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        addlistrecyclerview = (RecyclerView)findViewById(R.id.addlistrecyclerview);
        adlistbackarrowlayout = (RelativeLayout) findViewById(R.id.adlistbackarrowlayout);
        noadfound = (RelativeLayout) findViewById(R.id.noadfound);
        createadd = (ImageView) findViewById(R.id.createadd);
        linearLayoutManager = new LinearLayoutManager(this);
        addlistrecyclerview.setLayoutManager(linearLayoutManager);
        addlist = new ArrayList<>();
        progressDialogClass = new ProgressDialogClass(AddListActivity.this);
        config = new Config(AddListActivity.this);
        webervice();


        final View decorView = getWindow().getDecorView();
       // final View decorView = getActivity().getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int i) {
                        int height = decorView.getHeight();
                        int width = decorView.getWidth();
                    }
                });

        createadd.setOnClickListener(this);
        adlistbackarrowlayout.setOnClickListener(this);
    }


    public void webervice() {
        if (!isNetworkAvailable()) {
            Toast.makeText(AddListActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        } else {
            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();
            Call<AddListResponse> call = requestInterface.addlist(sharedPreferences.getInt(Constant.USERID,0));
            call.enqueue(new Callback<AddListResponse>() {
                @Override
                public void onResponse(Call<AddListResponse> call, Response<AddListResponse> response) {
                    progressDialogClass.hideDialog();
                    if(response.body().getAds().size()==0) {
                        addlistrecyclerview.setVisibility(View.GONE);
                        noadfound.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        addlist = response.body().getAds();
                        addListAdapter = new AddListAdapter(sharedPreferences.getInt(Constant.USERID,0),addlist, response.body().getImageUrl(), AddListActivity.this);
                        addlistrecyclerview.setAdapter(addListAdapter);
                    }
                }
                @Override
                public void onFailure(Call<AddListResponse> call, Throwable t) {
                    progressDialogClass.hideDialog();
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
            case R.id.createadd:
                if(sharedPreferences.getInt(Constant.USERNOOFADS,0)==0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddListActivity.this);
                    builder.setMessage("Please Buy Subscription Plan")
                            .setTitle("");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    Intent intent2 = new Intent(AddListActivity.this,CreateAddActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.adlistbackarrowlayout:
                onBackPressed();
                break;
        }
    }
}
