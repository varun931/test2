package awayzoneadvertiser.com.advertiser.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import awayzoneadvertiser.com.advertiser.R;
import awayzoneadvertiser.com.advertiser.culturelist.CountryList;
import awayzoneadvertiser.com.advertiser.culturelist.ListResponse;
import awayzoneadvertiser.com.advertiser.culturelist.PrintSparseArrays;
import awayzoneadvertiser.com.advertiser.custominterface.CultureListInterface;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryAdapterList extends BaseExpandableListAdapter {

	/**
	 * Multiple choice for all the groups
	 */
	CheckedTextView text1;

	public static final int CHOICE_MODE_MULTIPLE = AbsListView.CHOICE_MODE_MULTIPLE;

	// TODO: Coverage this case
	// Example:
	//https://github.com/commonsguy/cw-omnibus/blob/master/ActionMode/ActionModeMC/src/com/commonsware/android/actionmodemc/ActionModeDemo.java

	public static final int CHOICE_MODE_MULTIPLE_MODAL = AbsListView.CHOICE_MODE_MULTIPLE_MODAL;

	/**
	 * No child could be selected
	 */

	public static final int CHOICE_MODE_NONE = AbsListView.CHOICE_MODE_NONE;

	/**
	 * One single choice per group
	 */

	public static final int CHOICE_MODE_SINGLE_PER_GROUP = AbsListView.CHOICE_MODE_SINGLE;

	/**
	 * One single choice for all the groups
	 */

	public static final int CHOICE_MODE_SINGLE_ABSOLUTE = 10001;

	private static final String LOG_TAG = CountryAdapterList.class.getSimpleName();
	private List<CountryList> countries;
	private LayoutInflater inflater;
	private SparseArray<SparseBooleanArray> checkedPositions;

	// The default choice is the multiple one
//	private int choiceMode = CHOICE_MODE_MULTIPLE;;
	private int choiceMode = CHOICE_MODE_SINGLE_ABSOLUTE;;

	CultureListInterface cultureListInterface;
	List<String> mainlist = new ArrayList<>();
	CheckedTextView delete;

	public CountryAdapterList(Context context, List<CountryList> countries, CultureListInterface cultureListInterface) {
		this.countries = countries;
		inflater = LayoutInflater.from(context);
		checkedPositions = new SparseArray<SparseBooleanArray>();
		this.cultureListInterface= cultureListInterface;
	}

	/*public CountryAdapterList(Context context, List<CountryList> countries, int choiceMode) {
		this(context, countries);
		// For now the choice mode CHOICE_MODE_MULTIPLE_MODAL
		// is not implemented
		if (choiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
			throw new RuntimeException("The choice mode CHOICE_MODE_MULTIPLE_MODAL " +
					"has not implemented yet");
		}
		this.choiceMode = choiceMode;
	}*/

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return countries.get(groupPosition).getCities().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.democheckbox, parent, false);

		//	convertView = inflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
		}

		CheckedTextView checkedTextView = (CheckedTextView)convertView.findViewById(R.id.text1);
		ImageView flagimageview = (ImageView) convertView.findViewById(R.id.flagimageview);

		//((CheckedTextView)convertView).setText(getChild(groupPosition, childPosition).toString());
		checkedTextView.setText(getChild(groupPosition, childPosition).toString());
		if (checkedPositions.get(groupPosition) != null) {
			boolean isChecked = checkedPositions.get(groupPosition).get(childPosition);

			checkedTextView.setChecked(isChecked);
//			((CheckedTextView)convertView).setChecked(isChecked);
		// If it does not exist, mark the checkBox as false
			if(isChecked)
			{
				mainlist.add(getChild(groupPosition, childPosition).toString());
				cultureListInterface.totallist(mainlist);
				culturewebservicedata(convertView,flagimageview,getChild(groupPosition, childPosition).toString());
			}
			else if(!isChecked)
			{
			//	mainlist.add(getChild(groupPosition, childPosition).toString());
				culturewebservicedata(convertView,flagimageview,getChild(groupPosition, childPosition).toString());
				for(int j=0; j<mainlist.size();j++)
				{
					if(getChild(groupPosition, childPosition).toString().equalsIgnoreCase(mainlist.get(j)))
					{
						mainlist.remove(j);
						cultureListInterface.totallist(mainlist);
					}
				}
			}
		//	mainlist.add(getChild(groupPosition, childPosition).toString());
		} else {
		/*	for(int j=0; j<mainlist.size();j++)
			{
				mainlist.remove(j);
			}
			cultureListInterface.totallist(mainlist);*/
			culturewebservicedata(convertView,flagimageview,getChild(groupPosition, childPosition).toString());
			checkedTextView.setChecked(false);
//			((CheckedTextView)convertView).setChecked(false);
		}
		return convertView;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return countries.get(groupPosition).getCities().size();
	}
	@Override
	public Object getGroup(int groupPosition) {
		return countries.get(groupPosition).getName();
	}
	@Override
	public int getGroupCount() {
		return countries.size();
	}
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
		}
		((TextView)convertView).setText(getGroup(groupPosition).toString());
		return convertView;
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/**
	 * Update the list of the positions checked and update the view
	 * @param groupPosition The position of the group which has been checked
	 * @param childPosition The position of the child which has been checked
	 */
	public void setClicked(int groupPosition, int childPosition) {
		switch (choiceMode) {
		case CHOICE_MODE_MULTIPLE:
			SparseBooleanArray checkedChildPositionsMultiple = checkedPositions.get(groupPosition);
			// if in the group there was not any child checked
			if (checkedChildPositionsMultiple == null) {
				checkedChildPositionsMultiple = new SparseBooleanArray();
				// By default, the status of a child is not checked
				// So a click will enable it
				checkedChildPositionsMultiple.put(childPosition, true);
				checkedPositions.put(groupPosition, checkedChildPositionsMultiple);
			} else {
				boolean oldState = checkedChildPositionsMultiple.get(childPosition);
				checkedChildPositionsMultiple.put(childPosition, !oldState);
			}
			break;
		// TODO: Implement it
		case CHOICE_MODE_MULTIPLE_MODAL:
			throw new RuntimeException("The choice mode CHOICE_MODE_MULTIPLE_MODAL " +
					"has not implemented yet");
		case CHOICE_MODE_NONE:
			checkedPositions.clear();
			break;
		case CHOICE_MODE_SINGLE_PER_GROUP:
			SparseBooleanArray checkedChildPositionsSingle = checkedPositions.get(groupPosition);
			// If in the group there was not any child checked
			if (checkedChildPositionsSingle == null) {
				checkedChildPositionsSingle = new SparseBooleanArray();
				// By default, the status of a child is not checked
				checkedChildPositionsSingle.put(childPosition, true);
				checkedPositions.put(groupPosition, checkedChildPositionsSingle);
			} else {
				boolean oldState = checkedChildPositionsSingle.get(childPosition);
				// If the old state was false, set it as the unique one which is true
				if (!oldState) {
					checkedChildPositionsSingle.clear();
					checkedChildPositionsSingle.put(childPosition, !oldState);
				} // Else does not allow the user to uncheck it
			}
			break;
		// This mode will remove all the checked positions from other groups
		// and enable just one from the selected group
		case CHOICE_MODE_SINGLE_ABSOLUTE:
			checkedPositions.clear();
			SparseBooleanArray checkedChildPositionsSingleAbsolute = new SparseBooleanArray();
			checkedChildPositionsSingleAbsolute.put(childPosition, true);
			checkedPositions.put(groupPosition, checkedChildPositionsSingleAbsolute);
			break;
		}

		// Notify that some data has been changed
		notifyDataSetChanged();
		Log.v(LOG_TAG, PrintSparseArrays.sparseArrayToString(checkedPositions));
	}

	public List<String>  returnlist()
	{
		return  mainlist;
	}

	public int getChoiceMode() {
		return choiceMode;
	}

	/**
	 * Set a new choice mode. This will remove
	 * all the checked positions
	 * @param choiceMode
	 */
	public void setChoiceMode(int choiceMode) {
		this.choiceMode = choiceMode;
		// For now the choice mode CHOICE_MODEL_MULTIPLE_MODAL
		// is not implemented
		if (choiceMode == CHOICE_MODE_MULTIPLE_MODAL) {
			throw new RuntimeException("The choice mode CHOICE_MODE_MULTIPLE_MODAL " +
					"has not implemented yet");
		}
		checkedPositions.clear();
	}

	/**
	 * Method used to get the actual state of the checked lists
	 * @return The list of the all the positions checked
	 */
	public SparseArray<SparseBooleanArray> getCheckedPositions() {
		return checkedPositions;
	}





	public void culturewebservicedata(final View view, final ImageView imageView, String text)
	{
		RequestInterface requestInterface=retrofitRegister();
		retrofit2.Call<ListResponse> call = requestInterface.flag(text);
		call.enqueue(new Callback<ListResponse>() {
			@Override
			public void onResponse(retrofit2.Call<ListResponse> call, Response<ListResponse> response) {

				RequestOptions requestOptions = new RequestOptions();
				requestOptions.placeholder(R.mipmap.ic_launcher_round);
				requestOptions.error(R.mipmap.ic_launcher_round);

				Glide.with(view)
						.setDefaultRequestOptions(requestOptions)
						.load(response.body().getImageUrl()).apply(RequestOptions.circleCropTransform()).into(imageView);


			//	Glide.with(view).load(response.body().getImageUrl()).apply(RequestOptions.circleCropTransform()).into(imageView);

			}
			@Override
			public void onFailure(retrofit2.Call<ListResponse> call, Throwable t) {
			}
		});
	}


	public RequestInterface retrofitRegister()
	{
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(1, TimeUnit.MINUTES)
				.readTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(15, TimeUnit.SECONDS)
				.build();
		//  Retrofit retrofit = new Retrofit.Builder().baseUrl("http://triusinfo.com/away_zone/users/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://173.255.247.199/away_zone/users/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
		//  Retrofit retrofit = new Retrofit.Builder().baseUrl("http://122.180.20.185:91/skilltoskill/backend/web/api/user/").addConverterFactory(GsonConverterFactory.create()).build();
		RequestInterface requestInterface = retrofit.create(RequestInterface.class);
		return  requestInterface;
	}



}
