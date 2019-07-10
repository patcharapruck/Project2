package baby.com.project2.manager.http;

import baby.com.project2.dto.LoginItemsDto;
import baby.com.project2.dto.RegisterDto;
import baby.com.project2.dto.child.DeleteChildDto;
import baby.com.project2.dto.child.InsertChildDto;
import baby.com.project2.dto.child.SelectChildDto;
import baby.com.project2.dto.child.UpdateChildDto;
import baby.com.project2.dto.devlopment.DeleteDevelorMentDto;
import baby.com.project2.dto.devlopment.InsertDevelorMentDto;
import baby.com.project2.dto.devlopment.SelectAgeDevDto;
import baby.com.project2.dto.devlopment.SelectBDDto;
import baby.com.project2.dto.devlopment.SelectDataDevDto;
import baby.com.project2.dto.devlopment.SelectDevDto;
import baby.com.project2.dto.devlopment.SelectTypeDevDto;
import baby.com.project2.dto.growup.DeleteGrowUpDto;
import baby.com.project2.dto.growup.InsertGrowUpDto;
import baby.com.project2.dto.growup.SelectGrowUpDto;
import baby.com.project2.dto.intro.SelectAgeIntroDto;
import baby.com.project2.dto.intro.SelectDataintroDto;
import baby.com.project2.dto.milk.DeleteMilkDto;
import baby.com.project2.dto.milk.InsertMilkDto;
import baby.com.project2.dto.milk.SelectMilkDto;
import baby.com.project2.dto.milk.UpdateMilkDto;
import baby.com.project2.dto.vaccine.DeleteVaccineDto;
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

    @POST("manage_milk/insert")
    Call<InsertMilkDto> loadAPIInsertMilk(@Body RequestBody requestBody);

    @POST("manage_milk/select")
    Call<SelectMilkDto> loadAPISelectMilk(@Body RequestBody requestBody);

    @POST("manage_milk/update")
    Call<UpdateMilkDto> loadAPIupdateMilk(@Body RequestBody requestBody);

    @POST("manage_milk/delete")
    Call<DeleteMilkDto> loadAPIdeleteMilk(@Body RequestBody requestBody);

    @POST("listageintro")
    Call<SelectAgeIntroDto> loadAPIAgeIntroDtoCall();

    @POST("intro/select")
    Call<SelectDataintroDto> loadAPIDataintroDtoCall(@Body RequestBody requestBody);

    @POST("listagedev")
    Call<SelectAgeDevDto> loadAPIAgeDevDtoCall();

    @POST("listtypedev")
    Call<SelectTypeDevDto> loadAPITypeDevDtoCall();

    @POST("develorment/select")
    Call<SelectDevDto> loadAPIDevDtoCall(@Body RequestBody requestBody);

    @POST("manage_develorment")
    Call<InsertDevelorMentDto> loadAPInsertDevelorMentDtoCall(@Body RequestBody requestBody);

    @POST("manage_develorment/selete")
    Call<SelectDataDevDto> loadAPIDataDevDtoCall(@Body RequestBody requestBody);

    @POST("manage_develorment/delete")
    Call<DeleteDevelorMentDto> loadAPIDeleteDevelorMentDtoCall(@Body RequestBody requestBody);

    @POST("manage_vaccine/delete")
    Call<DeleteVaccineDto> loadAPIDeleteVaccineDtoCall(@Body RequestBody requestBody);

    @POST("manage_develorment/selete/BD")
    Call<SelectBDDto> loadAPIBdDtoCall();

}
