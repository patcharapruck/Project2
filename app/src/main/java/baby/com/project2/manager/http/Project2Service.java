package baby.com.project2.manager.http;

import baby.com.project2.dto.LoginItemsDto;
import baby.com.project2.dto.RegisterDto;
import baby.com.project2.dto.child.DeleteChildDto;
import baby.com.project2.dto.child.InsertChildDto;
import baby.com.project2.dto.child.SelectChildDto;
import baby.com.project2.dto.child.UpdateChildDto;
import baby.com.project2.dto.growup.DeleteGrowUpDto;
import baby.com.project2.dto.growup.InsertGrowUpDto;
import baby.com.project2.dto.growup.SelectGrowUpDto;
import baby.com.project2.dto.vaccine.InsertVaccineDto;
import baby.com.project2.dto.vaccine.SelectAgeVaccineDto;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Project2Service {

    @POST("login")
    Call<LoginItemsDto> loadAPILogin(@Body RequestBody login);

    @POST("register")
    Call<RegisterDto> loadAPIRegister(@Body RequestBody Register);

    @POST("manage_child/insert")
    Call<InsertChildDto> loadAPIInsertChild(@Body RequestBody insertchild);

    @POST("manage_child/select")
    Call<SelectChildDto> loadAPISelect(@Body RequestBody selectchild);

    @POST("manage_child/update")
    Call<UpdateChildDto> loadAPIupdate(@Body RequestBody updatechild);

    @POST("manage_child/delete")
    Call<DeleteChildDto> loadAPIdelete(@Body RequestBody deletechild);

    @POST("listvaccine/selete/age")
    Call<SelectAgeVaccineDto> loadAPIAgeVaccine();

    @POST("listvaccine/selete")
    Call<SelectVaccineDto> loadAPIvaccine(@Body RequestBody vaccine);

    @POST("manage_vaccine")
    Call<InsertVaccineDto> loadAPIVaccineinsert(@Body RequestBody insertvaccine);

    @POST("manage_vaccine/selete")
    Call<SelectDataVaccineDto> loadAPIVaccineData(@Body RequestBody datavaccine);

    @POST("manage_growup")
    Call<InsertGrowUpDto> loadAPIGrowup(@Body RequestBody requestBody);

    @POST("manage_growup/select")
    Call<SelectGrowUpDto> loadAPIGrowUpSelect(@Body RequestBody requestBody);

    @POST("manage_growup/delete")
    Call<DeleteGrowUpDto> loadAPIDeleteGrow(@Body RequestBody requestBody);
}
