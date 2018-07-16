package awayzoneadvertiser.com.advertiser.gettersetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatDatum {

    @SerializedName("chat_id")
    @Expose
    private Integer chatId;
    @SerializedName("advertiser_id")
    @Expose
    private Integer advertiserId;
    @SerializedName("admin_id")
    @Expose
    private Integer adminId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("chat_date")
    @Expose
    private String chatDate;
    @SerializedName("chat_time")
    @Expose
    private String chatTime;
    @SerializedName("admin_status")
    @Expose
    private Integer adminStatus;
    @SerializedName("advertiser_status")
    @Expose
    private Integer advertiserStatus;



    public ChatDatum(String message,Integer adminId,String chatTime,String chatDate)
    {
        this.message = message;
        this.adminId = adminId;
        this.chatTime = chatTime;
        this.chatDate = chatDate;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Integer advertiserId) {
        this.advertiserId = advertiserId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatDate() {
        return chatDate;
    }

    public void setChatDate(String chatDate) {
        this.chatDate = chatDate;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public Integer getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Integer adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Integer getAdvertiserStatus() {
        return advertiserStatus;
    }

    public void setAdvertiserStatus(Integer advertiserStatus) {
        this.advertiserStatus = advertiserStatus;
    }

}
