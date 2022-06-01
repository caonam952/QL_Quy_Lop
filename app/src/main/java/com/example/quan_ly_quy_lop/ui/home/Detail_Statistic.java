package com.example.quan_ly_quy_lop.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.quan_ly_quy_lop.R;
import com.example.quan_ly_quy_lop.models.DatabaseHelper;

import java.time.YearMonth;
import java.util.ArrayList;

public class Detail_Statistic extends AppCompatActivity {

    TextView txtTitle;
    ListView lsv;
    Spinner spn;

    ArrayList<Detail_Statistic_Model> data;
    ArrayList<Detail_Statistic_Model> root_data;
    Detail_Statistic_Adapter adapter;
    ArrayList<String> days;

    DatabaseHelper db;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_statistic);

        txtTitle = findViewById(R.id.txtTitleDetailStatistic);
        lsv = findViewById(R.id.lsvDetailStatistic);
        spn = findViewById(R.id.spnDetailStatistic);

        data = new ArrayList<>();
        root_data = new ArrayList<>();
        adapter = new Detail_Statistic_Adapter(this, data);

        days = new ArrayList<>();
        lsv.setAdapter(adapter);

        db = new DatabaseHelper(this);

        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            date = bd.getString("date");
        }

        txtTitle.setText("CHI TIẾT " + date);

        init();
        initSpn();
    }

    void init() {
        ArrayList<Detail_Statistic_Model> dt = db.getStatisticMY(date);
        dt.sort((o1, o2) ->
                o2.getDate().compareTo(o1.getDate()));

        root_data.addAll(dt);
        data.addAll(dt);
    }

    void initSpn() {
        int dash = date.indexOf('-');
        int month = Integer.parseInt(date.substring(0, dash));
        int year = Integer.parseInt(date.substring(dash + 1));
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        days = new ArrayList<>();
        days.add("All");
        for (int i = 1; i <= daysInMonth; i++) {
            days.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        spn.setAdapter(adapter);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    ArrayList<Detail_Statistic_Model> filter = new ArrayList<>();

                    String day;
                    if (i < 10) day = "0" + days.get(i);
                    else day = days.get(i);
                    for (int j = 0; j < root_data.size(); j++) {
                        String dateR = root_data.get(j).getDate();
                        int dash = dateR.indexOf('-');
                        String cD = dateR.substring(0, dash);
                        if (cD.equals(day)) filter.add(root_data.get(j));
                    }
                    data.clear();
                    data.addAll(filter);
                }
                else {
                    data.clear();
                    data.addAll(root_data);
                }
                Change();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void Change() {
        adapter.notifyDataSetChanged();
    }

    public void Back(View view) {
        finish();
    }
}