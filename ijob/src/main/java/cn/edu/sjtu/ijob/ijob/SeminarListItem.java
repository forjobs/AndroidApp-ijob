package cn.edu.sjtu.ijob.ijob;

/**
 * Created by tian on 16/3/15.
 */
public class SeminarListItem {
    private String mCompanyName;
    private String mTime;
    private String mPlace;
    private String mInfoUrl;

    public SeminarListItem() {
        super();
    }

    public String getCompanyName() {return mCompanyName;}
    public String getTime() {return mTime;}
    public String getPlace() {return mPlace;}
    public String getInfoUrl() {return mInfoUrl;}

    public void setCompanyName(String name) {this.mCompanyName = name;}
    public void setTime(String time) {this.mTime = time;}
    public void setPlace(String place) {this.mPlace = place;}
    public void setInfoUrl(String url) {this.mInfoUrl = url;}
}
