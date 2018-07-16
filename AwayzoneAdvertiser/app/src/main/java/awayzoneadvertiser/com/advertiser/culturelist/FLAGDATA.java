package awayzoneadvertiser.com.advertiser.culturelist;

/**
 * Created by Devraj on 2/17/2018.
 */

public class FLAGDATA {


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

       public int id;
       public String flagname;
      public  String imageurl;

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    int check;

    public int getMainid() {
        return mainid;
    }

    public void setMainid(int mainid) {
        this.mainid = mainid;
    }

    public  int mainid;

        public String getFlagname() {
            return flagname;
        }

        public void setFlagname(String flagname) {
            this.flagname = flagname;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public FLAGDATA(String flagname, String imageurl,int id ,int mainid,int check)
        {
            this.flagname = flagname;
            this.imageurl = imageurl;
            this.mainid = mainid;
            this.id = id;
            this.check = check;
        }

}
