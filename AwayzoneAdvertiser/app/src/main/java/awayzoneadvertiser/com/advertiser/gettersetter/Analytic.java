package awayzoneadvertiser.com.advertiser.gettersetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Analytic {

    @SerializedName("ad_clicks")
    @Expose
    private Integer adClicks;
    @SerializedName("impression")
    @Expose
    private Integer impression;

    public Integer getAdClicks() {
        return adClicks;
    }

    public void setAdClicks(Integer adClicks) {
        this.adClicks = adClicks;
    }

    public Integer getImpression() {
        return impression;
    }

    public void setImpression(Integer impression) {
        this.impression = impression;
    }

}
