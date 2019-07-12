package baby.com.project2.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import baby.com.project2.R;
import baby.com.project2.dto.user.UpdateUserDto;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.user.UpdateUserManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPassUserActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private EditText EditTextPass,EditTextEditPass,EditTextEditConfrim;
    private Button ButtonSaveNewpass;

    private String U_id,U_name,U_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass_user);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent id = getIntent();
        U_id = id.getStringExtra("U_id");
        U_name = id.getStringExtra("U_name");
        U_password = id.getStringExtra("U_password");

        initInstances();
    }

    private void initInstances() {

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditTextPass= (EditText)findViewById(R.id.edittext_pass);
        EditTextEditPass= (EditText)findViewById(R.id.edittext_edit_pass);
        EditTextEditConfrim= (EditText)findViewById(R.id.edittext_edit_confrim);
        ButtonSaveNewpass = (Button)findViewById(R.id.button_save_newpass);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ButtonSaveNewpass.setOnClickListener(this);
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
        if(v == ButtonSaveNewpass){
            String pass,edpass,cfpass;
            pass = EditTextPass.getText().toString();
            edpass = EditTextEditPass.getText().toString();
            cfpass = EditTextEditConfrim.getText().toString();

            if(pass.length()<1||edpass.length()<1||cfpass.length()<1){
                Toast.makeText(EditPassUserActivity.this,"กรุณาใส่ข้อมูลให้ครบ",Toast.LENGTH_LONG).show();
            }else{
                if(!pass.equals(U_password)) {
                    Toast.makeText(EditPassUserActivity.this, "รหัสผ่านปัจจุบันไม่ถูกต้อง", Toast.LENGTH_LONG).show();
                }else{
                    if(!edpass.equals(cfpass)){
                        Toast.makeText(EditPassUserActivity.this, "การยืนยันรหัสผ่านไม่ตรงกัน", Toast.LENGTH_LONG).show();
                    }else{
                        U_password = pass;
                        requpdate(U_name,U_password,U_id);
                    }
                }
            }
        }
    }

    public void requpdate(String u_name,String u_pass,String u_id) {

        final Context mcontext = EditPassUserActivity.this;
        String reqBody = "{\"id\":\""+u_id+"\",\"name\" :\""+u_name+"\",\"pass\":\""+u_pass+"\" }";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<UpdateUserDto> call = HttpManager.getInstance().getService().loadAPIUpdateUserDtoCall(requestBody);
        call.enqueue(new Callback<UpdateUserDto>() {

            @Override
            public void onResponse(Call<UpdateUserDto> call, Response<UpdateUserDto> response) {
                if(response.isSuccessful()){
                    UpdateUserDto dto = response.body();
                    UpdateUserManager.getInstance().setItemsDto(dto);
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
            public void onFailure(Call<UpdateUserDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ShowAlertDialog(boolean success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditPassUserActivity.this);

        if(success){
            builder.setTitle("อัพเดทข้อมูลผู้ใช้");
            builder.setMessage("อัพเดทข้อมูลผู้ใช้แล้ว");
            builder.setIcon(R.mipmap.ic_success);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }else {
            builder.setTitle("อัพเดทข้อมูลผู้ใช้");
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
}
