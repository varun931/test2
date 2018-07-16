package awayzoneadvertiser.com.advertiser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.dashboard.AdsDatum;

/**
 * Created by Devraj on 12/11/2017.
 */

public class ViewPagerOneFragment extends Fragment {

    ImageView imageview;
    TextView impression,click;

    String image,impressiondata,clickdata;
    LinearLayout mainview;

    List<AdsDatum> adlist  = new ArrayList<>();

    public ViewPagerOneFragment()
    {

    }

    @SuppressLint("ValidFragment")
    public ViewPagerOneFragment(String image, String impressiondata, String clickdata, List<AdsDatum> adlist )
    {
        this.adlist = adlist;
        this.image = image;
        this.impressiondata = impressiondata;
        this.clickdata = clickdata;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.viewpagerfragment,container,false);
        imageview = (ImageView)view.findViewById(R.id.imageview);
        mainview = (LinearLayout) view.findViewById(R.id.mainview);
        impression = (TextView)view.findViewById(R.id.impression);
        click = (TextView)view.findViewById(R.id.click);
        Glide.with(this).load("http://173.255.247.199/away_zone/webroot/img/ads_image/"+image).into(imageview);
        impression.setText(impressiondata);
        click.setText(clickdata);


       /* mainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),DetailScreenActivity.class);
                startActivity(intent);

            }
        });*/




        return view;
    }
}
