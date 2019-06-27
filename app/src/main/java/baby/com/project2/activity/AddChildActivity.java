package baby.com.project2.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import baby.com.project2.dto.child.InsertChildDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.InsertChildManager;
import baby.com.project2.manager.singleton.LoginManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChildActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ImageBtnAddProfileAddChild;
    private ImageView CloseImgbtnAddChild,ImageAlertNameAddChild,ImageViewCalendarAddChild;
    private TextView TextViewAddChildBirthday;
    private EditText EditTextAddChildName,EditTextAddChildWeight,EditTextAddChildHeight;
    private Spinner SpinnerBloodTypeAddChild;
    private RadioGroup Gender;
    private RadioButton RdGenderBoy,RdGenderGirl;
    private Button BtnAddChild;
    private Toolbar toolbar;

    private ArrayList<String> mTypeSearch = new ArrayList<String>();

    private int Day;
    private int Month;
    private int Year;

    private String formatDateTimeToday;
    private String BloodType = "A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        initInstances();
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
        ImageViewCalendarAddChild.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initInstances() {

        toolbar                     = (Toolbar)findViewById(R.id.toolbar);
        ImageBtnAddProfileAddChild  = (ImageButton)findViewById(R.id.imagebtn_addprofile_addchild);
        CloseImgbtnAddChild         = (ImageView)findViewById(R.id.close_imgbtn_addchild);
        ImageAlertNameAddChild      = (ImageView)findViewById(R.id.image_alert_name_addchild);
        ImageViewCalendarAddChild   = (ImageView)findViewById(R.id.imageview_calendar_add_child);
        TextViewAddChildBirthday    = (TextView)findViewById(R.id.textview_add_child_birthday);
        EditTextAddChildName        = (EditText)findViewById(R.id.edittext_add_child_name);
        EditTextAddChildHeight      = (EditText)findViewById(R.id.edittext_add_child_height);
        EditTextAddChildWeight      = (EditText)findViewById(R.id.edittext_add_child_weight);
        SpinnerBloodTypeAddChild    = (Spinner)findViewById(R.id.spinner_bloodtype_add_child);
        Gender                      = (RadioGroup)findViewById(R.id.gender_addchild);
        RdGenderBoy                 = (RadioButton)findViewById(R.id.rd_gender_boy_addchild);
        RdGenderGirl                = (RadioButton)findViewById(R.id.rd_gender_girl_addchild);
        BtnAddChild                 = (Button)findViewById(R.id.btn_add_child);

        CloseImgbtnAddChild.setVisibility(View.INVISIBLE);
        ImageAlertNameAddChild.setVisibility(View.INVISIBLE);

        createTypeSearchData();
        setToolBar();
        spinnerChoose();
        setaddData();

    }

    private void setaddData() {
        BtnAddChild.setOnClickListener(this);
    }

    private void spinnerChoose() {
        ArrayAdapter<String> spinsSearch = new ArrayAdapter<String>(AddChildActivity.this
                ,R.layout.support_simple_spinner_dropdown_item,mTypeSearch);

        SpinnerBloodTypeAddChild.setAdapter(spinsSearch);
        SpinnerBloodTypeAddChild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void reqinsert(String name,int gender,float weight,float height,String birthday,String blood,String uid) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"c_name\": \""+name+"\",\"c_gender\":"+gender+",\"c_weight\" :"+weight+",\"c_height\":"+height+","+
                "\"c_birthday\":\""+birthday+"\",\"c_blood\":\""+blood+"\",\"u_id\":\""+uid+"\" }";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<InsertChildDto> call = HttpManager.getInstance().getService().loadAPIInsertChild(requestBody);
        call.enqueue(new Callback<InsertChildDto>() {

            @Override
            public void onResponse(Call<InsertChildDto> call, Response<InsertChildDto> response) {
                if(response.isSuccessful()){
                    InsertChildDto dto = response.body();
                    if(response.body().getSuccess().equals("Acount created")){
                        InsertChildManager.getInstance().setItemsDto(dto);
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
            public void onFailure(Call<InsertChildDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialog(String success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddChildActivity.this);

        if(success.equals("Acount created")){
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

        final DatePickerDialog dialog = new DatePickerDialog(AddChildActivity.this,new DatePickerDialog.OnDateSetListener() {
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

        TextViewAddChildBirthday.setText(formatDateTime);

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

        String name,brithday,blood,uid;
        int gender=1;
        float height=0f,weigth=0f;

        uid = formatter.format(Integer.valueOf(loginItemsDto.getId()));
        name = EditTextAddChildName.getText().toString();
        brithday = TextViewAddChildBirthday.getText().toString();
        blood = BloodType;
        try {
            height = Float.valueOf(EditTextAddChildHeight.getText().toString());
        }catch (Exception e){
            height = 0f;
        }
        try {
            weigth = Float.valueOf(EditTextAddChildWeight.getText().toString());
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
            ImageAlertNameAddChild.setVisibility(View.VISIBLE);
        }else {
            ImageAlertNameAddChild.setVisibility(View.INVISIBLE);
            reqinsert(name,gender,weigth,height,brithday,blood,uid);
        }

    }

    @Override
    public void onClick(View v) {
        if(v == ImageViewCalendarAddChild){
            setDateDialog();
        }
        if(v == BtnAddChild){
            DataAddChild();
        }
    }

}
