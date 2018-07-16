package awayzoneadvertiser.com.advertiser;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.triusinfotech.awayzoneadvertiser.util.Config;

import awayzoneadvertiser.com.advertiser.util.Constant;


/**
 * Created by Devraj on 12/1/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ActivityCompat.OnRequestPermissionsResultCallback {

    RelativeLayout signupbtn,signinbtn;
    Config config;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        config = new Config(MainActivity.this);
        signupbtn = (RelativeLayout)findViewById(R.id.signupbtn);
        signinbtn = (RelativeLayout)findViewById(R.id.signinbtn);
        signupbtn.setOnClickListener(this);
        signinbtn.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilenames, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        permission();


      /*  if(!sharedPreferences.getBoolean(Constant.locationPermission,true))
        {
            config.permissionAlert();
        }
        else
        {*/
           //permission();
       // }
   }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signupbtn:
                Intent signintent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(signintent);
                break;
            case R.id.signinbtn:
                Intent signupintent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(signupintent);
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(String permission: permissions){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                //denied
                if(permission.equalsIgnoreCase("android.permission.ACCESS_COARSE_LOCATION"))
                {
                    editor.putString(Constant.READ_EXTERNAL_STORAGE,"NO");
                    editor.commit();
                }
                else if(permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE"))
                {
                    editor.putString(Constant.ACCESS_COARSE_LOCATION,"NO");
                    editor.commit();
                }
            }else{
                if(ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
                    //allowed
                    if(permission.equalsIgnoreCase("android.permission.ACCESS_COARSE_LOCATION"))
                    {
                        editor.putString(Constant.READ_EXTERNAL_STORAGE,"YES");
                        editor.commit();
                    }
                    else if(permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE"))
                    {
                        editor.putString(Constant.ACCESS_COARSE_LOCATION,"YES");
                        editor.commit();
                    }
                    //      showAddressData();
                } else{
                    //set to never ask again
                    if(permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE"))
                    {
                        editor.putBoolean(Constant.ImagePermission,false);
                        editor.commit();
                        //  config.imageData=false;
                    }
                    else if(permission.equalsIgnoreCase("android.permission.ACCESS_COARSE_LOCATION"))
                    {
                        editor.putBoolean(Constant.locationPermission,false);
                        editor.commit();
                        //   config.locationData=false;
                    }
                }
            }
        }
    }

   /* public void permission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (!config.checkLocationPermission()) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else if (!config.checkLocationPermission2()) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
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

    public void permission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (!config.checkLocationPermission()) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
           /* else if (!sharedPreferences.getBoolean(Constant.locationPermission,false)) {

                config.permissionAlert();
            }
            else if(!sharedPreferences.getBoolean(Constant.ImagePermission,false))
            {
                config.permissionImageAlert();
            }
            else {
                config.permissiontrue();
            }*/
        }
    }
}
