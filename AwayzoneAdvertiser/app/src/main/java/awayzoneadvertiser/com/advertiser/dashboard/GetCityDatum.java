package awayzoneadvertiser.com.advertiser.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCityDatum {

    @SerializedName("get_city")
    @Expose
    private String getCity;
    @SerializedName("get_city_count")
    @Expose
    private Float getCityCount;

    public String getGetCity() {
        return getCity;
    }

    public void setGetCity(String getCity) {
        this.getCity = getCity;
    }

    public Float getGetCityCount() {
        return getCityCount;
    }

    public void setGetCityCount(Float getCityCount) {
        this.getCityCount = getCityCount;
    }

}
