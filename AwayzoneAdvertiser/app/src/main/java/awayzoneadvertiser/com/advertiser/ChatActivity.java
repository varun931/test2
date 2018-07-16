package awayzoneadvertiser.com.advertiser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.adapter.ChatRecyclerViewAdapter;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.ChatDatum;
import awayzoneadvertiser.com.advertiser.gettersetter.ChatListResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.PostChatResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/19/2018.
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {


    TextView decriptiontext,titletext,chatstatustext,toolbartext;
    RecyclerView chatrecyclerview;
    ChatRecyclerViewAdapter chatRecyclerViewAdapter;
    LinearLayoutManager linearLayoutManager;
    ProgressDialogClass progressDialogClass;
    RelativeLayout chatbackarrowlayout,commentview;
    Config config;
    EditText commentedittext;
    RelativeLayout postbtn;
    List<ChatDatum> chatlist = new ArrayList<>();
    int chataddid, ticketid;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatactivity);

        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialogClass = new ProgressDialogClass(ChatActivity.this);
        config = new Config(ChatActivity.this);
        chataddid = getIntent().getIntExtra(Constant.CHATADDID,0);
        ticketid = getIntent().getIntExtra(Constant.TICKETID,0);
        commentedittext = (EditText)findViewById(R.id.commentedittext);
        postbtn = (RelativeLayout)findViewById(R.id.postbtn);
        chatbackarrowlayout = (RelativeLayout)findViewById(R.id.chatbackarrowlayout);
        commentview = (RelativeLayout)findViewById(R.id.commentview);
        titletext = (TextView)findViewById(R.id.titletext);
        toolbartext = (TextView)findViewById(R.id.toolbartext);
        chatstatustext = (TextView)findViewById(R.id.chatstatustext);
        decriptiontext = (TextView)findViewById(R.id.decriptiontext);
        chatrecyclerview = (RecyclerView)findViewById(R.id.chatrecyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        chatrecyclerview.setLayoutManager(linearLayoutManager);
        postbtn.setOnClickListener(this);
        chatstatustext.setOnClickListener(this);
        chatbackarrowlayout.setOnClickListener(this);

        webservice();
    }

    public void webservice()
    {

        //toolbartext

        //ChatListResponse
        if (!isNetworkAvailable()) {
            Toast.makeText(ChatActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        } else {
            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();
            Call<ChatListResponse> call = requestInterface.adverchatlist(chataddid,ticketid);
            call.enqueue(new Callback<ChatListResponse>() {
                @Override
                public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                    progressDialogClass.hideDialog();
                    if(response.body().getChatArr().get(0).getTicketStatus()==1)
                    {
                        chatstatustext.setText("CLOSE");
                        toolbartext.setText("HELP");
                    }
                    else
                    {
                        chatstatustext.setText("REOPEN");
                        toolbartext.setText("CLOSED");
                        commentview.setVisibility(View.GONE);
                    }
                    titletext.setText(response.body().getChatArr().get(0).getTitle());
                    decriptiontext.setText(response.body().getChatArr().get(0).getDescription());
                    chatlist= response.body().getChatArr().get(0).getChatData();

                    //            Glide.with(this).load(sharedPreferences.getString(Constant.REGPROFILEIMAGEURL,"")+ sharedPreferences.getString(Constant.REGPROFILEIMAGE,"")).apply(RequestOptions.circleCropTransform()).into(profileimageview);


                    chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(chatlist,sharedPreferences.getString(Constant.REGPROFILEIMAGEURL,"")+ sharedPreferences.getString(Constant.REGPROFILEIMAGE,""),ChatActivity.this);
                    chatrecyclerview.setAdapter(chatRecyclerViewAdapter);
                   /* list= response.body().getTicketData();
                    ticketListRecyclerAdapter = new HelpListRecyclerAdapter(list,HelpListActivitiy.this);
                    ticketlistrecyclerview.setAdapter(ticketListRecyclerAdapter);*/
                }
                @Override
                public void onFailure(Call<ChatListResponse> call, Throwable t) {
                    progressDialogClass.showdialog();
                }
            });

        }
    }
    public void postwebservice()
    {
        //ChatListResponse
        if (!isNetworkAvailable()) {
            Toast.makeText(ChatActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        } else {
            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();
            Call<PostChatResponse> call = requestInterface.postchat(chataddid,ticketid,commentedittext.getText().toString());
            call.enqueue(new Callback<PostChatResponse>() {
                @Override
                public void onResponse(Call<PostChatResponse> call, Response<PostChatResponse> response) {
                    progressDialogClass.hideDialog();
                    chatlist.add(new ChatDatum(commentedittext.getText().toString(),0,response.body().getTime(),response.body().getDate()));
                    chatRecyclerViewAdapter.notifyDataSetChanged();
                    chatRecyclerViewAdapter.notifyItemChanged(chatlist.size());
                    commentedittext.setText("");
                }
                @Override
                public void onFailure(Call<PostChatResponse> call, Throwable t) {
                    progressDialogClass.showdialog();

                }
            });

        }
    }


    public void closewebservice()
    {
        //ChatListResponse
        if (!isNetworkAvailable()) {
            Toast.makeText(ChatActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        } else {
            progressDialogClass.showdialog();
            RequestInterface requestInterface = config.retrofitRegister();
            Call<PostChatResponse> call = requestInterface.openclose(chataddid,ticketid);
            call.enqueue(new Callback<PostChatResponse>() {
                @Override
                public void onResponse(Call<PostChatResponse> call, Response<PostChatResponse> response) {
                    if(response.body().getStatus().equalsIgnoreCase("1"))
                    {
                        commentedittext.setText("");
                        Intent intent = new Intent(ChatActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }

                }
                @Override
                public void onFailure(Call<PostChatResponse> call, Throwable t) {
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

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.postbtn:
                String input=commentedittext.getText().toString().trim().replaceAll(" ", "");
                int length = input.length();
                if(commentedittext.getText().toString().equalsIgnoreCase(""))
                {
                    commentedittext.setError("Text is required");
                }
                else if(length>65)
                {
                    commentedittext.setError("Title text is not required more than 65 characters");
                }
                else
                {
                    postwebservice();
                }
                break;
            case R.id.chatstatustext:
                closewebservice();
                break;
            case R.id.chatbackarrowlayout:
                onBackPressed();
                break;
        }
    }
}
