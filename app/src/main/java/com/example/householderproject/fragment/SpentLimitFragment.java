package com.example.householderproject.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.householderproject.MainActivity;
import com.example.householderproject.R;
import com.example.householderproject.adapter.StaticsAdapter;
import com.example.householderproject.model.CalendarListData;
import com.example.householderproject.util.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class SpentLimitFragment extends Fragment{

    private Button btDatePicker;

    private TextView textViewRemainCredit;
    private TextView textViewPercentage;
    private TextView textViewBudget;
    private TextView textViewSpent;

    private ListView listViewSpent;
    private ListView listViewIncome;

    private ProgressBar progressBar;

    private int yearOfNow = 0;
    private int monthOfNow = 0;

    private ArrayList<CalendarListData> spentList = new ArrayList<>();
    private ArrayList<CalendarListData> incomeList = new ArrayList<>();
    private ArrayList<CalendarListData> incomeListForProgressBar = new ArrayList<>();

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.spending_limit_fragment, container, false);

        btDatePicker = view.findViewById(R.id.btDatePicker);
        textViewRemainCredit = view.findViewById(R.id.textViewRemainCredit);
        textViewPercentage = view.findViewById(R.id.textViewPercentage);
        textViewBudget = view.findViewById(R.id.textViewBudget);
        textViewSpent = view.findViewById(R.id.textViewSpent);

        listViewSpent = view.findViewById(R.id.listViewSpent);
        listViewIncome = view.findViewById(R.id.listViewIncome);

        progressBar = view.findViewById(R.id.progressBar);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        yearOfNow = year;
        monthOfNow = month;

        btDatePicker.setText(yearOfNow + "년 " + monthOfNow + "월");

        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        yearOfNow = year;
                        monthOfNow = month + 1;

                        btDatePicker.setText(yearOfNow + "년 " + monthOfNow + "월");

                        dbHelper = new DBHelper(getContext());
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        cursor = sqLiteDatabase.rawQuery("SELECT * FROM calenderTBL WHERE date like '" + yearOfNow + "" + monthOfNow + "%' AND detail = '" + "지출" + "';", null);

                        spentList.removeAll(spentList);
                        while(cursor.moveToNext()) {
                            spentList.add(new CalendarListData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
                        }

                        int spentSum = 0;
                        for(int i = 0; i < spentList.size(); i++) {
                            String stringSpentSum = spentList.get(i).getCredit().replaceAll(",","");
                            spentSum = Integer.parseInt(stringSpentSum) + spentSum;
                        }
                        StaticsAdapter spentstaticsAdapter = new StaticsAdapter(getContext(), spentList);
                        listViewSpent.setAdapter(spentstaticsAdapter);

                        cursor.close();

                        cursor = sqLiteDatabase.rawQuery("SELECT * FROM calenderTBL WHERE date like '" + String.valueOf(yearOfNow) + String.valueOf(monthOfNow) + "%' AND detail = '" + "수입" + "';", null);

                        incomeList.removeAll(incomeList);
                        while(cursor.moveToNext()) {
                            incomeListForProgressBar.add(new CalendarListData(cursor.getString(2)));
                            incomeList.add(new CalendarListData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
                        }

                        int incomeSum = 0;
                        for(int i = 0; i < incomeList.size(); i++) {
                            String stringIncomSum = incomeList.get(i).getCredit().replaceAll(",", "");
                            incomeSum = Integer.parseInt(stringIncomSum) + incomeSum;
                        }

                        int remainMoney = incomeSum - spentSum;

                        textViewRemainCredit.setText(String.valueOf(remainMoney));
                        textViewBudget.setText(String.valueOf(incomeSum));
                        textViewSpent.setText(String.valueOf(spentSum));

                        try {
                            Log.d("spent", "ddd"+String.valueOf(incomeSum));
                            float ratio;
                            ratio = (float) spentSum / incomeSum;

                            Log.d("spent", String.valueOf(ratio));
                            int percent = (int) (ratio * 100);

                            progressBar.setMax(100);
                            progressBar.setScaleY(3f);

                            Log.d("spent", String.valueOf(percent));
                            if(percent <= 100) {
                                if(percent > 1) {
                                    progressBar.setProgress(percent);
                                    textViewPercentage.setText(String.valueOf(percent) + "% 사용중");
                                } else {
                                    progressBar.setProgress(percent);
                                    textViewPercentage.setText("1% 이하 사용중");
                                }
                            } else {
                                progressBar.setProgress(100);
                                textViewPercentage.setText("예산을 초과하였습니다.");
                            }

                        } catch (ArithmeticException e) {
                            progressBar.setProgress(0);
                            textViewPercentage.setText("예산을 설정 하지 않았습니다.");
                        }

                        StaticsAdapter incomeStaticsAdapter1 = new StaticsAdapter(getContext(), incomeList);
                        listViewIncome.setAdapter(incomeStaticsAdapter1);

                        cursor.close();
                        sqLiteDatabase.close();
                    }

                }, year, month - 1, day);
                datePickerDialog.getDatePicker().findViewById(getResources().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                datePickerDialog.show();
            }
        });

        return view;
    }


}
