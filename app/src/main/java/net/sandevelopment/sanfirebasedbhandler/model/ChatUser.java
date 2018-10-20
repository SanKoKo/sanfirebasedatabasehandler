package net.sandevelopment.sanfirebasedbhandler.model;

import net.sandevelopment.sanfirebasedatabasehandler.SanFireDbResult;

import java.io.Serializable;

public class ChatUser extends SanFireDbResult<ChatUser> implements Serializable{
    private String uid;
    private String userName;
    private String email;
    public ChatUser() {
    }

    public ChatUser( String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public ChatUser(String uid, String userName, String email) {
        this.uid = uid;
        this.userName = userName;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
