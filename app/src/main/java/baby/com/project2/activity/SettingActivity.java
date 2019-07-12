package baby.com.project2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import baby.com.project2.R;
import baby.com.project2.dto.user.SelectUserDto;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.user.SelectUserManager;
import baby.com.project2.manager.singleton.vaccine.DataVaccineManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    SelectUserDto dto;
    private TextView TextViewNameUser,TextViewEmailUser;
    private CardView CardViewSettingEditUser,CardViewSettingEditUserPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initInstances();
    }

    private void initInstances() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextViewNameUser = (TextView)findViewById(R.id.textview_name_user);
        TextViewEmailUser = (TextView)findViewById(R.id.textview_email_user);
        CardViewSettingEditUser = (CardView)findViewById(R.id.cardview_setting_edit_user);
        CardViewSettingEditUserPass = (CardView)findViewById(R.id.cardview_setting_edit_user_pass);

    }

    @Override
    protected void onStart() {
        super.onStart();
        reqSelectUser(SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getUid());
    }

    @Override
    protected void onResume() {
        super.onResume();
        CardViewSettingEditUser.setOnClickListener(this);
        CardViewSettingEditUserPass.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void reqSelectUser(String U_id) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"id\":\""+U_id+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<SelectUserDto> call = HttpManager.getInstance().getService().loadAPISelectUserDtoCall(requestBody);
        call.enqueue(new Callback<SelectUserDto>() {

            @Override
            public void onResponse(Call<SelectUserDto> call, Response<SelectUserDto> response) {
                if(response.isSuccessful()){
                    dto = response.body();
                    SelectUserManager.getInstance().setItemsDto(dto);
                    setDataUser();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectUserDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setDataUser() {

        TextViewNameUser.setText(dto.getUser().get(0).getU_name());
        TextViewEmailUser.setText(dto.getUser().get(0).getU_email());

    }

    @Override
    public void onClick(View v) {
        if(v == CardViewSettingEditUserPass){
            Intent intent = new Intent(SettingActivity.this, EditPassUserActivity.class);
            intent.putExtra("U_id",dto.getUser().get(0).getU_id());
            intent.putExtra("U_name",dto.getUser().get(0).getU_name());
            intent.putExtra("U_password",dto.getUser().get(0).getU_password());
            this.startActivity(intent);
        }

        if(v == CardViewSettingEditUser){
            Intent intent = new Intent(SettingActivity.this, EditUserNameActivity.class);
            intent.putExtra("U_id",dto.getUser().get(0).getU_id());
            intent.putExtra("U_name",dto.getUser().get(0).getU_name());
            intent.putExtra("U_password",dto.getUser().get(0).getU_password());
            this.startActivity(intent);
        }
    }
}
