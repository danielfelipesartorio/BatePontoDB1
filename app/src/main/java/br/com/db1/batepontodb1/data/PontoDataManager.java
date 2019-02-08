package br.com.db1.batepontodb1.data;

import android.util.Log;

import br.com.db1.batepontodb1.data.utils.UrlRequestCallbackInterface;
import br.com.db1.batepontodb1.data.utils.UrlRegisterCallbackInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PontoDataManager {
    private UrlRequestCallbackInterface callback;

    public void UrlRequest(final String user, final String password, UrlRequestCallbackInterface callbackInterface){
        callback = callbackInterface;

        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("Password",password)
                        .add("UserName",user)
                        .add("tipo","1")
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
                        .addHeader("accept-language", "pt-BR,pt;q=0.9,en-US;q=0.8,en;q=0.7")
                        .addHeader("cookie", "ASP.NET_SessionId=if3vgcdyplz2tmyped4gdbxf")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("postman-token", "597a6ee9-85fe-6129-d49a-0dcc147605ed")
                        .build();
                emitter.onNext(client.newCall(request).execute().body().string());
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    String[]  allMarkings =null;
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.loading(true);
                    }

                    @Override
                    public void onNext(String s) {
                        allMarkings = getAllMarkings(s);
                        Log.v("teste","onnext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.UrlRequestCallback(false,null);
                        Log.v("teste","onerror");
                    }

                    @Override
                    public void onComplete() {
                        callback.loading(false);

                        Log.v("teste","onComplete");
                        if (allMarkings==null){
                            callback.UrlRequestCallback(false,null);
                        }else {
                            callback.UrlRequestCallback(true, allMarkings);
                        }
                    }
                });
    }

    private String[] getAllMarkings(String s){
        //String today = DateFormat.format("dd/MM/yyyy",new Date()).toString();
        //String today = "01/02/2019";
        int numOfOccurrences = countString(s);
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

    private static int countString(final String string)
    {
        int count = 0;
        int idx = 0;
        while ((idx = string.indexOf("Chave de Seguran", idx)) != -1)
        {
            idx++;
            count++;
        }
        return count;
    }

    public void UrlRegister(final String user, final String password, final UrlRegisterCallbackInterface callbackInterface) {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
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
                        .add("tipo","0")

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
                emitter.onNext(client.newCall(request).execute().body().string());
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    String[]  allMarkings =null;
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        allMarkings = getAllMarkings(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("erro","OnError");
                        callbackInterface.Successful(false);
                    }

                    @Override
                    public void onComplete() {
                        if (allMarkings==null){
                            Log.e("erro","OnComplete failure");
                            callbackInterface.Successful(false);
                        }else {
                            callbackInterface.Successful(true);
                        }
                    }
                });
    }
}
