package baby.com.project2.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUserFace {

    private static SharedPrefUserFace mInstance;
    private static Context mCtx;

    private static final String SHARED_LOGIN_FACEBOOK = "myaccount";
    private static final String SHARED_LOGIN_FACEBOOK_UID = "myaccountuid";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_ID_FACE = "id_face";
    private static final String KEY_IMAGE = "image_proflie";
    private static final String KEY_LOGIN_FACE = "loginf";
    private static final String KEY_LOGIN_UID = "uid";


    SharedPrefUserFace(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefUserFace getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefUserFace(context);
        }
        return mInstance;
    }

    public boolean saveLoginface(String firstname,String lastname,String id,String imageproflie,Boolean loginf){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_FIRST_NAME,firstname);
        editor.putString(KEY_LAST_NAME,lastname);
        editor.putString(KEY_ID_FACE,id);
        editor.putString(KEY_IMAGE,imageproflie);
        editor.putBoolean(KEY_LOGIN_FACE,loginf);
        editor.apply();

        return true;
    }


    public boolean saveUidFace(String uid){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK_UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_LOGIN_UID,uid);
        editor.apply();

        return true;
    }


    public boolean logoutface(){

        SharedPreferences sharedPreferences= mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK, Context.MODE_PRIVATE);
        SharedPreferences.Editor c = sharedPreferences.edit();

        c.clear();
        c.apply();

        SharedPreferences sharedPreferences2= mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK_UID, Context.MODE_PRIVATE);
        SharedPreferences.Editor c2 = sharedPreferences.edit();

        c2.clear();
        c2.apply();

        return true;
    }

    public String getKeyFirstName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FIRST_NAME,"");
    }
    public String getKeyLastName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LAST_NAME,"");
    }
    public String getKeyIdFace(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID_FACE,"");
    }

    public Boolean getLoginFace(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_LOGIN_FACE,false);
    }

    public String getImageProflie(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_IMAGE,"");
    }

    public String getKeyUid(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGIN_FACEBOOK_UID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LOGIN_UID,"");
    }

}
