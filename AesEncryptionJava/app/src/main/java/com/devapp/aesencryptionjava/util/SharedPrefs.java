package com.devapp.aesencryptionjava.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private String APP_NAME = "AES_ENCRYPTION";
    private SharedPreferences prefs;

    public SharedPrefs(Context context){
        prefs = context.getSharedPreferences(APP_NAME,Context.MODE_PRIVATE);
    }

    public void saveKey(String key){
        prefs.edit().putString("key",key).apply();
    }

    public String getKey(){
        return prefs.getString("key","");
    }

    public void saveIv(String iv){
        prefs.edit().putString("iv",iv).apply();
    }

    public String getIv(){
        return prefs.getString("iv","");
    }
}
