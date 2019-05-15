package baby.com.project2;

import android.app.Application;

import baby.com.project2.manager.Contextor;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Contextor.getInstance().getmContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
