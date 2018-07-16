package awayzoneadvertiser.com.advertiser.dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Analytic {

    @SerializedName("get_comment")
    @Expose
    private Integer getComment;
    @SerializedName("get_review")
    @Expose
    private Integer getReview;
    @SerializedName("get_story")
    @Expose
    private Integer getStory;
    @SerializedName("get_like")
    @Expose
    private Integer getLike;
    @SerializedName("get_been_here")
    @Expose
    private Integer getBeenHere;
    @SerializedName("get_bookmarks")
    @Expose
    private Integer getBookmarks;
    @SerializedName("get_impression")
    @Expose
    private Integer getImpression;
    @SerializedName("get_click")
    @Expose
    private Integer getClick;
    @SerializedName("get_share")
    @Expose
    private Integer getShare;

    public Integer getGetComment() {
        return getComment;
    }

    public void setGetComment(Integer getComment) {
        this.getComment = getComment;
    }

    public Integer getGetReview() {
        return getReview;
    }

    public void setGetReview(Integer getReview) {
        this.getReview = getReview;
    }

    public Integer getGetStory() {
        return getStory;
    }

    public void setGetStory(Integer getStory) {
        this.getStory = getStory;
    }

    public Integer getGetLike() {
        return getLike;
    }

    public void setGetLike(Integer getLike) {
        this.getLike = getLike;
    }

    public Integer getGetBeenHere() {
        return getBeenHere;
    }

    public void setGetBeenHere(Integer getBeenHere) {
        this.getBeenHere = getBeenHere;
    }

    public Integer getGetBookmarks() {
        return getBookmarks;
    }

    public void setGetBookmarks(Integer getBookmarks) {
        this.getBookmarks = getBookmarks;
    }

    public Integer getGetImpression() {
        return getImpression;
    }

    public void setGetImpression(Integer getImpression) {
        this.getImpression = getImpression;
    }

    public Integer getGetClick() {
        return getClick;
    }

    public void setGetClick(Integer getClick) {
        this.getClick = getClick;
    }

    public Integer getGetShare() {
        return getShare;
    }

    public void setGetShare(Integer getShare) {
        this.getShare = getShare;
    }

}
