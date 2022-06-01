package com.example.quan_ly_quy_lop.ui.home;

public class StatisticsModel {
    String title;
    String income;
    String expense;
    String balance;

    public StatisticsModel() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StatisticsModel(String month, String income, String expense, String balance) {
        this.title = month;
        this.income = income;
        this.expense = expense;
        this.balance = balance;
    }

    public StatisticsModel(String income, String expense, String balance) {
        this.income = income;
        this.expense = expense;
        this.balance = balance;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
