package baby.com.project2.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import baby.com.project2.R;
import baby.com.project2.dto.DateDto;
import baby.com.project2.dto.growup.DeleteGrowUpDto;
import baby.com.project2.dto.growup.InsertGrowUpDto;
import baby.com.project2.dto.growup.SelectGrowItemsDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.growup.DeleteGrowManager;
import baby.com.project2.manager.singleton.growup.InsertGrowupManager;
import baby.com.project2.manager.singleton.growup.SelectGrowManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGrowActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageButton ImageBtnGrow;
    private ImageView ImageDeletePhoto,ImageAlertWidth,ImageAlertHeight,ImageDeleteData;
    private Button ButtonSave;
    private TextView TextViewEditChildBirthday;
    private EditText EdittextEditChildWeight,EdittextEditChildHeight;

    private int GId;
    private String gid;

    private int Day;
    private int Month;
    private int Year;

    private String dateStr;
    private float Weight,Height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grow);
        Intent id = getIntent();
        GId = id.getIntExtra("Gid",0);
        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
        TextViewEditChildBirthday.setOnClickListener(this);
    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageBtnGrow       = (ImageButton)findViewById(R.id.imagebtn_grow);
        ImageDeletePhoto   = (ImageView)findViewById(R.id.image_delete_photo_grow);
        ImageAlertWidth    = (ImageView)findViewById(R.id.image_alert_width_grow);
        ImageAlertHeight   = (ImageView)findViewById(R.id.image_alert_height_grow);

        ImageDeleteData    = (ImageView)findViewById(R.id.delete_grow);
        ButtonSave         = (Button)findViewById(R.id.button_save_editgrow);

        TextViewEditChildBirthday = (TextView)findViewById(R.id.textview_edit_child_birthday);

        EdittextEditChildWeight = (EditText)findViewById(R.id.edittext_edit_child_weight);
        EdittextEditChildHeight = (EditText)findViewById(R.id.edittext_edit_child_height);

        ImageDeletePhoto.setVisibility(View.INVISIBLE);
        ImageAlertWidth.setVisibility(View.INVISIBLE);
        ImageAlertHeight.setVisibility(View.INVISIBLE);

        setDataupdate();
        setDate();

    }

    private void setDataupdate() {

        DecimalFormat formatter = new DecimalFormat("00");
        SelectGrowItemsDto growUpDto = SelectGrowManager.getInstance().getItemsDto().getGrowup().get(GId);
        gid = formatter.format(growUpDto.getG_id());
        dateStr = growUpDto.getG_date();
        Height = growUpDto.getG_height();
        Weight = growUpDto.getG_weight();

        TextViewEditChildBirthday.setText(growUpDto.getG_date());
        EdittextEditChildHeight.setText(String.valueOf(growUpDto.getG_height()));
        EdittextEditChildWeight.setText(String.valueOf(growUpDto.getG_weight()));
    }

    private void setDate() {
        ImageDeleteData.setOnClickListener(this);
        ButtonSave.setOnClickListener(this);
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

            if(v == TextViewEditChildBirthday){
                setDateDialog();
            }

            if(v == ButtonSave){
                setDateGrow();
            }
            if(v == ImageDeleteData){
                ShowAlertDialogConfirm();
            }
    }

    private void ShowAlertDialogConfirm() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditGrowActivity.this);
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
                reqdeletechild(gid);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setDateGrow() {

        if(EdittextEditChildHeight.length()<=0){

        }
        if(EdittextEditChildWeight.length()<=0){

        }
        if(EdittextEditChildWeight.length()>0&&EdittextEditChildHeight.length()>0) {
            Weight = Float.valueOf(EdittextEditChildWeight.getText().toString());
            Height = Float.valueOf(EdittextEditChildHeight.getText().toString());
            reqinsert(dateStr, Weight, Height, SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyChild());
        }
    }

    public void reqinsert(String date,float weight,float height,String Cid) {

        final Context mcontext = EditGrowActivity.this;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditGrowActivity.this);

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

    private void setDateDialog() {

        final DatePickerDialog dialog = new DatePickerDialog(EditGrowActivity.this,new DatePickerDialog.OnDateSetListener() {
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
                TextViewEditChildBirthday.setText(fulldate);

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

    public void reqdeletechild(String gid) {

        final Context mcontext = EditGrowActivity.this;
        String reqBody = "{\"id\":\""+gid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<DeleteGrowUpDto> call = HttpManager.getInstance().getService().loadAPIDeleteGrow(requestBody);
        call.enqueue(new Callback<DeleteGrowUpDto>() {

            @Override
            public void onResponse(Call<DeleteGrowUpDto> call, Response<DeleteGrowUpDto> response) {
                if(response.isSuccessful()){
                    DeleteGrowUpDto dto = response.body();
                    DeleteGrowManager.getInstance().setItemsDto(dto);

                    ShowAlertDialogDelete(response.body().isSuccess());

                }else {
                    ShowAlertDialogDelete(response.body().isSuccess());
//                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<DeleteGrowUpDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialogDelete(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditGrowActivity.this);

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
