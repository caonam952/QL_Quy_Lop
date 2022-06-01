package com.example.quan_ly_quy_lop.models;

import static com.example.quan_ly_quy_lop.XuLyChung.DMY;
import static com.example.quan_ly_quy_lop.XuLyChung.DMYTime;
import static com.example.quan_ly_quy_lop.XuLyChung.MonthYear;
import static com.example.quan_ly_quy_lop.XuLyChung.MonthYear1;
import static com.example.quan_ly_quy_lop.XuLyChung.YMD;
import static com.example.quan_ly_quy_lop.XuLyChung.YMDTime;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

import androidx.annotation.Nullable;

import com.example.quan_ly_quy_lop.R;
import com.example.quan_ly_quy_lop.ui.expense.ExpenseModel;
import com.example.quan_ly_quy_lop.ui.home.Detail_Statistic_Model;
import com.example.quan_ly_quy_lop.ui.home.StatisticsModel;
import com.example.quan_ly_quy_lop.ui.income.Detail_IncomeModel;
import com.example.quan_ly_quy_lop.ui.income.IncomeModel;
import com.example.quan_ly_quy_lop.ui.member.MemberModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QLQL.db";
    private static final int DATABASE_VERSION = 1;

    // table name
    private static final String TABLE_MEMBER_ALL = "member_all";
    private static final String TABLE_MEMBER = "member";
    private static final String TABLE_INCOME = "income";
    private static final String TABLE_EXPENSE = "expense";
    private static final String TABLE_SESSION_INCOME = "session_income";

    // tên trùng nhau có thể các bảng liên quan tới nhau hoặc không
    // common name between student - income
    private static final String KEY_ID_SV = "id_sv";
    // common name between income - session_income;
    private static final String KEY_ID_INCOME = "id_income";
    private static final String KEY_DATE_INCOME = "date_income";
    // common name between expense - session_income;
    private static final String KEY_CONTENT = "content";
    private static final String KEY_MONEY = "money";

    // table student
    private static final String KEY_NAME_MEMBER = "name_sv";

    // table income
    private static final String KEY_CHECK_INCOME = "check_income";

    // table expense
    private static final String KEY_ID_EXPENSE = "id_expense";
    private static final String KEY_DATE_EXPENSE = "date_expense";

    // table session income // save all id of income
    private static final String KEY_DONE_INCOME = "done";

    // create table
    private static final String CREATE_TABLE_MEMBER_ALL = "CREATE TABLE " + TABLE_MEMBER_ALL +
            " (" + KEY_ID_SV + " TEXT PRIMARY KEY, " + KEY_NAME_MEMBER + " TEXT)";

    private static final String CREATE_TABLE_MEMBER = "CREATE TABLE " + TABLE_MEMBER +
            " (" + KEY_ID_SV + " TEXT PRIMARY KEY, " + KEY_NAME_MEMBER + " TEXT)";

    private static final String CREATE_TABLE_INCOME = "CREATE TABLE " + TABLE_INCOME +
            " (" + KEY_ID_INCOME + " TEXT, " + KEY_ID_SV + " TEXT, " + KEY_CHECK_INCOME + " BOOLEAN, " + KEY_DATE_INCOME + " DATETIME," + "  PRIMARY KEY (" + KEY_ID_INCOME + ", " + KEY_ID_SV + "))";

    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE " + TABLE_EXPENSE +
            " (" + KEY_ID_EXPENSE + " TEXT PRIMARY KEY, " + KEY_CONTENT + " TEXT, " + KEY_MONEY + " INTEGER, " + KEY_DATE_EXPENSE + " DATETIME)";

    private static final String CREATE_TABLE_SESSION_INCOME = "CREATE TABLE " + TABLE_SESSION_INCOME +
            " (" + KEY_ID_INCOME + " TEXT PRIMARY KEY, " + KEY_CONTENT + " TEXT, " + KEY_MONEY + " INTEGER, " + KEY_DATE_INCOME + " DATE, " + KEY_DONE_INCOME + " BOOLEAN)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MEMBER_ALL);
        sqLiteDatabase.execSQL(CREATE_TABLE_MEMBER);
        sqLiteDatabase.execSQL(CREATE_TABLE_INCOME);
        sqLiteDatabase.execSQL(CREATE_TABLE_EXPENSE);
        sqLiteDatabase.execSQL(CREATE_TABLE_SESSION_INCOME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER_ALL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION_INCOME);
        onCreate(sqLiteDatabase);
    }

    // Thêm thành viên
    public void insertMember(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_SV, id);
        values.put(KEY_NAME_MEMBER, name);

        db.insert(TABLE_MEMBER_ALL, null, values);
        db.insert(TABLE_MEMBER, null, values);
        db.close();
    }

    // Thêm đợt thu tiền
    public void insertSessionIncome(String id, String content, int money, String date, Boolean done) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_INCOME, id);
        values.put(KEY_CONTENT, content);
        values.put(KEY_MONEY, money);
        values.put(KEY_DATE_INCOME, YMD(date));
        values.put(KEY_DONE_INCOME, done);

        db.insert(TABLE_SESSION_INCOME, null, values);
        insertIncome(id);
        db.close();
    }

    // Thêm thành viên vào bảng thu tiền
    public void insertIncome(String idIncome) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<MemberModel> sv = this.getMember();
        ContentValues values;

        for (int i = 0; i < sv.size(); i++) {
            values = new ContentValues();
            values.put(KEY_ID_INCOME, idIncome);
            values.put(KEY_ID_SV, sv.get(i).getId());
            values.put(KEY_CHECK_INCOME, false);

            db.insert(TABLE_INCOME, null, values);
        }

        db.close();
    }

    // Thêm phiên chi tiền
    public void insertExpense(String id, String content, int money, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_EXPENSE, id);
        values.put(KEY_CONTENT, content);
        values.put(KEY_MONEY, money);
        values.put(KEY_DATE_EXPENSE, YMDTime(date));

        db.insert(TABLE_EXPENSE, null, values);
        db.close();
    }

    // Lấy tên 1 thành viên trong bảng tổng thành viên
    @SuppressLint("Range")
    public String getNameMember(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MEMBER_ALL + " WHERE " + KEY_ID_SV + " = '" + id + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        String name = null;
        if (c.getCount() > 0) {
            c.moveToFirst();
            name = c.getString(c.getColumnIndex(KEY_NAME_MEMBER));
        }

        c.close();
        return name;
    }

    // Lấy danh sách thành viên hiện tại
    @SuppressLint("Range")
    public ArrayList<MemberModel> getMember() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MEMBER;

        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<MemberModel> data = new ArrayList<>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                MemberModel sv = new MemberModel();
                sv.setId(c.getString(c.getColumnIndex(KEY_ID_SV)));
                sv.setName(c.getString(c.getColumnIndex(KEY_NAME_MEMBER)));

                data.add(sv);
            }
            while (c.moveToNext());
        }

        c.close();
        return data;
    }

    // Lấy ra danh sách các đợt thu tiền để hiển thị cho màn hình thu tiền
    @SuppressLint("Range")
    public ArrayList<IncomeModel> getSessionIncome() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SESSION_INCOME;

        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<IncomeModel> data = new ArrayList<>();

        if(c.getCount() > 0) {
            c.moveToFirst();
            do {
                IncomeModel d = new IncomeModel();
                d.setId(c.getString(c.getColumnIndex(KEY_ID_INCOME)));

                String date = c.getString(c.getColumnIndex(KEY_DATE_INCOME));

                d.setDate(DMY(date));
                int done = c.getInt(c.getColumnIndex(KEY_DONE_INCOME));
                if (done == 1) d.setIdImg(R.drawable.tich_green);
                else d.setIdImg(R.drawable.tich_red);

                d.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
                d.setMoney(String.valueOf(c.getInt(c.getColumnIndex(KEY_MONEY))));
                data.add(d);
            }
            while (c.moveToNext());
        }
        c.close();
        return data;
    }

    // Lấy ra danh sách đợt thu tiền theo năm
    @SuppressLint("Range")
    public ArrayList<IncomeModel> getSessionIncomeYear(String year) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SESSION_INCOME + " WHERE strftime('%Y', " + KEY_DATE_INCOME + ") = '" + year + "'";

        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<IncomeModel> data = new ArrayList<>();

        if(c.getCount() > 0) {
            c.moveToFirst();
            do {
                IncomeModel d = new IncomeModel();
                d.setId(c.getString(c.getColumnIndex(KEY_ID_INCOME)));

                String date = c.getString(c.getColumnIndex(KEY_DATE_INCOME));

                d.setDate(DMY(date));
                int done = c.getInt(c.getColumnIndex(KEY_DONE_INCOME));
                if (done == 1) d.setIdImg(R.drawable.tich_green);
                else d.setIdImg(R.drawable.tich_red);

                d.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
                d.setMoney(String.valueOf(c.getInt(c.getColumnIndex(KEY_MONEY))));
                data.add(d);
            }
            while (c.moveToNext());
        }
        c.close();
        return data;
    }

    // Lấy chi tiêt danh sách từng đợt thu
    @SuppressLint("Range")
    public ArrayList<Detail_IncomeModel> getDetailIncome(String idIncome) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_INCOME +
                " WHERE " + KEY_ID_INCOME + " = '" + idIncome + "'";
        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<Detail_IncomeModel> data = new ArrayList<>();

        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                Detail_IncomeModel a = new Detail_IncomeModel();
                a.setId(c.getString(c.getColumnIndex(KEY_ID_SV)));

                int cb = c.getInt(c.getColumnIndex(KEY_CHECK_INCOME));
                if (cb == 1) a.setCb(true);
                else a.setCb(false);

                String name = this.getNameMember(c.getString(c.getColumnIndex(KEY_ID_SV)));
                if (name != null) {
                    a.setHt(name);
                }

                data.add(a);
            }
            while (c.moveToNext());
        }

        c.close();
        return data;
    }

    // Lấy danh sách chi tiền
    @SuppressLint("Range")
    public ArrayList<ExpenseModel> getExpense() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_EXPENSE;

        ArrayList<ExpenseModel> data = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
           c.moveToFirst();
           do {
               ExpenseModel d = new ExpenseModel();

               d.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
               d.setMoney(String.valueOf(c.getInt(c.getColumnIndex(KEY_MONEY))));

               String date = c.getString(c.getColumnIndex(KEY_DATE_EXPENSE));
               date = DMYTime(date);

               d.setDate(date);
               data.add(d);
           }
           while (c.moveToNext());
        }

        c.close();
        return data;
    }

    // Lấy danh sách chi tiên theo tháng hoặc năm
    @SuppressLint("Range")
    public ArrayList<ExpenseModel> getExpenseMonthYear(String month, String year) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query;
        if (!month.equals("-1") && !year.equals("-1")) {
            query = "SELECT * FROM " + TABLE_EXPENSE +
                    " WHERE strftime('%m'," + KEY_DATE_EXPENSE + ") = '" + month +
                    "' AND strftime('%Y'," + KEY_DATE_EXPENSE + ") = '" + year + "'";
        }
        else if (!month.equals("-1")) {
            query = "SELECT * FROM " + TABLE_EXPENSE +
                    " WHERE strftime('%m'," + KEY_DATE_EXPENSE + ") = '" + month + "'";
        }
        else {
            query = "SELECT * FROM " + TABLE_EXPENSE +
                    " WHERE strftime('%Y'," + KEY_DATE_EXPENSE + ") = '" + year + "'";
        }


        ArrayList<ExpenseModel> data = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                ExpenseModel d = new ExpenseModel();

                d.setContent(c.getString(c.getColumnIndex(KEY_CONTENT)));
                d.setMoney(String.valueOf(c.getInt(c.getColumnIndex(KEY_MONEY))));

                String date = c.getString(c.getColumnIndex(KEY_DATE_EXPENSE));
                date = DMYTime(date);

                d.setDate(date);
                data.add(d);
            }
            while (c.moveToNext());
        }

        c.close();
        return data;
    }

    // Lấy ra thống kê theo tháng năm
    @SuppressLint("Range")
    public ArrayList<StatisticsModel> getStatistic() {
        SQLiteDatabase db = this.getReadableDatabase();

        HashMap<String, StatisticsModel> data = new HashMap<>();

        // Lấy danh sách chi tiền và sắp xếp theo ngày tháng giảm dần
        ArrayList<ExpenseModel> em = getExpense();
        em.sort((o1, o2) ->
                o2.getDate().compareTo(o1.getDate()));
        for (int i = 0; i < em.size(); i++) {
            // Lấy ra tháng năm làm key của map
            String my = MonthYear(em.get(i).getDate());
            if (data.containsKey(my)) {
                StatisticsModel a = data.get(my);
                int expense = Integer.parseInt(a.getExpense());
                expense += Integer.parseInt(em.get(i).getMoney());
                a.setExpense(String.valueOf(expense));
                data.put(my, a);
            }
            else {
                StatisticsModel a = new StatisticsModel();
                a.setTitle(my);
                a.setExpense(em.get(i).getMoney());
                a.setIncome("0");
                data.put(my, a);
            }
        }

        // Lấy ra danh sách đợt đóng góp
        ArrayList<IncomeModel> im = getSessionIncome();
        im.sort((o1, o2) ->
                o2.getDate().compareTo(o1.getDate()));
        for (int i = 0; i < im.size(); i++) {
            String query = "SELECT * FROM " + TABLE_INCOME + " WHERE " + KEY_ID_INCOME + " = '" + im.get(i).getId() + "'";
            Cursor c = db.rawQuery(query, null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    String dateI = c.getString(c.getColumnIndex(KEY_DATE_INCOME));
                    if (dateI != null) {
                        String chMY = MonthYear1(dateI);
                        if (data.containsKey(chMY)) {
                            StatisticsModel a = data.get(chMY);
                            int income = Integer.parseInt(a.getIncome());
                            income += Integer.parseInt(im.get(i).getMoney());
                            a.setIncome(String.valueOf(income));
                            data.put(chMY, a);
                        }
                        else {
                            StatisticsModel a = new StatisticsModel();
                            a.setTitle(chMY);
                            a.setIncome(String.valueOf(im.get(i).getMoney()));
                            a.setExpense("0");
                            data.put(chMY, a);
                        }
                    }
                }
                while (c.moveToNext());
            }
        }

        ArrayList<StatisticsModel> dt = new ArrayList<>();
        for (Map.Entry<String, StatisticsModel> entry: data.entrySet()) {
            dt.add(entry.getValue());
        }

        return dt;
    }

    // Lấy chi tiết thống kê theo tháng năm
    @SuppressLint("Range")
    public ArrayList<Detail_Statistic_Model> getStatisticMY(String my) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Detail_Statistic_Model> data = new ArrayList<>();

        int dash = my.indexOf('-');
        String month = my.substring(0, dash);
        String year = my.substring(dash + 1);

        // Lấy danh sách chi theo tháng và năm
        ArrayList<ExpenseModel> em = getExpenseMonthYear(month, year);
        for (int i = 0; i< em.size(); i++) {
            Detail_Statistic_Model a = new Detail_Statistic_Model();
            a.setDate(em.get(i).getDate());
            a.setMoney(em.get(i).getMoney());
            a.setContent(em.get(i).getContent());
            a.setColor(Color.RED);
            a.setName("");
            data.add(a);
        }

        // Lấy danh sách thu tiền theo tháng và năm
        String query = "SELECT * FROM " + TABLE_INCOME +
                " WHERE strftime('%Y', " + KEY_DATE_INCOME + ") = '" + year + "' AND strftime('%m'," +
                KEY_DATE_INCOME + ") = '" + month + "'";
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                Detail_Statistic_Model b = new Detail_Statistic_Model();
                b.setDate(DMYTime(c.getString(c.getColumnIndex(KEY_DATE_INCOME))));
                String query1 = "SELECT * FROM " + TABLE_SESSION_INCOME +
                        " WHERE " + KEY_ID_INCOME + " = '" + c.getString(c.getColumnIndex(KEY_ID_INCOME)) + "'";
                Cursor c1 = db.rawQuery(query1, null);
                if (c1.getCount() > 0) {
                    c1.moveToFirst();
                    b.setMoney(c1.getString(c1.getColumnIndex(KEY_MONEY)));
                    b.setContent(c1.getString(c1.getColumnIndex(KEY_CONTENT)));
                    b.setColor(Color.GREEN);
                }
                String query2 = "SELECT * FROM " + TABLE_MEMBER_ALL +
                        " WHERE " + KEY_ID_SV + " = '" + c.getString(c.getColumnIndex(KEY_ID_SV)) + "'";
                Cursor c2 = db.rawQuery(query2, null);
                if (c2.getCount() > 0) {
                    c2.moveToFirst();
                    b.setName(c2.getString(c2.getColumnIndex(KEY_NAME_MEMBER)));
                }
                data.add(b);
            }
            while (c.moveToNext());
        }

        return data;
    }

    // Cập nhật đợt thu tiền
    public void updateSessionIncome(String id, boolean done) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DONE_INCOME, done);

        String query = KEY_ID_INCOME + " = '" + id + "'";
        db.update(TABLE_SESSION_INCOME, values, query, null);
    }

    // Cập nhật danh sách đóng góp
    public void updateIncome(String idIncome, String idSV, boolean cb, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHECK_INCOME, cb);
        values.put(KEY_DATE_INCOME, date);

        String query = KEY_ID_INCOME + " = '" + idIncome + "' AND " + KEY_ID_SV + " = '" + idSV + "'";
        db.update(TABLE_INCOME, values, query, null);
    }

    // Cập nhật tên thành viên
    public void updateMember(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_MEMBER, name);

        String query = KEY_ID_SV + " = '" + id + "'";
        db.update(TABLE_MEMBER, values, query, null);
    }

    // Xóa thành viên
    public Boolean deleteMember(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = KEY_ID_SV + " = '" + id + "'";
        return db.delete(TABLE_MEMBER, query, null) > 0;
    }
}
