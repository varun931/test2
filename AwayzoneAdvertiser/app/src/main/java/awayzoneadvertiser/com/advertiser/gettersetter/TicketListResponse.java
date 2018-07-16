package awayzoneadvertiser.com.advertiser.gettersetter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import awayzoneadvertiser.com.advertiser.gettersetter.TicketDatum;

public class TicketListResponse {

    @SerializedName("ticket_data")
    @Expose
    private List<TicketDatum> ticketData = null;

    public List<TicketDatum> getTicketData() {
        return ticketData;
    }

    public void setTicketData(List<TicketDatum> ticketData) {
        this.ticketData = ticketData;
    }

}
