package com.breaktime.breaksecretary.model;

//type 0: 비어있음
//type 1: 이용중
//type 2: 예약중
//type 3: 자리비움

public class TestUser {

    private static int status;
    private static String seatNum;

    public static void TestUserInit(){
        status = 0;
        seatNum = "00";
    }

    public TestUser(int status) {
        this.status = status;
    }


    public static int getStatus() {
        return status;
    }

    public static void setStatus(int statust) {
        status = statust;
    }

    public static String getSeatNum() {
        return seatNum;
    }

    public static void setSeatNum(String seatNumt) {
        seatNum = seatNumt;
    }
}
