package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import com.twitter.sdk.android.core.Twitter;

import awayzoneadvertiser.com.advertiser.util.Constant;


/**
 * Created by Devraj on 12/1/2017.
 */

public class SplashActivity extends AppCompatActivity {

    ImageView image;
    Config config;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);
      //  image = (ImageView) findViewById(R.id.image);
      //  Glide.with(this).load(R.drawable.splashb).into(image);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Twitter.initialize(this);
         config = new Config(SplashActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
         if(sharedPreferences.getString(Constant.FULLREG,"").equalsIgnoreCase("YES"))
                {

                    Log.e("RAWDATA","rawdata is called");

                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
               else if(sharedPreferences.getString(Constant.HAFLREG,"").equalsIgnoreCase("YES"))
                {
                    Log.e("RAWDATA","rawdata is ofkjkdkjfk called");
                    Intent intent = new Intent(SplashActivity.this,ProfileStepOneActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Log.e("RAWDATA","rawdata is else is called  called");

                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
}
