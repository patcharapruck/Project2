package baby.com.project2.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.devlopment.InsertDevelorMentDto;
import baby.com.project2.dto.devlopment.SelectDataDevDto;
import baby.com.project2.dto.devlopment.SelectDataDevItemsDto;
import baby.com.project2.dto.growup.InsertGrowUpDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.develorment.DataDevManager;
import baby.com.project2.manager.singleton.develorment.InsertDevelorMentManager;
import baby.com.project2.manager.singleton.growup.InsertGrowupManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertDevActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView ImageViewShowDev;
    private TextView TextViewTypeDev, TextViewDataDev, TextViewDevBirthday;

    private CardView CardViewTrue, CardViewFail;

    private String FKcd_id,C_id,BD_id;
    private String dateStr,typeStr,DataStr;
    private int status;

    private int Day;
    private int Month;
    private int Year;
    private String formatDateTimeToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_dev);

        Intent id = getIntent();
        BD_id = id.getStringExtra("id");
        typeStr = id.getStringExtra("type");
        DataStr = id.getStringExtra("data");
        dateStr = id.getStringExtra("date");

        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        TextViewDevBirthday.setOnClickListener(this);
        CardViewTrue.setOnClickListener(this);
        CardViewFail.setOnClickListener(this);
    }

    private void initInstances() {

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageViewShowDev = (ImageView)findViewById(R.id.imageview_show_dev);
        TextViewTypeDev = (TextView)findViewById(R.id.textview_type_dev);
        TextViewDataDev = (TextView)findViewById(R.id.textview_data_dev);
        TextViewDevBirthday = (TextView)findViewById(R.id.textview_dev_birthday);
        CardViewTrue = (CardView)findViewById(R.id.cardview_true);
        CardViewFail = (CardView)findViewById(R.id.cardview_fail);

        setDataupdate();
    }

    private void setDataupdate() {
        C_id = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild();
        TextViewTypeDev.setText(typeStr);
        TextViewDataDev.setText(DataStr);
        TextViewDevBirthday.setText(dateStr);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v == TextViewDevBirthday){
            setDateDialog();
        }
        if(v == CardViewTrue){
            insertdata(1);
        }
        if(v == CardViewFail){
            insertdata(0);
        }

    }

    private void insertdata(int i) {
        reqinsert(C_id,BD_id,dateStr,i);
    }

    private void setDateDialog() {

        final DatePickerDialog dialog = new DatePickerDialog(InsertDevActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                DecimalFormat formatter = new DecimalFormat("00");
                String mm = formatter.format(month+1);
                String dd = formatter.format(dayOfMonth);;

                String fulldate = year+ "-" + mm + "-" +dd;

                dateStr = fulldate;

                Day = dayOfMonth;
                Month = month+1;
                Year = year;
                TextViewDevBirthday.setText(fulldate);

            }
        },Year,Month-1,Day);

        Date date = null;
        Date d = null;
        String oldDateString = "2019/01/06";
        String NewDateString = formatDateTimeToday;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        try {
            d = sdf.parse(oldDateString);
            date = sdf.parse(NewDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dialog.show();
        dialog.getDatePicker().setMinDate(d.getTime());
        dialog.getDatePicker().setMaxDate(date.getTime());
    }

    public void reqinsert(String c_id,String bD_id,String date,int status) {

        final Context mcontext = InsertDevActivity.this;
        String reqBody = "{\"C_id\":\""+c_id+"\",\"BD_id\":\""+bD_id+"\",\"FKcd_date\":\""+date+"\","+
                "\"FKcd_status\":"+status+" }";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<InsertDevelorMentDto> call = HttpManager.getInstance().getService().loadAPInsertDevelorMentDtoCall(requestBody);
        call.enqueue(new Callback<InsertDevelorMentDto>() {

            @Override
            public void onResponse(Call<InsertDevelorMentDto> call, Response<InsertDevelorMentDto> response) {
                if(response.isSuccessful()){
                    InsertDevelorMentDto dto = response.body();
                    InsertDevelorMentManager.getInstance().setItemsDto(dto);
                    if(response.body().getSuccess()){
                        ShowAlertDialog(response.body().getSuccess());
                    }
                    else{
                        ShowAlertDialog(response.body().getSuccess());
//                        Toast.makeText(mcontext,dto.getSuccess(),Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<InsertDevelorMentDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialog(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertDevActivity.this);

        if(success){
            builder.setTitle("เพิ่มข้อมูลเด็ก");
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
            builder.setTitle("เพิ่มข้อมูลเด็ก");
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
}
