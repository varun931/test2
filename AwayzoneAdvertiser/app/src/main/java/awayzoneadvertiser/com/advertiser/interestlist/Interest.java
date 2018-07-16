
package awayzoneadvertiser.com.advertiser.interestlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Interest {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("interest_category_id")
    @Expose
    private Integer interestCategoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInterestCategoryId() {
        return interestCategoryId;
    }

    public void setInterestCategoryId(Integer interestCategoryId) {
        this.interestCategoryId = interestCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
