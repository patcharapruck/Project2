package baby.com.project2.activity;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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

import com.mcsoft.timerangepickerdialog.RangeTimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.milk.InsertMilkDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.milk.InsertMilkManager;
import baby.com.project2.sentImage.ApiClient;
import baby.com.project2.sentImage.Img_Pojo;
import baby.com.project2.sentImage.Img_Pojo_milk;
import baby.com.project2.util.SharedPrefDayMonthYear;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMilkActivity extends AppCompatActivity implements RangeTimePickerDialog.ISelectedTime, View.OnClickListener {


    private TextView TextViewClock,TextViewAddMilkBirthday;
    private EditText EditTextAddMilkNamefood,EditTextAddMilkAge,EditTextVolume;
    private Spinner SpinnerFood,SpinnerVolume;
    private ImageView  CloseImgbtnAddmilk,ImageAlertNameAddmilk,ImageViewCalendarAddMilk,ImageViewClock;
    private ImageView ImagebtnAddprofileAddMilk;
    private Button BtnAddMilk;

    private ArrayList<String> mTypeSearch = new ArrayList<String>();
    private ArrayList<String> mType = new ArrayList<String>();

    private Toolbar toolbar;
    private int Day;
    private int Month;
    private int Year;
    private String currentDateTimeString;
    private String formatDateTimeToday;
    private String formatDateTime;

    private String Volume="",NameType="";

    Bitmap bitmap,resize;
    private  static final int IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_milk);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
        getTime();
        ImagebtnAddprofileAddMilk.setOnClickListener(this);
        ImageViewCalendarAddMilk.setOnClickListener(this);
        TextViewAddMilkBirthday.setOnClickListener(this);
        ImageViewClock.setOnClickListener(this);
        TextViewClock.setOnClickListener(this);

    }

    private void getTime() {

        Date d =new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm");
        currentDateTimeString = sdf.format(d);
        TextViewClock.setText(currentDateTimeString);

    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setToolBar();

        TextViewAddMilkBirthday = (TextView)findViewById(R.id.textview_add_milk_birthday);
        TextViewClock = (TextView)findViewById(R.id.textview_clock);
        EditTextAddMilkAge = (EditText)findViewById(R.id.edittext_add_milk_age);
        EditTextAddMilkNamefood = (EditText)findViewById(R.id.edittext_add_milk_namefood);
        EditTextVolume = (EditText)findViewById(R.id.edittext_volume);
        SpinnerFood = (Spinner) findViewById(R.id.spinner_food);
        SpinnerVolume = (Spinner)findViewById(R.id.spinner_volume);
        ImageViewCalendarAddMilk = (ImageView)findViewById(R.id.imageview_calendar_add_milk);
        ImageViewClock = (ImageView)findViewById(R.id.imageview_clock);
        CloseImgbtnAddmilk = (ImageView)findViewById(R.id.close_imgbtn_addmilk);
        ImageAlertNameAddmilk = (ImageView)findViewById(R.id.image_alert_name_addmilk);
        ImagebtnAddprofileAddMilk = (ImageView) findViewById(R.id.imagebtn_addprofile_addmilk);
        BtnAddMilk = (Button)findViewById(R.id.btn_add_milk);

        CloseImgbtnAddmilk.setVisibility(View.INVISIBLE);
        ImageAlertNameAddmilk.setVisibility(View.INVISIBLE);

        BtnAddMilk.setOnClickListener(this);
        EditTextAddMilkAge.setText(SharedPrefDayMonthYear.getInstance(Contextor.getInstance().getmContext()).getKeydateformat());

        createTypeSearchData();
        createTypeData();
        spinnerChoose();
        spinnerChooseType();
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
        ArrayAdapter<String> spinsSearch = new ArrayAdapter<String>(AddMilkActivity.this
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
        ArrayAdapter<String> spinsSearch = new ArrayAdapter<String>(AddMilkActivity.this
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

        final DatePickerDialog dialog = new DatePickerDialog(AddMilkActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                DecimalFormat formatter = new DecimalFormat("00");
                String mm = formatter.format(month+1);
                String dd = formatter.format(dayOfMonth);;

                String fulldate = year+ "-" + mm + "-" +dd;
                formatDateTime = fulldate;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Format form = new SimpleDateFormat("dd MMMM", new Locale("th", "TH"));
                Format formatter2 = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
                Date d = null;
                try {
                    d = sdf.parse(fulldate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendartoday = Calendar.getInstance();
                calendartoday.setTime(d);
                String f = form.format(d);
                String f2 = formatter2.format(d);
                int yth = Integer.parseInt(f2)+543;
                String datefullTh = f+" "+yth;

                Day = dayOfMonth;
                Month = month+1;
                Year = year;
                TextViewAddMilkBirthday.setText(datefullTh);

            }
        },Year,Month-1,Day);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dialog.show();
        dialog.getDatePicker().setMaxDate(date.getTime());
    }

    private void getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        formatDateTime = dateFormat.format(calendar.getTime());
        formatDateTimeToday = dateFormat2.format(calendar.getTime());

        Format formatter = new SimpleDateFormat("dd MMMM", new Locale("th", "TH"));
        Format formatter2 = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
        String f= formatter.format(calendar.getTime());
        String f2 = formatter2.format(calendar.getTime());
        int yth = Integer.parseInt(f2)+543;
        String datefullTh = f+" "+yth;

        TextViewAddMilkBirthday.setText(datefullTh);

        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Month = calendar.get(Calendar.MONTH)+1;
        Year = calendar.get(Calendar.YEAR);

        DateDto dateDto = new DateDto();
        dateDto.setCalendar(calendar);
        dateDto.setDateToday(date);
        dateDto.setDateString(formatDateTime);
        dateDto.setDay(Day);
        dateDto.setMonth(Month);
        dateDto.setYear(Year);
        DateManager.getInstance().setDateDto(dateDto);
    }

    public void reqinsert(String nameType,String name,String age,int amount,String volume,String birthday,String time,String cid) {

        final Context mcontext = AddMilkActivity.this;
        String reqBody = "{\"M_foodname\": \""+name+"\",\"M_Milk\":\""+nameType+"\",\"M_age\" :\""+age+"\",\"M_amount\":"+amount+","+
                "\"M_unit\":\""+volume+"\",\"M_date\":\""+birthday+"\",\"M_time\":\""+time+"\",\"C_id\":\""+cid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<InsertMilkDto> call = HttpManager.getInstance().getService().loadAPIInsertMilk(requestBody);
        call.enqueue(new Callback<InsertMilkDto>() {

            @Override
            public void onResponse(Call<InsertMilkDto> call, Response<InsertMilkDto> response) {
                if(response.isSuccessful()){
                    InsertMilkDto dto = response.body();
                    InsertMilkManager.getInstance().setItemsDto(dto);
                    if(response.body().isSuccess()){
                        DecimalFormat formatter = new DecimalFormat("00");
                        ShowAlertDialog(response.body().isSuccess());

                        uploadImage(formatter.format(dto.getId()));

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
            public void onFailure(Call<InsertMilkDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialog(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddMilkActivity.this);

        if(success){
            builder.setTitle("เพิ่มข้อมูลโภชนาการเด็ก");
            builder.setMessage("เพิ่มข้อมูลสำเร็จ");
            builder.setIcon(R.mipmap.ic_success);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }else {
            builder.setTitle("เพิ่มข้อมูลโภชนาการเด็ก");
            builder.setMessage("เกิดข้อผิดพลาด เพิ่มข้อมูลล้มเหลว");
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

        String age,name,brithday,volume,nametype,cid,time;
        int amount;

        cid = SharedPrefUser.getInstance(AddMilkActivity.this).getKeyChild();
        name = EditTextAddMilkNamefood.getText().toString();
        brithday = formatDateTime;
        time = TextViewClock.getText().toString();
        volume = Volume;
        nametype = NameType;
        age = SharedPrefDayMonthYear.getInstance(Contextor.getInstance().getmContext()).getKeydateformat();

        try {
            amount = Integer.valueOf(EditTextVolume.getText().toString());
        }catch (Exception e){
            amount = 0;
        }


        if(name.length()<1){
            ImageAlertNameAddmilk.setVisibility(View.VISIBLE);
        }else {
            ImageAlertNameAddmilk.setVisibility(View.INVISIBLE);
            reqinsert(nametype,name,age,amount,volume,brithday,time,cid);
        }

    }

    @Override
    public void onClick(View v) {
        if(v==TextViewAddMilkBirthday||v==ImageViewCalendarAddMilk){
            setDateDialog();
        }
        if(v== BtnAddMilk){
            DataAddMilk();
        }
        if(v == ImageViewClock||v == TextViewClock){
            setTimeDialog();
        }
        if(v == ImagebtnAddprofileAddMilk){
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(AddMilkActivity.this);
            alertDialog.setTitle(R.string.pick_profile_picture);

            alertDialog.setItems(R.array.change_button_items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int which) {
                    if (which == 0) {
                        // Open Camera
//                        captureImage();
                    }
                    if (which == 1) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, IMAGE);
                    }
                    if (which == 2) {
                        ImagebtnAddprofileAddMilk.setBackgroundResource(R.mipmap.ic_baby_milk);
                    }

                }
            });
            android.app.AlertDialog alert = alertDialog.create();
            alert.show();
        }
    }

    private void setTimeDialog() {

        RangeTimePickerDialog dialog = new RangeTimePickerDialog();
        dialog.newInstance();
        dialog.setRadiusDialog(20); // Set radius of dialog (default is 50)
        dialog.setIs24HourView(true); // Indicates if the format should be 24 hours
        dialog.setValidateRange(false);
        dialog.setColorBackgroundHeader(R.color.colorPrimary); // Set Color of Background header dialog
        dialog.setColorTextButton(R.color.colorPrimaryDark); // Set Text color of button
        dialog.setInitialOpenedTab(RangeTimePickerDialog.InitialOpenedTab.START_CLOCK_TAB);
        dialog.setTextTabStart(currentDateTimeString);
        FragmentManager fragmentManager = getFragmentManager();
        dialog.show(fragmentManager, "");
    }


    @Override
    public void onSelectedTime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {

        DecimalFormat formatter = new DecimalFormat("00");
        currentDateTimeString = formatter.format(hourStart)+":"+formatter.format(minuteStart);
        TextViewClock.setText(currentDateTimeString);
    }


    private String convertToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resize = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.5), (int)(bitmap.getHeight()*0.5), true);
        resize.compress(Bitmap.CompressFormat.JPEG,1,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== IMAGE && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                ImagebtnAddprofileAddMilk.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void uploadImage(String format) {

        ArrayList<String[]> ttt = new ArrayList<>();
        String image ="";

        try{
            image = convertToString();
        }catch (Exception e){
            image = "";
        }

        image.replaceAll("\\\\n", "\n");
        ttt.add(image.split("\\n"));
        String zz="";
        String[] a = ttt.get(0);
        int size = a.length;

        for(int i=0;i<size;i++){
            zz = zz + a[i];
        }

        String imageName= "testUploed";
        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"image\":\""+zz+"\",\"image_name\": \""+imageName+"\",\"mid\":\""+format+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<Img_Pojo_milk> call = ApiClient.getInstance().getService().uploadImageMilk(requestBody);
        call.enqueue(new Callback<Img_Pojo_milk>() {

            @Override
            public void onResponse(Call<Img_Pojo_milk> call, Response<Img_Pojo_milk> response) {
                if(response.isSuccessful()){
                    Img_Pojo_milk dto = response.body();
                    if(response.body().isResponse()){

                    }
                    else{
                        Toast.makeText(mcontext,"ลงไม่ได้บักโง่",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Img_Pojo_milk> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
