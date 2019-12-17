package com.example.householderproject.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.householderproject.model.HouseHoldModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper dbHelper = null;

    private DBHelper(@Nullable Context context) {

        super(context,"calenderDB",null,1);

    }

    public static DBHelper getInstance(Context context) {

        if(dbHelper == null) {
            dbHelper = new DBHelper(context);
        }

        return dbHelper;

    }

    public static ArrayList<HouseHoldModel> selectYearDateDatabase(ArrayList<HouseHoldModel> listViewData, Context context, int yearOfNow, String detail) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM calenderTBL WHERE date like '" + yearOfNow + "%' AND detail = '" + detail + "';", null);

        listViewData.removeAll(listViewData);

        while(cursor.moveToNext()) {

            listViewData.add(new HouseHoldModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));

        }

        cursor.close();
        sqLiteDatabase.close();

        return listViewData;
    }

    public static ArrayList<HouseHoldModel> selectYearAndMonthDateDatabase(ArrayList<HouseHoldModel> listViewData, Context context, int yearOfNow, int monthOfNow, String detail) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM calenderTBL WHERE date like '" + yearOfNow + "" + monthOfNow + "%' AND detail = '" + detail + "';", null);

        listViewData.removeAll(listViewData);

        while(cursor.moveToNext()) {

            listViewData.add(new HouseHoldModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));

        }

        cursor.close();
        sqLiteDatabase.close();

        return listViewData;
    }

    public static void updateFromNumberDatabase(Context context, String credit, String detail, String category, String location, int no) {
        DBHelper myDBHelper = DBHelper.getInstance(context);
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("UPDATE calenderTBL SET credit = '" + credit + "', detail = '" + detail + "',category = '" + category + "',location = '" + location + "' WHERE id ="+ no +";");
        sqlDB.close();

    }

    public static void deleteFromDatabase(Context context, int no) {
        DBHelper myDBHelper = DBHelper.getInstance(context);
        SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("DELETE FROM calenderTBL WHERE id = " + no + ";");
        sqlDB.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE calenderTBL(id INTEGER PRIMARY KEY AUTOINCREMENT , date CHAR(20), credit CHAR(10), detail CHAR(10), category CHAR(20), location CHAR(30));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS calenderTBL");
        onCreate(db);

    }

    public static ArrayList<HouseHoldModel> selectDateFromDatabase(ArrayList<HouseHoldModel> calendarList, Context context, String currentDate) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM calenderTBL WHERE date = '" + currentDate + "';", null);

        calendarList.removeAll(calendarList);

        while (cursor.moveToNext()) {

            int getNo = cursor.getInt(0);
            String getDate = cursor.getString(1);
            String getCredit = cursor.getString(2);
            String getDetail = cursor.getString(3);
            String getCategory = cursor.getString(4);
            String getLocation = cursor.getString(5);

            calendarList.add(new HouseHoldModel(getNo, getDate, getCredit, getDetail, getCategory, getLocation));

        }

        cursor.close();
        sqLiteDatabase.close();

        return calendarList;

    }


    public static void insertIncomeData(Context context, String currentDate, String editTextCredit, String radioButtonPlus, String spinnerFilter, String editTextLocation) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        sqLiteDatabase.execSQL("INSERT INTO calenderTBL VALUES(null,'" + currentDate + "','" + editTextCredit + "','"
                + radioButtonPlus + "','" + spinnerFilter + "', '" + editTextLocation + "');");

        sqLiteDatabase.close();
    }

    public static void insertSpentData(Context context, String currentDate, String editTextCredit, String radioButtonMinus, String spinnerFilter, String editTextLocation) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        sqLiteDatabase.execSQL("INSERT INTO calenderTBL VALUES(null,'" + currentDate + "','" + editTextCredit + "','"
                + radioButtonMinus + "','" + spinnerFilter + "', '" + editTextLocation + "');");

        sqLiteDatabase.close();
    }


}
