package com.example.quan_ly_quy_lop.ui.member;

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

public class MemberAdapter extends BaseAdapter {
    Context context;
    ArrayList<MemberModel> data;

    public MemberAdapter(Context context, ArrayList<MemberModel> data) {
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
        view = inflater.inflate(R.layout.custom_item_member, viewGroup, false);

        TextView stt = view.findViewById(R.id.txtSTTMember);
        TextView name = view.findViewById(R.id.txtNameMember1);
        CheckBox cb = view.findViewById(R.id.cbMember);

        stt.setText(data.get(i).getStt());
        name.setText(data.get(i).getName());

        if (data.get(i).isCb()) cb.setChecked(true);
        else cb.setChecked(false);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MemberModel m = data.get(i);
                m.setCb(b);
                data.set(i, m);
            }
        });

        return view;
    }
}
