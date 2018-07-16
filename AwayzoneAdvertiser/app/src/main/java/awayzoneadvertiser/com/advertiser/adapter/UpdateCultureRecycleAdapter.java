package awayzoneadvertiser.com.advertiser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.custominterface.AddRemoveItemInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.CulturePojo;

/**
 * Created by Devraj on 12/27/2017.
 */

public class UpdateCultureRecycleAdapter extends RecyclerView.Adapter<UpdateCultureRecycleAdapter.UpdateREcyclerView> {


    Context context;
    AddRemoveItemInterface addRemoveItemInterface;
    List<CulturePojo> list = new ArrayList<>();
    String TAG="UpdateCultureRecycleAdapter";

    public UpdateCultureRecycleAdapter(Context context, List<CulturePojo> list, AddRemoveItemInterface addRemoveItemInterface)
    {
        this.context = context;
        this.list = list;
        this.addRemoveItemInterface = addRemoveItemInterface;
    }

    @Override
    public UpdateREcyclerView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.updaterecyclerview,parent,false);
        return new UpdateREcyclerView(view) ;
    }

    @Override
    public void onBindViewHolder(UpdateREcyclerView holder, int position) {
      //  UpdateREcyclerView riskHolder = (UpdateREcyclerView)holder;
        holder.culturename.setText(list.get(position).getCulturename());
    /*    if(position >0){
            final int index = position - 1;
            final CulturePojo anIssue = list.get(index);
            riskHolder.crossbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int index = list.indexOf(anIssue);
                        list.remove(anIssue);
                        notifyItemRemoved(index);

                        //notifyDataSetChanged();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }*/



    }

    public void notifyData(List<CulturePojo> list) {


        this.list = list;

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UpdateREcyclerView extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView culturename;
        ImageView crossbtn;

        public UpdateREcyclerView(View itemView) {
            super(itemView);
            culturename = (TextView)itemView.findViewById(R.id.culturename);
            crossbtn = (ImageView) itemView.findViewById(R.id.crossbtn);
            crossbtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.crossbtn:
                 //   addRemoveItemInterface.updateaddremoveitemId(list.get(getAdapterPosition()).getId(),true);
                    addRemoveItemInterface.updateaddremoveitemId(getAdapterPosition(),list.get(getAdapterPosition()).getCultureId(),true,true);
                    break;
            }
        }
    }
}
