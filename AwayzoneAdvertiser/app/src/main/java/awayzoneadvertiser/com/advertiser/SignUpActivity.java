package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Devraj on 12/1/2017.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout signuplayout;
    EditText emailedittext,passwordedittxt,repasswordedittxt;
    TextView peyeicon,reeyeicon;
    boolean valuetrue=false;
    Config config;
    TwitterLoginButton loginButton;
    LoginButton fbloginButton;
    CallbackManager callbackManager;
    ProgressDialogClass progressDialogClass;
   // ProgressDialog progressDoalog;
    String fbID;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        callbackManager = CallbackManager.Factory.create();
        signuplayout = (RelativeLayout) findViewById(R.id.signuplayout);

        fbloginButton =(LoginButton)findViewById(R.id.fblogin_button);
        fbloginButton.setReadPermissions("email");

        emailedittext = (EditText) findViewById(R.id.emailedittext);
        passwordedittxt = (EditText) findViewById(R.id.passwordedittxt);
        repasswordedittxt = (EditText) findViewById(R.id.repasswordedittxt);
        peyeicon = (TextView) findViewById(R.id.peyeicon);
        reeyeicon = (TextView) findViewById(R.id.reeyeicon);
//        progressDoalog = new ProgressDialog(this);
        progressDialogClass = new ProgressDialogClass(this);
        signuplayout.setOnClickListener(this);
        peyeicon.setOnClickListener(this);
        reeyeicon.setOnClickListener(this);
        config = new Config(SignUpActivity.this);
        loginButton = (TwitterLoginButton)findViewById(R.id.loginbutton);


        fbloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                getUserDetails(loginResult);
                //  updateUI();
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                final Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false,true);
                user.enqueue(new com.twitter.sdk.android.core.Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {
                        String name = userResult.data.name;
                        String email = userResult.data.email;
                        long  id = userResult.data.id;
                        String photoUrlNormalSize   = userResult.data.profileImageUrl;
                        editor.putString(Constant.TWEMAIL,email);
                        editor.putString(Constant.TWNAME,name);
                        editor.putString(Constant.TWIMAGEURL,photoUrlNormalSize);
                        editor.commit();
                        webservice(id+"",id+"", "twitter");
                    }
                    @Override
                    public void failure(TwitterException exc) {
                    }
                });
                //   AccountService _AccountService = Twitter.getApiClient(result.data).getAccountService();
            }
            @Override
            public void failure(TwitterException exception) {
                // Do something on failure

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signuplayout:

                if(emailedittext.getText().toString().equalsIgnoreCase(""))
                {
                    emailedittext.setError("Email is required");
                }
                else if(!config.isValidEmail(emailedittext.getText().toString()))
                {
                    emailedittext.setError("Enter a valid Email");
                }
                else if(passwordedittxt.getText().toString().equalsIgnoreCase(""))
                {
                    passwordedittxt.setError("Password is required");
                }
                else if(passwordedittxt.getText().toString().length()<8)
                {
                    passwordedittxt.setError("Minimum 8 character is required");
                }
                else if(repasswordedittxt.getText().toString().equalsIgnoreCase(""))
                {
                    repasswordedittxt.setError("Password does not match");
                }
                else if(!repasswordedittxt.getText().toString().equalsIgnoreCase(passwordedittxt.getText().toString()))
                {
                    repasswordedittxt.setError("Password does not match");
                }
                else {
                    editor.putString(Constant.REGEMAIL,emailedittext.getText().toString());
                    editor.putString(Constant.REGPASSWORD,passwordedittxt.getText().toString());
                    editor.commit();
                    if (!isNetworkAvailable()) {
                        Toast.makeText(SignUpActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                       webservice(emailedittext.getText().toString(),passwordedittxt.getText().toString(),"email");
                    }
                }
                break;
           case R.id.peyeicon:
               showhidepassword(peyeicon,passwordedittxt);
            break;
            case R.id.reeyeicon:
                showhidepassword(reeyeicon,repasswordedittxt);
            break;
        }
    }
    public void showhidepassword(TextView textView,EditText editText)
    {
        if(textView.getText().equals(getResources().getString(R.string.eye)))
        {
            textView.setText(getResources().getString(R.string.eyeblock));
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else if(textView.getText().equals(getResources().getString(R.string.eyeblock)))
        {
            textView.setText(getResources().getString(R.string.eye));
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        json_object.toString();
                        try {
                            editor.putString(Constant.FBUSERNAME,json_object.get("name").toString());
                            editor.putString(Constant.FBEMAIL,json_object.get("email").toString());
                            editor.commit();
                            updateUI();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Intent intent = new Intent(SignUpActivity.this, UserProfile.class);
//                        intent.putExtra("userProfile", json_object.toString());
//                        startActivity(intent);
                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }
    private void updateUI() {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            editor.putString(Constant.FBIMAGEURL,profile.getProfilePictureUri(80,80)+"");
            editor.commit();
            webservice(profile.getId()+"",profile.getId()+"","facebook");
        } else {
        }
    }
    public void webservice(String email, String password, final String type)
    {
        progressDialogClass.showdialog();
        // final Config config = new Config(SignUpActivity.this);
        RequestInterface requestInterface= config.retrofitRegister();
        Call<HalfRegisterationResponse> call = requestInterface.halfReg(email,password,sharedPreferences.getString(Constant.DEVICEID,""),type);
        call.enqueue(new retrofit2.Callback<HalfRegisterationResponse>() {
            @Override
            public void onResponse(Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {

                editor.putString(Constant.USERTYPE,type);
                progressDialogClass.hideDialog();
                if (response.body().getStatus().equalsIgnoreCase("3"))
                {
                    Toast.makeText(SignUpActivity.this, "You are already registered in Awayzone app", Toast.LENGTH_LONG).show();
                }
               else if(response.body().getStatus().equalsIgnoreCase("1"))
                {
                    editor.putString(Constant.HAFLREG,"YES");
                    editor.putString(Constant.REGPROFILEIMAGE,response.body().getImage());
                    editor.putString(Constant.REGPROFILEIMAGEURL,response.body().getImageUrl());
                    editor.putInt(Constant.USERID,response.body().getUserId());
                    editor.putString(Constant.REGUSERNAME,response.body().getFirstName());
                    editor.putString(Constant.HAFLREGTYPE,type);
                    editor.commit();
                    // config.dwonloadimage();
                   /* Intent intent = new Intent(activity,ProfileStepOneActivity.class);
                    intent.putExtra(Constant.HAFLREGTYPE,type);
                    activity.startActivity(intent);*/
                    if(type.equalsIgnoreCase("twitter")||type.equalsIgnoreCase("facebook")) {
                        if (response.body().getFullReg()== 1) {
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //  Toast.makeText(activity, "Registration Successfully", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(SignUpActivity.this, ProfileStepOneActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this,"User is already Exist",Toast.LENGTH_LONG).show();
                    }
                }
                else if(response.body().getStatus().equalsIgnoreCase("0"))
                {
                    editor.putString(Constant.HAFLREG,"YES");
                    editor.putString(Constant.HAFLREGTYPE,type);
                    editor.putString(Constant.REGPROFILEIMAGE,response.body().getImage());
                    editor.putString(Constant.REGPROFILEIMAGEURL,response.body().getImageUrl());
                    editor.putInt(Constant.USERID,response.body().getUserId());
                    editor.putString(Constant.REGPROFILEIMAGEURL,response.body().getImageUrl());
                    editor.putString(Constant.REGUSERNAME,response.body().getFirstName());
                    editor.commit();
                    Toast.makeText(SignUpActivity.this,"Registration Successfully",Toast.LENGTH_LONG).show();
                    if(response.body().getFullReg()==1)
                    {
                        Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this,"Registration Successfully",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this,ProfileStepOneActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<HalfRegisterationResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
                //  webservice();
            }
        });
    }
}
