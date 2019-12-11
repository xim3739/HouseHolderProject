package com.example.householderproject.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.householderproject.MainActivity;
import com.example.householderproject.R;
import com.example.householderproject.model.RecyclerViewData;

import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.example.householderproject.MainActivity.myContext;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

    private ArrayList<RecyclerViewData> list;
    private int layout;
    private String location;

    public RecyclerViewAdapter(ArrayList<RecyclerViewData> list, int layout) {

        this.list = list;
        this.layout = layout;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {

        holder.textViewDetail.setText(list.get(position).getDetail());
        holder.textViewCategory.setText(list.get(position).getCategory());
        holder.textViewCredit.setText(list.get(position).getCredit());
        holder.textViewLocation.setText(list.get(position).getLocation());

        location = list.get(position).getLocation();

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDetail, textViewCategory, textViewCredit, textViewLocation;

        public CustomViewHolder(@NonNull View itemView) {

            super(itemView);

            this.textViewDetail = itemView.findViewById(R.id.textViewDetail);
            this.textViewCategory = itemView.findViewById(R.id.textViewCategory);
            this.textViewCredit = itemView.findViewById(R.id.textViewCredit);
            this.textViewLocation = itemView.findViewById(R.id.textViewLocation);

        }
    }

}
