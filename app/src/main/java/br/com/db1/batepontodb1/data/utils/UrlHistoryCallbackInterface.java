package br.com.db1.batepontodb1.data.utils;


public interface UrlHistoryCallbackInterface {
    void UrlRequestCallback(Boolean success, String s);
    void loading(boolean loadingStatus);
}
