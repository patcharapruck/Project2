package baby.com.project2.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefDayMonthYear {

    private static SharedPrefDayMonthYear mInstance;
    private static Context mCtx;

    private static final String SHARED_DATEFORMAT = "myaccount";
    private static final String KEY_DAY = "day";
    private static final String KEY_MONTH = "month";
    private static final String KEY_YEAR = "year";
    private static final String KEY_DAYMONTHYEAR = "dmy";



    SharedPrefDayMonthYear(Context context){
        mCtx = context;
    }

    public static synchronized SharedPrefDayMonthYear getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefDayMonthYear(context);
        }
        return mInstance;
    }

    public boolean saveage(int day,int month,int year,String date){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_DATEFORMAT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_DAY,day);
        editor.putInt(KEY_MONTH,month);
        editor.putInt(KEY_YEAR,year);
        editor.putString(KEY_DAYMONTHYEAR,date);
        editor.apply();

        return true;
    }



    public boolean clearDate(){

        SharedPreferences sharedPreferences= mCtx.getSharedPreferences(SHARED_DATEFORMAT, Context.MODE_PRIVATE);
        SharedPreferences.Editor c = sharedPreferences.edit();

        c.clear();
        c.apply();

        return true;
    }

    public int getKeyday(){
        SharedPreferences sharedPreferences2 = mCtx.getSharedPreferences(SHARED_DATEFORMAT, Context.MODE_PRIVATE);
        return sharedPreferences2.getInt(KEY_DAY,0);
    }
    public int getKeymonth(){
        SharedPreferences sharedPreferences2 = mCtx.getSharedPreferences(SHARED_DATEFORMAT, Context.MODE_PRIVATE);
        return sharedPreferences2.getInt(KEY_MONTH,0);
    }
    public int getKeyyear(){
        SharedPreferences sharedPreferences2 = mCtx.getSharedPreferences(SHARED_DATEFORMAT, Context.MODE_PRIVATE);
        return sharedPreferences2.getInt(KEY_YEAR,0);
    }


    public String getKeydateformat(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_DATEFORMAT, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DAYMONTHYEAR,"");
    }

}
