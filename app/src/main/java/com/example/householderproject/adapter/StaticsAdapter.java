package com.example.householderproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.householderproject.R;
import com.example.householderproject.model.CalendarListData;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StaticsAdapter extends BaseAdapter {

    Context context;
    ArrayList<CalendarListData> list;

    public StaticsAdapter(Context context, ArrayList<CalendarListData> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView listViewImage;
        TextView textViewCategory;
        TextView textViewDate;
        TextView textViewLocation;
        TextView textViewCredit;


        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment2_list_view_holder, null);
        }
        listViewImage = convertView.findViewById(R.id.listViewImage);
        textViewCategory = convertView.findViewById(R.id.textViewCategory);
        textViewDate = convertView.findViewById(R.id.textViewDate);
        textViewLocation = convertView.findViewById(R.id.textViewLocation);
        textViewCredit = convertView.findViewById(R.id.textViewCredit);

        textViewCategory.setText(list.get(position).getCategory());
        textViewCredit.setText(list.get(position).getCredit());
        textViewLocation.setText(list.get(position).getLocation());
        textViewDate.setText(list.get(position).getDate());

        return convertView;
    }
}
