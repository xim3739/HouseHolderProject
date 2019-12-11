package com.example.householderproject.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.householderproject.R;
import com.example.householderproject.adapter.RecyclerViewAdapter;
import com.example.householderproject.model.RecyclerViewData;
import com.example.householderproject.util.DBHelper;
import com.github.mikephil.charting.charts.BarChart;

import net.daum.android.map.MapView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fragment3 extends Fragment implements DatePicker.OnDateChangedListener {

    private ArrayList<RecyclerViewData> list = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment3, container, false);

        DatePicker datePicker = view.findViewById(R.id.datePicker);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(list, R.layout.recyclerview_list_item_holder);
        recyclerView.setAdapter(recyclerViewAdapter);

        datePicker.setOnDateChangedListener(this);

        return view;

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = "";

        if(monthOfYear < 10 && dayOfMonth < 10) {
            date = year + "0" + monthOfYear + "0" + dayOfMonth;
        } else if(monthOfYear < 10 && dayOfMonth > 10) {
            date = year + "0" + monthOfYear + "" +dayOfMonth;
        } else if (monthOfYear > 10 && dayOfMonth < 10) {
            date = year + "" + monthOfYear + "0" +dayOfMonth;
        } else {
            date = year + "" + monthOfYear + "" + dayOfMonth;
        }

        DBHelper dbHelper = new DBHelper(view.getContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM calendarTBL WHERE date = '" + date + "';", null);
    }

}
