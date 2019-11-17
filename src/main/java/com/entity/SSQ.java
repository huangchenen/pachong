package com.entity;

/**
 * @author huangchenen
 * @
 */
public class SSQ {
    private String periodNum;//期数
    private String ticketTime;//开奖日期
    private String red1;
    private String red2;
    private String red3;
    private String red4;
    private String red5;
    private String red6;
    private String blue;

    public String getPeriodNum() {
        return periodNum;
    }

    public void setPeriodNum(String periodNum) {
        this.periodNum = periodNum;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public String getRed1() {
        return red1;
    }

    public void setRed1(String red1) {
        this.red1 = red1;
    }

    public String getRed2() {
        return red2;
    }

    public void setRed2(String red2) {
        this.red2 = red2;
    }

    public String getRed3() {
        return red3;
    }

    public void setRed3(String red3) {
        this.red3 = red3;
    }

    public String getRed4() {
        return red4;
    }

    public void setRed4(String red4) {
        this.red4 = red4;
    }

    public String getRed5() {
        return red5;
    }

    public void setRed5(String red5) {
        this.red5 = red5;
    }

    public String getRed6() {
        return red6;
    }

    public void setRed6(String red6) {
        this.red6 = red6;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public SSQ() {
    }

    public SSQ(String periodNum,
               String red1, String red2, String red3, String red4,
               String red5, String red6, String blue) {
        this.periodNum = periodNum;
        this.ticketTime = ticketTime;
        this.red1 = red1;
        this.red2 = red2;
        this.red3 = red3;
        this.red4 = red4;
        this.red5 = red5;
        this.red6 = red6;
        this.blue = blue;
    }

    @Override
    public String toString() {
        return "SSQ{" +
                "periodNum='" + periodNum + '\'' +
                ", ticketTime='" + ticketTime + '\'' +
                ", red1='" + red1 + '\'' +
                ", red2='" + red2 + '\'' +
                ", red3='" + red3 + '\'' +
                ", red4='" + red4 + '\'' +
                ", red5='" + red5 + '\'' +
                ", red6='" + red6 + '\'' +
                ", blue='" + blue + '\'' +
                '}';
    }
}
