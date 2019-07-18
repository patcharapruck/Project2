package baby.com.project2.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import baby.com.project2.dto.growup.InsertGrowUpDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.DateManager;
import baby.com.project2.manager.singleton.child.DeleteChildManager;
import baby.com.project2.manager.singleton.LoginManager;
import baby.com.project2.manager.singleton.child.SelectChildManager;
import baby.com.project2.manager.singleton.child.UpdateChildManager;
import baby.com.project2.manager.singleton.growup.InsertGrowupManager;
import baby.com.project2.sentImage.ApiClient;
import baby.com.project2.sentImage.Img_Pojo;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditChildActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ImageBtnAddProfileEditChild;
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

    private String formatDate;
    private String BloodType ;
    private int CId;
    private String cid;

    Bitmap bitmap,resize;
    private  static final int IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent id = getIntent();
        CId = id.getIntExtra("Cid",0);

        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDateTime();
        ImageViewCalendarEditChild.setOnClickListener(this);
        ImageBtnAddProfileEditChild.setOnClickListener(this);
    }

    private void initInstances() {

        toolbar                     = (Toolbar)findViewById(R.id.toolbar);
        ImageBtnAddProfileEditChild = (ImageView) findViewById(R.id.imagebtn_addprofile_editchild);
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

        setDataupdate();
        createTypeSearchData();
        setToolBar();
        spinnerChoose();
        setaddData();

    }

    private void setDataupdate() {
        SelectChildItemsDto childItemsDto = SelectChildManager.getInstance().getItemsDto().getResult().get(CId);

        cid = childItemsDto.getC_id();
        formatDate = childItemsDto.getC_birthday();
        EditTextEditChildName.setText(childItemsDto.getC_name());
        BloodType = childItemsDto.getC_blood();

        switch (childItemsDto.getC_gender()){
            case 1:
                RdGenderBoy.setChecked(true);
                break;
            case 2:
                RdGenderGirl.setChecked(true);
                break;
        }

        TextViewEditChildBirthday.setText(formatDate);
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

        if(childItemsDto.getC_image().length()<1||childItemsDto.getC_image()==null){
            if(childItemsDto.getC_gender() == 1){
                ImageBtnAddProfileEditChild.setImageResource(R.mipmap.ic_baby_boy);
            }else {
                ImageBtnAddProfileEditChild.setImageResource(R.mipmap.ic_baby_gril);
            }
        }else{
            byte[] decodedString = Base64.decode(childItemsDto.getC_image(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageBtnAddProfileEditChild.setImageBitmap(decodedByte);
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

    private void createTypeSearchData() {

        if (mTypeSearch.isEmpty()){
            mTypeSearch.add(BloodType);
            mTypeSearch.add("A");
            mTypeSearch.add("AB");
            mTypeSearch.add("B");
            mTypeSearch.add("O");
        }
    }

    private void DataAddChild() {

        DecimalFormat formatter = new DecimalFormat("00");
        LoginItemsDto loginItemsDto = LoginManager.getInstance().getItemsDto();

        String name,brithday,blood,uid,id,image="";
        int gender=1;
        float height=0f,weigth=0f;

        id = formatter.format(Integer.valueOf(cid));
      //  uid = formatter.format(Integer.valueOf(loginItemsDto.getId()));
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
            case R.id.rd_gender_boy_editchild:
                gender = 1;
                break;
            case R.id.rd_gender_girl_editchild:
                gender = 2;
                break;
        }

        if(name.length()<1){
            ImageAlertNameEditChild.setVisibility(View.VISIBLE);
        }else {
            try{
                image = uploadImage();
            }catch (Exception e){
                image = "";
            }

            ImageAlertNameEditChild.setVisibility(View.INVISIBLE);
            requpdate(id,name,gender,weigth,height,brithday,blood,image);
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
        if(v == ImageBtnAddProfileEditChild){
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(EditChildActivity.this);
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
                        ImageBtnAddProfileEditChild.setBackgroundResource(R.mipmap.ic_add_baby);
                    }

                }
            });
            android.app.AlertDialog alert = alertDialog.create();
            alert.show();
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

    public void requpdate(final String cid, String name, int gender, final float weight, final float height, final String birthday, String blood,String image) {

        final Context mcontext = EditChildActivity.this;
        String reqBody = "{\"id\" :\""+cid+"\",\"name\": \""+name+"\",\"gender\":"+gender+",\"weight\" :"+weight+"," +
                "\"height\" :"+height+",\"birthday\":\""+birthday+"\",\"blood\" : \""+blood+"\",\"c_image\":\""+image+"\" }";
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
                ImageBtnAddProfileEditChild.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String uploadImage() {

        ArrayList<String[]> ttt = new ArrayList<>();
        final String image = convertToString();
        image.replaceAll("\\\\n", "\n");
        ttt.add(image.split("\\n"));
        String zz="";
        String[] a = ttt.get(0);
        int size = a.length;

        for(int i=0;i<size;i++){
            zz = zz + a[i];
        }

        return zz;
    }
}
