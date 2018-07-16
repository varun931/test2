package awayzoneadvertiser.com.advertiser.gettersetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_data")
    @Expose
    private List<UserDatum> userData = null;
    @SerializedName("cul_response")
    @Expose
    private List<CulResponse> culResponse = null;
    @SerializedName("int_response")
    @Expose
    private List<IntResponse> intResponse = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserDatum> getUserData() {
        return userData;
    }

    public void setUserData(List<UserDatum> userData) {
        this.userData = userData;
    }

    public List<CulResponse> getCulResponse() {
        return culResponse;
    }

    public void setCulResponse(List<CulResponse> culResponse) {
        this.culResponse = culResponse;
    }

    public List<IntResponse> getIntResponse() {
        return intResponse;
    }

    public void setIntResponse(List<IntResponse> intResponse) {
        this.intResponse = intResponse;
    }

}
