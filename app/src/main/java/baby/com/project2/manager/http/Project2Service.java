package baby.com.project2.manager.http;

import baby.com.project2.dto.LoginItemsDto;
import baby.com.project2.dto.child.InsertChildDto;
import baby.com.project2.dto.child.SelectChildDto;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Project2Service {

    @POST("login")
    Call<LoginItemsDto> loadAPILogin(@Body RequestBody login);

    @POST("manage_child/insert")
    Call<InsertChildDto> loadAPIInsertChild(@Body RequestBody insertchild);

    @POST("manage_child/select")
    Call<SelectChildDto> loadAPISelect(@Body RequestBody selectchild);
}
