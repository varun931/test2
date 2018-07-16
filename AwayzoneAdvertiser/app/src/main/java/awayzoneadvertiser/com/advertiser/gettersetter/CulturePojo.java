package awayzoneadvertiser.com.advertiser.gettersetter;

import java.io.Serializable;

/**
 * Created by Devraj on 12/4/2017.
 */

public class CulturePojo implements Serializable {

    int id;
    String culturename;
    int cultureId;
    String check;



    public CulturePojo(int id, int cultureId, String culturename, String check)
    {
        this.id = id;
        this.culturename = culturename;
        this.check = check;
        this.cultureId = cultureId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCulturename() {
        return culturename;
    }

    public void setCulturename(String culturename) {
        this.culturename = culturename;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
    public int getCultureId() {
        return cultureId;
    }
    public void setCultureId(int cultureId) {
        this.cultureId = cultureId;
    }
}
