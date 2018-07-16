package awayzoneadvertiser.com.advertiser.gettersetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPlan {

    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("no_of_ads")
    @Expose
    private Integer noOfAds;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("start_date")
    @Expose
    private String startDate;

    @SerializedName("plan_subscription_id")
    @Expose
    private Integer id;

    @SerializedName("incrementvalue")
    @Expose
    private Integer incrementvalue;


    public UserPlan(Integer id, String planName, Integer amount, String description, Integer noOfAds, int incrementvalue,String startDate)
    {
        this.id = id;
        this.planName = planName;
        this.amount = amount;
        this.description = description;
        this.noOfAds = noOfAds;
        this.incrementvalue = incrementvalue;
        this.startDate = startDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getNoOfAds() {
        return noOfAds;
    }

    public void setNoOfAds(Integer noOfAds) {
        this.noOfAds = noOfAds;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIncrementvalue() {
        return incrementvalue;
    }
    public void setIncrementvalue(Integer incrementvalue) {
        this.incrementvalue = incrementvalue;
    }


}
