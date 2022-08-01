package com.jr.websession;

public enum ConferenceWebSessionSingleton {
    INSTANCE;

    private ConferenceWebSessionManager conferenceWebSessionManager;

    public void init() {
        conferenceWebSessionManager = new ConferenceWebSessionManager();
    }
}
