package awayzoneadvertiser.com.advertiser.gettersetter;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Planss {

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


    public Integer getIncrementvalue() {
        return incrementvalue;
    }

    public void setIncrementvalue(Integer incrementvalue) {
        this.incrementvalue = incrementvalue;
    }

    private Integer incrementvalue;


    public Planss(Integer id, String planName, Integer amount, String description, Integer noOfAds, int incrementvalue)
    {
        this.id = id;
        this.planName = planName;
        this.amount = amount;
        this.description = description;
        this.noOfAds = noOfAds;
        this.incrementvalue = incrementvalue;
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

}
