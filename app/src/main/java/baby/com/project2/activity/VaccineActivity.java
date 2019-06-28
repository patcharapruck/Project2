package baby.com.project2.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.adapter.VaccineListItemsAdapter;
import baby.com.project2.dto.child.InsertChildDto;
import baby.com.project2.dto.child.SelectChildDto;
import baby.com.project2.dto.vaccine.SelectAgeVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.AgeVaccineManager;
import baby.com.project2.manager.singleton.InsertChildManager;
import baby.com.project2.manager.singleton.SelectChildManager;
import baby.com.project2.manager.singleton.VaccineManager;
import baby.com.project2.view.VaccineModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VaccineActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Spinner SpinsVaccine;
    private ArrayList<String> mTypeSearch = new ArrayList<String>();

    SelectAgeVaccineDto dtoage;
    SelectVaccineDto dto;

    ArrayList<VaccineModelClass> items;
    VaccineListItemsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);
        initInstances();
    }

    private void initInstances(){
        SpinsVaccine = (Spinner)findViewById(R.id.spins_vaccine);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_vaccine);
        reqAgeVaccine();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRecyclerView() {
        items = new ArrayList<>();
        adapter = new VaccineListItemsAdapter(VaccineActivity.this, items);

        recyclerView.setLayoutManager(new LinearLayoutManager(VaccineActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        int size = dto.getVaccine().size();

        for (int i = 0; i < size; i++) {
            try {
                items.add(new VaccineModelClass(dto.getVaccine().get(i).getV_name(),dto.getVaccine().get(i).getV_type()));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }

            adapter.notifyDataSetChanged();
        }
    }

    private void createTypeSearchData() {

        if (mTypeSearch.isEmpty()){
            for(int i=0;i<dtoage.getAge_vaccine().size();i++){
                mTypeSearch.add(dtoage.getAge_vaccine().get(i).getAge_vac());
            }
        }
        spinnerChoose();
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

                    createTypeSearchData();

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

    private void spinnerChoose() {
        ArrayAdapter<String> spinsSearch = new ArrayAdapter<String>(VaccineActivity.this
                ,R.layout.support_simple_spinner_dropdown_item,mTypeSearch);

        SpinsVaccine.setAdapter(spinsSearch);
        SpinsVaccine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String vid = dtoage.getAge_vaccine().get(position).getId_agevac();
                    reqvaccine(vid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void reqvaccine(String vid) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"id_agevac\":\""+vid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectVaccineDto> call = HttpManager.getInstance().getService().loadAPIvaccine(requestBody);
        call.enqueue(new Callback<SelectVaccineDto>() {

            @Override
            public void onResponse(Call<SelectVaccineDto> call, Response<SelectVaccineDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    VaccineManager.getInstance().setItemsDto(dto);
                    setRecyclerView();

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
