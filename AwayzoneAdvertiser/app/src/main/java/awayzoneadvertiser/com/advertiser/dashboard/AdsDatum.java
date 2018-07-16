package awayzoneadvertiser.com.advertiser.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsDatum {

    @SerializedName("ad_id")
    @Expose
    private Integer adId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("comment")
    @Expose
    private Integer comment;
    @SerializedName("review")
    @Expose
    private Integer review;
    @SerializedName("story")
    @Expose
    private Integer story;
    @SerializedName("rating_sum")
    @Expose
    private Float ratingSum;
    @SerializedName("impression")
    @Expose
    private Integer impression;


    @SerializedName("click")
    @Expose
    private Integer click;




    public AdsDatum( Integer adId,String image,String title,String description,Integer likes,Integer comment,Integer review,Integer story,Float ratingSum)
    {
        this.adId = adId;
        this.image = image;
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.comment =comment;
        this.review = review;
        this.story = story;
        this.ratingSum = ratingSum;

    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public Integer getStory() {
        return story;
    }

    public void setStory(Integer story) {
        this.story = story;
    }

    public Float getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(Float ratingSum) {
        this.ratingSum = ratingSum;
    }


    public Integer getImpression() {
        return impression;
    }

    public void setImpression(Integer impression) {
        this.impression = impression;
    }


    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

}
