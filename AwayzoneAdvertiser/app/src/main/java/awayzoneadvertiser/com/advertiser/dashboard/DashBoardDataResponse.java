package awayzoneadvertiser.com.advertiser.dashboard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import awayzoneadvertiser.com.advertiser.dashboard.Analytic;

public class DashBoardDataResponse {

    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("rating_sum")
    @Expose
    private Float ratingSum;
    @SerializedName("analytics")
    @Expose
    private List<Analytic> analytics = null;
    @SerializedName("get_city_data")
    @Expose
    private List<GetCityDatum> getCityData = null;
    @SerializedName("ads_data")
    @Expose
    private List<AdsDatum> adsData = null;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("last_login")
    @Expose
    private int last_login;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Float getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(Float ratingSum) {
        this.ratingSum = ratingSum;
    }

    public List<Analytic> getAnalytics() {
        return analytics;
    }

    public void setAnalytics(List<Analytic> analytics) {
        this.analytics = analytics;
    }

    public List<GetCityDatum> getGetCityData() {
        return getCityData;
    }

    public void setGetCityData(List<GetCityDatum> getCityData) {
        this.getCityData = getCityData;
    }

    public List<AdsDatum> getAdsData() {
        return adsData;
    }

    public void setAdsData(List<AdsDatum> adsData) {
        this.adsData = adsData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLast_login() {
        return last_login;
    }
    public void setLast_login(int last_login) {
        this.last_login = last_login;
    }

}
