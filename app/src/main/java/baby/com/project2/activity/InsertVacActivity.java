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
import android.widget.EditText;
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
import baby.com.project2.dto.devlopment.DeleteDevelorMentDto;
import baby.com.project2.dto.vaccine.DeleteVaccineDto;
import baby.com.project2.dto.vaccine.InsertVaccineDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.develorment.DeleteDevManager;
import baby.com.project2.manager.singleton.vaccine.DataVaccineManager;
import baby.com.project2.manager.singleton.vaccine.DeleteVacManager;
import baby.com.project2.manager.singleton.vaccine.InsertVaccineManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertVacActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView TextViewNameVac,TextViewTypeVac,TextViewVacBirthday;
    private EditText TextViewVacLocation;
    private ImageView DeleteVac;
    private CardView CardViewTrue, CardViewFail;

    private String Fkcv_id,V_id,typeStr,NameStr,dateStr,Place;
    private int update;
    private String C_id;

    private int Day;
    private int Month;
    private int Year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_vac);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent id = getIntent();
        Fkcv_id = id.getStringExtra("id");
        V_id = id.getStringExtra("vid");
        typeStr = id.getStringExtra("type");
        NameStr = id.getStringExtra("name");
        dateStr = id.getStringExtra("date");
        Place = id.getStringExtra("place");
        update = id.getIntExtra("update",0);
        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
        TextViewVacBirthday.setOnClickListener(this);
        CardViewTrue.setOnClickListener(this);
        CardViewFail.setOnClickListener(this);
        DeleteVac.setOnClickListener(this);
    }

    private void initInstances() {

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextViewNameVac = (TextView)findViewById(R.id.textview_name_vac);
        TextViewTypeVac = (TextView)findViewById(R.id.textview_type_vac);
        TextViewVacBirthday = (TextView)findViewById(R.id.textview_vac_birthday);
        TextViewVacLocation = (EditText)findViewById(R.id.edit_vac_location);
        DeleteVac = (ImageView)findViewById(R.id.delete_vac);

        DeleteVac.setVisibility(View.INVISIBLE);

        CardViewTrue = (CardView)findViewById(R.id.cardview_true);
        CardViewFail = (CardView)findViewById(R.id.cardview_fail);
        setDataupdate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDataupdate() {

        C_id = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild();
        TextViewNameVac.setText(NameStr);
        TextViewTypeVac.setText(typeStr);
        TextViewVacBirthday.setText(dateStr);
        TextViewVacLocation.setText(Place);

        if(update == 1){
            DeleteVac.setVisibility(View.VISIBLE);
            DeleteVac.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        if(v == TextViewVacBirthday){
            setDateDialog();
        }
        if(v == CardViewTrue){
            insertdata(1);
        }
        if(v == CardViewFail){
            insertdata(0);
        }
        if(v == DeleteVac){
            ShowAlertDialogConfirm();
        }
    }

    private void insertdata(int i) {
        Place = TextViewVacLocation.getText().toString();
        reqinsert(C_id,V_id,dateStr,i,Place);
    }

    public void reqinsert(String cid,String vid,String date,int status,String place) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"C_id\": \""+cid+"\",\"V_id\":\""+vid+"\",\"FKcv_date\":\""+date+"\",\"FKcv_status\":"+status+","+
                "\"FKcv_plase\":\""+place+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<InsertVaccineDto> call = HttpManager.getInstance().getService().loadAPIVaccineinsert(requestBody);
        call.enqueue(new Callback<InsertVaccineDto>() {

            @Override
            public void onResponse(Call<InsertVaccineDto> call, Response<InsertVaccineDto> response) {
                if(response.isSuccessful()){
                    InsertVaccineDto dto = response.body();
                    InsertVaccineManager.getInstance().setItemsDto(dto);
                    if(response.body().getSuccess()){

                        ShowAlertDialog(response.body().getSuccess());
                    }
                    else{
                        ShowAlertDialog(response.body().getSuccess());
                        //Toast.makeText(mcontext,dto.getSuccess(),Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<InsertVaccineDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialog(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertVacActivity.this);

        if(success){

            if(update == 1){
                builder.setTitle("อัพเดทวัคซีน");
                builder.setMessage("อัพเดทข้อมูลวัคซีนเรียบร้อย");
            }else {
                builder.setTitle("เพิ่มข้อมูลวัคซีน");
                builder.setMessage("เพิ่มข้อมูลวัคซีนเรียบร้อย");
            }

            builder.setIcon(R.mipmap.ic_success);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
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


    private void setDateDialog() {

        final DatePickerDialog dialog = new DatePickerDialog(InsertVacActivity.this,new DatePickerDialog.OnDateSetListener() {
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
                TextViewVacBirthday.setText(fulldate);

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(InsertVacActivity.this);
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
                reqdelete(Fkcv_id);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void reqdelete(String id) {

        final Context mcontext = InsertVacActivity.this;
        String reqBody = "{\"id\":\""+id+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<DeleteVaccineDto> call = HttpManager.getInstance().getService().loadAPIDeleteVaccineDtoCall(requestBody);
        call.enqueue(new Callback<DeleteVaccineDto>() {

            @Override
            public void onResponse(Call<DeleteVaccineDto> call, Response<DeleteVaccineDto> response) {
                if(response.isSuccessful()){
                    DeleteVaccineDto dto = response.body();
                    DeleteVacManager.getInstance().setItemsDto(dto);

                    ShowAlertDialogDelete(response.body().isSuccess());

                }else {
                    ShowAlertDialogDelete(response.body().isSuccess());
//                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<DeleteVaccineDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialogDelete(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InsertVacActivity.this);

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
