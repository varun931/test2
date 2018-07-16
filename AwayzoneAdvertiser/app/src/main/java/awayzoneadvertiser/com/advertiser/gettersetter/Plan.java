package awayzoneadvertiser.com.advertiser.gettersetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plan {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("no_of_ads")
    @Expose
    private Integer noOfAds;

    @SerializedName("incrementvalue")
    @Expose
    private Integer incrementvalue;

    @SerializedName("start_date")
    @Expose
    private String startDate;



    @SerializedName("userdata")
    @Expose
    private String  userdata;







    public Plan(Integer id, String planName, Integer amount, String description, Integer noOfAds, int incrementvalue,String startDate,String userdata)
    {
        this.id = id;
        this.planName = planName;
        this.amount = amount;
        this.description = description;
        this.noOfAds = noOfAds;
        this.incrementvalue = incrementvalue;
        this.startDate = startDate;
        this.userdata = userdata;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfAds() {
        return noOfAds;
    }

    public void setNoOfAds(Integer noOfAds) {
        this.noOfAds = noOfAds;
    }


    public Integer getIncrementvalue() {
        return incrementvalue;
    }

    public void setIncrementvalue(Integer incrementvalue) {
        this.incrementvalue = incrementvalue;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getUserdata() {
        return userdata;
    }

    public void setUserdata(String userdata) {
        this.userdata = userdata;
    }
}
