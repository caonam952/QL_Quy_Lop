package com.example.quan_ly_quy_lop.ui.expense;

import static com.example.quan_ly_quy_lop.XuLyChung.CreateListMonth;
import static com.example.quan_ly_quy_lop.XuLyChung.CreateListYear;
import static com.example.quan_ly_quy_lop.XuLyChung.DMYNoTime;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quan_ly_quy_lop.databinding.FragmentExpenseBinding;
import com.example.quan_ly_quy_lop.models.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class Expense extends Fragment {

    private FragmentExpenseBinding binding;

    FloatingActionButton fabExpense;
    ListView lsvExpense;
    Spinner spnYear;
    Spinner spnMonth;

    ArrayList<String> year;
    ArrayList<String> month;
    ArrayList<ExpenseModel> data;
    ExpenseAdapter expenseAdapter;
    int cacheMonth = -1;
    int cacheYear = -1;

    DatabaseHelper db;

    @Override
    public void onResume() {
        super.onResume();
        if (cacheMonth != -1 || cacheYear != -1) getDataMonthYear();
        else LoadData();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentExpenseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lsvExpense = binding.lsvExpense;
        fabExpense = binding.fabExpense;
        spnYear = binding.spnExpenseYear;
        spnMonth = binding.spnExpenseMonth;

        data = new ArrayList<>();
        year = new ArrayList<>();
        month = new ArrayList<>();

        expenseAdapter = new ExpenseAdapter(getActivity(), data);
        lsvExpense.setAdapter(expenseAdapter);

        db = new DatabaseHelper(getActivity());

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddExpense.class);

                startActivity(intent);
            }
        });

        initSpinnerYear();

        return root;
    }

    void initSpinnerYear() {
        year.addAll(CreateListYear());
        month.addAll(CreateListMonth());

        ArrayAdapter<String> adapterMonth = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, month);
        spnMonth.setAdapter(adapterMonth);

        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, year);
        spnYear.setAdapter(adapterYear);

        spnMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) cacheMonth = -1;
                else cacheMonth = i;
                getDataMonthYear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) cacheYear = -1;
                else cacheYear = i;
                getDataMonthYear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void LoadData() {
        ArrayList<ExpenseModel> dt = db.getExpense();
        ArrayList<ExpenseModel> dtn = Sort(dt);

        data.clear();
        data.addAll(dtn);

        Change();
    }

    public ArrayList<ExpenseModel> Sort(ArrayList<ExpenseModel> input) {
        ArrayList<ExpenseModel> dt = input;
        dt.sort((o1, o2) ->
                o2.getDate().compareTo(o1.getDate()));

        for (int i = 0; i < dt.size(); i++) {
            ExpenseModel em = dt.get(i);
            em.setDate(DMYNoTime(em.getDate()));
            dt.set(i, em);
        }

        return dt;
    }

    void Change() {
        expenseAdapter.notifyDataSetChanged();
    }

    void getDataMonthYear() {
        if (cacheMonth != -1 || cacheYear != -1) {
            ArrayList<ExpenseModel> dt = new ArrayList<>();
            ArrayList<ExpenseModel> dtn = new ArrayList<>();

            if (cacheMonth != -1 && cacheYear != -1) {
                String m = month.get(cacheMonth);
                if (cacheMonth < 10) m = "0" + m;
                dt.addAll(db.getExpenseMonthYear(m, year.get(cacheYear)));
            }
            else if (cacheMonth != -1) {
                String m = month.get(cacheMonth);
                if (cacheMonth < 10) m = "0" + m;
                dt.addAll(db.getExpenseMonthYear(m, "-1"));
            }
            else {
                dt.addAll(db.getExpenseMonthYear("-1", year.get(cacheYear)));
            }

            dtn.addAll(Sort(dt));
            data.clear();
            data.addAll(dtn);
            Change();
        }
        else LoadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}