package baby.com.project2.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.activity.VaccineActivity;
import baby.com.project2.adapter.VaccineListItemsAdapter;
import baby.com.project2.dto.SizeVacDto;
import baby.com.project2.dto.vaccine.SelectAgeVaccineDto;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.vaccine.AgeVaccineManager;
import baby.com.project2.manager.singleton.vaccine.DataVaccineManager;
import baby.com.project2.manager.singleton.vaccine.SizeVacManager;
import baby.com.project2.manager.singleton.vaccine.VaccineManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.VaccineModelClass;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListVaccineFragment extends Fragment {

    private Spinner SpinsVaccine;
    private ArrayList<String> mTypeSearch = new ArrayList<String>();

    SelectAgeVaccineDto dtoage;
    SelectVaccineDto dto;

    ArrayList<String> stringArrayList;

    String vid;
    ArrayList<VaccineModelClass> items;
    VaccineListItemsAdapter adapter;
    private RecyclerView recyclerView;


    public ListVaccineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_vaccine, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        SpinsVaccine = (Spinner) rootView.findViewById(R.id.spins_vaccine);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_vaccine);
        reqDatavaccine(SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild());
    }

    private void setRecyclerView() {
        items = new ArrayList<>();
        adapter = new VaccineListItemsAdapter(getContext(), items);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        int size = dto.getVaccine().size();
        int sum = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyBrithint();

        if (sum >= 18) {

            for (int i = 0; i < size; i++) {
                try {
                    items.add(new VaccineModelClass(dto.getVaccine().get(i).getV_id(), dto.getVaccine().get(i).getV_name()
                            , dto.getVaccine().get(i).getV_type()));
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }
            }

            SizeVacDto sizeVacDto = new SizeVacDto();
            sizeVacDto.setSize_dev(size);
            SizeVacManager.getInstance().setItemsDto(sizeVacDto);

        } else {
            int sizeType = stringArrayList.size();
            int s = 0;
            for (int i = 0; i < sizeType; i++) {

                for (int j = 0; j < size; j++) {

                    if (dto.getVaccine().get(j).getId_agevac().equals(stringArrayList.get(i))) {

                        s++;
                        try {
                            items.add(new VaccineModelClass(dto.getVaccine().get(j).getV_id(), dto.getVaccine().get(j).getV_name()
                                    , dto.getVaccine().get(j).getV_type()));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                    }
                }
            }

            SizeVacDto sizeVacDto = new SizeVacDto();
            sizeVacDto.setSize_dev(s);
            SizeVacManager.getInstance().setItemsDto(sizeVacDto);
        }

        adapter.notifyDataSetChanged();
    }

    private void createTypeSearchData() {

        if (mTypeSearch.isEmpty()) {
            for (int i = 0; i < dtoage.getAge_vaccine().size(); i++) {
                mTypeSearch.add(dtoage.getAge_vaccine().get(i).getAge_vac());
            }
        }
        spinnerChoose();
    }

    public void reqAgeVaccine() {

        final Context mcontext = getContext();
        Call<SelectAgeVaccineDto> call = HttpManager.getInstance().getService().loadAPIAgeVaccine();
        call.enqueue(new Callback<SelectAgeVaccineDto>() {

            @Override
            public void onResponse(Call<SelectAgeVaccineDto> call, Response<SelectAgeVaccineDto> response) {
                if (response.isSuccessful()) {
                    dtoage = response.body();
                    AgeVaccineManager.getInstance().setItemsDto(dtoage);
                    //createTypeSearchData();
                    reqvaccine("");

                } else {
                    Toast.makeText(mcontext, "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SelectAgeVaccineDto> call, Throwable t) {
                Toast.makeText(mcontext, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void spinnerChoose() {
        ArrayAdapter<String> spinsSearch = new ArrayAdapter<String>(getContext()
                , R.layout.support_simple_spinner_dropdown_item, mTypeSearch);

        SpinsVaccine.setAdapter(spinsSearch);
        SpinsVaccine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vid = dtoage.getAge_vaccine().get(position).getId_agevac();
                reqvaccine(vid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void reqvaccine(String vid) {

        final Context mcontext = getContext();
        String reqBody = "{\"id_agevac\":\"" + vid + "\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqBody);
        Call<SelectVaccineDto> call = HttpManager.getInstance().getService().loadAPIvaccine(requestBody);
        call.enqueue(new Callback<SelectVaccineDto>() {

            @Override
            public void onResponse(Call<SelectVaccineDto> call, Response<SelectVaccineDto> response) {
                if (response.isSuccessful()) {
                    dto = response.body();
                    VaccineManager.getInstance().setItemsDto(dto);
                    Vac();

                } else {
                    Toast.makeText(mcontext, "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SelectVaccineDto> call, Throwable t) {
                Toast.makeText(mcontext, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void Vac() {
        stringArrayList = new ArrayList<>();
        int sum = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyBrithint();

        if (sum == 0) {
            stringArrayList.add("01");
        } else if (sum < 4) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
        } else if (sum < 6) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
        } else if (sum < 12) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
        } else if (sum < 18) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
        } else if (sum < 24) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
            stringArrayList.add("07");
            stringArrayList.add("10");
            stringArrayList.add("12");
        }
        setRecyclerView();
    }

    public void reqDatavaccine(String cid) {

        final Context mcontext = getContext();
        String reqBody = "{\"C_id\":\"" + cid + "\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqBody);
        Call<SelectDataVaccineDto> call = HttpManager.getInstance().getService().loadAPIVaccineData(requestBody);
        call.enqueue(new Callback<SelectDataVaccineDto>() {

            @Override
            public void onResponse(Call<SelectDataVaccineDto> call, Response<SelectDataVaccineDto> response) {
                if (response.isSuccessful()) {
                    SelectDataVaccineDto dtovac = response.body();
                    DataVaccineManager.getInstance().setItemsDto(dtovac);
                    reqAgeVaccine();

                } else {
                    Toast.makeText(getContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SelectDataVaccineDto> call, Throwable t) {
                Toast.makeText(mcontext, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
