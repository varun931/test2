package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import awayzoneadvertiser.com.advertiser.culturelist.Culture;
import awayzoneadvertiser.com.advertiser.culturelist.FLAGDATA;
import awayzoneadvertiser.com.advertiser.culturelist.FlagList;
import awayzoneadvertiser.com.advertiser.culturelist.ListResponse;
import awayzoneadvertiser.com.advertiser.custominterface.AddRemoveItemInterface;
import awayzoneadvertiser.com.advertiser.custominterface.CultureListInterface;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.CultureDataResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.CulturePojo;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 12/4/2017.
 */

public class ProfileStepTwoActivity extends AppCompatActivity implements View.OnClickListener , AddRemoveItemInterface,CultureListInterface {

     RecyclerView culturerecyclerview;
     GridLayoutManager gridLayoutManager;
     CultureRecyclerViewAdapter cultureRecyclerViewAdapter;
     ArrayList<CulturePojo> culturelist;
     Config config;
     List<String> addCulture = new ArrayList<>();
     RelativeLayout profilesecondbackarrowlayout,profilesecondnextarrowlayout;
     Toolbar toolbar;
     ProgressDialogClass progressDialogClass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    ExpandableListView expListView;

    CountryAdapterList expListAdapter;

    //List<String> selectedlist = new ArrayList<>();
    List<Integer> mainfinallist = new ArrayList<>();


    private static List<CountryList> countries;

    List<FLAGDATA> flagdata  = new ArrayList<>();

    // ExpandableListAdapter expListAdapter;
    List<FlagList> childList= new ArrayList<>();

    List<String> selectedlist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesteptwoactivity);

        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        config = new Config(ProfileStepTwoActivity.this);
        profilesecondbackarrowlayout = (RelativeLayout)findViewById(R.id.profilesecondbackarrowlayout);
        profilesecondnextarrowlayout = (RelativeLayout)findViewById(R.id.profilesecondnextarrowlayout);
        culturerecyclerview = (RecyclerView)findViewById(R.id.culturerecyclerview);
        gridLayoutManager = new GridLayoutManager(this,2);
        culturerecyclerview.setLayoutManager(gridLayoutManager);
        progressDialogClass = new ProgressDialogClass(this);
        culturelist = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Culture...");
        culturewebservice();
        profilesecondbackarrowlayout.setOnClickListener(this);
        profilesecondnextarrowlayout.setOnClickListener(this);



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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.search_menu_grey, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.profilesecondbackarrowlayout:
                super.onBackPressed();
                break;
            case R.id.profilesecondnextarrowlayout:



                removedata();
                selectedData();
                if(mainfinallist.size()==0)
                {
                    Toast.makeText(ProfileStepTwoActivity.this,"Please select atleast one culture",Toast.LENGTH_LONG).show();
                }
                else if(mainfinallist.size()>14)
                {
                    Toast.makeText(ProfileStepTwoActivity.this,"you can select only fourteen culture",Toast.LENGTH_LONG).show();
                }
                else {
                    JSONArray roomrules = new JSONArray();
                    JSONArray roomrulesid = new JSONArray();
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
                    sharedPreferences.getString(Constant.PROFILECULTURE, "");
               /*     if (addCulture.size() <= 0) {

                        Toast.makeText(ProfileStepTwoActivity.this, "Seleact atleast one culture", Toast.LENGTH_LONG).show();
                    } else {*/
                        Intent intent = new Intent(ProfileStepTwoActivity.this, ProfileStepThreeActivity.class);
                        startActivity(intent);
                  //  }
                }
                break;
        }
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


    private void search(SearchView searchView) {
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }
           @Override
            public boolean onQueryTextChange(String newText) {
                if (cultureRecyclerViewAdapter != null)
                {
                    cultureRecyclerViewAdapter.getFilter().filter(newText);
                }
                else
                {
                }


                return true;
            }
        });
    }
    @Override
    public void addremoveitemId(int id,boolean check,boolean contentvalue) {
        if(check)
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
              //  cultureRecyclerViewAdapter.notifyDataSetChanged();
                cultureRecyclerViewAdapter.notifyItemChanged(id);
               // adapter.notifyItemChanged(0)
            }
            }
        }
    }

    @Override
    public void updateaddremoveitemId(int position,int id, boolean value,boolean itemvalue) {

    }

    public void culturewebservice()
    {
        progressDialogClass.showdialog();
        final Config config = new Config(ProfileStepTwoActivity.this);
        RequestInterface requestInterface= config.retrofitRegister();
        retrofit2.Call<CultureDataResponse> call = requestInterface.getCulture();
        call.enqueue(new Callback<CultureDataResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CultureDataResponse> call, Response<CultureDataResponse> response) {
                progressDialogClass.hideDialog();
                for(int i=0;i<response.body().getCultureData().size();i++)
                {
                    culturelist.add(new CulturePojo(i,response.body().getCultureData().get(i).getId(),response.body().getCultureData().get(i).getTagName(),"NO"));
                }
                cultureRecyclerViewAdapter = new CultureRecyclerViewAdapter(ProfileStepTwoActivity.this,culturelist,ProfileStepTwoActivity.this);
                culturerecyclerview.setAdapter(cultureRecyclerViewAdapter);
            }
            @Override
            public void onFailure(retrofit2.Call<CultureDataResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
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
      //  laptopCollection.put(name, childList);
       /* for (String model : laptopModels)
            childList.add(model);*/
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
                //    groupList.add(response.body().getCultureData().get(i).getName());
                    loadwebchild(response.body().getCultureData().get(i).getCultures(), response.body().getCultureData().get(i).getName());

                  /*  for(int j=0;j<response.body().getCultureData().get(i).getCultures().size();j++)
                    {
                        groupList.add(response.body().getCultureData().get(i).getCultures().get(j).get)
                    }*/
                }
//                expListAdapter = new ExpandableListAdapter(ProfileStepTwoActivity.this, groupList, laptopCollection,ProfileStepTwoActivity.this,ProfileStepTwoActivity.this);
                // expListAdapter = new ExpandableListAdapter(ProfileStepOneActivity.this, groupList, laptopCollection,ProfileStepOneActivity.this);
//                expListView.setAdapter(expListAdapter);



                expListAdapter = new CountryAdapterList(ProfileStepTwoActivity.this, countries,ProfileStepTwoActivity.this);
                expListView.setAdapter(expListAdapter);
                expListAdapter.setChoiceMode(CountryAdapterList.CHOICE_MODE_MULTIPLE);

            }
            @Override
            public void onFailure(retrofit2.Call<ListResponse> call, Throwable t) {
            }
        });
    }

    @Override
    public void totallist(List<String> list) {
        selectedlist = list;
        for(int i=0;i<selectedlist.size();i++)
        {
        }
    }
}

