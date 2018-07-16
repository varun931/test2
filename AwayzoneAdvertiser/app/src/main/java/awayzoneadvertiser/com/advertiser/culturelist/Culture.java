
package awayzoneadvertiser.com.advertiser.culturelist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Culture {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("culture_category_id")
    @Expose
    private Integer cultureCategoryId;
    @SerializedName("cul_name")
    @Expose
    private String culName;
    @SerializedName("flag_image")
    @Expose
    private String flagImage;
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

    public Integer getCultureCategoryId() {
        return cultureCategoryId;
    }

    public void setCultureCategoryId(Integer cultureCategoryId) {
        this.cultureCategoryId = cultureCategoryId;
    }

    public String getCulName() {
        return culName;
    }

    public void setCulName(String culName) {
        this.culName = culName;
    }

    public String getFlagImage() {
        return flagImage;
    }

    public void setFlagImage(String flagImage) {
        this.flagImage = flagImage;
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
