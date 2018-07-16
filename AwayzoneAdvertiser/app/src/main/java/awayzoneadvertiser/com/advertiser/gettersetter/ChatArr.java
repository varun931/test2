package awayzoneadvertiser.com.advertiser.gettersetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatArr {

    @SerializedName("tickets_id")
    @Expose
    private Integer ticketsId;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ticket_status")
    @Expose
    private Integer ticketStatus;
    @SerializedName("tickets_date")
    @Expose
    private String ticketsDate;
    @SerializedName("tickets_time")
    @Expose
    private String ticketsTime;
    @SerializedName("adver_profile_image")
    @Expose
    private String adverProfileImage;
    @SerializedName("chat_data")
    @Expose
    private List<ChatDatum> chatData = null;

    public Integer getTicketsId() {
        return ticketsId;
    }

    public void setTicketsId(Integer ticketsId) {
        this.ticketsId = ticketsId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public Integer getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(Integer ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTicketsDate() {
        return ticketsDate;
    }

    public void setTicketsDate(String ticketsDate) {
        this.ticketsDate = ticketsDate;
    }

    public String getTicketsTime() {
        return ticketsTime;
    }

    public void setTicketsTime(String ticketsTime) {
        this.ticketsTime = ticketsTime;
    }

    public String getAdverProfileImage() {
        return adverProfileImage;
    }

    public void setAdverProfileImage(String adverProfileImage) {
        this.adverProfileImage = adverProfileImage;
    }

    public List<ChatDatum> getChatData() {
        return chatData;
    }

    public void setChatData(List<ChatDatum> chatData) {
        this.chatData = chatData;
    }

}
