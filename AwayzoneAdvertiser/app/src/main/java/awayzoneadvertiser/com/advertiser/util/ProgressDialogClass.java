package awayzoneadvertiser.com.advertiser.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import awayzoneadvertiser.com.advertiser.R;


/**
 * Created by Devraj on 12/12/2017.
 */

public class ProgressDialogClass {
    Dialog pDialog;
    public ProgressDialogClass(Context context){
        pDialog = new Dialog(context);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.progress_view);
        pDialog.setCancelable(false);
    }
    public  void showdialog(){
        pDialog.show();
    }
    public void hideDialog(){
        if (pDialog!=null){
            pDialog.dismiss();
        }
    }


}
