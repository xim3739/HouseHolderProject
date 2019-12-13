package com.example.householderproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.daimajia.swipe.SwipeLayout;
import com.example.householderproject.R;
import com.example.householderproject.fragment.Fragment1;
import com.example.householderproject.model.CalendarListData;
import com.example.householderproject.util.DBHelper;

import java.util.ArrayList;

public class CalendarListAdapter extends BaseAdapter {

    // 레이아웃의 아이디를 가지고 인플레이터를 이용해서 객체만듬
    private LayoutInflater inflater;
    //데이터
    private ArrayList<CalendarListData> list;
    // 레이아웃의 아이디
    private int layoutID;
    //어뎁터가 어디서 사용될지 알아야 한다
    private Context context;

    private boolean deleteFlag = false;


    public CalendarListAdapter(ArrayList<CalendarListData> list, int layoutID, Context context) {
        this.list = list;
        this.layoutID = layoutID;
        this.context = context;
        this.inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        if (convertView == null) { convertView = inflater.inflate(layoutID, null); }

        ImageView listImgView = convertView.findViewById(R.id.listImgView);
        TextView listTextViewRdo = convertView.findViewById(R.id.listTextViewRdo);//detail
        final TextView listTextViewCombo = convertView.findViewById(R.id.listTextViewCombo);//categry
        TextView listTextViewEdt = convertView.findViewById(R.id.listTextViewEdt);//credit
        ImageButton imgBtnTrash = convertView.findViewById(R.id.imgBtnTrash);

        //리스트의 이미지 버튼 이벤트가 발생 하는 부분이다. 이벤트가 발생을 하게 되면 데이터가 삭제가 되지만 리스트의 데이터는 업데이트를 해주지 않았기 때문에 리스트의 내용이 변하지 않는다.
        // 그래서 리스트의 내용이 변하는 부분인 그리드뷰 클릭 이벤트를 현재 날짜(위치)에 맞게 호출을 하게 되면 리스트의 내용이 업데이트가 되면서 삭제가 되어지는 효과를 불러온다.
        imgBtnTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(parent.getContext(), ""+list.get(position).getNo(), Toast.LENGTH_LONG).show();
                Log.e("!!!", "adapter"+list.get(position).getNo());
                DBHelper myDBHelper = new DBHelper(parent.getContext());
                SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
                //클릭된 위치에 데이타 값을 가져와서 해당 프라이머리키로 지운다
                sqlDB.execSQL("DELETE FROM calenderTBL WHERE id = " + list.get(position).getNo() + ";");
                sqlDB.close();

                //selectedposition = 그리드 뷰의 위치
                Fragment1.gridViewClickEvent(Fragment1.sparent,Fragment1.selectedposition);

            }
        });

        /*if(listTextViewRdo.getText().toString().equals("수입")){
            listImgView.setImageResource(R.mipmap.income);
        }else if(listTextViewRdo.getText().toString().equals("지출")){
            listImgView.setImageResource(R.mipmap.expenditure);
        }*/

        if(list.get(position).getDetail().equals("수입")){ listImgView.setImageResource(R.mipmap.income);
        }else { listImgView.setImageResource(R.mipmap.expenditure); }

        listTextViewRdo.setText(list.get(position).getDetail());
        listTextViewCombo.setText(list.get(position).getCategory());
        listTextViewEdt.setText(list.get(position).getCredit());

        listTextViewCombo.setTag(position);
        //리스트를 클릭하면 클릭한 효과를 흰색으로 준다
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = new AlphaAnimation(0.3f,1.0f);
                animation.setDuration(500);
                v.startAnimation(animation);
            }
        });
        return convertView;
    }
}
