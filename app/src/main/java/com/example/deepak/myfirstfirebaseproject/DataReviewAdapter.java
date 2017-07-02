package com.example.deepak.myfirstfirebaseproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.deepak.myfirstfirebaseproject.Model.ShowDataResponse;

/**
 * Created on 12/5/17.
 */


class DataReviewAdapter extends ArrayAdapter {
    private Context context;
    private ShowDataResponse showDataResponse;

    public DataReviewAdapter(Context context, ShowDataResponse showDataResponse, String date[]) {
        super(context, R.layout.adapter_review_values, R.id.tv_date, date);
        this.context = context;
        this.showDataResponse = showDataResponse;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.adapter_review_values, parent, false);
        }
        TextView tvDate = (TextView) row.findViewById(R.id.tv_date);
        TextView tvMoistureLevel = (TextView) row.findViewById(R.id.tv_moisture_level);
        TextView tvLightIntensity = (TextView) row.findViewById(R.id.tv_light_intensity);
        tvDate.setText(showDataResponse.getResult().get(position).getDate());
        tvMoistureLevel.setText(showDataResponse.getResult().get(position).getMoisture_level());
//        if (attendanceData.getResult().get(position).getLogout_time().equals(null)) {
//            tvLogoutTime.setText("Logged in");
//        } else {
        tvLightIntensity.setText(showDataResponse.getResult().get(position).getLight_intensity());
//        }
        return row;
    }

}
