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

import java.text.DecimalFormat;
import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.activity.VaccineActivity;
import baby.com.project2.adapter.ReportGrowListItemsAdapter;
import baby.com.project2.adapter.ReportVaccineListItemsAdapter;
import baby.com.project2.dto.growup.SelectGrowUpDto;
import baby.com.project2.dto.vaccine.SelectAgeVaccineDto;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.AgeVaccineManager;
import baby.com.project2.manager.singleton.DataVaccineManager;
import baby.com.project2.manager.singleton.SelectGrowManager;
import baby.com.project2.manager.singleton.VaccineManager;
import baby.com.project2.view.ReportGrowModelClass;
import baby.com.project2.view.ReportVaccineModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportVaccineFragment extends Fragment {


    ArrayList<ReportVaccineModelClass> items;
    ReportVaccineListItemsAdapter adapter;
    private RecyclerView recyclerView;

    SelectDataVaccineDto  dto;
    SelectAgeVaccineDto dtoage;
    SelectVaccineDto dtov;


    ArrayList<String> age;

    public ReportVaccineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report_vaccine, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_report_vaccine);
        reqvaccine();

    }

    public void reqListGrow(String C_id) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"C_id\":\""+C_id+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectDataVaccineDto> call = HttpManager.getInstance().getService().loadAPIVaccineData(requestBody);
        call.enqueue(new Callback<SelectDataVaccineDto>() {

            @Override
            public void onResponse(Call<SelectDataVaccineDto> call, Response<SelectDataVaccineDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    DataVaccineManager.getInstance().setItemsDto(dto);
                    setRecyclerView();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectDataVaccineDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecyclerView() {

        Context context = getContext();
        items = new ArrayList<>();
        adapter = new ReportVaccineListItemsAdapter(context, items);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        DecimalFormat formatter = new DecimalFormat("00");
        int size = dto.getDatavaccine().size();
        String Age="";
        String Status="";
        String Name="";

        String V = "";

        for (int i = 0; i < size; i++) {

            for(int j=0;j<dtov.getVaccine().size();j++){
                if(dtov.getVaccine().get(i).getV_id().equals(formatter.format(dto.getDatavaccine().get(i).getV_id()))){
                    Name = dtov.getVaccine().get(i).getV_name();
                    V = dtov.getVaccine().get(i).getId_agevac();
                }
            }

            for(int j=0;j<dtoage.getAge_vaccine().size();j++){
                if(dtoage.getAge_vaccine().get(j).getId_agevac().equals(V)){
                    Age = dtoage.getAge_vaccine().get(j).getAge_vac();
                }
            }

            if(dto.getDatavaccine().get(i).getFKcv_status()==1){
                Status = "ฉีดแล้ว";
            }else {
                Status = "ยังไม่ฉีด";
            }

            try {
                items.add(new ReportVaccineModelClass(dto.getDatavaccine().get(i).getFKcv_id()
                        ,Name,Age,dto.getDatavaccine().get(i).getFKcv_date(),Status));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }

            adapter.notifyDataSetChanged();
        }
    }

    public void reqAgeVaccine() {

        final Context mcontext = Contextor.getInstance().getmContext();
        Call<SelectAgeVaccineDto> call = HttpManager.getInstance().getService().loadAPIAgeVaccine();
        call.enqueue(new Callback<SelectAgeVaccineDto>() {

            @Override
            public void onResponse(Call<SelectAgeVaccineDto> call, Response<SelectAgeVaccineDto> response) {
                if(response.isSuccessful()){
                    dtoage = response.body();
                    AgeVaccineManager.getInstance().setItemsDto(dtoage);
                    reqListGrow("01");

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectAgeVaccineDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void reqvaccine() {

        final Context mcontext = getContext();
        String reqBody = " {\"id_agevac\":\"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectVaccineDto> call = HttpManager.getInstance().getService().loadAPIvaccine(requestBody);
        call.enqueue(new Callback<SelectVaccineDto>() {

            @Override
            public void onResponse(Call<SelectVaccineDto> call, Response<SelectVaccineDto> response) {
                if(response.isSuccessful()){
                    dtov = response.body();
                    reqAgeVaccine();
                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectVaccineDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }


}
