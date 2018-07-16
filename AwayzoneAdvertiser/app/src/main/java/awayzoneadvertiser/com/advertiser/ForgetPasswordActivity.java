package awayzoneadvertiser.com.advertiser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Devraj on 12/6/2017.
 */

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView forgetbackbutton;
    RelativeLayout resetpassword;
    Config config;
    ProgressDialog progressDialog;
    EditText emailedittext;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpasswordactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        forgetbackbutton = (ImageView) findViewById(R.id.forgetbackbutton);
        emailedittext = (EditText)findViewById(R.id.emailedittext);
        resetpassword = (RelativeLayout)findViewById(R.id.resetpassword);
        forgetbackbutton.setOnClickListener(this);
        resetpassword.setOnClickListener(this);
        config = new Config(ForgetPasswordActivity.this);
        progressDialog = new ProgressDialog(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.forgetbackbutton:
                onBackPressed();
                break;
            case R.id.resetpassword:

                if(emailedittext.getText().toString().equalsIgnoreCase(""))
                {
                    emailedittext.setError("Email is required");
                }
                else if(!isValidEmail(emailedittext.getText().toString()))
                {
                    emailedittext.setError("Enter a valid Email");
                }
                else
                {
                    webervice();
                }
        //        webervice();
                break;
        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public void webervice()
    {
        progressDialog.show();
        RequestInterface requestInterface= config.retrofitRegister();
        Call<HalfRegisterationResponse> call = requestInterface.forgetpassword(emailedittext.getText().toString());
        call.enqueue(new retrofit2.Callback<HalfRegisterationResponse>() {
            @Override
            public void onResponse(Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {
                if(response.body().getStatus().equalsIgnoreCase("1"))
                {
                    Toast.makeText(ForgetPasswordActivity.this,"Password sent to your Email", Toast.LENGTH_LONG).show();
                    progressDialog.hide();

                    Intent intent = new Intent(ForgetPasswordActivity.this,MainActivity.class);
                    startActivity(intent);
                    finishAffinity();


                }
                else if(response.body().getStatus().equalsIgnoreCase("0"))
                {
                    Toast.makeText(ForgetPasswordActivity.this,"No Account Found", Toast.LENGTH_LONG).show();
                    sharedPreferences.getString(Constant.USERID,response.body().getUserId()+"");
                    progressDialog.hide();
                }
            }
            @Override
            public void onFailure(Call<HalfRegisterationResponse> call, Throwable t) {
                         progressDialog.hide();
                //  webservice();
            }
        });
    }
}
