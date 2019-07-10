package baby.com.project2.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import baby.com.project2.R;
import baby.com.project2.dto.LoginItemsDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpLoginManager;
import baby.com.project2.manager.singleton.LoginManager;
import baby.com.project2.util.SharedPrefUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    CardView BtnLogin,BtnFace;
    TextInputEditText UserId,PassId;
    CheckBox CbRemember;
    TextView CreateAccount;

    Context context = Contextor.getInstance().getmContext();

    String user="",pass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initInstances();

    }

    private void initInstances() {

        UserId = (TextInputEditText) findViewById(R.id.userId);
        PassId = (TextInputEditText) findViewById(R.id.passId);
        BtnLogin = (CardView) findViewById(R.id.btnlogin);
        BtnFace = (CardView) findViewById(R.id.btnface);
        CbRemember = (CheckBox) findViewById(R.id.cbRemember);
        CreateAccount = (TextView) findViewById(R.id.create_account);

        CreateAccount.setOnClickListener(this);

        if(SharedPrefUser.getInstance(context).getRemember()){
            CbRemember.setChecked(SharedPrefUser.getInstance(context).getRemember());
            UserId.setText(SharedPrefUser.getInstance(context).getUsername());
            PassId.setText(SharedPrefUser.getInstance(context).getPassword());
        }

        if(SharedPrefUser.getInstance(context).getUid().length()>0){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            finish();
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == BtnLogin){
            reqLogin(UserId.getText().toString(),PassId.getText().toString(),CbRemember.isChecked());
//            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            finish();
//            startActivity(intent);
        }

        if(v == CreateAccount){
            Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        }
    }

    public void reqLogin(final String user, final String pass,final boolean b) {

        final Context mcontext = LoginActivity.this;
        String reqBody = "{\"email\":\""+user+"\",\"password\":\""+pass+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<LoginItemsDto> call = HttpLoginManager.getInstance().getService().loadAPILogin(requestBody);
        call.enqueue(new Callback<LoginItemsDto>() {

            @Override
            public void onResponse(Call<LoginItemsDto> call, Response<LoginItemsDto> response) {

                if(response.isSuccessful()){

                    LoginItemsDto dto = response.body();
                    LoginManager.getInstance().setItemsDto(dto);

                    SharedPrefUser.getInstance(mcontext).saveLogin(user,pass,b,dto.getId());

                    if(response.body().isConnect()){
                            if(dto.isChildchecked()){
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(LoginActivity.this, AddChildActivity.class);
                                startActivity(intent);
                            }
                    }

                    else if (!response.body().isConnect()){
                        Toast.makeText(mcontext,dto.getComment(),Toast.LENGTH_LONG).show();
                    }


                }else {
                    Toast.makeText(LoginActivity.this,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginItemsDto> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
