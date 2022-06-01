package com.example.quan_ly_quy_lop.ui.income;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quan_ly_quy_lop.R;

import java.util.ArrayList;

public class IncomeAdapter extends BaseAdapter {
    Context context;
    ArrayList<IncomeModel> incomeModels;

    public IncomeAdapter(Context context, ArrayList<IncomeModel> incomeModels) {
        this.context = context;
        this.incomeModels = incomeModels;
    }

    @Override
    public int getCount() {
        return incomeModels.size();
    }

    @Override
    public Object getItem(int i) {
        return incomeModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.custome_item_income, viewGroup, false);

        TextView txtSessionContent;
        ImageView imgSession;

        txtSessionContent = view.findViewById(R.id.txtSessionIncome);
        imgSession = view.findViewById(R.id.imgSessionIncome);

        txtSessionContent.setText("Từ ngày " + incomeModels.get(i).date);
        imgSession.setImageResource(incomeModels.get(i).idImg);

        return view;
    }
}
