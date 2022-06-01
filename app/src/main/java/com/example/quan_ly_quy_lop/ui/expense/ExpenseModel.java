package com.example.quan_ly_quy_lop.ui.expense;

public class ExpenseModel {
    String date;
    String content;
    String money;

    public ExpenseModel() {}

    public ExpenseModel(String date, String content, String money) {
        this.date = date;
        this.content = content;
        this.money = money;
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
