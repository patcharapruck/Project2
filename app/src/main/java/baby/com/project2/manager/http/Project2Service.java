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
import baby.com.project2.dto.user.SelectUserDto;
import baby.com.project2.dto.user.UpdateUserDto;
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

    @POST("testpro/login.php")
    Call<LoginItemsDto> loadAPILogin(@Body RequestBody login);

    @POST("testpro/register.php")
    Call<RegisterDto> loadAPIRegister(@Body RequestBody Register);

    @POST("testpro/insertChild.php")
    Call<InsertChildDto> loadAPIInsertChild(@Body RequestBody insertchild);

    @POST("testpro/selectChild.php")
    Call<SelectChildDto> loadAPISelect(@Body RequestBody selectchild);

    @POST("testpro/updateChild.php")
    Call<UpdateChildDto> loadAPIupdate(@Body RequestBody updatechild);

    @POST("testpro/deleteChild.php")
    Call<DeleteChildDto> loadAPIdelete(@Body RequestBody deletechild);

    @POST("testpro/select_agevaccine.php")
    Call<SelectAgeVaccineDto> loadAPIAgeVaccine();

    @POST("testpro/listVaccine.php")
    Call<SelectVaccineDto> loadAPIvaccine(@Body RequestBody vaccine);

    @POST("testpro/insetVaccine.php")
    Call<InsertVaccineDto> loadAPIVaccineinsert(@Body RequestBody insertvaccine);

    @POST("testpro/select_data_vaccine.php")
    Call<SelectDataVaccineDto> loadAPIVaccineData(@Body RequestBody datavaccine);

    @POST("testpro/insertGrow.php")
    Call<InsertGrowUpDto> loadAPIGrowup(@Body RequestBody requestBody);

    @POST("testpro/select_grow.php")
    Call<SelectGrowUpDto> loadAPIGrowUpSelect(@Body RequestBody requestBody);

    @POST("testpro/deleteGrow.php")
    Call<DeleteGrowUpDto> loadAPIDeleteGrow(@Body RequestBody requestBody);

    @POST("testpro/insertMilk.php")
    Call<InsertMilkDto> loadAPIInsertMilk(@Body RequestBody requestBody);

    @POST("testpro/selectMilk.php")
    Call<SelectMilkDto> loadAPISelectMilk(@Body RequestBody requestBody);

    @POST("testpro/updateMilk.php")
    Call<UpdateMilkDto> loadAPIupdateMilk(@Body RequestBody requestBody);

    @POST("testpro/deleteMilk.php")
    Call<DeleteMilkDto> loadAPIdeleteMilk(@Body RequestBody requestBody);

    @POST("testpro/selectListIntroage.php")
    Call<SelectAgeIntroDto> loadAPIAgeIntroDtoCall();

    @POST("testpro/selectintro.php")
    Call<SelectDataintroDto> loadAPIDataintroDtoCall(@Body RequestBody requestBody);

    @POST("testpro/select_agedev.php")
    Call<SelectAgeDevDto> loadAPIAgeDevDtoCall();

    @POST("testpro/selecttypedev.php")
    Call<SelectTypeDevDto> loadAPITypeDevDtoCall();

    @POST("testpro/listDevelorment.php")
    Call<SelectDevDto> loadAPIDevDtoCall(@Body RequestBody requestBody);

    @POST("testpro/InsertDevelorment.php")
    Call<InsertDevelorMentDto> loadAPInsertDevelorMentDtoCall(@Body RequestBody requestBody);

    @POST("testpro/select_data_develorment.php")
    Call<SelectDataDevDto> loadAPIDataDevDtoCall(@Body RequestBody requestBody);

    @POST("testpro/deletedevelorment.php")
    Call<DeleteDevelorMentDto> loadAPIDeleteDevelorMentDtoCall(@Body RequestBody requestBody);

    @POST("testpro/deletevaccine.php")
    Call<DeleteVaccineDto> loadAPIDeleteVaccineDtoCall(@Body RequestBody requestBody);

    @POST("testpro/selectDev.php")
    Call<SelectBDDto> loadAPIBdDtoCall();

    @POST("testpro/selectUser.php")
    Call<SelectUserDto> loadAPISelectUserDtoCall(@Body RequestBody requestBody);

    @POST("testpro/updateUser.php")
    Call<UpdateUserDto> loadAPIUpdateUserDtoCall(@Body RequestBody requestBody);

}
