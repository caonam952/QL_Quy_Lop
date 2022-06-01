package com.example.quan_ly_quy_lop.ui.income;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.quan_ly_quy_lop.R;

import java.util.ArrayList;

public class Detail_IncomeAdapter extends BaseAdapter {
    Context context;
    ArrayList<Detail_IncomeModel> data;

    public Detail_IncomeAdapter(Context context, ArrayList<Detail_IncomeModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.custom_item_table, viewGroup, false);

        TextView txtSTT = view.findViewById(R.id.txtAddIncomeSTT);
        TextView txtHT = view.findViewById(R.id.txtAddIncomeHT);
        CheckBox cb = view.findViewById(R.id.cbAddIncome);

        txtSTT.setText(data.get(i).stt);
        txtHT.setText(data.get(i).ht);
        if (data.get(i).cb) {
            cb.setChecked(true);
            cb.setClickable(false);
        }
        else cb.setChecked(false);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Detail_IncomeModel d = new Detail_IncomeModel();
                d = data.get(i);
                d.setCb(b);
                data.set(i, d);
            }
        });

        return view;
    }
}
