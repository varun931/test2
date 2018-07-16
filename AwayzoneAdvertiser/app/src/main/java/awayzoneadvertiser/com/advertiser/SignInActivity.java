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

import com.facebook.AccessToken;
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

import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.LoginResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Devraj on 12/1/2017.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    TextView forgetpassword,showPass;
    EditText passwordtxt,emailedittext;
    RelativeLayout signinbtn;
   // ProgressDialog progressDoalog;
    ProgressDialogClass progressDialogClass;
    Config config;
    LoginButton fbloginButton;
    CallbackManager callbackManager;
    TwitterLoginButton loginButton;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinactivity);
        callbackManager = CallbackManager.Factory.create();
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        forgetpassword = (TextView)findViewById(R.id.forgetpassword);
        fbloginButton =(LoginButton)findViewById(R.id.fblogin_button);
        showPass = (TextView)findViewById(R.id.showPass);
        loginButton = (TwitterLoginButton)findViewById(R.id.loginbutton);
        signinbtn = (RelativeLayout)findViewById(R.id.signinbtn);
        passwordtxt = (EditText) findViewById(R.id.passwordtxt);
        emailedittext = (EditText) findViewById(R.id.emailedittext);
        forgetpassword.setOnClickListener(this);
        showPass.setOnClickListener(this);
        signinbtn.setOnClickListener(this);
        progressDialogClass = new ProgressDialogClass(this);
        config = new Config(SignInActivity.this);

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
                        long      id = userResult.data.id;
                        // _normal (48x48px) | _bigger (73x73px) | _mini (24x24px)
                        String photoUrlNormalSize   = userResult.data.profileImageUrl;
                        editor.putString(Constant.TWEMAIL,email);
                        editor.putString(Constant.TWNAME,name);
                        editor.putString(Constant.TWIMAGEURL,photoUrlNormalSize);
                        editor.commit();
                        webervice(id+"",id+"", "twitter");
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null) {
        }
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
            webervice(profile.getId()+"",profile.getId()+"","facebook");
        } else {
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.forgetpassword:
                Intent intent = new Intent(SignInActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.showPass:
                if(showPass.getText().equals(getResources().getString(R.string.eye)))
                {
                    showPass.setText(getResources().getString(R.string.eyeblock));
                    passwordtxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else if(showPass.getText().equals(getResources().getString(R.string.eyeblock)))
                {
                    showPass.setText(getResources().getString(R.string.eye));
                    passwordtxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.signinbtn:
                if(emailedittext.getText().toString().equalsIgnoreCase(""))
                {
                    emailedittext.setError("Email is required");
                }
                else if(!config.isValidEmail(emailedittext.getText().toString()))
                {
                    emailedittext.setError("Enter a valid Email");
                }
                else if(passwordtxt.getText().toString().equalsIgnoreCase(""))
                {
                    passwordtxt.setError("Password is required");
                }
                else
                {
                    webervice(emailedittext.getText().toString(),passwordtxt.getText().toString(),"email");
                }
                break;
        }
    }
    public void webervice(String email, String password, final String type)
    {
        if (!isNetworkAvailable()) {
            Toast.makeText(SignInActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        }
        else {
            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();
            Call<LoginResponse> call = requestInterface.login(email, password, sharedPreferences.getString(Constant.DEVICEID,""), type);
            call.enqueue(new retrofit2.Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    progressDialogClass.hideDialog();
                    editor.putString(Constant.USERTYPE, type);
                    if (response.body().getStatus().equalsIgnoreCase("3"))
                    {
                        Toast.makeText(SignInActivity.this, "You are already registered in Awayzone app", Toast.LENGTH_LONG).show();
                    }
                    else if (response.body().getStatus().equalsIgnoreCase("0")) {
                        editor.putString(Constant.REGPROFILEIMAGE, response.body().getImage());
                        editor.putString(Constant.REGPROFILEIMAGEURL, response.body().getImageUrl());
                        editor.putInt(Constant.USERID, response.body().getUserId());
                        editor.putString(Constant.REGUSERNAME, response.body().getFirstName());
                        editor.commit();

                        if (type.equalsIgnoreCase("twitter") || type.equalsIgnoreCase("facebook")) {
                            editor.putString(Constant.HAFLREGTYPE, type);
                            editor.putString(Constant.HAFLREG, "YES");
                            editor.commit();
                            Intent intent = new Intent(SignInActivity.this, ProfileStepOneActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, "Email and Password does not match", Toast.LENGTH_LONG).show();
                        }
                    } else if (response.body().getStatus().equalsIgnoreCase("1")) {
                        //   Toast.makeText(SignInActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                        editor.putString(Constant.REGPROFILEIMAGE, response.body().getImage());
                        editor.putString(Constant.REGPROFILEIMAGEURL, response.body().getImageUrl());
                        editor.putInt(Constant.USERID, response.body().getUserId());
                        editor.putString(Constant.REGUSERNAME, response.body().getFirstName());
                        editor.commit();

                        if (response.body().getFullReg() == 1) {
                            editor.putString(Constant.FULLREG, "YES");
                            editor.commit();

                          Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            editor.putString(Constant.HAFLREGTYPE, type);
                            editor.putString(Constant.HAFLREG, "YES");
                            editor.commit();

                            if (type.equalsIgnoreCase("facebook")) {
                                editor.putString(Constant.FBUSERNAME, response.body().getFirstName());
                                editor.putString(Constant.FBEMAIL, response.body().getEmail());
                                editor.commit();

                            } else if (type.equalsIgnoreCase("twitter")) {
                                editor.putString(Constant.TWNAME, response.body().getFirstName());
                                editor.putString(Constant.TWEMAIL, response.body().getEmail());
                                editor.commit();

                            } else {
                                editor.putString(Constant.REGEMAIL, response.body().getEmail());
                                editor.putString(Constant.REGUSERNAME, response.body().getEmail());
                                editor.commit();

                            }
                            Intent intent = new Intent(SignInActivity.this, ProfileStepOneActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    progressDialogClass.hideDialog();
                    //  webservice();
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
