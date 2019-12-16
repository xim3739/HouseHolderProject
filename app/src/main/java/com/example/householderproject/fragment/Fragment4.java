package com.example.householderproject.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.WrapTogetherSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.householderproject.R;
import com.example.householderproject.model.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class Fragment4 extends Fragment {
    private View view;
    private LinearLayout line1, line2, line3, line4;
    private ArrayList<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_setting, container, false);

        line1 = view.findViewById(R.id.line1);
        line2 = view.findViewById(R.id.line2);
        line3 = view.findViewById(R.id.line3);
        line4 = view.findViewById(R.id.line4);

        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(v.getContext(), R.layout.setting_category_dialog, null);
                final EditText editTextAdd = dialogView.findViewById(R.id.editTextAdd);
                final Button btnAdd = dialogView.findViewById(R.id.btnAdd);
                final Button btnEdit = dialogView.findViewById(R.id.btnEdit);
                final Button btnDelete = dialogView.findViewById(R.id.btnDelete);
                final FlowLayout dialogflowLayout = dialogView.findViewById(R.id.flowlayout);
                final LinearLayout layoutButtonAdd = dialogView.findViewById(R.id.layoutButtonAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button connectButton = new Button(getContext());
                        for(int i= 0;i<list.size();i++){
                            if (editTextAdd.getText().toString().equals(list.get(i))){
                                return;
                            }
                        }
                        String stringAddButton = editTextAdd.getText().toString();
                        connectButton.setWidth((int) (layoutButtonAdd.getWidth() / 4));
                        connectButton.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        connectButton.setText(stringAddButton);
                        FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(10, 10);
                        connectButton.setLayoutParams(params);
                        dialogflowLayout.addView(connectButton);
                        list.add(connectButton.getText().toString());
                    }
                });
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editTextAdd.setText();
                    }
                });


                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setView(dialogView);
                dialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNegativeButton("취소", null);
                dialog.show();

                Animation animation = new AlphaAnimation(0.3f, 1.0f);
                animation.setDuration(500);
                v.startAnimation(animation);
            }
        });
        line2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        line3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation(0.3f, 1.0f);
                animation.setDuration(500);
                v.startAnimation(animation);
            }
        });
        line4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation(0.3f, 1.0f);
                animation.setDuration(500);
                v.startAnimation(animation);
            }
        });

        return view;
    }
}
