package com.example.quan_ly_quy_lop.ui.income;

public class IncomeModel {
    int idImg;
    String id;
    String date;
    String content;
    String money;

    public IncomeModel() {}

    public IncomeModel(int idImg, String id, String date, String content, String money) {
        this.idImg = idImg;
        this.id = id;
        this.date = date;
        this.content = content;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdImg() {
        return idImg;
    }

    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
