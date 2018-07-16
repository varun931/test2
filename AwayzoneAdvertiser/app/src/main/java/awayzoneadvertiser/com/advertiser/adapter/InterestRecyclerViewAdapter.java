package awayzoneadvertiser.com.advertiser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import java.util.ArrayList;
import java.util.List;
import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.custominterface.AddRemoveItemInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.CulturePojo;

/**
 * Created by Devraj on 1/16/2018.
 */

public class InterestRecyclerViewAdapter extends RecyclerView.Adapter<InterestRecyclerViewAdapter.CultureRecyclerView> implements Filterable {

    List<CulturePojo> list = new ArrayList<>();
    List<CulturePojo> mfilterlist = new ArrayList<>();
    Context context;
    AddRemoveItemInterface addRemoveItemInterface;
    String TAG="CultureRecyclerViewAdapter";
    public InterestRecyclerViewAdapter(Context context, List<CulturePojo> list, AddRemoveItemInterface addRemoveItemInterface)
    {
        this.context = context;
        this.list = list;
        this.addRemoveItemInterface = addRemoveItemInterface;
        mfilterlist = list;
    }

    @Override
    public InterestRecyclerViewAdapter.CultureRecyclerView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.culturerecyclerview, parent, false);
        return new InterestRecyclerViewAdapter.CultureRecyclerView(view);
    }
    @Override
    public void onBindViewHolder(InterestRecyclerViewAdapter.CultureRecyclerView holder, int position) {

        if(mfilterlist.get(position).getCheck().equalsIgnoreCase("YES"))
        {
            holder.culturecheckbox.setChecked(true);
            holder.culturecheckbox.setText(mfilterlist.get(position).getCulturename());
        }
        else
        {
            holder.culturecheckbox.setChecked(false);
            holder.culturecheckbox.setText(mfilterlist.get(position).getCulturename());
        }
    }
    @Override
    public int getItemCount() {


        return mfilterlist.size();


    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mfilterlist = list;
                } else {

                    ArrayList<CulturePojo> filteredList = new ArrayList<>();

                    for (CulturePojo androidVersion : list) {

                        if (androidVersion.getCulturename().toLowerCase().contains(charString) || androidVersion.getCulturename().toUpperCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }
                    mfilterlist = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mfilterlist;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mfilterlist = (ArrayList<CulturePojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class CultureRecyclerView extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox culturecheckbox;
        public CultureRecyclerView(View itemView) {
            super(itemView);
            culturecheckbox = (CheckBox)itemView.findViewById(R.id.culturecheckbox);
            culturecheckbox.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.culturecheckbox:
                    if(culturecheckbox.isChecked())
                    {
                        // Toast.makeText(context,"Checked",Toast.LENGTH_SHORT).show();
                        addRemoveItemInterface.addremoveitemId(mfilterlist.get(getAdapterPosition()).getId(),true,true);
                    }
                    else if(!culturecheckbox.isChecked())
                    {
                        //   Toast.makeText(context,"UnChecked",Toast.LENGTH_SHORT).show();
                        addRemoveItemInterface.addremoveitemId(mfilterlist.get(getAdapterPosition()).getId(),false,true);
                    }
                    break;
            }
        }
    }
}
