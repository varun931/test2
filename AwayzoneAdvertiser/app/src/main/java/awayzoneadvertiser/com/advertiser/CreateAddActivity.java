package awayzoneadvertiser.com.advertiser;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.triusinfotech.awayzoneadvertiser.util.Config;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import awayzoneadvertiser.com.advertiser.cropimage.ImagePickerActivity;
import awayzoneadvertiser.com.advertiser.custominterface.RequestInterface;
import awayzoneadvertiser.com.advertiser.gettersetter.HalfRegisterationResponse;
import awayzoneadvertiser.com.advertiser.gettersetter.ServerResponse;
import awayzoneadvertiser.com.advertiser.util.Constant;
import awayzoneadvertiser.com.advertiser.util.ProgressDialogClass;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devraj on 1/20/2018.
 */

public class CreateAddActivity extends AppCompatActivity implements View.OnClickListener{

    DatePickerDialog picker;
    TextView startdate,enddate,uploadbtn;
    RelativeLayout startdatelayout,enddatelayout,imageupload,addbackbackarrowlayout;
    int GALLERY_CODE = 1;
    private static final int REQUEST_CAMERA = 0;
    String pathphotovalidation="";
    ImageView imageuploadimg;
    EditText titleeditext,descriptionedittext;
    ProgressDialogClass progressDialogClass;
    Config config;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final int REQUEST_PICK_IMAGE = 1002;
    String imagePath="";
    int saal,endsaal;
    int mahina,endmahina;
    int din,enddin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaddactivity);
        sharedPreferences = getSharedPreferences(Constant.sharedpreffilename, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialogClass = new ProgressDialogClass(CreateAddActivity.this);
        config = new Config(this);
        startdatelayout = (RelativeLayout)findViewById(R.id.startdatelayout);
        enddatelayout = (RelativeLayout)findViewById(R.id.enddatelayout);
        imageupload = (RelativeLayout)findViewById(R.id.imageupload);
        addbackbackarrowlayout = (RelativeLayout)findViewById(R.id.addbackbackarrowlayout);
        startdate=(TextView)findViewById(R.id.startdate);
        enddate=(TextView)findViewById(R.id.enddate);
        titleeditext=(EditText) findViewById(R.id.titleeditext);
        descriptionedittext=(EditText) findViewById(R.id.descriptionedittext);
        uploadbtn=(TextView)findViewById(R.id.uploadbtn);
        imageuploadimg =(ImageView)findViewById(R.id.imageuploadimg);
        startdate.setText("START DATE");
        enddate.setText("END DATE");

        startdatelayout.setOnClickListener(this);
        enddatelayout.setOnClickListener(this);
        imageupload.setOnClickListener(this);
        uploadbtn.setOnClickListener(this);
        imageuploadimg.setOnClickListener(this);
        addbackbackarrowlayout.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.startdatelayout:
                final Calendar cldr = Calendar.getInstance();
                final int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                               saal=year;
                               mahina=monthOfYear;
                               din=dayOfMonth;
                               if(endsaal!=0) {
                                   if (endsaal < year) {
                                       Toast.makeText(CreateAddActivity.this, "Select Correct Date", Toast.LENGTH_SHORT).show();
                                       startdate.setText("START DATE");
                                   } else if (endmahina < monthOfYear) {
                                       Toast.makeText(CreateAddActivity.this, "Select Correct Date", Toast.LENGTH_SHORT).show();
                                       startdate.setText("START DATE");
                                   } else if (enddin < dayOfMonth && endmahina <= monthOfYear) {
                                       Toast.makeText(CreateAddActivity.this, "Select Correct Date", Toast.LENGTH_SHORT).show();
                                       startdate.setText("START DATE");
                                   } else {
                                       if (monthOfYear < 9) {
                                      //   startdate.setText("0" + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                           startdate.setText( (monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                       } else {
                                           startdate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                       }
                                   }
                               }
                               else
                               {
                                   if (monthOfYear < 9) {
                                     //  startdate.setText("0" + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                       startdate.setText( (monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                   } else {
                                       startdate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                   }
                               }
                               // startdate.setText("  "+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                break;
              case R.id.enddatelayout:
                final Calendar calender = Calendar.getInstance();
                int endday = calender.get(Calendar.DAY_OF_MONTH);
                int endmonth = calender.get(Calendar.MONTH);
                int endyear = calender.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                endsaal=year;
                                endmahina=monthOfYear;
                                enddin=dayOfMonth;
                                if(saal>year )
                                {
                                  Toast.makeText(CreateAddActivity.this,"Select Correct Date",Toast.LENGTH_SHORT).show();
                                    enddate.setText("END DATE");
                                }
                                else if(mahina>monthOfYear )
                                {
                                    Toast.makeText(CreateAddActivity.this,"Select Correct Date",Toast.LENGTH_SHORT).show();
                                    enddate.setText("END DATE");
                                }
                                else if(din>dayOfMonth&&mahina>=monthOfYear)
                                {
                                    Toast.makeText(CreateAddActivity.this,"Select Correct Date",Toast.LENGTH_SHORT).show();
                                    enddate.setText("END DATE");
                                }
                                else {
                                    if (monthOfYear < 9) {
                                    //    enddate.setText("0" + (monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                        enddate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                    } else {
                                        enddate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                                    }
                                }

                            }
                        }, endyear, endmonth, endday);
                picker.show();
                break;
           case R.id.imageupload:
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Camera permission has not been granted.
                        permission();
                    }
                    else
                    {
                        pickImage();
  /*                      Intent intentk = new Intent(Intent.ACTION_PICK);
                        intentk.setType("image*//*");
                        startActivityForResult(intentk, GALLERY_CODE);*/
                    }
                }
                else
                {
                    pickImage();
               /*     Intent intentk = new Intent(Intent.ACTION_PICK);
                    intentk.setType("image*//*");
                    startActivityForResult(intentk, GALLERY_CODE);*/
                }
                break;
            case R.id.imageuploadimg:
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Camera permission has not been granted.
                        permission();
                    }
                    else
                    {
                        pickImage();
                   /*     Intent intentk = new Intent(Intent.ACTION_PICK);
                        intentk.setType("image*//*");
                        startActivityForResult(intentk, GALLERY_CODE);*/
                    }
                }
                else
                {
                    pickImage();/*
                    Intent intentk = new Intent(Intent.ACTION_PICK);
                    intentk.setType("image*//*");
                    startActivityForResult(intentk, GALLERY_CODE);*/
                }
                break;
            case R.id.uploadbtn:

                String input=titleeditext.getText().toString().trim().replaceAll(" ", "");
                int length = input.length();
                String inputdes=descriptionedittext.getText().toString().trim().replaceAll(" ", "");
                int lengthdes = inputdes.length();
           if(titleeditext.getText().toString().equalsIgnoreCase(""))
            {
                titleeditext.setError("Title is required");
            }
           else if(length>25)
           {
               titleeditext.setError("Title text is not required more than 25 characters");
           }
            else if(descriptionedittext.getText().toString().equalsIgnoreCase(""))
            {
                descriptionedittext.setError("Description is required");
            }
           else if(lengthdes>256)
           {
               descriptionedittext.setError("description text is not required more than 256 characters");
           }
          else if(startdate.getText().toString().equalsIgnoreCase("START DATE"))
            {
                Toast.makeText(CreateAddActivity.this,"start date is required",Toast.LENGTH_SHORT).show();
            }
          else if(enddate.getText().toString().equalsIgnoreCase("END DATE"))
            {
                Toast.makeText(CreateAddActivity.this,"end date is required",Toast.LENGTH_SHORT).show();
            }
          //  else if(pathphotovalidation.toString().equalsIgnoreCase(""))
          else if(imagePath .toString().equalsIgnoreCase(""))
            {
               Toast.makeText(CreateAddActivity.this,"Image is required",Toast.LENGTH_SHORT).show();
            }
          else
            {
                uploadwebservice();
            }
           break;
          case R.id.addbackbackarrowlayout:
                onBackPressed();
                break;
        }
    }
    public void permission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {



            ActivityCompat.requestPermissions(CreateAddActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
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


 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG,"data is callled...."+requestCode+".....resulcode.."+resultCode+"....data.."+data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            {
                Log.e(TAG, "gallery current path=----" + pathphotovalidation);
                Uri uri = data.getData();
                Log.e("photo", uri + "");
                String path = "";
                path = getRealPathFromURI(uri);
                pathphotovalidation = path;
                imageuploadimg.setVisibility(View.VISIBLE);
                imageuploadimg.setPadding(0,0,0,0);
                Glide.with(getApplicationContext()).load(pathphotovalidation).into(imageuploadimg);
                imageupload.setVisibility(View.GONE);
            //    uploadFile(pathphotovalidation);
            }
        }
    }*/
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


    public void uploadwebservice()
    {
        File file = new File(imagePath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestInterface requestInterface = config.retrofitRegister();

        if (!isNetworkAvailable()) {
            Toast.makeText(CreateAddActivity.this, "Please Check your Internet connection", Toast.LENGTH_LONG).show();
        } else {
            progressDialogClass.showdialog();
            Log.e("MAINDATA","file...."+fileToUpload+"..filename...."+filename);
            Log.e("MAINDATA","start date"+startdate.getText().toString()+"..end data.."+enddate.getText().toString());
        Call<ServerResponse> call = requestInterface.createadd(fileToUpload,filename,sharedPreferences.getInt(Constant.USERID,0),titleeditext.getText().toString(),descriptionedittext.getText().toString());
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
/*                *//**//*    progressDialogClass.hideDialog();
                    Log.e(TAG,"response of date..."+response.body().getStatus());
                    if(response.body().getStatus().equalsIgnoreCase("1"))
                    {
                        Intent intent = new Intent(CreateAddActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }*//**//**/
                    ServerResponse serverResponse = response.body();
                    if (serverResponse != null) {
                        if (serverResponse.getSuccess()) {
                            createadd(serverResponse.getAd_id());
                            } else {
                        }
                    } else {
                        assert serverResponse != null;
                 //       Log.e(TAG,"response photo "+ serverResponse.toString());
                    }
                }
                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    progressDialogClass.hideDialog();
                }
            });
        }
    }
    public void createadd(int createad)
    {
            RequestInterface requestInterface = config.retrofitRegister();
            Call<HalfRegisterationResponse> call = requestInterface.creataddfully(createad,startdate.getText().toString(),enddate.getText().toString());
            call.enqueue(new Callback<HalfRegisterationResponse>() {
                @Override
                public void onResponse(Call<HalfRegisterationResponse> call, Response<HalfRegisterationResponse> response) {
                    progressDialogClass.hideDialog();
                    if(response.body().getStatus().equalsIgnoreCase("1")) {
                        Intent intent = new Intent(CreateAddActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                    else
                    {
                    }
                }
                @Override
                public void onFailure(Call<HalfRegisterationResponse> call, Throwable t) {
                    progressDialogClass.hideDialog();
                }
            });
        }
   // Private class isNetworkAvailable
    private boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void pickImage() {
        startActivityForResult(new Intent(this, ImagePickerActivity.class), REQUEST_PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                     imagePath = data.getStringExtra("image_path");
                    setImage(imagePath);
                    break;
            }
        } else {
            System.out.println("Failed to load image");
        }
    }
    private void setImage(String imagePath) {
        imageuploadimg.setVisibility(View.VISIBLE);
        imageuploadimg.setImageBitmap(getImageFromStorage(imagePath));
    }
    private Bitmap getImageFromStorage(String path) {
        try {
            File f = new File(path);
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 512, 512);

            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
