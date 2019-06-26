package baby.com.project2.manager.http;

import baby.com.project2.dto.LoginItemsDto;
import baby.com.project2.dto.child.InsertChildDto;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Project2Service {

    @POST("login")
    Call<LoginItemsDto> loadAPILogin(@Body RequestBody login);

    @POST("manage_child")
    Call<InsertChildDto> loadAPIInsertChild(@Body RequestBody insertchild);
}
