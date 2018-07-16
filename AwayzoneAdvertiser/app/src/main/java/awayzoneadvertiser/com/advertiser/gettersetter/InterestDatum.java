package awayzoneadvertiser.com.advertiser.gettersetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterestDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("specific_name")
    @Expose
    private String specificName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecificName() {
        return specificName;
    }

    public void setSpecificName(String specificName) {
        this.specificName = specificName;
    }

}
