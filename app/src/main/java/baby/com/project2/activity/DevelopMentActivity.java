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
import android.widget.Toast;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.adapter.DevelopMentListItemsAdapter;
import baby.com.project2.dto.devlopment.SelectAgeDevDto;
import baby.com.project2.dto.devlopment.SelectDevDto;
import baby.com.project2.dto.devlopment.SelectTypeDevDto;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.develorment.AgeDevManager;
import baby.com.project2.manager.singleton.develorment.DevelopmentManager;
import baby.com.project2.manager.singleton.develorment.TypeDevManager;
import baby.com.project2.view.DevelopMentModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevelopMentActivity extends AppCompatActivity {

    private Toolbar toolbar;

    SelectAgeDevDto dtoage;
    SelectTypeDevDto dtotype;
    SelectDevDto dto;

    private Spinner SpinsDev;
    private ArrayList<String> mTypeSearch = new ArrayList<String>();

    ArrayList<DevelopMentModelClass> items;
    DevelopMentListItemsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_develop_ment);
        initInstances();
    }

    private void initInstances(){
        SpinsDev = (Spinner)findViewById(R.id.spins_dev);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_dev);
        reqAgeDev();
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
        adapter = new DevelopMentListItemsAdapter(DevelopMentActivity.this, items);

        recyclerView.setLayoutManager(new LinearLayoutManager(DevelopMentActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        int size = dtotype.getTypeDev().size();

        for (int i = 0; i < size; i++) {
            try {
                items.add(new DevelopMentModelClass(dtotype.getTypeDev().get(i).getId_type(),dtotype.getTypeDev().get(i).getType_dev()));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void createTypeSearchData() {

        if (mTypeSearch.isEmpty()){
            for(int i=0;i<dtoage.getAge_dev().size();i++){
                mTypeSearch.add(dtoage.getAge_dev().get(i).getAge_dev());
            }
        }
        spinnerChoose();
    }

    public void reqAgeDev() {

        final Context mcontext = DevelopMentActivity.this;
        Call<SelectAgeDevDto> call = HttpManager.getInstance().getService().loadAPIAgeDevDtoCall();
        call.enqueue(new Callback<SelectAgeDevDto>() {

            @Override
            public void onResponse(Call<SelectAgeDevDto> call, Response<SelectAgeDevDto> response) {
                if(response.isSuccessful()){
                    dtoage = response.body();
                    AgeDevManager.getInstance().setItemsDto(dtoage);
                    createTypeSearchData();

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

    public void reqTypeDev() {

        final Context mcontext = DevelopMentActivity.this;
        Call<SelectTypeDevDto> call = HttpManager.getInstance().getService().loadAPITypeDevDtoCall();
        call.enqueue(new Callback<SelectTypeDevDto>() {

            @Override
            public void onResponse(Call<SelectTypeDevDto> call, Response<SelectTypeDevDto> response) {
                if(response.isSuccessful()){
                    dtotype = response.body();
                    TypeDevManager.getInstance().setItemsDto(dtotype);
                    setRecyclerView();

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

    private void spinnerChoose() {
        ArrayAdapter<String> spinsSearch = new ArrayAdapter<String>(DevelopMentActivity.this
                ,R.layout.support_simple_spinner_dropdown_item,mTypeSearch);

        SpinsDev.setAdapter(spinsSearch);
        SpinsDev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String vid = dtoage.getAge_dev().get(position).getId_agedev();
                reqDav(vid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void reqDav(String id) {

        final Context mcontext = DevelopMentActivity.this;
        String reqBody = "{\"id_agedev\":\""+id+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectDevDto> call = HttpManager.getInstance().getService().loadAPIDevDtoCall(requestBody);
        call.enqueue(new Callback<SelectDevDto>() {

            @Override
            public void onResponse(Call<SelectDevDto> call, Response<SelectDevDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    DevelopmentManager.getInstance().setItemsDto(dto);
                    reqTypeDev();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectDevDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
