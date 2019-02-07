package br.com.db1.batepontodb1.data.utils;


public interface UrlRequestCallbackInterface {
    void UrlRequestCallback(Boolean success, String[]  s);
    void loading(boolean loadingStatus);
}
