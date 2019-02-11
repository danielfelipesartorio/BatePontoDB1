package br.com.db1.batepontodb1.mainui;

import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

import br.com.db1.batepontodb1.data.PontoManager;
import br.com.db1.batepontodb1.data.utils.PontoPreferenceHelperImpl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

class MainActivityPresenter {
    private static final String TAG = MainActivityPresenter.class.getSimpleName();
    private PontoManager pontoManager;
    private MainActivityInterface mainActivityInterface;
    private CompositeDisposable compositeDisposable;

    MainActivityPresenter(MainActivityInterface mainActivityInterface){
        this.mainActivityInterface = mainActivityInterface;
        this.pontoManager = new PontoManager();
        this.compositeDisposable = new CompositeDisposable();
    }

    void validate(String user, String pass){
        mainActivityInterface.showLoading(true);
        compositeDisposable.add(
                pontoManager.UrlHistory(user,pass)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(validateSuccess(), validateFailure())
        );
    }

    private Consumer<String[]> validateSuccess(){
        return new Consumer<String[]>() {
            @Override
            public void accept(String[] strings) throws Exception {
                if (strings!=null){
                    mainActivityInterface.loginSuccess(strings);
                }else{
                    mainActivityInterface.loginError();
                }
            }
        };
    }

    private Consumer<Throwable> validateFailure(){

        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, Log.getStackTraceString(throwable), throwable);
                mainActivityInterface.loginError();
            }
        };
    }


public void clear (){
        compositeDisposable.clear();
}
}
