package baby.com.project2.sentImage;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.http.Project2Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiClient instance;

    public static ApiClient getInstance(){
        if(instance == null)
            instance = new ApiClient();
        return instance;
    }

    private Context mContext;
    private ApiInterface service;
    private ApiClient(){

        mContext = Contextor.getInstance().getmContext();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class,new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://admitbaby.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ApiInterface.class);

    }

    public ApiInterface getService() {
        return service;
    }

}
