package com.jr.websession;

import java.util.UUID;

public class ConferenceWebSession {

    private String id = UUID.fromString("0-0-0-0-0").toString();

    private String userName = "systemDefaultUserName";

    public ConferenceWebSession(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

