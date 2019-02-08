package br.com.db1.batepontodb1.secondui;

import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import br.com.db1.batepontodb1.data.PontoDataManager;
import br.com.db1.batepontodb1.data.utils.CountString;
import br.com.db1.batepontodb1.data.utils.UrlRegisterCallbackInterface;
import br.com.db1.batepontodb1.data.utils.UrlHistoryCallbackInterface;

public class SecondActivityPresenter implements UrlRegisterCallbackInterface, UrlHistoryCallbackInterface {
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

    public String[] getAllMarkingsFromHTMLResponse(String htmlResponse){
        return getAllMarkings(htmlResponse);
    }

    @Override
    public void Successful(boolean success) {
        if (success){
            secondActivityInterface.successMsg();
            pontoDataManager.UrlHistory(user,password,this);
        }else{
            secondActivityInterface.error();
        }
    }

    @Override
    public void UrlRequestCallback(Boolean success, String s) {
        if (success){
            String[]  allMarkings;
            allMarkings = getAllMarkingsFromHTMLResponse(s);
            secondActivityInterface.updateMarkings(allMarkings);
        } else {
            secondActivityInterface.error();
        }
    }
    @Override
    public void loading(boolean loadingStatus) {
    }
    public String[] getAllMarkings(String s){
        //String today = DateFormat.format("dd/MM/yyyy",new Date()).toString();
        //String today = "01/02/2019";
        int numOfOccurrences = CountString.CountStringChaveDeSeg(s);
        if (numOfOccurrences==0){
            return null;
        }
        String[] result = new String[numOfOccurrences];
        String day;
        String time;
        int count=0;

        while (s.contains("<td>Data: </td>")){
            int dayindex =  s.indexOf("<td>Data: </td>");
            day = s.substring(dayindex+41,dayindex+51);
            s=s.replaceFirst("<td>Data: </td>","");

            int timeindex= s.indexOf("&nbsp; Hora: ");
            time = s.substring(timeindex+13, timeindex+18);
            s=s.replaceFirst("&nbsp; Hora: ","");

            result[count] = day + "\n"+time;
            count++;
        }
        return result;
    }
}