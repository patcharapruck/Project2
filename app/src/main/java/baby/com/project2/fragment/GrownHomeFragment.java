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
import java.text.Format;
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
    private TextView TextViewDateUpdate, TextViewHeight, TextViewWidth;
    private CardView CardViewHeight, CardViewWidth, CardViewStatusBody;
    private TextView TextViewStatusHeight, TextViewStatusWidth, TextViewStatusBody;

    SelectGrowUpDto dto;

    private String Height_c,Weigth_c;

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

        ButtonAddData = (Button) rootView.findViewById(R.id.button_add_data);

        TextViewDateUpdate = (TextView) rootView.findViewById(R.id.textview_date_update);
        TextViewHeight = (TextView) rootView.findViewById(R.id.textview_height);
        TextViewWidth = (TextView) rootView.findViewById(R.id.textview_width);
        TextViewStatusHeight = (TextView) rootView.findViewById(R.id.textview_status_height);
        TextViewStatusWidth = (TextView) rootView.findViewById(R.id.textview_status_width);
        TextViewStatusBody = (TextView) rootView.findViewById(R.id.textview_status_body);

        CardViewHeight = (CardView) rootView.findViewById(R.id.cardview_height);
        CardViewWidth = (CardView) rootView.findViewById(R.id.cardview_width);
        CardViewStatusBody = (CardView) rootView.findViewById(R.id.cardview_status_body);

        ButtonAddData.setOnClickListener(this);

        reqListGrow(SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild());

    }

    @Override
    public void onClick(View v) {
        if (v == ButtonAddData) {
            Intent intent = new Intent(getContext(), ChildGrowActivity.class);
            getContext().startActivity(intent);
        }
    }

    public void reqListGrow(String C_id) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"C_id\":\"" + C_id + "\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), reqBody);
        Call<SelectGrowUpDto> call = HttpManager.getInstance().getService().loadAPIGrowUpSelect(requestBody);
        call.enqueue(new Callback<SelectGrowUpDto>() {

            @Override
            public void onResponse(Call<SelectGrowUpDto> call, Response<SelectGrowUpDto> response) {
                if (response.isSuccessful()) {
                    dto = response.body();

                    try {
                        setDate();
                    }catch (Exception e){

                    }
                    try {
                        setData();
                    }catch (Exception e){

                    }

                } else {
                    Toast.makeText(mcontext, "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SelectGrowUpDto> call, Throwable t) {
                Toast.makeText(mcontext, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
        int index = dto.getGrowup().size() - 1;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Format formatter = new SimpleDateFormat("dd MMMM", new Locale("th", "TH"));
        Format formatter2 = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
        Date d = null;
        try {
            d = sdf.parse(dto.getGrowup().get(index).getG_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendartoday = Calendar.getInstance();
        calendartoday.setTime(d);
        String formatDateTime = formatter.format(d);
        String formatDateTime2 = formatter2.format(d);

        int yth = Integer.parseInt(formatDateTime2)+543;

        TextViewDateUpdate.setText(formatDateTime +" "+yth);
        TextViewWidth.setText(String.valueOf(dto.getGrowup().get(index).getG_weight()));
        TextViewHeight.setText(String.valueOf(dto.getGrowup().get(index).getG_height()));
    }

    private void setDate() {

        int index = dto.getGrowup().size() - 1;
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
            criterion(Day, Sum, SharedPrefUser.getInstance(getContext()).getGender()
                    , dto.getGrowup().get(index).getG_height(), dto.getGrowup().get(index).getG_weight());
        }catch (Exception e){

        }

        try {
            setbody();
        }catch (Exception e){

        }

    }

    private void setbody() {

        if(Weigth_c.equals(getString(R.string.grade_low).toString())&& Height_c.equals(getString(R.string.grade_good).toString())){
            setlow();
        }else if(Weigth_c.equals(getString(R.string.grade_low).toString())&& Height_c.equals(getString(R.string.grade_heigth).toString())){
            setlow();
        }else if(Weigth_c.equals(getString(R.string.grade_good).toString())&& Height_c.equals(getString(R.string.grade_good).toString())){
            selgood();
        }else if(Weigth_c.equals(getString(R.string.grade_good).toString())&& Height_c.equals(getString(R.string.grade_heigth).toString())){
            setlow();
        }else if(Weigth_c.equals(getString(R.string.grade_heigth).toString())&& Height_c.equals(getString(R.string.grade_good).toString())){
            setfat();
        }else if(Weigth_c.equals(getString(R.string.grade_heigth).toString())&& Height_c.equals(getString(R.string.grade_heigth).toString())){
            setfat();
        }else {
            TextViewStatusBody.setText("ไม่สามารถคำนวณได้");
            CardViewStatusBody.setCardBackgroundColor(getResources().getColor(R.color.noting));
        }
    }

    private void setfat() {
        TextViewStatusBody.setText("อ้วน");
        CardViewStatusBody.setCardBackgroundColor(getResources().getColor(R.color.very_low));
    }

    private void selgood() {
        TextViewStatusBody.setText("สมส่วน");
        CardViewStatusBody.setCardBackgroundColor(getResources().getColor(R.color.good));
    }

    private void setlow() {
        TextViewStatusBody.setText(getString(R.string.bodylow).toString());
        CardViewStatusBody.setCardBackgroundColor(getResources().getColor(R.color.very_low));
    }

    private void criterion(int day, int sum, int gender, float h, float w) {

        if (gender == 1) {

            if(sum == 0){

                if (w < 2.8) {
                    lowWidth();
                } else if (w > 3.9) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 47.6) {
                    lowHeigth();
                } else if (h > 53.1) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 1) {
                if (w < 3.5) {
                    lowWidth();
                } else if (w > 5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 50) {
                    lowHeigth();
                } else if (h > 57) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 2) {
                if (w < 4) {
                    lowWidth();
                } else if (w > 6) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 53) {
                    lowHeigth();
                } else if (h > 60) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 3) {
                if (w < 4.5) {
                    lowWidth();
                } else if (w > 6.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 55) {
                    lowHeigth();
                } else if (h > 63) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 4) {
                if (w < 5) {
                    lowWidth();
                } else if (w > 7.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 57) {
                    lowHeigth();
                } else if (h > 66) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 5) {
                if (w < 5.5) {
                    lowWidth();
                } else if (w > 8) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 59) {
                    lowHeigth();
                } else if (h > 68) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 6) {
                if (w < 6) {
                    lowWidth();
                } else if (w > 9) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 61) {
                    lowHeigth();
                } else if (h > 70) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 7) {
                if (w < 7) {
                    lowWidth();
                } else if (w > 10) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 63) {
                    lowHeigth();
                } else if (h > 73) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 8) {
                if (w < 7.5) {
                    lowWidth();
                } else if (w > 10.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 65) {
                    lowHeigth();
                } else if (h > 75) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 9) {
                if (w < 7.5) {
                    lowWidth();
                } else if (w > 11) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 66) {
                    lowHeigth();
                } else if (h > 76) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 10) {
                if (w < 8) {
                    lowWidth();
                } else if (w > 11) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 68) {
                    lowHeigth();
                } else if (h > 78) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 11) {
                if (w < 8) {
                    lowWidth();
                } else if (w > 11.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 69) {
                    lowHeigth();
                } else if (h > 80) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 12) {
                if (w < 8.2) {
                    lowWidth();
                } else if (w > 11) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 70) {
                    lowHeigth();
                } else if (h > 81) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 13) {
                if (w < 8.5) {
                    lowWidth();
                } else if (w > 12.2) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 71) {
                    lowHeigth();
                } else if (h > 83) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 14) {
                if (w < 8.6) {
                    lowWidth();
                } else if (w > 12.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 72) {
                    lowHeigth();
                } else if (h > 84) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 15) {
                if (w < 8.7) {
                    lowWidth();
                } else if (w > 13) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 73) {
                    lowHeigth();
                } else if (h > 85) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 16) {
                if (w < 8.7) {
                    lowWidth();
                } else if (w > 13.2) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 74) {
                    lowHeigth();
                } else if (h > 87) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 17) {
                if (w < 9) {
                    lowWidth();
                } else if (w > 13.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 75) {
                    lowHeigth();
                } else if (h > 88) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 18) {
                if (w < 9.2) {
                    lowWidth();
                } else if (w > 13.8) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 76) {
                    lowHeigth();
                } else if (h > 89) {
                    heigthHeigth();
                } else {
                    goodWidth();
                }
            } else if (sum == 19) {
                if (w < 9.3) {
                    lowWidth();
                } else if (w > 14) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 78) {
                    lowHeigth();
                } else if (h > 90) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 20) {
                if (w < 9.5) {
                    lowWidth();
                } else if (w > 14.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 78) {
                    lowHeigth();
                } else if (h > 91) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 21) {
                if (w < 9.5) {
                    lowWidth();
                } else if (w > 14.7) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 78) {
                    lowHeigth();
                } else if (h > 92) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 22) {
                if (w < 9.5) {
                    lowWidth();
                } else if (w > 14.7) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 79) {
                    lowHeigth();
                } else if (h > 92) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 23) {
                if (w < 9.7) {
                    lowWidth();
                } else if (w > 15) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 80) {
                    lowHeigth();
                } else if (h > 92) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 24) {
                if (w < 10) {
                    lowWidth();
                } else if (w > 15.2) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 81) {
                    lowHeigth();
                } else if (h > 93) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else {
                notting();
            }

        } else if (gender == 2) {
            if(sum == 0){
                if (w < 2.7) {
                    lowWidth();
                } else if (w > 3.7) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 46.8) {
                    lowHeigth();
                } else if (h > 52.9) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 1) {
                if (w < 3) {
                    lowWidth();
                } else if (w > 4.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 49) {
                    lowHeigth();
                } else if (h > 57) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 2) {
                if (w < 4) {
                    lowWidth();
                } else if (w > 5.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 51) {
                    lowHeigth();
                } else if (h > 60) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 3) {
                if (w < 4.2) {
                    lowWidth();
                } else if (w > 6) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 53) {
                    lowHeigth();
                } else if (h > 63) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 4) {
                if (w < 4.5) {
                    lowWidth();
                } else if (w > 7) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 55) {
                    lowHeigth();
                } else if (h > 66) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 5) {
                if (w < 5) {
                    lowWidth();
                } else if (w > 7.8) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 58) {
                    lowHeigth();
                } else if (h > 68) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 6) {
                if (w < 5.5) {
                    lowWidth();
                } else if (w > 8.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 59) {
                    lowHeigth();
                } else if (h > 71) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 7) {
                if (w < 6) {
                    lowWidth();
                } else if (w > 9.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 61) {
                    lowHeigth();
                } else if (h > 73) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 8) {
                if (w < 6.5) {
                    lowWidth();
                } else if (w > 10) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 63) {
                    lowHeigth();
                } else if (h > 74) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 9) {
                if (w < 6.8) {
                    lowWidth();
                } else if (w > 10.3) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 64) {
                    lowHeigth();
                } else if (h > 76) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 10) {
                if (w < 7) {
                    lowWidth();
                } else if (w > 10.7) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 65) {
                    lowHeigth();
                } else if (h > 78) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 11) {
                if (w < 7.2) {
                    lowWidth();
                } else if (w > 11) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 66) {
                    lowHeigth();
                } else if (h > 79) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 12) {
                if (w < 7.5) {
                    lowWidth();
                } else if (w > 11.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 67) {
                    lowHeigth();
                } else if (h > 81) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 13) {
                if (w < 7.7) {
                    lowWidth();
                } else if (w > 11.7) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 68) {
                    lowHeigth();
                } else if (h > 82) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 14) {
                if (w < 8) {
                    lowWidth();
                } else if (w > 12) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 69) {
                    lowHeigth();
                } else if (h > 73) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }

            } else if (sum == 15) {
                if (w < 8) {
                    lowWidth();
                } else if (w > 12.3) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 70) {
                    lowHeigth();
                } else if (h > 84) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 16) {
                if (w < 8.2) {
                    lowWidth();
                } else if (w > 12.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 71) {
                    lowHeigth();
                } else if (h > 85) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 17) {
                if (w < 8.4) {
                    lowWidth();
                } else if (w > 12.7) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 72) {
                    lowHeigth();
                } else if (h > 86) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 18) {
                if (w < 8.5) {
                    lowWidth();
                } else if (w > 13) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 73) {
                    lowHeigth();
                } else if (h > 87) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 19) {
                if (w < 8.7) {
                    lowWidth();
                } else if (w > 13.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 74) {
                    lowHeigth();
                } else if (h > 88) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 20) {
                if (w < 8.8) {
                    lowWidth();
                } else if (w > 13.6) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 75) {
                    lowHeigth();
                } else if (h > 89) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 21) {
                if (w < 8.8) {
                    lowWidth();
                } else if (w > 14) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 76) {
                    lowHeigth();
                } else if (h > 90) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 22) {
                if (w < 8.8) {
                    lowWidth();
                } else if (w > 14) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 76) {
                    lowHeigth();
                } else if (h > 91) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 23) {
                if (w < 9) {
                    lowHeigth();
                } else if (w > 14.2) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 77) {
                    lowHeigth();
                } else if (h > 91) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else if (sum == 24) {
                if (w < 9.2) {
                    lowWidth();
                } else if (w > 14.5) {
                    heigthWidth();
                } else {
                    goodWidth();
                }

                if (h < 78) {
                    lowHeigth();
                } else if (h > 92) {
                    heigthHeigth();
                } else {
                    goodHeight();
                }
            } else {
                notting();
            }

        }

    }

    private void notting() {
        TextViewStatusBody.setText("ไม่สามารถคำนวณได้");
        CardViewStatusBody.setCardBackgroundColor(getResources().getColor(R.color.noting));
        TextViewStatusWidth.setText("ไม่สามารถคำนวณได้");
        CardViewWidth.setCardBackgroundColor(getResources().getColor(R.color.noting));
        TextViewStatusHeight.setText("ไม่สามารถคำนวณได้");
        CardViewHeight.setCardBackgroundColor(getResources().getColor(R.color.noting));
    }

    private void goodWidth() {
        Weigth_c = getString(R.string.grade_good).toString();
        TextViewStatusWidth.setText(getString(R.string.grade_good).toString());
        CardViewWidth.setCardBackgroundColor(getResources().getColor(R.color.good));
    }

    private void heigthWidth() {
        Weigth_c = getString(R.string.grade_heigth).toString();
        TextViewStatusWidth.setText(getString(R.string.grade_heigth).toString());
        CardViewWidth.setCardBackgroundColor(getResources().getColor(R.color.very_low));
    }

    private void lowHeigth() {
        Height_c = getString(R.string.grade_low).toString();
        TextViewStatusHeight.setText(getString(R.string.grade_low).toString());
        CardViewHeight.setCardBackgroundColor(getResources().getColor(R.color.very_low));
    }

    private void goodHeight() {
        Height_c = getString(R.string.grade_good).toString();
        TextViewStatusHeight.setText(getString(R.string.grade_good).toString());
        CardViewHeight.setCardBackgroundColor(getResources().getColor(R.color.good));
    }

    private void heigthHeigth() {
        Height_c = getString(R.string.grade_heigth).toString();
        TextViewStatusHeight.setText(getString(R.string.grade_heigth).toString());
        CardViewHeight.setCardBackgroundColor(getResources().getColor(R.color.very_low));
    }

    private void lowWidth() {
        Weigth_c = getString(R.string.grade_low).toString();
        TextViewStatusWidth.setText(getString(R.string.grade_low).toString());
        CardViewWidth.setCardBackgroundColor(getResources().getColor(R.color.very_low));
    }

}