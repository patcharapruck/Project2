package baby.com.project2.manager.http;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpLoginManager {
    private static HttpLoginManager instance;

    public static HttpLoginManager getInstance(){
        if(instance == null)
            instance = new HttpLoginManager();
        return instance;
    }

    private Context mContext;
    private Project2Service service;
    private HttpLoginManager(){

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

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.33.1.98:1337/api/project2/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();



        service = retrofit.create(Project2Service.class);

    }

    public Project2Service getService() {
        return service;
    }
}
