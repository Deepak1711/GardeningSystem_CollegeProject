package com.example.deepak.myfirstfirebaseproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.deepak.myfirstfirebaseproject.Model.ApiResponse;
import com.example.deepak.myfirstfirebaseproject.Model.ShowDataResponse;
import com.example.deepak.myfirstfirebaseproject.Model.TokenUpdateResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created on 2/4/17.
 */


public class ProcessFeed {
    private Context context;
    private TokenUpdateResponse response;
    private ApiResponse apiResponse;
    private ApiCallback apiCallback;
    private android.content.SharedPreferences pref;
    private final static int REQUEST_CODE = -1;
    private SharedPreferences sharedPreferences;
    private DisplayValueInterface displayValueInterface;
    private ShowDataResponse showDataResponse;

    public ProcessFeed(Context context) {
        this.context = context;
    }

    public ProcessFeed(Context context, ApiCallback apiCallback) {
        this.context = context;
        this.apiCallback = apiCallback;
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
    }

    public ProcessFeed(Context context, DisplayValueInterface displayValueInterface) {
        this.context = context;
        this.displayValueInterface = displayValueInterface;
    }

    public void updateRegToken(final Map<String, String> params) {
        final APICallInterface api = AppController.getInstance().getRetrofitBuilder().create(APICallInterface.class);
        Call<TokenUpdateResponse> call = api.processRequest(params);
        call.enqueue(new Callback<TokenUpdateResponse>() {
            @Override
            public void onResponse(Call<TokenUpdateResponse> call, Response<TokenUpdateResponse> response) {
                ProcessFeed.this.response = response.body();
                if (response.body().isResult()) {
                    Toast.makeText(context, R.string.token_update_successful, Toast.LENGTH_LONG).show();
                    if (params.get(Constants.SharedPreferences.FCM_TOKEN).equals("")) {
                        pref = context.getSharedPreferences(Constants.SharedPreferences.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean(Constants.SharedPreferences.LOGIN_FLAG, false);
                        editor.putString(Constants.SharedPreferences.FARMER_ID, "");
                        editor.commit();
                        startAnotherActivity(LoginActivity.class);
                        ((MainActivity) context).finish();
                    }
                    displayValueInterface.showData(null);
                } else {
                    Toast.makeText(context, R.string.token_update_unsuccessful, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TokenUpdateResponse> call, Throwable t) {
                Toast.makeText(context, R.string.api_call_fail, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void registerUser(Map<String, String> params) {
        final APICallInterface api = AppController.getInstance().getRetrofitBuilder().create(APICallInterface.class);
        Call<ApiResponse> call = api.registerUser(params);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ProcessFeed.this.apiResponse = response.body();
                processResult(REQUEST_CODE);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context, R.string.api_call_fail, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loginUser(Map<String, String> params) {
        final APICallInterface api = AppController.getInstance().getRetrofitBuilder().create(APICallInterface.class);
        Call<ApiResponse> call = api.loginUser(params);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ProcessFeed.this.apiResponse = response.body();
                int farmerId = -1;
                if (apiResponse.getCode() == 700) {
                    farmerId = Integer.parseInt(apiResponse.getResult());
                }
                processResult(farmerId);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context, R.string.api_call_fail, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setSharedPreferences(int farmerId) {
        pref = context.getSharedPreferences(Constants.SharedPreferences.SHARED_PREFERENCES_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constants.SharedPreferences.FARMER_ID, String.valueOf(farmerId));
        editor.putBoolean(Constants.SharedPreferences.LOGIN_FLAG, true);
        editor.commit();
    }

    private void processResult(int farmerId) {
        if (apiResponse.getCode() == 700) {
            if (farmerId != -1) {
                setSharedPreferences(farmerId);
            }
            apiCallback.onSuccess();
        } else if (apiResponse.getCode() == 701) {
            Toast.makeText(context, R.string.unregistered_admin, Toast.LENGTH_LONG).show();
        } else if (apiResponse.getCode() == 702) {
            Toast.makeText(context, R.string.sql_fail, Toast.LENGTH_LONG).show();
        } else if (apiResponse.getCode() == 703) {
            Toast.makeText(context, R.string.mobile_already_registered, Toast.LENGTH_LONG).show();
        } else if (apiResponse.getCode() == 706) {
            Toast.makeText(context, R.string.user_not_exist, Toast.LENGTH_LONG).show();
        } else if (apiResponse.getCode() == 707) {
            Toast.makeText(context, R.string.pswrd_unmatch, Toast.LENGTH_LONG).show();
        }
    }

    public void fetchValues(Map<String, String> params) {
        final APICallInterface api = AppController.getInstance().getRetrofitBuilder().create(APICallInterface.class);
        Call<ShowDataResponse> call = api.fetchParameterValues(params);
        call.enqueue(new Callback<ShowDataResponse>() {
            @Override
            public void onResponse(Call<ShowDataResponse> call, Response<ShowDataResponse> response) {
                ProcessFeed.this.showDataResponse = response.body();
                displayValueInterface.showData(showDataResponse);
            }

            @Override
            public void onFailure(Call<ShowDataResponse> call, Throwable t) {
                Toast.makeText(context, R.string.api_call_fail, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startAnotherActivity(Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }
}
