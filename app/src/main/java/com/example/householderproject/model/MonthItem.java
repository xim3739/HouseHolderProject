package com.example.householderproject.model;

public class MonthItem {

    private int dayValue;
    private int monthValue;

    public MonthItem(int dayValue, int monthValue) {
        this.dayValue = dayValue;
        this.monthValue = monthValue;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(int monthValue) {
        this.monthValue = monthValue;
    }

    public MonthItem() {
    }

    public MonthItem(int dayValue) {
        this.dayValue = dayValue;
    }

    public int getDayValue() {
        return dayValue;
    }

    public void setDayValue(int dayValue) {
        this.dayValue = dayValue;
    }

}
