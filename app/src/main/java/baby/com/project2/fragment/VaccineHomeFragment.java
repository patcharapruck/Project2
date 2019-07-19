package baby.com.project2.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.activity.DevelopMentActivity;
import baby.com.project2.activity.VaccineActivity;
import baby.com.project2.dto.SizeVacDto;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
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
public class VaccineHomeFragment extends Fragment implements View.OnClickListener {

    private Button ButtonAddData;
    private TextView TextViewStatusVaccine;

    private String cid;
    private int ageV,vv,vv2=0;

    SelectVaccineDto dto;
    SelectDataVaccineDto dataVaccineDto;

    ArrayList<String> stringArrayList;

    public VaccineHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vaccine_home, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        ButtonAddData         = (Button)rootView.findViewById(R.id.button_add_data);
        TextViewStatusVaccine = (TextView)rootView.findViewById(R.id.textview_status_vaccine);
        cid = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild();
        ButtonAddData.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        reqDatavaccine(cid);
        Vac();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), VaccineActivity.class);
        getContext().startActivity(intent);
    }

    public void reqvaccine(String vid) {

        final Context mcontext = getContext();
        String reqBody = "{\"id_agevac\":\""+vid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectVaccineDto> call = HttpManager.getInstance().getService().loadAPIvaccine(requestBody);
        call.enqueue(new Callback<SelectVaccineDto>() {

            @Override
            public void onResponse(Call<SelectVaccineDto> call, Response<SelectVaccineDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    try{
                        setDate();
                    }catch (Exception e){
                    }
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

    public void reqDatavaccine(String cid) {

        final Context mcontext = getContext();
        String reqBody = "{\"C_id\":\""+cid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectDataVaccineDto> call = HttpManager.getInstance().getService().loadAPIVaccineData(requestBody);
        call.enqueue(new Callback<SelectDataVaccineDto>() {

            @Override
            public void onResponse(Call<SelectDataVaccineDto> call, Response<SelectDataVaccineDto> response) {
                if(response.isSuccessful()){
                    dataVaccineDto = response.body();
                    reqvaccine("");

                }else {
                    Toast.makeText(getContext(),"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectDataVaccineDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        int DayBrith, MonthBrith, YearBrith, DayTo, MonthTo, YearTo, Day, Month, Year, Sum;

        Date datetoday = new Date();
        Calendar calendartoday = Calendar.getInstance();
        calendartoday.setTime(datetoday);

        DayTo = calendartoday.get(Calendar.DAY_OF_MONTH);
        MonthTo = calendartoday.get(Calendar.MONTH) + 1;
        YearTo = calendartoday.get(Calendar.YEAR);

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date = sdf.parse(SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyBrith());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        DayBrith = calendar.get(Calendar.DAY_OF_MONTH);
        MonthBrith = calendar.get(Calendar.MONTH) + 1;
        YearBrith = calendar.get(Calendar.YEAR);

        if (DayTo >= DayBrith) {
            Day = DayTo - DayBrith;
            if (MonthTo >= MonthBrith) {
                Month = MonthTo - MonthBrith;
                Year = YearTo - YearBrith;
                Sum = Month + Year;
            } else {
                Month = (MonthTo + 12) - MonthBrith;
                Year = ((YearTo - 1) - YearBrith) * 12;
                Sum = Month + Year;
            }
        } else {
            Day = (DayTo + 30) - DayBrith;
            Month = MonthTo - 1;
            if (Month >= MonthBrith) {
                Month = Month - MonthBrith;
                Year = (YearTo - YearBrith) * 12;
                Sum = Month + Year;
            } else {
                Month = (Month + 12) - MonthBrith;
                Year = ((YearTo - 1) - YearBrith) * 12;
                Sum = Month + Year;
            }
        }

        try {
            Showdata(Sum);
        }catch (Exception e){

        }

    }

    private void Showdata(int sum) {

        int all = vacAll(sum);
        int succ = 0;

        int size = dataVaccineDto.getDatavaccine().size();
        for (int i = 0; i < size; i++) {
            if(dataVaccineDto.getDatavaccine().get(i).getFKcv_status()==1){
                succ++;
            }
        }

        TextViewStatusVaccine.setText("ฉีดแล้ว "+ succ +" จาก "+all);

    }

    private int vacAll(int sum) {
        int size = dto.getVaccine().size();
        int s = 0;
        if (sum >= 18) {

            for (int i = 0; i < size; i++) {
                    s++;

            }


        } else {
            int sizeType = stringArrayList.size();
            for (int i = 0; i < sizeType; i++) {
                for (int j = 0; j < size; j++) {
                    if (dto.getVaccine().get(j).getId_agevac().equals(stringArrayList.get(i))) {
                            s++;

                    }
                }
            }
        }

        return s;
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
    }


}
