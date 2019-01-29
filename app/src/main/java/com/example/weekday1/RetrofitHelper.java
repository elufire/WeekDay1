package com.example.weekday1;



import com.example.weekday1.repository.RepositoryResults;
import com.example.weekday1.userdata.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.weekday1.Constants.BASE_URL;
import static com.example.weekday1.Constants.PATH_REPOSITORY;
import static com.example.weekday1.Constants.PATH_USER;
import static com.example.weekday1.Constants.USER_QUERY;


public class RetrofitHelper {
    public static Retrofit createRetrofitIntance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpHelper.okhttpWithInterceptorClient())
                .build();
        return retrofit;
    }
    public static Retrofit createRetrofitForRx(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHelper.okhttpWithInterceptorClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public static Call<User> getUsers(String userName){
        Retrofit retrofit = createRetrofitIntance();
        RemoteService remoteService = retrofit.create(RemoteService.class);
        //Log.d("TAG", "getUsers: " + remoteService.getUserList());
        return remoteService.getUserList(userName);
    }

    public static Call<RepositoryResults> getRopositories(String userName){
        Retrofit retrofit = createRetrofitIntance();
        RemoteService remoteService = retrofit.create(RemoteService.class);
        //Log.d("TAG", "getUsers: " + remoteService.getUserList());
        return remoteService.getRepositoryList(userName);
    }

//    public static Observable<User> getUserOb(){
//        Retrofit retrofit = createRetrofitForRx();
//        RemoteService remoteService = retrofit.create(RemoteService.class);
//        return  remoteService.getUserObservable("10");
//    }

    public interface RemoteService{
        @GET(PATH_USER)
        Call<User> getUserList(@Query(USER_QUERY) String resultCount);

        @GET(PATH_REPOSITORY)
        Call<RepositoryResults> getRepositoryList(@Query(USER_QUERY) String resultCount);

//        @GET(PATH)
//        Observable<User> getUserObservable(@Query(USER_QUERY) String resultCount);
    }

}
