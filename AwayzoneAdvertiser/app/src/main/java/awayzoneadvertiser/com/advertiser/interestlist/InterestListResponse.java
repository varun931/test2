
package awayzoneadvertiser.com.advertiser.interestlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InterestListResponse {

    @SerializedName("interest_data")
    @Expose
    private List<InterestDatum> interestData = null;

    public List<InterestDatum> getInterestData() {
        return interestData;
    }

    public void setInterestData(List<InterestDatum> interestData) {
        this.interestData = interestData;
    }

}
