package com.example.quan_ly_quy_lop.ui.home;

import static com.example.quan_ly_quy_lop.XuLyChung.Dot;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quan_ly_quy_lop.R;

import java.util.ArrayList;

public class Detail_Statistic_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Detail_Statistic_Model> data;

    public Detail_Statistic_Adapter(Context context, ArrayList<Detail_Statistic_Model> data) {
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
        view = inflater.inflate(R.layout.custome_item_detail_statistic, viewGroup, false);

        TextView date = view.findViewById(R.id.txtDetailStatisticDate);
        TextView money = view.findViewById(R.id.txtDetailStatisticMoney);
        TextView content = view.findViewById(R.id.txtDetailStatisticContent);
        TextView name = view.findViewById(R.id.txtNameMember);

        date.setText(data.get(i).getDate());
        money.setText(Dot(data.get(i).getMoney()) + " VNĐ");
        content.setText(data.get(i).getContent());
        name.setText(data.get(i).getName());

        money.setTextColor(data.get(i).getColor());

        return view;
    }
}
