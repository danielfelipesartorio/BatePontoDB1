package br.com.db1.batepontodb1.secondui;

import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import br.com.db1.batepontodb1.data.PontoDataManager;
import br.com.db1.batepontodb1.data.utils.UrlRegisterCallbackInterface;
import br.com.db1.batepontodb1.data.utils.UrlRequestCallbackInterface;

public class SecondActivityPresenter implements UrlRegisterCallbackInterface, UrlRequestCallbackInterface {
    private SecondActivityInterface secondActivityInterface;
    private PontoDataManager pontoDataManager;
    private String user,password;

    SecondActivityPresenter(SecondActivityInterface secondActivityInterface){
        this.secondActivityInterface = secondActivityInterface;
        pontoDataManager = new PontoDataManager();
    }

    double getTimeWorked(String[] s) {
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
        return timeWorked;
    }

    Intent openTaskWeb(){
        Intent intentToOpenTaskWeb = new Intent(Intent.ACTION_VIEW);
        intentToOpenTaskWeb.setData(Uri.parse("https://taskweb.db1.com.br/"));
        return intentToOpenTaskWeb;
    }

    void register(String user, String password) {
        this.user=user;
        this.password = password;
        pontoDataManager.UrlRegister(this.user,this.password,this);
    }

    @Override
    public void Successful(boolean success) {
        if (success){
            secondActivityInterface.successMsg();
            pontoDataManager.UrlRequest(user,password,this);
        }else{
            secondActivityInterface.error();
        }
    }

    @Override
    public void UrlRequestCallback(Boolean success, String[] s) {
        if (success){
            secondActivityInterface.updateMarkings(s);
        } else {
            secondActivityInterface.error();
        }
    }
    @Override
    public void loading(boolean loadingStatus) {
    }
}