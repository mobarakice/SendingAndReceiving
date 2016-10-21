package group.atomap.firebasedemo.firebasechat;

import java.io.Serializable;

/**
 * Created by Tauhid on 10/18/2016.
 */

public class User implements Serializable {
    private String userName;
    private String userId;
    private String userEmail;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
