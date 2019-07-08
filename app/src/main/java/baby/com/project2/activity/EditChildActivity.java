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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import baby.com.project2.dto.LoginItemsDto;
import baby.com.project2.dto.child.DeleteChildDto;
import baby.com.project2.dto.child.SelectChildItemsDto;
import baby.com.project2.dto.child.UpdateChildDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.child.DeleteChildManager;
import baby.com.project2.manager.singleton.LoginManager;
import baby.com.project2.manager.singleton.child.SelectChildManager;
import baby.com.project2.manager.singleton.child.UpdateChildManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditChildActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ImageBtnAddProfileEditChild;
    private ImageView CloseImgbtnEditChild, ImageAlertNameEditChild, ImageViewCalendarEditChild,DeleteChild;
    private TextView TextViewEditChildBirthday;
    private EditText EditTextEditChildName, EditTextEditChildWeight, EditTextEditChildHeight;
    private Spinner SpinnerBloodTypeEditChild;
    private RadioGroup Gender;
    private RadioButton RdGenderBoy,RdGenderGirl;
    private Button BtnEditChild;
    private Toolbar toolbar;

    private ArrayList<String> mTypeSearch = new ArrayList<String>();

    private int Day;
    private int Month;
    private int Year;

    private String formatDateTimeToday;
    private String BloodType = "A";
    private int CId;
    private String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);

        Intent id = getIntent();
        CId = id.getIntExtra("Cid",0);

        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
        ImageViewCalendarEditChild.setOnClickListener(this);
    }

    private void initInstances() {

        toolbar                     = (Toolbar)findViewById(R.id.toolbar);
        ImageBtnAddProfileEditChild = (ImageButton)findViewById(R.id.imagebtn_addprofile_editchild);
        CloseImgbtnEditChild        = (ImageView)findViewById(R.id.close_imgbtn_editchild);
        ImageAlertNameEditChild     = (ImageView)findViewById(R.id.image_alert_name_editchild);
        ImageViewCalendarEditChild  = (ImageView)findViewById(R.id.imageview_calendar_editchild);
        TextViewEditChildBirthday   = (TextView)findViewById(R.id.textview_editchild_birthday);
        EditTextEditChildName       = (EditText)findViewById(R.id.edittext_editchild_name);
        EditTextEditChildHeight     = (EditText)findViewById(R.id.edittext_editchild_height);
        EditTextEditChildWeight     = (EditText)findViewById(R.id.edittext_editchild_weight);
        SpinnerBloodTypeEditChild   = (Spinner)findViewById(R.id.spinner_bloodtype_editchild);
        Gender                      = (RadioGroup)findViewById(R.id.gender_editchild);
        RdGenderBoy                 = (RadioButton)findViewById(R.id.rd_gender_boy_editchild);
        RdGenderGirl                = (RadioButton)findViewById(R.id.rd_gender_girl_editchild);
        BtnEditChild                = (Button)findViewById(R.id.btn_editchild);
        DeleteChild                 = (ImageView)findViewById(R.id.delete_child);

        DeleteChild.setOnClickListener(this);

        CloseImgbtnEditChild.setVisibility(View.INVISIBLE);
        ImageAlertNameEditChild.setVisibility(View.INVISIBLE);

        createTypeSearchData();
        setDataupdate();
        setToolBar();
        spinnerChoose();
        setaddData();

    }

    private void setDataupdate() {
        SelectChildItemsDto childItemsDto = SelectChildManager.getInstance().getItemsDto().getResult().get(CId);

        cid = childItemsDto.getC_id();
        EditTextEditChildName.setText(childItemsDto.getC_name());

        switch (childItemsDto.getC_gender()){
            case 1:
                RdGenderBoy.setChecked(true);
                break;
            case 2:
                RdGenderGirl.setChecked(true);
                break;
        }

        TextViewEditChildBirthday.setText(childItemsDto.getC_birthday());
        try {
            EditTextEditChildWeight.setText(childItemsDto.getC_weight()+"");
        }catch (Exception e){
            EditTextEditChildWeight.setText("");
        }
        try {
            EditTextEditChildHeight.setText(childItemsDto.getC_height()+"");
        }catch (Exception e){
            EditTextEditChildHeight.setText("");
        }

    }

    private void setaddData() {
        BtnEditChild.setOnClickListener(this);
    }

    private void spinnerChoose() {
        ArrayAdapter<String> spinsSearch = new ArrayAdapter<String>(EditChildActivity.this
                ,R.layout.support_simple_spinner_dropdown_item,mTypeSearch);

        SpinnerBloodTypeEditChild.setAdapter(spinsSearch);
        SpinnerBloodTypeEditChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BloodType = mTypeSearch.get(position);
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

        final DatePickerDialog dialog = new DatePickerDialog(EditChildActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                DecimalFormat formatter = new DecimalFormat("00");
                String mm = formatter.format(month+1);
                String dd = formatter.format(dayOfMonth);;

                String fulldate = year+ "-" + mm + "-" +dd;

                Day = dayOfMonth;
                Month = month+1;
                Year = year;
                TextViewEditChildBirthday.setText(fulldate);

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

    private void getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String formatDateTime = dateFormat.format(calendar.getTime());
        formatDateTimeToday = dateFormat2.format(calendar.getTime());

        TextViewEditChildBirthday.setText(formatDateTime);

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

    private void createTypeSearchData() {

        if (mTypeSearch.isEmpty()){
            mTypeSearch.add("A");
            mTypeSearch.add("AB");
            mTypeSearch.add("B");
            mTypeSearch.add("O");
        }
    }

    private void DataAddChild() {

        DecimalFormat formatter = new DecimalFormat("00");
        LoginItemsDto loginItemsDto = LoginManager.getInstance().getItemsDto();

        String name,brithday,blood,uid,id;
        int gender=1;
        float height=0f,weigth=0f;

        id = formatter.format(Integer.valueOf(cid));
        uid = formatter.format(Integer.valueOf(loginItemsDto.getId()));
        name = EditTextEditChildName.getText().toString();
        brithday = TextViewEditChildBirthday.getText().toString();
        blood = BloodType;
        try {
            height = Float.valueOf(EditTextEditChildHeight.getText().toString());
        }catch (Exception e){
            height = 0f;
        }
        try {
            weigth = Float.valueOf(EditTextEditChildWeight.getText().toString());
        }catch (Exception e){
            weigth = 0f;
        }

        switch (Gender.getCheckedRadioButtonId()){
            case R.id.rd_gender_boy_addchild:
                gender = 1;
                break;
            case R.id.rd_gender_girl_addchild:
                gender = 2;
                break;
        }

        if(name.length()<1){
            ImageAlertNameEditChild.setVisibility(View.VISIBLE);
        }else {
            ImageAlertNameEditChild.setVisibility(View.INVISIBLE);
            requpdate(id,name,gender,weigth,height,brithday,blood);
        }

    }

    @Override
    public void onClick(View v) {
        if(v == ImageViewCalendarEditChild){
            setDateDialog();
        }
        if(v == BtnEditChild){
            DataAddChild();
        }
        if(v == DeleteChild){
            ShowAlertDialogConfirm();
        }
    }

    private void ShowAlertDialogConfirm() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditChildActivity.this);
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
                    reqdeletechild(cid);
                }
            });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void requpdate(String cid,String name,int gender,float weight,float height,String birthday,String blood) {

        final Context mcontext = EditChildActivity.this;
        String reqBody = "{\"id\" :\""+cid+"\",\"name\": \""+name+"\",\"gender\":"+gender+",\"weight\" :"+weight+"," +
                "\"height\" :"+height+",\"birthday\":\""+birthday+"\",\"blood\" : \""+blood+"\" }";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<UpdateChildDto> call = HttpManager.getInstance().getService().loadAPIupdate(requestBody);
        call.enqueue(new Callback<UpdateChildDto>() {

            @Override
            public void onResponse(Call<UpdateChildDto> call, Response<UpdateChildDto> response) {
                if(response.isSuccessful()){
                    UpdateChildDto dto = response.body();
                    if(response.body().isSuccess()){
                        UpdateChildManager.getInstance().setItemsDto(dto);
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
            public void onFailure(Call<UpdateChildDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialog(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditChildActivity.this);

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

    public void reqdeletechild(String cid) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"id\":\""+cid+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<DeleteChildDto> call = HttpManager.getInstance().getService().loadAPIdelete(requestBody);
        call.enqueue(new Callback<DeleteChildDto>() {

            @Override
            public void onResponse(Call<DeleteChildDto> call, Response<DeleteChildDto> response) {
                if(response.isSuccessful()){
                    DeleteChildDto dto = response.body();
                    DeleteChildManager.getInstance().setItemsDto(dto);

                    ShowAlertDialogDelete(response.body().isSuccess());

                }else {
                    ShowAlertDialogDelete(response.body().isSuccess());
//                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<DeleteChildDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialogDelete(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditChildActivity.this);

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
}
