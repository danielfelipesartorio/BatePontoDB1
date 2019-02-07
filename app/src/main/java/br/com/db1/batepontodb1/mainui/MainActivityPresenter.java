package br.com.db1.batepontodb1.mainui;

import br.com.db1.batepontodb1.data.PontoDataManager;
import br.com.db1.batepontodb1.data.utils.UrlRequestCallbackInterface;

public class MainActivityPresenter implements UrlRequestCallbackInterface {
    private PontoDataManager pontoDataManager;
    private MainActivityInterface mainActivityInterface;

    MainActivityPresenter(MainActivityInterface mainActivityInterface){
        this.mainActivityInterface = mainActivityInterface;
        this.pontoDataManager = new PontoDataManager();
    }

    void validate(String user, String pass){
        pontoDataManager.UrlRequest(user,pass,this);
    }

    @Override
    public void UrlRequestCallback(Boolean success, String[]  s) {
        if (success){
            mainActivityInterface.loginSuccess(s);
        } else{
            mainActivityInterface.loginError();
        }
    }

    @Override
    public void loading(boolean loadingStatus) {
        mainActivityInterface.showLoading(loadingStatus);
    }
}
