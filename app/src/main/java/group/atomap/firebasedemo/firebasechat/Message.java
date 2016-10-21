package group.atomap.firebasedemo.firebasechat;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String mText;
    private String mSender;
    private String time;
    private Date mDate;

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}