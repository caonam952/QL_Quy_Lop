package com.example.quan_ly_quy_lop.ui.income;

import static com.example.quan_ly_quy_lop.XuLyChung.DateTime;
import static com.example.quan_ly_quy_lop.XuLyChung.Dot;
import static com.example.quan_ly_quy_lop.XuLyChung.Name;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quan_ly_quy_lop.R;
import com.example.quan_ly_quy_lop.XuLyChung;
import com.example.quan_ly_quy_lop.models.DatabaseHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Detail_Income extends AppCompatActivity {

    TextView txtTitle;
    TextView txtMoney;
//    TextView txtDaThu;
//    TextView txtChuaThu;
    TableLayout tblListPeople;

    ArrayList<Detail_IncomeModel> data;
    Detail_IncomeAdapter adapter;
    AlertDialog dialog;
    Bundle bd;
    String idIncome;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_income);

        txtTitle = findViewById(R.id.txtTitleDetailIncome);
        txtMoney = findViewById(R.id.txtMoneyPP);
//        txtDaThu = findViewById(R.id.txtDaThu);
//        txtChuaThu = findViewById(R.id.txtChuaThu);
        tblListPeople = findViewById(R.id.tblListPeople);

        dialog = new AlertDialog.Builder(this).create();
        data = new ArrayList<>();
        adapter = new Detail_IncomeAdapter(Detail_Income.this, data);
        db = new DatabaseHelper(this);

        bd = getIntent().getExtras();

        if (bd != null) {
            String money = Dot(bd.getString("money"));
            idIncome = bd.getString("id");
            txtTitle.setText("Từ ngày " + bd.get("date"));
            txtMoney.setText("Số tiền thu mỗi người: " + money + " VNĐ");
//            txtDaThu.setText("Đã thu: ");
//            txtChuaThu.setText("Chưa thu: ");
        }

        init();
    }

    void init() {
        ArrayList<Detail_IncomeModel> dt = getAndSort();

        for (int i = 0; i < dt.size(); i++) {
            Detail_IncomeModel d = dt.get(i);
            d.setStt(String.valueOf(i + 1));
            dt.set(i, d);
        }

        data.addAll(dt);

        for (int i = 0; i < adapter.getCount(); i++) {
            tblListPeople.addView(adapter.getView(i, null, tblListPeople));
        }
    }

    // Lấy và sắp xếp tên theo alphaB
    ArrayList<Detail_IncomeModel> getAndSort() {
        ArrayList<Detail_IncomeModel> data = db.getDetailIncome(idIncome);
        data.sort((o1, o2) ->
                Name(o1.getHt()).compareTo(Name(o2.getHt())));
        return data;
    }

    public void Back(View view) {
        if (CheckThayDoi()) {
            dialog.setTitle("Chú ý!");
            dialog.setMessage("Bạn có muốn hủy bọ thay đổi?");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                    finish();
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else {
            finish();
        }
    }

    public void Done(View view) {
        if (CheckThayDoi()) {
            dialog.setTitle("Chú ý!");
            dialog.setMessage("Bạn có muốn cập nhật thay đổi không?");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int pos) {
                    ArrayList<Detail_IncomeModel> dt = getAndSort();
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getCb() != dt.get(i).getCb()) {
                            LocalDateTime now = LocalDateTime.now();
                            String date = DateTime(now.toString());
                            db.updateIncome(idIncome, data.get(i).id, data.get(i).getCb(), date);
                        }
                    }
                    tblListPeople.removeAllViews();
                    data.clear();
                    init();
                    ThongBao("Cập nhật thành công!");
                    CheckTable();
                    dialog.dismiss();
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else {
            dialog.setTitle("Không có thay đổi để cập nhật!");
            dialog.setMessage("");
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    // Kiểm tra tất cả thành viên đã đóng góp chuyển bảng thành tích xanh
    public void CheckTable() {
        ArrayList<Detail_IncomeModel> dt = db.getDetailIncome(idIncome);
        int i = 0;
        while (i < dt.size()) {
            if (!dt.get(i).getCb()) return;
            else i++;
        }
        db.updateSessionIncome(idIncome, true);
    }

    // Kiểm tra xem có thay đổi so với ban đầu ko
    public boolean CheckThayDoi() {
        ArrayList<Detail_IncomeModel> dt = getAndSort();

        int i = 0;
        while (i < dt.size()) {
            if (data.get(i).getCb() != dt.get(i).getCb()) return true;
            i++;
        }
        return false;
    }

    public void ThongBao(String tb) {
        Toast.makeText(Detail_Income.this, tb, Toast.LENGTH_SHORT).show();
    }

    public void ShowContent(View view) {
        if (bd != null) {
            dialog.setMessage(bd.getString("content"));
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}