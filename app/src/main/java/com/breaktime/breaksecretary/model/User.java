package com.breaktime.breaksecretary.model;

import java.util.ArrayList;

public class User {

    private String email;
    private int status;
    private boolean WannaBeNotified;
    private long lastLoginTime;
    private long lastStartTime;
    private long ReserveTime;
    private long idleTime;
    private boolean isExtended;

    private ArrayList<String> favoritesSeat;
    private boolean verified;


    public User(String email) {
        this.email = email;
        this.status = 0;
        this.WannaBeNotified = false;
        this.lastLoginTime = 0;
        this.lastStartTime = 0;
        this.ReserveTime = 0;
        this.idleTime = 0;
        this.isExtended = false;
        this.verified = true;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isWannaBeNotified() {
        return WannaBeNotified;
    }

    public void setWannaBeNotified(boolean wannaBeNotified) {
        WannaBeNotified = wannaBeNotified;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public long getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(long lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public long getResumeTime() {
        return ReserveTime;
    }

    public void setResumeTime(long resumeTime) {
        ReserveTime = resumeTime;
    }

    public long getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(long idleTime) {
        this.idleTime = idleTime;
    }

    public boolean getIsExtended() {
        return isExtended;
    }

    public void setIsExtended(boolean isExtended) {
        this.isExtended = isExtended;
    }

    public ArrayList<String> getFavoritesSeat() {
        return favoritesSeat;
    }

    public void setFavoritesSeat(ArrayList<String> favoritesSeat) {
        this.favoritesSeat = favoritesSeat;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
