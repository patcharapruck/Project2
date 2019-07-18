package baby.com.project2.sentImage;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
//
//    @POST("testpro/upload.php")
//    Call<Img_Pojo> uploadImage(@Field("image") String image,@Field("image_name") String title,@Field("cid") String cid);


    @POST("testpro/upload.php")
    Call<Img_Pojo> uploadImage(@Body RequestBody requestBody);

    @POST("testpro/uploadImgMilk.php")
    Call<Img_Pojo_milk> uploadImageMilk(@Body RequestBody requestBody);
}
