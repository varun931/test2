package awayzoneadvertiser.com.advertiser.gettersetter;

/**
 * Created by Devraj on 12/21/2017.
 */

public class CommentList {

    String date;
    String time;
    String comment;
    int user_id;
    int comment_id;
    int baseId;
    int ad_id;
    String first_name;
    String profile_image;

    public CommentList()
    {

    }
    public CommentList(int baseId, int ad_id, int user_id, int comment_id, String date, String time, String first_name, String profile_image, String comment)
    {
        this.baseId = baseId;
        this.ad_id= ad_id;
        this.user_id = user_id;
        this.ad_id = ad_id;
        this.comment_id = comment_id;
        this.date = date;
        this.comment = comment;
        this.time = time;
        this.first_name = first_name;
        this.profile_image = profile_image;

    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public int getAd_id() {
        return ad_id;
    }

    public void setAd_id(int ad_id) {
        this.ad_id = ad_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }








}
