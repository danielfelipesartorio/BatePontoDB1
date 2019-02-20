package br.com.db1.batepontodb1.secondui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import br.com.db1.batepontodb1.data.PontoManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

class SecondActivityPresenter {
    private SecondActivityInterface secondActivityInterface;
    private PontoManager pontoManager;
    private CompositeDisposable compositeDisposable;

    SecondActivityPresenter(SecondActivityInterface secondActivityInterface){
        this.secondActivityInterface = secondActivityInterface;
        pontoManager = new PontoManager();
        compositeDisposable=new CompositeDisposable();
    }

    int getTimeWorked(String[] s) {
        double timeWorked = 0;
        ArrayList<Double> todayMarkings = new ArrayList<>();
        Date now = Calendar.getInstance(TimeZone.getDefault()).getTime();
        String today = DateFormat.format("dd/MM/yyyy", now).toString();
        String timeNow = DateFormat.format("HH:mm", now).toString();
        double timeNowDouble = Double.parseDouble(timeNow.substring(0, 2)) + Double.parseDouble(timeNow.substring(3, 5)) / 60;

        int count = 0;
        while (s[count].contains(today)) {
            todayMarkings.add(
                    Double.parseDouble(s[count].substring(11, 13)) + Double.parseDouble(s[count].substring(14, 16)) / 60
            );
            count++;
        }
        switch (todayMarkings.size() % 2) {
            case 1: {
                int flip = -1;
                timeWorked = timeNowDouble;
                for (double item:todayMarkings){
                    timeWorked = timeWorked + item*flip;
                    flip = -flip;
                }
                secondActivityInterface.updateStatus(true,timeWorked);
                break;
            }
            case 0: {
                int flip = 1;
                for (double item:todayMarkings){
                    timeWorked = timeWorked + item*flip;
                    flip = -flip;
                }
                secondActivityInterface.updateStatus(false,timeWorked);
                break;
            }
        }
        return (int) Math.ceil(timeWorked/8.8*100);
    }

    Intent openTaskWeb(){
        Intent intentToOpenTaskWeb = new Intent(Intent.ACTION_VIEW);
        intentToOpenTaskWeb.setData(Uri.parse("https://taskweb.db1.com.br/"));
        return intentToOpenTaskWeb;
    }

    void register(String user, String password) {
        compositeDisposable.add(
                pontoManager.UrlRegister(user,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registerSuccess(), registerFailure())
        );

    }

    private Consumer<String> registerSuccess(){
        return new Consumer<String>() {
            @Override
            public void accept(String strings) throws Exception {
                secondActivityInterface.successMsg();
            }
        };
    }

    private Consumer<Throwable> registerFailure(){
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                secondActivityInterface.error();
            }
        };
    }


    void getNewMarkings(String user, String password) {
        compositeDisposable.add(
                pontoManager.UrlHistory(user,password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getNewMarkingsSuccess(), getNewMarkingsFailure())
        );

    }

    private Consumer<String[]> getNewMarkingsSuccess(){

        Consumer<String[]> consumer = new Consumer<String[]>() {
            @Override
            public void accept(String[] strings) throws Exception {
                secondActivityInterface.updateMarkings(strings);
            }
        };
        return consumer;
    }

    private Consumer<Throwable> getNewMarkingsFailure(){
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                secondActivityInterface.error();
            }
        };
    }

    void clear(){
        compositeDisposable.clear();
    }
}