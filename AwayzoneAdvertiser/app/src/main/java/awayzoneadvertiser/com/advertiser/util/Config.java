package com.triusinfotech.awayzoneadvertiser.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.GpsTracker;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Devraj on 12/6/2017.
 */

public class  Config {

   // Context context;
    SharedPreferences sharedPreferences,sharedPreferencess;
    SharedPreferences.Editor editor,editors;
    Activity activity;
    public boolean locationData=true;
    public boolean imageData=true;
    Geocoder geocoder;
    String locationName;
    double latitude;
    double longitude;
    double from_lat;
    double from__lng;
    String TAG="config";
    String fbData="";
    GpsTracker locationTracker;
    TwitterLoginButton twitterLoginButton;
    ProgressDialogClass progressDialogClass;
    LoginButton fbloginButton;
    CallbackManager callbackManager;
    long id;
    String beenhereStatus;


    public  Config()
    {

    }
    public Config(Activity activity)
    {
      //  this.context = context;
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences(Constant.sharedpreffilename,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialogClass = new ProgressDialogClass(activity);
    }
     public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public RequestInterface retrofitRegister()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
      //  Retrofit retrofit = new Retrofit.Builder().baseUrl("http://triusinfo.com/away_zone/users/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
      //  Retrofit retrofit = new Retrofit.Builder().baseUrl("http://triusinfo.com/away_zone/advertisers/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://173.255.247.199/away_zone/advertisers/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();

        // Retrofit retrofit = new Retrofit.Builder().baseUrl("http://122.180.20.185:91/skilltoskill/backend/web/api/user/").addConverterFactory(GsonConverterFactory.create()).build();
        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        return  requestInterface;
    }
 /*   public void saveSharedPref(String key,String value)
    {
        editor.putString(key,value);
        editor.commit();
    }*/

   /*  public void saveSharedIntPref(String key,int value)
    {
        editor.putInt(key,value);
        editor.commit();
    }*/

 /*   public int getSharedIntPref(String key)
    {
        return  sharedPreferences.getInt(key,0);
    }
    public String getSharedPref(String key)
    {
        return  sharedPreferences.getString(key,"");
    }*/


  /*  public void saveboleanShared(String key,boolean value)
    {
        editor.putBoolean(key,value);
        editor.commit();
    }
    public boolean getboleanShared(String key)
    {
        return  sharedPreferences.getBoolean(key,true);
    }*/


    // facebook Login
    public String  facebook(LoginButton loginButton, CallbackManager callbackManager)
    {
      // final String [] fb = new String[3];
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            //    updateUI();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    //  config.saveSharedPref(Constant.FBIMAGEURL,profile.getProfilePictureUri(80,80)+"");
                    //   webservice(profile.getId(),profile.getId(),"Facebook");
                    fbData= profile.getId()+"";
                } else {
                }
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
              /*  Toast.makeText(
                        SignUpActivity.this,
                        "Login Error",
                        Toast.LENGTH_LONG).show();*/
            }
        });
        return fbData;
    }
    // permisson
    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_COARSE_LOCATION";
        int res = activity.getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    //permission
    public boolean checkLocationPermission2() {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = activity.getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    //permission
    public void permissionAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Permission to access the Location is required for this app to get Current Location! Please Go to setting and ON Location Permission")
                .setTitle("Permission required");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                //   makeRequest();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    //permission image
    //permission
    public void permissionImageAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Permission to External Storage is required for this app to get profile photo! Please Go to setting and ON External Storage Permission")
                .setTitle("Permission required");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                //   makeRequest();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //permision
  /*  public void permission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

         if (!checkLocationPermission()) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else if (!checkLocationPermission2()) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
           else if (!getboleanShared(Constant.locationPermission)) {

                permissionAlert();
            }
            else if(!getboleanShared(Constant.ImagePermission))
            {
                permissionImageAlert();
            }
            else {
                permissiontrue();
            }
        }
    }*/
        //permission
    public void permissiontrue()
    {
        if (checkLocationPermission() && checkLocationPermission2()) {
            //chg   showAddress();
            //String email, String password, String lat, String longi, String device_id, String device_type, String auth_key
            // new LoginApiExecute(emaildata, passworddata, "lat", "longi", fireBaseRegId, "device_type", "auth_key").execute(UtilURL.BASE_URL);
        } else {
            //showSettingsAlert();
            Toast.makeText(activity, "Alloy permission to get location", Toast.LENGTH_SHORT).show();
        }
    }


    public void showhidepassword(TextView textView, EditText editText)
    {
        if(textView.getText().equals(activity.getResources().getString(R.string.eye)))
        {
            textView.setText(activity.getResources().getString(R.string.eyeblock));
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else if(textView.getText().equals(activity.getResources().getString(R.string.eyeblock)))
        {
            textView.setText(activity.getResources().getString(R.string.eye));
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
