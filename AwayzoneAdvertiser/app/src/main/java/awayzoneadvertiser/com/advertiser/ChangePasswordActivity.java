package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 12/26/2017.
 */

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout changepasswordbackarrowlayout;
    Config config;
    EditText oldpasswordedittxt,newpasswordedittxt,reconfirmpasswordedittxt;
    TextView oldeyeicon,neweyeicon,reeyeicon,changepasswordbtn;
    ProgressDialogClass progressDialogClass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepasswordactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        config = new Config(ChangePasswordActivity.this);
        progressDialogClass = new ProgressDialogClass(this);
        changepasswordbackarrowlayout = (RelativeLayout)findViewById(R.id.changepasswordbackarrowlayout);
        oldpasswordedittxt = (EditText)findViewById(R.id.oldpasswordedittxt);
        newpasswordedittxt = (EditText)findViewById(R.id.newpasswordedittxt);
        reconfirmpasswordedittxt = (EditText)findViewById(R.id.reconfirmpasswordedittxt);
        oldeyeicon = (TextView) findViewById(R.id.oldeyeicon);
        neweyeicon = (TextView) findViewById(R.id.neweyeicon);
        reeyeicon = (TextView) findViewById(R.id.reeyeicon);
        changepasswordbtn = (TextView) findViewById(R.id.changepasswordbtn);
        changepasswordbackarrowlayout.setOnClickListener(this);
        oldeyeicon.setOnClickListener(this);
        neweyeicon.setOnClickListener(this);
        reeyeicon.setOnClickListener(this);
        changepasswordbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.changepasswordbackarrowlayout:
                onBackPressed();
                break;
            case R.id.oldeyeicon:
                config.showhidepassword(oldeyeicon,oldpasswordedittxt);
                 break;
            case R.id.neweyeicon:
                config.showhidepassword(neweyeicon,newpasswordedittxt);
                break;
            case R.id.reeyeicon:
                config.showhidepassword(reeyeicon,reconfirmpasswordedittxt);
                break;
            case R.id.changepasswordbtn:
           if(oldpasswordedittxt.getText().toString().equalsIgnoreCase(""))
             {
               oldpasswordedittxt.setError("Password is required");
            }
           if(newpasswordedittxt.getText().toString().equalsIgnoreCase(""))
            {
                newpasswordedittxt.setError("Password is required");
            }
            else if(newpasswordedittxt.getText().toString().length()<8)
            {
                newpasswordedittxt.setError("Minimum 8 character is required");
            }
            else if(reconfirmpasswordedittxt.getText().toString().equalsIgnoreCase(""))
            {
                reconfirmpasswordedittxt.setError("Password does not match");
            }
            else if(!reconfirmpasswordedittxt.getText().toString().equalsIgnoreCase(newpasswordedittxt.getText().toString()))
            {
                reconfirmpasswordedittxt.setError("Password does not match");
            }
            else
           {
               updatechangepassword();
           }
                break;
        }

    }
    public void updatechangepassword()
    {
        progressDialogClass.showdialog();
        RequestInterface requestInterface= config.retrofitRegister();
        retrofit2.Call<HalfRegisterationResponse> call = requestInterface.changePassword(sharedPreferences.getInt(Constant.USERID,0),oldpasswordedittxt.getText().toString(),newpasswordedittxt.getText().toString());
        call.enqueue(new Callback<HalfRegisterationResponse>() {
            @Override
            public void onResponse(Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {
                progressDialogClass.hideDialog();
                if(response.body().getStatus().equalsIgnoreCase("0"))
                {
                    Toast.makeText(ChangePasswordActivity.this,"Your old password does not match",Toast.LENGTH_LONG).show();
                }
               else if(response.body().getStatus().equalsIgnoreCase("1"))
                {
                    Intent intent = new Intent(ChangePasswordActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<HalfRegisterationResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
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
