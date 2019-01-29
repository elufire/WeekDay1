package com.example.weekday1;

import android.util.Log;

import com.example.weekday1.userdata.User;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserObserver implements Observer<User> {
    Callback callback;
    User user = new User();

    public UserObserver(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d("TAG", "onSubscribe: ");

    }

    @Override
    public void onNext(User user) {
        this.user = user;
        Log.d("TAG", "onNext: " + user.getItems().get(0).getLogin());
    }

    @Override
    public void onError(Throwable e) {
        Log.d("TAG", "onError: ");
    }

    @Override
    public void onComplete() {
        callback.onSuccess(user);
        Log.d("TAG", "onComplete: ");
    }
}
