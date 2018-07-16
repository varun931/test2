package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import awayzoneadvertiser.com.advertiser.adapter.CountryAdapterList;
import awayzoneadvertiser.com.advertiser.adapter.CultureRecyclerViewAdapter;
import awayzoneadvertiser.com.advertiser.culturelist.CountryList;
import awayzoneadvertiser.com.advertiser.culturelist.FLAGDATA;
import awayzoneadvertiser.com.advertiser.culturelist.FlagList;
import awayzoneadvertiser.com.advertiser.custominterface.AddRemoveItemInterface;
import awayzoneadvertiser.com.advertiser.custominterface.CultureListInterface;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.CulturePojo;

import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.InterestDataResponse;
import awayzoneadvertiser.com.advertiser.interestlist.Interest;
import awayzoneadvertiser.com.advertiser.interestlist.InterestListResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 12/4/2017.
 */

public class ProfileStepThreeActivity extends AppCompatActivity implements View.OnClickListener,AddRemoveItemInterface,CultureListInterface {

    RecyclerView interestrecyclerview;
    GridLayoutManager gridLayoutManager;
    CultureRecyclerViewAdapter cultureRecyclerViewAdapter;
    ArrayList<CulturePojo> culturelist;
    RelativeLayout profilethirdbackarrowlayout,profilefinisharrowlayout;
    List<String> addCulture = new ArrayList<>();
    Toolbar toolbar;
    ProgressDialogClass progressDialogClass;
    Config config;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<FlagList> childList= new ArrayList<>();

    ExpandableListView expListView;

    CountryAdapterList expListAdapter;

    //List<String> selectedlist = new ArrayList<>();
    List<Integer> mainfinallist = new ArrayList<>();


    private static List<CountryList> countries;

    List<FLAGDATA> flagdata  = new ArrayList<>();

    // ExpandableListAdapter expListAdapter;


    List<String> selectedlist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilestepthreeactivity);

        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        progressDialogClass = new ProgressDialogClass(this);
        profilethirdbackarrowlayout = (RelativeLayout)findViewById(R.id.profilethirdbackarrowlayout);
        profilefinisharrowlayout = (RelativeLayout)findViewById(R.id.profilefinisharrowlayout);
        culturelist = new ArrayList<>();
        interestrecyclerview = (RecyclerView)findViewById(R.id.interestrecyclerview);
        gridLayoutManager = new GridLayoutManager(this,2);
        interestrecyclerview.setLayoutManager(gridLayoutManager);
        config = new Config(ProfileStepThreeActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Interest...");
    //    interestwebservice();
        profilethirdbackarrowlayout.setOnClickListener(this);
        profilefinisharrowlayout.setOnClickListener(this);



        expListView = (ExpandableListView) findViewById(R.id.expandablelist);
        interestwebservicedata();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                expListAdapter.setClicked(groupPosition, childPosition);
                return false;
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


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.profilethirdbackarrowlayout:
                super.onBackPressed();
                break;
            case R.id.profilefinisharrowlayout:


                removedata();
                selectedData();
                if(mainfinallist.size()==0)
                {
                    Toast.makeText(ProfileStepThreeActivity.this,"Please select atleast one interest",Toast.LENGTH_LONG).show();
                }
                else if(mainfinallist.size()>14)
                {
                    Toast.makeText(ProfileStepThreeActivity.this,"you can select only fourteen interest",Toast.LENGTH_LONG).show();
                }
                else {

                JSONArray roomrules = new JSONArray();
                JSONArray roomrulesid = new JSONArray();
                for(int i=0; i<mainfinallist.size();i++)
                {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("interest",mainfinallist.get(i));
                        roomrules.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               editor.putString(Constant.PROFILEINTEREST,roomrules.toString());
                editor.commit();
                sharedPreferences.getString(Constant.PROFILEINTEREST,"");
              /*  if(addCulture.size()<=0) {
                    Toast.makeText(ProfileStepThreeActivity.this,"Seleact atleast one Interest", Toast.LENGTH_LONG).show();
                }
                else
                {*/
                fullRegWebService();

                }
                break;
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
                if (cultureRecyclerViewAdapter != null) cultureRecyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
    public void interestwebservice()
    {
        progressDialogClass.showdialog();
        RequestInterface requestInterface= config.retrofitRegister();
        retrofit2.Call<InterestDataResponse> call = requestInterface.getInterest(sharedPreferences.getString(Constant.PROFILECULTURE,""));
        call.enqueue(new Callback<InterestDataResponse>() {
            @Override
            public void onResponse(retrofit2.Call<InterestDataResponse> call, Response<InterestDataResponse> response) {
                progressDialogClass.hideDialog();
                for(int i=0;i<response.body().getCultureData().size();i++)
                {
                    culturelist.add(new CulturePojo(i,response.body().getCultureData().get(i).getId(),response.body().getCultureData().get(i).getSpecificName(),"NO"));
                }
                cultureRecyclerViewAdapter = new CultureRecyclerViewAdapter(ProfileStepThreeActivity.this,culturelist,ProfileStepThreeActivity.this);
                interestrecyclerview.setAdapter(cultureRecyclerViewAdapter);
            }
            @Override
            public void onFailure(retrofit2.Call<InterestDataResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }
    public void fullRegWebService()
    {
        if (!isNetworkAvailable()) {
            Toast.makeText(ProfileStepThreeActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        }
        else {
            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();



            Call<HalfRegisterationResponse> call = requestInterface.fullReg(sharedPreferences.getInt(Constant.USERID,0), sharedPreferences.getString(Constant.PROFILENAME,""),sharedPreferences.getString(Constant.NAMEROFORGANISATION,""),sharedPreferences.getString(Constant.CONTACTNUMBER,""),  sharedPreferences.getString(Constant.PROFILEEMAIL,""), sharedPreferences.getString(Constant.PROFILELAT,""), sharedPreferences.getString(Constant.PROFILELAN,""), sharedPreferences.getString(Constant.PROFILECULTURE,""), sharedPreferences.getString(Constant.PROFILEINTEREST,""));
            call.enqueue(new Callback<HalfRegisterationResponse>() {
                @Override
                public void onResponse(Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {

                    progressDialogClass.hideDialog();
                    //   Toast.makeText(ProfileStepThreeActivity.this,"response is"+response.body().getStatus()+"",Toast.LENGTH_LONG).show();
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        editor.putString(Constant.REGPROFILEIMAGE, response.body().getImage());
                        editor.putString(Constant.REGPROFILEIMAGEURL, response.body().getImageUrl());
                        editor.putInt(Constant.USERID, response.body().getUserId());
                        editor.putString(Constant.REGUSERNAME, response.body().getFirstName());
                        editor.putString(Constant.FULLREG, "YES");
                        editor.commit();
                       Intent intent = new Intent(ProfileStepThreeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }
                @Override
                public void onFailure(Call<HalfRegisterationResponse> call, Throwable t) {
                    progressDialogClass.hideDialog();
                    //     Toast.makeText(ProfileStepThreeActivity.this,"failure is the data response is"+"",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    @Override
    public void addremoveitemId(int id, boolean value,boolean contentvalue) {
        if(value)
        {
            for(int i=0;i<culturelist.size();i++)
            {
                if(id==culturelist.get(i).getId())
                {
                    addCulture.add(culturelist.get(i).getCultureId()+"");
                    culturelist.set(id,new CulturePojo(id,culturelist.get(i).getCultureId(),culturelist.get(i).getCulturename(),"YES"));
//                    cultureRecyclerViewAdapter.notifyDataSetChanged();
                    cultureRecyclerViewAdapter.notifyItemChanged(id);
                }
            }
        }
        else
        {
            for(int i=0;i<culturelist.size();i++)
            {
                if(id==culturelist.get(i).getId())
                {
                    addCulture.remove(culturelist.get(i).getCultureId()+"");
                    culturelist.set(id,new CulturePojo(id,culturelist.get(i).getCultureId(),culturelist.get(i).getCulturename(),"NO"));
                    cultureRecyclerViewAdapter.notifyItemChanged(id);
                }
            }
        }
    }
    @Override
    public void updateaddremoveitemId(int postion, int id, boolean value,boolean itemvalue) {

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }



    public void interestwebservicedata()
    {
        countries = new ArrayList<CountryList>();
        RequestInterface requestInterface= config.retrofitRegister();
        retrofit2.Call<InterestListResponse> call = requestInterface.getInterest();
        call.enqueue(new Callback<InterestListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<InterestListResponse> call, Response<InterestListResponse> response) {


                for(int i=0;i<response.body().getInterestData().size();i++)
                {
                  //d  groupList.add(response.body().getInterestData().get(i).getName());
                    loadwebchild(response.body().getInterestData().get(i).getInterests(), response.body().getInterestData().get(i).getName());
                  /*  for(int j=0;j<response.body().getCultureData().get(i).getCultures().size();j++)
                    {
                        groupList.add(response.body().getCultureData().get(i).getCultures().get(j).get)

                    }*/
                }
//                expListAdapter = new ExpandableInterestAdapter(ProfileStepThreeActivity.this, groupList, laptopCollection,ProfileStepThreeActivity.this,ProfileStepThreeActivity.this);
                // expListAdapter = new ExpandableListAdapter(ProfileStepOneActivity.this, groupList, laptopCollection,ProfileStepOneActivity.this);
//                expListView.setAdapter(expListAdapter);



                expListAdapter = new CountryAdapterList(ProfileStepThreeActivity.this, countries,ProfileStepThreeActivity.this);
                expListView.setAdapter(expListAdapter);
                expListAdapter.setChoiceMode(CountryAdapterList.CHOICE_MODE_MULTIPLE);

            }
            @Override
            public void onFailure(retrofit2.Call<InterestListResponse> call, Throwable t) {
            }
        });
    }

    private void loadwebchild(List<Interest> laptopModels, String name) {
        childList = new ArrayList<FlagList>();
        List<String> list = new ArrayList<>();
        // flagdata = new ArrayList<>();
        for(int i=0;i<laptopModels.size();i++)
        {
            //              flagdata = new ArrayList<>();
            FLAGDATA fllll = new FLAGDATA(laptopModels.get(i).getName(),"",i,laptopModels.get(i).getId(),0);
            flagdata.add(fllll);
//          /  childList.add(laptopModels.get(i).getCulName());
            childList.add(new FlagList(laptopModels.get(i).getName(),"",0,flagdata));
            list.add(laptopModels.get(i).getName());
        }
        countries.add(new CountryList(name, list));
       // laptopCollection.put(name, childList);
       /* for (String model : laptopModels)
            childList.add(model);*/
    }
    @Override
    public void totallist(List<String> list) {
        selectedlist = list;
    }
}
