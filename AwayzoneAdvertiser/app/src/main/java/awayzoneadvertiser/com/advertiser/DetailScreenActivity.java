package awayzoneadvertiser.com.advertiser;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 12/5/2017.
 */

public class DetailScreenActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView storiesrecyclerview;
    LinearLayoutManager linearLayoutManager;
  //  StoriesRecyclerViewAdapter storiesRecyclerViewAdapter;
    RatingBar ratingBar,postratingbar;
    BottomSheetDialog dialog,storydialog;
    Button btn_cancel;
    LinearLayout ratingbarlayout,commentbtn,likebutton;
    RelativeLayout closeratinglayout,adddetailbackarrowlayout,reviebutton,postratingbtn,closestory,addstorybtn,storynofound;
    String addImage,addTitle,addDescription;
    int addId,addComment, addLike,addUserLike,addUserRatingStatus,addReview;
    TextView addtitle,adddescription,addlike,addcomment,ratingbartext,review,storytextview,addstorytextview;
    ImageView addimageview,storyimageview;
    Config config;
    float finalrating,adduserrating;
    ProgressDialogClass progressDialogClass;
    String TAG="DetailScreenActivity";
    int GALLERY_CODE = 1;
    String pathphotovalidation="";
 //   List<Story> list = new ArrayList<>();
    String imageUrl;
    TextView storycounttextview,likeicon;
    float addRating;
    String likevalue="3";
    SharedPreferences sharedPreferences,sharedPreferencess;
    SharedPreferences.Editor editor,editors;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailscreenactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        sharedPreferencess = getSharedPreferences(Constant.sharedpreffilenames, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editors = sharedPreferencess.edit();
        config = new Config(DetailScreenActivity.this);
        progressDialogClass = new ProgressDialogClass(this);
        ratingBar = (RatingBar)findViewById(R.id.ratingbar);
/*        addImage = getIntent().getStringExtra(Constant.ADDIMAGE);
        addTitle = getIntent().getStringExtra(Constant.ADDTITLE);
        addDescription = getIntent().getStringExtra(Constant.ADDDESCRIPTION);
        addId = getIntent().getIntExtra(Constant.ADDID,0);
        addComment = getIntent().getIntExtra(Constant.ADDCOMMENT,0);
        addRating = getIntent().getFloatExtra(Constant.ADDRATING,0);
        addLike = getIntent().getIntExtra(Constant.ADDLIKE,0);
        addUserLike = getIntent().getIntExtra(Constant.ADDUSERLIKE,0);
        adduserrating = getIntent().getFloatExtra(Constant.ADDUSERRATING,0);
        addUserRatingStatus = getIntent().getIntExtra(Constant.ADDUSERRATINGSTATUS,0);
        addReview = getIntent().getIntExtra(Constant.ADDREVIEW,0);*/

        addtitle = (TextView)findViewById(R.id.addtitle);
        likeicon = (TextView)findViewById(R.id.likeicon);
        addstorytextview = (TextView)findViewById(R.id.addstorytextview);
        storynofound = (RelativeLayout) findViewById(R.id.storynofound);
        storycounttextview = (TextView)findViewById(R.id.storycounttextview);
        adddescription = (TextView)findViewById(R.id.adddescription);
        addlike = (TextView)findViewById(R.id.addlike);
        addcomment = (TextView)findViewById(R.id.addcomment);
        ratingbartext = (TextView)findViewById(R.id.ratingbartext);
        review = (TextView)findViewById(R.id.review);
        addimageview = (ImageView) findViewById(R.id.addimageview);
        storiesrecyclerview = (RecyclerView)findViewById(R.id.storiesrecyclerview);
        adddetailbackarrowlayout = (RelativeLayout) findViewById(R.id.adddetailbackarrowlayout);
        reviebutton = (RelativeLayout) findViewById(R.id.reviebutton);
        ratingbarlayout = (LinearLayout) findViewById(R.id.ratingbarlayout);
        commentbtn = (LinearLayout) findViewById(R.id.commentbtn);
        likebutton = (LinearLayout) findViewById(R.id.likebutton);
        linearLayoutManager = new LinearLayoutManager(this);
        storiesrecyclerview.setLayoutManager(linearLayoutManager);

        ratingBar.setOnClickListener(this);
        commentbtn.setOnClickListener(this);
        ratingbarlayout.setOnClickListener(this);
        addtitle.setText(addTitle);
        adddescription.setText(addDescription);
        addlike.setText(addLike+"");
        addcomment.setText(addComment+"");
        ratingBar.setRating(addRating);
    ratingbartext.setText("("+addRating+")");
        Glide.with(this).load(sharedPreferences.getString(Constant.ADDIMAGEURL,"")+addImage).into(addimageview);
        adddetailbackarrowlayout.setOnClickListener(this);
        reviebutton.setOnClickListener(this);
        addstorytextview.setOnClickListener(this);
        likebutton.setOnClickListener(this);
        review.setText(addReview+" REVIEWS");
        if(addUserLike==1)
        {
          likeicon.setTextColor(ContextCompat.getColor(DetailScreenActivity.this,R.color.blue));
        }

     //   getstorylist();
    }



    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {

       if (v.getId() == ratingbarlayout.getId()) {
            dialog.show();
        } else if (v.getId() == closeratinglayout.getId()) {
            dialog.hide();
        }
        else if(v.getId()==adddetailbackarrowlayout.getId())
       {
           Intent intent = new Intent(DetailScreenActivity.this,HomeActivity.class);
           startActivity(intent);
       }
       else if(v.getId()==commentbtn.getId())
       {
           Intent commentintent = new Intent(DetailScreenActivity.this, ReviewCommentActivity.class);
           commentintent.putExtra(Constant.ADDID,addId);
           commentintent.putExtra(Constant.COMMENTTYPE,Constant.COMMENTS);
           startActivity(commentintent);
       }
       else if(v.getId()==reviebutton.getId())
       {
           Intent intent = new Intent(DetailScreenActivity.this,ReviewCommentActivity.class);
           intent.putExtra(Constant.ADDID,addId);
           intent.putExtra(Constant.COMMENTTYPE,Constant.REVIEWS);
           startActivity(intent);
       }

    }







//    public void getstorylist()
//    {
//        progressDialogClass.showdialog();
//        // final Config config = new Config(SignUpActivity.this);
//        RequestInterface requestInterface= config.retrofitRegister();
//        Call<UserStoryListReponse> call = requestInterface.addstorylist(sharedPreferences.getInt(Constant.USERID,0),addId);
//        call.enqueue(new Callback<UserStoryListReponse>() {
//            @Override
//            public void onResponse(Call<UserStoryListReponse> call, Response<UserStoryListReponse> response) {
//                progressDialogClass.hideDialog();
//                imageUrl=response.body().getImageUrl();
//                storycounttextview.setText("STORIES ("+response.body().getStory().size()+")");
//
//                if(response.body().getStory().size()==0)
//                {
//                    storiesrecyclerview.setVisibility(View.GONE);
//                    storynofound.setVisibility(View.VISIBLE);
//                }
//                else {
//                    storynofound.setVisibility(View.GONE);
//                    storiesrecyclerview.setVisibility(View.VISIBLE);
//
//                    for (int i = 0; i < response.body().getStory().size(); i++) {
//                        // StoryData( int baseid,int story_id,int user_id,int ad_id,String caption,String image)
//                        list.add(new Story(0, response.body().getStory().get(i).getStoryId(), response.body().getStory().get(i).getUserId(), response.body().getStory().get(i).getAdId(), response.body().getStory().get(i).getCaption(), response.body().getStory().get(i).getImage(),response.body().getStory().get(i).getUsers()));
//                        //   public CommentList(int baseId,int ad_id,                                           int user_id,int comment_id,String date,                                             String time, String first_name, String profile_image)
//                    }
//                    storiesRecyclerViewAdapter = new StoriesRecyclerViewAdapter(imageUrl, DetailScreenActivity.this, list, DetailScreenActivity.this);
//                    storiesrecyclerview.setAdapter(storiesRecyclerViewAdapter);
//                }
//
//            }
//            @Override
//            public void onFailure(Call<UserStoryListReponse> call, Throwable t) {
//                progressDialogClass.hideDialog();
//            }
//        });
//    }










}
