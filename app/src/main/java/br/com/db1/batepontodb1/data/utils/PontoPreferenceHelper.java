package br.com.db1.batepontodb1.data.utils;

import android.content.SharedPreferences;

public interface PontoPreferenceHelper {
    void saveUser(String user);
    void savePassword(String password);
    void saveRememberMe (boolean rememberMe);
    String getUser();
    String getPassword();
    boolean getRememberMe();
}
