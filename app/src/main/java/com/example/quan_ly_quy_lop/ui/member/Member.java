package com.example.quan_ly_quy_lop.ui.member;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.quan_ly_quy_lop.R;
import com.example.quan_ly_quy_lop.databinding.FragmentMemberBinding;
import com.example.quan_ly_quy_lop.models.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Member extends Fragment {

    private FragmentMemberBinding binding;

    FloatingActionButton fab;
    ListView member;
    Button delete;

    ArrayList<MemberModel> data;
    MemberAdapter adapter;

    DatabaseHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMemberBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        member = binding.lsvMember;
        fab = binding.fabMember;
        delete = binding.btnDeleteMember;

        data = new ArrayList<>();
        adapter = new MemberAdapter(getActivity(), data);

        db = new DatabaseHelper(getActivity());

        member.setAdapter(adapter);
        init();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogAdd();
            }
        });

        member.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                customDialogUpdate(data.get(i).getId(), data.get(i).getName());
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = 0;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).isCb()) dem++;
                }
                if (dem != 0) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                    dialog.setTitle("Chú ý!");
                    dialog.setMessage("Bạn có muốn xóa?");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int pos) {
                            for (int i = 0; i < data.size(); i++) {
                                if (data.get(i).isCb()) {
                                    db.deleteMember(data.get(i).getId());
                                }
                            }
                            init();
                            Change();
                            Toast.makeText(getActivity(), "Đã xóa!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Chọn 1 thành viên để xóa!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    void init() {
        ArrayList<MemberModel> dt = db.getMember();
        dt.sort((o1, o2) ->
                getName(o1.getName()).compareTo(getName(o2.getName())));

        for (int i = 0; i < dt.size(); i++) {
            MemberModel m = dt.get(i);
            m.setStt(String.valueOf(i + 1));
            dt.set(i, m);
        }

        data.clear();
        data.addAll(dt);
    }

    void Change() {
        adapter.notifyDataSetChanged();
    }

    public String getName(String input) {
        int space = input.lastIndexOf(' ');
        if (space != -1) {
            String c = input.substring(space + 1);
            return c;
        }
        return input;
    }

    void customDialogAdd() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog, null);

        EditText name = alertLayout.findViewById(R.id.edtNameMember);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setTitle("Thêm thành viên");
        dialog.setView(alertLayout);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LocalDateTime now = LocalDateTime.now();
                db.insertMember(now.toString(), name.getText().toString());
                init();
                Change();
                dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    void customDialogUpdate(String id, String t) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog, null);

        EditText name = alertLayout.findViewById(R.id.edtNameMember);
        name.setText(t);

        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setTitle("Thêm thành viên");
        dialog.setView(alertLayout);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.updateMember(id, name.getText().toString());

                init();
                Change();
                dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
