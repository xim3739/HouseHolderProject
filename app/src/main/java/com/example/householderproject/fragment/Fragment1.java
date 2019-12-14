package com.example.householderproject.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.householderproject.R;
import com.example.householderproject.adapter.CalendarListAdapter;
import com.example.householderproject.adapter.MonthAdapter;
import com.example.householderproject.calendar.MonthItemView;
import com.example.householderproject.model.CalendarListData;
import com.example.householderproject.model.MonthItem;
import com.example.householderproject.util.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.householderproject.MainActivity.myContext;

public class Fragment1 extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TextView btPrevious, btNext, txtDate;
    public static GridView gridViewCalendar;
    private TextView textViewYear;
    private ListView listView;
    private View view;
    private String str;
    private static String currentDate;

    private EditText editTextCredit;
    private EditText editTextLocation;
    private Spinner spinnerFilter;
    private RadioButton radioButtonPlus;
    private RadioButton radioButtonMinus;
    public static int selectedposition;

    public static CalendarListAdapter calendarListAdapter;
    private static ArrayList<CalendarListData> calendarList = new ArrayList<>();
    private static MonthAdapter monthAdapter;

    public static SQLiteDatabase sqlDB;
    public static DBHelper dbHelper;

    public static AdapterView<?> sparent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment1, container, false);

        btPrevious = view.findViewById(R.id.btnPrevious);
        btNext = view.findViewById(R.id.btnNext);
        listView = view.findViewById(R.id.listView);

        gridViewCalendar = view.findViewById(R.id.gvCalender);
        textViewYear = view.findViewById(R.id.tvYearMonth);


        //1.어뎁터를 생성
        monthAdapter = new MonthAdapter(myContext, selectedposition);
        gridViewCalendar.setAdapter(monthAdapter);
        setYearMonth();

        calendarListAdapter = new CalendarListAdapter(calendarList, R.layout.calendar_list_view_holder, myContext);
        listView.setAdapter(calendarListAdapter);

        gridViewCalendar.setOnItemClickListener(this);

        btNext.setOnClickListener(this);
        btPrevious.setOnClickListener(this);

        gridViewCalendar.setOnItemLongClickListener(this);

        return view;

    }


    // 클릭 이벤트
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnNext:
                monthAdapter.setNextMonth();
                monthAdapter.notifyDataSetChanged();
                calendarList.removeAll(calendarList);
                calendarListAdapter.notifyDataSetChanged();
                setYearMonth();
                break;

            case R.id.btnPrevious:
                monthAdapter.setPreviousMonth();
                monthAdapter.notifyDataSetChanged();
                calendarList.removeAll(calendarList);
                calendarListAdapter.notifyDataSetChanged();
                setYearMonth();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        sparent = parent;
        gridViewClickEvent(sparent, position);


    }

    //그리드뷰 클릭시 처리하는 함수
    public static void gridViewClickEvent(View view, int position) {
        MonthItem curItem = (MonthItem) monthAdapter.getItem(position);
        currentDate = String.valueOf(monthAdapter.curYear) + (monthAdapter.curMonth + 1);
        //어뎁터에 있는 위치의 값을 가져와 현재 위치에 넣어준다
        monthAdapter.setSelectedPosition(position);
        monthAdapter.notifyDataSetChanged();
        selectedposition = position;

        if (curItem.getDayValue() != 0) {

            dbHelper = new DBHelper(myContext);
            sqlDB = dbHelper.getReadableDatabase();

            Cursor cursor;
            cursor = sqlDB.rawQuery("SELECT * FROM calenderTBL WHERE date = '" + monthAdapter.curYear + ""
                    + (monthAdapter.curMonth + 1) + "" + monthAdapter.items[position].getDayValue() + "';", null);

            calendarList.removeAll(calendarList);

            while (cursor.moveToNext()) {

                int getNo = cursor.getInt(0);
                String getdate = cursor.getString(1);
                String getCredit = cursor.getString(2);
                String getDetail = cursor.getString(3);
                String getCategory = cursor.getString(4);
                String getLocation = cursor.getString(5);
                calendarList.add(new CalendarListData(getNo,getdate, getCredit, getDetail, getCategory,getLocation));

            }

            calendarListAdapter.notifyDataSetChanged();
            cursor.close();
            sqlDB.close();

        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

        switch (parent.getId()) {
            case R.id.gvCalender:
                MonthItem curItem1 = (MonthItem) monthAdapter.getItem(position);

                currentDate = monthAdapter.curYear + "/" + (monthAdapter.curMonth + 1);

                if (curItem1.getDayValue() != 0) {

                    dbHelper = new DBHelper(myContext);
                    sqlDB = dbHelper.getReadableDatabase();

                    Cursor cursor;
                    cursor = sqlDB.rawQuery("SELECT * FROM calenderTBL WHERE date = '" + monthAdapter.curYear + ""
                            + (monthAdapter.curMonth + 1) + "" + monthAdapter.items[position].getDayValue() + "';", null);

                    calendarList.removeAll(calendarList);

                    while (cursor.moveToNext()) {

                        int getNo = cursor.getInt(0);
                        String getCredit = cursor.getString(2);
                        String getDetail = cursor.getString(3);
                        String getCategory = cursor.getString(4);

                        calendarList.add(new CalendarListData(getNo, getCredit, getDetail, getCategory));

                    }

                    calendarListAdapter.notifyDataSetChanged();
                    cursor.close();
                    sqlDB.close();

                }

                selectFromDatabase(curItem1, position);

                View dialogView = View.inflate(view.getContext(), R.layout.calendar_input_data_dialog, null);

                editTextCredit = dialogView.findViewById(R.id.edtLittle);
                editTextLocation = dialogView.findViewById(R.id.editTextLocation);
                spinnerFilter = dialogView.findViewById(R.id.spinnerFilter);
                radioButtonPlus = dialogView.findViewById(R.id.rdoPlus);
                radioButtonMinus = dialogView.findViewById(R.id.rdoMinus);
                final MonthItem currentItem = (MonthItem) monthAdapter.getItem(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());

                spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        str = parent.getItemAtPosition(position).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });
                dialog.setView(dialogView);
                dialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        currentDate = String.valueOf(monthAdapter.curYear) + (monthAdapter.curMonth + 1) + currentItem.getDayValue();
                        Toast.makeText(getContext(), currentDate, Toast.LENGTH_LONG).show();

                        if (radioButtonPlus.isChecked() && !(spinnerFilter.getSelectedItem().toString().equals("필터를 설정해 주세요")) &&
                                !(editTextCredit.getText().toString().equals(""))) {

                            insertPlusDataBase(view.getContext(), currentDate, editTextCredit.getText().toString() + " 원",
                                    radioButtonPlus.getText().toString(), spinnerFilter.getSelectedItem().toString(), editTextLocation.getText().toString());

                        } else if (radioButtonMinus.isChecked() && !(spinnerFilter.getSelectedItem().toString().equals("필터를 설정해 주세요")) &&
                                !(editTextCredit.getText().toString().equals(""))) {

                            insertMinusDataBase(view.getContext(), currentDate, editTextCredit.getText().toString() + " 원", radioButtonMinus.getText().toString(), spinnerFilter.getSelectedItem().toString(), editTextLocation.getText().toString());
                        } else {
                            Toast.makeText(getContext(), "선택하지 않은 항목이 있습니다", Toast.LENGTH_LONG).show();
                        }
                        calendarListAdapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("취소", null);
                dialog.show();
                break;
        }
        return false;
    }

    private void selectFromDatabase(MonthItem curItem1, int position) {
    }

    // 월 표시 텍스트 설정
    private void setYearMonth() {

        String yearMonth = monthAdapter.curYear + "년   " + (monthAdapter.curMonth + 1) + " 월 ";
        textViewYear.setText(yearMonth);

    }

    //DB 에 저장
    private void insertMinusDataBase(Context context, String currentDate, String editTextCredit, String radioButtonMinus, String spinnerFilter, String editTextLocation) {

        dbHelper = new DBHelper(context);
        sqlDB = dbHelper.getWritableDatabase();

        sqlDB.execSQL("INSERT INTO calenderTBL VALUES(null,'" + currentDate + "','" + editTextCredit + "','"
                + radioButtonMinus + "','" + spinnerFilter + "', '" + editTextLocation + "');");

        sqlDB.close();

        calendarList.add(new CalendarListData(editTextCredit, radioButtonMinus, spinnerFilter,editTextLocation));
        monthAdapter.notifyDataSetChanged();

        Toast.makeText(context, str + "(으)로 " + editTextCredit + "의 지출이 발생하였습니다", Toast.LENGTH_LONG).show();

    }

    //DB 에 저장
    private void insertPlusDataBase(Context context, String currentDate, String editTextCredit, String radioButtonPlus, String spinnerFilter, String editTextLocation) {

        dbHelper = new DBHelper(context);
        sqlDB = dbHelper.getWritableDatabase();

        sqlDB.execSQL("INSERT INTO calenderTBL VALUES(null,'" + currentDate + "','" + editTextCredit + "','"
                + radioButtonPlus + "','" + spinnerFilter + "', '" + editTextLocation + "');");

        sqlDB.close();

        calendarList.add(new CalendarListData(editTextCredit, radioButtonPlus, spinnerFilter,editTextLocation));
        monthAdapter.notifyDataSetChanged();

        Toast.makeText(context, str + "(으)로 " + editTextCredit + "의 수입이 발생하였습니다", Toast.LENGTH_LONG).show();

    }
}