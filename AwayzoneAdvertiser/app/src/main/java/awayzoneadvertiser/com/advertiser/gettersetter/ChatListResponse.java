package awayzoneadvertiser.com.advertiser.gettersetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatListResponse {

    @SerializedName("chat_arr")
    @Expose
    private List<ChatArr> chatArr = null;

    public List<ChatArr> getChatArr() {
        return chatArr;
    }

    public void setChatArr(List<ChatArr> chatArr) {
        this.chatArr = chatArr;
    }

}
