package baby.com.project2.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.activity.DevelopMentActivity;
import baby.com.project2.adapter.DevelopMentListItemsAdapter;
import baby.com.project2.adapter.ReportDevListItemsAdapter;
import baby.com.project2.adapter.ReportGrowListItemsAdapter;
import baby.com.project2.dto.devlopment.SelectAgeDevDto;
import baby.com.project2.dto.devlopment.SelectBDDto;
import baby.com.project2.dto.devlopment.SelectDataDevDto;
import baby.com.project2.dto.devlopment.SelectDevDto;
import baby.com.project2.dto.devlopment.SelectTypeDevDto;
import baby.com.project2.dto.growup.SelectGrowUpDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.develorment.AgeDevManager;
import baby.com.project2.manager.singleton.develorment.DataDevManager;
import baby.com.project2.manager.singleton.develorment.DevelopmentManager;
import baby.com.project2.manager.singleton.develorment.TypeDevManager;
import baby.com.project2.manager.singleton.growup.SelectGrowManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.DevelopMentModelClass;
import baby.com.project2.view.ReportDevModelClass;
import baby.com.project2.view.ReportGrowModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportDevFragment extends Fragment {


    ArrayList<ReportDevModelClass> items;
    ReportDevListItemsAdapter adapter;
    private RecyclerView recyclerView;

    SelectAgeDevDto dtoage;
    SelectTypeDevDto dtotype;
    SelectBDDto dto;
    SelectDataDevDto dtodev;

    private String date,type,name,id,age;
    private int status;

    public ReportDevFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report_dev, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_report_dev);
        String cid = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild();
        reqDataDevelorment(cid);
    }

    public void reqDataDevelorment(String cid) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"C_id\":\""+cid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectDataDevDto> call = HttpManager.getInstance().getService().loadAPIDataDevDtoCall(requestBody);
        call.enqueue(new Callback<SelectDataDevDto>() {

            @Override
            public void onResponse(Call<SelectDataDevDto> call, Response<SelectDataDevDto> response) {
                if(response.isSuccessful()){
                    dtodev = response.body();
                    DataDevManager.getInstance().setItemsDto(dtodev);
                    reqAgeDev();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectDataDevDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void reqAgeDev() {

        final Context mcontext = Contextor.getInstance().getmContext();
        Call<SelectAgeDevDto> call = HttpManager.getInstance().getService().loadAPIAgeDevDtoCall();
        call.enqueue(new Callback<SelectAgeDevDto>() {

            @Override
            public void onResponse(Call<SelectAgeDevDto> call, Response<SelectAgeDevDto> response) {
                if(response.isSuccessful()){
                    dtoage = response.body();
                    AgeDevManager.getInstance().setItemsDto(dtoage);
                    reqTypeDev();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectAgeDevDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createTypeSearchData() {
        reqDav();
    }

    public void reqDav() {

        final Context mcontext = Contextor.getInstance().getmContext();
        Call<SelectBDDto> call = HttpManager.getInstance().getService().loadAPIBdDtoCall();
        call.enqueue(new Callback<SelectBDDto>() {

            @Override
            public void onResponse(Call<SelectBDDto> call, Response<SelectBDDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    setRecyclerView();
                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectBDDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void reqTypeDev() {

        final Context mcontext = Contextor.getInstance().getmContext();
        Call<SelectTypeDevDto> call = HttpManager.getInstance().getService().loadAPITypeDevDtoCall();
        call.enqueue(new Callback<SelectTypeDevDto>() {

            @Override
            public void onResponse(Call<SelectTypeDevDto> call, Response<SelectTypeDevDto> response) {
                if(response.isSuccessful()){
                    dtotype = response.body();
                    TypeDevManager.getInstance().setItemsDto(dtotype);
                    createTypeSearchData();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectTypeDevDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecyclerView() {

        items = new ArrayList<>();
        adapter = new ReportDevListItemsAdapter(Contextor.getInstance().getmContext(),items);

        recyclerView.setLayoutManager(new LinearLayoutManager(Contextor.getInstance().getmContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        int size = dtodev.getDatadev().size();

        for (int i = 0; i < size; i++) {

            for(int j=0;j<dto.getBD().size();j++){
                if(dtodev.getDatadev().get(i).getBD_id().equals(dto.getBD().get(j).getBD_id())){
                    name = dto.getBD().get(j).getBD_data();
                    type = dto.getBD().get(j).getId_type();
                    age = dto.getBD().get(j).getId_agedev();

                    for(int k=0;k<dtotype.getTypeDev().size();k++){
                        if(type.equals(dtotype.getTypeDev().get(k).getId_type())){
                            type = dtotype.getTypeDev().get(k).getType_dev();
                        }
                    }
                    for(int k=0;k<dtoage.getAge_dev().size();k++){
                        if(age.equals(dtoage.getAge_dev().get(k).getId_agedev())){
                            age = dtoage.getAge_dev().get(k).getAge_dev();
                        }
                    }
                }
            }

            try {
                items.add(new ReportDevModelClass(dtodev.getDatadev().get(i).getFKcd_id()
                        ,dtodev.getDatadev().get(i).getFKcd_date()
                        ,type,age,name,dtodev.getDatadev().get(i).getFKcd_status()));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }
            adapter.notifyDataSetChanged();
        }

    }


}
