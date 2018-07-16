package awayzoneadvertiser.com.advertiser.gettersetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import awayzoneadvertiser.com.advertiser.gettersetter.InterestDatum;

public class InterestDataResponse {

    @SerializedName("culture_data")
    @Expose
    private List<InterestDatum> cultureData = null;

    public List<InterestDatum> getCultureData() {
        return cultureData;
    }

    public void setCultureData(List<InterestDatum> cultureData) {
        this.cultureData = cultureData;
    }

}
