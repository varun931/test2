package awayzoneadvertiser.com.advertiser;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import com.triusinfotech.awayzoneadvertiser.util.PlaceArrayAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.ServerResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.GpsTracker;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 12/4/2017.
 */

public class ProfileStepOneActivity extends AppCompatActivity implements View.OnClickListener ,ActivityCompat.OnRequestPermissionsResultCallback,GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks
     {
    ImageView imageview;
    EditText usernameedittxt,contactedittext,nameperonedittext;
    RelativeLayout profilefirstnextbtn;
   // Boolean data=true;
    String result="";
    AutoCompleteTextView locationtextview;
    String pathphotovalidation="";
    String responsebody;
    FrameLayout imglayout;
    Config config;
    int GALLERY_CODE = 1;
    ProgressDialogClass progressDialogClass;
    EditText emailedittext;

    //get location
    private static final int GOOGLE_API_CLIENT_ID = 0;
    String usertype="";
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
    new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    boolean locationvalue=true;
    SharedPreferences sharedPreferences,sharedPreferencess;
    SharedPreferences.Editor editor,editors;
    LinearLayout mainlayout;
  private static final int REQUEST_CAMERA = 0;
         /**
          * Id to identify a contacts permission request.
          */
   private static final int REQUEST_CONTACTS = 1;

   GpsTracker locationTracker;
   double latitude;
   double longitude;
   double from_lat;
   double from__lng;
   Geocoder geocoder;
   String locationName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesteponeactivity);
        mainlayout = (LinearLayout)findViewById(R.id.mainlayout);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sharedPreferencess = getSharedPreferences(Constant.sharedpreffilenames, Context.MODE_PRIVATE);
        editors = sharedPreferencess.edit();

        imageview = (ImageView)findViewById(R.id.imageview);
        profilefirstnextbtn = (RelativeLayout) findViewById(R.id.profilefirstnextbtn);
        locationtextview = (AutoCompleteTextView)findViewById(R.id.locationtextview);
        emailedittext = (EditText)findViewById(R.id.emailedittext);
        contactedittext = (EditText)findViewById(R.id.contactedittext);
        nameperonedittext = (EditText)findViewById(R.id.nameperonedittext);
        usernameedittxt = (EditText)findViewById(R.id.usernameedittxt);
        imglayout = (FrameLayout)findViewById(R.id.imglayout);
        config = new Config(ProfileStepOneActivity.this);
        String data = sharedPreferences.getString(Constant.FBIMAGEURL,"");
       // Glide.with(this).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(imageview);
        usertype= sharedPreferences.getString(Constant.HAFLREGTYPE,"");

        progressDialogClass = new ProgressDialogClass(this);
       // config.dwonloadimage();
        if(usertype.equalsIgnoreCase(Constant.FACEBOOK))
        {
            usernameedittxt.setText(sharedPreferences.getString(Constant.FBUSERNAME,""));
            emailedittext.setText(sharedPreferences.getString(Constant.FBEMAIL,""));
            editor.putString(Constant.HAFLREGTYPE,Constant.FACEBOOK);
            editor.commit();
            Glide.with(this).load(sharedPreferences.getString(Constant.FBIMAGEURL,"")).apply(RequestOptions.circleCropTransform()).into(imageview);
            new DownloadFilesTask().execute();
        }
        else if(usertype.equalsIgnoreCase(Constant.TWITTER))
        {
            usernameedittxt.setText(sharedPreferences.getString(Constant.TWNAME,""));
            emailedittext.setText(sharedPreferences.getString(Constant.TWEMAIL,""));
           editor.putString(Constant.HAFLREGTYPE,Constant.TWITTER);
            editor.commit();

          Glide.with(this).load(sharedPreferences.getString(Constant.TWIMAGEURL,"")).apply(RequestOptions.circleCropTransform()).into(imageview);


          //  getBitmapFromURL(config.getSharedPref(Constant.TWIMAGE));
           new DownloadFilesTask().execute();
        }
        else if(usertype.equalsIgnoreCase(Constant.EMAIL))
        {
        //    usernameedittxt.setText(config.getSharedPref(Constant.TWNAME));
            emailedittext.setText(sharedPreferences.getString(Constant.REGEMAIL,""));
            editor.putString(Constant.HAFLREGTYPE,Constant.EMAIL);
            editor.commit();
            Glide.with(this).load(R.drawable.sundar).apply(RequestOptions.circleCropTransform()).into(imageview);
        }
        profilefirstnextbtn.setOnClickListener(this);
     /*   if(!sharedPreferencess.getBoolean(Constant.locationPermission,false))
        {
            config.permissionAlert();
        }
        else
        {
          permission();
        }*/
       showAddressData();


        imglayout.setOnClickListener(this);
        // current lcoaiton
        locationtextview.setThreshold(3);
       // mNameView = (TextView) findViewById(R.id.name);
        mGoogleApiClient = new GoogleApiClient.Builder(ProfileStepOneActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        locationtextview.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        locationtextview.setAdapter(mPlaceArrayAdapter);
       // uploadFile(imagepath);
    }

  // curren location
  private AdapterView.OnItemClickListener mAutocompleteClickListener
          = new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          locationvalue=true;
          final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
          final String placeId = String.valueOf(item.placeId);
          PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                  .getPlaceById(mGoogleApiClient, placeId);
          placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

      }
  };

    //   curren location
         private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
                 = new ResultCallback<PlaceBuffer>() {
             @Override
             public void onResult(PlaceBuffer places) {
                 if (!places.getStatus().isSuccess()) {
                     return;
                 }
                 final Place place = places.get(0);
                 CharSequence attributions = places.getAttributions();
                 LatLng latlng= place.getLatLng();
                 double latitude=latlng.latitude;
                 double longitude=latlng.longitude;

                 editor.putString(Constant.PROFILELAN,longitude+"");
                 editor.putString(Constant.PROFILELAT,latitude+"");
                 editor.commit();


          //       locationtextview.setText(place.getAddress()+"");
                 //     mNameView.setText(Html.fromHtml(place.getAddress() + ""));
             }
         };
    // onactivityresult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            {
                Uri uri = data.getData();
                String path = "";
                path = getRealPathFromURI(uri);
                pathphotovalidation = path;
                imageview.setPadding(0,0,0,0);
                Glide.with(getApplicationContext()).load(pathphotovalidation).apply(RequestOptions.circleCropTransform()).into(imageview);
                uploadFile(pathphotovalidation);
            }
        }
    }
    // image path
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor =this.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    @Override
    protected void onResume() {
        super.onResume();
        //config.permission();
    }
   @Override
   public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.profilefirstnextbtn:
                if(usernameedittxt.getText().toString().equalsIgnoreCase(""))
                {
                    usernameedittxt.setError("Organization is required");
                //    uploadFile(imagepath);
                }
                else if(locationtextview.getText().toString().equalsIgnoreCase("")||locationtextview.getText().toString().length()<3)
                {
                    locationtextview.setError("Location is required");
                    locationvalue=false;
                }
                else if(!locationvalue)
                {
                    locationtextview.setError("select a correct Location");
                }
                else if(sharedPreferences.getString(Constant.PROFILELAT,"").equalsIgnoreCase("")||sharedPreferences.getString(Constant.PROFILELAN,"").equalsIgnoreCase(""))
                {
                    locationtextview.setError("Location is required");
                }
                else if(emailedittext.getText().toString().equalsIgnoreCase(""))
                {
                    if(config.isValidEmail(emailedittext.getText().toString()))
                    {
                        emailedittext.setError("Enter a valid Email");
                    }
                    else
                    {
                        nextscreen();
                    }
                }
                else
                {
                    nextscreen();
                }
                break;
            case R.id.imglayout:

                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Camera permission has not been granted.

                        permission();

                    }
                    else
                    {

                        Intent intentk = new Intent(Intent.ACTION_PICK);
                        intentk.setType("image/*");
                        startActivityForResult(intentk, GALLERY_CODE);
                    }
                }
                else
                {

                    Intent intentk = new Intent(Intent.ACTION_PICK);
                    intentk.setType("image/*");
                    startActivityForResult(intentk, GALLERY_CODE);
                }
                break;
        }
    }
    //next screen
     public void nextscreen()
     {
        editor.putString(Constant.NAMEROFORGANISATION, usernameedittxt.getText().toString());
         editor.putString(Constant.PROFILEEMAIL,emailedittext.getText().toString());
         editor.putString(Constant.CONTACTNUMBER,contactedittext.getText().toString());
         editor.putString(Constant.PROFILENAME,nameperonedittext.getText().toString());
         editor.commit();
         Intent intent = new Intent(ProfileStepOneActivity.this,ProfileStepTwoActivity.class);
         startActivity(intent);
     }
    // permision onactivityreult
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for(String permission: permissions){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                if(permission.equalsIgnoreCase("android.permission.ACCESS_COARSE_LOCATION"))
                {
                    editors.putString(Constant.READ_EXTERNAL_STORAGE,"NO");
                    editors.commit();
                }
                else if(permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE"))
                {
                    editors.putString(Constant.ACCESS_COARSE_LOCATION,"NO");
                    editors.commit();
                }
            }else{
                if(ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){

                    if(permission.equalsIgnoreCase("android.permission.ACCESS_COARSE_LOCATION"))
                    {
                        editors.putString(Constant.READ_EXTERNAL_STORAGE,"YES");
                        editors.commit();

                    }
                    else if(permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE"))
                    {
                        editors.putString(Constant.ACCESS_COARSE_LOCATION,"YES");
                        editors.commit();
                    }
                    //      showAddressData();
                } else{
                    if(permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE"))
                    {
                        config.permissionAlert();
                        editors.putBoolean(Constant.ImagePermission,false);
                        editors.commit();
                    }

                }
            }
        }

    }

         public static boolean verifyPermissions(int[] grantResults) {
             // At least one result must be checked.
             if (grantResults.length < 1) {
                 return false;
             }

             // Verify that each required permission has been granted, otherwise return false.
             for (int result : grantResults) {
                 if (result != PackageManager.PERMISSION_GRANTED) {
                     return false;
                 }
             }
             return true;
         }


    // Uploading Image/Video
    public  void uploadFile(String path) {
        progressDialogClass.showdialog();
       File file = new File(path);
       // File file = new File(config.getSharedPref(Constant.FBIMAGEURL));
       // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestInterface requestInterface = config.retrofitRegister();

        Call<ServerResponse> call = requestInterface.uploadFile(fileToUpload, filename,sharedPreferences.getInt(Constant.USERID,0));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                progressDialogClass.hideDialog();
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(ProfileStepOneActivity.this,"Image Upload Sucessfull", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                } else {
                    assert serverResponse != null;
                }
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progressDialogClass.hideDialog();
            }
        });
    }
   public void permission() {
       if (ActivityCompat.shouldShowRequestPermissionRationale(this,
               Manifest.permission.WRITE_EXTERNAL_STORAGE)) {



           ActivityCompat.requestPermissions(ProfileStepOneActivity.this,
                   new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                   REQUEST_CAMERA);
       } else {
           // Camera permission has not been granted yet. Request it directly.



           ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                   REQUEST_CAMERA);
       }
        /*
             if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                 if (!config.checkLocationPermission()) {
                     ActivityCompat.requestPermissions(ProfileStepOneActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                 } else if (!config.checkLocationPermission2()) {
                     ActivityCompat.requestPermissions(ProfileStepOneActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                 }
                 else if (!sharedPreferences.getBoolean(Constant.locationPermission,false)) {
                     config.permissionAlert();
                 }
                 else if(!sharedPreferences.getBoolean(Constant.ImagePermission,false))
                 {
                     config.permissionImageAlert();
                 }
                 else {
                     config.permissiontrue();
                 }
             }*/
         }
         private class DownloadFilesTask extends AsyncTask<Void, Void, String> {
             protected String doInBackground(Void... urls) {
                 String imageurl="";
                 URL url = null;
                 try {
                     if(usertype.equalsIgnoreCase("facebook"))
                     {
                         url = new URL(sharedPreferences.getString(Constant.FBIMAGEURL,""));
                     }
                     else if(usertype.equalsIgnoreCase("twitter"))
                     {
                         url = new URL(sharedPreferences.getString(Constant.TWIMAGEURL,""));
                     }
                     Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                     imageurl=   saveFacebookImage(image);
                 } catch (MalformedURLException e) {
                     e.printStackTrace();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 return imageurl;
             }
             protected void onProgressUpdate(Integer... progress) {

             }
             protected void onPostExecute(String result) {
                 uploadFile(result);
             }
         }
         public String saveFacebookImage(Bitmap source) {
             Bitmap bitmap = source;
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
             File direct = new File(Environment.getExternalStorageDirectory() + "/Awayzone/FacebookImages");
             if (!direct.exists())
             {
                 File wallpaperDirectory = new File(Environment.getExternalStorageDirectory()+"/Awayzone/FacebookImages/");
                 wallpaperDirectory.mkdirs();
             }
//      CommonMethod.createImagesHiddenFile();
             Date date = new Date();
             CharSequence s = android.text.format.DateFormat.format("MM-dd-yyhh-mm-ss", date.getTime());
             String imagename = "myImage-" + s.toString() + ".png";
             File galleryFile = new File(new File(Environment.getExternalStorageDirectory()+"/Awayzone/FacebookImages/"), imagename);
             try
             {
                 FileOutputStream fileOutputStream = new FileOutputStream(galleryFile);
                 fileOutputStream.write(byteArrayOutputStream.toByteArray());
                 fileOutputStream.flush();
                 fileOutputStream.close();
             }
             catch(FileNotFoundException e)
             {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();

             }
             String path = "/sdcard/Awayzone/FacebookImages/" + imagename;
             return  path;
         }
         @Override
         public void onConnected(@Nullable Bundle bundle) {
             mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
         }
         @Override
         public void onConnectionSuspended(int i) {
             mPlaceArrayAdapter.setGoogleApiClient(null);
         }
         @Override
         public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
             Toast.makeText(this,
                     "Google Places API connection failed with error code:" +
                             connectionResult.getErrorCode(),
                     Toast.LENGTH_LONG).show();
         }
         public void showAddressData() {
             locationTracker = new GpsTracker(ProfileStepOneActivity.this);
             latitude = locationTracker.getLatitude();
             longitude = locationTracker.getLongitude();
             from_lat = latitude;
             from__lng=latitude;
             if (latitude!=0.0 ||longitude!=0.0) {
//            saveSharedPref(Constant.PROFILELAT,latitude+"");
//            saveSharedPref(Constant.PROFILELAN,longitude+"");
                 geoderclass(latitude,longitude);
             }
             else {

             }
         }
         public void geoderclass(double lat, double lang)
         {
             // latitude=30.7333; longitude=76.7794;
             editor.putString(Constant.PROFILELAT,lat+"");
             editor.putString(Constant.PROFILELAN,lang+"");
             editor.commit();
             geocoder = new Geocoder(ProfileStepOneActivity.this, Locale.getDefault());
             try {
                 List<Address> addressList = geocoder.getFromLocation(
                         lat, lang, 1);
                 Address address = addressList.get(0);
                 StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
//                sb.append(address.getAddressLine(i)).append("\n");
//            }
//            sb.append(address.getLocality()).append("\n");
//            sb.append(address.getPostalCode()).append("\n");
//           sb.append(address.getCountryName());
                 sb.append(address.getSubAdminArea()).append("\n");
                 sb.append(address.getSubLocality()).append("\n");
                 sb.append(address.getAdminArea()).append("\n");
//            sb.append(address.getFeatureName()).append("\n");



                 locationtextview.setText(address.getSubLocality());
                 //   result = sb.toString();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }







     }
