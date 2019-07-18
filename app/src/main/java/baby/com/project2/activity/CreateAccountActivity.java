package baby.com.project2.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import baby.com.project2.R;
import baby.com.project2.dto.RegisterDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.RegisterManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    EditText EditTextName,EditTextEmail,EditTextPassword,EditTextConfirmPassword;
    CheckBox Cbrobod;
    CardView BtnSignup;
    TextView alert_name,alert_email,alert_pass,alert_confirmpassword;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initInstances();
    }

    private void initInstances() {

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditTextName            = (EditText)findViewById(R.id.edittext_name);
        EditTextEmail           = (EditText)findViewById(R.id.edittext_email);
        EditTextPassword        = (EditText)findViewById(R.id.edittext_password);
        EditTextConfirmPassword = (EditText)findViewById(R.id.edittext_confirmpassword);
        Cbrobod                 = (CheckBox) findViewById(R.id.cbrobod);
        BtnSignup               = (CardView)findViewById(R.id.btnsignup);

        alert_confirmpassword = (TextView)findViewById(R.id.alert_confirmpassword);
        alert_name = (TextView)findViewById(R.id.alert_name);
        alert_pass = (TextView)findViewById(R.id.alert_pass);
        alert_email = (TextView)findViewById(R.id.alert_email);

        BtnSignup.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void reqregister(String email,String password,String name) {

        final Context mcontext = CreateAccountActivity.this;
        String reqBody = "{\"user_email\": \""+email+"\",\"user_pass\":\""+password+"\",\"user_name\":\""+name+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<RegisterDto> call = HttpManager.getInstance().getService().loadAPIRegister(requestBody);
        call.enqueue(new Callback<RegisterDto>() {

            @Override
            public void onResponse(Call<RegisterDto> call, Response<RegisterDto> response) {
                if(response.isSuccessful()){
                    RegisterDto dto = response.body();
                    RegisterManager.getInstance().setItemsDto(dto);
                    ShowAlertDialog(response.body().getSuccess());
                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<RegisterDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == BtnSignup){
            DataRegister();
        }
    }

    private void DataRegister() {

        String name,email,pass,confirm;
        boolean checked;
        boolean checked_reg = true;

        name = EditTextName.getText().toString();
        email = EditTextEmail.getText().toString();
        pass = EditTextPassword.getText().toString();
        confirm = EditTextConfirmPassword.getText().toString();
        checked = Cbrobod.isChecked();

        alert_confirmpassword.setText("");
        alert_name.setText("");
        alert_pass.setText("");
        alert_email.setText("");

        if(name.length()<1){
            alert_name.setText("กรุณากรอกข้อมูล");
            checked_reg = false;
        }
        if(email.length()<1){
            alert_email.setText("กรุณากรอกข้อมูล");
            checked_reg = false;
        }
        if(pass.length()<6){
            alert_pass.setText("กรุณากรอกข้อมูลรหัสผ่านอย่างน้อย 6 ตัว");
            checked_reg = false;
        }
        if(confirm.length()<6){
            alert_confirmpassword.setText("กรุณากรอกข้อมูลรหัสผ่านอย่างน้อย 6 ตัว");
            checked_reg = false;
        }
        if(!pass.equals(confirm)){
            Toast.makeText(Contextor.getInstance().getmContext(),"รหัสผ่านไม่ตรงกัน",Toast.LENGTH_LONG).show();
            checked_reg = false;
        }
        if(!checked){
            checked_reg = false;
        }

        if(checked_reg){
            reqregister(email,pass,name);
        }

    }

    private void ShowAlertDialog(String success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccountActivity.this);

        if(success.equals("Acount created")){
            builder.setTitle("สมัครสมาชิก");
            builder.setMessage("สมัครสมาชิกสำเร็จ");
            builder.setIcon(R.mipmap.ic_success);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }else if (success.equals("Creat failed")){
            builder.setTitle("สมัครสมาชิก");
            builder.setMessage("เกิดข้อผิดพลาด สมัครสมาชิกล้มเหลว");
            builder.setIcon(R.mipmap.ic_failed);
            builder.setCancelable(true);
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }else if (success.equals("Repeat information")){
            builder.setTitle("สมัครสมาชิก");
            builder.setMessage("เกิดข้อผิดพลาด email นี้มีผู้ใช้แล้ว");
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
