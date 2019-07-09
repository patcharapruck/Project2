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
import baby.com.project2.adapter.ReportGrowListItemsAdapter;
import baby.com.project2.dto.growup.SelectGrowUpDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.growup.SelectGrowManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.ReportGrowModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportGrowFragment extends Fragment {


    ArrayList<ReportGrowModelClass> items;
    ReportGrowListItemsAdapter adapter;
    private RecyclerView recyclerView;

    SelectGrowUpDto dto;

    public ReportGrowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report_grow, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_report_grow);
        reqListGrow(SharedPrefUser.getInstance(getContext()).getKeyChild());

    }

    public void reqListGrow(String C_id) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"C_id\":\""+C_id+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectGrowUpDto> call = HttpManager.getInstance().getService().loadAPIGrowUpSelect(requestBody);
        call.enqueue(new Callback<SelectGrowUpDto>() {

            @Override
            public void onResponse(Call<SelectGrowUpDto> call, Response<SelectGrowUpDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    SelectGrowManager.getInstance().setItemsDto(dto);
                    setRecyclerView();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectGrowUpDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecyclerView() {

        Context context = getContext();
        items = new ArrayList<>();
        adapter = new ReportGrowListItemsAdapter(context, items);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        int size = dto.getGrowup().size();

        for (int i = 0; i < size; i++) {
            try {
                items.add(new ReportGrowModelClass(dto.getGrowup().get(i).getG_id()
                        ,dto.getGrowup().get(i).getG_height()
                        ,dto.getGrowup().get(i).getG_weight()
                        ,dto.getGrowup().get(i).getG_date()));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }

            adapter.notifyDataSetChanged();
        }
    }

}
