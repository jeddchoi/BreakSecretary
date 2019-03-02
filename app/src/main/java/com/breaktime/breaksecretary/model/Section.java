package com.breaktime.breaksecretary.model;

public class Section {
    String sectionNum;
    String reserveNum;
    String totallNum;

    public Section(String sectionNum, String reserveNum, String totallNum)
    {
        this.sectionNum = sectionNum;
        this.reserveNum = reserveNum;
        this.totallNum = totallNum;
    }

    public String getSectionNum() {
        return sectionNum;
    }

    public String getReserveNum() {
        return reserveNum;
    }

    public String getTotallNum() {
        return totallNum;
    }

    public void setSectionNum(String sectionNum) {
        this.sectionNum = sectionNum;
    }

    public void setReserveNum(String reserveNum) {
        this.reserveNum = reserveNum;
    }

    public void setTotallNum(String totallNum) {
        this.totallNum = totallNum;
    }
}
