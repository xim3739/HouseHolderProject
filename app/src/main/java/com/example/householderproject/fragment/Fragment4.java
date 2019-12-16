package com.example.householderproject.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.householderproject.R;
import com.example.householderproject.receiver.SmsReceiver;

public class Fragment4 extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout line1, line4;
    private ImageButton customDenseGreen, customPink, customGreen, customWhite, customBrightGreen, customDark, customGray;
    private Switch switchOnOff;
    public static boolean settingFlag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_setting, container, false);

        line1 = view.findViewById(R.id.line1);
        line4 = view.findViewById(R.id.line4);

        customDenseGreen = view.findViewById(R.id.customDenseGreen);
        customPink = view.findViewById(R.id.customPink);
        customGreen = view.findViewById(R.id.customGreen);
        customWhite = view.findViewById(R.id.customWhite);
        customBrightGreen = view.findViewById(R.id.customBrightGreen);
        customDark = view.findViewById(R.id.customDark);
        customGray = view.findViewById(R.id.customGray);
        switchOnOff = view.findViewById(R.id.switchOnOff);

        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(v.getContext(), R.layout.setting_category_dialog, null);

                final EditText editTextAdd = dialogView.findViewById(R.id.editTextAdd);
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

        // 테마색 변경을 위한 이벤트
        customDenseGreen.setOnClickListener(this);
        customPink.setOnClickListener(this);
        customGreen.setOnClickListener(this);
        customWhite.setOnClickListener(this);
        customBrightGreen.setOnClickListener(this);
        customDark.setOnClickListener(this);
        customGray.setOnClickListener(this);
        //


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customDenseGreen:
                AnimationEffect(v);
                Utils.setStatusBarColor(getActivity(), Utils.StatusBarColorType.CUSTOM_DENSE_GREEN);
                break;
            case R.id.customPink:
                AnimationEffect(v);
                Utils.setStatusBarColor(getActivity(), Utils.StatusBarColorType.CUSTOM_PINK);
                break;
            case R.id.customGreen:
                AnimationEffect(v);
                Utils.setStatusBarColor(getActivity(), Utils.StatusBarColorType.CUSTOM_GREEN);
                break;
            case R.id.customWhite:
                AnimationEffect(v);
                Utils.setStatusBarColor(getActivity(), Utils.StatusBarColorType.CUSTOM_WHITE);
                break;
            case R.id.customBrightGreen:
                AnimationEffect(v);
                Utils.setStatusBarColor(getActivity(), Utils.StatusBarColorType.CUSTOM_BRIGHTGREEN);
                break;
            case R.id.customDark:
                AnimationEffect(v);
                Utils.setStatusBarColor(getActivity(), Utils.StatusBarColorType.CUSTOM_DARK);
                break;
            case R.id.customGray:
                AnimationEffect(v);
                Utils.setStatusBarColor(getActivity(), Utils.StatusBarColorType.CUSTOM_GRAY);
                break;

        }
    }
    //애니메이션 효과
    private void AnimationEffect(View v) {
        Animation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(500);
        v.startAnimation(animation);
    }

    //테마 변경을 위한 클래스
    //Enum클래스형을 기반으로 한 StatusBarColorType을 선언하여 7가지 타입의 StatusBarColor를 정의, 열거형 상수와 관련된 값을 생성자를 통해 연결시켜 사용
    public static class Utils {
        public enum StatusBarColorType {
            CUSTOM_DENSE_GREEN(R.color.customDenseGreen),
            CUSTOM_PINK(R.color.customPink),
            CUSTOM_GREEN(R.color.customGreen),
            CUSTOM_WHITE(R.color.customWhite),
            CUSTOM_BRIGHTGREEN(R.color.customBrightGreen),
            CUSTOM_DARK(R.color.customDark),
            CUSTOM_GRAY(R.color.customGray);

            private int backgroundColorId;

            StatusBarColorType(int backgroundColorId) {
                this.backgroundColorId = backgroundColorId;
            }

            public int getBackgroundColorId() {
                return backgroundColorId;
            }
        }

        //API level21에서 추가된 setStatusBarColor 함수를 이용해 열거형 상수와 연결된 값으로 StatusBar색상을 변경
        public static void setStatusBarColor(Activity activity, StatusBarColorType colorType) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, colorType.getBackgroundColorId()));
            }
        }
    }

  /*  //앱이 종료되어도 값을 저장할 부분
    @Override
    public void onResume() {
        super.onResume();
        //switch버튼, notification 알림 끄기
        switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // The toggle is enabled -> work
                if (isChecked) {
                    settingFlag = true;
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked", true);
                    editor.commit();
                } else {// The toggle is disabled -> stop
                    settingFlag = false;
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked", false);
                    editor.commit();
                }
            }
        });

    }
//그 저장한 부분을 부르는 곳
    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Boolean checked = sharedPreferences.getBoolean("checked", true);

        if(checked){
            settingFlag = true;
        }else{
            settingFlag = false;
        }
    }

//값을 저장
    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        Boolean checked = sharedPreferences.getBoolean("checked", true);

        if(checked){
            settingFlag = true;
        }else{
            settingFlag = false;
        }
    }*/
}
