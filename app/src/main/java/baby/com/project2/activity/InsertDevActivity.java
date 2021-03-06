package baby.com.project2.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.devlopment.DeleteDevelorMentDto;
import baby.com.project2.dto.devlopment.InsertDevelorMentDto;
import baby.com.project2.dto.devlopment.SelectDataDevDto;
import baby.com.project2.dto.devlopment.SelectDataDevItemsDto;
import baby.com.project2.dto.growup.DeleteGrowUpDto;
import baby.com.project2.dto.growup.InsertGrowUpDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.develorment.DataDevManager;
import baby.com.project2.manager.singleton.develorment.DeleteDevManager;
import baby.com.project2.manager.singleton.develorment.InsertDevelorMentManager;
import baby.com.project2.manager.singleton.growup.DeleteGrowManager;
import baby.com.project2.manager.singleton.growup.InsertGrowupManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertDevActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView ImageViewShowDev,DeleteDev;
    private TextView TextViewTypeDev, TextViewDataDev, TextViewDevBirthday;
    private CardView CardViewTrue, CardViewFail;

    private String FKcd_id,C_id,BD_id;
    private String dateStr,typeStr,DataStr,url;
    private int update;

    private int Day;
    private int Month;
    private int Year;
    private String formatDateTimeToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_dev);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent id = getIntent();
        FKcd_id = id.getStringExtra("fkcd_id");
        BD_id = id.getStringExtra("id");
        typeStr = id.getStringExtra("type");
        DataStr = id.getStringExtra("data");
        dateStr = id.getStringExtra("date");
        update = id.getIntExtra("update",0);
        url = id.getStringExtra("imag");

        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
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
        DeleteDev = (ImageView)findViewById(R.id.delete_dev);

        Glide.with(InsertDevActivity.this)
                .load(url)
                .into(ImageViewShowDev);

        DeleteDev.setVisibility(View.INVISIBLE);

        setDataupdate();
    }

    private void setDataupdate() {
        C_id = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild();
        TextViewTypeDev.setText(typeStr);
        TextViewDataDev.setText(DataStr);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Format form = new SimpleDateFormat("dd MMMM", new Locale("th", "TH"));
        Format formatter2 = new SimpleDateFormat("yyyy", new Locale("th", "TH"));
        Date d = null;
        try {
            d = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendartoday = Calendar.getInstance();
        calendartoday.setTime(d);
        String f = form.format(d);
        String f2 = formatter2.format(d);
        int yth = Integer.parseInt(f2)+543;
        String datefullTh = f+" "+yth;

        TextViewDevBirthday.setText(datefullTh);

        if(update == 1){
            DeleteDev.setVisibility(View.VISIBLE);
            DeleteDev.setOnClickListener(this);
        }

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
        if(v == DeleteDev){
            ShowAlertDialogConfirm();
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
                TextViewDevBirthday.setText(datefullTh);

            }
        },Year,Month-1,Day);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dialog.show();
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
            if(update == 1){
                builder.setTitle("อัพเดทพัฒนาการ");
                builder.setMessage("อัพเดทข้อมูลพัฒนาการเรียบร้อย");
            }else {
                builder.setTitle("เพิ่มพัฒนาการ");
                builder.setMessage("เพิ่มข้อมูลพัฒนาการเรียบร้อย");
            }
            builder.setIcon(R.mipmap.ic_success);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }else {
            if(update == 1){
                builder.setTitle("ล้มเหลว");
                builder.setMessage("เกิดข้อผิดพลาด อัพเดทข้อมูลไม่สำเร็จ");
            }else {
                builder.setTitle("ล้มเหลว");
                builder.setMessage("เกิดข้อผิดพลาด เพิ่มข้อมูลไม่สำเร็จ");
            }
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

    private void getDateTime() {

        Date date = null;
        String NewDateString = dateStr;
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

    private void ShowAlertDialogConfirm() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(InsertDevActivity.this);
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
                reqdelete(FKcd_id);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void reqdelete(String id) {

        final Context mcontext = InsertDevActivity.this;
        String reqBody = "{\"id\":\""+id+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<DeleteDevelorMentDto> call = HttpManager.getInstance().getService().loadAPIDeleteDevelorMentDtoCall(requestBody);
        call.enqueue(new Callback<DeleteDevelorMentDto>() {

            @Override
            public void onResponse(Call<DeleteDevelorMentDto> call, Response<DeleteDevelorMentDto> response) {
                if(response.isSuccessful()){
                    DeleteDevelorMentDto dto = response.body();
                    DeleteDevManager.getInstance().setItemsDto(dto);

                    ShowAlertDialogDelete(response.body().isSuccess());

                }else {
                    ShowAlertDialogDelete(response.body().isSuccess());
//                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<DeleteDevelorMentDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialogDelete(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertDevActivity.this);

        if(success){
            builder.setTitle("ลบข้อมูล");
            builder.setMessage("ลบข้อมูลสำเร็จ");
            builder.setIcon(R.mipmap.ic_success);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
        }else if (!success) {
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
}
