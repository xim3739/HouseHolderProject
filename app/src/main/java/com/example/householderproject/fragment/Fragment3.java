package com.example.householderproject.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import com.example.householderproject.MainActivity;
import com.example.householderproject.R;
import com.example.householderproject.adapter.RecyclerViewAdapter;
import com.example.householderproject.model.RecyclerViewData;
import com.example.householderproject.util.DBHelper;
import com.github.mikephil.charting.charts.BarChart;

import net.daum.android.map.MapView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class Fragment3 extends Fragment implements DatePicker.OnDateChangedListener {

    private ArrayList<RecyclerViewData> list = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment3, container, false);

        DatePicker datePicker = view.findViewById(R.id.datePicker);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.myContext);

        recyclerView.setLayoutManager(linearLayoutManager);

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        Log.e("!!!", year + "||" + month + "||" + day);

        String transDate = transformDate(year, month, day);

        Log.e("!!!", transDate);

        recyclerViewAdapter = new RecyclerViewAdapter(list, R.layout.recyclerview_list_item_holder);

        selectFromDatabase(transDate);

        recyclerView.setAdapter(recyclerViewAdapter);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);

        Log.e("!!!", "onCreateView");

        return view;

    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        String date = "";

        date = transformDate(year, monthOfYear, dayOfMonth);

        Log.e("!!!", date);

        selectFromDatabase(date);

    }

    private void selectFromDatabase(String date) {

        DBHelper dbHelper = new DBHelper(MainActivity.myContext);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM calenderTBL WHERE date = '" + date + "';", null);

        Log.e("!!!", "onDateChanged");

        list.removeAll(list);
        while(cursor.moveToNext()) {

            list.add(new RecyclerViewData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));

        }

        recyclerViewAdapter.notifyDataSetChanged();

    }

    private String transformDate(int year, int monthOfYear, int dayOfMonth) {

        String date = "";

        if(monthOfYear < 10 && dayOfMonth < 10) {
            date = year + "0" + (monthOfYear+1) + "0" + dayOfMonth;
        } else if(monthOfYear < 10 && dayOfMonth > 10) {
            date = year + "0" + (monthOfYear+1) + "" +dayOfMonth;
        } else if (monthOfYear > 10 && dayOfMonth < 10) {
            date = year + "" + (monthOfYear+1) + "0" +dayOfMonth;
        } else {
            date = String.valueOf(year) + (monthOfYear+1) + (dayOfMonth);
        }

        return date;

    }

}
