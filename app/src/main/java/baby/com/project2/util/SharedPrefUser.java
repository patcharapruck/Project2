package baby.com.project2.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUser {

    private static SharedPrefUser mInstance;
    private static Context mCtx;

    private static final String SHARED_LOGIN = "myaccount";
    private static final String SHARED_TOKEN = "myaccount";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS= "pass";
    private static final String KEY_CHILD = "child";
    private static final String KEY_REMEMBER = "remember";

    SharedPrefUser(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefUser getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefUser(context);
        }
        return mInstance;
    }

    public boolean saveLogin(String user,String pass,Boolean remember){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER,user);
        editor.putString(KEY_PASS,pass);
        editor.putBoolean(KEY_REMEMBER,remember);
        editor.apply();

        return true;
    }

    public boolean saveChidId(String id){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_CHILD,id);
        editor.apply();
        return true;
    }

    public boolean logout(){
        SharedPreferences sharedPreferencestoken = mCtx.getSharedPreferences(SHARED_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor usertoken = sharedPreferencestoken.edit();
        usertoken.clear();
        usertoken.apply();

        SharedPreferences sharedPreferences= mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor c = sharedPreferences.edit();
        c.clear();
        c.apply();

        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER,"");
    }
    public String getPassword(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PASS,"");
    }
    public String getKeyChild(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CHILD,"");
    }

    public Boolean getRemember(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_REMEMBER,false);
    }

}
