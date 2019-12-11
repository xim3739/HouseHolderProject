package com.example.householderproject.model;

public class RecyclerViewData {

    private String date;
    private String credit;
    private String detail;
    private String category;
    private String location;

    public RecyclerViewData(String date, String credit, String detail, String category, String location) {

        this.date = date;
        this.credit = credit;
        this.detail = detail;
        this.category = category;
        this.location = location;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
