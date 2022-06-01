package com.example.quan_ly_quy_lop.ui.home;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quan_ly_quy_lop.XuLyChung;
import com.example.quan_ly_quy_lop.databinding.FragmentHomeBinding;
import com.example.quan_ly_quy_lop.models.DatabaseHelper;

import java.util.ArrayList;

public class Home extends Fragment {

    private FragmentHomeBinding binding;
    Spinner spnYear;
    TextView txtBalance;
    TextView txtIncome;
    TextView txtExpense;

    ListView lsvStatistics;
    ArrayList<String> year;
    ArrayList<StatisticsModel> data;
    ArrayList<StatisticsModel> rootDT;
    StatisticsAdapter statisticsAdapter;

    DatabaseHelper db;
    int cacheYear = -1;

    @Override
    public void onResume() {
        super.onResume();
        filterYear();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txtBalance = binding.txtBalanceHome;
        txtIncome = binding.txtTotalIncome;
        txtExpense = binding.txtTotalExpense;
        spnYear = binding.spnYear;
        lsvStatistics = binding.lsvStatistics;

        year = new ArrayList<>();
        data = new ArrayList<>();
        rootDT = new ArrayList<>();

        statisticsAdapter = new StatisticsAdapter(getActivity(), data);
        lsvStatistics.setAdapter(statisticsAdapter);
        lsvStatistics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Detail_Statistic.class);

                Bundle bd = new Bundle();
                bd.putString("date", data.get(i).getTitle());

                intent.putExtras(bd);
                startActivity(intent);
            }
        });

        db = new DatabaseHelper(getActivity());

        initSpinner();
        initListView();

        return root;
    }

    void initSpinner() {

        year.addAll(XuLyChung.CreateListYear());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, year);
        spnYear.setAdapter(adapter);

        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) cacheYear = i;
                else cacheYear = -1;
                filterYear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void initListView() {
        ArrayList<StatisticsModel> dt = db.getStatistic();
        dt.sort((o1, o2) ->
                o2.getTitle().compareTo(o1.getTitle()));
        int balance = 0;
        int sumIncome = 0;
        int sumExpense = 0;
        for (int i = dt.size() - 1; i >= 0; i--) {
            StatisticsModel a = dt.get(i);
            int income = Integer.parseInt(a.getIncome());
            int expense = Integer.parseInt(a.getExpense());
            sumIncome += income;
            sumExpense += expense;
            balance += income - expense;
            a.setBalance(String.valueOf(balance));
            dt.set(i, a);
        }
        rootDT.addAll(dt);
        data.addAll(dt);

        txtBalance.setText(balance + " VNĐ");
        txtIncome.setText(sumIncome + " VNĐ");
        txtExpense.setText(sumExpense + " VNĐ");
    }

    void filterYear() {
        if (cacheYear == -1) {
            data.clear();
            data.addAll(rootDT);
        }
        else {
            ArrayList<StatisticsModel> loc = new ArrayList<>();
            String y = year.get(cacheYear);
            for (int i = 0; i < rootDT.size(); i++) {
                if (getYear(rootDT.get(i).title).equals(y)) {
                    loc.add(rootDT.get(i));
                }
            }
            data.clear();
            data.addAll(loc);
        }
    }

    public String getYear(String input) {
        int dash = input.indexOf('-');
        String c = input.substring(dash + 1);
        return c;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}