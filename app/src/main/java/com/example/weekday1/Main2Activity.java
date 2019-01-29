package com.example.weekday1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.weekday1.repository.Item;
import com.example.weekday1.repository.RepositoryResults;
import com.example.weekday1.userdata.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter viewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.rvRepos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Call<RepositoryResults> responseCall2 = RetrofitHelper.getRopositories("user:elufire");

        responseCall2.enqueue(new Callback<RepositoryResults>() {
            @Override
            public void onResponse(Call<RepositoryResults> call, Response<RepositoryResults> response) {
                String urlUsed = call.request().url().toString();
                ArrayList<Item> itemArrayList = new ArrayList<>(response.body().getItems());
                viewAdapter = new RecyclerViewAdapter(itemArrayList);
                recyclerView.setAdapter(viewAdapter);
                Log.d("TAG", "onRespinse: URL "+ urlUsed);
                Log.d("TAG", "onRespinse: EMAIL ");
            }
            @Override
            public void onFailure(Call<RepositoryResults> call, Throwable t) {
                Log.d("TAG", "onFailure: URL Request Failed" + t.getStackTrace());
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }
}
