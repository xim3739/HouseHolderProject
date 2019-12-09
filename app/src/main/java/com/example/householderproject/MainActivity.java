package com.example.householderproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.householderproject.fragment.Fragment1;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment1 fragment1;
//    private Fragment2 fragment2;
//    private Fragment3 fragment3;
//    private Fragment4 fragment4;

    public static Context myContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DBHelper dbHelper = new DBHelper(this);
//        SQLiteDatabase sqLiteOpenHelper = dbHelper.getReadableDatabase();
//        dbHelper.onUpgrade(sqLiteOpenHelper, 1, 2);

        myContext = MainActivity.this;

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconTintList(null);
        frameLayout = findViewById(R.id.frameLayout);
        fragment1 = new Fragment1();
//        fragment2 = new Fragment2();
//        fragment3 = new Fragment3();
//        fragment4 = new Fragment4();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_1: //fragmentManager1 화면 전환
                        setChangeFragment(0);
                        break;
//                    case R.id.action_2: //fragmentManager2 화면 전환
//                        setChangeFragment(1);
//                        break;
//                    case R.id.action_3: //fragmentManager3 화면 전환
//                        setChangeFragment(2);
//                        break;
//                    case R.id.action_4: //fragmentManager4 화면 전환
//                        setChangeFragment(3);
//                        break;
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_1);


    }

    //화면 전환
    private void setChangeFragment(int position) {
        //화면을 전환하기 위해서 fragmentManager 필요
        fragmentManager = getSupportFragmentManager();
        //fragmentManager의 권한을 받아서 화면 체인지 하는 Transaction 필요
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                fragmentTransaction.replace(R.id.frameLayout, fragment1);
                fragmentTransaction.commit();
                break;
//            case 1:
//                fragmentTransaction.replace(R.id.frameLayout, fragment2);
//                fragmentTransaction.commit();
//                break;
//            case 2:
//                fragmentTransaction.replace(R.id.frameLayout, fragment3);
//                fragmentTransaction.commit();
//                break;
//            case 3:
//                fragmentTransaction.replace(R.id.frameLayout, fragment4);
//                fragmentTransaction.commit();
//                break;
        }
    }

}
