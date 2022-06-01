package com.example.quan_ly_quy_lop.ui.member;

public class MemberModel {
    String stt;
    String id;
    String name;
    boolean cb;

    public MemberModel() {}

    public MemberModel(String stt, String name, boolean cb) {
        this.stt = stt;
        this.name = name;
        this.cb = cb;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCb() {
        return cb;
    }

    public void setCb(boolean cb) {
        this.cb = cb;
    }
}
