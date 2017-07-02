package com.example.deepak.myfirstfirebaseproject;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepak.myfirstfirebaseproject.Model.ShowDataResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DisplayValueInterface {
    ListView list;
    ProcessFeed processFeed;
    private SharedPreferences sharedPreferences;
    private String farmer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        sharedPreferences = getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
        updateToken(token);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            setLogoutDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLogoutDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.logout_custom_dialog);
        dialog.setTitle("Custom Alert Dialog");

        final TextView textView = (TextView) dialog.findViewById(R.id.textView);
        textView.setText("Do you want to Logout?");
        Button btnSave = (Button) dialog.findViewById(R.id.save);
        Button btnCancel = (Button) dialog.findViewById(R.id.cancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateToken("");
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void updateToken(String token) {
        sharedPreferences = getSharedPreferences(Constants.SharedPreferences.SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        farmer_id = sharedPreferences.getString(Constants.SharedPreferences.FARMER_ID, "");
        Map<String, String> params = new HashMap<>();
        params.put(Constants.SharedPreferences.FCM_TOKEN, token);
        params.put("farmer_id", farmer_id);
        processFeed.updateRegToken(params);
    }

    private void initViews() {
        processFeed = new ProcessFeed(this, this);
        list = (ListView) findViewById(R.id.list);
    }

    @Override
    public void showData(ShowDataResponse showDataResponse) {
        if (showDataResponse == null) {
            Map<String, String> params = new HashMap<>();
            params.put("farmer_id", farmer_id);
            processFeed.fetchValues(params);
        }else{
            if (showDataResponse.getSTATUS() == 700) {
                List<ShowDataResponse.ResultBean> resultBeanList = showDataResponse.getResult();
                String date[] = new String[resultBeanList.size()];
                int i = 0;
                for (ShowDataResponse.ResultBean resultBean : resultBeanList) {
                    date[i] = resultBean.getDate();
                    i++;
                }
                DataReviewAdapter adapter = new DataReviewAdapter(this, showDataResponse, date);
                list.setAdapter(adapter);
            } else if (showDataResponse.getSTATUS() == 404) {
                Toast.makeText(this, R.string.record_not_found, Toast.LENGTH_LONG).show();
            }
        }
    }
}
