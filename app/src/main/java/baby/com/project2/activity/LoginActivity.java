package baby.com.project2.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import baby.com.project2.R;
import baby.com.project2.dto.LoginItemsDto;
import baby.com.project2.dto.RegisterDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.http.HttpLoginManager;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.LoginManager;
import baby.com.project2.manager.singleton.RegisterManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.util.SharedPrefUserFace;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    CardView BtnLogin;
    private LoginButton BtnFace;
    TextInputEditText UserId,PassId;
    CheckBox CbRemember;
    TextView CreateAccount;

    Context context = Contextor.getInstance().getmContext();

    private CallbackManager callbackManager;

    String user="",pass="";

    String id,email,last_name,first_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initInstances();

    }

    private void initInstances() {

        UserId = (TextInputEditText) findViewById(R.id.userId);
        PassId = (TextInputEditText) findViewById(R.id.passId);
        BtnLogin = (CardView) findViewById(R.id.btnlogin);
        BtnFace = (LoginButton) findViewById(R.id.btnface);
        CbRemember = (CheckBox) findViewById(R.id.cbRemember);
        CreateAccount = (TextView) findViewById(R.id.create_account);


        callbackManager = CallbackManager.Factory.create();

        BtnFace.setReadPermissions(Arrays.asList("email","public_profile"));
        BtnFace.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        CreateAccount.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if(currentAccessToken == null){
                Toast.makeText(LoginActivity.this,"logout",Toast.LENGTH_LONG).show();
            }else {
                loadUserProflie(currentAccessToken);
            }

        }
    };

    private void loadUserProflie(AccessToken accessToken){

        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    first_name = object.getString("first_name");
                    last_name = object.getString("last_name");
                    email = object.getString("email");
                    id = object.getString("id");

                    SharedPrefUserFace.getInstance(Contextor.getInstance().getmContext()).saveLoginface(first_name,last_name,email,"",true);
                    reqregister(id,id,first_name+" "+last_name);
                   // Toast.makeText(LoginActivity.this,id,Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("fields","first_name,last_name,email,id");
        request.setParameters(bundle);
        request.executeAsync();

    }

    @Override
    protected void onStart() {
        super.onStart();
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
    protected void onResume() {
        super.onResume();
        BtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == BtnLogin){
            reqLogin(UserId.getText().toString(),PassId.getText().toString(),CbRemember.isChecked());

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

                    if(SharedPrefUserFace.getInstance(Contextor.getInstance().getmContext()).getLoginFace()){
                        SharedPrefUserFace.getInstance(Contextor.getInstance().getmContext()).saveUidFace(dto.getId());
                    }else {
                        SharedPrefUser.getInstance(mcontext).saveLogin(user,pass,b,dto.getId());
                    }

                    if(response.body().isConnect()){
                            if(dto.isChildchecked()){
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                finish();
                                startActivity(intent);
                            } else{
                                Intent intent = new Intent(LoginActivity.this, AddChildActivity.class);
                                startActivity(intent);
                            }
                    }
                    else if (!response.body().isConnect()){
                        Toast.makeText(mcontext,dto.getComment(),Toast.LENGTH_LONG).show(); }
                }else {
                    Toast.makeText(LoginActivity.this,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show(); }
            }
            @Override
            public void onFailure(Call<LoginItemsDto> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }


    public void reqregister(String email,String password,String name) {

        final Context mcontext = Contextor.getInstance().getmContext();
        String reqBody = "{\"user_email\": \""+email+"\",\"user_pass\":\""+password+"\",\"user_name\":\""+name+"\"}";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),reqBody);
        Call<RegisterDto> call = HttpManager.getInstance().getService().loadAPIRegister(requestBody);
        call.enqueue(new Callback<RegisterDto>() {

            @Override
            public void onResponse(Call<RegisterDto> call, Response<RegisterDto> response) {
                    RegisterDto dto = response.body();
                    RegisterManager.getInstance().setItemsDto(dto);

                  reqLogin(id,id,true);

            }
            @Override
            public void onFailure(Call<RegisterDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
