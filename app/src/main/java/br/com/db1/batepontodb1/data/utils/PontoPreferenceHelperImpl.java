package br.com.db1.batepontodb1.data.utils;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.db1.batepontodb1.R;

public class PontoPreferenceHelperImpl implements PontoPreferenceHelper {
    private String user,password;
    private boolean rememberMe;
    private SharedPreferences preferences;
    private static PontoPreferenceHelperImpl INSTANCE;


    public static PontoPreferenceHelperImpl getPreferenceInstance(Context context){
        if (INSTANCE==null){
            INSTANCE = new PontoPreferenceHelperImpl(context);
        }
        return INSTANCE;
    }

    private PontoPreferenceHelperImpl(Context context) {
        preferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
    }

    @Override
    public void saveUser(String user) {
        preferences.edit().putString("user",user).apply();
    }

    @Override
    public void savePassword(String password) {
        preferences.edit().putString("password",password).apply();
    }

    @Override
    public void saveRememberMe(boolean rememberMe) {
        preferences.edit().putBoolean("rememberMe",rememberMe).apply();
    }

    @Override
    public String getUser() {
        return preferences.getString("user","");
    }

    @Override
    public String getPassword() {
        return preferences.getString("password","");
    }

    @Override
    public boolean getRememberMe() {
        return preferences.getBoolean("rememberMe",false);
    }
}
