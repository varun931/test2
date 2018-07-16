package awayzoneadvertiser.com.advertiser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaypalDemoActivity extends AppCompatActivity {
    String TAG=getClass().getSimpleName();
    PayPalConfiguration payPalConfiguration;
    int Paypal_Request_Code=500;
    int subid,amount,noofads;
    ProgressDialogClass progressDialogClass;
    Config config;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialogClass = new ProgressDialogClass(PaypalDemoActivity.this);
        subid= getIntent().getIntExtra(Constant.PLANDID,0);
        config = new Config(PaypalDemoActivity.this);
        amount= getIntent().getIntExtra(Constant.AMOUNT,0);
        noofads= getIntent().getIntExtra(Constant.NOOFADS,0);

        if(amount==0)
        {
            paymentwebservice("12112222", "");
        }
        else {
            payPalConfiguration = new PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
                    .clientId(Constant.Client_id);
            Intent inten = new Intent(PaypalDemoActivity.this, PayPalService.class);
            inten.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
            startService(inten);
            PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "USD", "Amount", PayPalPayment.PAYMENT_INTENT_ORDER);
            Intent intent = new Intent(PaypalDemoActivity.this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, Paypal_Request_Code);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
            if (requestCode==Paypal_Request_Code){
                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation!=null){
                    try {
//                        loadingDialog.showDialog();
                        String responseData=confirmation.toJSONObject().toString(6);
                        String shortResponse=confirmation.getPayment().toJSONObject().toString();
                        checkDataFromPaypal(responseData,shortResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(PaypalDemoActivity.this,"Payment is failure, Pls try again",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }else if (resultCode==Activity.RESULT_CANCELED){
        }else if (resultCode==PaymentActivity.RESULT_EXTRAS_INVALID){
        }
    }

    public void checkDataFromPaypal(String response,String shortResponse){
        try {
            JSONObject jsonObject=new JSONObject(response);
            String responseData=String.valueOf(jsonObject.get("response"));
            JSONObject jsonObject1=new JSONObject(responseData);
            String state=jsonObject1.getString("state");
            if (state.equalsIgnoreCase("approved")){
                String id= jsonObject1.getString("id");
                String time=jsonObject1.getString("create_time");
                String intent=jsonObject1.getString("intent");


                String clientBundle=String.valueOf(jsonObject.get("client"));
                JSONObject jsonObject2=new JSONObject(clientBundle);
                String platform = jsonObject2.getString("platform");
                String paypal_sdk_version = jsonObject2.getString("paypal_sdk_version");
                String product_name = jsonObject2.getString("product_name");
                String environment = jsonObject2.getString("environment");
                String responseTypeBundle = jsonObject.getString("response_type");
                paymentwebservice(id,time);
            }else {
            }
    } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
          super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       Intent intent = new Intent(PaypalDemoActivity.this,SubscriptionList.class);
        startActivity(intent);
    }


    public void paymentwebservice(String id, String time)
    {
        //ChatListResponse
        if (!isNetworkAvailable()) {
            Toast.makeText(PaypalDemoActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        } else {


            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();
            Call<HalfRegisterationResponse> call = requestInterface.payment(sharedPreferences.getInt(Constant.USERID,0),subid,amount,id,noofads);
            call.enqueue(new Callback<HalfRegisterationResponse>() {
                @Override
                public void onResponse(Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {
                    progressDialogClass.hideDialog();
                    if(response.body().getStatus().equalsIgnoreCase("1"))
                    {
                        Intent intent = new Intent(PaypalDemoActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
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
