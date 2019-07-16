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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.activity.DevelopMentActivity;
import baby.com.project2.activity.VaccineActivity;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.vaccine.DataVaccineManager;
import baby.com.project2.manager.singleton.vaccine.VaccineManager;
import baby.com.project2.util.SharedPrefUser;
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

        reqDatavaccine(cid);
        ButtonAddData.setOnClickListener(this);
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
                    setDate();

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

        int size = dataVaccineDto.getDatavaccine().size();


        if(sum == 0){
            vv = 2;
        }
        else if(sum <= 2){
            vv = 2;
        }else if(sum <= 4){
            vv = 5;
        }else if(sum <= 6){
            vv = 7;
        }else if(sum <= 12){
            vv =10;
        }else if(sum <= 18){
            vv = 11;
        }else if(sum <= 24){
            vv = 15;
        }

        for(int j=0;j<size;j++){

            int sizeV = dto.getVaccine().size();
            DecimalFormat formatter = new DecimalFormat("00");

            for (int k=0;k<sizeV;k++){

                if(formatter.format(dataVaccineDto.getDatavaccine().get(j).getV_id())
                        .equals(dto.getVaccine().get(k).getV_id())){

                    if(dto.getVaccine().get(k).getV_type().equals("วัคซีนหลัก")){
                            if(dto.getVaccine().get(k).getId_agevac().equals("01")){
                                ageV = 0;
                            }
                            else if(dto.getVaccine().get(k).getId_agevac().equals("02")||
                                    dto.getVaccine().get(k).getId_agevac().equals("03")){
                                ageV = 2;
                            }else if(dto.getVaccine().get(k).getId_agevac().equals("04")){
                                ageV = 4;
                            }else if(dto.getVaccine().get(k).getId_agevac().equals("05")){
                                ageV = 6;
                            }else if(dto.getVaccine().get(k).getId_agevac().equals("06")){
                                ageV = 12;
                            }else if(dto.getVaccine().get(k).getId_agevac().equals("07")){
                                ageV = 18;
                            }else if(dto.getVaccine().get(k).getId_agevac().equals("08")){
                                ageV = 24;
                            }else if(dto.getVaccine().get(k).getId_agevac().equals("07")||
                                    dto.getVaccine().get(k).getId_agevac().equals("10")||
                                    dto.getVaccine().get(k).getId_agevac().equals("12")){
                                ageV = 18;
                            }else {
                                ageV = 24;
                            }


                            if(sum>ageV){
                                vv2++;
                            }
                    }

                }

            }

        }

        int s=0;
        if (vv>=vv2){
            s = vv-vv2;
        }

        TextViewStatusVaccine.setText("วัคซีนที่ยังไม่ฉีดมี " +s+" ชนิด");

    }


}
