package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.login.LoginManager;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/19/2018.
 */

public class CreateNewAddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{


    Spinner spinner;
    List<String> categories;
    ArrayAdapter<String> dataAdapter;
    EditText titleeditext,descriptionedittext;
    TextView sendbtn;
    ProgressDialogClass progressDialogClass;
    Config config;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String selectculture;
    RelativeLayout newbackarrowlayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnewaddactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        spinner = (Spinner) findViewById(R.id.spinner);
        titleeditext = (EditText) findViewById(R.id.titleeditext);
        descriptionedittext = (EditText) findViewById(R.id.descriptionedittext);
        sendbtn = (TextView) findViewById(R.id.sendbtn);
        newbackarrowlayout=(RelativeLayout)findViewById(R.id.newbackarrowlayout);
        progressDialogClass = new ProgressDialogClass(CreateNewAddActivity.this);
        config = new Config(CreateNewAddActivity.this);

        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        categories = new ArrayList<String>();
        categories.add("High");
        categories.add("Medium");
        categories.add("Low");

        dataAdapter = new ArrayAdapter<String>(CreateNewAddActivity.this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        sendbtn.setOnClickListener(this);
        newbackarrowlayout.setOnClickListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String item = adapterView.getItemAtPosition(i).toString();

        // Showing selected spinner item
        //  Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        selectculture = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.sendbtn:
               String input=titleeditext.getText().toString().trim().replaceAll(" ", "");
                int length = input.length();
               String inputdes=descriptionedittext.getText().toString().trim().replaceAll(" ", "");
                int lengthdes = inputdes.length();
                //textview.setText(convert);
             if(titleeditext.getText().toString().equalsIgnoreCase(""))
                {
                    titleeditext.setError("Title is required");
                }
             else if(length>20)
             {
                 titleeditext.setError("Title text is not required more than 20 characters");
             }
           else if(descriptionedittext.getText().toString().equalsIgnoreCase(""))
                {
             descriptionedittext.setError("description is required");
                }
             else if(lengthdes>200)
             {
                 descriptionedittext.setError("description text is not required more than 200 characters");
             }
           else
              {
         webservice();
             }
                break;
            case R.id.newbackarrowlayout:
                onBackPressed();
                break;
        }
    }
    public void webservice()
    {
        if (!isNetworkAvailable()) {
            Toast.makeText(CreateNewAddActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        } else {

            RequestInterface requestInterface = config.retrofitRegister();


            Call<HalfRegisterationResponse> call = requestInterface.createnewticket(sharedPreferences.getInt(Constant.USERID,0),selectculture,titleeditext.getText().toString(),descriptionedittext.getText().toString());
            call.enqueue(new Callback<HalfRegisterationResponse>() {
                @Override
                public void onResponse(Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {

                      progressDialogClass.hideDialog();
                            Intent intent = new Intent(CreateNewAddActivity.this, HomeActivity.class);
                            startActivity(intent);
                }
                @Override
                public void onFailure(Call<HalfRegisterationResponse> call, Throwable t) {
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


}
