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
import baby.com.project2.adapter.ReportMilkListItemsAdapter;
import baby.com.project2.dto.milk.SelectMilkDto;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.milk.SelectMilkManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.ReportMilkModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportMilkFragment extends Fragment {


    ArrayList<ReportMilkModelClass> items;
    ReportMilkListItemsAdapter adapter;
    private RecyclerView recyclerView;

    SelectMilkDto dto;

    public ReportMilkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report_milk, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_report_milk);
        reqListMilk(SharedPrefUser.getInstance(getContext()).getKeyChild());

    }

    public void reqListMilk(String C_id) {

        final Context mcontext = getContext();
        String reqBody = "{\"C_id\":\""+C_id+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectMilkDto> call = HttpManager.getInstance().getService().loadAPISelectMilk(requestBody);
        call.enqueue(new Callback<SelectMilkDto>() {

            @Override
            public void onResponse(Call<SelectMilkDto> call, Response<SelectMilkDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    SelectMilkManager.getInstance().setItemsDto(dto);
                    setRecyclerView();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectMilkDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecyclerView() {

        Context context = getContext();
        items = new ArrayList<>();
        adapter = new ReportMilkListItemsAdapter(context, items);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        int size = dto.getMilk().size();

        for (int i = 0; i < size; i++) {
            try {
                items.add(new ReportMilkModelClass(dto.getMilk().get(i).getM_id()
                        ,dto.getMilk().get(i).getM_date()
                        ,dto.getMilk().get(i).getM_time()
                        ,dto.getMilk().get(i).getM_foodname()
                        ,dto.getMilk().get(i).getM_age()+" เดือน"
                        ,dto.getMilk().get(i).getM_Milk()
                        ,dto.getMilk().get(i).getM_unit()
                        ,dto.getMilk().get(i).getM_amount()));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }

            adapter.notifyDataSetChanged();
        }
    }
}
