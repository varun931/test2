package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import com.triusinfotech.awayzoneadvertiser.util.CustomGridLayoutManager;
import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.adapter.HomeRecycleViewAdapter;
import awayzoneadvertiser.com.advertiser.adapter.ViewPagerAdapter;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.dashboard.AdsDatum;
import awayzoneadvertiser.com.advertiser.dashboard.DashBoardDataResponse;
import awayzoneadvertiser.com.advertiser.dashboard.GetCityDatum;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.CustomViewPager;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import im.dacer.androidcharts.PieHelper;
import im.dacer.androidcharts.PieView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/9/2018.
 */



public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView homerecyclervie;
    GridLayoutManager gridLayoutManager;
    LinearLayout notificationlayout,pichartlayout;
    LinearLayoutManager linearLayoutManager;
    HomeRecycleViewAdapter homeRecycleViewAdapter,mainrecyclervieadapter;
    CustomGridLayoutManager customGridLayoutManager;
    ViewPagerAdapter viewPagerAdapter;
 //  ViewPager viewpager;
    CustomViewPager viewpager;
    RelativeLayout imageviebtn,nodata;
    Config config;
    RelativeLayout nextbtn,previousbtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialogClass progressDialogClass;
    List<String> analyticlist = new ArrayList();
    List<AdsDatum> adlist  = new ArrayList<>();
    RatingBar ratingbar;
    TextView ratingbartext;
    boolean dialogvalue=false;
    List<GetCityDatum> piechartdata = new ArrayList<>();
    ImageView profilepic;
    TextView lastsevendays,lastsevendayspie,notificationtext;
    BottomSheetDialog dialog;
    RelativeLayout sortbylastsevendays, sortbylastmonth, sortbylastyear,sortingcancelbtn;
    ScrollView scrollview;



    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        lastsevendays = (TextView)findViewById(R.id.lastsevendays);
        scrollview = (ScrollView)findViewById(R.id.scrollview);
        config = new Config(HomeActivity.this);
        progressDialogClass = new ProgressDialogClass(this);
        homerecyclervie = (RecyclerView)findViewById(R.id.homerecyclervie);
        notificationlayout = (LinearLayout) findViewById(R.id.notificationlayout);
        notificationtext = (TextView)findViewById(R.id.notificationtext);
        profilepic  = (ImageView)findViewById(R.id.profilepic);
        ratingbar = (RatingBar) findViewById(R.id.ratingbar);
        pichartlayout = (LinearLayout)findViewById(R.id.pichartlayout);
        imageviebtn = (RelativeLayout) findViewById(R.id.imageviebtn);
        nodata = (RelativeLayout) findViewById(R.id.nodata);
        ratingbartext = (TextView) findViewById(R.id.ratingbartext);
        lastsevendayspie = (TextView) findViewById(R.id.lastsevendayspie);
       // viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager = (CustomViewPager) findViewById(R.id.customviewpager);
         nextbtn = (RelativeLayout) findViewById(R.id.nextbtn);
        previousbtn = (RelativeLayout) findViewById(R.id.previousbtn);
        gridLayoutManager = new GridLayoutManager(this,3);
        homerecyclervie.setNestedScrollingEnabled(false);


        linearLayoutManager = new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false);
        homerecyclervie.setLayoutManager(gridLayoutManager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        customGridLayoutManager = new CustomGridLayoutManager(HomeActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };


        if(sharedPreferences.getString(Constant.REGPROFILEIMAGE,"").equalsIgnoreCase(""))
        {
            Glide.with(this).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(profilepic);
        }
        else
        {
            Glide.with(this).load(sharedPreferences.getString(Constant.REGPROFILEIMAGEURL,"")+ sharedPreferences.getString(Constant.REGPROFILEIMAGE,"")).apply(RequestOptions.circleCropTransform()).into(profilepic);
        }
        PieView pieView = (PieView)findViewById(R.id.pie_view);
//        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
//        pieView.setDate(pieHelperArrayList);
//        pieView.selectedPie(2); //optional
//    //    pieView.setOnPieClickListener(listener) //optional
//        pieView.showPercentLabel(false); //optional

//        viewPagerAdapter.addFragment(new ViewPagerOneFragment(), "FRAG1");
//        viewPagerAdapter.addFragment(new ViewPagerOneFragment(), "FRAG2");
//        viewPagerAdapter.addFragment(new ViewPagerOneFragment(), "FRAG3");
//        viewpager.setAdapter(viewPagerAdapter);
        set(pieView);
        config = new Config(HomeActivity.this);


        imageviebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,PorfileActivity.class);
                startActivity(intent);
                            }
        });
        nextbtn.setOnClickListener(this);
        previousbtn.setOnClickListener(this);
        dashboardwebservice();



     /*   ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(8f, 0));
        yvalues.add(new PieEntry(15f, 1));
        yvalues.add(new PieEntry(12f, 2));
        yvalues.add(new PieEntry(25f, 3));
        yvalues.add(new PieEntry(23f, 4));
        yvalues.add(new PieEntry(17f, 5));

        PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");
        IPieDataSet dataSetm = new IPieDataSet(yvalues, "Election Results");



        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("January");
        xVals.add("February");
        xVals.add("March");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");

        PieData data = new PieData(dataSetm);

        pieChart.setDrawHoleEnabled(false);
        // In percentage Term
        data.setValueFormatter(new PercentFormatter());
           pieChart.setData(data);
*/



    /*    entries.add(new PieEntry(8f, 0));
        entries.add(new PieEntry(15f, 1));
        entries.add(new PieEntry(12f, 2));
        entries.add(new PieEntry(25f, 3));
        entries.add(new PieEntry(23f, 4));
        entries.add(new PieEntry(17f, 5));*/














      /*  PieDataSet dataSet = new PieDataSet(yvalues, "Election Results");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("January");
        xVals.add("February");
        xVals.add("March");
        xVals.add("April");
        xVals.add("May");
        xVals.add("June");

        PieData data = new PieData(xVals, dataSet);
        // In Percentage
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
        pieChart.setDescription("This is Pie Chart");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);

        pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);*/

  //dd      pieChart.setOnChartValueSelectedListener(this);


        lastsevendays.setText(Html.fromHtml("<u>Last 7 Days</u>"));
        lastsevendayspie.setText(Html.fromHtml("<u>Last 7 Days</u>"));
        //<u>Last 7 Days</u>
        lastsevendays.setOnClickListener(this);
        lastsevendayspie.setOnClickListener(this);
        sortingview();
    }

    @Override
    protected void onResume() {
        if(sharedPreferences.getString(Constant.REGPROFILEIMAGE,"").equalsIgnoreCase(""))
        {
            Glide.with(this).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(profilepic);
        }
        else
        {
            Glide.with(this).load(sharedPreferences.getString(Constant.REGPROFILEIMAGEURL,"")+ sharedPreferences.getString(Constant.REGPROFILEIMAGE,"")).apply(RequestOptions.circleCropTransform()).into(profilepic);
        }
        super.onResume();
    }

    public void sortingview() {
        View modalbottomsheet = getLayoutInflater().inflate(R.layout.dashboardfilter, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(modalbottomsheet);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        sortingcancelbtn = (RelativeLayout) modalbottomsheet.findViewById(R.id.sortingcancelbtn);
        sortbylastsevendays = (RelativeLayout) modalbottomsheet.findViewById(R.id.sortbylastsevendays);
        sortbylastmonth = (RelativeLayout) modalbottomsheet.findViewById(R.id.sortbylastmonth);
        sortbylastyear = (RelativeLayout) modalbottomsheet.findViewById(R.id.sortbylastyear);
        sortingcancelbtn.setOnClickListener(this);
        sortbylastmonth.setOnClickListener(this);
        sortbylastsevendays.setOnClickListener(this);
        sortbylastyear.setOnClickListener(this);
    }

    public void chartlib()
    {


       PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

// dd       mChart.setCenterTextTypeface(mTfLight);
// dd        mChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);


        float mult = 100;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
       /* for (int i = 0; i < piechartdata.size() ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.star)));
        }*/

        for (int i = 0; i < piechartdata.size() ; i++) {

            entries.add(new PieEntry(piechartdata.get(i).getGetCityCount(),piechartdata.get(i).getGetCity()));
          /*  entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    piechartdata.get(i).getGetCity()[i %  piechartdata.size()],
                    getResources().getDrawable(R.drawable.star)));*/
        }


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        PieDataSet dataSet = new PieDataSet(entries, "State");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setDrawHoleEnabled(false);

        //    data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    private void set(PieView pieView) {
        ArrayList<PieHelper> pieHelperArrayList = new ArrayList<PieHelper>();
        pieHelperArrayList.add(new PieHelper(66.37f, Color.GREEN));
        pieHelperArrayList.add(new PieHelper(33.33f,Color.RED));

        pieView.setDate(pieHelperArrayList);
        pieView.setOnPieClickListener(new PieView.OnPieClickListener() {
            @Override public void onPieClick(int index) {
                if (index != PieView.NO_SELECTED_INDEX) {
               //     textView.setText(index + " selected");
                } else {
                   // textView.setText("No selected pie");
                }
            }
        });
      //  pieView.selectedPie(2);
    }
    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.nextbtn:
             viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
                break;
            case R.id.previousbtn:
                viewpager.setCurrentItem(viewpager.getCurrentItem()-1);
                break;
            case R.id.lastsevendays:
                dialogvalue=false;
                dialog.show();
                break;
            case R.id.sortingcancelbtn:
                dialog.hide();
                break;
            case R.id.sortbylastmonth:
                if(dialogvalue)
                {
                  dashboardfilter(Constant.MONTH,Constant.GRAPH);
                }
                else
                {
                    dashboardfilter(Constant.MONTH,Constant.ANALYTIC);
                }
                break;
            case R.id.sortbylastsevendays:
                if(dialogvalue) {
                    dashboardfilter(Constant.SEVENDAYS,Constant.GRAPH);
                }
                else
                {
                    dashboardfilter(Constant.SEVENDAYS,Constant.ANALYTIC);
                }
                break;
            case R.id.sortbylastyear:
                if(dialogvalue) {
                    dashboardfilter(Constant.YEAR,Constant.GRAPH);
                }
                else
                {
                    dashboardfilter(Constant.YEAR,Constant.ANALYTIC);
                }
                break;
            case R.id.lastsevendayspie:
                dialogvalue=true;
                dialog.show();
                break;
        }

    }

    public void dashboardwebservice()
    {
        progressDialogClass.showdialog();
        RequestInterface requestInterface= config.retrofitRegister();
//        retrofit2.Call<DashBoardDataResponse> call = requestInterface.dashboard(2);
        //rating_sum

        retrofit2.Call<DashBoardDataResponse> call = requestInterface.dashboard(sharedPreferences.getInt(Constant.USERID,0));
        call.enqueue(new Callback<DashBoardDataResponse>() {
            @Override
            public void onResponse(Call<DashBoardDataResponse> call, Response<DashBoardDataResponse> response) {
                progressDialogClass.hideDialog();
                if(response.body().getStatus().equalsIgnoreCase("0"))
                {
                    scrollview.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                }
                else if(response.body().getStatus().equalsIgnoreCase("1")) {
                    nodata.setVisibility(View.GONE);
                    scrollview.setVisibility(View.VISIBLE);
                    if(response.body().getLast_login()==0)
                    {
                        notificationtext.setText("Welcome to Awayzone");
                    }
                    else if(response.body().getLast_login()==-1)
                    {
                        notificationlayout.setVisibility(View.GONE);
                    }
                    else
                    {
                        notificationtext.setText("You have got "+response.body().getLast_login()+" new views since last login");
                    }
                    ratingbar.setRating(response.body().getRatingSum());
                    ratingbar.setEnabled(false);
                    ratingbartext.setText("(" + response.body().getRatingSum() + ")");

                    for (int i = 0; i < response.body().getAnalytics().size(); i++) {

                        analyticlist.add(response.body().getAnalytics().get(i).getGetImpression() + Constant.ADDVALUE);
                        analyticlist.add(response.body().getAnalytics().get(i).getGetClick() + Constant.ADDVALUE);
                        analyticlist.add(response.body().getAnalytics().get(i).getGetReview() + Constant.ADDVALUE);
                        analyticlist.add(response.body().getAnalytics().get(i).getGetStory() + Constant.ADDVALUE);
                        analyticlist.add(response.body().getAnalytics().get(i).getGetBeenHere() + Constant.ADDVALUE);
                        analyticlist.add(response.body().getAnalytics().get(i).getGetBookmarks() + Constant.ADDVALUE);
                        analyticlist.add(response.body().getAnalytics().get(i).getGetLike() + Constant.ADDVALUE);
                        analyticlist.add(response.body().getAnalytics().get(i).getGetShare() + Constant.ADDVALUE);
                        analyticlist.add(response.body().getAnalytics().get(i).getGetComment() + Constant.ADDVALUE);
                    }
                    homeRecycleViewAdapter = new HomeRecycleViewAdapter(HomeActivity.this, analyticlist);
                    homerecyclervie.setAdapter(homeRecycleViewAdapter);

                    if(response.body().getGetCityData().size()==0) {
                        pichartlayout.setVisibility(View.GONE);
                    }
                    else
                    {
                        piechartdata = response.body().getGetCityData();
                        chartlib();
                    }
               /* for(int  j=0; j<response.body().getAdsData().size();j++)
                {
                    //  public AdsDatum( Integer adId,String image,String title,String description,Integer likes,Integer comment,Integer review,Integer story,Float ratingSum)
                    adlist.add(new AdsDatum(response.body().getAdsData().get(j).getAdId(),response.body().getAdsData().get(j).getImage(),response.body().getAdsData().get(j).getTitle(),response.body().getAdsData().get(j).getDescription(),response.body().getAdsData().get(j).getLikes(),response.body().getAdsData().get(j).getComment(),response.body().getAdsData().get(j).getReview(),response.body().getAdsData().get(j).getStory(),response.body().getAdsData().get(j).getRatingSum()));
                  //  viewPagerAdapter.addFragment(new ViewPagerOneFragment( response.body().getAdsData().get(j).getImage(), response.body().getAdsData().get(j).getComment()+Constant.ADDVALUE,adlist), response.body().getAdsData().get(j).getImage());
                }*/


                    for (int j = 0; j < response.body().getAdsData().size(); j++) {
                        //  public AdsDatum( Integer adId,String image,String title,String description,Integer likes,Integer comment,Integer review,Integer story,Float ratingSum)
                        adlist.add(new AdsDatum(response.body().getAdsData().get(j).getAdId(), response.body().getAdsData().get(j).getImage(), response.body().getAdsData().get(j).getTitle(), response.body().getAdsData().get(j).getDescription(), response.body().getAdsData().get(j).getLikes(), response.body().getAdsData().get(j).getComment(), response.body().getAdsData().get(j).getReview(), response.body().getAdsData().get(j).getStory(), response.body().getAdsData().get(j).getRatingSum()));
                        viewPagerAdapter.addFragment(new ViewPagerOneFragment(response.body().getAdsData().get(j).getImage(), response.body().getAdsData().get(j).getImpression() + Constant.ADDVALUE, response.body().getAdsData().get(j).getClick() + Constant.ADDVALUE, adlist), response.body().getAdsData().get(j).getImage());
                    }
                    viewpager.setAdapter(viewPagerAdapter);
                }
            }
            @Override
            public void onFailure(Call<DashBoardDataResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }



    public void dashboardfilter(final String type,final String shortytyp)
    {
        /*if(shortytyp.equalsIgnoreCase(Constant.ANALYTIC))
        {
            int size = analyticlist.size();
            analyticlist.clear();
        }*/

       // homeRecycleViewAdapter.notifyItemRangeRemoved(0, size);
        dialog.hide();
        progressDialogClass.showdialog();
        RequestInterface requestInterface= config.retrofitRegister();
       // retrofit2.Call<DashBoardDataResponse> call = requestInterface.dashboardfilter(2,type,shortytyp);
        retrofit2.Call<DashBoardDataResponse> call = requestInterface.dashboardfilter(sharedPreferences.getInt(Constant.USERID,0),type,shortytyp);
        // retrofit2.Call<DashBoardDataResponse> call = requestInterface.dashboard(sharedPreferences.getInt(Constant.USERID,0));
        call.enqueue(new Callback<DashBoardDataResponse>() {
            @Override
            public void onResponse(Call<DashBoardDataResponse> call, Response<DashBoardDataResponse> response) {
                progressDialogClass.hideDialog();
           /*     ratingbar.setRating(response.body().getRatingSum());
                ratingbar.setEnabled(false);
                ratingbartext.setText("("+response.body().getRatingSum()+")");*/
           if(shortytyp.equalsIgnoreCase(Constant.ANALYTIC)) {
               analyticlist.clear();
               for (int i = 0; i < response.body().getAnalytics().size(); i++) {
                   analyticlist.add(response.body().getAnalytics().get(i).getGetImpression() + Constant.ADDVALUE);
                   analyticlist.add(response.body().getAnalytics().get(i).getGetClick() + Constant.ADDVALUE);
                   analyticlist.add(response.body().getAnalytics().get(i).getGetReview() + Constant.ADDVALUE);
                   analyticlist.add(response.body().getAnalytics().get(i).getGetStory() + Constant.ADDVALUE);
                   analyticlist.add(response.body().getAnalytics().get(i).getGetBeenHere() + Constant.ADDVALUE);
                   analyticlist.add(response.body().getAnalytics().get(i).getGetBookmarks() + Constant.ADDVALUE);
                   analyticlist.add(response.body().getAnalytics().get(i).getGetLike() + Constant.ADDVALUE);
                   analyticlist.add(response.body().getAnalytics().get(i).getGetShare() + Constant.ADDVALUE);
                   analyticlist.add(response.body().getAnalytics().get(i).getGetComment() + Constant.ADDVALUE);
               }
               homeRecycleViewAdapter = new HomeRecycleViewAdapter(HomeActivity.this, analyticlist);
               homerecyclervie.setAdapter(homeRecycleViewAdapter);

               if (type.equalsIgnoreCase(Constant.MONTH)) {
                   lastsevendays.setText(Html.fromHtml("<u>Last month</u>"));
               } else if (type.equalsIgnoreCase(Constant.SEVENDAYS)) {
                   lastsevendays.setText(Html.fromHtml("<u>Last 7 Days</u>"));
               } else if (type.equalsIgnoreCase(Constant.YEAR)) {
                   lastsevendays.setText(Html.fromHtml("<u>Last year</u>"));
               }
           }
           else
           {
               piechartdata=  response.body().getGetCityData();

               chartlib();
               if (type.equalsIgnoreCase(Constant.MONTH)) {
                   lastsevendayspie.setText(Html.fromHtml("<u>Last month</u>"));
               } else if (type.equalsIgnoreCase(Constant.SEVENDAYS)) {
                   lastsevendayspie.setText(Html.fromHtml("<u>Last 7 Days</u>"));
               } else if (type.equalsIgnoreCase(Constant.YEAR)) {
                   lastsevendayspie.setText(Html.fromHtml("<u>Last year</u>"));
               }
           }

            }
            @Override
            public void onFailure(Call<DashBoardDataResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }

}
