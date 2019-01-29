package com.example.weekday1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.example.weekday1.repository.RepositoryResults;
import com.example.weekday1.userdata.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvLogin;
    TextView tvName;
    TextView tvType;
    TextView tvLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLocation = findViewById(R.id.tvLocation);
        tvName = findViewById(R.id.tvName);
        tvLogin = findViewById(R.id.tvLogin);
        tvType = findViewById(R.id.tvType);

        //OkHttpHelper.asynWithIntercept(BASE_URL);
        //Retrofit Async
        Call<User> responseCall = RetrofitHelper.getUsers("user:elufire");

        responseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String urlUsed = call.request().url().toString();
                String login = response.body().getItems().get(0).getLogin();
                String name = response.body().getItems().get(0).getUrl();
                String location = response.body().getItems().get(0).getReposUrl();
                String type = response.body().getItems().get(0).getType();
                tvLocation.setText(location);
                tvName.setText(name);
                tvLogin.setText(login);
                tvType.setText(type);
                Log.d("TAG", "onRespinse: URL "+ urlUsed);
                Log.d("TAG", "onRespinse: EMAIL "+ login);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("TAG", "onFailure: URL Request Failed" + t.getStackTrace());
            }
        });



//        DataSourceRepo dataSourceRepo = new DataSourceRepo();
//        dataSourceRepo.getUserResponse(new Callback() {
//            @Override
//            public void onSuccess(User userResponse) {
//                Log.d("TAG_RX", "onSuccess: ");
//            }
//        });
    }


    public void onClick(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
