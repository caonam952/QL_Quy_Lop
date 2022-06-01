package com.example.quan_ly_quy_lop;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class XuLyChung {
    @NonNull
    // Chuẩn hóa tiền từ số sang có phẩy Ví dụ: 1000 -> 1,000
    public static String Dot(String input) {

        Long val = Long.parseLong(input);

        DecimalFormat fm = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        fm.applyPattern("#,###,###,###");

        return fm.format(val);
    }

    // Rút gọn tiền từ có phẩy sang ko chấm 1,000,000 -> 1000000
    public static int NotDot(String input) {
        input = input.replaceAll(",", "");
        return Integer.parseInt(input);
    }

    @NonNull
    // Tạo mảng gồm khoảng 5 năm trở về trước tính từ năm nay
    public static ArrayList<String> CreateListYear () {
        ArrayList<String> year = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        year.add("All");

        for (int i = 4; i >= 0; i--) {
            year.add(String.valueOf(currentYear));
            currentYear--;
        }

        return year;
    }

    @NonNull
    // Tao ds thang
    public static ArrayList<String> CreateListMonth() {
        ArrayList<String> month = new ArrayList<>();
        month.add("All");

        for (int i = 1; i <= 12; i ++) {
            month.add(String.valueOf(i));
        }

        return month;
    }

    // Check xem EditText có nhập nội dung không
    // Nếu toàn khoảng trắng cũng tính là rỗng
    public static boolean ChuoiRong(String input) {
        return Pattern.matches("^\\s*$", input);
    }

    //Chuẩn hóa xâu
    public static String DeleteSpace(@NonNull String input) {
        String c = input.trim();
        c = c.replaceAll("\\s{2,}", " ");
        return c;
    }

    // Chuyen String dd-MM-yyyy -> yyyy-MM-dd
    @SuppressLint("SimpleDateFormat")
    @NonNull
    public static String YMD(String input) {
        Date d = null;
        try {
            d = new SimpleDateFormat("dd-MM-yyyy").parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        return fm.format(d);
    }

    // Chuyen String yyyy-MM-dd -> dd-MM-yyyy
    @SuppressLint("SimpleDateFormat")
    @NonNull
    public static String DMY(String input) {
        Date d = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
        return fm.format(d);
    }

    // Datetime lấy localdatetime
    // Ngày 2021-10-15T21:23:31.423 -> 2021-10-15 21:23:31
    public static String DateTime(String input) {
        String c = input.replaceAll("T", " ");
        c = c.replaceAll("\\.\\d+", "");
        return c;
    }

    // Ngày 2021-10-15 21:23:31 -> 15-10-2021 21:23:31
    public static String DMYTime(String input) {
        int space = input.lastIndexOf(' ');
        String c = input.substring(0, space);
        String d = input.substring(space + 1);
        c = DMY(c);
        return c + " " + d;
    }

    // Ngày 15-10-2021 21:23:31 -> 2021-10-15 21:23:31
    public static String YMDTime(String input) {
        int space = input.lastIndexOf(' ');
        String c = input.substring(0, space);
        String d = input.substring(space + 1);
        c = YMD(c);
        return c + " " + d;
    }

    // Ngày 15-10-2021 21:23:31 -> 15-10-2121
    public static String DMYNoTime(String input) {
        int space = input.lastIndexOf(' ');
        String c = input.substring(0, space);
        return c;
    }

    // Lấy time 2021-10-15T21:23:31.234 -> 21:23:31
    public static String Time(String input) {
        int space = input.lastIndexOf('T');
        String c = input.substring(space + 1);
        c = c.replaceAll("\\.\\d+", "");
        return c;
    }

    // Lấy 15-10-2021 21:23:31 -> 10-2021
    public static String MonthYear(String input) {
        String c = DMYNoTime(input);
        int dash = c.indexOf('-');
        c = c.substring(dash + 1);
        return c;
    }

    // 2021-10-15 21:23:31 -> 10-2021
    public static String MonthYear1(String input) {
        String c = DMYTime(input);
        return MonthYear(c);
    }

    // Lấy tên để sắp xếp
    public static String Name(String input) {
        int space = input.lastIndexOf(' ');
        if (space != -1) return input.substring(space);
        else return input;
    }

}
