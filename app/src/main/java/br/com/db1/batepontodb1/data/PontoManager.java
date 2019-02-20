package br.com.db1.batepontodb1.data;

import android.util.Log;

import java.io.IOException;

import br.com.db1.batepontodb1.data.utils.CountString;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PontoManager {
    private String htmlResponse;

    private String OkHTTPUrlRequest(String user, String password, String tipo){
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                //.add("Latitude","-25,43816880")
                //.add("Lembrame","false")
                //.add("Longitude","-49,27908340")
                .add("OrigemRegistro","RE")
                .add("Password",password)
                .add("Situacao","I")
                //.add("TimeZone","2Iu3RaeKHyuSHJRZJ0OO9j+clMKWxsipGghFxBaUZ84=")
                .add("UserName",user)
                .add("tipo",tipo)

                .build();

        final Request request = new Request.Builder()
                .url("https://registra.pontofopag.com.br/")
                .post(formBody)
                .addHeader("origin", "https://registra.pontofopag.com.br")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("referer", "https://registra.pontofopag.com.br/")
                //.addHeader("accept-encoding", "gzip, deflate, br")
                .addHeader("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("cookie", "ASP.NET_SessionId=r4itmylfby1sreglhr3ebdr1")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "88b12c47-043f-de5b-8e46-a4abae5ca9ff")
                .build();

        try {
            return (client.newCall(request).execute().body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public Observable<String> ValidateLogin(final String user, final String password){

        return Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(OkHTTPUrlRequest (user,password,"1"));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String[]> UrlHistory(final String user, final String password){

        return Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(OkHTTPUrlRequest (user,password,"1"));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String[]>() {
                    @Override
                    public String[] apply(String s) throws Exception {
                        return getAllMarkings(s);
                    }
                });
    }

    public Observable<String> UrlRegister(final String user, final String password) {
        return Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext(OkHTTPUrlRequest (user,password,"0"));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.contains("Chave de Seguran");
                    }
                });
    }
    public String[] getAllMarkings(String s){
        int numOfOccurrences = CountString.CountStringChaveDeSeg(s);
        if (numOfOccurrences==0){
            return null;
        }
        int size = 12;
        if (numOfOccurrences<size){
            size = numOfOccurrences;
        }
        String[] result = new String[size];
        String day;
        String time;
        String empresa;
        int count=0;

        while (s.contains("<td>Data: </td>")&&count<12){
            int dayindex =  s.indexOf("<td>Data: </td>");
            day = s.substring(dayindex+41,dayindex+51);
            s=s.replaceFirst("<td>Data: </td>","");

            int timeindex= s.indexOf("&nbsp; Hora: ");
            time = s.substring(timeindex+13, timeindex+18);
            s=s.replaceFirst("&nbsp; Hora: ","");

            int empresaindex = s.indexOf("<td>Empresa: </td>");
            int empresaindexfim = s.indexOf("</tr>",empresaindex);
            empresa = s.substring(empresaindex+44, empresaindexfim-23);
            s=s.replaceFirst("<td>Empresa: </td>","");
            result[count] = day + "\n"+time+ "\n"+empresa;
            count++;
        }
        return result;
    }



}
