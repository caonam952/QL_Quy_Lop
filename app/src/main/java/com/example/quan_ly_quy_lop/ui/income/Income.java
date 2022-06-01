package com.example.quan_ly_quy_lop.ui.income;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quan_ly_quy_lop.XuLyChung;
import com.example.quan_ly_quy_lop.databinding.FragmentIncomeBinding;
import com.example.quan_ly_quy_lop.models.DatabaseHelper;
import com.example.quan_ly_quy_lop.ui.member.MemberModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Income extends Fragment {

    private FragmentIncomeBinding binding;
    Spinner spnIncome;
    ListView lsvIncome;
    FloatingActionButton fabIncome;

    ArrayList<String> year;
    ArrayList<IncomeModel> data;
    IncomeAdapter incomeAdapter;
    DatabaseHelper db;

    int cacheYear = -1;

    @Override
    public void onResume() {
        super.onResume();
        if (cacheYear != -1) getDataYear();
        else LoadData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIncomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spnIncome = binding.spnIncome;
        lsvIncome = binding.lsvIncome;
        fabIncome = binding.fabIncome;

        year = new ArrayList<>();
        data = new ArrayList<>();

        incomeAdapter = new IncomeAdapter(getActivity(), data);
        lsvIncome.setAdapter(incomeAdapter);

        db = new DatabaseHelper(getActivity());

        fabIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<MemberModel> mb = db.getMember();
                if (mb.size() == 0) {
                    Toast.makeText(getActivity(), "Không có thành viên\n Không thể thêm", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), AddIncome.class);

                    startActivity(intent);
                }
            }
        });

        lsvIncome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Detail_Income.class);

                Bundle bd = new Bundle();
                bd.putString("id", data.get(i).getId());
                bd.putString("date", data.get(i).getDate());
                bd.putString("money", data.get(i).getMoney());
                bd.putString("content", data.get(i).getContent());

                intent.putExtras(bd);
                startActivity(intent);
            }
        });

        initSpinner();
        return root;
    }

    void initSpinner() {
        year.addAll(XuLyChung.CreateListYear());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, year);
        spnIncome.setAdapter(adapter);

        spnIncome.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    cacheYear = i;
                    getDataYear();
                }
                else {
                    cacheYear = -1;
                    LoadData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void LoadData() {
        ArrayList<IncomeModel> da = db.getSessionIncome();
        da.sort((o1, o2)
                -> o2.getDate().compareTo(o1.getDate())
        );

        data.clear();
        data.addAll(da);
        Change();
    }

    void getDataYear() {
        if (cacheYear != -1) {
            String sp = year.get(cacheYear);
            ArrayList<IncomeModel> spn = db.getSessionIncomeYear(sp);
            data.clear();
            data.addAll(spn);
            Change();
        }
    }

    void Change() {
        incomeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}