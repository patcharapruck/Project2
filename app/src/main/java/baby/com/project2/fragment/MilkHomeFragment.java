package baby.com.project2.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.activity.AddMilkActivity;
import baby.com.project2.activity.VaccineActivity;
import baby.com.project2.dto.milk.SelectMilkDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.milk.SelectMilkManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MilkHomeFragment extends Fragment implements View.OnClickListener {

    private Button ButtonAddData;
    private TextView TextviewDateUpdate,TextviewTimeUpdate,TextviewReportTypeMilk,TextviewReportNameMilk,TextviewReportAmountMilk
            ,TextviewReportVolumeMilk;

    private ImageView ImageViewFood;

    private String cid;

    SelectMilkDto dto;

    public MilkHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_milk_home, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        ButtonAddData         = (Button)rootView.findViewById(R.id.button_add_data);
        TextviewDateUpdate = (TextView)rootView.findViewById(R.id.textview_date_update);
        TextviewTimeUpdate = (TextView)rootView.findViewById(R.id.textview_time_update);
        TextviewReportNameMilk = (TextView)rootView.findViewById(R.id.textview_report_name_milk);
        TextviewReportTypeMilk = (TextView)rootView.findViewById(R.id.textview_report_type_milk);
        TextviewReportAmountMilk = (TextView)rootView.findViewById(R.id.textview_report_amount_milk);
        TextviewReportVolumeMilk = (TextView)rootView.findViewById(R.id.textview_report_volume_milk);
        ImageViewFood = (ImageView)rootView.findViewById(R.id.imageview_food);
        ButtonAddData.setOnClickListener(this);
        cid = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild();
    }

    @Override
    public void onStart() {
        super.onStart();
        reqListMilk(cid);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), AddMilkActivity.class);
        getContext().startActivity(intent);
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

//                    try {
//                        setDate();
//                    }catch (Exception e){
//
//                    }
                    try {
                        setData();
                    }catch (Exception e){

                    }


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

    private void setData() {
        int index = dto.getMilk().size() - 1;


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Format formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
        Date d = null;
        try {
            d = sdf.parse(dto.getMilk().get(index).getM_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendartoday = Calendar.getInstance();
        calendartoday.setTime(d);
        String formatDateTime = formatter.format(d);

        TextviewDateUpdate.setText(formatDateTime);
        TextviewTimeUpdate.setText(dto.getMilk().get(index).getM_time());
        TextviewReportNameMilk.setText(dto.getMilk().get(index).getM_foodname());
        TextviewReportTypeMilk.setText(dto.getMilk().get(index).getM_Milk());
        TextviewReportAmountMilk.setText(String.valueOf(dto.getMilk().get(index).getM_amount()));
        TextviewReportVolumeMilk.setText(dto.getMilk().get(index).getM_unit());

        if(dto.getMilk().get(index).getM_image().length()<1||dto.getMilk().get(index).getM_image()==null){
            ImageViewFood.setBackgroundResource(R.mipmap.ic_baby_milk);
            ImageViewFood.setImageResource(0);
        }else{
            byte[] decodedString = Base64.decode(dto.getMilk().get(index).getM_image(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageViewFood.setBackgroundResource(0);
            ImageViewFood.setImageBitmap(decodedByte);
        }
    }

}
