package baby.com.project2.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUser {

    private static SharedPrefUser mInstance;
    private static Context mCtx;

    private static final String SHARED_LOGIN = "myaccount";
    private static final String SHARED_TOKEN = "myaccount2";
    private static final String SHARED_SUM = "myaccount3";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_ID = "id";
    private static final String KEY_CHILD = "child";
    private static final String KEY_BRITH= "b";
    private static final String KEY_GENDER = "child1";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USER2 = "user2";
    private static final String KEY_PASS2= "pass2";
    private static final String KEY_BRITH_INT= "b1";

    SharedPrefUser(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefUser getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefUser(context);
        }
        return mInstance;
    }

    public boolean saveLogin(String user,String pass,Boolean remember,String id){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        SharedPreferences sharedPreferences2 = mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();

        editor.putString(KEY_USER,user);
        editor.putString(KEY_PASS,pass);
        editor.putBoolean(KEY_REMEMBER,remember);
        editor.apply();

        editor2.putString(KEY_ID,id);
        editor2.putString(KEY_USER2,user);
        editor2.putString(KEY_PASS2,pass);
        editor2.apply();

        return true;
    }

    public boolean saveChidId(String id,int gender,String b){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_CHILD,id);
        editor.putInt(KEY_GENDER,gender);
        editor.putString(KEY_BRITH,b);
        editor.apply();
        return true;
    }

    public boolean saveChidIdDate(int br){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_SUM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_BRITH_INT,br);
        editor.apply();
        return true;
    }

    public boolean logout(){

        SharedPreferences sharedPreferences= mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor c = sharedPreferences.edit();
        c.clear();
        c.apply();

        SharedPreferences sharedPreferences2= mCtx.getSharedPreferences(SHARED_SUM, Context.MODE_PRIVATE);
        SharedPreferences.Editor c2 = sharedPreferences2.edit();
        c2.clear();
        c2.apply();

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

    public String getUid(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID,"");
    }

    public int getGender(){
        SharedPreferences sharedPreferences2 = mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        return sharedPreferences2.getInt(KEY_GENDER,0);
    }

    public int getKeyBrithint(){
        SharedPreferences sharedPreferences2 = mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        return sharedPreferences2.getInt(KEY_BRITH_INT,0);
    }

    public String getKeyBrith(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_TOKEN, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_BRITH,"");
    }

}
