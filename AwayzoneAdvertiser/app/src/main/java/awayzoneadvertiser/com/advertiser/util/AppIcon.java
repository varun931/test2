package awayzoneadvertiser.com.advertiser.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Devraj on 12/6/2017.
 */

public class AppIcon extends android.support.v7.widget.AppCompatTextView {

    Context context;

    public AppIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }
    public void init() {

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "icon/appicon.ttf");
        setTypeface(typeface);

    }
}

