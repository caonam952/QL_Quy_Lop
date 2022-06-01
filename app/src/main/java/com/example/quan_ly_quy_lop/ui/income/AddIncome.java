package com.example.quan_ly_quy_lop.ui.income;

import static com.example.quan_ly_quy_lop.XuLyChung.ChuoiRong;
import static com.example.quan_ly_quy_lop.XuLyChung.DeleteSpace;
import static com.example.quan_ly_quy_lop.XuLyChung.NotDot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quan_ly_quy_lop.R;
import com.example.quan_ly_quy_lop.XuLyChung;
import com.example.quan_ly_quy_lop.models.DatabaseHelper;
import com.example.quan_ly_quy_lop.ui.expense.AddExpense;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class AddIncome extends AppCompatActivity {

    EditText edtContentIncome;
    EditText edtDate;
    EditText edtMoneyIncome;

    AlertDialog alertDialog;
    DatePickerDialog.OnDateSetListener date;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        edtContentIncome = findViewById(R.id.edtContentIncome);
        edtDate = findViewById(R.id.edtDateStartIncome);
        edtMoneyIncome = findViewById(R.id.edtMoneyAddIncome);

        db = new DatabaseHelper(this);
        alertDialog = new AlertDialog.Builder(AddIncome.this).create();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                edtDate.setText(day + "-" + month + "-" + year);
            }
        };

        init();
    }

    void init() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate now = LocalDate.now();
        edtDate.setText(dtf.format(now));

        edtMoneyIncome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                edtMoneyIncome.removeTextChangedListener(this);

                try {
                    String ori = s.toString();
                    if (ori.contains(",")) {
                        ori = ori.replaceAll(",", "");
                    }

                    String fmS = XuLyChung.Dot(ori);

                    edtMoneyIncome.setText(fmS);
                    edtMoneyIncome.setSelection(edtMoneyIncome.getText().length());
                } catch (Exception e) {

                }

                edtMoneyIncome.addTextChangedListener(this);

            }
        });
    }

    // Nút hủy thêm
    public void Cancel(View view) {
        if (!ChuoiRong(edtContentIncome.getText().toString()) || !ChuoiRong(edtMoneyIncome.getText().toString())) {
            alertDialog.setMessage("Bạn có muốn hủy không?");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                    finish();
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
        else finish();
    }

    // Nút thêm
    public void Done(View view) {
        if (ChuoiRong(edtContentIncome.getText().toString())) {
            ThongBaoDuoi("Không được bỏ trống nội dung");
        }
        else if (ChuoiRong(edtMoneyIncome.getText().toString())) {
            ThongBaoDuoi("Không được bỏ trống tiền");
        }
        else {
            alertDialog.setMessage("Bạn có chắc chắn muốn thêm không?");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    LocalDateTime now = LocalDateTime.now();
                    String chuanHoa = DeleteSpace(edtContentIncome.getText().toString());
                    int money = NotDot(edtMoneyIncome.getText().toString());

                    db.insertSessionIncome(now.toString(), chuanHoa, money, edtDate.getText().toString(), false);

                    alertDialog.dismiss();
                    finish();
                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    public void PickDateIncome(View view) {
        String date = edtDate.getText().toString();
        int dash = date.indexOf('-');
        int day = Integer.parseInt(date.substring(0, dash));
        date = date.substring(dash + 1);
        dash = date.indexOf('-');
        int month = Integer.parseInt(date.substring(0, dash));
        int year = Integer.parseInt(date.substring(dash + 1));
        new DatePickerDialog(AddIncome.this, this.date, year, month - 1, day).show();
    }

    void ThongBaoDuoi(String input) {
        Toast.makeText(AddIncome.this, input, Toast.LENGTH_SHORT).show();
    }
}