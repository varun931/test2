package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.CulturePojo;
import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.UserProfileResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/15/2018.
 */

public class PorfileActivity extends AppCompatActivity implements View.OnClickListener{

    ProgressDialogClass progressDialogClass;
    TextView username,adlist;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView profileimageview,profilepic;
    RelativeLayout editprofilebtn,changepasswordbtn,profilebackarrowlayout,myadrelativebtn,helpbtn,subscriptionbtn,referafriendbtn,rateappbtn;
    String email,name,imageurl,imagename,contacno,organizationname,defaultprofileimage,userdescription,userlias;
    float lat,lang;
    String userculture;
    List<CulturePojo> culturelist = new ArrayList<>();
    List<CulturePojo> interestlist = new ArrayList<>();
    List<String> addCulture = new ArrayList<>();
    RelativeLayout signoutbtn,addlistlayout;
    Config config;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        config = new Config(PorfileActivity.this);
        profileimageview = (ImageView)findViewById(R.id.profileimageview);
        profilepic = (ImageView)findViewById(R.id.profilepic);
        editprofilebtn = (RelativeLayout)findViewById(R.id.editprofilebtn);
        subscriptionbtn = (RelativeLayout)findViewById(R.id.subscriptionbtn);
        referafriendbtn = (RelativeLayout)findViewById(R.id.referafriendbtn);
        rateappbtn = (RelativeLayout)findViewById(R.id.rateappbtn);
        helpbtn = (RelativeLayout)findViewById(R.id.helpbtn);
        addlistlayout = (RelativeLayout)findViewById(R.id.addlistlayout);
        myadrelativebtn = (RelativeLayout)findViewById(R.id.myadrelativebtn);
        changepasswordbtn = (RelativeLayout)findViewById(R.id.changepasswordbtn);
        profilebackarrowlayout = (RelativeLayout)findViewById(R.id.profilebackarrowlayout);
        signoutbtn = (RelativeLayout)findViewById(R.id.signoutbtn);
        progressDialogClass = new ProgressDialogClass(PorfileActivity.this);
        username = (TextView)findViewById(R.id.username);
        adlist = (TextView)findViewById(R.id.adlist);
        if(sharedPreferences.getString(Constant.REGPROFILEIMAGE,"").equalsIgnoreCase(""))
        {
            Glide.with(this).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(profileimageview);
        }
        else
        {
            Glide.with(this).load(sharedPreferences.getString(Constant.REGPROFILEIMAGEURL,"")+ sharedPreferences.getString(Constant.REGPROFILEIMAGE,"")).apply(RequestOptions.circleCropTransform()).into(profileimageview);
        }
        if(sharedPreferences.getString(Constant.USERTYPE,"").equalsIgnoreCase(Constant.EMAIL))
        {
            changepasswordbtn.setVisibility(View.VISIBLE);
        }
        else
        {
            changepasswordbtn.setVisibility(View.GONE);
        }
        culturewebservice();
        editprofilebtn.setOnClickListener(this);
        changepasswordbtn.setOnClickListener(this);
        signoutbtn.setOnClickListener(this);
        profilebackarrowlayout.setOnClickListener(this);
        profilepic.setOnClickListener(this);
        myadrelativebtn.setOnClickListener(this);
        helpbtn.setOnClickListener(this);
        subscriptionbtn.setOnClickListener(this);
        referafriendbtn.setOnClickListener(this);
        rateappbtn.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        if(sharedPreferences.getString(Constant.REGPROFILEIMAGE,"").equalsIgnoreCase(""))
        {
            Glide.with(this).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(profileimageview);
        }
        else
        {
            Glide.with(this).load(sharedPreferences.getString(Constant.REGPROFILEIMAGEURL,"")+ sharedPreferences.getString(Constant.REGPROFILEIMAGE,"")).apply(RequestOptions.circleCropTransform()).into(profileimageview);
        }
        super.onResume();
    }
    public void culturewebservice()
    {
        progressDialogClass.showdialog();

        RequestInterface requestInterface= config.retrofitRegister();

        retrofit2.Call<UserProfileResponse> call = requestInterface.adverprofile(sharedPreferences.getInt(Constant.USERID,0));
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(retrofit2.Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                progressDialogClass.hideDialog();
                username.setText(response.body().getFirstName());
                email = response.body().getEmail();
                name = response.body().getFirstName();
                imageurl = response.body().getImageUrl();
                imagename = response.body().getImage();
                contacno = response.body().getContactNo();
                organizationname = response.body().getOrganizationName();
                defaultprofileimage = response.body().getDefault_profile_image();
                userdescription=response.body().getUser_description();
                userlias =response.body().getAlias_name();
                if(response.body().getAds()==0)
                {
                 addlistlayout.setVisibility(View.GONE);
                }
                else
                {
                addlistlayout.setVisibility(View.VISIBLE);
                adlist.setText(response.body().getAds()+"");
                }

                editor.putInt(Constant.USERNOOFADS,response.body().getNo_of_ads());
                editor.commit();
               username.setText("Welcome "+name);
                //       culturelist = response.body().getCulture_data();
                for (int i = 0; i < response.body().getCultureData().size(); i++) {
                    culturelist.add(new CulturePojo(i, response.body().getCultureData().get(i).getId(), response.body().getCultureData().get(i).getCul_name(), "NO"));
                }

                for (int i = 0; i < response.body().getInterest().size(); i++) {
                    interestlist.add(new CulturePojo(i, response.body().getInterest().get(i).getId(), response.body().getInterest().get(i).getName(), "NO"));
                }
                lat = response.body().getLati();
                lang = response.body().getLongi();
                for (int j = 0; j < culturelist.size(); j++) {
                    addCulture.add(culturelist.get(j).getId() + "");
                }

                JSONArray roomrules = new JSONArray();
                for (int i = 0; i < addCulture.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("culture", addCulture.get(i));
                        roomrules.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                editor.putString(Constant.PROFILECULTURE, roomrules.toString());
                editor.commit();
            }
            @Override
            public void onFailure(retrofit2.Call<UserProfileResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.editprofilebtn:
                Intent intent = new Intent(PorfileActivity.this,EditProfileActivity.class);
                intent.putExtra(Constant.PROFILEUSERNNAME,name);
                intent.putExtra(Constant.PROFILEUSEREMAIL,email);
                intent.putExtra(Constant.PROFILEIMAGEURL,imageurl);
                intent.putExtra(Constant.PROFILEIMAGENAME,imagename);
                intent.putExtra(Constant.PROFILELATITUDE,lat);
                intent.putExtra(Constant.PROFILELONGITUDE,lang);
                intent.putExtra(Constant.PROFILEUSERCULTRURE,userculture);
                intent.putExtra(Constant.PROFILECONTACTNO,contacno);
                intent.putExtra(Constant.PROFILEORGANISATIONNAME,organizationname);
                intent.putExtra(Constant.PRFOLIECULTURELIST,(ArrayList<CulturePojo>)culturelist);
                intent.putExtra(Constant.PROFILEINTERESTLIST,(ArrayList<CulturePojo>)interestlist);
                intent.putExtra(Constant.DEFAULTPROFILEIMAGE,defaultprofileimage);
                intent.putExtra(Constant.PROFILEUSERDESCRIPTION,userdescription);
                intent.putExtra(Constant.PROFILEUSERALIAS,userlias);
                startActivity(intent);
                break;
            case R.id.changepasswordbtn:
          Intent intent1 = new Intent(PorfileActivity.this,ChangePasswordActivity.class);
          startActivity(intent1);
               break;
            case R.id.signoutbtn:
                loginwebservice();
          /*      progressDialogClass.showdialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(sharedPreferences.getString(Constant.USERTYPE,"").equalsIgnoreCase("facebook"))
                        {
                            LoginManager.getInstance().logOut();
                        }
                        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename,Context.MODE_PRIVATE);
                        sharedPreferences.edit().clear().commit();
                        progressDialogClass.hideDialog();
                        Intent intent = new Intent(PorfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                },4000);*/
                break;
            case R.id.profilebackarrowlayout:
                onBackPressed();
                break;
            case R.id.profilepic:
                if(sharedPreferences.getInt(Constant.USERNOOFADS,0)==0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PorfileActivity.this);
                    builder.setMessage("Please Buy Subscription Plan")
                            .setTitle("");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent2 = new Intent(PorfileActivity.this,SubscriptionList.class);
                            startActivity(intent2);
                       }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    Intent intent2 = new Intent(PorfileActivity.this,CreateAddActivity.class);
                    startActivity(intent2);
                }
                break;
            case R.id.myadrelativebtn:
                Intent addbutton = new Intent(PorfileActivity.this,AddListActivity.class);
                startActivity(addbutton);
                break;
            case R.id.helpbtn:
                Intent helpintent = new Intent(PorfileActivity.this,HelpListActivitiy.class);
                startActivity(helpintent);
                break;
             case R.id.subscriptionbtn:
                Intent subintentn = new Intent(PorfileActivity.this,SubscriptionList.class);
                startActivity(subintentn);
                break;
            case R.id.referafriendbtn:
                final String appPackageName = BuildConfig.APPLICATION_ID;
                final String appName = PorfileActivity.this.getString(R.string.app_name);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBodyText = "https://play.google.com/store/apps/details?id=" +
                        appPackageName;
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                PorfileActivity.this.startActivity(Intent.createChooser(shareIntent, PorfileActivity.this.getString(R.string
                        .app_name)));
                break;
            case R.id.rateappbtn:
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()+"");
                Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(goMarket);
                break;
        }
    }
    public void loginwebservice()
    {
        if (!isNetworkAvailable()) {
            Toast.makeText(PorfileActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        } else {
            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();
            Call<HalfRegisterationResponse> call = requestInterface.logout(sharedPreferences.getInt(Constant.USERID,0));
            call.enqueue(new Callback<HalfRegisterationResponse>() {
                @Override
                public void onResponse(Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(sharedPreferences.getString(Constant.USERTYPE,"").equalsIgnoreCase("facebook"))
                            {
                                LoginManager.getInstance().logOut();
                            }
                            sharedPreferences = getSharedPreferences(Constant.sharedpreffilename,Context.MODE_PRIVATE);
                            sharedPreferences.edit().clear().commit();
                            progressDialogClass.hideDialog();
                            Intent intent = new Intent(PorfileActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    },3000);
                }
                @Override
                public void onFailure(Call<HalfRegisterationResponse> call, Throwable t) {
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
