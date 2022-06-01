package com.example.quan_ly_quy_lop.ui.expense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quan_ly_quy_lop.R;
import com.example.quan_ly_quy_lop.XuLyChung;

import java.util.ArrayList;

public class ExpenseAdapter extends BaseAdapter {
    Context context;
    ArrayList<ExpenseModel> expenseModels;

    public ExpenseAdapter(Context context, ArrayList<ExpenseModel> expenseModels) {
        this.context = context;
        this.expenseModels = expenseModels;
    }

    @Override
    public int getCount() {
        return expenseModels.size();
    }

    @Override
    public Object getItem(int i) {
        return expenseModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.custom_item_expense, viewGroup, false);

        TextView txtDate = view.findViewById(R.id.txtExpenseDate);
        TextView txtContent = view.findViewById(R.id.txtExpenseContent);
        TextView txtMoney = view.findViewById(R.id.txtExpenseMoney);

        txtDate.setText(expenseModels.get(i).date);
        txtContent.setText(expenseModels.get(i).content);
        txtMoney.setText(XuLyChung.Dot(expenseModels.get(i).money) + " VNĐ");

        return view;
    }
}
