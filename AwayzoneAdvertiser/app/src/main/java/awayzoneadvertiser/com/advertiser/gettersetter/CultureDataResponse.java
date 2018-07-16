package awayzoneadvertiser.com.advertiser.gettersetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CultureDataResponse {

    @SerializedName("culture_data")
    @Expose
    private List<CultureDatum> cultureData = null;

    public List<CultureDatum> getCultureData() {
        return cultureData;
    }

    public void setCultureData(List<CultureDatum> cultureData) {
        this.cultureData = cultureData;
    }

}
