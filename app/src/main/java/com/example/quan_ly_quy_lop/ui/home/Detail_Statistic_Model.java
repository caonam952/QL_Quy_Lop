package com.example.quan_ly_quy_lop.ui.home;

import android.graphics.Color;

public class Detail_Statistic_Model {
    private String date;
    private String money;
    private String content;
    private String name;
    private int color;

    public Detail_Statistic_Model(){}

    public Detail_Statistic_Model(String date, String money, String content, int color, String name) {
        this.date = date;
        this.money = money;
        this.content = content;
        this.color = color;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
