package awayzoneadvertiser.com.advertiser.gettersetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CultureDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tag_name")
    @Expose
    private String tagName;

    public String getCul_name() {
        return cul_name;
    }

    public void setCul_name(String cul_name) {
        this.cul_name = cul_name;
    }

    @SerializedName("cul_name")
    @Expose
    private String cul_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
