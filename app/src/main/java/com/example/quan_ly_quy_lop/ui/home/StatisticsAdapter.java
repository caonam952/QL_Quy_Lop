package com.example.quan_ly_quy_lop.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quan_ly_quy_lop.R;
import com.example.quan_ly_quy_lop.XuLyChung;

import java.util.ArrayList;

public class StatisticsAdapter extends BaseAdapter {
    Context context;
    ArrayList<StatisticsModel> statistics;

    public StatisticsAdapter(Context context, ArrayList<StatisticsModel> statistics) {
        this.context = context;
        this.statistics = statistics;
    }

    @Override
    public int getCount() {
        return statistics.size();
    }

    @Override
    public Object getItem(int i) {
        return statistics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.custom_item_statistics, viewGroup, false);

        View viewGreen = view.findViewById(R.id.viewGreen);
        View viewRed = view.findViewById(R.id.viewRed);
        TextView txtMonth = view.findViewById(R.id.txtMonth);
        TextView txtIncome = view.findViewById(R.id.txtIncome);
        TextView txtExpense = view.findViewById(R.id.txtExpense);
        TextView txtBalance = view.findViewById(R.id.txtBalance);

        // max 70dp
        double income = Double.parseDouble(statistics.get(i).income);
        double expense = Double.parseDouble(statistics.get(i).expense);
        double tong = income + expense;
        double dp = view.getResources().getDisplayMetrics().density;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewGreen.getLayoutParams();
        params.height = (int) (income/tong * 70 * dp);
        params.width = (int) (25 * dp);
        viewGreen.setLayoutParams(params);

        params = (RelativeLayout.LayoutParams) viewRed.getLayoutParams();
        params.height = (int) (expense/tong * 70 * dp);
        params.width = (int) (25 * dp);
        viewRed.setLayoutParams(params);

        txtMonth.setText(statistics.get(i).title);
        txtIncome.setText(XuLyChung.Dot(statistics.get(i).income) + " VNĐ");
        txtExpense.setText(XuLyChung.Dot(statistics.get(i).expense) + " VNĐ");
        txtBalance.setText(XuLyChung.Dot(statistics.get(i).balance) + " VNĐ");

        return view;
    }
}
