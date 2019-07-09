package baby.com.project2.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.activity.ChildGrowActivity;
import baby.com.project2.dto.growup.SelectGrowUpDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.growup.SelectGrowManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrownHomeFragment extends Fragment implements View.OnClickListener {


    private Button ButtonAddData;
    private TextView TextViewDateUpdate,TextViewHeight,TextViewWidth;
    private CardView CardViewHeight,CardViewWidth,CardViewStatusBody;
    private TextView TextViewStatusHeight,TextViewStatusWidth,TextViewStatusBody;

    SelectGrowUpDto dto;

    public GrownHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_grown_home, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        ButtonAddData        = (Button)rootView.findViewById(R.id.button_add_data);

        TextViewDateUpdate   = (TextView)rootView.findViewById(R.id.textview_date_update);
        TextViewHeight       = (TextView)rootView.findViewById(R.id.textview_height);
        TextViewWidth        = (TextView)rootView.findViewById(R.id.textview_width);
        TextViewStatusHeight = (TextView)rootView.findViewById(R.id.textview_status_height);
        TextViewStatusWidth  = (TextView)rootView.findViewById(R.id.textview_status_width);
        TextViewStatusBody   = (TextView)rootView.findViewById(R.id.textview_status_body);

        CardViewHeight       = (CardView)rootView.findViewById(R.id.cardview_height);
        CardViewWidth        = (CardView)rootView.findViewById(R.id.cardview_width);
        CardViewStatusBody   = (CardView)rootView.findViewById(R.id.cardview_status_body);

        ButtonAddData.setOnClickListener(this);

        reqListGrow("01");

    }

    @Override
    public void onClick(View v) {
        if(v == ButtonAddData){
            Intent intent = new Intent(getContext(), ChildGrowActivity.class);
            getContext().startActivity(intent);
        }
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
                    setDate();
                    setData();

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

    private void setData() {
        int index = dto.getGrowup().size()-1;

        TextViewDateUpdate.setText(dto.getGrowup().get(index).getG_date());
        TextViewWidth.setText(String.valueOf(dto.getGrowup().get(index).getG_weight()));
        TextViewHeight.setText(String.valueOf(dto.getGrowup().get(index).getG_height()));
    }

    private void setDate() {

        int index = dto.getGrowup().size()-1;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        int DayBrith,MonthBrith,YearBrith,DayTo,MonthTo,YearTo,Day,Month,Year,Sum;

        Date datetoday = new Date();
        Calendar calendartoday = Calendar.getInstance();
        calendartoday.setTime(datetoday);

        DayTo = calendartoday.get(Calendar.DAY_OF_MONTH);
        MonthTo = calendartoday.get(Calendar.MONTH)+1;
        YearTo = calendartoday.get(Calendar.YEAR);

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date = sdf.parse("2019-06-08");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        DayBrith = calendar.get(Calendar.DAY_OF_MONTH);
        MonthBrith = calendar.get(Calendar.MONTH)+1;
        YearBrith = calendar.get(Calendar.YEAR);

        if(DayTo>=DayBrith){
            Day = DayTo-DayBrith;
            if(MonthTo>=MonthBrith){
                Month = MonthTo-MonthBrith;
                Year = YearTo-YearBrith;
                Sum = Month+Year;
            }else {
                Month = (MonthTo+12)-MonthBrith;
                Year = ((YearTo-1)-YearBrith)*12;
                Sum = Month+Year;
            }
        }else {
            Day = (DayTo+30)-DayBrith;
            Month = MonthTo-1;
            if(Month>=MonthBrith){
                Month = Month-MonthBrith;
                Year = (YearTo-YearBrith)*12;
                Sum = Month+Year;
            }else {
                Month = (Month+12)-MonthBrith;
                Year = ((YearTo-1)-YearBrith)*12;
                Sum = Month+Year;
            }
        }

        criterion(Day,Sum,SharedPrefUser.getInstance(getContext()).getGender()
                ,dto.getGrowup().get(index).getG_height(),dto.getGrowup().get(index).getG_weight());

    }

    private void criterion(int day, int sum,int gender,float h,float w) {

        if(gender == 1){
            if(sum == 1||sum == 0){
                if(w<3.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }
                if(h<50){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>57){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 2){
                if(w<4){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>6){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<53){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>60){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 3){
                if(w<4.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>6.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<55){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>63){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 4){
                if(w<5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>7.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<57){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>66){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 5){
                if(w<5.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>8){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<59){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>68){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 6){
                if(w<6){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>9){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<61){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>70){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 7){
                if(w<7){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>10){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<63){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>73){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 8){
                if(w<7.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>10.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<65){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>75){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 9){
                if(w<7.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>11){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<66){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>76){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 10){
                if(w<8){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>11){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<68){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>78){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 11){
                if(w<8){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>11.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<69){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>80){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 12){
                if(w<8.2){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>11){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<70){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>81){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 13){
                if(w<8.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>12.2){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<71){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>83){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 14){
                if(w<8.6){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>12.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<72){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>84){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 15){
                if(w<8.7){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>13){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<73){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>85){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 16){
                if(w<8.7){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>13.2){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<74){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>87){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 17){
                if(w<9){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>13.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<75){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>88){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 18){
                if(w<9.2){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>13.8){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<76){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>89){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 19){
                if(w<9.3){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>14){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<78){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>90){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 20){
                if(w<9.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>14.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<78){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>91){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 21){
                if(w<9.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>14.7){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<78){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>92){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 22){
                if(w<9.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>14.7){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<79){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>92){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 23){
                if(w<9.7){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>15){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<80){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>92){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 24){
                if(w<10){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>15.2){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<81){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>93){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else{
                
            }

        }else if (gender ==2){

            if(sum == 1||sum == 0){
                if(w<3){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>4.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<49){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>57){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 2){
                if(w<4){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>5.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<51){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>60){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 3){
                if(w<4.2){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>6){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<53){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>63){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 4){
                if(w<4.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>7){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<55){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>66){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 5){
                if(w<5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>7.8){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<58){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>68){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 6){
                if(w<5.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>8.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<59){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>71){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 7){
                if(w<6){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>9.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<61){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>73){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 8){
                if(w<6.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>10){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<63){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>74){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 9){
                if(w<6.8){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>10.3){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<64){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>76){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 10){
                if(w<7){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>10.7){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<65){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>78){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 11){
                if(w<7.2){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>11){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<66){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>79){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 12){
                if(w<7.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>11.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<67){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>81){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 13){
                if(w<7.7){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>11.7){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<68){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>82){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 14){
                if(w<8){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>12){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<69){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>73){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }

            }else if(sum == 15){
                if(w<8){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>12.3){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<70){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>84){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 16){
                if(w<8.2){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>12.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<71){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>85){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 17){
                if(w<8.4){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>12.7){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<72){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>86){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 18){
                if(w<8.5){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>13){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<73){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>87){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 19){
                if(w<8.7){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>13.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<74){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>88){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 20){
                if(w<8.8){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>13.6){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<75){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>89){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 21){
                if(w<8.8){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>14){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<76){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>90){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 22){
                if(w<8.8){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>14){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<76){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>91){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 23){
                if(w<9){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>14.2){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<77){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>91){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else if(sum == 24){
                if(w<9.2){
                    TextViewStatusWidth.setText("ต่ำกว่าเกณฑ์");
                }else if (w>14.5){
                    TextViewStatusWidth.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusWidth.setText("ตามเกณฑ์");
                }

                if(h<78){
                    TextViewStatusHeight.setText("ต่ำกว่าเกณฑ์");
                }else if (h>92){
                    TextViewStatusHeight.setText("มากกว่าเกณฑ์");
                }else {
                    TextViewStatusHeight.setText("ตามเกณฑ์");
                }
            }else{

            }

        }

    }
}
