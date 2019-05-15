package baby.com.project2.manager;

import android.content.Context;

public class Contextor {

    private static Contextor instance;

    public static Contextor getInstance(){
        if (instance == null)
            instance = new Contextor();
        return instance;
    }

    private Context mContext;

    private Contextor(){

    }

    public void init(Context context){ mContext = context; }

    public Context getmContext() {
        return mContext;
    }

}
