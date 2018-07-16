package awayzoneadvertiser.com.advertiser.culturelist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devraj on 2/17/2018.
 */

public class FlagList {

    String name;
    String flagimageurl;
    int check;

    public List<FLAGDATA> getList() {
        return list;
    }

    public void setList(List<FLAGDATA> list) {
        this.list = list;
    }

    public List<FLAGDATA> list = new ArrayList<>();

    public FlagList(String name, String flagimageurl, int check, List<FLAGDATA> list)
    {
        this.name = name;
        this.flagimageurl = flagimageurl;
        this.check = check;
        this.list = list;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagimageurl() {
        return flagimageurl;
    }

    public void setFlagimageurl(String flagimageurl) {
        this.flagimageurl = flagimageurl;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }




}
