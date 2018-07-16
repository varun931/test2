
package awayzoneadvertiser.com.advertiser.culturelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResponse {

    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("culture_data")
    @Expose
    private List<CultureDatum> cultureData = null;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<CultureDatum> getCultureData() {
        return cultureData;
    }

    public void setCultureData(List<CultureDatum> cultureData) {
        this.cultureData = cultureData;
    }

}
