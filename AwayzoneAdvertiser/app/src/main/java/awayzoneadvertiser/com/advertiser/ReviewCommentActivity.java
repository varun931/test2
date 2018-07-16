package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.adapter.CommentRecyclerViewAdapter;
import awayzoneadvertiser.com.advertiser.gettersetter.CommentList;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/15/2018.
 */

public class ReviewCommentActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout commentbackarrowlayout;
    RecyclerView commentrecyclerview;
    ProgressDialogClass progressDialogClass;
    LinearLayoutManager linearLayoutManager;
    Config config;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<CommentList> list = new ArrayList<>();
    CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    TextView toolbartext;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewcommentactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        config = new Config(ReviewCommentActivity.this);
        progressDialogClass = new ProgressDialogClass(this);
        commentbackarrowlayout = (RelativeLayout)findViewById(R.id.commentbackarrowlayout);
        commentrecyclerview = (RecyclerView)findViewById(R.id.commentrecyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        commentrecyclerview.setLayoutManager(linearLayoutManager);
        toolbartext = (TextView)findViewById(R.id.toolbartext);

        commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(sharedPreferences.getInt(Constant.USERID,0),"",list,ReviewCommentActivity.this);
        commentrecyclerview.setAdapter(commentRecyclerViewAdapter);
        commentbackarrowlayout.setOnClickListener(this);

        toolbartext.setText("COMMENTS");
      //  getWebService();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.commentbackarrowlayout:
                onBackPressed();
                break;
        }
    }

   /* public void getWebService()
    {
        progressDialogClass.showdialog();
        RequestInterface requestInterface = config.retrofitRegister();
//        Call<RegisterationResponse> call = requestInterface.getcommentreview(149,4,"comment");
        Log.e(TAG,"get webservice of comment/revuew..."+sharedPreferences.getInt(Constant.USERID,0)+"...addid.."+addId+"..apitype.."+apitype);
        Call<RegisterationResponse> call = requestInterface.getcommentreview(sharedPreferences.getInt(Constant.USERID,0),addId,apitype);
        call.enqueue(new Callback<RegisterationResponse>() {
            @Override
            public void onResponse(Call<RegisterationResponse> call, Response<RegisterationResponse> response) {
                progressDialogClass.hideDialog();
                editor.putString(Constant.COMMENTIMAGEURL,response.body().getImage_url());
                for(int i=0;i<response.body().getComment().size();i++)
                {
                    //         list.add(new CommentList(0,"Mukesh Singh","454554456",commentedittext.getText().toString(),"jhhjdhfhdf",45,16,10));
                    //     public CommentList(int baseId,int ad_id,                                           int user_id,int comment_id,String date,                                             String time, String first_name, String profile_image)
                    list.add(new CommentList(i,response.body().getComment().get(i).getAd_id(),response.body().getComment().get(i).getUser_id(),response.body().getComment().get(i).getComment_id(),response.body().getComment().get(i).getDate(),response.body().getComment().get(i).getTime(),response.body().getComment().get(i).getFirst_name(),response.body().getComment().get(i).getProfile_image(),response.body().getComment().get(i).getComment()));
                }
                commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(sharedPreferences.getInt(Constant.USERID,0),response.body().getImage_url(),list,CommentAcitivity.this,CommentAcitivity.this);
                commentrecyclerview.setAdapter(commentRecyclerViewAdapter);
            }
            @Override
            public void onFailure(Call<RegisterationResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }*/
}
