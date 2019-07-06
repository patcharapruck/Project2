package baby.com.project2.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.adapter.IntroListAdapter;
import baby.com.project2.adapter.IntroListItemsAdapter;
import baby.com.project2.dto.growup.DeleteGrowUpDto;
import baby.com.project2.dto.intro.SelectDataintroDto;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DataIntroManager;
import baby.com.project2.manager.singleton.DeleteGrowManager;
import baby.com.project2.view.IntroDataModelClass;
import baby.com.project2.view.IntroModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataIntroActivity extends AppCompatActivity {

    private Toolbar toolbar;

    ArrayList<IntroDataModelClass> items;
    IntroListItemsAdapter adapter;
    private RecyclerView recyclerView;

    SelectDataintroDto dto;
    private String I_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_intro);
        Intent id = getIntent();
        I_id = id.getStringExtra("id");
        initInstances();
    }

    private void initInstances() {

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_data_intro);
        reqListselect(I_id);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void reqListselect(String I_id) {

        final Context mcontext = DataIntroActivity.this;
        String reqBody = "{\"id_ageintro\":\""+I_id+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectDataintroDto> call = HttpManager.getInstance().getService().loadAPIDataintroDtoCall(requestBody);
        call.enqueue(new Callback<SelectDataintroDto>() {

            @Override
            public void onResponse(Call<SelectDataintroDto> call, Response<SelectDataintroDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    DataIntroManager.getInstance().setItemsDto(dto);
                    setRecyclerView();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectDataintroDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecyclerView() {

        Context context = DataIntroActivity.this;
        items = new ArrayList<>();
        adapter = new IntroListItemsAdapter(context, items);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        int size = dto.getDataintro().size();

        for (int i = 0; i < size; i++) {
            try {
                items.add(new IntroDataModelClass(dto.getDataintro().get(i).getI_id()
                        ,dto.getDataintro().get(i).getI_data()));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }
            adapter.notifyDataSetChanged();
        }
    }
}
