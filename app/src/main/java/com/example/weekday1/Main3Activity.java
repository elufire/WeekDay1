package com.example.weekday1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class Main3Activity extends AppCompatActivity {
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        final String greeting = "Fly Away";

        Observable<Integer> indexes = Observable.range(0, greeting.length());

        Observable<Character> characters = indexes
                .map(new Function<Integer, Character>() {
                    @Override
                    public Character apply(Integer integer) throws Exception {
                        System.out.println("");
                        return greeting.charAt(integer);
                    }
                });

                String greeting2 = "Down by the riva";

        Single<String> single = Single.just(greeting2);

        single.subscribe(new Consumer<String>() {
            @Override
            public void accept(String item){
                System.out.println(item);
            }
        });





        Maybe<String> empty = Maybe.empty();

        empty.subscribe(
                new Consumer<String>() {
                    @Override
                    public void accept(String v) throws Exception {
                        System.out.println("Dont Print");
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        System.out.println("Dont PRINT!!!!");
                    }
                },
                new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("MADE IT!!!");
                    }
                });

        Observable.range(1, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        Log.d("TAG", "onApply: " + integer.toString());
                        return integer + " is even number";
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("TAG", "onNext: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "All numbers emitted!");
                    }
                });

        getGamesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Game, Observable<Game>>() {

                    @Override
                    public Observable<Game> apply(Game game) throws Exception {

                        // getting each user address by making another network call
                        return getRatingsObservable(game);
                    }
                })
                .subscribe(new Observer<Game>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("TAG", "onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(Game game) {
                        Log.e("TAG", "onNext: " + game.getTitle() + ", " + game.getGenre() + ", " + game.getRating());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG", "All users emitted!");
                    }
                });


        Flowable<Long> eggTimer = Flowable.timer(5, TimeUnit.SECONDS);

        eggTimer.blockingSubscribe(new Consumer<Long>() {
            @Override
            public void accept(Long v) throws Exception {
                System.out.println("Eggs SUNNY SIDE UP!");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }





    private static Observable<Game> getRatingsObservable(final Game game) {

        final String[] ratings = new String[]{
                "90",
                "50",
                "60",
                "80"
        };

        return Observable
                .create(new ObservableOnSubscribe<Game>() {
                    @Override
                    public void subscribe(ObservableEmitter<Game> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            game.setRating(ratings[new Random().nextInt(3) + 0]);

                            emitter.onNext(game);
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    public Observable<Game> getGamesObservable() {
        String[] malegames = new String[]{"Halo", "COD", "Res Evil", "Battlefield"};

        final List<Game> games = new ArrayList<>();

        for (String name : malegames) {
            Game game = new Game();
            game.setTitle(name);
            game.setGenre("Shooter");

            games.add(game);
        }

        return Observable
                .create(new ObservableOnSubscribe<Game>() {
                    @Override
                    public void subscribe(ObservableEmitter<Game> emitter) throws Exception {
                        for (Game game : games) {
                            if (!emitter.isDisposed()) {
                                emitter.onNext(game);
                            }
                        }

                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }





  }

   class Game{
        public String title;
        public String genre;
        public String rating;

      public Game() {

      }

      public String getTitle() {
          return title;
      }

      public void setTitle(String title) {
          this.title = title;
      }

      public String getGenre() {
          return genre;
      }

      public void setGenre(String genre) {
          this.genre = genre;
      }

      public String getRating() {
          return rating;
      }

      public void setRating(String rating) {
          this.rating = rating;
      }
  }

