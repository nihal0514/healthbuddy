package com.example.healthbuddy.chats.chatfragment;

public class alldoctors {
    private alldoctors(){

    }
    private String profileimage;
    private String fullname;
    private String key;
    private String id;

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public alldoctors(String profileimage, String fullname, String key, String id) {
        this.profileimage = profileimage;
        this.fullname = fullname;
        this.key = key;
        this.id = id;
    }

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public alldoctors(String profileimage, String fullname, String key,String id) {
        this.profileimage = profileimage;
        this.fullname = fullname;
        this.key=key;
        this.id=id;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getUsername() {
        return fullname;
    }

    public void setUsername(String fullname) {
        this.fullname = fullname;
    }*/
}
