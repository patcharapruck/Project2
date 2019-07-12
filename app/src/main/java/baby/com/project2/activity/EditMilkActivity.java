package baby.com.project2.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.milk.DeleteMilkDto;
import baby.com.project2.dto.milk.SelectMilkItemsDto;
import baby.com.project2.dto.milk.UpdateMilkDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.milk.DeleteMilkManager;
import baby.com.project2.manager.singleton.milk.SelectMilkManager;
import baby.com.project2.manager.singleton.milk.UpdateMilkManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMilkActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView TextViewClock, TextViewEditMilkBirthday;
    private EditText EditTextEditMilkNamefood, EditTextEditMilkAge,EditTextVolume;
    private Spinner SpinnerFood,SpinnerVolume;
    private ImageView CloseImgbtnEditmilk, ImageAlertNameEditmilk, ImageViewCalendarEditMilk,ImageViewClock,DeleteMilk;
    private ImageButton ImagebtnAddprofileEditMilk;
    private Button BtnEditMilk;

    private ArrayList<String> mTypeSearch = new ArrayList<String>();
    private ArrayList<String> mType = new ArrayList<String>();

    private Toolbar toolbar;
    private int Day;
    private int Month;
    private int Year;
    private String formatDate;

    private String Volume="",NameType="";

    private int index;
    private String mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_milk);

        Intent id = getIntent();
        index = id.getIntExtra("id",0);

        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
        ImageViewCalendarEditMilk.setOnClickListener(this);
        TextViewEditMilkBirthday.setOnClickListener(this);
    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setToolBar();

        TextViewEditMilkBirthday = (TextView)findViewById(R.id.textview_edit_milk_birthday);
        TextViewClock = (TextView)findViewById(R.id.textview_clock);
        EditTextEditMilkAge = (EditText)findViewById(R.id.edittext_edit_milk_age);
        EditTextEditMilkNamefood = (EditText)findViewById(R.id.edittext_edit_milk_namefood);
        EditTextVolume = (EditText)findViewById(R.id.edittext_volume);
        SpinnerFood = (Spinner) findViewById(R.id.spinner_food);
        SpinnerVolume = (Spinner)findViewById(R.id.spinner_volume);
        ImageViewCalendarEditMilk = (ImageView)findViewById(R.id.imageview_calendar_edit_milk);
        ImageViewClock = (ImageView)findViewById(R.id.imageview_clock);
        CloseImgbtnEditmilk = (ImageView)findViewById(R.id.close_imgbtn_editmilk);
        ImageAlertNameEditmilk = (ImageView)findViewById(R.id.image_alert_name_editmilk);
        ImagebtnAddprofileEditMilk = (ImageButton)findViewById(R.id.imagebtn_addprofile_editmilk);
        BtnEditMilk = (Button)findViewById(R.id.btn_edit_milk);
        DeleteMilk = (ImageView)findViewById(R.id.delete_milk);

        CloseImgbtnEditmilk.setVisibility(View.INVISIBLE);
        ImageAlertNameEditmilk.setVisibility(View.INVISIBLE);

        BtnEditMilk.setOnClickListener(this);
        DeleteMilk.setOnClickListener(this);

        createTypeSearchData();
        createTypeData();
        spinnerChoose();
        spinnerChooseType();
        setDataupdate();
    }

    private void setDataupdate() {
        DecimalFormat formatter = new DecimalFormat("00");
        SelectMilkItemsDto milkDto = SelectMilkManager.getInstance().getItemsDto().getMilk().get(index);

        formatDate = milkDto.getM_date();

        mid = formatter.format(milkDto.getM_id());
        EditTextEditMilkNamefood.setText(milkDto.getM_foodname());
        TextViewEditMilkBirthday.setText(milkDto.getM_date());
        EditTextEditMilkAge.setText(String.valueOf(milkDto.getM_age()));
        EditTextVolume.setText(String.valueOf(milkDto.getM_amount()));
        TextViewClock.setText(milkDto.getM_time());

//        int size = milkDto.getMilk().size();
//        for(int i=0;i<size;i++){
//            if(index == milkDto.getMilk().get(i).getM_id()){
//                mid = formatter.format(milkDto.getMilk().get(i).getM_id());
//                EditTextEditMilkNamefood.setText(milkDto.getMilk().get(i).getM_foodname());
//                TextViewEditMilkBirthday.setText(milkDto.getMilk().get(i).getM_date());
//                EditTextEditMilkAge.setText(String.valueOf(milkDto.getMilk().get(i).getM_age()));
//                EditTextVolume.setText(String.valueOf(milkDto.getMilk().get(i).getM_amount()));
//                TextViewClock.setText(milkDto.getMilk().get(i).getM_time());
//            }
//        }
    }

    private void createTypeSearchData() {

        if (mTypeSearch.isEmpty()){
            mTypeSearch.add("นม");
            mTypeSearch.add("อาหาร");
            mTypeSearch.add("อาหารเสริม");
        }
    }

    private void createTypeData() {

        if (mType.isEmpty()){
            mType.add("กรัม");
            mType.add("ออนซ์");
        }
    }

    private void spinnerChoose() {
        ArrayAdapter<String> spinsSearch = new ArrayAdapter<String>(EditMilkActivity.this
                ,R.layout.support_simple_spinner_dropdown_item,mTypeSearch);

        SpinnerFood.setAdapter(spinsSearch);
        SpinnerFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NameType = mTypeSearch.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void spinnerChooseType() {
        ArrayAdapter<String> spinsSearch = new ArrayAdapter<String>(EditMilkActivity.this
                ,R.layout.support_simple_spinner_dropdown_item,mType);

        SpinnerVolume.setAdapter(spinsSearch);
        SpinnerVolume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Volume = mType.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDateDialog() {

        final DatePickerDialog dialog = new DatePickerDialog(EditMilkActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                DecimalFormat formatter = new DecimalFormat("00");
                String mm = formatter.format(month+1);
                String dd = formatter.format(dayOfMonth);;

                String fulldate = year+ "-" + mm + "-" +dd;

                Day = dayOfMonth;
                Month = month+1;
                Year = year;
                TextViewEditMilkBirthday.setText(fulldate);

            }
        },Year,Month-1,Day);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dialog.show();
        dialog.getDatePicker().setMaxDate(date.getTime());
    }

    private void getDateTime() {

        Date date = null;
        String NewDateString = formatDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date = sdf.parse(NewDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Month = calendar.get(Calendar.MONTH)+1;
        Year = calendar.get(Calendar.YEAR);
    }

    public void requpdate(String mid,String nameType,String name,int age,int amount,String volume,String birthday,String time) {

        final Context mcontext = EditMilkActivity.this;
        String reqBody = "{\"M_id\":\""+mid+"\",\"M_foodname\": \""+name+"\",\"M_Milk\":\""+nameType+"\",\"M_age\" :"+age+",\"M_amount\":"+amount+","+
                "\"M_unit\":\""+volume+"\",\"M_date\":\""+birthday+"\",\"M_time\":\""+time+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<UpdateMilkDto> call = HttpManager.getInstance().getService().loadAPIupdateMilk(requestBody);
        call.enqueue(new Callback<UpdateMilkDto>() {

            @Override
            public void onResponse(Call<UpdateMilkDto> call, Response<UpdateMilkDto> response) {
                if(response.isSuccessful()){
                    UpdateMilkDto dto = response.body();
                    UpdateMilkManager.getInstance().setItemsDto(dto);
                    if(response.body().isSuccess()){
                        ShowAlertDialog(response.body().isSuccess());
                    }
                    else{
                        ShowAlertDialog(response.body().isSuccess());
//                        Toast.makeText(mcontext,dto.getSuccess(),Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UpdateMilkDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialog(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditMilkActivity.this);

        if(success){
            builder.setTitle("อัพเดทข้อมูล");
            builder.setMessage("อัพเดทข้อมูลสำเร็จ");
            builder.setIcon(R.mipmap.ic_success);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }else {
            builder.setTitle("อัพเดทข้อมูล");
            builder.setMessage("เกิดข้อผิดพลาด อัพเดทข้อมูลล้มเหลว");
            builder.setIcon(R.mipmap.ic_failed);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    private void DataAddMilk() {

        DecimalFormat formatter = new DecimalFormat("00");

        String name,brithday,volume,nametype,time;
        int age,amount;

        name = EditTextEditMilkNamefood.getText().toString();
        brithday = TextViewEditMilkBirthday.getText().toString();
        time = TextViewClock.getText().toString();
        volume = Volume;
        nametype = NameType;

        try {
            age = Integer.valueOf(EditTextEditMilkAge.getText().toString());
        }catch (Exception e){
            age = 0;
        }
        try {
            amount = Integer.valueOf(EditTextVolume.getText().toString());
        }catch (Exception e){
            amount = 0;
        }


        if(name.length()<1){
            ImageAlertNameEditmilk.setVisibility(View.VISIBLE);
        }else {
            ImageAlertNameEditmilk.setVisibility(View.INVISIBLE);
            requpdate(mid,nametype,name,age,amount,volume,brithday,time);
        }

    }

    private void ShowAlertDialogConfirm() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditMilkActivity.this);
        builder.setTitle("ลบข้อมูล");
        builder.setMessage("คุณต้องการลบข้อมูลเด็กคนนี้ใช่หรือไม่");
        builder.setIcon(R.mipmap.ic_confirm_dialog);
        builder.setCancelable(true);
        builder.setPositiveButton("ไม่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                reqdelete(mid);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void reqdelete(String mid) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"id\":\""+mid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<DeleteMilkDto> call = HttpManager.getInstance().getService().loadAPIdeleteMilk(requestBody);
        call.enqueue(new Callback<DeleteMilkDto>() {

            @Override
            public void onResponse(Call<DeleteMilkDto> call, Response<DeleteMilkDto> response) {
                if(response.isSuccessful()){
                    DeleteMilkDto dto = response.body();
                    DeleteMilkManager.getInstance().setItemsDto(dto);

                    ShowAlertDialogDelete(response.body().isSuccess());

                }else {
                    ShowAlertDialogDelete(response.body().isSuccess());
//                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<DeleteMilkDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialogDelete(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditMilkActivity.this);

        if(success){
            builder.setTitle("ลบข้อมูล");
            builder.setMessage("ลบข้อมูลสำเร็จ");
            builder.setIcon(R.mipmap.ic_success);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }else {
            builder.setTitle("ลบข้อมูล");
            builder.setMessage("เกิดข้อผิดพลาด ลบข้อมูลล้มเหลว");
            builder.setIcon(R.mipmap.ic_failed);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v== TextViewEditMilkBirthday ||v== ImageViewCalendarEditMilk){
            setDateDialog();
        }
        if(v== BtnEditMilk){
            DataAddMilk();
        }
        if(v == DeleteMilk){
            ShowAlertDialogConfirm();
        }
    }
}
