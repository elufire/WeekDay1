package com.example.weekday1;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpHelper {
    Context context;
    public OkHttpHelper(Context context) {
        this.context = context;
    }

    public static void ascyncOkHttpApi(String url, final Context context){
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            String jsonResonse;
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonResonse = response.body().string();
                Intent intent = new Intent();
                intent.setAction("http_broadcast");
                intent.putExtra("json", jsonResonse);
                Log.d("TAG", "onCreate: SENDING ASYNC BROADCAST");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                //Log.d("TAG", "onResponse: " + jsonResonse);
            }
        });
    }

    public static void syncOkHttpApiCall(String url, final Context context){
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String jsonResponse = response.body().string();
                    Intent intent = new Intent();
                    intent.setAction("httpSync_broadcast");
                    intent.putExtra("json", jsonResponse);
                    Log.d("TAG", "onCreate: SENDING SYNC BROADCAST");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    //Log.d("TAG",  "run: " + jsonResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    //build okhttp client with interceptor
    public  static  OkHttpClient okhttpWithInterceptorClient(){
        HttpLoggingInterceptor httpLoggingInterceptor
                = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public  static void asynWithIntercept(String url){
        OkHttpClient okHttpClient = okhttpWithInterceptorClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d("TAG", "onResponse: " + response.body().string());
            }
        });
    }


}
