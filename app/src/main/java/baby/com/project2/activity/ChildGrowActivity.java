package baby.com.project2.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.growup.InsertGrowUpDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.growup.InsertGrowupManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildGrowActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageButton ImageBtnGrow;
    private ImageView ImageDeletePhoto,ImageAlertWidth,ImageAlertHeight;
    private Button ButtonSave;
    private TextView TextViewAddChildBirthday;
    private EditText EdittextAddChildWeight,EdittextAddChildHeight;

    private String dateStr;
    private float Weight,Height;

    private int Day;
    private int Month;
    private int Year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_grow);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
        TextViewAddChildBirthday.setOnClickListener(this);
    }

    private void initInstances(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageBtnGrow       = (ImageButton)findViewById(R.id.imagebtn_grow);
        ImageDeletePhoto   = (ImageView)findViewById(R.id.image_delete_photo);
        ImageAlertWidth    = (ImageView)findViewById(R.id.image_alert_width);
        ImageAlertHeight   = (ImageView)findViewById(R.id.image_alert_height);

        ButtonSave         = (Button)findViewById(R.id.button_save);

        TextViewAddChildBirthday = (TextView)findViewById(R.id.textview_add_child_birthday);

        EdittextAddChildWeight = (EditText)findViewById(R.id.edittext_add_child_weight);
        EdittextAddChildHeight = (EditText)findViewById(R.id.edittext_add_child_height);

        ImageDeletePhoto.setVisibility(View.INVISIBLE);
        ImageAlertWidth.setVisibility(View.INVISIBLE);
        ImageAlertHeight.setVisibility(View.INVISIBLE);

        ButtonSave.setOnClickListener(this);
    }

    private void setDate() {

        if(EdittextAddChildHeight.length()<=0){

        }
        if(EdittextAddChildWeight.length()<=0){

        }
        if(EdittextAddChildWeight.length()>0&&EdittextAddChildHeight.length()>0){
            Weight = Float.valueOf(EdittextAddChildWeight.getText().toString());
            Height = Float.valueOf(EdittextAddChildHeight.getText().toString());
            reqinsert(dateStr,Weight,Height, SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild());
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

        if(v == ButtonSave){
            setDate();
        }
        if(v == TextViewAddChildBirthday){
            setDateDialog();
        }
    }
    public void reqinsert(String date,float weight,float height,String Cid) {

        final Context mcontext = ChildGrowActivity.this;
        String reqBody = "{\"G_height\":"+height+",\"G_weight\" :"+weight+",\"G_date\":\""+date+"\","+
                "\"C_id\":\""+Cid+"\" }";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<InsertGrowUpDto> call = HttpManager.getInstance().getService().loadAPIGrowup(requestBody);
        call.enqueue(new Callback<InsertGrowUpDto>() {

            @Override
            public void onResponse(Call<InsertGrowUpDto> call, Response<InsertGrowUpDto> response) {
                if(response.isSuccessful()){
                    InsertGrowUpDto dto = response.body();
                    InsertGrowupManager.getInstance().setItemsDto(dto);
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
            public void onFailure(Call<InsertGrowUpDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialog(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChildGrowActivity.this);

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

    private void getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        dateStr = dateFormat.format(calendar.getTime());

        TextViewAddChildBirthday.setText(dateStr);

        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Month = calendar.get(Calendar.MONTH)+1;
        Year = calendar.get(Calendar.YEAR);

        DateDto dateDto = new DateDto();
        dateDto.setCalendar(calendar);
        dateDto.setDateToday(date);
        dateDto.setDateString(dateStr);
        dateDto.setDay(Day);
        dateDto.setMonth(Month);
        dateDto.setYear(Year);
        DateManager.getInstance().setDateDto(dateDto);
    }

    private void setDateDialog() {

        final DatePickerDialog dialog = new DatePickerDialog(ChildGrowActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                DecimalFormat formatter = new DecimalFormat("00");
                String mm = formatter.format(month+1);
                String dd = formatter.format(dayOfMonth);;

                String fulldate = year+ "-" + mm + "-" +dd;

                Day = dayOfMonth;
                Month = month+1;
                Year = year;
                TextViewAddChildBirthday.setText(fulldate);

            }
        },Year,Month-1,Day);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dialog.show();
        dialog.getDatePicker().setMaxDate(date.getTime());
    }
}
