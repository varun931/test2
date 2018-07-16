package awayzoneadvertiser.com.advertiser.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Devraj on 1/9/2018.
 */

public class GCMTOKEN extends FirebaseInstanceIdService {
    public static String TAG = "FCMToken";

    @Override
    public void onTokenRefresh(){
        super.onTokenRefresh();
        getToken(GCMTOKEN.this);
//        sendRegistrationToServer(token);

    }

//    private void sendRegistrationToServer(String token){
//        editor.putString(MyConstants.DeviceID,token);
//        editor.commit();
//    }

    public static void getToken(Context context){
        String token= FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences=context.getSharedPreferences(Constant.sharedpreffilename,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Constant.DEVICEID,token);
        editor.commit();
    }
}
