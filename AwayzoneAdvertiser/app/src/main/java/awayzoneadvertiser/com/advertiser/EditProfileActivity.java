package awayzoneadvertiser.com.advertiser;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import com.triusinfotech.awayzoneadvertiser.util.PlaceArrayAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import awayzoneadvertiser.com.advertiser.adapter.CountryAdapterList;
import awayzoneadvertiser.com.advertiser.adapter.CultureRecyclerViewAdapter;
import awayzoneadvertiser.com.advertiser.adapter.InterestAdapterList;
import awayzoneadvertiser.com.advertiser.adapter.InterestRecyclerViewAdapter;
import awayzoneadvertiser.com.advertiser.adapter.UpdateCultureRecycleAdapter;
import awayzoneadvertiser.com.advertiser.adapter.UpdateInterestRecycleAdapter;
import awayzoneadvertiser.com.advertiser.culturelist.CountryList;
import awayzoneadvertiser.com.advertiser.culturelist.Culture;
import awayzoneadvertiser.com.advertiser.culturelist.FLAGDATA;
import awayzoneadvertiser.com.advertiser.culturelist.FlagList;
import awayzoneadvertiser.com.advertiser.culturelist.ListResponse;
import awayzoneadvertiser.com.advertiser.custominterface.AddRemoveItemInterface;
import awayzoneadvertiser.com.advertiser.custominterface.CultureListInterface;
import awayzoneadvertiser.com.advertiser.custominterface.IntereestListInterface;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.CultureDataResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.CulturePojo;
import awayzoneadvertiser.com.advertiser.gettersetter.EditProfileResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.InterestDataResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.ServerResponse;
import awayzoneadvertiser.com.advertiser.interestlist.Interest;
import awayzoneadvertiser.com.advertiser.interestlist.InterestListResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.CustomViewPager;
import awayzoneadvertiser.com.advertiser.util.GpsTracker;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/16/2018.
 */

public class EditProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, View.OnClickListener,ActivityCompat.OnRequestPermissionsResultCallback,AdapterView.OnItemSelectedListener,AddRemoveItemInterface,CultureListInterface,IntereestListInterface {

    CustomViewPager customviewpager;
    LinearLayout accountdetail,updateinterestlayout,detaillayout;
    RelativeLayout accountdetalbtn,culturebtn,interetbtn,detailsbtn,changedefaultpic;
    LinearLayout updateculturelayout;
 //   ViewPagerAdapter viewPagerAdapter;
    //account
    String name,email,imageurl,imagename,userculture,contactno,organiationname,defultprofileimageurl,userprofiledescription,useralias;
    EditText emailedittext, contactedittext,organiationnameedittext,nameofperonedittext,desccriptionedittext,aliasedittext;
    ImageView imageview,defaultprofileimg;
    AutoCompleteTextView locationtextview;
    String result;

    Config config;

    TextView updateprofile,interesttitletext,seletectedculture,seletectedinterest;
    RelativeLayout editprofilebackarrowlayout;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final int GOOGLE_API_CLIENT_ID = 0;
    float lat,lang;
    ProgressDialogClass progressDialogClass;
    FrameLayout imglayout;
    int GALLERY_CODE = 1;
    int DEFAULT_CODE = 101;
    String pathphotovalidation="";
    String defaultpathphotovalidation="";
    boolean locationvalue=true;
    SharedPreferences sharedPreferences,sharedPreferencess;
    SharedPreferences.Editor editor,editors;
    GpsTracker locationTracker;
    double latitude;
    double longitude;
    double from_lat;
    double from__lng;
    Geocoder geocoder;
  //  Spinner spinner;
    List<String> categories;
    ArrayAdapter<String> dataAdapter;
    boolean userculturevalue;
    String selectculture;
    int position;

    //culture webservice
    RecyclerView userculturerecyclerview,culturerecyclerview;
    List<CulturePojo> list = new ArrayList<>();
    List<CulturePojo> userlist = new ArrayList<>();
    ArrayList<CulturePojo> culturelist= new ArrayList<>();
    ArrayList<CulturePojo> weblist= new ArrayList<>();
    UpdateCultureRecycleAdapter updateCultureRecycleAdapter;
    GridLayoutManager gridLayoutManager,searchgridlayoutmanager;
    Toolbar searchtoolbar;


    CultureRecyclerViewAdapter cultureRecyclerViewAdapter;
    List<String> addCulture = new ArrayList<>();


   // JSONArray roomrules;



    //update interest
    RecyclerView userinteretrecyclerview,interestrecyclerview;

    List<CulturePojo> interestlist = new ArrayList<>();
    List<CulturePojo> interestuserlist = new ArrayList<>();

    List<String> interetaddCulture = new ArrayList<>();
    UpdateInterestRecycleAdapter interestupdateCultureRecycleAdapter;
    GridLayoutManager interestgridLayoutManager,interestsearchgridlayoutmanager;
    Toolbar searchinteresttoolbar;

    TextView updatebtn;
    JSONArray roomrules,roomrulesinterest;
    ArrayList<CulturePojo> interestculturelist= new ArrayList<>();
   // CultureRecyclerViewAdapter interestcultureRecyclerViewAdapter;
   InterestRecyclerViewAdapter interestcultureRecyclerViewAdapter;
    ArrayList<CulturePojo> interestweblist= new ArrayList<>();
    RelativeLayout changepasswordbackarrowlayout;
    boolean manualData=false;





    ExpandableListView expListView;

    CountryAdapterList expListAdapter;

    //List<String> selectedlist = new ArrayList<>();
    List<Integer> mainfinallist = new ArrayList<>();
    List<Integer> interestmainfinallist = new ArrayList<>();




    List<FLAGDATA> flagdata  = new ArrayList<>();

    // ExpandableListAdapter expListAdapter;
    List<String> selectedlist = new ArrayList<>();
    List<FlagList> childList= new ArrayList<>();

    ExpandableListView interestexpandablelist;
    InterestAdapterList interestexpListAdapter;
    //List<String> selectedlist = new ArrayList<>();

    private static List<CountryList> countries;
    private static List<CountryList> interestcountries;
    List<FLAGDATA> interestflagdata  = new ArrayList<>();
    // ExpandableListAdapter expListAdapter;
    List<String> interestselectedlist = new ArrayList<>();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateprofileactivity);
        accountdetail = (LinearLayout) findViewById(R.id.accountdetail);
        updateinterestlayout = (LinearLayout) findViewById(R.id.updateinterestlayout);
        accountdetalbtn = (RelativeLayout) findViewById(R.id.accountdetalbtn);
        detailsbtn = (RelativeLayout) findViewById(R.id.detailsbtn);
        culturebtn = (RelativeLayout) findViewById(R.id.culturebtn);
        interetbtn = (RelativeLayout) findViewById(R.id.interetbtn);
        updateculturelayout = (LinearLayout)findViewById(R.id.updateculturelayout);
        contactedittext = (EditText) findViewById(R.id.contactedittext);
        organiationnameedittext = (EditText) findViewById(R.id.organiationnameedittext);
        nameofperonedittext = (EditText) findViewById(R.id.nameofperonedittext);
        culturebtn.setOnClickListener(this);
        accountdetalbtn.setOnClickListener(this);
        detailsbtn.setOnClickListener(this);
        interetbtn.setOnClickListener(this);

        //account detail

        config = new Config(EditProfileActivity.this);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        sharedPreferencess = getSharedPreferences(Constant.sharedpreffilenames, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editors = sharedPreferencess.edit();
        progressDialogClass= new ProgressDialogClass(EditProfileActivity.this);
        name = getIntent().getStringExtra(Constant.PROFILEUSERNNAME);
        email = getIntent().getStringExtra(Constant.PROFILEUSEREMAIL);
        imageurl = getIntent().getStringExtra(Constant.PROFILEIMAGEURL);
        imagename = getIntent().getStringExtra(Constant.PROFILEIMAGENAME);
        userculture = getIntent().getStringExtra(Constant.PROFILEUSERCULTRURE);
        userculture = getIntent().getStringExtra(Constant.PROFILEUSERCULTRURE);

        contactno = getIntent().getStringExtra(Constant.PROFILECONTACTNO);
        organiationname = getIntent().getStringExtra(Constant.PROFILEORGANISATIONNAME);
        defultprofileimageurl = getIntent().getStringExtra(Constant.DEFAULTPROFILEIMAGE);
        userprofiledescription = getIntent().getStringExtra(Constant.PROFILEUSERDESCRIPTION);
        useralias =getIntent().getStringExtra(Constant.PROFILEUSERALIAS);


        interesttitletext = (TextView)findViewById(R.id.interesttitletext);
        updateprofile = (TextView)findViewById(R.id.updateprofile);
        lat = getIntent().getFloatExtra("lat",000);
        lang = getIntent().getFloatExtra("long",000);





        contactedittext.setText(contactno);
        organiationnameedittext.setText(organiationname);
        nameofperonedittext.setText(name);

        imglayout = (FrameLayout)findViewById(R.id.imglayout);
        emailedittext = (EditText)findViewById(R.id.emailedittext);
        imageview = (ImageView) findViewById(R.id.imageview);
        seletectedculture = (TextView) findViewById(R.id.seletectedculture);
        seletectedinterest = (TextView) findViewById(R.id.seletectedinterest);
        editprofilebackarrowlayout = (RelativeLayout) findViewById(R.id.editprofilebackarrowlayout);
        locationtextview = (AutoCompleteTextView)findViewById(R.id.locationtextview);
        nameofperonedittext.setText(name);
        emailedittext.setText(email);
        //   Glide.with(this).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(imageview);
        if(imagename.equalsIgnoreCase(""))
        {
            Glide.with(this).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(imageview);
        }
        else {
            Glide.with(this).load(imageurl + imagename).apply(RequestOptions.circleCropTransform()).into(imageview);
        }

        geoderclass(lat,lang);
        locationtextview.setThreshold(3);
        mGoogleApiClient = new GoogleApiClient.Builder(EditProfileActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        locationtextview.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        locationtextview.setAdapter(mPlaceArrayAdapter);

        updateprofile.setOnClickListener(this);
        imglayout.setOnClickListener(this);
        editprofilebackarrowlayout.setOnClickListener(this);

   //     spinner = (Spinner) findViewById(R.id.spinner);


        //culturewebservice();

//        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        categories = new ArrayList<String>();


        //culture webservice


        expListView = (ExpandableListView) findViewById(R.id.expandablelist);
        culturewebservicedata();
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                expListAdapter.setClicked(groupPosition, childPosition);
                return false;
            }
        });






        userculturerecyclerview = (RecyclerView)findViewById(R.id.userculturerecyclerview);
        culturerecyclerview = (RecyclerView)findViewById(R.id.culturerecyclerview);

        list = (List<CulturePojo>) getIntent().getSerializableExtra("test");

        gridLayoutManager = new GridLayoutManager(this,2);
        userculturerecyclerview.setNestedScrollingEnabled(false);
        userculturerecyclerview.setLayoutManager(gridLayoutManager);

     /*   for(int i=0;i<list.size();i++)
        {
            userlist.add(new CulturePojo(i,list.get(i).getCultureId(),list.get(i).getCulturename(),"NO"));
        }*/




        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i<list.size();i++)
        {
            if(i!=0)
            {
                stringBuilder.append(",");
            }
            stringBuilder.append(list.get(i).getCulturename()+" ");
            userlist.add(new CulturePojo(i,list.get(i).getCultureId(),list.get(i).getCulturename(),"NO"));
        }
        seletectedculture.setText("Selected Culture :"+stringBuilder.toString());






      /*  Log.e(TAG,"userlist size is..."+userlist.size());
        for(int j=0;j<userlist.size();j++)
        {
            addCulture.add(userlist.get(j).getCultureId()+"");
        }*/
        updateCultureRecycleAdapter = new UpdateCultureRecycleAdapter(EditProfileActivity.this,userlist,EditProfileActivity.this);
        userculturerecyclerview.setAdapter(updateCultureRecycleAdapter);
        searchgridlayoutmanager = new GridLayoutManager(this,2);
        culturerecyclerview.setLayoutManager(searchgridlayoutmanager);
        searchtoolbar = (Toolbar) findViewById(R.id.searchtoolbar);

      /*  setSupportActionBar(searchtoolbar);
        getSupportActionBar().setTitle("Search Culture...");*/

        //culturewebservice();

        //update interewst
        userinteretrecyclerview = (RecyclerView)findViewById(R.id.userinteretrecyclerview);
        interestrecyclerview = (RecyclerView)findViewById(R.id.interestrecyclerview);
        interestlist = (List<CulturePojo>) getIntent().getSerializableExtra("test3");
        interestgridLayoutManager = new GridLayoutManager(this,2);
        userinteretrecyclerview.setNestedScrollingEnabled(false);
        userinteretrecyclerview.setLayoutManager(interestgridLayoutManager);

        StringBuilder stringBuilders = new StringBuilder();
        for(int i=0;i<interestlist.size();i++)
        {
            if(i!=0)
            {
                stringBuilders.append(", ");
            }
            stringBuilders.append(interestlist.get(i).getCulturename()+"");
            userlist.add(new CulturePojo(i,interestlist.get(i).getCultureId(),interestlist.get(i).getCulturename(),"NO"));
        }
        seletectedinterest.setText("Selected Interest :"+stringBuilders.toString());


        /*for(int i=0;i<interestlist.size();i++)
        {
            interestuserlist.add(new CulturePojo(i,interestlist.get(i).getCultureId(),interestlist.get(i).getCulturename(),"NO"));
        }
        for(int j=0;j<interestuserlist.size();j++)
        {
            interetaddCulture.add(interestuserlist.get(j).getCultureId()+"");
        }*/

        sharedPreferences.getString(Constant.PROFILECULTURE,"");


        interestupdateCultureRecycleAdapter = new UpdateInterestRecycleAdapter(EditProfileActivity.this,interestuserlist,EditProfileActivity.this);
        userinteretrecyclerview.setAdapter(interestupdateCultureRecycleAdapter);
        interestsearchgridlayoutmanager = new GridLayoutManager(this,2);
        interestrecyclerview.setLayoutManager(interestsearchgridlayoutmanager);
        searchinteresttoolbar = (Toolbar) findViewById(R.id.searchinteresttoolbar);


        interestexpandablelist =(ExpandableListView) findViewById(R.id.interestexpandablelist);
        interestwebservicedata();
        interestexpandablelist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                interestexpListAdapter.setClicked(groupPosition, childPosition);
                return false;
            }
        });
    //    interestwebservice();
        //detail
        detaillayout = (LinearLayout)findViewById(R.id.detaillayout);
        defaultprofileimg = (ImageView)findViewById(R.id.defaultprofileimg);
        desccriptionedittext = (EditText)findViewById(R.id.desccriptionedittext);
        aliasedittext = (EditText)findViewById(R.id.aliasedittext);
        changedefaultpic = (RelativeLayout)findViewById(R.id.changedefaultpic);
        changedefaultpic.setOnClickListener(this);

        desccriptionedittext.setText(userprofiledescription);
        aliasedittext.setText(useralias);

        if(defultprofileimageurl.equalsIgnoreCase(""))
        {
            Glide.with(this).load(R.drawable.sundar).into(defaultprofileimg);
        }
        else {
            Glide.with(this).load(imageurl + defultprofileimageurl).into(defaultprofileimg);
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.accountdetalbtn:
                culturebtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                accountdetalbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.orange));
                interetbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                detailsbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                updateculturelayout.setVisibility(View.GONE);
                updateinterestlayout.setVisibility(View.GONE);
                detaillayout.setVisibility(View.GONE);
                accountdetail.setVisibility(View.VISIBLE);
                break;
                case R.id.detailsbtn:
                culturebtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                    accountdetalbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                detailsbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.orange));
                interetbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                updateculturelayout.setVisibility(View.GONE);
                updateinterestlayout.setVisibility(View.GONE);
                 accountdetail.setVisibility(View.GONE);
                detaillayout.setVisibility(View.VISIBLE);
                break;
            case R.id.culturebtn:
                culturebtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.orange));
                accountdetalbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                interetbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                detailsbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                accountdetail.setVisibility(View.GONE);
                updateculturelayout.setVisibility(View.VISIBLE);
                updateinterestlayout.setVisibility(View.GONE);
                detaillayout.setVisibility(View.GONE);
                setSupportActionBar(searchtoolbar);
                getSupportActionBar().setTitle("Search Culture...");
                manualData=false;

                break;
            case R.id.interetbtn:
                interetbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.orange));
                culturebtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                accountdetalbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                detailsbtn.setBackgroundColor(ContextCompat.getColor(EditProfileActivity.this,R.color.blue));
                accountdetail.setVisibility(View.GONE);
                updateculturelayout.setVisibility(View.GONE);
                detaillayout.setVisibility(View.GONE);
                updateinterestlayout.setVisibility(View.VISIBLE);
                setSupportActionBar(searchinteresttoolbar);
                getSupportActionBar().setTitle("Search Interest...");
                manualData=true;
                break;
            case R.id.updateprofile:
                String input=organiationnameedittext.getText().toString().trim().replaceAll(" ", "");
                int length = input.length();
                String descriptioninput=desccriptionedittext.getText().toString().trim().replaceAll(" ", "");
                int descriptionlength = descriptioninput.length();

                if(input.toString().equalsIgnoreCase(""))
                {
                  //  organiationnameedittext.setError("Organisation name is required");
                    Toast.makeText(EditProfileActivity.this, "Organisation name is required", Toast.LENGTH_LONG).show();
                    //    uploadFile(imagepath);
                }
                else if(emailedittext.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(EditProfileActivity.this, "Email is Required", Toast.LENGTH_LONG).show();

                 //   emailedittext.setError("Email is Required");
                }
                else if(!config.isValidEmail(emailedittext.getText().toString()))
                {
                    Toast.makeText(EditProfileActivity.this, "Enter a valid Email", Toast.LENGTH_LONG).show();
                   // emailedittext.setError("Enter a valid Email");
                }
                else if(locationtextview.getText().toString().equalsIgnoreCase("")||locationtextview.getText().toString().length()<3)
                {
                    Toast.makeText(EditProfileActivity.this, "Location is required", Toast.LENGTH_LONG).show();
                   // locationtextview.setError("Location is required");
                    locationvalue=false;
                }
                else if(!locationvalue)
                {
                    Toast.makeText(EditProfileActivity.this, "select a correct Location", Toast.LENGTH_LONG).show();
                   // locationtextview.setError("select a correct Location");
                }
                else if(sharedPreferences.getString(Constant.PROFILELAT,"").equalsIgnoreCase("")||sharedPreferences.getString(Constant.PROFILELAN,"").equalsIgnoreCase(""))
                {
                    Toast.makeText(EditProfileActivity.this, "Location is required", Toast.LENGTH_LONG).show();
                 //   locationtextview.setError("Location is required");
                }
                else if(descriptionlength>255)
                {
                    desccriptionedittext.setError("description text is not required more than 255 characters");
                }
                else
                {
                    updateculture();
                }
                break;
            case R.id.imglayout:
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Camera permission has not been granted.
                        permission();
                    }
                    else
                    {
                        Intent intentj = new Intent(Intent.ACTION_PICK);
                        intentj.setType("image/*");
                        startActivityForResult(intentj, GALLERY_CODE);
                    }
                }
                else
                {
                    Intent intentk = new Intent(Intent.ACTION_PICK);
                    intentk.setType("image/*");
                    startActivityForResult(intentk, GALLERY_CODE);
                }
                break;

            case R.id.changedefaultpic:
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Camera permission has not been granted.
                        permission();
                    }
                    else
                    {
                        Intent intentj = new Intent(Intent.ACTION_PICK);
                        intentj.setType("image/*");
                        startActivityForResult(intentj, DEFAULT_CODE);
                    }
                }
                else
                {
                    Intent intentk = new Intent(Intent.ACTION_PICK);
                    intentk.setType("image/*");
                    startActivityForResult(intentk, DEFAULT_CODE);
                }
                break;

            case R.id.editprofilebackarrowlayout:
                onBackPressed();
                break;


        }
    }


    public void culturewebservicedata()
    {
        countries = new ArrayList<CountryList>();
        RequestInterface requestInterface= config.retrofitRegister();
        retrofit2.Call<ListResponse> call = requestInterface.getCulturedata();
        call.enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ListResponse> call, Response<ListResponse> response) {
                for(int i=0;i<response.body().getCultureData().size();i++)
                {
              //      groupList.add(response.body().getCultureData().get(i).getName());
                    loadwebchild(response.body().getCultureData().get(i).getCultures(), response.body().getCultureData().get(i).getName());

                  /*  for(int j=0;j<response.body().getCultureData().get(i).getCultures().size();j++)
                    {
                        groupList.add(response.body().getCultureData().get(i).getCultures().get(j).get)
                    }*/
                }
//                expListAdapter = new ExpandableListAdapterDemo(UpdateCultureActivity.this, groupList, laptopCollection,UpdateCultureActivity.this,UpdateCultureActivity.this);
                // expListAdapter = new ExpandableListAdapter(ProfileStepOneActivity.this, groupList, laptopCollection,ProfileStepOneActivity.this);
//                expListView.setAdapter(expListAdapter);
                expListAdapter = new CountryAdapterList(EditProfileActivity.this, countries,EditProfileActivity.this);
                expListView.setAdapter(expListAdapter);
                expListAdapter.setChoiceMode(CountryAdapterList.CHOICE_MODE_MULTIPLE);

            }
            @Override
            public void onFailure(retrofit2.Call<ListResponse> call, Throwable t) {
            }
        });
    }

    private void loadwebchild(List<Culture> laptopModels, String name) {
        childList = new ArrayList<FlagList>();
        List<String> list = new ArrayList<>();

        // flagdata = new ArrayList<>();

        for(int i=0;i<laptopModels.size();i++)
        {
            //              flagdata = new ArrayList<>();
            FLAGDATA fllll = new FLAGDATA(laptopModels.get(i).getCulName(),laptopModels.get(i).getFlagImage(),i,laptopModels.get(i).getId(),0);
            flagdata.add(fllll);
//          /  childList.add(laptopModels.get(i).getCulName());
            childList.add(new FlagList(laptopModels.get(i).getCulName(),laptopModels.get(i).getFlagImage(),0,flagdata));

            list.add(laptopModels.get(i).getCulName());
        }
        countries.add(new CountryList(name, list));
       // laptopCollection.put(name, childList);
       /* for (String model : laptopModels)
            childList.add(model);*/
    }

    // curren location
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            locationvalue=true;
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };
    //   curren location
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);

            editor.putString(Constant.CITYNAME,place.getName()+"");
            editor.commit();

            CharSequence attributions = places.getAttributions();
            LatLng latlng= place.getLatLng();
            double latitude=latlng.latitude;
            double longitude=latlng.longitude;
            editor.putString(Constant.PROFILELAN,longitude+"");
            editor.putString(Constant.PROFILELAT,latitude+"");
            editor.commit();
            //       locationtextview.setText(place.getAddress()+"");
            //     mNameView.setText(Html.fromHtml(place.getAddress() + ""));
        }
    };
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }
    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(String permission: permissions){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                if(permission.equalsIgnoreCase("android.permission.ACCESS_COARSE_LOCATION"))
                {
                    editors.putString(Constant.READ_EXTERNAL_STORAGE,"NO");
                    editors.commit();
                }
                else if(permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE"))
                {
                    editors.putString(Constant.ACCESS_COARSE_LOCATION,"NO");
                    editors.commit();
                }
            }else{
                if(ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
                    if(permission.equalsIgnoreCase("android.permission.ACCESS_COARSE_LOCATION"))
                    {
                        editors.putString(Constant.READ_EXTERNAL_STORAGE,"YES");
                        editors.commit();
                    }
                    else if(permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE"))
                    {
                        editors.putString(Constant.ACCESS_COARSE_LOCATION,"YES");
                        editors.commit();
                    }
                    //      showAddressData();
                } else{
                    if(permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE"))
                    {
                        config.permissionImageAlert();
                        //  config.imageData=false;
                    }
                    /*else if(permission.equalsIgnoreCase("android.permission.ACCESS_COARSE_LOCATION"))
                    {
                        editors.putBoolean(Constant.locationPermission,false);
                        editors.commit();
                        //   config.locationData=false;
                    }*/
                }
            }
        }
    }

  /*  public void updateProfile()
    {
        progressDialogClass.showdialog();
        RequestInterface requestInterface= config.retrofitRegister();

        //selectculture
        retrofit2.Call<HalfRegisterationResponse> call =requestInterface.editProfile(sharedPreferences.getInt(Constant.USERID,0),emailedittext.getText().toString(),usernameedittxt.getText().toString(),sharedPreferences.getString(Constant.PROFILELAT,""),sharedPreferences.getString(Constant.PROFILELAN,""));
        call.enqueue(new Callback<HalfRegisterationResponse>() {
            @Override
            public void onResponse(retrofit2.Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {
                progressDialogClass.hideDialog();
                if(response.body().getStatus().equalsIgnoreCase("1"))
                {
                    Intent intent = new Intent(EditProfileActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {

                }
            }
            @Override
            public void onFailure(retrofit2.Call<HalfRegisterationResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String path = "";
                path = getRealPathFromURI(uri);
                pathphotovalidation = path;
                imageview.setPadding(0,0,0,0);
                Glide.with(getApplicationContext()).load(pathphotovalidation).apply(RequestOptions.circleCropTransform()).into(imageview);
                uploadFile(pathphotovalidation);
            }
           else  if (requestCode == DEFAULT_CODE && resultCode == RESULT_OK) {
                {
                    Uri uri = data.getData();
                    String path = "";
                    path = getRealPathFromURI(uri);
                    defaultpathphotovalidation = path;
                    defaultprofileimg.setPadding(0,0,0,0);
                    Glide.with(getApplicationContext()).load(defaultpathphotovalidation).into(defaultprofileimg);
                    uploaddefaultFile(defaultpathphotovalidation);
                }
        }
    }
    // image path
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor =this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    // Uploading Image/Video
    public  void uploadFile(String path) {
        //  progressDialog.show();
        // Map is used to multipart the file using okhttp3.RequestBody
        progressDialogClass.showdialog();
        File file = new File(path);
        // File file = new File(config.getSharedPref(Constant.FBIMAGEURL));
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestInterface requestInterface = config.retrofitRegister();
        // Toast.makeText(ProfileStepOneActivity.this,"image upload is..."+fileToUpload.toString()+"....filename..."+filename.toString()+"file name is.."+file.getName(),Toast.LENGTH_LONG).show();
        //config.getSharedIntPref(Constant.USERID)
        Call<ServerResponse> call = requestInterface.uploadFile(fileToUpload, filename,sharedPreferences.getInt(Constant.USERID,0));
      //  Call<ServerResponse> call = requestInterface.uploadFile(fileToUpload, filename,sharedPreferences.getInt(Constant.USERID,0));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                progressDialogClass.hideDialog();
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        editor.putString(Constant.REGPROFILEIMAGE,response.body().getImage());
                        editor.putString(Constant.REGPROFILEIMAGEURL,response.body().getImage_url());
                        editor.commit();
                    } else {
                        //  Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                 }
                // progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
                // Toast.makeText(getApplicationContext(),"failure is.."+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void permission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (!config.checkLocationPermission()) {

                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else if (!config.checkLocationPermission2()) {

                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            else if (!sharedPreferencess.getBoolean(Constant.locationPermission,false)) {

                config.permissionAlert();
            }
            else if(!sharedPreferencess.getBoolean(Constant.ImagePermission,false))
            {
                config.permissionImageAlert();
            }
            else {
                config.permissiontrue();
            }
        }
    }

    public void showAddressData() {
        locationTracker = new GpsTracker(EditProfileActivity.this);
        latitude = locationTracker.getLatitude();
        longitude = locationTracker.getLongitude();
        from_lat = latitude;
        from__lng=latitude;
        if (latitude!=0.0 ||longitude!=0.0) {
//            saveSharedPref(Constant.PROFILELAT,latitude+"");
//            saveSharedPref(Constant.PROFILELAN,longitude+"");
            geoderclass(latitude,longitude);
        }
    }
    public void geoderclass(double lat, double lang)
    {
        // latitude=30.7333; longitude=76.7794;
        editor.putString(Constant.PROFILELAT,lat+"");
        editor.putString(Constant.PROFILELAN,lang+"");
        editor.commit();
        geocoder = new Geocoder(EditProfileActivity.this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    lat, lang, 1);
            Address address = addressList.get(0);
            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
//                sb.append(address.getAddressLine(i)).append("\n");
//            }
//            sb.append(address.getLocality()).append("\n");
//            sb.append(address.getPostalCode()).append("\n");
//           sb.append(address.getCountryName());
            sb.append(address.getSubAdminArea()).append("\n");
            sb.append(address.getSubLocality()).append("\n");
            sb.append(address.getAdminArea()).append("\n");
//            sb.append(address.getFeatureName()).append("\n");
            editor.putString(Constant.CITYNAME,address.getAdminArea());
            editor.commit();
            locationtextview.setText(address.getSubAdminArea());
            //   result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    public void culturewebservice()
    {
        progressDialogClass.showdialog();
        RequestInterface requestInterface= config.retrofitRegister();
        retrofit2.Call<CultureDataResponse> call = requestInterface.getCulture();
        call.enqueue(new Callback<CultureDataResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CultureDataResponse> call, Response<CultureDataResponse> response) {
                progressDialogClass.hideDialog();
                //    categories.add("Select a Culture");

                for(int i=0;i<response.body().getCultureData().size();i++)
                {
                    categories.add(response.body().getCultureData().get(i).getTagName());

                    if(response.body().getCultureData().get(i).getTagName().equalsIgnoreCase(userculture))
                    {
                        position = i;
                    }

                }
                userculturevalue=true;
                dataAdapter = new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_spinner_item, categories);
                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // attaching data adapter to spinner
               // spinner.setAdapter(dataAdapter);
            }
            @Override
            public void onFailure(retrofit2.Call<CultureDataResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }*/

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item;
        if(userculturevalue)
        {
         //   spinner.setSelection(position);
            item = adapterView.getItemAtPosition(position).toString();
            userculturevalue=false;
        }
        else
        {
            item = adapterView.getItemAtPosition(i).toString();
        }

        // adapterView.getItemAtPosition(i).toString();
        //      spinner.setSelection(2);

        // Showing selected spinner item
        //  Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        selectculture = item;
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //culture webservice
    public void culturewebservice()
    {
        progressDialogClass.showdialog();
        RequestInterface requestInterface= config.retrofitRegister();
        retrofit2.Call<CultureDataResponse> call = requestInterface.getCulture();
        call.enqueue(new Callback<CultureDataResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CultureDataResponse> call, Response<CultureDataResponse> response) {
                progressDialogClass.hideDialog();


                for(int i=0;i<response.body().getCultureData().size();i++)
                {
                    weblist.add(new CulturePojo(i,response.body().getCultureData().get(i).getId(),response.body().getCultureData().get(i).getTagName(),"NO"));
                }
                for(int j=0;j<userlist.size();j++)
                {

                    for(int k=0;k<weblist.size();k++) {
                        if (userlist.get(j).getCulturename().equalsIgnoreCase(weblist.get(k).getCulturename())) {
                            weblist.remove(k);
                        }
                    }
                }
                for(int m=0;m<weblist.size();m++)
                {
                    culturelist.add(new CulturePojo(m,weblist.get(m).getCultureId(),weblist.get(m).getCulturename(),"NO"));
                }
                cultureRecyclerViewAdapter = new CultureRecyclerViewAdapter(EditProfileActivity.this,culturelist,EditProfileActivity.this);
                culturerecyclerview.setAdapter(cultureRecyclerViewAdapter);
            }
            @Override
            public void onFailure(retrofit2.Call<CultureDataResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }

    @Override
    public void addremoveitemId(int id, boolean value,boolean contentvalue) {

        if(contentvalue)
        {

            /*RecyclerView userinteretrecyclerview,interestrecyclerview;
            List<CulturePojo> interestlist = new ArrayList<>();
            List<CulturePojo> interestuserlist = new ArrayList<>();

            List<String> interetaddCulture = new ArrayList<>();
            UpdateInterestRecycleAdapter interestupdateCultureRecycleAdapter;
            GridLayoutManager interestgridLayoutManager,interestsearchgridlayoutmanager;
            Toolbar searchinteresttoolbar;

            TextView updatebtn;
            JSONArray roomrules;
            ArrayList<CulturePojo> interestculturelist= new ArrayList<>();
            // CultureRecyclerViewAdapter interestcultureRecyclerViewAdapter;
            InterestRecyclerViewAdapter interestcultureRecyclerViewAdapter;
            ArrayList<CulturePojo> interestweblist= new ArrayList<>();
            RelativeLayout changepasswordbackarrowlayout;

            boolean manualData=false; */

            if(value)
            {
                for(int i=0;i<interestculturelist.size();i++)
                {
                    if(id==interestculturelist.get(i).getId())
                    {
                        interetaddCulture.add(interestculturelist.get(i).getCultureId()+"");
                        interestculturelist.set(id,new CulturePojo(id,interestculturelist.get(i).getCultureId(),interestculturelist.get(i).getCulturename(),"YES"));
//                    cultureRecyclerViewAdapter.notifyDataSetChanged();
                        interestcultureRecyclerViewAdapter.notifyItemChanged(id);
                    }
                }
            }
            else
            {
                for(int i=0;i<interestculturelist.size();i++)
                {
                    if(id==interestculturelist.get(i).getId())
                    {
                        interetaddCulture.remove(interestculturelist.get(i).getCultureId()+"");
                        interestculturelist.set(id,new CulturePojo(id,interestculturelist.get(i).getCultureId(),interestculturelist.get(i).getCulturename(),"NO"));
                        //  cultureRecyclerViewAdapter.notifyDataSetChanged();
                        interestcultureRecyclerViewAdapter.notifyItemChanged(id);
                        // adapter.notifyItemChanged(0)
                    }
                }
            }
        }
        else {
            if (value) {
                for (int i = 0; i < culturelist.size(); i++) {
                    if (id == culturelist.get(i).getId()) {
                        addCulture.add(culturelist.get(i).getCultureId() + "");
                        culturelist.set(id, new CulturePojo(id, culturelist.get(i).getCultureId(), culturelist.get(i).getCulturename(), "YES"));
//                    cultureRecyclerViewAdapter.notifyDataSetChanged();
                        cultureRecyclerViewAdapter.notifyItemChanged(id);
                    }
                }
            } else {
                for (int i = 0; i < culturelist.size(); i++) {
                    if (id == culturelist.get(i).getId()) {
                        addCulture.remove(culturelist.get(i).getCultureId() + "");
                        culturelist.set(id, new CulturePojo(id, culturelist.get(i).getCultureId(), culturelist.get(i).getCulturename(), "NO"));
                        //  cultureRecyclerViewAdapter.notifyDataSetChanged();
                        cultureRecyclerViewAdapter.notifyItemChanged(id);

                        // adapter.notifyItemChanged(0)
                    }
                }
            }
        }
    }

    @Override
    public void updateaddremoveitemId(int position,int id, boolean value,boolean itemvalue) {
        if(itemvalue) {
            if (userlist.size() == 1) {
                addCulture.remove(userlist.get(0).getCultureId() + "");
                userlist.remove(position);
                updateCultureRecycleAdapter.notifyItemRemoved(position);
            } else {
                for (int i = 0; i < userlist.size(); i++) {
                    if (id == userlist.get(i).getCultureId()) {
                        addCulture.remove(userlist.get(i).getCultureId() + "");
                        userlist.remove(position);
                        updateCultureRecycleAdapter.notifyItemRemoved(position);
                    }
                }
            }
        }
        else
        {
           if(interestuserlist.size()==1)
            {
                interetaddCulture.remove(interestuserlist.get(0).getCultureId() + "");
                interestuserlist.remove(position);
                interestupdateCultureRecycleAdapter.notifyItemRemoved(position);
            }
            else {
                for (int i = 0; i < interestuserlist.size(); i++) {
                    if (id == interestuserlist.get(i).getCultureId()) {
                        interetaddCulture.remove(interestuserlist.get(i).getCultureId() + "");
                        interestuserlist.remove(position);
                        interestupdateCultureRecycleAdapter.notifyItemRemoved(position);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_grey, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
               // if (cultureRecyclerViewAdapter != null) cultureRecyclerViewAdapter.getFilter().filter(newText);
               if(manualData)
                {
                    if (interestcultureRecyclerViewAdapter != null) interestcultureRecyclerViewAdapter.getFilter().filter(newText);
                }
                else
                {
                    if (cultureRecyclerViewAdapter != null) cultureRecyclerViewAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }






    public void interestwebservice()
    {
        progressDialogClass.showdialog();
        RequestInterface requestInterface= config.retrofitRegister();
        retrofit2.Call<InterestDataResponse> call = requestInterface.getInterest( sharedPreferences.getString(Constant.PROFILECULTURE,""));
        call.enqueue(new Callback<InterestDataResponse>() {
            @Override
            public void onResponse(retrofit2.Call<InterestDataResponse> call, Response<InterestDataResponse> response) {
                progressDialogClass.hideDialog();
                for(int i=0;i<response.body().getCultureData().size();i++)
                {
                    interestweblist.add(new CulturePojo(i,response.body().getCultureData().get(i).getId(),response.body().getCultureData().get(i).getSpecificName(),"NO"));
                }
                for(int j=0;j<interestuserlist.size();j++)
                {
                    for(int k=0;k<interestweblist.size();k++) {
                        if (interestuserlist.get(j).getCulturename().equalsIgnoreCase(interestweblist.get(k).getCulturename())) {
                            interestweblist.remove(k);
                        }
                    }
                }
                for(int m=0;m<interestweblist.size();m++)
                {
                    interestculturelist.add(new CulturePojo(m,interestweblist.get(m).getCultureId(),interestweblist.get(m).getCulturename(),"NO"));
                }
                interestcultureRecyclerViewAdapter = new InterestRecyclerViewAdapter(EditProfileActivity.this,interestculturelist,EditProfileActivity.this);
                interestrecyclerview.setAdapter(interestcultureRecyclerViewAdapter);
            }
            @Override
            public void onFailure(retrofit2.Call<InterestDataResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }

    public void selectedData() {
        mainfinallist.clear();
        for (int i = 0; i < flagdata.size(); i++) {
            for (int j = 0; j < selectedlist.size(); j++) {
                if (selectedlist.get(j).equalsIgnoreCase(flagdata.get(i).getFlagname())) {
                    mainfinallist.add(flagdata.get(i).getMainid());
                }
            }
        }
    }


    public void removedata()
    {
        Set<String> uniqueList = new HashSet<String>(selectedlist);
        selectedlist = new ArrayList<String>(uniqueList);
        for(int i=0;i<selectedlist.size();i++)
        {
        }
    }

   public void updateculture()
   {
      /* Log.e("MAINDATA", "final culture name is size..." + addCulture.size());
       for (int i = 0; i < addCulture.size(); i++) {
       }*/
       removedata();
       selectedData();
       if(mainfinallist.size()==0)
       {
           Toast.makeText(EditProfileActivity.this,"Please select atleast one culture",Toast.LENGTH_LONG).show();
       }
       else if(mainfinallist.size()>14)
       {
           Toast.makeText(EditProfileActivity.this,"you can select only fourteen culture",Toast.LENGTH_LONG).show();
       }
       else {


       roomrules = new JSONArray();

       for (int i = 0; i < mainfinallist.size(); i++) {
           JSONObject jsonObject = new JSONObject();
           try {
               jsonObject.put("culture", mainfinallist.get(i));
               roomrules.put(jsonObject);
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
       editor.putString(Constant.PROFILECULTURE, roomrules.toString());
       editor.commit();
//                config.getSharedPref(Constant.PROFILECULTURE);

      /* if (addCulture.size() <= 0) {
           Toast.makeText(EditProfileActivity.this, "Seleact at least one culture", Toast.LENGTH_LONG).show();
       } else {*/


         updateinterest();
       }
   }



    public void interestselectedData() {
        interestmainfinallist.clear();
        for (int i = 0; i < interestflagdata.size(); i++) {
            for (int j = 0; j < interestselectedlist.size(); j++) {
                if (interestselectedlist.get(j).equalsIgnoreCase(interestflagdata.get(i).getFlagname())) {
                    interestmainfinallist.add(interestflagdata.get(i).getMainid());
                }
            }
        }
    }

    public void interestremovedata()
    {
        Set<String> uniqueList = new HashSet<String>(interestselectedlist);
        interestselectedlist = new ArrayList<String>(uniqueList);
        for(int i=0;i<interestselectedlist.size();i++)
        {
        }
    }

   public void updateinterest()
    {
       interestremovedata();
        interestselectedData();

        if(interestmainfinallist.size()==0)
        {
            Toast.makeText(EditProfileActivity.this,"Please select atleast one interest",Toast.LENGTH_LONG).show();
        }
        else if(interestmainfinallist.size()>14)
        {
            Toast.makeText(EditProfileActivity.this,"you can select only fourteen interest",Toast.LENGTH_LONG).show();
        }
        else
        {

        for (int i = 0; i < interetaddCulture.size(); i++) {

        }
        roomrulesinterest = new JSONArray();
        JSONArray roomrulesid = new JSONArray();
        for (int i = 0; i < interestmainfinallist.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("interest", interestmainfinallist.get(i));
                roomrulesinterest.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
     /*   if (interetaddCulture.size() <= 0) {
            Toast.makeText(EditProfileActivity.this, "Seleact at least one Interest", Toast.LENGTH_LONG).show();
        } else {*/

            editprofiledata();
        }
    }


    public void editprofiledata()
    {

       //updatefullReg
        if (!isNetworkAvailable()) {
            Toast.makeText(EditProfileActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        }
        else {
            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();



          /*  contactedittext.setText(contactno);
            organiationnameedittext.setText(organiationname);
            nameofperonedittext.setText(name);*/
          //  Call<HalfRegisterationResponse> call = requestInterface.fullReg(sharedPreferences.getInt(Constant.USERID,0), sharedPreferences.getString(Constant.PROFILENAME,""),sharedPreferences.getString(Constant.NAMEROFORGANISATION,""),sharedPreferences.getString(Constant.CONTACTNUMBER,""),  sharedPreferences.getString(Constant.PROFILEEMAIL,""), sharedPreferences.getString(Constant.PROFILELAT,""), sharedPreferences.getString(Constant.PROFILELAN,""), sharedPreferences.getString(Constant.PROFILECULTURE,""), sharedPreferences.getString(Constant.PROFILEINTEREST,""));
            Call<EditProfileResponse> call = requestInterface.updatefullReg(sharedPreferences.getInt(Constant.USERID,0), nameofperonedittext.getText().toString(),organiationnameedittext.getText().toString(),contactedittext.getText().toString(),  emailedittext.getText().toString(), sharedPreferences.getString(Constant.PROFILELAT,""), sharedPreferences.getString(Constant.PROFILELAN,""), roomrules.toString(), roomrulesinterest.toString(),desccriptionedittext.getText().toString(),aliasedittext.getText().toString());
            call.enqueue(new Callback<EditProfileResponse>() {
                @Override
                public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                    progressDialogClass.hideDialog();
                    //   Toast.makeText(ProfileStepThreeActivity.this,"response is"+response.body().getStatus()+"",Toast.LENGTH_LONG).show();
                    if (response.body().getStatus().equalsIgnoreCase("1")) {

                        Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }
                @Override
                public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                    progressDialogClass.hideDialog();

                    //     Toast.makeText(ProfileStepThreeActivity.this,"failure is the data response is"+"",Toast.LENGTH_LONG).show();
                }
            });
        }
    }





    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    @Override
    public void totallist(List<String> list) {
        selectedlist = list;
    }


    public void interestwebservicedata()
    {
        interestcountries = new ArrayList<CountryList>();
        RequestInterface requestInterface= config.retrofitRegister();
        retrofit2.Call<InterestListResponse> call = requestInterface.getInterest();
        call.enqueue(new Callback<InterestListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<InterestListResponse> call, Response<InterestListResponse> response) {


                for(int i=0;i<response.body().getInterestData().size();i++)
                {
                //    groupList.add(response.body().getInterestData().get(i).getName());
                    loadinterestchild(response.body().getInterestData().get(i).getInterests(), response.body().getInterestData().get(i).getName());
                  /*  for(int j=0;j<response.body().getCultureData().get(i).getCultures().size();j++)
                    {
                        groupList.add(response.body().getCultureData().get(i).getCultures().get(j).get)

                    }*/
                }
//                expListAdapter = new ExpandableInterestAdapter(UpdateInterestActivity.this, groupList, laptopCollection,UpdateInterestActivity.this,UpdateInterestActivity.this);
                // expListAdapter = new ExpandableListAdapter(ProfileStepOneActivity.this, groupList, laptopCollection,ProfileStepOneActivity.this);
//                expListView.setAdapter(expListAdapter);


                interestexpListAdapter = new InterestAdapterList(EditProfileActivity.this, interestcountries,EditProfileActivity.this);
                interestexpandablelist.setAdapter(interestexpListAdapter);
                interestexpListAdapter.setChoiceMode(CountryAdapterList.CHOICE_MODE_MULTIPLE);
            }
            @Override
            public void onFailure(retrofit2.Call<InterestListResponse> call, Throwable t) {
            }
        });
    }



    private void loadinterestchild(List<Interest> laptopModels, String name) {
        childList = new ArrayList<FlagList>();
        List<String> list = new ArrayList<>();
        // flagdata = new ArrayList<>();
        for(int i=0;i<laptopModels.size();i++)
        {
            //              flagdata = new ArrayList<>();
            FLAGDATA fllll = new FLAGDATA(laptopModels.get(i).getName(),"",i,laptopModels.get(i).getId(),0);
            interestflagdata.add(fllll);
//          /  childList.add(laptopModels.get(i).getCulName());
            childList.add(new FlagList(laptopModels.get(i).getName(),"",0,flagdata));
            list.add(laptopModels.get(i).getName());
        }
        interestcountries.add(new CountryList(name, list));
       // laptopCollection.put(name, childList);
       /* for (String model : laptopModels)
            childList.add(model);*/
    }

    @Override
    public void interestllist(List<String> list) {
        interestselectedlist = list;
    }




    public  void uploaddefaultFile(String path) {
        //  progressDialog.show();
        // Map is used to multipart the file using okhttp3.RequestBody
        progressDialogClass.showdialog();
        File file = new File(path);
        // File file = new File(config.getSharedPref(Constant.FBIMAGEURL));
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestInterface requestInterface = config.retrofitRegister();
        // Toast.makeText(ProfileStepOneActivity.this,"image upload is..."+fileToUpload.toString()+"....filename..."+filename.toString()+"file name is.."+file.getName(),Toast.LENGTH_LONG).show();
        //config.getSharedIntPref(Constant.USERID)
        Call<ServerResponse> call = requestInterface.uploaddefaultFile(fileToUpload, filename,sharedPreferences.getInt(Constant.USERID,0));
        //  Call<ServerResponse> call = requestInterface.uploadFile(fileToUpload, filename,sharedPreferences.getInt(Constant.USERID,0));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                progressDialogClass.hideDialog();
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                        editor.putString(Constant.REGPROFILEIMAGE,response.body().getImage());
//                        editor.putString(Constant.REGPROFILEIMAGEURL,response.body().getImage_url());
//                        editor.commit();
                    } else {
                        //  Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                }
                // progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
                // Toast.makeText(getApplicationContext(),"failure is.."+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
