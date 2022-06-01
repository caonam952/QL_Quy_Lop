package com.example.quan_ly_quy_lop.ui.income;

public class Detail_IncomeModel {
    String id;
    String stt;
    String ht;
    Boolean cb;

    public Detail_IncomeModel() {}

    public Detail_IncomeModel(String stt, String ht, Boolean cb) {
        this.stt = stt;
        this.ht = ht;
        this.cb = cb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getHt() {
        return ht;
    }

    public void setHt(String ht) {
        this.ht = ht;
    }

    public Boolean getCb() {
        return cb;
    }

    public void setCb(Boolean cb) {
        this.cb = cb;
    }

}
