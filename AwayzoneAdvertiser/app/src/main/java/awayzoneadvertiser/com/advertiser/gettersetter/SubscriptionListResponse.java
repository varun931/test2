package awayzoneadvertiser.com.advertiser.gettersetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubscriptionListResponse {

    @SerializedName("user_plan")
    @Expose
    private List<UserPlan> userPlan = null;
    @SerializedName("plan")
    @Expose
    private List<Plan> plan = null;


    public List<UserPlan> getUserPlan() {
        return userPlan;
    }
    public void setUserPlan(List<UserPlan> userPlan) {
        this.userPlan = userPlan;
    }

    public List<Plan> getPlan() {
        return plan;
    }

    public void setPlan(List<Plan> plan) {
        this.plan = plan;
    }

}
