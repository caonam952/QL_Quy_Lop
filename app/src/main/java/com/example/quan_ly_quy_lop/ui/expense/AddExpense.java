package com.example.quan_ly_quy_lop.ui.expense;

import static com.example.quan_ly_quy_lop.XuLyChung.ChuoiRong;
import static com.example.quan_ly_quy_lop.XuLyChung.DMY;
import static com.example.quan_ly_quy_lop.XuLyChung.DeleteSpace;
import static com.example.quan_ly_quy_lop.XuLyChung.NotDot;
import static com.example.quan_ly_quy_lop.XuLyChung.Time;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


public class AddExpense extends AppCompatActivity {

    EditText edtContent;
    EditText edtDate;
    EditText edtMoney;

    AlertDialog alertDialog;
    DatePickerDialog.OnDateSetListener date;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtContent = findViewById(R.id.edtContentExpense);
        edtDate = findViewById(R.id.edtDateExpense);
        edtMoney = findViewById(R.id.edtMoneyExpense);

        db = new DatabaseHelper(this);
        alertDialog = new AlertDialog.Builder(AddExpense.this).create();

        init();
    }

    void init() {
        LocalDate now = LocalDate.now();

        edtDate.setText(DMY(now.toString()));

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                edtDate.setText(day + "-" + month + "-" + year);
            }
        };

        edtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtMoney.removeTextChangedListener(this);

                try {
                    String ori = s.toString();
                    if (ori.contains(",")) {
                        ori = ori.replaceAll(",", "");
                    }

                    String fmS = XuLyChung.Dot(ori);

                    edtMoney.setText(fmS);
                    edtMoney.setSelection(edtMoney.getText().length());
                } catch (Exception e) {

                }

                edtMoney.addTextChangedListener(this);
            }
        });

        alertDialog.setTitle("Chú ý");
    }

    public void Done(View view) {
        if (ChuoiRong(edtContent.getText().toString())) {
            ThongBaoDuoi("Không được bỏ trống nội dung");
        }
        else if (ChuoiRong(edtMoney.getText().toString())) {
            ThongBaoDuoi("Không được bỏ trống tiền");
        }
        else {
            alertDialog.setTitle("Chú ý!");
            alertDialog.setMessage("Bạn có muốn thêm không?");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String chuanHoa = DeleteSpace(edtContent.getText().toString());
                    LocalDateTime now = LocalDateTime.now();
                    String date = edtDate.getText().toString() + " " + Time(now.toString());
                    db.insertExpense(now.toString(), chuanHoa, NotDot(edtMoney.getText().toString()), date);
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

    public void Cancel(View view) {
        if (!ChuoiRong(edtContent.getText().toString()) || !ChuoiRong(edtMoney.getText().toString())) {
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


    public void PickDateExpense(View view) {
        String date = edtDate.getText().toString();
        int dash = date.indexOf('-');
        int day = Integer.parseInt(date.substring(0, dash));
        date = date.substring(dash + 1);
        dash = date.indexOf('-');
        int month = Integer.parseInt(date.substring(0, dash));
        int year = Integer.parseInt(date.substring(dash + 1));
        new DatePickerDialog(AddExpense.this, this.date, year, month - 1, day).show();
    }

    void ThongBaoDuoi(String input) {
        Toast.makeText(AddExpense.this, input, Toast.LENGTH_SHORT).show();
    }
}