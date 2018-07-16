package awayzoneadvertiser.com.advertiser.gettersetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponse {

    @SerializedName("culture_data")
    @Expose
    private List<CultureDatum> cultureData = null;
    @SerializedName("interest")
    @Expose
    private List<Interest> interest = null;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("lati")
    @Expose
    private Float lati;
    @SerializedName("longi")
    @Expose
    private Float longi;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("ads")
    @Expose
    private Integer ads;
    @SerializedName("organization_name")
    @Expose
    private String organizationName;
    @SerializedName("contact_no")
    @Expose
    private String contactNo;

    @SerializedName("no_of_ads")
    @Expose
    private int no_of_ads;

    @SerializedName("default_profile_image")
    @Expose
    private String default_profile_image;



    @SerializedName("user_description")
    @Expose
    private String user_description;




    @SerializedName("alias_name")
    @Expose
    private String alias_name;

    public List<CultureDatum> getCultureData() {
        return cultureData;
    }
    public void setCultureData(List<CultureDatum> cultureData) {
        this.cultureData = cultureData;
    }

    public List<Interest> getInterest() {
        return interest;
    }

    public void setInterest(List<Interest> interest) {
        this.interest = interest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Float getLati() {
        return lati;
    }

    public void setLati(Float lati) {
        this.lati = lati;
    }

    public Float getLongi() {
        return longi;
    }

    public void setLongi(Float longi) {
        this.longi = longi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getAds() {
        return ads;
    }

    public void setAds(Integer ads) {
        this.ads = ads;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getNo_of_ads() {
        return no_of_ads;
    }

    public void setNo_of_ads(int no_of_ads) {
        this.no_of_ads = no_of_ads;
    }


    public String getDefault_profile_image() {
        return default_profile_image;
    }

    public void setDefault_profile_image(String default_profile_image) {
        this.default_profile_image = default_profile_image;
    }

    public String getUser_description() {
        return user_description;
    }

    public void setUser_description(String user_description) {
        this.user_description = user_description;
    }

    public String getAlias_name() {
        return alias_name;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }



}
