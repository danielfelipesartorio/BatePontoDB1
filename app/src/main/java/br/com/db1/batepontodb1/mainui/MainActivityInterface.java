package br.com.db1.batepontodb1.mainui;

public interface MainActivityInterface {
    void loginError();
    void loginSuccess(String[]  s);
    void showLoading(boolean loading);
}
